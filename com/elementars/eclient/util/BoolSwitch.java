package com.elementars.eclient.util;

public class BoolSwitch {
   // $FF: synthetic field
   boolean value;

   public BoolSwitch(boolean var1) {
      this.value = var1;
   }

   public void setValue(boolean var1) {
      this.value = var1;
   }

   public boolean isValue() {
      return this.value;
   }

   public void toggle() {
      this.value = !this.value;
   }
}
