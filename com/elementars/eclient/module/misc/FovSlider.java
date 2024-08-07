package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FovSlider extends Module {
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Fov Changer", new String[]{"Fov Changer", "Hand Changer"}));
   // $FF: synthetic field
   private final Value FOV = this.register(new Value("FOV", this, 110, 90, 200));
   // $FF: synthetic field
   private float fov;

   public void onDisable() {
      EVENT_BUS.unregister(this);
      mc.gameSettings.fovSetting = this.fov;
   }

   @SubscribeEvent
   public void fovOn(FOVModifier var1) {
      if (((String)this.mode.getValue()).equalsIgnoreCase("Hand Changer")) {
         var1.setFOV((float)(Integer)this.FOV.getValue());
      }

   }

   public void onUpdate() {
      if (this.isToggled() && mc.world != null) {
         if (((String)this.mode.getValue()).equalsIgnoreCase("Fov Changer")) {
            mc.gameSettings.fovSetting = (float)(Integer)this.FOV.getValue();
         }

      }
   }

   public void onEnable() {
      EVENT_BUS.register(this);
      this.fov = mc.gameSettings.fovSetting;
   }

   public FovSlider() {
      super("FovSlider", "Better FOV slider", 0, Category.MISC, true);
   }
}
