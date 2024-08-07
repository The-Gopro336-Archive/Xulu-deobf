package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class CoordLogger extends Module {
   // $FF: synthetic field
   private final Value dragon = this.register(new Value("Dragon", this, false));
   // $FF: synthetic field
   private final Value wither = this.register(new Value("Wither", this, false));
   // $FF: synthetic field
   private final Value portal = this.register(new Value("EndPortal", this, false));
   // $FF: synthetic field
   private HashMap knownPlayers = new HashMap();
   // $FF: synthetic field
   private final Value savetofile = this.register(new Value("SaveToFile", this, false));
   // $FF: synthetic field
   private final Value tp = this.register(new Value("TpExploit", this, false));
   // $FF: synthetic field
   private final Value lightning = this.register(new Value("Thunder", this, false));
   // $FF: synthetic field
   SPacketSoundEffect packet;
   // $FF: synthetic field
   SPacketEffect packet2;

   public void onUpdate() {
      if ((Boolean)this.tp.getValue()) {
         if (mc.player != null) {
            List var1 = mc.world.getLoadedEntityList();
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               Entity var3 = (Entity)var2.next();
               if (var3 instanceof EntityPlayer && !var3.getName().equals(mc.player.getName())) {
                  Vec3d var4 = new Vec3d(var3.posX, var3.posY, var3.posZ);
                  if (this.knownPlayers.containsKey(var3)) {
                     if (Math.abs(mc.player.getPositionVector().distanceTo(var4)) >= 128.0D && ((Vec3d)this.knownPlayers.get(var3)).distanceTo(var4) >= 64.0D) {
                        this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Player ").append(var3.getName()).append(" moved to Position ").append(var4.toString())));
                     }

                     this.knownPlayers.put(var3, var4);
                  } else {
                     this.knownPlayers.put(var3, var4);
                  }
               }
            }

         }
      }
   }

   @EventTarget
   public void onPacket(EventSendPacket var1) {
      if ((Boolean)this.lightning.getValue() && var1.getPacket() instanceof SPacketSoundEffect) {
         this.packet = (SPacketSoundEffect)var1.getPacket();
         if (this.packet.getCategory() == SoundCategory.WEATHER && this.packet.getSound() == SoundEvents.ENTITY_LIGHTNING_THUNDER) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Lightning spawned at X").append(this.packet.getX()).append(" Z").append(this.packet.getZ())));
         }
      }

      if (var1.getPacket() instanceof SPacketEffect) {
         this.packet2 = (SPacketEffect)var1.getPacket();
         if ((Boolean)this.portal.getValue() && this.packet2.getSoundType() == 1038) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("End Portal activated at X").append(this.packet2.getSoundPos().getX()).append(" Y").append(this.packet2.getSoundPos().getY()).append(" Z").append(this.packet2.getSoundPos().getZ())));
         }

         if ((Boolean)this.wither.getValue() && this.packet2.getSoundType() == 1023) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Wither spawned at X").append(this.packet2.getSoundPos().getX()).append(" Y").append(this.packet2.getSoundPos().getY()).append(" Z").append(this.packet2.getSoundPos().getZ())));
         }

         if ((Boolean)this.dragon.getValue() && this.packet2.getSoundType() == 1028) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Dragon killed at X").append(this.packet2.getSoundPos().getX()).append(" Y").append(this.packet2.getSoundPos().getY()).append(" Z").append(this.packet2.getSoundPos().getZ())));
         }
      }

   }

   public CoordLogger() {
      super("CoordLogger", "Logs coords taken from several exploits", 0, Category.MISC, true);
   }

   private void sendNotification(String var1) {
      Command.sendChatMessage(var1);
      if ((Boolean)this.savetofile.getValue()) {
         Wrapper.getFileManager().appendTextFile(var1, "CoordLogger.txt");
      }

   }
}
