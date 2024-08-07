package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.event.events.MotionEvent;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.Timer;
import dev.xulu.settings.Value;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.math.MathHelper;

public class ElytraFly extends Module {
   // $FF: synthetic field
   public Value allowUp;
   // $FF: synthetic field
   public Value hSpeed;
   // $FF: synthetic field
   private final Timer timer;
   // $FF: synthetic field
   public Value devMode;
   // $FF: synthetic field
   public Value mode;
   // $FF: synthetic field
   public Value speed;
   // $FF: synthetic field
   private Double flyHeight;
   // $FF: synthetic field
   public Value instaMode;
   // $FF: synthetic field
   public Value noKick;
   // $FF: synthetic field
   public Value glide;
   // $FF: synthetic field
   public Value disableInLiquid;
   // $FF: synthetic field
   private static ElytraFly INSTANCE = new ElytraFly();
   // $FF: synthetic field
   public Value instaFly;
   // $FF: synthetic field
   public Value autoStart;
   // $FF: synthetic field
   public Value vSpeed;
   // $FF: synthetic field
   private Double posX;
   // $FF: synthetic field
   public Value infiniteDura;
   // $FF: synthetic field
   private Double posZ;
   // $FF: synthetic field
   private final Timer instaTimer;
   // $FF: synthetic field
   public Value keepMotion;
   // $FF: synthetic field
   public Value tooBeeSpeed;

   public void onEnable() {
      if (this.mode.getValue() == ElytraFly.Mode.BETTER && !(Boolean)this.autoStart.getValue() && (Integer)this.devMode.getValue() == 1) {
         mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
      }

      this.flyHeight = null;
      this.posX = null;
      this.posZ = null;
   }

   private void runNoKick(EntityPlayer var1) {
      if ((Boolean)this.noKick.getValue() && !var1.isElytraFlying() && var1.ticksExisted % 4 == 0) {
         var1.motionY = -0.03999999910593033D;
      }

   }

   private void freezePlayer(EntityPlayer var1) {
      var1.motionX = 0.0D;
      var1.motionY = 0.0D;
      var1.motionZ = 0.0D;
   }

   private void setInstance() {
      INSTANCE = this;
   }

   @EventTarget
   public void onSendPacket(EventSendPacket var1) {
      CPacketPlayer var2;
      if (var1.getPacket() instanceof CPacketPlayer && this.mode.getValue() == ElytraFly.Mode.TOOBEE) {
         var2 = (CPacketPlayer)var1.getPacket();
         if (mc.player.isElytraFlying() && !mc.player.movementInput.jump && var2.pitch < 1.0F) {
            var2.pitch = 1.0F;
         }
      }

      if (var1.getPacket() instanceof CPacketPlayer && this.mode.getValue() == ElytraFly.Mode.BETTER) {
         var2 = (CPacketPlayer)var1.getPacket();
         if (mc.player.isElytraFlying() && !mc.player.movementInput.jump && var2.pitch < 1.0F) {
            var2.pitch = 1.0F;
         }
      }

   }

   public String getHudInfo() {
      return ((ElytraFly.Mode)this.mode.getValue()).name();
   }

