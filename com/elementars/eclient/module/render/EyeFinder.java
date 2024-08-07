package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.opengl.GL11;

public class EyeFinder extends Module {
   // $FF: synthetic field
   private final Value players = this.register(new Value("Players", this, true));
   // $FF: synthetic field
   private final Value mobs = this.register(new Value("Mobs", this, false));
   // $FF: synthetic field
   private final Value animals = this.register(new Value("Animals", this, false));

   public EyeFinder() {
      super("EyeFinder", "Draws line at an entity's eyes", 0, Category.RENDER, true);
   }

   private void drawLine(EntityLivingBase var1) {
      RayTraceResult var2 = var1.rayTrace(6.0D, Minecraft.getMinecraft().getRenderPartialTicks());
      if (var2 != null) {
         Vec3d var3 = var1.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());
         GlStateManager.enableDepth();
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         double var4 = var3.x - mc.getRenderManager().renderPosX;
         double var6 = var3.y - mc.getRenderManager().renderPosY;
         double var8 = var3.z - mc.getRenderManager().renderPosZ;
         double var10 = var2.hitVec.x - mc.getRenderManager().renderPosX;
         double var12 = var2.hitVec.y - mc.getRenderManager().renderPosY;
         double var14 = var2.hitVec.z - mc.getRenderManager().renderPosZ;
         GL11.glColor4f(0.2F, 0.1F, 0.3F, 0.8F);
         GlStateManager.glLineWidth(1.5F);
         GL11.glBegin(1);
         GL11.glVertex3d(var4, var6, var8);
         GL11.glVertex3d(var10, var12, var14);
         GL11.glVertex3d(var10, var12, var14);
         GL11.glVertex3d(var10, var12, var14);
         GL11.glEnd();
         if (var2.typeOfHit == Type.BLOCK) {
            XuluTessellator.prepare(7);
            GL11.glEnable(2929);
            BlockPos var16 = var2.getBlockPos();
            float var17 = (float)var16.x - 0.01F;
            float var18 = (float)var16.y - 0.01F;
            float var19 = (float)var16.z - 0.01F;
            XuluTessellator.drawBox(XuluTessellator.getBufferBuilder(), var17, var18, var19, 1.01F, 1.01F, 1.01F, 51, 25, 73, 200, 63);
            XuluTessellator.release();
         }

         GlStateManager.enableTexture2D();
         GlStateManager.enableLighting();
      }
   }

   public void onWorldRender(RenderEvent var1) {
      mc.world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter((var0) -> {
         return mc.player != var0;
      }).map((var0) -> {
         return (EntityLivingBase)var0;
      }).filter((var0) -> {
         return !var0.isDead;
      }).filter((var1x) -> {
         return (Boolean)this.players.getValue() && var1x instanceof EntityPlayer || EntityUtil.isPassive(var1x) ? (Boolean)this.animals.getValue() : (Boolean)this.mobs.getValue();
      }).forEach(this::drawLine);
   }
}
