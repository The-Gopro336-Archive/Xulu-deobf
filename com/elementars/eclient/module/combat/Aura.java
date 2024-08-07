package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.LagCompensator;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public class Aura extends Module {
   // $FF: synthetic field
   boolean isSpoofingAngles = false;
   // $FF: synthetic field
   final Value sharpness = this.register(new Value("32k Switch", this, false));
   // $FF: synthetic field
   double pitch;
   // $FF: synthetic field
   final Value wait = this.register(new Value("Wait", this, true));
   // $FF: synthetic field
   final Value walls = this.register(new Value("Walls", this, false));
   // $FF: synthetic field
   final Value animals = this.register(new Value("Animals", this, false));
   // $FF: synthetic field
   double yaw;
   // $FF: synthetic field
   final Value range = this.register(new Value("Range", this, 5.5D, 1.0D, 10.0D));
   // $FF: synthetic field
   final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   final Value players = this.register(new Value("Players", this, true));
   // $FF: synthetic field
   final Value mobs = this.register(new Value("Mobs", this, false));

   @EventTarget
   public void onSend(EventSendPacket var1) {
      Packet var2 = var1.getPacket();
      if (var2 instanceof CPacketPlayer && (Boolean)this.rotate.getValue() && this.isSpoofingAngles) {
         ((CPacketPlayer)var2).yaw = (float)this.yaw;
         ((CPacketPlayer)var2).pitch = (float)this.pitch;
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

   private void lookAtPacket(double var1, double var3, double var5, EntityPlayer var7) {
      double[] var8 = AutoCrystal.calculateLookAt(var1, var3, var5, var7);
      this.setYawAndPitch((float)var8[0], (float)var8[1]);
   }

   public void onDisable() {
      this.resetRotation();
   }

   private boolean canEntityFeetBeSeen(Entity var1) {
      return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posX + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(var1.posX, var1.posY, var1.posZ), false, true, false) == null;
   }

   private void setYawAndPitch(float var1, float var2) {
      this.yaw = (double)var1;
      this.pitch = (double)var2;
      this.isSpoofingAngles = true;
   }

   public void onUpdate() {
      if (!mc.player.isDead) {
         if (this.shouldPause()) {
            this.resetRotation();
         } else {
            boolean var1 = mc.player.getHeldItemOffhand().getItem().equals(Items.SHIELD) && mc.player.getActiveHand() == EnumHand.OFF_HAND;
            boolean var2 = mc.player.getHeldItemOffhand().getItem().equals(Items.GOLDEN_APPLE) && mc.player.getActiveHand() == EnumHand.OFF_HAND;
            if (!mc.player.isHandActive() || var1 || var2) {
               if ((Boolean)this.wait.getValue()) {
                  if (mc.player.getCooledAttackStrength(this.getLagComp()) < 1.0F) {
                     return;
                  }

                  if (mc.player.ticksExisted % 2 != 0) {
                     return;
                  }
               }

               Iterator var3 = Minecraft.getMinecraft().world.loadedEntityList.iterator();

               Entity var4;
               while(true) {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    if (!var3.hasNext()) {
                                       this.resetRotation();
                                       return;
                                    }

                                    var4 = (Entity)var3.next();
                                 } while(!EntityUtil.isLiving(var4));
                              } while(var4 == mc.player);
                           } while((double)mc.player.getDistance(var4) > (Double)this.range.getValue());
                        } while(((EntityLivingBase)var4).getHealth() <= 0.0F);
                     } while(((EntityLivingBase)var4).hurtTime != 0 && (Boolean)this.wait.getValue());
                  } while(!(Boolean)this.walls.getValue() && !mc.player.canEntityBeSeen(var4) && !this.canEntityFeetBeSeen(var4));

                  if ((Boolean)this.players.getValue() && var4 instanceof EntityPlayer && !Friends.isFriend(var4.getName())) {
                     this.attack(var4);
                     return;
                  }

                  if (EntityUtil.isPassive(var4)) {
                     if ((Boolean)this.animals.getValue()) {
                        break;
                     }
                  } else if (EntityUtil.isMobAggressive(var4) && (Boolean)this.mobs.getValue()) {
                     break;
                  }
               }

               this.attack(var4);
            }
         }
      }
   }

   private void resetRotation() {
      if (this.isSpoofingAngles) {
         this.yaw = (double)mc.player.rotationYaw;
         this.pitch = (double)mc.player.rotationPitch;
         this.isSpoofingAngles = false;
      }

   }

   private float getLagComp() {
      return (Boolean)this.wait.getValue() ? -(20.0F - LagCompensator.INSTANCE.getTickRate()) : 0.0F;
   }

   private void attack(Entity var1) {
      if ((Boolean)this.sharpness.getValue() && !this.checkSharpness(mc.player.getHeldItemMainhand())) {
         int var2 = -1;

         for(int var3 = 0; var3 < 9; ++var3) {
            ItemStack var4 = mc.player.inventory.getStackInSlot(var3);
            if (var4 != ItemStack.EMPTY && this.checkSharpness(var4)) {
               var2 = var3;
               break;
            }
         }

         if (var2 != -1) {
            mc.player.inventory.currentItem = var2;
         }
      }

      if ((Boolean)this.rotate.getValue()) {
         this.lookAtPacket(var1.posX, var1.posY, var1.posZ, mc.player);
      }

      mc.playerController.attackEntity(mc.player, var1);
      mc.player.swingArm(EnumHand.MAIN_HAND);
   }

   public Aura() {
      super("Aura", "Hits people", 0, Category.COMBAT, true);
   }

   private boolean checkSharpness(ItemStack var1) {
      if (var1.getTagCompound() == null) {
         return false;
      } else {
         NBTTagList var2 = (NBTTagList)var1.getTagCompound().getTag("ench");
         if (var2 == null) {
            return false;
         } else {
            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               NBTTagCompound var4 = var2.getCompoundTagAt(var3);
               if (var4.getInteger("id") == 16) {
                  int var5 = var4.getInteger("lvl");
                  if (var5 >= 16) {
                     return true;
                  }
                  break;
               }
            }

            return false;
         }
      }
   }
}
