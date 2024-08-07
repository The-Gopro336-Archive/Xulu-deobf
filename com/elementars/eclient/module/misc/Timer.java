package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class Timer extends Module {
   // $FF: synthetic field
   private final Value tickSpeed = this.register(new Value("Speed", this, 4.0F, 0.0F, 10.0F));

   public Timer() {
      super("Timer", "Modifies the game speed", 0, Category.MISC, true);
   }

   public void onUpdate() {
      mc.timer.tickLength = 50.0F / ((Float)this.tickSpeed.getValue() == 0.0F ? 0.1F : (Float)this.tickSpeed.getValue());
   }

   public void onDisable() {
      mc.timer.tickLength = 50.0F;
   }
}
