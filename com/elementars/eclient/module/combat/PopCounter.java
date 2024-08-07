package com.elementars.eclient.module.combat;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.event.events.EventTotemPop;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.TargetPlayers;
import dev.xulu.settings.Value;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

public class PopCounter extends Module {
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   private final Value chat = this.register(new Value("Send Message", this, true));
   // $FF: synthetic field
   public static PopCounter INSTANCE;
   // $FF: synthetic field
   public ConcurrentHashMap popMap = new ConcurrentHashMap();
   // $FF: synthetic field
   private final Value ncolor;
   // $FF: synthetic field
   private final Value onlyTargets = this.register(new Value("Only Targets", this, true));
   // $FF: synthetic field
   private final Value color;

   public void onUpdate() {
      Iterator var1 = mc.world.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (var2.getHealth() == 0.0F && this.popMap.containsKey(var2)) {
            if ((Boolean)this.chat.getValue()) {
               this.sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" has died!")));
            }

            this.popMap.remove(var2);
         }
      }

   }

   @EventTarget
   public void onPop(EventTotemPop var1) {
      if (TargetPlayers.targettedplayers.containsKey(var1.getPlayer().getName()) || !(Boolean)this.onlyTargets.getValue()) {
         int var2 = (Integer)this.popMap.getOrDefault(var1.getPlayer(), 0) + 1;
         if ((Boolean)this.chat.getValue()) {
            this.sendChatMessage(String.valueOf((new StringBuilder()).append(var1.getPlayer().getName()).append(" has popped ").append(ColorTextUtils.getColor((String)this.ncolor.getValue())).append(var2).append(ColorTextUtils.getColor((String)this.color.getValue())).append(" times!")));
         }

         this.popMap.put(var1.getPlayer(), var2);
      }

   }

   public PopCounter() {
      super("PopCounter", "Counts how many times your enemy pops", 0, Category.COMBAT, true);
      this.color = this.register(new Value("Color", this, "White", ColorTextUtils.colors));
      this.ncolor = this.register(new Value("Number Color", this, "White", ColorTextUtils.colors));
      INSTANCE = this;
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketEntityStatus) {
         SPacketEntityStatus var2 = (SPacketEntityStatus)var1.getPacket();
         if (var2.getOpCode() == 35 && var2.getEntity(mc.world) instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer)var2.getEntity(mc.world);
            EventTotemPop var4 = new EventTotemPop(var3);
            var4.call();
         }
      }

   }

   public void sendChatMessage(String var1) {
      if ((Boolean)this.watermark.getValue()) {
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var1)));
      } else {
         Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var1)));
      }

   }
}
