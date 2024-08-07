package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SecretShaders extends Module {
   // $FF: synthetic field
   private final Value shader = this.register(new Value("Shader", this, "notch", new ArrayList(Arrays.asList("antialias", "art", "bits", "blobs", "blobs2", "blur", "bumpy", "color_convolve", "creeper", "deconverge", "desaturate", "entity_outline", "flip", "fxaa", "green", "invert", "notch", "ntsc", "outline", "pencil", "phosphor", "scan_pincusion", "sobel", "spider", "wobble"))));

   public void onDisable() {
      if (mc.entityRenderer.getShaderGroup() != null) {
         mc.entityRenderer.getShaderGroup().deleteShaderGroup();
      }

   }

   public void onEnable() {
      if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
         if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
         }

         try {
            mc.entityRenderer.loadShader(new ResourceLocation(String.valueOf((new StringBuilder()).append("shaders/post/").append((String)this.shader.getValue()).append(".json"))));
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      } else if (mc.entityRenderer.getShaderGroup() != null && mc.currentScreen == null) {
         mc.entityRenderer.getShaderGroup().deleteShaderGroup();
      }

   }

   public SecretShaders() {
      super("SecretShaders", "Brings back super secret settings shaders", 0, Category.RENDER, true);
   }
}
