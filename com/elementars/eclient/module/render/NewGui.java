package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.newgui.NewGUI;
import dev.xulu.newgui.Panel;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class NewGui extends Module {
   // $FF: synthetic field
   public static Value blue;
   // $FF: synthetic field
   public static Value rainbowgui;
   // $FF: synthetic field
   public static Value bgAlpha;
   // $FF: synthetic field
   public static Value red;
   // $FF: synthetic field
   public static Value outline;
   // $FF: synthetic field
   public static Value sliderSetting;
   // $FF: synthetic field
   public static Value customfont;
   // $FF: synthetic field
   public static Value green;
   // $FF: synthetic field
   public static Value moduleSetting;
   // $FF: synthetic field
   public static Value toggleSetting;
   // $FF: synthetic field
   public static Value rainbowspeed;
   // $FF: synthetic field
   public static Value blur;
   // $FF: synthetic field
   public static Value resetGui;

   public NewGui() {
      super("NewGui", "New gui for the client", 21, Category.CORE, false);
      resetGui = this.register(new Value("Reset Gui", this, false));
      customfont = this.register(new Value("Custom Font", this, false));
      rainbowgui = this.register(new Value("Rainbow ClickGui", this, false));
      rainbowspeed = this.register(new Value("Rainbow Speed", this, 20, 1, 50));
      blur = this.register(new Value("Blur", this, true));
      outline = this.register(new Value("Outline", this, true));
      moduleSetting = this.register(new Value("Module Setting", this, "Normal", new ArrayList(Arrays.asList("Normal", "MiniButton", "Text"))));
      toggleSetting = this.register(new Value("Toggle Setting", this, "Full-box", new ArrayList(Arrays.asList("Checkbox", "Full-box"))));
      sliderSetting = this.register(new Value("Slider Setting", this, "Box", new ArrayList(Arrays.asList("Line", "Box"))));
      bgAlpha = this.register(new Value("Background alpha", this, 130, 0, 255));
      red = this.register(new Value("GuiRed", this, 255, 0, 255));
      green = this.register(new Value("GuiGreen", this, 26, 0, 255));
      blue = this.register(new Value("GuiBlue", this, 42, 0, 255));
   }

   public void onEnable() {
      mc.displayGuiScreen(Xulu.newGUI);
      this.toggle();
   }

   public static void resetGui() {
      if ((Boolean)resetGui.getValue()) {
         int var0 = 10;

         for(Iterator var1 = NewGUI.getPanels().iterator(); var1.hasNext(); var0 += 23) {
            Panel var2 = (Panel)var1.next();
            var2.x = 10.0D;
            var2.y = (double)var0;
         }

         resetGui.setValue(false);
      }

   }
}
