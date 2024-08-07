package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.settings.Value;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ESP extends Module {
   // $FF: synthetic field
   private final Value players = this.register(new Value("Players", this, true));
   // $FF: synthetic field
   private final Value mobs = this.register(new Value("Mobs", this, false));
   // $FF: synthetic field
   private final Value animals = this.register(new Value("Animals", this, false));

   public ESP() {
      super("ESP", "Highlights entities", 0, Category.RENDER, true);
   }

   public void onWorldRender(RenderEvent var1) {
      if (Wrapper.getMinecraft().getRenderManager().options != null) {
         boolean var2 = Wrapper.getMinecraft().getRenderManager().options.thirdPersonView == 2;
         float var3 = Wrapper.getMinecraft().getRenderManager().playerViewY;
         mc.world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter((var0) -> {
            return mc.player != var0;
         }).map((var0) -> {
            return (EntityLivingBase)var0;
         }).filter((var0) -> {
            return !var0.isDead;
         }).filter((var1x) -> {
            return (Boolean)this.players.getValue() && var1x instanceof EntityPlayer || EntityUtil.isPassive(var1x) ? (Boolean)this.animals.getValue() : (Boolean)this.mobs.getValue();
         }).forEach((var3x) -> {
            GlStateManager.pushMatrix();
            Vec3d var4 = EntityUtil.getInterpolatedPos(var3x, var1.getPartialTicks());
            GlStateManager.translate(var4.x - mc.getRenderManager().renderPosX, var4.y - mc.getRenderManager().renderPosY, var4.z - mc.getRenderManager().renderPosZ);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-var3, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(var2 ? -1 : 1), 1.0F, 0.0F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
            if (var3x instanceof EntityPlayer) {
               GL11.glColor3f(1.0F, 1.0F, 1.0F);
            } else if (EntityUtil.isPassive(var3x)) {
               GL11.glColor3f(0.11F, 0.9F, 0.11F);
            } else {
               GL11.glColor3f(0.9F, 0.1F, 0.1F);
            }

            GlStateManager.disableTexture2D();
            GL11.glLineWidth(2.0F);
            GL11.glEnable(2848);
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-var3x.width / 2.0F), 0.0D);
            GL11.glVertex2d((double)(-var3x.width / 2.0F), (double)var3x.height);
            GL11.glVertex2d((double)(var3x.width / 2.0F), (double)var3x.height);
            GL11.glVertex2d((double)(var3x.width / 2.0F), 0.0D);
            GL11.glEnd();
            GlStateManager.popMatrix();
         });
         GlStateManager.enableDepth();
         GlStateManager.depthMask(true);
         GlStateManager.disableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.shadeModel(7425);
         GlStateManager.disableDepth();
         GlStateManager.enableCull();
         GlStateManager.glLineWidth(1.0F);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
      }
   }
}
