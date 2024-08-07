package com.elementars.eclient.font.rainbow;

public class RainbowCycle implements Cloneable {
   // $FF: synthetic field
   public int b;
   // $FF: synthetic field
   public ColorChangeType blue;
   // $FF: synthetic field
   public ColorChangeType red;
   // $FF: synthetic field
   public int g;
   // $FF: synthetic field
   public ColorChangeType green;
   // $FF: synthetic field
   public int r;

   public RainbowCycle() {
      this.red = ColorChangeType.INCREASE;
      this.green = ColorChangeType.NONE;
      this.blue = ColorChangeType.NONE;
      this.r = 0;
      this.g = 0;
      this.b = 0;
   }

   public RainbowCycle clone() {
      try {
         return (RainbowCycle)super.clone();
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
         return this;
      }
   }
}
