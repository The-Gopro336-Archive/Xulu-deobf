package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.events.EventModelRender;
import com.elementars.eclient.event.events.EventPostRenderLayers;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {RenderLivingBase.class},
   priority = Integer.MAX_VALUE
)
public abstract class MixinRenderLivingBase {
   @Shadow
   protected ModelBase mainModel;

   @Redirect(
      method = {"renderModel"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"
)
   )
   private void renderModelWrapper(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      EventModelRender var9 = new EventModelRender(var1, var2, var3, var4, var5, var6, var7, var8);
      var9.setState(Event.State.PRE);
      var9.call();
      if (!var9.isCancelled()) {
         var1.render(var2, var3, var4, var5, var6, var7, var8);
         EventModelRender var10 = new EventModelRender(var1, var2, var3, var4, var5, var6, var7, var8);
         var10.setState(Event.State.POST);
         var10.call();
      }
   }

   @Inject(
      method = {"renderLayers"},
      at = {@At("RETURN")}
   )
   public void renderLayers(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, CallbackInfo var9) {
      EventPostRenderLayers var10 = new EventPostRenderLayers((RenderLivingBase)RenderLivingBase.class.cast(this), this.mainModel, var1, var2, var3, var4, var5, var6, var7, var8);
      var10.call();
   }
}
