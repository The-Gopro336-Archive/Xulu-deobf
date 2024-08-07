package com.elementars.eclient.module.core;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import dev.xulu.settings.Value;

public class Global extends Module {
   // $FF: synthetic field
   public static Value command2;
   // $FF: synthetic field
   public static Value rainbowAmount;
   // $FF: synthetic field
   public static Value shortShadow;
   // $FF: synthetic field
   public static Value direction;
   // $FF: synthetic field
   public static Value coordinates;
   // $FF: synthetic field
   public static Value rainbowLightness;
   // $FF: synthetic field
   public static Value command1;
   // $FF: synthetic field
   public static Value lagColor;
   // $FF: synthetic field
   public static Value rainbowspeed;
   // $FF: synthetic field
   public static Value textShadow;
   // $FF: synthetic field
   public static Value hudAlpha;
   // $FF: synthetic field
   public static Value rainbowSaturation;
   // $FF: synthetic field
   public static Value command3;
   // $FF: synthetic field
   public static Value rainbowspeed2;
   // $FF: synthetic field
   public static Value showLag;

   public Global() {
      super("Global", "Stores global settings", 0, Category.CORE, false);
      rainbowAmount = this.register(new Value("Gradient Amt", this, 5, 1, 20));
      hudAlpha = this.register(new Value("Hud Alpha", this, 255, 0, 255));
      direction = this.register(new Value("Direction", this, true));
      coordinates = this.register(new Value("Coordinates", this, false));
      showLag = this.register(new Value("Show Lag", this, true));
      lagColor = this.register(new Value("Lag Color", this, "Default", new String[]{"Default", "Gui Color"}));
      this.register(new Value("Rainbow Watermark", this, false));
      this.register(new Value("Hide Potions", this, true));
      rainbowspeed2 = this.register(new Value("Rainbow Speed", this, 5, 1, 100));
      rainbowspeed = this.register(new Value("Block Rainbow Speed", this, 2, 1, 50));
      rainbowSaturation = this.register(new Value("Rainbow Sat.", this, 255, 0, 255));
      rainbowLightness = this.register(new Value("Rainbow Light.", this, 255, 0, 255));
      textShadow = this.register(new Value("Text Shadow", this, true));
      shortShadow = this.register(new Value("Short Shadow", this, false));
      command1 = this.register(new Value("Watermark Color", this, "Purple", ColorTextUtils.colors));
      command2 = this.register(new Value("Bracket Color", this, "Purple", ColorTextUtils.colors));
      command3 = this.register(new Value("Bracket Type", this, "[]", new String[]{"[]", "<>", "()", "{}", "-==-"}));
   }

   public void onEnable() {
      this.disable();
   }
}
