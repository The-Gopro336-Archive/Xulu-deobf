package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class Avoid extends Module {
   // $FF: synthetic field
   public static Value lava;
   // $FF: synthetic field
   public static Value fire;
   // $FF: synthetic field
   public static Value webs;
   // $FF: synthetic field
   public static Value cactus;

   public Avoid() {
      super("Avoid", "Avoids interactions with certain things", 0, Category.MISC, true);
      cactus = this.register(new Value("Cactus", this, true));
      fire = this.register(new Value("Fire", this, true));
      lava = this.register(new Value("Lava", this, true));
      webs = this.register(new Value("Webs", this, true));
   }
}
