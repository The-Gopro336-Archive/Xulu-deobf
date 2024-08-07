package com.elementars.eclient.module.player;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class Blink extends Module {
   // $FF: synthetic field
   Queue packets = new LinkedList();
   // $FF: synthetic field
   private EntityOtherPlayerMP clonedPlayer;

   public void onDisable() {
      Xulu.EVENT_MANAGER.unregister(this);

      while(!this.packets.isEmpty()) {
         mc.player.connection.sendPacket((Packet)this.packets.poll());
      }

      EntityPlayerSP var1 = mc.player;
      if (var1 != null) {
         mc.world.removeEntityFromWorld(-100);
         this.clonedPlayer = null;
      }

   }

   public Blink() {
      super("Blink", "Hides movement for a short distance", 0, Category.PLAYER, true);
   }

   public String getHudInfo() {
      return String.valueOf(this.packets.size());
   }

   public void onEnable() {
      Xulu.EVENT_MANAGER.register(this);
      if (mc.player != null) {
         this.clonedPlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
         this.clonedPlayer.copyLocationAndAnglesFrom(mc.player);
         this.clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
         mc.world.addEntityToWorld(-100, this.clonedPlayer);
      }

   }

   @EventTarget
   public void onPacket(EventSendPacket var1) {
      if (this.isToggled() && var1.getPacket() instanceof CPacketPlayer) {
         var1.setCancelled(true);
         this.packets.add((CPacketPlayer)var1.getPacket());
      }

   }
}
