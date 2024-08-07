package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Pair;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BossStack extends Module {
   // $FF: synthetic field
   private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
   // $FF: synthetic field
   public static Value mode;
   // $FF: synthetic field
   private static Value scale;

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   @SubscribeEvent
   public void render(Pre var1) {
      Map var2;
      int var5;
      String var9;
      if (((String)mode.getValue()).equalsIgnoreCase("Minimize")) {
         var2 = Minecraft.getMinecraft().ingameGUI.getBossOverlay().mapBossInfos;
         if (var2 == null) {
            return;
         }

         ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft());
         int var4 = var3.getScaledWidth();
         var5 = 12;

         for(Iterator var6 = var2.entrySet().iterator(); var6.hasNext(); var5 += 10 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) {
            Entry var7 = (Entry)var6.next();
            BossInfoClient var8 = (BossInfoClient)var7.getValue();
            var9 = var8.getName().getFormattedText();
            int var10 = (int)((float)var4 / (Float)scale.getValue() / 2.0F - 91.0F);
            GL11.glScaled((double)(Float)scale.getValue(), (double)(Float)scale.getValue(), 1.0D);
            if (!var1.isCanceled()) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().getTextureManager().bindTexture(GUI_BARS_TEXTURES);
               Minecraft.getMinecraft().ingameGUI.getBossOverlay().render(var10, var5, var8);
               Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(var9, (float)var4 / (Float)scale.getValue() / 2.0F - (float)(Minecraft.getMinecraft().fontRenderer.getStringWidth(var9) / 2), (float)(var5 - 9), 16777215);
            }

            GL11.glScaled(1.0D / (double)(Float)scale.getValue(), 1.0D / (double)(Float)scale.getValue(), 1.0D);
         }
      } else if (((String)mode.getValue()).equalsIgnoreCase("Stack")) {
         var2 = Minecraft.getMinecraft().ingameGUI.getBossOverlay().mapBossInfos;
         HashMap var13 = new HashMap();
         Iterator var14 = var2.entrySet().iterator();

         while(var14.hasNext()) {
            Entry var16 = (Entry)var14.next();
            String var17 = ((BossInfoClient)var16.getValue()).getName().getFormattedText();
            Pair var19;
            if (var13.containsKey(var17)) {
               var19 = (Pair)var13.get(var17);
               var19 = new Pair(var19.getKey(), (Integer)var19.getValue() + 1);
               var13.put(var17, var19);
            } else {
               var19 = new Pair(var16.getValue(), 1);
               var13.put(var17, var19);
            }
         }

         ScaledResolution var15 = new ScaledResolution(Minecraft.getMinecraft());
         var5 = var15.getScaledWidth();
         int var18 = 12;

         for(Iterator var20 = var13.entrySet().iterator(); var20.hasNext(); var18 += 10 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) {
            Entry var21 = (Entry)var20.next();
            var9 = (String)var21.getKey();
            BossInfoClient var22 = (BossInfoClient)((Pair)var21.getValue()).getKey();
            int var11 = (Integer)((Pair)var21.getValue()).getValue();
            var9 = String.valueOf((new StringBuilder()).append(var9).append(" x").append(var11));
            int var12 = (int)((float)var5 / (Float)scale.getValue() / 2.0F - 91.0F);
            GL11.glScaled((double)(Float)scale.getValue(), (double)(Float)scale.getValue(), 1.0D);
            if (!var1.isCanceled()) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().getTextureManager().bindTexture(GUI_BARS_TEXTURES);
               Minecraft.getMinecraft().ingameGUI.getBossOverlay().render(var12, var18, var22);
               Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(var9, (float)var5 / (Float)scale.getValue() / 2.0F - (float)(Minecraft.getMinecraft().fontRenderer.getStringWidth(var9) / 2), (float)(var18 - 9), 16777215);
            }

            GL11.glScaled(1.0D / (double)(Float)scale.getValue(), 1.0D / (double)(Float)scale.getValue(), 1.0D);
         }
      }

   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public BossStack() {
      super("BossStack", "Stacks boss bars", 0, Category.RENDER, true);
      mode = this.register(new Value("Mode", this, "Stack", new ArrayList(Arrays.asList("Remove", "Stack", "Minimize"))));
      scale = this.register(new Value("Scale", this, 0.5F, 0.0F, 1.0F));
   }
}
