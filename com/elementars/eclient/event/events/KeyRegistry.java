package com.elementars.eclient.event.events;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.command.CommandManager;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.TargetPlayers;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import java.util.Iterator;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.opengl.GL11;

public class KeyRegistry implements Helper {
   @SubscribeEvent
   public void onKeyPress(KeyInputEvent var1) {
      Iterator var2 = Xulu.MODULE_MANAGER.getModules().iterator();

      while(var2.hasNext()) {
         Module var3 = (Module)var2.next();
         if (var3.keybind != null && var3.keybind.isPressed()) {
            var3.toggle();
         }
      }

   }

   @SubscribeEvent
   public void onTick(ClientTickEvent var1) {
      if (mc.player != null) {
         ModuleManager var10000 = Xulu.MODULE_MANAGER;
         ModuleManager.onUpdate();
         TargetPlayers.onUpdate();
         RainbowUtils.updateRainbow();
      }
   }

   @SubscribeEvent
   public void onRenderPre(Pre var1) {
      if (var1.getType() == ElementType.BOSSINFO && Xulu.MODULE_MANAGER.getModuleByName("BossStack").isToggled()) {
         var1.setCanceled(true);
      }

   }

   @SubscribeEvent
   public void onRender(Post var1) {
      if (!var1.isCanceled()) {
         ElementType var2 = ElementType.EXPERIENCE;
         if (!Wrapper.getPlayer().isCreative() && Wrapper.getPlayer().getRidingEntity() instanceof AbstractHorse) {
            var2 = ElementType.HEALTHMOUNT;
         }

         if (var1.getType() == var2) {
            ModuleManager var10000 = Xulu.MODULE_MANAGER;
            ModuleManager.onRender();
            GL11.glPushMatrix();
            GL11.glPopMatrix();
            XuluTessellator.releaseGL();
         }

      }
   }

   @SubscribeEvent
   public void onChatMessage(ClientChatEvent var1) {
      if (var1.getMessage().startsWith(Command.getPrefix())) {
         String var2 = var1.getMessage();
         var1.setCanceled(true);
         CommandManager.runCommand(var2.substring(Command.getPrefix().length()));
      }

   }

   @SubscribeEvent
   public void onAttack(AttackEntityEvent var1) {
      TargetPlayers.onAttack(var1);
   }

   @SubscribeEvent
   public void onWorldRender(RenderWorldLastEvent var1) {
      if (!var1.isCanceled()) {
         ModuleManager var10000 = Xulu.MODULE_MANAGER;
         ModuleManager.onWorldRender(var1);
      }
   }

   @SubscribeEvent
   public void onPlayerUpdate(LivingUpdateEvent var1) {
      if (Wrapper.getMinecraft().world != null && var1.getEntity().getEntityWorld().isRemote && var1.getEntityLiving().equals(Wrapper.getPlayer())) {
         LocalPlayerUpdateEvent var2 = new LocalPlayerUpdateEvent(var1.getEntityLiving());
         var2.call();
      }

   }
}
