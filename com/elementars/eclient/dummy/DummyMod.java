package com.elementars.eclient.dummy;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class DummyMod extends Module {
   // $FF: synthetic field
   String info;

   public DummyMod(String var1, String var2) {
      super(var1, "Dummy", 0, Category.DUMMY, true);
      this.info = var2;
   }

   public String getHudInfo() {
      return this.info;
   }

   public DummyMod(String var1) {
      super(var1, "Dummy", 0, Category.DUMMY, true);
   }
}
