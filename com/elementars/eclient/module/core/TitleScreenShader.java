package com.elementars.eclient.module.core;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.GLSLShaders;
import dev.xulu.settings.Value;

public class TitleScreenShader extends Module {
   // $FF: synthetic field
   public static Value fps;
   // $FF: synthetic field
   public final Value mode = this.register(new Value("Mode", this, "Random", new String[]{"Random", "Select"}));
   // $FF: synthetic field
   public final Value shader;

   public TitleScreenShader() {
      super("TitleScreenShader", "Displays cool graphics for the main menu background", 0, Category.CORE, false);
      this.shader = this.register(new Value("Shader", this, GLSLShaders.ICYFIRE, GLSLShaders.values()));
      fps = this.register(new Value("FPS", this, 60, 5, 60));
   }
}
