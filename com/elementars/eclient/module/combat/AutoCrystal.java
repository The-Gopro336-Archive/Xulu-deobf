package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.event.events.MotionEvent;
import com.elementars.eclient.event.events.MotionEventPost;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.guirewrite.elements.PvPInfo;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.Plane;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.TargetPlayers;
import com.elementars.eclient.util.VectorUtils;
import com.elementars.eclient.util.XuluTessellator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

public class AutoCrystal extends Module {
   // $FF: synthetic field
   private final Value placeTick = this.register(new Value("Place Delay", this, 1, 0, 20));
   // $FF: synthetic field
   private final Value facePlace = this.register(new Value("Faceplace HP", this, 6, 0, 40));
   // $FF: synthetic field
   EnumFacing f;
   // $FF: synthetic field
   private final Value fastType = this.register(new Value("Fast Type", this, "Instant", new String[]{"Instant", "Ignore"}));
   // $FF: synthetic field
   private final Value echatcolor;
   // $FF: synthetic field
   private final Value range = this.register(new Value("Hit Range", this, 5.0F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value armorDmg = this.register(new Value("Armor %", this, 15, 0, 100));
   // $FF: synthetic field
   private final Value explode = this.register(new Value("Hit", this, true));
   // $FF: synthetic field
   private final Value oneHole = this.register(new Value("1.13 Mode", this, false));
   // $FF: synthetic field
   private BlockPos renderOld;
   // $FF: synthetic field
   private final List placedCrystals = new ArrayList();
   // $FF: synthetic field
   private static float newPitch;
   // $FF: synthetic field
   private final Value fast = this.register(new Value("Fast Mode", this, false));
   // $FF: synthetic field
   private final Value minDmg = this.register(new Value("Min Damage", this, 5, 0, 40));
   // $FF: synthetic field
   private int newSlot;
   // $FF: synthetic field
   private final Value antiWeakness = this.register(new Value("Anti Weakness", this, true));
   // $FF: synthetic field
   private int placeCounter;
   // $FF: synthetic field
   private final Value toggleOff = this.register(new Value("Toggle Off", this, false));
   // $FF: synthetic field
   private final Value smoothEsp;
   // $FF: synthetic field
   private final List ignoreList;
   // $FF: synthetic field
   private final Value walls = this.register(new Value("Walls Range", this, 3.5F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value damageWhite;
   // $FF: synthetic field
   private Map retryMap;
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   private boolean switchCooldown = false;
   // $FF: synthetic field
   private final Value dchatcolor;
   // $FF: synthetic field
   private final Value sync = this.register(new Value("Sync Break", this, true));
   // $FF: synthetic field
   public boolean isActive;
   // $FF: synthetic field
   private static double yaw;
   // $FF: synthetic field
   private final Value smoothSpeed;
   // $FF: synthetic field
   private final Value espA;
   // $FF: synthetic field
   private static boolean isSpoofingAngles;
   // $FF: synthetic field
   private final Value hitRetryDelay = this.register(new Value("Retry Delay", this, 2, 0, 20));
   // $FF: synthetic field
   private final Value spam = this.register(new Value("Spam", this, true));
   // $FF: synthetic field
   private final Value ER = this.register(new Value("Enemy Range", this, 5.0F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value noSuicide = this.register(new Value("No Suicide", this, true));
   // $FF: synthetic field
   private final Value chat = this.register(new Value("Toggle Msgs", this, true));
   // $FF: synthetic field
   private int waitCounter;
   // $FF: synthetic field
   private final Value espF;
   // $FF: synthetic field
   private Map attemptMap;
   // $FF: synthetic field
   private final Value waitTick = this.register(new Value("Tick Delay", this, 1, 0, 20));
   // $FF: synthetic field
   private boolean isAttacking = false;
   // $FF: synthetic field
   private final Value toggleHealth = this.register(new Value("Toggle Off Health", this, 10, 0, 20));
   // $FF: synthetic field
   private final Value pre = this.register(new Value("Prioritize Enemies", this, false));
   // $FF: synthetic field
   private static float newYaw;
   // $FF: synthetic field
   private final Value raytrace = this.register(new Value("Raytrace", this, false));
   // $FF: synthetic field
   private final Value maxSelfDmg;
   // $FF: synthetic field
   private BlockPos render;
   // $FF: synthetic field
   private final Value rotateMode = this.register(new Value("Rot. Mode", this, "New", new String[]{"Old", "New"})).visibleWhen((var1) -> {
      return (Boolean)this.rotate.getValue();
   });
   // $FF: synthetic field
   private final Value renderDamage;
   // $FF: synthetic field
   private final Value armor = this.register(new Value("Armor Place", this, true));
   // $FF: synthetic field
   private final Value espB;
   // $FF: synthetic field
   private final Value autoSwitch = this.register(new Value("Auto Switch", this, true));
   // $FF: synthetic field
   private final Value randRotations = this.register(new Value("Random Rotations", this, true));
   // $FF: synthetic field
   private static double pitch;
   // $FF: synthetic field
   private final Value renderBoolean;
   // $FF: synthetic field
   private final Value place = this.register(new Value("Place", this, true));
   // $FF: synthetic field
   private final Value noGappleSwitch;
   // $FF: synthetic field
   private final Value rainbow;
   // $FF: synthetic field
   private final Value espR;
   // $FF: synthetic field
   private final Value placeRange = this.register(new Value("Place Range", this, 5.0F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value espG;
   // $FF: synthetic field
   private final Value rendermode;
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   private final Value nodesync = this.register(new Value("No Desync", this, true));
   // $FF: synthetic field
   private Entity renderEnt;
   // $FF: synthetic field
   private final Value hitAttempts = this.register(new Value("Hit Attempts", this, 5, 0, 20));
   // $FF: synthetic field
   private int oldSlot = -1;
   // $FF: synthetic field
   ConcurrentHashMap fadeList;
   // $FF: synthetic field
   private final Value explodeMode = this.register(new Value("Hit Mode", this, "All", new String[]{"All", "OnlyOwn"}));
   // $FF: synthetic field
   public static boolean isRand;

   private void doAutoCrystal() {
      isRand = (Boolean)this.randRotations.getValue();
      this.isActive = false;
      if (mc.player != null && !mc.player.isDead) {
         if (this.shouldPause()) {
            resetRotation();
         } else {
            if (mc.player.getHealth() <= (float)(Integer)this.toggleHealth.getValue() && (Boolean)this.toggleOff.getValue()) {
               this.toggle();
            }

            if ((Boolean)this.fast.getValue() && (Integer)this.waitTick.getValue() > 0) {
               if (this.waitCounter < (Integer)this.waitTick.getValue()) {
                  ++this.waitCounter;
               } else {
                  this.waitCounter = 0;
               }
            }

            this.fadeList.forEach((var1x, var2x) -> {
               if (var2x <= 0) {
                  this.fadeList.remove(var1x);
               } else {
                  this.fadeList.put(var1x, var2x - (Integer)this.smoothSpeed.getValue());
               }

            });
            int var1 = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
            if (var1 == -1) {
               for(int var2 = 0; var2 < 9; ++var2) {
                  if (mc.player.inventory.getStackInSlot(var2).getItem() == Items.END_CRYSTAL) {
                     var1 = var2;
                     break;
                  }
               }
            }

            this.retryMap.forEach((var1x, var2x) -> {
               if (var1x.isDead) {
                  this.retryMap.remove(var1x);
               } else if ((Integer)this.retryMap.get(var1x) != 0) {
                  this.retryMap.put(var1x, var2x - 1);
               }

            });
            EntityEnderCrystal var23 = null;
            ArrayList var3 = new ArrayList();
            Iterator var4 = mc.world.loadedEntityList.iterator();

            while(true) {
               while(true) {
                  EntityEnderCrystal var6;
                  do {
                     do {
                        Entity var5;
                        do {
                           if (!var4.hasNext()) {
                              if (!var3.isEmpty()) {
                                 var3.sort(Comparator.comparing((var0) -> {
                                    return mc.player.getDistance(var0);
                                 }));
                                 var23 = (EntityEnderCrystal)var3.get(0);
                              }

                              if (!(Boolean)this.explode.getValue() || var23 == null || (Boolean)this.fast.getValue() && this.waitCounter != 0) {
                                 resetRotation();
                                 if (this.oldSlot != -1) {
                                    mc.player.inventory.currentItem = this.oldSlot;
                                    this.oldSlot = -1;
                                 }

                                 this.isAttacking = false;
                                 this.isActive = false;
                                 if ((Integer)this.placeTick.getValue() > 0) {
                                    if (this.placeCounter < (Integer)this.placeTick.getValue()) {
                                       ++this.placeCounter;
                                       PvPInfo.place = false;
                                       return;
                                    }

                                    this.placeCounter = 0;
                                 }

                                 boolean var28 = false;
                                 if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                                    var28 = true;
                                 } else if (var1 == -1) {
                                    return;
                                 }

                                 List var30 = this.findCrystalBlocks();
                                 ArrayList var29 = new ArrayList();
                                 ArrayList var7 = new ArrayList();
                                 Iterator var8 = mc.world.playerEntities.iterator();

                                 EntityPlayer var9;
                                 while(var8.hasNext()) {
                                    var9 = (EntityPlayer)var8.next();
                                    if (!Friends.isFriend(var9.getName())) {
                                       var29.add(var9);
                                    }
                                 }

                                 var29.sort(Comparator.comparing((var0) -> {
                                    return mc.player.getDistance(var0);
                                 }));
                                 var29.removeIf((var1x) -> {
                                    return mc.player.getDistance(var1x) > (Float)this.ER.getValue();
                                 });
                                 if ((Boolean)this.pre.getValue()) {
                                    var8 = mc.world.playerEntities.iterator();

                                    while(var8.hasNext()) {
                                       var9 = (EntityPlayer)var8.next();
                                       if (!Friends.isFriend(var9.getName()) && Enemies.isEnemy(var9.getName())) {
                                          var7.add(var9);
                                       }
                                    }

                                    var7.sort(Comparator.comparing((var0) -> {
                                       return mc.player.getDistance(var0);
                                    }));
                                    var7.removeIf((var1x) -> {
                                       return mc.player.getDistance(var1x) > (Float)this.ER.getValue();
                                    });
                                    if (!var7.isEmpty()) {
                                       var29.clear();
                                       var29.addAll(var7);
                                    }
                                 }

                                 BlockPos var31 = null;
                                 double var32 = 0.5D;
                                 double var11 = 6.9696969E7D;
                                 Iterator var13 = var29.iterator();

                                 label360:
                                 while(true) {
                                    Entity var14;
                                    do {
                                       do {
                                          do {
                                             do {
                                                if (!var13.hasNext()) {
                                                   if (var32 == 0.5D) {
                                                      this.render = null;
                                                      this.renderEnt = null;
                                                      resetRotation();
                                                      return;
                                                   }

                                                   this.render = var31;
                                                   if ((Boolean)this.place.getValue()) {
                                                      if (mc.player == null) {
                                                         PvPInfo.place = false;
                                                         return;
                                                      }

                                                      this.isActive = true;
                                                      if ((Boolean)this.rotate.getValue()) {
                                                         this.lookAtPacket((double)var31.getX() + 0.5D, (double)var31.getY() - 0.5D, (double)var31.getZ() + 0.5D, mc.player);
                                                      }

                                                      RayTraceResult var33 = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)var31.getX() + 0.5D, (double)var31.getY() - 0.5D, (double)var31.getZ() + 0.5D));
                                                      if ((Boolean)this.raytrace.getValue()) {
                                                         if (var33 == null || var33.sideHit == null) {
                                                            var8 = null;
                                                            this.f = null;
                                                            this.render = null;
                                                            resetRotation();
                                                            this.isActive = false;
                                                            PvPInfo.place = false;
                                                            return;
                                                         }

                                                         this.f = var33.sideHit;
                                                      }

                                                      if (!var28 && mc.player.inventory.currentItem != var1) {
                                                         if ((Boolean)this.autoSwitch.getValue()) {
                                                            if ((Boolean)this.noGappleSwitch.getValue() && this.isEatingGap()) {
                                                               this.isActive = false;
                                                               resetRotation();
                                                               PvPInfo.place = false;
                                                               return;
                                                            }

                                                            this.isActive = true;
                                                            mc.player.inventory.currentItem = var1;
                                                            resetRotation();
                                                            this.switchCooldown = true;
                                                         }

                                                         PvPInfo.place = false;
                                                         return;
                                                      }

                                                      if (this.switchCooldown) {
                                                         this.switchCooldown = false;
                                                         PvPInfo.place = false;
                                                         return;
                                                      }

                                                      if (var31 != null && mc.player != null) {
                                                         PvPInfo.place = true;
                                                         this.isActive = true;
                                                         if ((Boolean)this.raytrace.getValue() && this.f != null) {
                                                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(var31, this.f, var28 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                                                         } else {
                                                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(var31, EnumFacing.UP, var28 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                                                         }

                                                         TargetPlayers.addTargetedPlayer(this.renderEnt.getName());
                                                      }

                                                      this.isActive = false;
                                                   }

                                                   return;
                                                }

                                                var14 = (Entity)var13.next();
                                             } while(var14 == mc.player);
                                          } while(((EntityLivingBase)var14).getHealth() <= 0.0F);
                                       } while(var14.isDead);
                                    } while(mc.player == null);

                                    Iterator var15 = var30.iterator();

                                    while(true) {
                                       BlockPos var16;
                                       double var19;
                                       double var21;
                                       do {
                                          do {
                                             do {
                                                double var17;
                                                do {
                                                   do {
                                                      do {
                                                         if (!var15.hasNext()) {
                                                            continue label360;
                                                         }

                                                         var16 = (BlockPos)var15.next();
                                                         var17 = var14.getDistanceSq(var16);
                                                      } while(var14.isDead);
                                                   } while(((EntityLivingBase)var14).getHealth() <= 0.0F);
                                                } while(var17 >= 169.0D);

                                                var19 = (double)calculateDamage((double)var16.getX() + 0.5D, (double)(var16.getY() + 1), (double)var16.getZ() + 0.5D, var14);
                                             } while(var19 < (double)(Integer)this.minDmg.getValue() && ((EntityLivingBase)var14).getHealth() + ((EntityLivingBase)var14).getAbsorptionAmount() > (float)(AutoTotem.isFullArmor((EntityPlayer)var14) && !this.isArmorLow((EntityPlayer)var14) ? (Integer)this.facePlace.getValue() : 36));
                                          } while(!(var19 > var32));

                                          var21 = (double)calculateDamage((double)var16.getX() + 0.5D, (double)(var16.getY() + 1), (double)var16.getZ() + 0.5D, mc.player);
                                       } while(var21 > var19 && !(var19 < (double)((EntityLivingBase)var14).getHealth()));

                                       if (!(var21 - 0.5D > (double)mc.player.getHealth()) && !(var21 > (double)(Integer)this.maxSelfDmg.getValue())) {
                                          var32 = var19;
                                          var31 = var16;
                                          this.renderEnt = var14;
                                       }
                                    }
                                 }
                              }

                              if (!mc.player.canEntityBeSeen(var23) && mc.player.getDistance(var23) > (Float)this.walls.getValue()) {
                                 PvPInfo.attack = false;
                                 return;
                              }

                              if (!(Boolean)this.fast.getValue() && (Integer)this.waitTick.getValue() > 0) {
                                 if (this.waitCounter < (Integer)this.waitTick.getValue()) {
                                    ++this.waitCounter;
                                    PvPInfo.attack = false;
                                    return;
                                 }

                                 this.waitCounter = 0;
                              }

                              int var24;
                              if ((Boolean)this.antiWeakness.getValue() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                                 if (!this.isAttacking) {
                                    this.oldSlot = mc.player.inventory.currentItem;
                                    PvPInfo.attack = true;
                                    this.isAttacking = true;
                                 }

                                 this.newSlot = -1;

                                 for(var24 = 0; var24 < 9; ++var24) {
                                    ItemStack var25 = mc.player.inventory.getStackInSlot(var24);
                                    if (var25 != ItemStack.EMPTY) {
                                       if (var25.getItem() instanceof ItemSword) {
                                          this.newSlot = var24;
                                          break;
                                       }

                                       if (var25.getItem() instanceof ItemTool) {
                                          this.newSlot = var24;
                                          break;
                                       }
                                    }
                                 }

                                 if (this.newSlot != -1) {
                                    mc.player.inventory.currentItem = this.newSlot;
                                    this.switchCooldown = true;
                                 }
                              }

                              PvPInfo.attack = true;
                              this.isActive = true;
                              if ((Boolean)this.rotate.getValue()) {
                                 this.lookAtPacket(var23.posX, var23.posY, var23.posZ, mc.player);
                              }

                              if ((Boolean)this.spam.getValue()) {
                                 for(var24 = 0; var24 < 50; ++var24) {
                                    if ((Boolean)this.sync.getValue()) {
                                       mc.player.connection.sendPacket(new CPacketUseEntity(var23));
                                    } else {
                                       mc.playerController.attackEntity(mc.player, var23);
                                    }
                                 }
                              } else if ((Boolean)this.sync.getValue()) {
                                 mc.player.connection.sendPacket(new CPacketUseEntity(var23));
                              } else {
                                 mc.playerController.attackEntity(mc.player, var23);
                              }

                              mc.player.swingArm(EnumHand.MAIN_HAND);
                              this.isActive = false;
                              if ((Boolean)this.fast.getValue()) {
                                 String var26 = (String)this.fastType.getValue();
                                 byte var27 = -1;
                                 switch(var26.hashCode()) {
                                 case -2106529294:
                                    if (var26.equals("Ignore")) {
                                       var27 = 1;
                                    }
                                    break;
                                 case -672743999:
                                    if (var26.equals("Instant")) {
                                       var27 = 0;
                                    }
                                 }

                                 switch(var27) {
                                 case 0:
                                    var23.setDead();
                                    break;
                                 case 1:
                                    this.ignoreList.add(var23);
                                 }
                              }

                              if (this.attemptMap.containsKey(var23)) {
                                 this.attemptMap.put(var23, (Integer)this.attemptMap.get(var23) + 1);
                              } else {
                                 this.attemptMap.put(var23, 1);
                              }

                              return;
                           }

                           var5 = (Entity)var4.next();
                        } while(!(var5 instanceof EntityEnderCrystal));

                        var6 = (EntityEnderCrystal)var5;
                     } while(!(mc.player.getDistance(var6) <= (Float)this.range.getValue()));
                  } while(!this.checkCrystal(var6));

                  if (this.attemptMap.containsKey(var6) && ((Integer)this.attemptMap.get(var6)).equals(this.hitAttempts.getValue())) {
                     if (!this.retryMap.containsKey(var6) || (Integer)this.retryMap.get(var6) != 0) {
                        if (!this.retryMap.containsKey(var6)) {
                           this.retryMap.put(var6, this.hitRetryDelay.getValue());
                        }
                        continue;
                     }

                     this.attemptMap.put(var6, 0);
                     this.retryMap.remove(var6);
                  }

                  var3.add(var6);
               }
            }
         }
      }
   }

   public static void setPlayerRotations(float var0, float var1) {
      mc.player.rotationYaw = var0;
      mc.player.rotationYawHead = var0;
      mc.player.rotationPitch = var1;
   }

   private static float getDamageMultiplied(float var0) {
      int var1 = mc.world.getDifficulty().getId();
      return var0 * (var1 == 0 ? 0.0F : (var1 == 2 ? 1.0F : (var1 == 1 ? 0.5F : 1.5F)));
   }

   private static void resetRotation() {
      if (isSpoofingAngles) {
         yaw = (double)mc.player.rotationYaw;
         pitch = (double)mc.player.rotationPitch;
         isSpoofingAngles = false;
      }

   }

   public static void updateRotations() {
      newYaw = mc.player.rotationYaw;
      newPitch = mc.player.rotationPitch;
   }

   private List findCrystalBlocks() {
      NonNullList var1 = NonNullList.create();
      var1.addAll((Collection)this.getSphere(getPlayerPos(), (Float)this.placeRange.getValue(), ((Float)this.placeRange.getValue()).intValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
      return var1;
   }

   private boolean isEatingGap() {
      return mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && mc.player.isHandActive();
   }

   public void onDisable() {
      Xulu.EVENT_MANAGER.unregister(this);
      PvPInfo.place = false;
      PvPInfo.attack = false;
      this.render = null;
      this.renderEnt = null;
      resetRotation();
      this.isActive = false;
      this.ignoreList.clear();
      this.attemptMap.clear();
      this.retryMap.clear();
      if ((Boolean)this.chat.getValue()) {
         if ((Boolean)this.watermark.getValue()) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.dchatcolor.getValue())).append("AutoCrystal OFF")));
         } else {
            Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.dchatcolor.getValue())).append("AutoCrystal OFF")));
         }
      }

   }

   public void onRender() {
      if (this.render != null && mc.player != null) {
         if ((Boolean)this.renderBoolean.getValue()) {
            if ((Boolean)this.renderDamage.getValue()) {
               int var1 = (Integer)this.espR.getValue();
               int var2 = (Integer)this.espG.getValue();
               int var3 = (Integer)this.espB.getValue();
               if ((Boolean)this.rainbow.getValue()) {
                  var1 = RainbowUtils.r;
                  var2 = RainbowUtils.g;
                  var3 = RainbowUtils.b;
               }

               Plane var4 = VectorUtils.toScreen((double)this.render.getX() + 0.5D, (double)this.render.getY() + 0.5D, (double)this.render.getZ() + 0.5D);
               float var5 = calculateDamage((double)this.render.getX() + 0.5D, (double)(this.render.getY() + 1), (double)this.render.getZ() + 0.5D, this.renderEnt);
               String var6 = String.valueOf(MathUtil.round((double)var5, 1));
               fontRenderer.drawStringWithShadow(var6, (float)var4.getX() - (float)(fontRenderer.getStringWidth(var6) / 2), (float)var4.getY() - (float)(fontRenderer.FONT_HEIGHT / 2), (Boolean)this.damageWhite.getValue() ? -1 : (new Color(var1, var2, var3)).getRGB());
            }

         }
      }
   }

   @EventTarget
   public void onMotionPre(MotionEvent var1) {
      if ((Boolean)this.rotate.getValue() && ((String)this.rotateMode.getValue()).equalsIgnoreCase("New")) {
         updateRotations();
         this.doAutoCrystal();
      }

   }

   @EventTarget
   public void onSend(EventSendPacket var1) {
      Packet var2 = var1.getPacket();
      if (var2 instanceof CPacketPlayer && (Boolean)this.rotate.getValue() && isSpoofingAngles) {
         ((CPacketPlayer)var2).yaw = (float)yaw;
         ((CPacketPlayer)var2).pitch = (float)pitch;
      }

      if (var1.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
         CPacketPlayerTryUseItemOnBlock var3 = (CPacketPlayerTryUseItemOnBlock)var1.getPacket();
         if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            synchronized(this.placedCrystals) {
               this.placedCrystals.add(var3.getPos());
            }
         }
      }

   }

   public static BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
   }

   public static void restoreRotations() {
      mc.player.rotationYaw = newYaw;
      mc.player.rotationYawHead = newYaw;
      mc.player.rotationPitch = newPitch;
      resetRotation();
   }

   public static float calculateDamage(EntityEnderCrystal var0, Entity var1) {
      return calculateDamage(var0.posX, var0.posY, var0.posZ, var1);
   }

   private void lookAtPacket(double var1, double var3, double var5, EntityPlayer var7) {
      double[] var8 = calculateLookAt(var1, var3, var5, var7);
      setYawAndPitch((float)var8[0], (float)var8[1]);
   }

   public static double[] calculateLookAt(double var0, double var2, double var4, EntityPlayer var6) {
      double var7 = var6.posX - var0;
      double var9 = var6.posY - var2;
      double var11 = var6.posZ - var4;
      double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
      var7 /= var13;
      var9 /= var13;
      var11 /= var13;
      double var15 = Math.asin(var9);
      double var17 = Math.atan2(var11, var7);
      var15 = var15 * 180.0D / 3.141592653589793D;
      var17 = var17 * 180.0D / 3.141592653589793D;
      var17 += 90.0D;
      return new double[]{var17, var15};
   }

   @EventTarget
   public void onRecieve(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketSoundEffect && (Boolean)this.nodesync.getValue()) {
         SPacketSoundEffect var2 = (SPacketSoundEffect)var1.getPacket();
         if (var2.getCategory() == SoundCategory.BLOCKS && var2.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            Iterator var3 = Minecraft.getMinecraft().world.loadedEntityList.iterator();

            while(var3.hasNext()) {
               Entity var4 = (Entity)var3.next();
               if (var4 instanceof EntityEnderCrystal) {
                  if (var4.getDistance(var2.getX(), var2.getY(), var2.getZ()) <= 6.0D) {
                     var4.setDead();
                  }

                  this.placedCrystals.removeIf((var1x) -> {
                     return var1x.getDistance((int)var2.getX(), (int)var2.getY(), (int)var2.getZ()) <= 6.0D;
                  });
               }
            }
         }
      }

   }

   public static float getBlastReduction(EntityLivingBase var0, float var1, Explosion var2) {
      if (var0 instanceof EntityPlayer) {
         EntityPlayer var3 = (EntityPlayer)var0;
         DamageSource var4 = DamageSource.causeExplosionDamage(var2);
         var1 = CombatRules.getDamageAfterAbsorb(var1, (float)var3.getTotalArmorValue(), (float)var3.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
         int var5 = EnchantmentHelper.getEnchantmentModifierDamage(var3.getArmorInventoryList(), var4);
         float var6 = MathHelper.clamp((float)var5, 0.0F, 20.0F);
         var1 *= 1.0F - var6 / 25.0F;
         if (var0.isPotionActive(Potion.getPotionById(11))) {
            var1 -= var1 / 4.0F;
         }

         return var1;
      } else {
         var1 = CombatRules.getDamageAfterAbsorb(var1, (float)var0.getTotalArmorValue(), (float)var0.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
         return var1;
      }
   }

   private boolean isArmorLow(EntityPlayer var1) {
      if (!(Boolean)this.armor.getValue()) {
         return false;
      } else {
         Iterator var2 = var1.getArmorInventoryList().iterator();

         int var6;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            ItemStack var3 = (ItemStack)var2.next();
            float var4 = ((float)var3.getMaxDamage() - (float)var3.getItemDamage()) / (float)var3.getMaxDamage();
            float var5 = 1.0F - var4;
            var6 = 100 - (int)(var5 * 100.0F);
         } while(var6 > (Integer)this.armorDmg.getValue());

         return true;
      }
   }

   public List getSphere(BlockPos var1, float var2, int var3, boolean var4, boolean var5, int var6) {
      ArrayList var7 = new ArrayList();
      int var8 = var1.getX();
      int var9 = var1.getY();
      int var10 = var1.getZ();

      for(int var11 = var8 - (int)var2; (float)var11 <= (float)var8 + var2; ++var11) {
         for(int var12 = var10 - (int)var2; (float)var12 <= (float)var10 + var2; ++var12) {
            for(int var13 = var5 ? var9 - (int)var2 : var9; (float)var13 < (var5 ? (float)var9 + var2 : (float)(var9 + var3)); ++var13) {
               double var14 = (double)((var8 - var11) * (var8 - var11) + (var10 - var12) * (var10 - var12) + (var5 ? (var9 - var13) * (var9 - var13) : 0));
               if (var14 < (double)(var2 * var2) && (!var4 || !(var14 < (double)((var2 - 1.0F) * (var2 - 1.0F))))) {
                  BlockPos var16 = new BlockPos(var11, var13 + var6, var12);
                  var7.add(var16);
               }
            }
         }
      }

      return var7;
   }

   private boolean canPlaceCrystal(BlockPos var1) {
      BlockPos var2 = var1.add(0, 1, 0);
      BlockPos var3 = var1.add(0, 2, 0);
      return (mc.world.getBlockState(var1).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(var1).getBlock() == Blocks.OBSIDIAN) && mc.world.getBlockState(var2).getBlock() == Blocks.AIR && ((Boolean)this.oneHole.getValue() || mc.world.getBlockState(var3).getBlock() == Blocks.AIR) && this.isTrulyEmpty(mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(var2))) && this.isTrulyEmpty(mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(var3)));
   }

   private boolean checkCrystal(Entity var1) {
      if (var1 == null) {
         return false;
      } else {
         if ((Boolean)this.noSuicide.getValue()) {
            double var2 = (double)calculateDamage(var1.posX, var1.posY, var1.posZ, mc.player);
            if (var2 - 0.5D > (double)mc.player.getHealth()) {
               return false;
            }
         }

         String var10 = (String)this.explodeMode.getValue();
         byte var3 = -1;
         switch(var10.hashCode()) {
         case 65921:
            if (var10.equals("All")) {
               var3 = 1;
            }
            break;
         case 350978074:
            if (var10.equals("OnlyOwn")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            if (this.render != null && this.render.getDistance((int)var1.posX, (int)var1.posY, (int)var1.posZ) <= 3.0D) {
               return true;
            }

            if (this.placedCrystals.isEmpty()) {
               break;
            }

            synchronized(this.placedCrystals) {
               try {
                  Iterator var5 = this.placedCrystals.iterator();

                  BlockPos var6;
                  do {
                     if (!var5.hasNext()) {
                        return false;
                     }

                     var6 = (BlockPos)var5.next();
                  } while(!(var6.getDistance((int)var1.posX, (int)var1.posY, (int)var1.posZ) <= 3.0D));

                  boolean var10000 = true;
                  return var10000;
               } catch (Exception var8) {
                  var8.printStackTrace();
                  break;
               }
            }
         case 1:
            return true;
         }

         return false;
      }
   }

   public void onWorldRender(RenderEvent var1) {
      if (this.render != null && mc.player != null) {
         if ((Boolean)this.renderBoolean.getValue()) {
            if (this.renderOld != null && this.renderOld != this.render) {
               this.fadeList.put(this.renderOld, this.espA.getValue());
            }

            int var2 = (Integer)this.espR.getValue();
            int var3 = (Integer)this.espG.getValue();
            int var4 = (Integer)this.espB.getValue();
            if ((Boolean)this.rainbow.getValue()) {
               var2 = RainbowUtils.r;
               var3 = RainbowUtils.g;
               var4 = RainbowUtils.b;
            }

            if (((String)this.rendermode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               XuluTessellator.drawBox(this.render, var2, var3, var4, (Integer)this.espA.getValue(), 63);
               XuluTessellator.release();
            } else {
               IBlockState var5;
               Vec3d var6;
               if (((String)this.rendermode.getValue()).equalsIgnoreCase("Outline")) {
                  var5 = mc.world.getBlockState(this.render);
                  var6 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.drawBoundingBox(var5.getSelectedBoundingBox(mc.world, this.render).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), 1.5F, var2, var3, var4, (Integer)this.espA.getValue());
               } else if (((String)this.rendermode.getValue()).equalsIgnoreCase("Full")) {
                  var5 = mc.world.getBlockState(this.render);
                  var6 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.drawFullBox(var5.getSelectedBoundingBox(mc.world, this.render).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), this.render, 1.5F, var2, var3, var4, (Integer)this.espA.getValue(), (Integer)this.espF.getValue());
               }
            }

            if ((Boolean)this.smoothEsp.getValue()) {
               Iterator var9 = this.fadeList.keySet().iterator();

               while(var9.hasNext()) {
                  BlockPos var10 = (BlockPos)var9.next();
                  if ((Integer)this.fadeList.get(var10) < 0) {
                     this.fadeList.remove(var10);
                  } else if (((String)this.rendermode.getValue()).equalsIgnoreCase("Solid")) {
                     XuluTessellator.prepare(7);
                     XuluTessellator.drawBox(var10, var2, var3, var4, (Integer)this.fadeList.get(var10), 63);
                     XuluTessellator.release();
                  } else {
                     IBlockState var7;
                     Vec3d var8;
                     if (((String)this.rendermode.getValue()).equalsIgnoreCase("Outline")) {
                        var7 = mc.world.getBlockState(var10);
                        var8 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                        XuluTessellator.drawBoundingBox(var7.getSelectedBoundingBox(mc.world, var10).grow(0.0020000000949949026D).offset(-var8.x, -var8.y, -var8.z), 1.5F, var2, var3, var4, (Integer)this.fadeList.get(var10));
                     } else if (((String)this.rendermode.getValue()).equalsIgnoreCase("Full")) {
                        var7 = mc.world.getBlockState(var10);
                        var8 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                        XuluTessellator.drawFullBox(var7.getSelectedBoundingBox(mc.world, var10).grow(0.0020000000949949026D).offset(-var8.x, -var8.y, -var8.z), this.render, 1.5F, var2, var3, var4, (Integer)this.fadeList.get(var10), (Integer)this.espF.getValue());
                     }
                  }
               }
            }

            this.renderOld = this.render;
         }
      }
   }

   public AutoCrystal() {
      super("AutoCrystal", "Xulu AutoCrystal", 0, Category.COMBAT, true);
      this.echatcolor = this.register(new Value("Enable Color", this, "White", ColorTextUtils.colors));
      this.dchatcolor = this.register(new Value("Disable Color", this, "White", ColorTextUtils.colors));
      this.renderDamage = this.register(new Value("Render Damage", this, false));
      this.damageWhite = this.register(new Value("Damage Color White", this, false));
      this.renderBoolean = this.register(new Value("Render", this, true));
      this.rendermode = this.register(new Value("RenderMode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Outline", "Full"))));
      this.rainbow = this.register(new Value("Esp Rainbow", this, false));
      this.espR = this.register(new Value("Esp Red", this, 200, 0, 255));
      this.espG = this.register(new Value("Esp Green", this, 50, 0, 255));
      this.espB = this.register(new Value("Esp Blue", this, 200, 0, 255));
      this.espA = this.register(new Value("Esp Alpha", this, 50, 0, 255));
      this.espF = this.register(new Value("Full Alpha", this, 80, 0, 255));
      this.maxSelfDmg = this.register(new Value("Max Self Dmg", this, 10, 0, 36));
      this.noGappleSwitch = this.register(new Value("No Gap Switch", this, false));
      this.smoothEsp = this.register(new Value("Smooth ESP", this, false));
      this.smoothSpeed = this.register(new Value("Smooth Speed", this, 5, 1, 20));
      this.isActive = false;
      this.fadeList = new ConcurrentHashMap();
      this.attemptMap = new WeakHashMap();
      this.retryMap = new WeakHashMap();
      this.ignoreList = new ArrayList();
   }

   public String getHudInfo() {
      if (this.renderEnt != null) {
         return mc.player.getDistance(this.renderEnt) <= (Float)this.range.getValue() ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append(this.renderEnt.getName())) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append(this.renderEnt.getName()));
      } else {
         return null;
      }
   }

   public static float calculateDamage(double var0, double var2, double var4, Entity var6) {
      float var7 = 12.0F;
      double var8 = var6.getDistance(var0, var2, var4) / (double)var7;
      Vec3d var10 = new Vec3d(var0, var2, var4);
      double var11 = (double)var6.world.getBlockDensity(var10, var6.getEntityBoundingBox());
      double var13 = (1.0D - var8) * var11;
      float var15 = (float)((int)((var13 * var13 + var13) / 2.0D * 7.0D * (double)var7 + 1.0D));
      double var16 = 1.0D;
      if (var6 instanceof EntityLivingBase) {
         var16 = (double)getBlastReduction((EntityLivingBase)var6, getDamageMultiplied(var15), new Explosion(mc.world, (Entity)null, var0, var2, var4, 6.0F, false, true));
      }

      return (float)var16;
   }

   @EventTarget
   public void onMotionPost(MotionEventPost var1) {
      if ((Boolean)this.rotate.getValue() && ((String)this.rotateMode.getValue()).equalsIgnoreCase("New")) {
         restoreRotations();
      }

   }

   private static void setYawAndPitch(float var0, float var1) {
      Random var2 = new Random(2L);
      yaw = (double)(var0 + (isRand ? var2.nextFloat() / 100.0F : 0.0F));
      pitch = (double)(var1 + (isRand ? var2.nextFloat() / 100.0F : 0.0F));
      isSpoofingAngles = true;
   }

   private boolean isTrulyEmpty(List var1) {
      if (var1.isEmpty()) {
         return true;
      } else {
         boolean var2 = true;
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if (!this.ignoreList.contains(var4)) {
               var2 = false;
               break;
            }
         }

         return var2;
      }
   }

   public void onEnable() {
      Xulu.EVENT_MANAGER.register(this);
      this.isActive = false;
      if ((Boolean)this.chat.getValue() && mc.player != null) {
         if ((Boolean)this.watermark.getValue()) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.echatcolor.getValue())).append("AutoCrystal ON")));
         } else {
            Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.echatcolor.getValue())).append("AutoCrystal ON")));
         }
      }

   }

   private boolean shouldPause() {
      if (Xulu.MODULE_MANAGER.getModule(Surround.class).isToggled() && Surround.isExposed() && ((Surround)Xulu.MODULE_MANAGER.getModuleT(Surround.class)).findObiInHotbar() != -1) {
         return true;
      } else if (Xulu.MODULE_MANAGER.getModule(AutoTrap.class).isToggled()) {
         return true;
      } else if (Xulu.MODULE_MANAGER.getModule(HoleFill.class).isToggled()) {
         return true;
      } else {
         return Xulu.MODULE_MANAGER.getModule(HoleBlocker.class).isToggled() && HoleBlocker.isExposed() && ((Surround)Xulu.MODULE_MANAGER.getModuleT(Surround.class)).findObiInHotbar() != -1;
      }
   }

   public void onUpdate() {
      if (((String)this.rotateMode.getValue()).equalsIgnoreCase("Old")) {
         this.doAutoCrystal();
      }

   }
}
