package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.events.EventModelPlayerRender;
import com.elementars.eclient.module.render.Skeleton;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {ModelPlayer.class},
   priority = 9999
)
public class MixinModelPlayer {
   @Shadow
   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
   }

   @Inject(
      method = {"setRotationAngles"},
      at = {@At("RETURN")}
   )
   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7, CallbackInfo var8) {
      if (Wrapper.getMinecraft().world != null && Wrapper.getMinecraft().player != null && var7 instanceof EntityPlayer) {
         Skeleton.addEntity((EntityPlayer)var7, (ModelPlayer)this);
      }

   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderPre(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7, CallbackInfo var8) {
      EventModelPlayerRender var9 = new EventModelPlayerRender((ModelBase)ModelPlayer.class.cast(this), var1, var2, var3, var4, var5, var6, var7);
      var9.setState(Event.State.PRE);
      var9.call();
      if (var9.isCancelled()) {
         var8.cancel();
      }

   }

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   private void renderPost(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7, CallbackInfo var8) {
      EventModelPlayerRender var9 = new EventModelPlayerRender((ModelBase)ModelPlayer.class.cast(this), var1, var2, var3, var4, var5, var6, var7);
      var9.setState(Event.State.POST);
      var9.call();
   }
}
