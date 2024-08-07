package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.misc.AntiSound;
import com.elementars.eclient.module.render.StorageESP;
import com.elementars.eclient.util.OutlineUtils2;
import com.elementars.eclient.util.Wrapper;
import java.util.Iterator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {RenderGlobal.class},
   priority = 9999
)
public class MixinRenderGlobal {
   @Shadow
   public ShaderGroup entityOutlineShader;
   @Shadow
   public boolean entityOutlinesRendered;
   @Shadow
   public WorldClient world;

   @Redirect(
      method = {"broadcastSound"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V"
)
   )
   private void playWitherSpawn(WorldClient var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11, boolean var12) {
      if (!Xulu.MODULE_MANAGER.getModule(AntiSound.class).isToggled() || !(Boolean)((AntiSound)Xulu.MODULE_MANAGER.getModuleT(AntiSound.class)).witherSpawn.getValue()) {
         this.world.playSound(var2, var4, var6, var8, var9, var10, var11, var12);
      }

   }

   @Redirect(
      method = {"playEvent"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;playSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V",
   ordinal = 22
)
   )
   private void playWitherShoot(WorldClient var1, BlockPos var2, SoundEvent var3, SoundCategory var4, float var5, float var6, boolean var7) {
      if (!Xulu.MODULE_MANAGER.getModule(AntiSound.class).isToggled() || !(Boolean)((AntiSound)Xulu.MODULE_MANAGER.getModuleT(AntiSound.class)).wither.getValue()) {
         this.world.playSound(var2, var3, var4, var5, var6, var7);
      }

   }

   @Inject(
      method = {"renderEntities"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/RenderGlobal;preRenderDamagedBlocks()V",
   shift = At.Shift.BEFORE
)}
   )
   public void renderEntities(Entity var1, ICamera var2, float var3, CallbackInfo var4) {
      if (Xulu.MODULE_MANAGER.getModuleByName("StorageESP") != null && Xulu.MODULE_MANAGER.getModuleByName("StorageESP").isToggled() && ((String)Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleByName("StorageESP"), "Mode").getValue()).equalsIgnoreCase("Shader")) {
         StorageESP var5 = (StorageESP)Xulu.MODULE_MANAGER.getModuleByName("StorageESP");
         StorageESP.renderNormal(var3);
         OutlineUtils2.VZWQ((Float)Xulu.VALUE_MANAGER.getValueByMod(var5, "Line Width").getValue());
         StorageESP.renderNormal(var3);
         OutlineUtils2.JLYv();
         StorageESP.renderColor(var3);
         OutlineUtils2.feKn();
         OutlineUtils2.mptE((EntityLivingBase)null);
         StorageESP.renderColor(var3);
         OutlineUtils2.VdOT();
      }

   }

   public void renderNormal(float var1) {
      RenderHelper.enableStandardItemLighting();
      Iterator var2 = Wrapper.getMinecraft().world.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         GL11.glPushMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Wrapper.getMinecraft().renderGlobal.renderManager.renderEntity(var3, var3.posX - Wrapper.getMinecraft().renderManager.renderPosX, var3.posY - Wrapper.getMinecraft().renderManager.renderPosY, var3.posZ - Wrapper.getMinecraft().renderManager.renderPosZ, var3.rotationYaw, var1, false);
         GL11.glPopMatrix();
      }

   }
}
