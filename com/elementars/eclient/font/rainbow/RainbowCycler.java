package com.elementars.eclient.font.rainbow;

public class RainbowCycler {
   public static RainbowCycle rainbowCycle(int var0, RainbowCycle var1) {
      for(int var2 = 0; var2 < var0; ++var2) {
         if (var1.red == ColorChangeType.INCREASE) {
            var1.r += 4;
            if (var1.r > 255) {
               var1.red = ColorChangeType.DECRASE;
               var1.green = ColorChangeType.INCREASE;
            }
         } else if (var1.red == ColorChangeType.DECRASE) {
            var1.r -= 4;
            if (var1.r == 0) {
               var1.red = ColorChangeType.NONE;
            }
         }

         if (var1.green == ColorChangeType.INCREASE) {
            var1.g += 4;
            if (var1.g > 255) {
               var1.green = ColorChangeType.DECRASE;
               var1.blue = ColorChangeType.INCREASE;
            }
         } else if (var1.green == ColorChangeType.DECRASE) {
            var1.g -= 4;
            if (var1.g == 0) {
               var1.green = ColorChangeType.NONE;
            }
         }

         if (var1.blue == ColorChangeType.INCREASE) {
            var1.b += 4;
            if (var1.b > 255) {
               var1.blue = ColorChangeType.DECRASE;
               var1.red = ColorChangeType.INCREASE;
            }
         } else if (var1.blue == ColorChangeType.DECRASE) {
            var1.b -= 4;
            if (var1.b == 0) {
               var1.blue = ColorChangeType.NONE;
            }
         }
      }

      return var1;
   }
}
