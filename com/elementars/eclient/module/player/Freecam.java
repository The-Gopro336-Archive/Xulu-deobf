package com.elementars.eclient.module.player;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.settings.Value;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Freecam extends Module {
   // $FF: synthetic field
   private final Value vspeed = this.register(new Value("V Speed", this, 7, 1, 20));
   // $FF: synthetic field
   private float pitch;
   // $FF: synthetic field
   private float yaw;
   // $FF: synthetic field
   private final Value cancelPackets = this.register(new Value("Cancel Packets", this, true));
   // $FF: synthetic field
   private EntityOtherPlayerMP clonedPlayer;
   // $FF: synthetic field
   private Entity ridingEntity;
   // $FF: synthetic field
   private double posX;
   // $FF: synthetic field
   private double startPosZ;
   // $FF: synthetic field
   private float startPitch;
   // $FF: synthetic field
   private boolean isRidingEntity;
   // $FF: synthetic field
   private float startYaw;
   // $FF: synthetic field
   private final Value speed = this.register(new Value("Speed", this, 11, 1, 20));
   // $FF: synthetic field
   private double posZ;
   // $FF: synthetic field
   private double startPosY;
   // $FF: synthetic field
   private double posY;
   // $FF: synthetic field
   private double startPosX;

   @EventTarget
   public void onPacketSent(EventSendPacket var1) {
      if ((Boolean)this.cancelPackets.getValue() && (var1.getPacket() instanceof CPacketPlayer || var1.getPacket() instanceof CPacketInput)) {
         var1.setCancelled(true);
      }

   }

   public void onUpdate() {
      if (!Wrapper.getMinecraft().player.onGround) {
         Wrapper.getMinecraft().player.motionY = -0.2D;
      }

      Wrapper.getMinecraft().player.onGround = true;
      Wrapper.getMinecraft().player.motionY = 0.0D;
      Wrapper.getMinecraft().player.noClip = true;
      Wrapper.getMinecraft().player.capabilities.isFlying = true;
      Wrapper.getMinecraft().player.capabilities.setFlySpeed((float)(Integer)this.speed.getValue() / 100.0F);
      Wrapper.getMinecraft().player.onGround = false;
      Wrapper.getMinecraft().player.fallDistance = 0.0F;
      EntityPlayerSP var10000;
      if (mc.gameSettings.keyBindJump.isKeyDown()) {
         var10000 = mc.player;
         var10000.motionY += (double)((float)(Integer)this.vspeed.getValue() / 10.0F);
      }

      if (mc.gameSettings.keyBindSneak.isKeyDown()) {
         var10000 = mc.player;
         var10000.motionY += (double)((float)(-(Integer)this.vspeed.getValue()) / 10.0F);
      }

   }

   private void playersSpeed(double var1) {
      if (Wrapper.getMinecraft().player != null) {
         MovementInput var3 = Wrapper.getMinecraft().player.movementInput;
         double var4 = (double)var3.moveForward;
         double var6 = (double)var3.moveStrafe;
         float var8 = Wrapper.getMinecraft().player.rotationYaw;
         if (var4 == 0.0D && var6 == 0.0D) {
            Wrapper.getMinecraft().player.motionX = 0.0D;
            Wrapper.getMinecraft().player.motionZ = 0.0D;
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

            Wrapper.getMinecraft().player.motionX = var4 * var1 * Math.cos(Math.toRadians((double)(var8 + 90.0F))) + var6 * var1 * Math.sin(Math.toRadians((double)(var8 + 90.0F)));
            Wrapper.getMinecraft().player.motionZ = var4 * var1 * Math.sin(Math.toRadians((double)(var8 + 90.0F))) - var6 * var1 * Math.cos(Math.toRadians((double)(var8 + 90.0F)));
         }
      }

   }

   public Freecam() {
      super("Freecam", "Allows you to fly out of your body", 0, Category.PLAYER, true);
   }

   @EventTarget
   public void onPacketRecived(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketPlayerPosLook) {
         SPacketPlayerPosLook var2 = (SPacketPlayerPosLook)var1.getPacket();
         this.startPosX = var2.getX();
         this.startPosY = var2.getY();
         this.startPosZ = var2.getZ();
         this.startPitch = var2.getPitch();
         this.startYaw = var2.getYaw();
      }

   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
      if (Wrapper.getMinecraft().player != null) {
         this.isRidingEntity = Wrapper.getMinecraft().player.getRidingEntity() != null;
         if (Wrapper.getMinecraft().player.getRidingEntity() == null) {
            this.posX = Wrapper.getMinecraft().player.posX;
            this.posY = Wrapper.getMinecraft().player.posY;
            this.posZ = Wrapper.getMinecraft().player.posZ;
         } else {
            this.ridingEntity = Wrapper.getMinecraft().player.getRidingEntity();
            Wrapper.getMinecraft().player.dismountRidingEntity();
         }

         this.pitch = Wrapper.getMinecraft().player.rotationPitch;
         this.yaw = Wrapper.getMinecraft().player.rotationYaw;
         this.clonedPlayer = new EntityOtherPlayerMP(Wrapper.getMinecraft().world, Wrapper.getMinecraft().getSession().getProfile());
         this.clonedPlayer.copyLocationAndAnglesFrom(Wrapper.getMinecraft().player);
         this.clonedPlayer.rotationYawHead = Wrapper.getMinecraft().player.rotationYawHead;
         Wrapper.getMinecraft().world.addEntityToWorld(-100, this.clonedPlayer);
         Wrapper.getMinecraft().player.noClip = true;
      }

      super.onEnable();
   }

   @EventTarget
   public void onMove(PlayerMoveEvent var1) {
      if (var1.getEventState() == Event.State.PRE) {
         mc.player.noClip = true;
      }
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
      if (Wrapper.getMinecraft().player != null) {
         Wrapper.getMinecraft().player.setPositionAndRotation(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
         Wrapper.getMinecraft().world.removeEntityFromWorld(-100);
         this.clonedPlayer = null;
         this.posX = this.posY = this.posZ = 0.0D;
         this.pitch = this.yaw = 0.0F;
         mc.player.capabilities.isFlying = false;
         Wrapper.getMinecraft().player.capabilities.setFlySpeed(0.05F);
         Wrapper.getMinecraft().player.noClip = false;
         Wrapper.getMinecraft().player.motionX = Wrapper.getMinecraft().player.motionY = Wrapper.getMinecraft().player.motionZ = 0.0D;
         if (this.isRidingEntity) {
            Wrapper.getMinecraft().player.startRiding(this.ridingEntity, true);
         }
      }

      Wrapper.getMinecraft().renderGlobal.loadRenderers();
      super.onDisable();
   }

   @SubscribeEvent
   public void onPush(PlayerSPPushOutOfBlocksEvent var1) {
      var1.setCanceled(true);
   }
}
