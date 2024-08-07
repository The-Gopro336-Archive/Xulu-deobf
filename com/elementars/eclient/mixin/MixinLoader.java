package com.elementars.eclient.mixin;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class MixinLoader implements IFMLLoadingPlugin {
   private static boolean isObfuscatedEnvironment = false;

   public MixinLoader() {
      MixinBootstrap.init();
      Mixins.addConfiguration("mixins.eclient.json");
      MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
   }

   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map var1) {
      isObfuscatedEnvironment = (Boolean)var1.get("runtimeDeobfuscationEnabled");
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
