package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.settings.Value;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class Compass extends Module {
   // $FF: synthetic field
   public final Value position = this.register(new Value("Y Position", this, 8.0F, 0.0F, 10.0F));
   // $FF: synthetic field
   public final Value axis = this.register(new Value("Axis", this, false));
   // $FF: synthetic field
   private static final double HALF_PI = 1.5707963267948966D;
   // $FF: synthetic field
   public final Value scale = this.register(new Value("Scale", this, 3, 1, 10));
   // $FF: synthetic field
   public final Value xposition = this.register(new Value("X Position", this, 0, -500, 500));

   private double getX(double var1) {
      return Math.sin(var1) * (double)((Integer)this.scale.getValue() * 10);
   }

   private double getY(double var1) {
      double var3 = (double)MathHelper.clamp(mc.player.rotationPitch + 30.0F, -90.0F, 90.0F);
      double var5 = Math.toRadians(var3);
      return Math.cos(var1) * Math.sin(var5) * (double)((Integer)this.scale.getValue() * 10);
   }

   public void onRender() {
      GlStateManager.pushMatrix();
      double var1 = (double)(mc.displayWidth / 4 + (Integer)this.xposition.getValue());
      double var3 = (double)((float)(mc.displayHeight / 2) * ((Float)this.position.getValue() / 10.0F));
      Compass.Direction[] var5 = Compass.Direction.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Compass.Direction var8 = var5[var7];
         double var9 = getPosOnCompass(var8);
         mc.fontRenderer.drawStringWithShadow((Boolean)this.axis.getValue() ? var8.getAlternate() : var8.name(), (float)(var1 + this.getX(var9)), (float)(var3 + this.getY(var9)), var8 == Compass.Direction.N ? ColorUtils.Colors.RED : ColorUtils.Colors.WHITE);
      }

      GlStateManager.popMatrix();
   }

   private static double getPosOnCompass(Compass.Direction var0) {
      double var1 = Math.toRadians((double)MathHelper.wrapDegrees(mc.player.rotationYaw));
      int var3 = var0.ordinal();
      return var1 + (double)var3 * 1.5707963267948966D;
   }

   public Compass() {
      super("Compass", "Credit to fr1kin", 0, Category.RENDER, true);
   }

   private static enum Direction {
      // $FF: synthetic field
      private String alternate;
      // $FF: synthetic field
      E("+X"),
      // $FF: synthetic field
      N("-Z"),
      // $FF: synthetic field
      W("-X"),
      // $FF: synthetic field
      S("+Z");

      private Direction(String var3) {
         this.alternate = var3;
      }

      public String getAlternate() {
         return this.alternate;
      }
   }
}
