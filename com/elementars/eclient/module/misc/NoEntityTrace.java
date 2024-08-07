package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.init.Items;

public class NoEntityTrace extends Module {
   // $FF: synthetic field
   private ArrayList options;
   // $FF: synthetic field
   private final Value pickaxe = this.register(new Value("Pickaxe Only", this, true));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Dynamic", new ArrayList(Arrays.asList("Dynamic", "Static"))));
   // $FF: synthetic field
   private static NoEntityTrace INSTANCE;

   public NoEntityTrace() {
      super("NoEntityTrace", "Keeps mining through an entity", 0, Category.MISC, true);
      INSTANCE = this;
   }

   public static boolean shouldBlock() {
      return INSTANCE.isToggled() && (((String)INSTANCE.mode.getValue()).equalsIgnoreCase("Static") || mc.playerController.isHittingBlock) && (!(Boolean)INSTANCE.pickaxe.getValue() || mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE);
   }
}
