package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.Chams;
import com.elementars.eclient.module.render.OutlineESP;
import com.elementars.eclient.util.OutlineUtils;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
   @Shadow
   public ModelBase modelEnderCrystal;
   @Shadow
   public ModelBase modelEnderCrystalNoBase;
   @Final
   @Shadow
   private static ResourceLocation ENDER_CRYSTAL_TEXTURES;

   @Shadow
   public abstract void doRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

   @Redirect(
      method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"
)
   )
   private void render1(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!((Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class)).isToggled() || !(Boolean)Chams.crystals.getValue() || !((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         if (!((OutlineESP)Xulu.MODULE_MANAGER.getModuleT(OutlineESP.class)).isToggled() || (Boolean)((OutlineESP)Xulu.MODULE_MANAGER.getModuleT(OutlineESP.class)).renderCrystals.getValue()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
         }

      }
   }

   @Redirect(
      method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V",
   ordinal = 1
)
   )
   private void render2(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!((Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class)).isToggled() || !(Boolean)Chams.crystals.getValue() || !((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
         if (!((OutlineESP)Xulu.MODULE_MANAGER.getModuleT(OutlineESP.class)).isToggled() || (Boolean)((OutlineESP)Xulu.MODULE_MANAGER.getModuleT(OutlineESP.class)).renderCrystals.getValue()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
         }

      }
   }

   @Inject(
      method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void IdoRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      OutlineESP var11 = (OutlineESP)Xulu.MODULE_MANAGER.getModuleT(OutlineESP.class);
      Chams var12 = (Chams)Xulu.MODULE_MANAGER.getModuleT(Chams.class);
      if (var11 != null && var12 != null) {
         float var13;
         float var14;
         if (var12.isToggled() && (Boolean)Chams.crystals.getValue() && ((String)Chams.mode.getValue()).equalsIgnoreCase("ESP")) {
            Color var16 = (Boolean)var12.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)var12.r.getValue(), (Integer)var12.g.getValue(), (Integer)var12.b.getValue());
            GL11.glPushMatrix();
            var14 = (float)var1.innerRotation + var9;
            GlStateManager.translate(var2, var4, var6);
            Wrapper.getMinecraft().renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            float var15 = MathHelper.sin(var14 * 0.2F) / 2.0F + 0.5F;
            var15 += var15 * var15;
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1.0E7F);
            GL11.glPushAttrib(1048575);
            if (!(Boolean)var12.lines.getValue()) {
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
            GL11.glColor4f((float)var16.getRed() / 255.0F, (float)var16.getGreen() / 255.0F, (float)var16.getBlue() / 255.0F, (float)(Integer)var12.a.getValue() / 255.0F);
            if ((Boolean)var12.lines.getValue()) {
               GL11.glLineWidth((Float)var12.width.getValue());
            }

            if (var1.shouldShowBottom()) {
               this.modelEnderCrystal.render(var1, 0.0F, var14 * 3.0F, var15 * 0.2F, 0.0F, 0.0F, 0.0625F);
            } else {
               this.modelEnderCrystalNoBase.render(var1, 0.0F, var14 * 3.0F, var15 * 0.2F, 0.0F, 0.0F, 0.0625F);
            }

            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0F, 100000.0F);
            GL11.glDisable(32823);
            GL11.glPopMatrix();
         } else if (var12.isToggled() && (Boolean)Chams.crystals.getValue() && ((String)Chams.mode.getValue()).equalsIgnoreCase("Walls")) {
            GL11.glPushMatrix();
            var13 = (float)var1.innerRotation + var9;
            GlStateManager.translate(var2, var4, var6);
            Wrapper.getMinecraft().renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            var14 = MathHelper.sin(var13 * 0.2F) / 2.0F + 0.5F;
            var14 += var14 * var14;
            GL11.glDisable(2929);
            GL11.glColor4f((float)(Integer)var12.Wr.getValue() / 255.0F, (float)(Integer)var12.Wg.getValue() / 255.0F, (float)(Integer)var12.Wb.getValue() / 255.0F, 1.0F);
            GL11.glDisable(3553);
            if (var1.shouldShowBottom()) {
               this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
            } else {
               this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
            }

            GL11.glEnable(2929);
            GL11.glColor4f((float)(Integer)var12.Vr.getValue() / 255.0F, (float)(Integer)var12.Vg.getValue() / 255.0F, (float)(Integer)var12.Vb.getValue() / 255.0F, 1.0F);
            if (var1.shouldShowBottom()) {
               this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
            } else {
               this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
            }

            GL11.glEnable(3553);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
         }

         if (((String)var11.mode.getValue()).equalsIgnoreCase("Shader") && (Boolean)var11.crystals.getValue() && var11.isToggled()) {
            var1.setGlowing(true);
         } else {
            var1.setGlowing(false);
            if ((Boolean)var11.crystals.getValue() && var11.isToggled()) {
               if (((String)var11.mode.getValue()).equalsIgnoreCase("Outline")) {
                  var13 = (float)var1.innerRotation + var9;
                  GlStateManager.pushMatrix();
                  GlStateManager.translate(var2, var4, var6);
                  Wrapper.getMinecraft().renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
                  var14 = MathHelper.sin(var13 * 0.2F) / 2.0F + 0.5F;
                  var14 += var14 * var14;
                  GL11.glLineWidth(5.0F);
                  if (var1.shouldShowBottom()) {
                     this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  } else {
                     this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  }

                  OutlineUtils.renderOne((Float)var11.width.getValue());
                  if (var1.shouldShowBottom()) {
                     this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  } else {
                     this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  }

                  OutlineUtils.renderTwo();
                  if (var1.shouldShowBottom()) {
                     this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  } else {
                     this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  }

                  OutlineUtils.renderThree();
                  OutlineUtils.renderFour(((String)var11.color.getValue()).equalsIgnoreCase("Rainbow") ? new Color(Xulu.rgb) : ColorUtil.getClickGUIColor());
                  if (var1.shouldShowBottom()) {
                     this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  } else {
                     this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  }

                  OutlineUtils.renderFive();
                  GlStateManager.popMatrix();
               } else {
                  var13 = (float)var1.innerRotation + var9;
                  GlStateManager.pushMatrix();
                  GlStateManager.translate(var2, var4, var6);
                  Wrapper.getMinecraft().renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
                  var14 = MathHelper.sin(var13 * 0.2F) / 2.0F + 0.5F;
                  var14 += var14 * var14;
                  GL11.glPushAttrib(1048575);
                  GL11.glPolygonMode(1032, 6913);
                  GL11.glDisable(3553);
                  GL11.glDisable(2896);
                  GL11.glDisable(2929);
                  GL11.glEnable(2848);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  OutlineUtils.setColor(ColorUtil.getClickGUIColor());
                  GL11.glLineWidth((Float)var11.width.getValue());
                  if (var1.shouldShowBottom()) {
                     this.modelEnderCrystal.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  } else {
                     this.modelEnderCrystalNoBase.render(var1, 0.0F, var13 * 3.0F, var14 * 0.2F, 0.0F, 0.0F, 0.0625F);
                  }

                  GL11.glPopAttrib();
                  GL11.glPopMatrix();
               }
            }
         }

      }
   }
}
