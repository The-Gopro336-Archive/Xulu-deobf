package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.awt.Color;

public class EnchantColor extends Module {
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Color", new String[]{"Color", "Rainbow"}));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 255, 0, 255));

   public static Color getColor(long var0, float var2) {
      if (((String)((EnchantColor)Xulu.MODULE_MANAGER.getModuleT(EnchantColor.class)).mode.getValue()).equalsIgnoreCase("Color")) {
         return new Color((Integer)((EnchantColor)Xulu.MODULE_MANAGER.getModuleT(EnchantColor.class)).red.getValue(), (Integer)((EnchantColor)Xulu.MODULE_MANAGER.getModuleT(EnchantColor.class)).green.getValue(), (Integer)((EnchantColor)Xulu.MODULE_MANAGER.getModuleT(EnchantColor.class)).blue.getValue());
      } else {
         float var3 = (float)(System.nanoTime() + var0) / 1.0E10F % 1.0F;
         long var4 = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(var3, 1.0F, 1.0F))), 16);
         Color var6 = new Color((int)var4);
         return new Color((float)var6.getRed() / 255.0F * var2, (float)var6.getGreen() / 255.0F * var2, (float)var6.getBlue() / 255.0F * var2, (float)var6.getAlpha() / 255.0F);
      }
   }

   public EnchantColor() {
      super("EnchantColor", "Changes the color of the enchantment effect", 0, Category.RENDER, true);
   }
}