   public ElytraFly() {
      super("ElytraFly", "Makes Elytra Flight better.", 0, Category.MOVEMENT, true);
      this.mode = this.register(new Value("Mode", this, ElytraFly.Mode.FLY, ElytraFly.Mode.values()));
      this.devMode = this.register(new Value("Type", this, 2, 1, 3)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BYPASS || this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.speed = this.register(new Value("Speed", this, 1.0F, 0.0F, 10.0F)).visibleWhen((var1) -> {
         return this.mode.getValue() != ElytraFly.Mode.FLY && this.mode.getValue() != ElytraFly.Mode.BOOST && this.mode.getValue() != ElytraFly.Mode.BETTER && this.mode.getValue() != ElytraFly.Mode.OHARE;
      });
      this.vSpeed = this.register(new Value("VSpeed", this, 0.3F, 0.0F, 10.0F)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER || this.mode.getValue() == ElytraFly.Mode.OHARE;
      });
      this.hSpeed = this.register(new Value("HSpeed", this, 1.0F, 0.0F, 10.0F)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER || this.mode.getValue() == ElytraFly.Mode.OHARE;
      });
      this.glide = this.register(new Value("Glide", this, 1.0E-4F, 0.0F, 0.2F)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.tooBeeSpeed = this.register(new Value("TooBeeSpeed", this, 1.8000001F, 1.0F, 2.0F)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.TOOBEE;
      });
      this.autoStart = this.register(new Value("AutoStart", this, true));
      this.disableInLiquid = this.register(new Value("NoLiquid", this, true));
      this.infiniteDura = this.register(new Value("InfiniteDura", this, false));
      this.noKick = this.register(new Value("NoKick", this, false)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.PACKET || this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.keepMotion = this.register(new Value("KeepMotion", this, true)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.instaFly = this.register(new Value("InstaFly", this, true)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.instaMode = this.register(new Value("Takeoff Mode", this, "Static", new String[]{"None", "Static"})).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.allowUp = this.register(new Value("AllowUp", this, true)).visibleWhen((var1) -> {
         return this.mode.getValue() == ElytraFly.Mode.BETTER;
      });
      this.timer = new Timer();
      this.instaTimer = new Timer();
      this.setInstance();
   }

   public void onDisable() {
      if (mc.player != null && mc.world != null) {
         if (this.mode.getValue() == ElytraFly.Mode.BETTER && (Integer)this.devMode.getValue() == 2 && (Boolean)this.keepMotion.getValue()) {
            mc.player.motionX = this.posX;
            mc.player.motionZ = this.posZ;
         }

         mc.timer.tickLength = 50.0F;
         if (!mc.player.capabilities.isCreativeMode) {
            mc.player.capabilities.isFlying = false;
         }
      }
   }

   private void setMoveSpeed(PlayerMoveEvent var1, double var2) {
      double var4 = (double)mc.player.movementInput.moveForward;
      double var6 = (double)mc.player.movementInput.moveStrafe;
      float var8 = mc.player.rotationYaw;
      if (var4 == 0.0D && var6 == 0.0D) {
         var1.setX(0.0D);
         var1.setZ(0.0D);
         mc.player.motionX = 0.0D;
         mc.player.motionZ = 0.0D;
      } else {
         if (var4 != 0.0D) {
            if (var6 > 0.0D) {
               var8 += (float)(var4 > 0.0D ? -45 : 45);
            } else if (var6 < 0.0D) {
               var8 += (float)(var4 > 0.0D ? 45 : -45);
            }

            var6 = 0.0D;
            if (var4 > 0.0D) {
               var4 = 1.0D;
            } else if (var4 < 0.0D) {
               var4 = -1.0D;
            }
         }

         double var9 = var4 * var2 * -Math.sin(Math.toRadians((double)var8)) + var6 * var2 * Math.cos(Math.toRadians((double)var8));
         double var11 = var4 * var2 * Math.cos(Math.toRadians((double)var8)) - var6 * var2 * -Math.sin(Math.toRadians((double)var8));
         var1.setX(var9);
         var1.setZ(var11);
         mc.player.motionX = var9;
         mc.player.motionZ = var11;
      }

   }

   public static ElytraFly getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ElytraFly();
      }

      return INSTANCE;
   }

