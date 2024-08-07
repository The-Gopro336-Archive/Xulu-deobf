package com.elementars.eclient.module.movement;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class PacketFly extends Module {
   // $FF: synthetic field
   private float counter = 0.0F;
   // $FF: synthetic field
   int j;
   // $FF: synthetic field
   private final Value fallSpeed = this.register(new Value("Fall Speed", this, 0.005F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value upSpeed = this.register(new Value("Up Speed", this, 0.05F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value defaults = this.register(new Value("Defaults", this, false));
   // $FF: synthetic field
   private final Value cooldown = this.register(new Value("Cooldown", this, Short.valueOf((short)0), Short.valueOf((short)0), Short.valueOf((short)10)));

   public void onUpdate() {
      if ((Boolean)this.defaults.getValue()) {
         this.cooldown.setValue(Short.valueOf((short)0));
         this.fallSpeed.setValue(0.005F);
         this.upSpeed.setValue(0.05F);
      }

   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent var1) {
      if (mc.player != null) {
         if (var1.phase == Phase.END) {
            if (!mc.player.isElytraFlying()) {
               if (this.counter < 1.0F) {
                  this.counter += (float)(Short)this.cooldown.getValue();
                  mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround));
                  mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY - 0.03D, mc.player.posZ, mc.player.onGround));
                  mc.player.connection.sendPacket(new CPacketConfirmTeleport(++this.j));
               } else {
                  --this.counter;
               }
            }
         } else if (mc.gameSettings.keyBindJump.isPressed()) {
            mc.player.motionY = (double)(Float)this.upSpeed.getValue();
         } else {
            mc.player.motionY = (double)(-(Float)this.fallSpeed.getValue());
         }

      }
   }

   public PacketFly() {
      super("PacketFly", "Flies with packets", 0, Category.MOVEMENT, true);
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
      this.j = 0;
   }
}
