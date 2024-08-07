package com.elementars.eclient.util;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class HueCycler {
   // $FF: synthetic field
   int index = 0;
   // $FF: synthetic field
   int[] cycles;

   public void reset(int var1) {
      this.index = var1;
   }

   public int next() {
      int var1 = this.cycles[this.index];
      ++this.index;
      if (this.index >= this.cycles.length) {
         this.index = 0;
      }

      return var1;
   }

   public HueCycler(int var1) {
      if (var1 <= 0) {
         throw new IllegalArgumentException("cycles <= 0");
      } else {
         this.cycles = new int[var1];
         double var2 = 0.0D;
         double var4 = 1.0D / (double)var1;

         for(int var6 = 0; var6 < var1; ++var6) {
            this.cycles[var6] = Color.HSBtoRGB((float)var2, 1.0F, 1.0F);
            var2 += var4;
         }

      }
   }

   public void reset() {
      this.index = 0;
   }

   public int current() {
      return this.cycles[this.index];
   }

   public void set() {
      int var1 = this.cycles[this.index];
      float var2 = (float)(var1 >> 16 & 255) / 255.0F;
      float var3 = (float)(var1 >> 8 & 255) / 255.0F;
      float var4 = (float)(var1 & 255) / 255.0F;
      GL11.glColor3f(var2, var3, var4);
   }

   public void setNext(float var1) {
      int var2 = this.next();
      float var3 = (float)(var2 >> 16 & 255) / 255.0F;
      float var4 = (float)(var2 >> 8 & 255) / 255.0F;
      float var5 = (float)(var2 & 255) / 255.0F;
      GL11.glColor4f(var3, var4, var5, var1);
   }

   public void setNext() {
      int var1 = this.next();
   }
}