   @EventTarget
   public void onUpdateWalkingPlayer(MotionEvent var1) {
      if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
         double var7;
         if (!mc.player.isElytraFlying()) {
            if (!mc.player.onGround && (Boolean)this.instaFly.getValue()) {
               var7 = mc.player.posY;
               if ((Boolean)this.noKick.getValue()) {
                  var7 -= (double)(Float)this.glide.getValue();
               }

               if ("Static".equals(this.instaMode.getValue())) {
                  mc.player.setPosition(mc.player.posX, var7, mc.player.posZ);
                  mc.player.setVelocity(0.0D, 0.0D, 0.0D);
               }

               if (!this.instaTimer.hasReached(1000L)) {
                  return;
               }

               this.instaTimer.reset();
               mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
            }

         } else {
            switch(var1.getEventState()) {
            case PRE:
               if ((Boolean)this.disableInLiquid.getValue() && (mc.player.isInWater() || mc.player.isInLava())) {
                  if (mc.player.isElytraFlying()) {
                     mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
                  }

                  return;
               }

               if ((Boolean)this.autoStart.getValue() && mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isElytraFlying() && mc.player.motionY < 0.0D && this.timer.hasReached(250L)) {
                  mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
                  this.timer.reset();
               }

               if (this.mode.getValue() == ElytraFly.Mode.BETTER) {
                  double[] var2 = MathUtil.directionSpeed((Integer)this.devMode.getValue() == 1 ? (double)(Float)this.speed.getValue() : (double)(Float)this.hSpeed.getValue());
                  switch((Integer)this.devMode.getValue()) {
                  case 1:
                     mc.player.setVelocity(0.0D, 0.0D, 0.0D);
                     mc.player.jumpMovementFactor = (Float)this.speed.getValue();
                     EntityPlayerSP var3;
                     if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        var3 = mc.player;
                        var3.motionY += (double)(Float)this.speed.getValue();
                     }

                     if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        var3 = mc.player;
                        var3.motionY -= (double)(Float)this.speed.getValue();
                     }

                     if (mc.player.movementInput.moveStrafe == 0.0F && mc.player.movementInput.moveForward == 0.0F) {
                        mc.player.motionX = 0.0D;
                        mc.player.motionZ = 0.0D;
                        break;
                     }

                     mc.player.motionX = var2[0];
                     mc.player.motionZ = var2[1];
                     break;
                  case 2:
                     if (!mc.player.isElytraFlying()) {
                        this.flyHeight = null;
                        return;
                     }

                     if (this.flyHeight == null) {
                        this.flyHeight = mc.player.posY;
                     }

                     if ((Boolean)this.noKick.getValue()) {
                        this.flyHeight = this.flyHeight - (double)(Float)this.glide.getValue();
                     }

                     this.posX = 0.0D;
                     this.posZ = 0.0D;
                     if (mc.player.movementInput.moveStrafe != 0.0F || mc.player.movementInput.moveForward != 0.0F) {
                        this.posX = var2[0];
                        this.posZ = var2[1];
                     }

                     if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.flyHeight = mc.player.posY - (double)(Float)this.vSpeed.getValue();
                     }

                     mc.player.setPosition(mc.player.posX + this.posX, this.flyHeight, mc.player.posZ + this.posZ);
                     mc.player.setVelocity(0.0D, 0.0D, 0.0D);
                     break;
                  case 3:
                     if (!mc.player.isElytraFlying()) {
                        this.flyHeight = null;
                        this.posX = null;
                        this.posZ = null;
                        return;
                     }

                     if (this.flyHeight == null || this.posX == null || this.posX == 0.0D || this.posZ == null || this.posZ == 0.0D) {
                        this.flyHeight = mc.player.posY;
                        this.posX = mc.player.posX;
                        this.posZ = mc.player.posZ;
                     }

                     if ((Boolean)this.noKick.getValue()) {
                        this.flyHeight = this.flyHeight - (double)(Float)this.glide.getValue();
                     }

                     if (mc.player.movementInput.moveStrafe != 0.0F || mc.player.movementInput.moveForward != 0.0F) {
                        this.posX = this.posX + var2[0];
                        this.posZ = this.posZ + var2[1];
                     }

                     if ((Boolean)this.allowUp.getValue() && mc.gameSettings.keyBindJump.isKeyDown()) {
                        this.flyHeight = mc.player.posY + (double)((Float)this.vSpeed.getValue() / 10.0F);
                     }

                     if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.flyHeight = mc.player.posY - (double)((Float)this.vSpeed.getValue() / 10.0F);
                     }

                     mc.player.setPosition(this.posX, this.flyHeight, this.posZ);
                     mc.player.setVelocity(0.0D, 0.0D, 0.0D);
                  }
               }

               var7 = Math.toRadians((double)mc.player.rotationYaw);
               if (mc.player.isElytraFlying()) {
                  double[] var4;
                  switch((ElytraFly.Mode)this.mode.getValue()) {
                  case VANILLA:
                     float var8 = (Float)this.speed.getValue() * 0.05F;
                     EntityPlayerSP var5;
                     if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        var5 = mc.player;
                        var5.motionY += (double)var8;
                     }

                     if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        var5 = mc.player;
                        var5.motionY -= (double)var8;
                     }

                     EntityPlayerSP var6;
                     if (mc.gameSettings.keyBindForward.isKeyDown()) {
                        var5 = mc.player;
                        var5.motionX -= Math.sin(var7) * (double)var8;
                        var6 = mc.player;
                        var6.motionZ += Math.cos(var7) * (double)var8;
                     }

                     if (mc.gameSettings.keyBindBack.isKeyDown()) {
                        var5 = mc.player;
                        var5.motionX += Math.sin(var7) * (double)var8;
                        var6 = mc.player;
                        var6.motionZ -= Math.cos(var7) * (double)var8;
                     }
                     break;
                  case PACKET:
                     this.freezePlayer(mc.player);
                     this.runNoKick(mc.player);
                     var4 = MathUtil.directionSpeed((double)(Float)this.speed.getValue());
                     if (mc.player.movementInput.jump) {
                        mc.player.motionY = (double)(Float)this.speed.getValue();
                     }

                     if (mc.player.movementInput.sneak) {
                        mc.player.motionY = (double)(-(Float)this.speed.getValue());
                     }

                     if (mc.player.movementInput.moveStrafe != 0.0F || mc.player.movementInput.moveForward != 0.0F) {
                        mc.player.motionX = var4[0];
                        mc.player.motionZ = var4[1];
                     }

                     mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
                     mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
                     break;
                  case BYPASS:
                     if ((Integer)this.devMode.getValue() == 3) {
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                           mc.player.motionY = 0.019999999552965164D;
                        }

                        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                           mc.player.motionY = -0.20000000298023224D;
                        }

                        if (mc.player.ticksExisted % 8 == 0 && mc.player.posY <= 240.0D) {
                           mc.player.motionY = 0.019999999552965164D;
                        }

                        mc.player.capabilities.isFlying = true;
                        mc.player.capabilities.setFlySpeed(0.025F);
                        var4 = MathUtil.directionSpeed(0.5199999809265137D);
                        if (mc.player.movementInput.moveStrafe == 0.0F && mc.player.movementInput.moveForward == 0.0F) {
                           mc.player.motionX = 0.0D;
                           mc.player.motionZ = 0.0D;
                        } else {
                           mc.player.motionX = var4[0];
                           mc.player.motionZ = var4[1];
                        }
                     }
                  }
               }

               if ((Boolean)this.infiniteDura.getValue()) {
                  mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
               }
               break;
            case POST:
               if ((Boolean)this.infiniteDura.getValue()) {
                  mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
               }
            }

         }
      }
   }

   private double[] forwardStrafeYaw(double var1, double var3, double var5) {
      double[] var7 = new double[]{var1, var3, var5};
      if ((var1 != 0.0D || var3 != 0.0D) && var1 != 0.0D) {
         boolean var9;
         if (var3 > 0.0D) {
            var9 = true;
            var7[2] += (double)(var1 > 0.0D ? -45 : 45);
         } else if (var3 < 0.0D) {
            var9 = true;
            var7[2] += (double)(var1 > 0.0D ? 45 : -45);
         }

         var7[1] = 0.0D;
         if (var1 > 0.0D) {
            var7[0] = 1.0D;
         } else if (var1 < 0.0D) {
            var7[0] = -1.0D;
         }
      }

      return var7;
   }

   public void onUpdate() {
      if (this.mode.getValue() == ElytraFly.Mode.BYPASS && (Integer)this.devMode.getValue() == 1 && mc.player.isElytraFlying()) {
         mc.player.motionX = 0.0D;
         mc.player.motionY = -1.0E-4D;
         mc.player.motionZ = 0.0D;
         double var1 = (double)mc.player.movementInput.moveForward;
         double var3 = (double)mc.player.movementInput.moveStrafe;
         double[] var5 = this.forwardStrafeYaw(var1, var3, (double)mc.player.rotationYaw);
         double var6 = var5[0];
         double var8 = var5[1];
         double var10 = var5[2];
         if (var1 != 0.0D || var3 != 0.0D) {
            mc.player.motionX = var6 * (double)(Float)this.speed.getValue() * Math.cos(Math.toRadians(var10 + 90.0D)) + var8 * (double)(Float)this.speed.getValue() * Math.sin(Math.toRadians(var10 + 90.0D));
            mc.player.motionZ = var6 * (double)(Float)this.speed.getValue() * Math.sin(Math.toRadians(var10 + 90.0D)) - var8 * (double)(Float)this.speed.getValue() * Math.cos(Math.toRadians(var10 + 90.0D));
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -1.0D;
         }
      }

   }

   @EventTarget
   public void onLivingUpdate(LocalPlayerUpdateEvent var1) {
      if (mc.player.isElytraFlying()) {
         switch((ElytraFly.Mode)this.mode.getValue()) {
         case BOOST:
            if (mc.player.isInWater()) {
               mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
               return;
            }

            EntityPlayerSP var2;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
               var2 = mc.player;
               var2.motionY += 0.08D;
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
               var2 = mc.player;
               var2.motionY -= 0.04D;
            }

            EntityPlayerSP var3;
            EntityPlayerSP var4;
            float var6;
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
               var6 = (float)Math.toRadians((double)mc.player.rotationYaw);
               var3 = mc.player;
               var3.motionX -= (double)(MathHelper.sin(var6) * 0.05F);
               var4 = mc.player;
               var4.motionZ += (double)(MathHelper.cos(var6) * 0.05F);
            } else if (mc.gameSettings.keyBindBack.isKeyDown()) {
               var6 = (float)Math.toRadians((double)mc.player.rotationYaw);
               var3 = mc.player;
               var3.motionX += (double)(MathHelper.sin(var6) * 0.05F);
               var4 = mc.player;
               var4.motionZ -= (double)(MathHelper.cos(var6) * 0.05F);
            }
            break;
         case FLY:
            mc.player.capabilities.isFlying = true;
         }

      }
   }

   @EventTarget
   public void onMove(PlayerMoveEvent var1) {
      if (this.mode.getValue() == ElytraFly.Mode.OHARE) {
         ItemStack var2 = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
         if (var2.getItem() == Items.ELYTRA && ItemElytra.isUsable(var2) && mc.player.isElytraFlying()) {
            var1.setY(mc.gameSettings.keyBindJump.isKeyDown() ? (double)(Float)this.vSpeed.getValue() : (mc.gameSettings.keyBindSneak.isKeyDown() ? (double)(-(Float)this.vSpeed.getValue()) : 0.0D));
            mc.player.addVelocity(0.0D, mc.gameSettings.keyBindJump.isKeyDown() ? (double)(Float)this.vSpeed.getValue() : (mc.gameSettings.keyBindSneak.isKeyDown() ? (double)(-(Float)this.vSpeed.getValue()) : 0.0D), 0.0D);
            mc.player.rotateElytraX = 0.0F;
            mc.player.rotateElytraY = 0.0F;
            mc.player.rotateElytraZ = 0.0F;
            mc.player.moveVertical = mc.gameSettings.keyBindJump.isKeyDown() ? (Float)this.vSpeed.getValue() : (mc.gameSettings.keyBindSneak.isKeyDown() ? -(Float)this.vSpeed.getValue() : 0.0F);
            double var3 = (double)mc.player.movementInput.moveForward;
            double var5 = (double)mc.player.movementInput.moveStrafe;
            float var7 = mc.player.rotationYaw;
            if (var3 == 0.0D && var5 == 0.0D) {
               var1.setX(0.0D);
               var1.setZ(0.0D);
            } else {
               if (var3 != 0.0D) {
                  if (var5 > 0.0D) {
                     var7 += (float)(var3 > 0.0D ? -45 : 45);
                  } else if (var5 < 0.0D) {
                     var7 += (float)(var3 > 0.0D ? 45 : -45);
                  }

                  var5 = 0.0D;
                  if (var3 > 0.0D) {
                     var3 = 1.0D;
                  } else if (var3 < 0.0D) {
                     var3 = -1.0D;
                  }
               }

               double var8 = Math.cos(Math.toRadians((double)(var7 + 90.0F)));
               double var10 = Math.sin(Math.toRadians((double)(var7 + 90.0F)));
               var1.setX(var3 * (double)(Float)this.hSpeed.getValue() * var8 + var5 * (double)(Float)this.hSpeed.getValue() * var10);
               var1.setZ(var3 * (double)(Float)this.hSpeed.getValue() * var10 - var5 * (double)(Float)this.hSpeed.getValue() * var8);
            }
         }
      } else if (var1.getEventState() == Event.State.PRE && this.mode.getValue() == ElytraFly.Mode.BYPASS && (Integer)this.devMode.getValue() == 3) {
         if (mc.player.isElytraFlying()) {
            var1.setX(0.0D);
            var1.setY(-1.0E-4D);
            var1.setZ(0.0D);
            double var13 = (double)mc.player.movementInput.moveForward;
            double var4 = (double)mc.player.movementInput.moveStrafe;
            double[] var6 = this.forwardStrafeYaw(var13, var4, (double)mc.player.rotationYaw);
            double var14 = var6[0];
            double var9 = var6[1];
            double var11 = var6[2];
            if (var13 != 0.0D || var4 != 0.0D) {
               var1.setX(var14 * (double)(Float)this.speed.getValue() * Math.cos(Math.toRadians(var11 + 90.0D)) + var9 * (double)(Float)this.speed.getValue() * Math.sin(Math.toRadians(var11 + 90.0D)));
               var1.setY(var14 * (double)(Float)this.speed.getValue() * Math.sin(Math.toRadians(var11 + 90.0D)) - var9 * (double)(Float)this.speed.getValue() * Math.cos(Math.toRadians(var11 + 90.0D)));
            }

            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
               var1.setY(-1.0D);
            }
         }
      } else if (this.mode.getValue() == ElytraFly.Mode.TOOBEE) {
         if (!mc.player.isElytraFlying()) {
            return;
         }

         if (mc.player.movementInput.jump) {
            return;
         }

         if (mc.player.movementInput.sneak) {
            mc.player.motionY = (double)(-((Float)this.tooBeeSpeed.getValue() / 2.0F));
            var1.setY((double)(-((Float)this.speed.getValue() / 2.0F)));
         } else if (var1.getY() != -1.01E-4D) {
            var1.setY(-1.01E-4D);
            mc.player.motionY = -1.01E-4D;
         }
      }

      this.setMoveSpeed(var1, (double)(Float)this.tooBeeSpeed.getValue());
   }

   public static enum Mode {
      // $FF: synthetic field
      FLY,
      // $FF: synthetic field
      OHARE,
      // $FF: synthetic field
      PACKET,
      // $FF: synthetic field
      VANILLA,
      // $FF: synthetic field
      BOOST,
      // $FF: synthetic field
      TOOBEE,
      // $FF: synthetic field
      BETTER,
      // $FF: synthetic field
      BYPASS;
   }
}
