package com.elementars.eclient.module.player;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.movement.Strafe;
import com.elementars.eclient.util.EntityUtil;
import dev.xulu.settings.Value;

public class FastFall extends Module {
   // $FF: synthetic field
   public final Value webMode = this.register(new Value("Web Mode", this, "Normal", new String[]{"Normal", "2b2t"}));
   // $FF: synthetic field
   public final Value fallMode = this.register(new Value("Fall Mode", this, "Normal", new String[]{"Normal", "2b2t"}));
   // $FF: synthetic field
   int delay;
   // $FF: synthetic field
   public final Value falling = this.register(new Value("Fast Fall", this, true));
   // $FF: synthetic field
   public final Value webs = this.register(new Value("Fast Web", this, true));

   public FastFall() {
      super("FastFall", "Immediately hits terminal velocity when falling", 0, Category.PLAYER, true);
   }

   public void onUpdate() {
      if (this.delay > 0) {
         --this.delay;
      }

      if ((Boolean)this.webs.getValue() && mc.player.isInWeb) {
         mc.player.motionY = ((String)this.webMode.getValue()).equalsIgnoreCase("Normal") ? -3.9200038147008747D : -0.22000000000000003D;
      }

      if ((Boolean)this.falling.getValue()) {
         if (mc.player.motionY > -0.05999999865889549D) {
            this.delay = 20;
         }

         if (mc.player.fallDistance > 0.0F && mc.player.fallDistance < 1.0F && this.delay == 0 && !Xulu.MODULE_MANAGER.getModule(Strafe.class).isToggled() && !EntityUtil.isInWater(mc.player)) {
            mc.player.motionY = ((String)this.fallMode.getValue()).equalsIgnoreCase("Normal") ? -3.9200038147008747D : -0.22000000000000003D;
         }
      }

   }
}
