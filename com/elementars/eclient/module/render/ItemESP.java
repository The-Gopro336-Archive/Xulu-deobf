package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.OutlineUtils2;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Timer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ItemESP extends Module {
   // $FF: synthetic field
   public static ItemESP INSTANCE;
   // $FF: synthetic field
   private final Value pblue = this.register(new Value("Pearl Blue", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value xpbottles = this.register(new Value("EXP Bottles", this, true));
   // $FF: synthetic field
   private final Value items = this.register(new Value("Items", this, true));
   // $FF: synthetic field
   public final Value mode = this.register(new Value("Mode", this, "Box", new ArrayList(Arrays.asList("Box", "Text", "Shader", "Chams"))));
   // $FF: synthetic field
   private final Value outlinewidth = this.register(new Value("Shader Width", this, 1, 1, 10));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 80, 0, 255));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value palpha = this.register(new Value("Pearl Alpha", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value outline;
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value alphaF = this.register(new Value("Full Alpha", this, 80, 0, 255));
   // $FF: synthetic field
   private final Value width;
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value pred = this.register(new Value("Pearl Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value pearls = this.register(new Value("Pearls", this, true));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value pgreen = this.register(new Value("Pearl Green", this, 255, 0, 255));

   public void onWorldRender(RenderEvent var1) {
      int var2;
      int var3;
      int var4;
      if ((Boolean)this.rainbow.getValue()) {
         var2 = RainbowUtils.r;
         var3 = RainbowUtils.g;
         var4 = RainbowUtils.b;
      } else {
         var2 = (Integer)this.red.getValue();
         var3 = (Integer)this.green.getValue();
         var4 = (Integer)this.blue.getValue();
      }

      XuluTessellator.prepare(7);
      mc.world.loadedEntityList.stream().filter((var1x) -> {
         return var1x instanceof EntityItem && (Boolean)this.items.getValue() || var1x instanceof EntityExpBottle && (Boolean)this.xpbottles.getValue() || var1x instanceof EntityEnderPearl && (Boolean)this.pearls.getValue();
      }).forEach((var4x) -> {
         if (((String)this.mode.getValue()).equalsIgnoreCase("text")) {
            this.drawText(var4x);
         } else if (((String)this.mode.getValue()).equalsIgnoreCase("shader")) {
            var4x.setGlowing(true);
         } else if (!((String)this.mode.getValue()).equalsIgnoreCase("chams")) {
            var4x.setGlowing(false);
            RenderManager var5 = mc.renderManager;
            Timer var6 = mc.timer;
            double var7 = var4x.lastTickPosX + (var4x.posX - var4x.lastTickPosX) * (double)var6.renderPartialTicks - var5.renderPosX;
            double var9 = var4x.lastTickPosY + (var4x.posY - var4x.lastTickPosY) * (double)var6.renderPartialTicks - var5.renderPosY;
            double var11 = var4x.lastTickPosZ + (var4x.posZ - var4x.lastTickPosZ) * (double)var6.renderPartialTicks - var5.renderPosZ;
            AxisAlignedBB var13 = var4x.getEntityBoundingBox();
            AxisAlignedBB var14 = new AxisAlignedBB(var13.minX - var4x.posX + var7 - 0.05D, var13.minY - var4x.posY + var9, var13.minZ - var4x.posZ + var11 - 0.05D, var13.maxX - var4x.posX + var7 + 0.05D, var13.maxY - var4x.posY + var9 + 0.15D, var13.maxZ - var4x.posZ + var11 + 0.05D);
            if (var4x instanceof EntityEnderPearl) {
               if ((Boolean)this.rainbow.getValue()) {
                  switch((ItemESP.RenderMode)this.outline.getValue()) {
                  case SOLID:
                     XuluTessellator.drawBox2(var14, RainbowUtils.r, RainbowUtils.g, RainbowUtils.b, (Integer)this.palpha.getValue(), 63);
                     break;
                  case OUTLINE:
                     XuluTessellator.drawBoundingBox(var14, (float)(Integer)this.width.getValue(), RainbowUtils.r, RainbowUtils.g, RainbowUtils.b, (Integer)this.palpha.getValue());
                     break;
                  case FULL:
                     XuluTessellator.drawFullBoxAA(var14, (float)(Integer)this.width.getValue(), RainbowUtils.r, RainbowUtils.g, RainbowUtils.b, (Integer)this.palpha.getValue(), (Integer)this.alphaF.getValue());
                  }
               } else {
                  switch((ItemESP.RenderMode)this.outline.getValue()) {
                  case SOLID:
                     XuluTessellator.drawBox2(var14, (Integer)this.pred.getValue(), (Integer)this.pgreen.getValue(), (Integer)this.pblue.getValue(), (Integer)this.palpha.getValue(), 63);
                     break;
                  case OUTLINE:
                     XuluTessellator.drawBoundingBox(var14, (float)(Integer)this.width.getValue(), (Integer)this.pred.getValue(), (Integer)this.pgreen.getValue(), (Integer)this.pblue.getValue(), (Integer)this.palpha.getValue());
                     break;
                  case FULL:
                     XuluTessellator.drawFullBoxAA(var14, (float)(Integer)this.width.getValue(), (Integer)this.pred.getValue(), (Integer)this.pgreen.getValue(), (Integer)this.pblue.getValue(), (Integer)this.palpha.getValue(), (Integer)this.alphaF.getValue());
                  }
               }
            } else {
               switch((ItemESP.RenderMode)this.outline.getValue()) {
               case SOLID:
                  XuluTessellator.drawBox2(var14, var2, var3, var4, (Integer)this.alpha.getValue(), 63);
                  break;
               case OUTLINE:
                  XuluTessellator.drawBoundingBox(var14, (float)(Integer)this.width.getValue(), var2, var3, var4, (Integer)this.alpha.getValue());
                  break;
               case FULL:
                  XuluTessellator.drawFullBoxAA(var14, (float)(Integer)this.width.getValue(), var2, var3, var4, (Integer)this.alpha.getValue(), (Integer)this.alphaF.getValue());
               }
            }

         }
      });
      XuluTessellator.release();
   }

   public ItemESP() {
      super("ItemESP", "Highlights items", 0, Category.RENDER, true);
      this.outline = this.register(new Value("Render Mode", this, ItemESP.RenderMode.SOLID, ItemESP.RenderMode.values()));
      this.width = this.register(new Value("Width", this, 1, 1, 10));
      INSTANCE = this;
   }

   public void onDisable() {
      mc.world.loadedEntityList.stream().filter((var1) -> {
         return var1 instanceof EntityItem && (Boolean)this.items.getValue() || var1 instanceof EntityExpBottle && (Boolean)this.xpbottles.getValue() || var1 instanceof EntityEnderPearl && (Boolean)this.pearls.getValue();
      }).forEach((var0) -> {
         var0.setGlowing(false);
      });
   }

   public void render1(float var1) {
      RenderHelper.enableStandardItemLighting();
      Iterator var2 = mc.world.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if (var3 instanceof EntityItem) {
            EntityItem var4 = (EntityItem)var3;
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getRenderManager().renderShadow = false;
            mc.getRenderManager().renderEntityStatic(var4, var1, true);
            GL11.glPopMatrix();
         }
      }

   }

   private void drawText(Entity var1) {
      GlStateManager.pushMatrix();
      double var2 = 1.0D;
      String var4 = var1 instanceof EntityItem ? ((EntityItem)var1).getItem().getDisplayName() : (var1 instanceof EntityEnderPearl ? "Thrown Ender Pearl" : (var1 instanceof EntityExpBottle ? "Thrown Exp Bottle" : "null"));
      Vec3d var5 = EntityUtil.getInterpolatedRenderPos(var1, mc.getRenderPartialTicks());
      float var6 = var1.height / 2.0F + 0.5F;
      double var7 = var5.x;
      double var9 = var5.y + (double)var6;
      double var11 = var5.z;
      float var13 = mc.getRenderManager().playerViewY;
      float var14 = mc.getRenderManager().playerViewX;
      boolean var15 = mc.getRenderManager().options.thirdPersonView == 2;
      GlStateManager.translate(var7, var9, var11);
      GlStateManager.rotate(-var13, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((float)(var15 ? -1 : 1) * var14, 1.0F, 0.0F, 0.0F);
      float var16 = mc.player.getDistance(var1);
      float var17 = var16 / 8.0F * (float)Math.pow(1.258925437927246D, var2);
      GlStateManager.scale(var17, var17, var17);
      FontRenderer var18 = mc.fontRenderer;
      GlStateManager.scale(-0.025F, -0.025F, 0.025F);
      String var19 = String.valueOf((new StringBuilder()).append(var4).append(var1 instanceof EntityItem ? String.valueOf((new StringBuilder()).append(" x").append(((EntityItem)var1).getItem().getCount())) : ""));
      int var20 = var18.getStringWidth(var19) / 2;
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
      if (Xulu.CustomFont) {
         Xulu.cFontRenderer.drawStringWithShadow(var19, (double)(-var20), 9.0D, ColorUtils.Colors.WHITE);
      } else {
         GlStateManager.enableTexture2D();
         var18.drawStringWithShadow(var19, (float)(-var20), 9.0F, ColorUtils.Colors.WHITE);
         GlStateManager.disableTexture2D();
      }

      GlStateManager.glNormal3f(0.0F, 0.0F, 0.0F);
      GlStateManager.popMatrix();
   }

   public void render2(float var1) {
      RenderHelper.enableStandardItemLighting();
      int var2;
      int var3;
      int var4;
      if ((Boolean)this.rainbow.getValue()) {
         var2 = RainbowUtils.r;
         var3 = RainbowUtils.g;
         var4 = RainbowUtils.b;
      } else {
         var2 = (Integer)this.red.getValue();
         var3 = (Integer)this.green.getValue();
         var4 = (Integer)this.blue.getValue();
      }

      Iterator var5 = mc.world.loadedEntityList.iterator();

      while(var5.hasNext()) {
         Entity var6 = (Entity)var5.next();
         if (var6 instanceof EntityItem) {
            EntityItem var7 = (EntityItem)var6;
            GlStateManager.pushMatrix();
            OutlineUtils2.setColor(new Color(var2, var3, var4));
            mc.getRenderManager().renderShadow = false;
            mc.getRenderManager().renderEntityStatic(var7, var1, true);
            GlStateManager.popMatrix();
         }
      }

   }

   private static enum RenderMode {
      // $FF: synthetic field
      FULL,
      // $FF: synthetic field
      SOLID,
      // $FF: synthetic field
      OUTLINE;
   }
}
