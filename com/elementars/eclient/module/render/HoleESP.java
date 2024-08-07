package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.combat.AutoCrystal;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.EnumUtil;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.Pair;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class HoleESP extends Module {
   // $FF: synthetic field
   int holes;
   // $FF: synthetic field
   private final Value obiGreen = this.register(new Value("ObiGreen", this, 12, 0, 255));
   // $FF: synthetic field
   private final Value hideOwn = this.register(new Value("HideOwn", this, false));
   // $FF: synthetic field
   private final Value drawMode = this.register(new Value("DrawMode", this, "Solid", EnumUtil.enumConverter(HoleESP.Modes.class)));
   // $FF: synthetic field
   ICamera camera = new Frustum();
   // $FF: synthetic field
   private final Value brockBlue = this.register(new Value("BrockBlue", this, 104, 0, 255));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 169, 0, 255));
   // $FF: synthetic field
   private final Value maxHoles = this.register(new Value("Maximum Num", this, 8, 1, 100));
   // $FF: synthetic field
   private final Value renderMode = this.register(new Value("RenderMode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Flat"))));
   // $FF: synthetic field
   private final Value renderDistance = this.register(new Value("RenderDistance", this, 8.0F, 1.0F, 32.0F));
   // $FF: synthetic field
   private final Value brockRed = this.register(new Value("BrockRed", this, 81, 0, 255));
   // $FF: synthetic field
   private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
   // $FF: synthetic field
   private final Value obiBlue = this.register(new Value("ObiBlue", this, 35, 0, 255));
   // $FF: synthetic field
   private final Value cuboid = this.register(new Value("Cuboid Height", this, 0.9F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value obiRed = this.register(new Value("ObiRed", this, 104, 0, 255));
   // $FF: synthetic field
   private ConcurrentHashMap safeHoles;
   // $FF: synthetic field
   private final Value holeMode = this.register(new Value("Hole Mode", this, "Both", new String[]{"Bedrock", "Obsidian", "Both"}));
   // $FF: synthetic field
   private final Value frustum = this.register(new Value("Frustum", this, true));
   // $FF: synthetic field
   private final Value brockGreen = this.register(new Value("BrockGreen", this, 12, 0, 255));
   // $FF: synthetic field
   private final Value future = this.register(new Value("Future Mode", this, false));
   // $FF: synthetic field
   private final Value alpha2 = this.register(new Value("Outline Alpha", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value max = this.register(new Value("Maximum Holes", this, false));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value offset = this.register(new Value("Offset Lower", this, false));

   public void onWorldRender(RenderEvent var1) {
      if (mc.player != null && this.safeHoles != null) {
         if (!this.safeHoles.isEmpty()) {
            if (((String)this.drawMode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               this.safeHoles.forEach((var1x, var2) -> {
                  if ((Boolean)this.offset.getValue()) {
                     var1x = var1x.add(0, -1, 0);
                  }

                  if ((Boolean)var2.getKey()) {
                     this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                  } else {
                     this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                  }

               });
               XuluTessellator.release();
            } else if (((String)this.drawMode.getValue()).equalsIgnoreCase("Outline")) {
               this.safeHoles.forEach((var1x, var2) -> {
                  if ((Boolean)this.offset.getValue()) {
                     var1x = var1x.add(0, -1, 0);
                  }

                  if (((String)this.renderMode.getValue()).equalsIgnoreCase("Solid")) {
                     if ((Boolean)var2.getKey()) {
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                     } else {
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                     }
                  } else if (((String)this.renderMode.getValue()).equalsIgnoreCase("Flat")) {
                     if ((Boolean)var2.getKey()) {
                        this.drawBlockOF(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                     } else {
                        this.drawBlockOF(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                     }
                  }

               });
            } else if (((String)this.drawMode.getValue()).equalsIgnoreCase("Full")) {
               this.safeHoles.forEach((var1x, var2) -> {
                  if ((Boolean)this.offset.getValue()) {
                     var1x = var1x.add(0, -1, 0);
                  }

                  if (((String)this.renderMode.getValue()).equalsIgnoreCase("Solid")) {
                     if ((Boolean)var2.getKey()) {
                        XuluTessellator.prepare(7);
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                        XuluTessellator.release();
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                     } else {
                        XuluTessellator.prepare(7);
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                        XuluTessellator.release();
                        this.drawBlockO(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                     }
                  } else if (((String)this.renderMode.getValue()).equalsIgnoreCase("Flat")) {
                     if ((Boolean)var2.getKey()) {
                        XuluTessellator.prepare(7);
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                        XuluTessellator.release();
                        this.drawBlockOF(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                     } else {
                        XuluTessellator.prepare(7);
                        this.drawBlock(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                        XuluTessellator.release();
                        this.drawBlockOF(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                     }
                  }

               });
            } else if (((String)this.drawMode.getValue()).equalsIgnoreCase("Cuboid")) {
               this.safeHoles.forEach((var1x, var2) -> {
                  if ((Boolean)this.offset.getValue()) {
                     var1x = var1x.add(0, -1, 0);
                  }

                  if ((Boolean)var2.getKey()) {
                     this.drawBlockCUB(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                  } else {
                     this.drawBlockCUB(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                  }

               });
            } else if (((String)this.drawMode.getValue()).equalsIgnoreCase("Indicator")) {
               this.safeHoles.forEach((var1x, var2) -> {
                  if ((Boolean)this.offset.getValue()) {
                     var1x = var1x.add(0, -1, 0);
                  }

                  if ((Boolean)var2.getKey()) {
                     this.drawBlockIndicator(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.brockRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.brockGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.brockBlue.getValue(), var2);
                  } else {
                     this.drawBlockIndicator(var1x, (Boolean)this.rainbow.getValue() ? RainbowUtils.r : (Integer)this.obiRed.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.g : (Integer)this.obiGreen.getValue(), (Boolean)this.rainbow.getValue() ? RainbowUtils.b : (Integer)this.obiBlue.getValue(), var2);
                  }

               });
            }

         }
      }
   }

   public HoleESP() {
      super("HoleESP", "Highlights holes for pvp", 0, Category.RENDER, true);
   }

   private void drawBlockCUB(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      int var6 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var2;
      int var7 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var3;
      int var8 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 0 : var4;
      IBlockState var9 = mc.world.getBlockState(var1);
      Vec3d var10 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      AxisAlignedBB var11 = var9.getSelectedBoundingBox(mc.world, var1);
      var11 = var11.setMaxY(var11.maxY - (double)(1.0F * (Float)this.cuboid.getValue())).grow(0.0020000000949949026D).offset(-var10.x, -var10.y, -var10.z);
      XuluTessellator.drawFullBox2(var11, var1, 1.5F, (new Color(var6, var7, var8, (Integer)this.alpha.getValue())).getRGB(), (Integer)this.alpha2.getValue());
   }

   private void drawBlockIndicator(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      int var6 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var2;
      int var7 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var3;
      int var8 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 0 : var4;
      IBlockState var9 = mc.world.getBlockState(var1);
      Vec3d var10 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      AxisAlignedBB var11 = var9.getSelectedBoundingBox(mc.world, var1);
      var11 = var11.setMaxY(var11.maxY + (double)(mc.player.getDistanceSq(var1) < 10.0D ? 0 : 3)).grow(0.0020000000949949026D).offset(-var10.x, -var10.y, -var10.z);
      GlStateManager.enableCull();
      XuluTessellator.drawIndicator(var11, (new Color(var6, var7, var8, (Integer)this.alpha.getValue())).getRGB(), 63);
   }

   public void onUpdate() {
      double var1 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)mc.getRenderPartialTicks();
      double var3 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)mc.getRenderPartialTicks();
      double var5 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)mc.getRenderPartialTicks();
      this.camera.setPosition(var1, var3, var5);
      if (this.safeHoles == null) {
         this.safeHoles = new ConcurrentHashMap();
      } else {
         this.safeHoles.clear();
      }

      int var7 = (int)Math.ceil((double)(Float)this.renderDistance.getValue());
      List var8 = BlockInteractionHelper.getSphere(AutoCrystal.getPlayerPos(), (float)var7, var7, false, true, 0);
      this.holes = 0;
      Iterator var9 = var8.iterator();

      while(true) {
         BlockPos var10;
         boolean var12;
         boolean var13;
         do {
            boolean var11;
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 if (!var9.hasNext()) {
                                    return;
                                 }

                                 var10 = (BlockPos)var9.next();
                              } while(!mc.world.getBlockState(var10).getBlock().equals(Blocks.AIR));
                           } while(!mc.world.getBlockState(var10.add(0, 1, 0)).getBlock().equals(Blocks.AIR));
                        } while(!mc.world.getBlockState(var10.add(0, 2, 0)).getBlock().equals(Blocks.AIR));
                     } while((Boolean)this.hideOwn.getValue() && var10.equals(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)));
                  } while((Boolean)this.frustum.getValue() && !this.camera.isBoundingBoxInFrustum(mc.world.getBlockState(var10).getSelectedBoundingBox(mc.world, var10)));

                  var11 = true;
                  var12 = true;
                  var13 = false;
                  BlockPos[] var14 = this.surroundOffset;
                  int var15 = var14.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     BlockPos var17 = var14[var16];
                     Block var18 = mc.world.getBlockState(var10.add(var17)).getBlock();
                     if (var18 != Blocks.BEDROCK) {
                        var12 = false;
                     }

                     if (var18 == Blocks.BEDROCK) {
                        var13 = true;
                     }

                     if (var18 != Blocks.BEDROCK && var18 != Blocks.OBSIDIAN && var18 != Blocks.ENDER_CHEST && var18 != Blocks.ANVIL) {
                        var11 = false;
                        break;
                     }
                  }
               } while(!var11);
            } while(var12 && ((String)this.holeMode.getValue()).equalsIgnoreCase("Obsidian"));
         } while(!var12 && ((String)this.holeMode.getValue()).equalsIgnoreCase("Bedrock"));

         this.safeHoles.put(var10, new Pair(var12, var13));
         if ((Boolean)this.max.getValue()) {
            ++this.holes;
            if (this.holes == (Integer)this.maxHoles.getValue()) {
               return;
            }
         }
      }
   }

   private void drawBlockOCUB(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      int var6 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var2;
      int var7 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var3;
      int var8 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 0 : var4;
      IBlockState var9 = mc.world.getBlockState(var1);
      Vec3d var10 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      AxisAlignedBB var11 = var9.getSelectedBoundingBox(mc.world, var1);
      var11 = var11.setMaxY(var11.maxY - (double)(1.0F * (Float)this.cuboid.getValue())).grow(0.0020000000949949026D).offset(-var10.x, -var10.y, -var10.z);
      XuluTessellator.drawBoundingBox(var11, 1.5F, var6, var7, var8, (Integer)this.alpha2.getValue());
   }

   public String getHudInfo() {
      return String.valueOf((new StringBuilder()).append((String)this.renderMode.getValue()).append(", ").append((String)this.drawMode.getValue()));
   }

   private void drawBlock(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      Color var6 = new Color(var2, var3, var4, (Integer)this.alpha.getValue());
      if ((Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue()) {
         var6 = new Color(255, 255, 0, (Integer)this.alpha.getValue());
      }

      byte var7 = 1;
      if (((String)this.renderMode.getValue()).equalsIgnoreCase("Solid")) {
         var7 = 63;
      }

      XuluTessellator.drawBox(var1, var6.getRGB(), var7);
   }

   private boolean isIntermediate(BlockPos var1) {
      boolean var2 = false;
      boolean var3 = false;
      BlockPos[] var4 = this.surroundOffset;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockPos var7 = var4[var6];
         Block var8 = mc.world.getBlockState(var1.add(var7)).getBlock();
         if (var8 == Blocks.BEDROCK) {
            var2 = true;
         } else if (var8 == Blocks.OBSIDIAN && var8 == Blocks.ENDER_CHEST && var8 == Blocks.ANVIL) {
            var3 = true;
         }
      }

      return var2 && var3;
   }

   private void drawBlockOF(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      int var6 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var2;
      int var7 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var3;
      int var8 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 0 : var4;
      IBlockState var9 = mc.world.getBlockState(var1);
      Vec3d var10 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      XuluTessellator.drawBoundingBoxFace(var9.getSelectedBoundingBox(mc.world, var1).grow(0.0020000000949949026D).offset(-var10.x, -var10.y, -var10.z), 1.5F, var6, var7, var8, (Integer)this.alpha2.getValue());
   }

   private void drawBlockO(BlockPos var1, int var2, int var3, int var4, Pair var5) {
      int var6 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var2;
      int var7 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 255 : var3;
      int var8 = (Boolean)this.future.getValue() && !(Boolean)var5.getKey() && (Boolean)var5.getValue() ? 0 : var4;
      IBlockState var9 = mc.world.getBlockState(var1);
      Vec3d var10 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
      XuluTessellator.drawBoundingBox(var9.getSelectedBoundingBox(mc.world, var1).grow(0.0020000000949949026D).offset(-var10.x, -var10.y, -var10.z), 1.5F, var6, var7, var8, (Integer)this.alpha2.getValue());
   }

   public static enum Modes {
      // $FF: synthetic field
      CUBOID,
      // $FF: synthetic field
      FULL,
      // $FF: synthetic field
      INDICATOR,
      // $FF: synthetic field
      SOLID,
      // $FF: synthetic field
      OUTLINE;
   }
}
