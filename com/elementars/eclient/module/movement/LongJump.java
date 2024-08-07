package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.potion.PotionEffect;

public class LongJump extends Module {
   // $FF: synthetic field
   Value speedDetect = this.register(new Value("Speed Detect", this, true));
   // $FF: synthetic field
   Value jumpDetect = this.register(new Value("Leaping Detect", this, true));
   // $FF: synthetic field
   private static String[] llIlIIl;
   // $FF: synthetic field
   private int currentState;
   // $FF: synthetic field
   Value autoSprint = this.register(new Value("Auto Sprint", this, false));
   // $FF: synthetic field
   Value accelerationTimer = this.register(new Value("Acceleration Timer", this, false));
   // $FF: synthetic field
   private static int[] llIllII;
   // $FF: synthetic field
   Value chat = this.register(new Value("Toggle msgs", this, false));
   // $FF: synthetic field
   @EventHandler
   private Listener packetEventListener;
   // $FF: synthetic field
   Value multiplier = this.register(new Value("Multiplier", this, 4.1D, 1.0D, 10.0D));
   // $FF: synthetic field
   Value timerSpeed = this.register(new Value("Timer Speed", this, 1, 0, 10));
   // $FF: synthetic field
   private double prevDist;
   // $FF: synthetic field
   private boolean attempting = false;
   // $FF: synthetic field
   private double motionSpeed;
   // $FF: synthetic field
   Value extraYBoost = this.register(new Value("Extra Y Boost", this, 0.0D, 0.0D, 1.0D));

