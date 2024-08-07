package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.clickgui.ClickGui;
import dev.xulu.settings.Value;

public class ExeterGui extends Module {
   // $FF: synthetic field
   private final Value rainbowspeed = this.register(new Value("Rainbow Speed", this, 20, 1, 50));
   // $FF: synthetic field
   private final Value customFont = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private final Value sound = this.register(new Value("Sound", this, true));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));

   public ExeterGui() {
      super("ExeterGui", "Exeter Gui", 0, Category.CORE, true);
   }

   public static int getSpeed() {
      return (Integer)((ExeterGui)Xulu.MODULE_MANAGER.getModuleT(ExeterGui.class)).rainbowspeed.getValue();
   }

   public static boolean getRainbow() {
      return (Boolean)((ExeterGui)Xulu.MODULE_MANAGER.getModuleT(ExeterGui.class)).rainbow.getValue();
   }

   public static boolean getSound() {
      return (Boolean)((ExeterGui)Xulu.MODULE_MANAGER.getModuleT(ExeterGui.class)).sound.getValue();
   }

   public static boolean getCF() {
      return (Boolean)((ExeterGui)Xulu.MODULE_MANAGER.getModuleT(ExeterGui.class)).customFont.getValue();
   }

   public void onEnable() {
      super.onEnable();
      mc.displayGuiScreen(ClickGui.getClickGui());
      this.toggle();
   }
}
