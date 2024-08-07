package com.elementars.eclient.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
   // $FF: synthetic field
   private final Map glCapMap = new HashMap();

   public void disableGlCap(int var1) {
      this.setGlCap(var1, false);
   }

   private void glColor(int var1) {
      float var2 = (float)(var1 >> 24 & 255) / 255.0F;
      float var3 = (float)(var1 >> 16 & 255) / 255.0F;
      float var4 = (float)(var1 >> 8 & 255) / 255.0F;
      float var5 = (float)(var1 & 255) / 255.0F;
      GlStateManager.color(var3, var4, var5, var2);
   }

   public void enableGlCap(int var1) {
      this.setGlCap(var1, true);
   }

   public void setGlCap(int var1, boolean var2) {
      this.glCapMap.put(var1, GL11.glGetBoolean(var1));
      this.setGlState(var1, var2);
   }

   public void glColor(int var1, int var2, int var3, int var4) {
      GlStateManager.color((float)var1 / 255.0F, (float)var2 / 255.0F, (float)var3 / 255.0F, (float)var4 / 255.0F);
   }

   public void setGlState(int var1, boolean var2) {
      if (var2) {
         GL11.glEnable(var1);
      } else {
         GL11.glDisable(var1);
      }

   }

   public void glColor(Color var1) {
      float var2 = (float)var1.getRed() / 255.0F;
      float var3 = (float)var1.getGreen() / 255.0F;
      float var4 = (float)var1.getBlue() / 255.0F;
      float var5 = (float)var1.getAlpha() / 255.0F;
      GlStateManager.color(var2, var3, var4, var5);
   }

   public void resetCaps() {
      this.glCapMap.forEach(this::setGlState);
   }

   public void enableGlCap(int... var1) {
      int[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4];
         this.setGlCap(var5, true);
      }

   }

   public void disableGlCap(int... var1) {
      int[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4];
         this.setGlCap(var5, false);
      }

   }
}
