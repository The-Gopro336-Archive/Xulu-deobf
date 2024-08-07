package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class SkinFlicker extends Module {
   // $FF: synthetic field
   private final Value slowness = this.register(new Value("Slowness", this, 2, 1, 5));
   // $FF: synthetic field
   private int len = EnumPlayerModelParts.values().length;
   // $FF: synthetic field
   private static final EnumPlayerModelParts[] PARTS_VERTICAL;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Horizontal", new ArrayList(Arrays.asList("Horizontal", "Vertical", "Random"))));
   // $FF: synthetic field
   private Random r = new Random();
   // $FF: synthetic field
   private static final EnumPlayerModelParts[] PARTS_HORIZONTAL;

   static {
      PARTS_HORIZONTAL = new EnumPlayerModelParts[]{EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE};
      PARTS_VERTICAL = new EnumPlayerModelParts[]{EnumPlayerModelParts.HAT, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.RIGHT_SLEEVE, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG};
   }

   public void onUpdate() {
      if (((String)this.mode.getValue()).equalsIgnoreCase("Random")) {
         if (mc.player.ticksExisted % (Integer)this.slowness.getValue() != 0) {
            return;
         }

         mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.values()[this.r.nextInt(this.len)]);
      } else {
         int var1 = mc.player.ticksExisted / (Integer)this.slowness.getValue() % (PARTS_HORIZONTAL.length * 2);
         boolean var2 = false;
         if (var1 >= PARTS_HORIZONTAL.length) {
            var2 = true;
            var1 -= PARTS_HORIZONTAL.length;
         }

         mc.gameSettings.setModelPartEnabled(((String)this.mode.getValue()).equalsIgnoreCase("Vertical") ? PARTS_VERTICAL[var1] : PARTS_HORIZONTAL[var1], var2);
      }

   }

   public SkinFlicker() {
      super("SkinFlicker", "Toggles skin layers", 0, Category.MISC, true);
   }
}
