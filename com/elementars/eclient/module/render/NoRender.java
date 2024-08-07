package com.elementars.eclient.module.render;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
   // $FF: synthetic field
   private final Value paint = this.register(new Value("Paintings", this, false));
   // $FF: synthetic field
   public final Value armor = this.register(new Value("Armor", this, false));
   // $FF: synthetic field
   private final Value object = this.register(new Value("Object", this, false));
   // $FF: synthetic field
   public final Value armorTrans = this.register(new Value("Armor Transparency", this, false));
   // $FF: synthetic field
   private final Value gentity = this.register(new Value("GEntity", this, false));
   // $FF: synthetic field
   public final Value alpha = this.register(new Value("Transparency", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value fire = this.register(new Value("Fire", this, true));
   // $FF: synthetic field
   private final Value mob = this.register(new Value("Mob", this, false));
   // $FF: synthetic field
   private final Value xp = this.register(new Value("XP", this, false));

   @SubscribeEvent
   public void onBlockOverlay(RenderBlockOverlayEvent var1) {
      if ((Boolean)this.fire.getValue() && var1.getOverlayType() == OverlayType.FIRE) {
         var1.setCanceled(true);
      }

   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public NoRender() {
      super("NoRender", "Prevents rendering of certain things", 0, Category.RENDER, true);
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      Packet var2 = var1.getPacket();
      if (var2 instanceof SPacketSpawnMob && (Boolean)this.mob.getValue() || var2 instanceof SPacketSpawnGlobalEntity && (Boolean)this.gentity.getValue() || var2 instanceof SPacketSpawnObject && (Boolean)this.object.getValue() || var2 instanceof SPacketSpawnExperienceOrb && (Boolean)this.xp.getValue() || var2 instanceof SPacketSpawnPainting && (Boolean)this.paint.getValue()) {
         var1.setCancelled(true);
      }

   }
}