   public void onDisable() {
      if ((Boolean)this.chat.getValue()) {
         this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("Disabled!")));
      }

   }

   static {
      lIIlIIIII();
      lIIIllllI();
   }

   private static int lIIlIIIIl(float var0, float var1) {
      float var2;
      return (var2 = var0 - var1) == 0.0F ? 0 : (var2 < 0.0F ? -1 : 1);
   }

   private static String lIIIllIII(String var0, String var1) {
      try {
         SecretKeySpec var2 = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(var1.getBytes(StandardCharsets.UTF_8)), "Blowfish");
         Cipher var3 = Cipher.getInstance("Blowfish");
         var3.init(llIllII[2], var2);
         return new String(var3.doFinal(Base64.getDecoder().decode(var0.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public LongJump() {
      super("LongJump", "hop around", 0, Category.MOVEMENT, true);
   }

   public String getHudInfo() {
      return "Speed";
   }

   public void onEnable() {
      if ((Boolean)this.chat.getValue()) {
         this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("Enabled!")));
      }

      this.attempting = false;
   }

   private static int lIIlIIlll(double var0, double var2) {
      double var4;
      return (var4 = var0 - var2) == 0.0D ? 0 : (var4 < 0.0D ? -1 : 1);
   }

   public void onUpdate() {
      if (!isNull(mc.player)) {
         this.prevDist = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ));
         if (lIIlIIlII((Boolean)this.accelerationTimer.getValue() ? 1 : 0)) {
            mc.timer.tickLength = 50.0F / (float)(Integer)this.timerSpeed.getValue();
         } else if (lIIlIIlII(lIIlIIIIl(mc.timer.tickLength, 50.0F))) {
            mc.timer.tickLength = 50.0F;
         }

         if (lIIlIIlIl(mc.player.isSprinting() ? 1 : 0) && lIIlIIlII((Boolean)this.autoSprint.getValue() ? 1 : 0)) {
            mc.player.setSprinting(llIllII[1] != 0);
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SPRINTING));
         }

      }
   }

   private static boolean lIIlIlIIl(int var0) {
      return var0 > 0;
   }

   private static boolean lIIlIIlIl(int var0) {
      return var0 == 0;
   }

   private static boolean isNull(Object var0) {
      return var0 == null;
   }

   @EventTarget
   public void onMove(PlayerMoveEvent var1) {
      if (var1.getEventState() == Event.State.PRE) {
         if (!isNull(mc.player)) {
            float var10 = mc.timer.tickLength / 1000.0F;
            switch(this.currentState) {
            case 0:
               this.currentState += llIllII[1];
               this.prevDist = 0.0D;
               break;
            case 1:
            default:
               if ((!lIIlIlIII(mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, mc.player.motionY, 0.0D)).size()) || lIIlIIlII(mc.player.collidedVertically ? 1 : 0)) && lIIlIlIIl(this.currentState)) {
                  if (lIIlIIlIl(lIIlIIllI(mc.player.moveForward, 0.0F)) && lIIlIIlIl(lIIlIIllI(mc.player.moveStrafing, 0.0F))) {
                     this.currentState = llIllII[0];
                  } else {
                     this.currentState = llIllII[1];
                  }
               }

               this.motionSpeed = this.prevDist - this.prevDist / 159.0D;
               break;
            case 2:
               double var2 = 0.40123128D + (Double)this.extraYBoost.getValue();
               if ((!lIIlIIlIl(lIIlIIllI(mc.player.moveForward, 0.0F)) || lIIlIIlII(lIIlIIllI(mc.player.moveStrafing, 0.0F))) && lIIlIIlII(mc.player.onGround ? 1 : 0)) {
                  if (lIIlIIlII(mc.player.isPotionActive(MobEffects.JUMP_BOOST) ? 1 : 0) && lIIlIIlII((Boolean)this.jumpDetect.getValue() ? 1 : 0)) {
                     var2 += (double)((float)(mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + llIllII[1]) * 0.1F);
                  }

                  var1.setY(mc.player.motionY = var2);
                  this.motionSpeed *= 2.149D;
               }
               break;
            case 3:
               this.motionSpeed = this.prevDist - 0.76D * (this.prevDist - this.getBaseMotionSpeed());
            }

            this.motionSpeed = Math.max(this.motionSpeed, this.getBaseMotionSpeed());
            double var4 = (double)mc.player.movementInput.moveForward;
            double var6 = (double)mc.player.movementInput.moveStrafe;
            double var8 = (double)mc.player.rotationYaw;
            if (lIIlIIlIl(lIIlIIlll(var4, 0.0D)) && lIIlIIlIl(lIIlIIlll(var6, 0.0D))) {
               var1.setX(0.0D);
               var1.setZ(0.0D);
            }

            if (lIIlIIlII(lIIlIIlll(var4, 0.0D)) && lIIlIIlII(lIIlIIlll(var6, 0.0D))) {
               var4 *= Math.sin(0.7853981633974483D);
               var6 *= Math.cos(0.7853981633974483D);
            }

            var1.setX((var4 * this.motionSpeed * -Math.sin(Math.toRadians(var8)) + var6 * this.motionSpeed * Math.cos(Math.toRadians(var8))) * 0.99D);
            var1.setZ((var4 * this.motionSpeed * Math.cos(Math.toRadians(var8)) - var6 * this.motionSpeed * -Math.sin(Math.toRadians(var8))) * 0.99D);
            this.attempting = true;
            this.currentState += llIllII[1];
         }

         var1.setCancelled(true);
      }
   }

   private static int lIIlIIllI(float var0, float var1) {
      float var2;
      return (var2 = var0 - var1) == 0.0F ? 0 : (var2 < 0.0F ? -1 : 1);
   }

   private static String lIIIlllIl(String var0, String var1) {
      try {
         SecretKeySpec var2 = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(var1.getBytes(StandardCharsets.UTF_8)), llIllII[9]), "DES");
         Cipher var3 = Cipher.getInstance("DES");
         var3.init(llIllII[2], var2);
         return new String(var3.doFinal(Base64.getDecoder().decode(var0.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static boolean lIIlIlIII(int var0) {
      return var0 <= 0;
   }

   private static boolean lIIlIIlII(int var0) {
      return var0 != 0;
   }

   private static void lIIIllllI() {
      (llIlIIl = new String[llIllII[8]])[llIllII[0]] = lIIIllIII("n9pHF6SFvkOs6iUr+fnXgA==", "GmCTC");
      llIlIIl[llIllII[1]] = lIIIllIII("4noHmwJ5F40+cu8qBPcyzA==", "CVFaT");
      llIlIIl[llIllII[2]] = lIIIllIII("R+hGwU+dCgQQcUdIkD9ZYaUO+QBhMxiN", "RjGgZ");
      llIlIIl[llIllII[3]] = lIIIllIII("Dk9SQuIPQSn5I8lWMj8Z+w==", "dWNML");
      llIlIIl[llIllII[5]] = lIIIllIII("rPWGh7vSeiSJWWJOJQfq5wdZ8fI6Y9G+", "QkkkG");
      llIlIIl[llIllII[6]] = lIIIllIII("6BSD78RsHX6yVgm/4JINjBgTGCxZfgXF", "rXpxu");
      llIlIIl[llIllII[7]] = lIIIlllIl("ENR8rJxJYtA86kRMf8iVlQ==", "RTxXY");
   }

   private double getBaseMotionSpeed() {
      double var1 = 0.272D * (Double)this.multiplier.getValue();
      if (lIIlIIlII(mc.player.isPotionActive(MobEffects.SPEED) ? 1 : 0) && lIIlIIlII((Boolean)this.speedDetect.getValue() ? 1 : 0)) {
         int var3 = ((PotionEffect)Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED))).getAmplifier();
         var1 *= 1.0D + 0.2D * (double)var3;
      }

      return var1;
   }

   private static void lIIlIIIII() {
      (llIllII = new int[10])[0] = 0;
      llIllII[1] = " ".length();
      llIllII[2] = "  ".length();
      llIllII[3] = "   ".length();
      llIllII[4] = 10;
      llIllII[5] = 4;
      llIllII[6] = 5;
      llIllII[7] = 6;
      llIllII[8] = 7;
      llIllII[9] = 8;
   }
}
