package com.elementars.eclient.module.combat;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketUseEntity.Action;

public class Criticals extends Module {
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Packet", new String[]{"Jump", "Packet"}));

   public Criticals() {
      super("Criticals", "Automatically crits people", 0, Category.COMBAT, true);
   }

   @EventTarget
   public void sendPacket(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketUseEntity) {
         CPacketUseEntity var2 = (CPacketUseEntity)var1.getPacket();
         if (var2.getAction() == Action.ATTACK && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && var2.getEntityFromWorld(mc.world) instanceof EntityLivingBase) {
            if (((String)this.mode.getValue()).equalsIgnoreCase("Packet")) {
               mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY + 0.10000000149011612D, mc.player.posZ, false));
               mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            } else {
               mc.player.jump();
            }
         }
      }

   }
}
