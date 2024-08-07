package com.elementars.eclient.module.render;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventRenderBlock;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.Triplet;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Search extends Module {
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Default Blue", this, 170, 0, 255));
   // $FF: synthetic field
   private final Value render = this.register(new Value("Render", this, true));
   // $FF: synthetic field
   ICamera camera = new Frustum();
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value renderDistance = this.register(new Value("RenderDistance", this, 50.0F, 1.0F, 100.0F));
   // $FF: synthetic field
   private static final List BLOCKS;
   // $FF: synthetic field
   private final Value tracers = this.register(new Value("Tracers", this, false));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Default Green", this, 130, 0, 255));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Outline", "Full"))));
   // $FF: synthetic field
   private final Value alphaF = this.register(new Value("Full Alpha", this, 100, 0, 255));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Default Red", this, 255, 0, 255));
   // $FF: synthetic field
   public final Map posList = new HashMap();
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 70, 0, 255));

   public static boolean addBlock(String var0) {
      if (Block.getBlockFromName(var0) != null) {
         BLOCKS.add(Block.getBlockFromName(var0));
         return true;
      } else {
         return false;
      }
   }

   public static List getBLOCKS() {
      return BLOCKS;
   }

   public static void drawLine(double var0, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
      Vec3d var12 = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationYaw)));
      drawLineFromPosToPos(var12.x, var12.y + (double)mc.player.getEyeHeight(), var12.z, var0, var2, var4, var6, var8, var9, var10, var11);
   }

   public void onDisable() {
      this.posList.clear();
      mc.renderGlobal.loadRenderers();
   }

   @EventTarget
   public void onRender(EventRenderBlock var1) {
      if (BLOCKS.contains(var1.getBlockState().getBlock())) {
         Vec3d var2 = var1.getBlockState().getOffset(var1.getBlockAccess(), var1.getBlockPos());
         double var3 = (double)var1.getBlockPos().getX() + var2.x;
         double var5 = (double)var1.getBlockPos().getY() + var2.y;
         double var7 = (double)var1.getBlockPos().getZ() + var2.z;
         BlockPos var9 = new BlockPos(var3, var5, var7);
         synchronized(this.posList) {
            this.posList.put(var9, this.getColor(var1.getBlockState().getBlock()));
         }
      }

   }

   public static void drawLineToBlock(BlockPos var0, float var1, float var2, float var3, float var4) {
      Vec3d var5 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      drawLine((double)var0.x - var5.x + 0.5D, (double)var0.y - var5.y + 0.5D, (double)var0.z - var5.z + 0.5D, 0.0D, var1, var2, var3, var4);
   }

   private void drawBlockO(BlockPos var1, int var2, int var3, int var4) {
      IBlockState var5 = mc.world.getBlockState(var1);
      Vec3d var6 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      XuluTessellator.drawBoundingBox(var5.getSelectedBoundingBox(mc.world, var1).offset(-var6.x, -var6.y, -var6.z), 1.5F, var2, var3, var4, (Integer)this.alphaF.getValue());
   }

   public void onEnable() {
      mc.renderGlobal.loadRenderers();
   }

   public Search() {
      super("Search", "ESP for a certain block id", 0, Category.RENDER, true);
   }

   private Triplet getColor(Block var1) {
      if (var1 == Blocks.DIAMOND_ORE) {
         return new Triplet(0, 255, 255);
      } else if (var1 == Blocks.IRON_ORE) {
         return new Triplet(255, 226, 191);
      } else if (var1 == Blocks.GOLD_ORE) {
         return new Triplet(255, 216, 0);
      } else if (var1 == Blocks.COAL_ORE) {
         return new Triplet(35, 35, 35);
      } else if (var1 == Blocks.LAPIS_ORE) {
         return new Triplet(0, 50, 255);
      } else if (var1 == Blocks.PORTAL) {
         return new Triplet(170, 0, 255);
      } else if (var1 == Blocks.EMERALD_ORE) {
         return new Triplet(0, 255, 0);
      } else if (var1 == Blocks.REDSTONE_ORE) {
         return new Triplet(186, 0, 0);
      } else {
         return var1 == Blocks.END_PORTAL_FRAME ? new Triplet(255, 255, 150) : new Triplet(this.red.getValue(), this.green.getValue(), this.blue.getValue());
      }
   }

   public static void drawLineFromPosToPos(double var0, double var2, double var4, double var6, double var8, double var10, double var12, float var14, float var15, float var16, float var17) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var14, var15, var16, var17);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GlStateManager.disableLighting();
      GL11.glLoadIdentity();
      boolean var18 = mc.gameSettings.viewBobbing;
      mc.gameSettings.viewBobbing = false;
      mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
      GL11.glBegin(1);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GlStateManager.enableLighting();
      mc.gameSettings.viewBobbing = var18;
   }

   static {
      BLOCKS = new ArrayList(Arrays.asList(Blocks.PORTAL, Blocks.DIAMOND_ORE));
   }

   private void drawBlock(BlockPos var1, int var2, int var3, int var4) {
      Color var5 = new Color(var2, var3, var4, (Integer)this.alpha.getValue());
      IBlockState var6 = mc.world.getBlockState(var1);
      Vec3d var7 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      XuluTessellator.drawBox2(var6.getSelectedBoundingBox(mc.world, var1).offset(-var7.x, -var7.y, -var7.z), var5.getRGB(), 63);
   }

   public void onWorldRender(RenderEvent var1) {
      double var2 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)var1.getPartialTicks();
      double var4 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)var1.getPartialTicks();
      double var6 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)var1.getPartialTicks();
      this.camera.setPosition(var2, var4, var6);
      if (mc.player != null) {
         if ((Boolean)this.render.getValue()) {
            if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               synchronized(this.posList) {
                  this.posList.forEach((var1x, var2x) -> {
                     if (var1x.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) <= (double)(Float)this.renderDistance.getValue() && this.camera.isBoundingBoxInFrustum(mc.world.getBlockState(var1x).getSelectedBoundingBox(mc.world, var1x))) {
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)var2x.getFirst(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)var2x.getSecond(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)var2x.getThird());
                     }

                  });
               }

               XuluTessellator.release();
            } else if (((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
               synchronized(this.posList) {
                  this.posList.forEach((var1x, var2x) -> {
                     if (var1x.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) <= (double)(Float)this.renderDistance.getValue()) {
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)var2x.getFirst(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)var2x.getSecond(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)var2x.getThird());
                     }

                  });
               }
            } else if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
               synchronized(this.posList) {
                  this.posList.forEach((var1x, var2x) -> {
                     XuluTessellator.prepare(7);
                     if (var1x.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) <= (double)(Float)this.renderDistance.getValue()) {
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)var2x.getFirst(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)var2x.getSecond(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)var2x.getThird());
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)var2x.getFirst(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)var2x.getSecond(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)var2x.getThird());
                     }

                     XuluTessellator.release();
                  });
               }
            }
         }

         if ((Boolean)this.tracers.getValue()) {
            synchronized(this.posList) {
               this.posList.forEach((var1x, var2x) -> {
                  if (var1x.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) <= (double)(Float)this.renderDistance.getValue()) {
                     drawLineToBlock(var1x, (Boolean)this.rainbow.getValue() ? (float)RainbowUtils.r : (float)(Integer)var2x.getFirst(), (Boolean)this.rainbow.getValue() ? (float)RainbowUtils.g : (float)(Integer)var2x.getSecond(), (Boolean)this.rainbow.getValue() ? (float)RainbowUtils.b : (float)(Integer)var2x.getThird(), (float)(Integer)this.alpha.getValue());
                  }

               });
            }
         }

      }
   }

   public static boolean delBlock(String var0) {
      if (Block.getBlockFromName(var0) != null) {
         BLOCKS.remove(Block.getBlockFromName(var0));
         return true;
      } else {
         return false;
      }
   }
}
