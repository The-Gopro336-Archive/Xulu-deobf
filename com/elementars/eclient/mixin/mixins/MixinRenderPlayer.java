package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.Chams;
import com.elementars.eclient.module.render.Nametags;
import com.elementars.eclient.module.render.ViewmodelChanger;
import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderPlayer.class})
public class MixinRenderPlayer {
   @Shadow
   public ModelPlayer getMainModel() {
      return new ModelPlayer(0.0F, false);
   }

   @Inject(
      method = {"renderEntityName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderLivingLabel(AbstractClientPlayer var1, double var2, double var4, double var6, String var8, double var9, CallbackInfo var11) {
      if (Nametags.INSTANCE.isToggled()) {
         var11.cancel();
      }

   }

   @Redirect(
      method = {"renderRightArm"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V"
)
   )
   private void renderRightArm(ModelRenderer var1, float var2) {
      Chams var3 = (Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class);
      Color var4 = (Boolean)var3.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)var3.r.getValue(), (Integer)var3.g.getValue(), (Integer)var3.b.getValue());
      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPushMatrix();
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1.0E7F);
         GL11.glPushAttrib(1048575);
         if (!(Boolean)var3.lines.getValue()) {
            GL11.glPolygonMode(1028, 6914);
         } else {
            GL11.glPolygonMode(1028, 6913);
         }

         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)(Integer)var3.a.getValue() / 255.0F);
         if ((Boolean)var3.lines.getValue()) {
            GL11.glLineWidth((Float)var3.width.getValue());
         }
      }

      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled() && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue()) {
         ModelPlayer var5 = this.getMainModel();
         var5.bipedRightArm.rotateAngleX = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).x.getValue() * 8.0F;
         var5.bipedRightArm.rotateAngleY = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).y.getValue() * 8.0F;
         var5.bipedRightArm.rotateAngleZ = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).z.getValue() * 8.0F;
         var5.bipedRightArm.render(var2);
      } else {
         var1.render(var2);
      }

      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPopAttrib();
         GL11.glPolygonOffset(1.0F, 100000.0F);
         GL11.glDisable(32823);
         GL11.glPopMatrix();
      }

   }

   @Redirect(
      method = {"renderRightArm"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V",
   ordinal = 1
)
   )
   private void renderRightArmWear(ModelRenderer var1, float var2) {
      Chams var3 = (Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class);
      Color var4 = (Boolean)var3.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)var3.r.getValue(), (Integer)var3.g.getValue(), (Integer)var3.b.getValue());
      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPushMatrix();
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1.0E7F);
         GL11.glPushAttrib(1048575);
         if (!(Boolean)var3.lines.getValue()) {
            GL11.glPolygonMode(1028, 6914);
         } else {
            GL11.glPolygonMode(1028, 6913);
         }

         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)(Integer)var3.a.getValue() / 255.0F);
         if ((Boolean)var3.lines.getValue()) {
            GL11.glLineWidth((Float)var3.width.getValue());
         }
      }

      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled() && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue()) {
         ModelPlayer var5 = this.getMainModel();
         var5.bipedRightArmwear.rotateAngleX = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).x.getValue() * 4.0F;
         var5.bipedRightArmwear.rotateAngleY = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).y.getValue() * 4.0F;
         var5.bipedRightArmwear.rotateAngleZ = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).z.getValue() * 4.0F;
         var5.bipedRightArmwear.render(var2);
      } else {
         var1.render(var2);
      }

      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPopAttrib();
         GL11.glPolygonOffset(1.0F, 100000.0F);
         GL11.glDisable(32823);
         GL11.glPopMatrix();
      }

   }

   @Redirect(
      method = {"renderLeftArm"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V"
)
   )
   private void renderLeftArm(ModelRenderer var1, float var2) {
      Chams var3 = (Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class);
      Color var4 = (Boolean)var3.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)var3.r.getValue(), (Integer)var3.g.getValue(), (Integer)var3.b.getValue());
      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPushMatrix();
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1.0E7F);
         GL11.glPushAttrib(1048575);
         if (!(Boolean)var3.lines.getValue()) {
            GL11.glPolygonMode(1028, 6914);
         } else {
            GL11.glPolygonMode(1028, 6913);
         }

         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)(Integer)var3.a.getValue() / 255.0F);
         if ((Boolean)var3.lines.getValue()) {
            GL11.glLineWidth((Float)var3.width.getValue());
         }
      }

      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled() && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue()) {
         ModelPlayer var5 = this.getMainModel();
         var5.bipedLeftArm.rotateAngleX = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).x.getValue() * 8.0F;
         var5.bipedLeftArm.rotateAngleY = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).y.getValue() * 8.0F;
         var5.bipedLeftArm.rotateAngleZ = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).z.getValue() * 8.0F;
         var5.bipedLeftArm.render(var2);
      } else {
         var1.render(var2);
      }

      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPopAttrib();
         GL11.glPolygonOffset(1.0F, 100000.0F);
         GL11.glDisable(32823);
         GL11.glPopMatrix();
      }

   }

   @Redirect(
      method = {"renderLeftArm"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V",
   ordinal = 1
)
   )
   private void renderLeftArmWear(ModelRenderer var1, float var2) {
      Chams var3 = (Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class);
      Color var4 = (Boolean)var3.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)var3.r.getValue(), (Integer)var3.g.getValue(), (Integer)var3.b.getValue());
      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPushMatrix();
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1.0E7F);
         GL11.glPushAttrib(1048575);
         if (!(Boolean)var3.lines.getValue()) {
            GL11.glPolygonMode(1028, 6914);
         } else {
            GL11.glPolygonMode(1028, 6913);
         }

         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)(Integer)var3.a.getValue() / 255.0F);
         if ((Boolean)var3.lines.getValue()) {
            GL11.glLineWidth((Float)var3.width.getValue());
         }
      }

      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled() && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue()) {
         ModelPlayer var5 = this.getMainModel();
         var5.bipedLeftArmwear.rotateAngleX = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).x.getValue() * 8.0F;
         var5.bipedLeftArmwear.rotateAngleY = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).y.getValue() * 8.0F;
         var5.bipedLeftArmwear.rotateAngleZ = (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).z.getValue() * 8.0F;
         var5.bipedLeftArmwear.render(var2);
      } else {
         var1.render(var2);
      }

      if ((Boolean)var3.hand.getValue() && var3.isToggled() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         GL11.glPopAttrib();
         GL11.glPolygonOffset(1.0F, 100000.0F);
         GL11.glDisable(32823);
         GL11.glPopMatrix();
      }

   }
}
