package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.combat.AutoCrystal;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import io.netty.util.internal.ConcurrentSet;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class VoidESP extends Module {
   // $FF: synthetic field
   private final Value activateAtY = this.register(new Value("ActivateAtY", this, 32, 1, 512));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 128, 0, 255));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private ConcurrentSet voidHoles;
   // $FF: synthetic field
   private final Value range = this.register(new Value("Range", this, 8, 1, 32));
   // $FF: synthetic field
   private final Value holeMode = this.register(new Value("HoleMode", this, "Sides", new ArrayList(Arrays.asList("Sides", "Above"))));
   // $FF: synthetic field
   private final Value renderMode = this.register(new Value("RenderMode", this, "Down", new ArrayList(Arrays.asList("Down", "Block"))));

   public VoidESP() {
      super("VoidESP", "Highlights possible void holes", 0, Category.RENDER, true);
   }

   public String getHudInfo() {
      return (String)this.holeMode.getValue();
   }

   private void drawBlock(BlockPos var1, int var2, int var3, int var4) {
      Color var5 = new Color(var2, var3, var4, (Integer)this.alpha.getValue());
      byte var6 = 0;
      if (((String)this.renderMode.getValue()).equalsIgnoreCase("Block")) {
         var6 = 63;
      }

      if (((String)this.renderMode.getValue()).equalsIgnoreCase("Down")) {
         var6 = 1;
      }

      XuluTessellator.drawBox(var1, var5.getRGB(), var6);
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.player != null && this.voidHoles != null && !this.voidHoles.isEmpty()) {
         XuluTessellator.prepare(7);
         this.voidHoles.forEach((var1x) -> {
            this.drawBlock(var1x, (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue());
         });
         XuluTessellator.release();
      }
   }

   private boolean isAnyBedrock(BlockPos var1, BlockPos[] var2) {
      BlockPos[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BlockPos var6 = var3[var5];
         if (mc.world.getBlockState(var1.add(var6)).getBlock().equals(Blocks.BEDROCK)) {
            return true;
         }
      }

      return false;
   }

   public void onUpdate() {
      if (mc.player.getPosition().y <= (Integer)this.activateAtY.getValue()) {
         if (this.voidHoles == null) {
            this.voidHoles = new ConcurrentSet();
         } else {
            this.voidHoles.clear();
         }

         List var1 = BlockInteractionHelper.getCircle(AutoCrystal.getPlayerPos(), 0, (float)(Integer)this.range.getValue(), false);
         Iterator var2 = var1.iterator();

         while(true) {
            while(true) {
               BlockPos var3;
               do {
                  do {
                     if (!var2.hasNext()) {
                        return;
                     }

                     var3 = (BlockPos)var2.next();
                  } while(mc.world.getBlockState(var3).getBlock().equals(Blocks.BEDROCK));
               } while(this.isAnyBedrock(var3, VoidESP.Offsets.center));

               boolean var4 = false;
               if (!this.isAnyBedrock(var3, VoidESP.Offsets.above)) {
                  var4 = true;
               }

               if (((String)this.holeMode.getValue()).equalsIgnoreCase("Above")) {
                  if (var4) {
                     this.voidHoles.add(var3);
                  }
               } else {
                  boolean var5 = false;
                  if (!this.isAnyBedrock(var3, VoidESP.Offsets.north)) {
                     var5 = true;
                  }

                  if (!this.isAnyBedrock(var3, VoidESP.Offsets.east)) {
                     var5 = true;
                  }

                  if (!this.isAnyBedrock(var3, VoidESP.Offsets.south)) {
                     var5 = true;
                  }

                  if (!this.isAnyBedrock(var3, VoidESP.Offsets.west)) {
                     var5 = true;
                  }

                  if (((String)this.holeMode.getValue()).equalsIgnoreCase("Sides") && (var4 || var5)) {
                     this.voidHoles.add(var3);
                  }
               }
            }
         }
      }
   }

   private static enum HoleMode {
      // $FF: synthetic field
      ABOVE,
      // $FF: synthetic field
      SIDES;
   }

   private static enum RenderMode {
      // $FF: synthetic field
      BLOCK,
      // $FF: synthetic field
      DOWN;
   }

   private static class Offsets {
      // $FF: synthetic field
      static final BlockPos[] south = new BlockPos[]{new BlockPos(0, 1, 1), new BlockPos(0, 2, 1)};
      // $FF: synthetic field
      static final BlockPos[] aboveStep2 = new BlockPos[]{new BlockPos(0, 4, 0)};
      // $FF: synthetic field
      static final BlockPos[] aboveStep1 = new BlockPos[]{new BlockPos(0, 3, 0)};
      // $FF: synthetic field
      static final BlockPos[] above = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(0, 4, 0)};
      // $FF: synthetic field
      static final BlockPos[] west = new BlockPos[]{new BlockPos(-1, 1, 0), new BlockPos(-1, 2, 0)};
      // $FF: synthetic field
      static final BlockPos[] center = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(0, 2, 0)};
      // $FF: synthetic field
      static final BlockPos[] north = new BlockPos[]{new BlockPos(0, 1, -1), new BlockPos(0, 2, -1)};
      // $FF: synthetic field
      static final BlockPos[] east = new BlockPos[]{new BlockPos(1, 1, 0), new BlockPos(1, 2, 0)};
   }
}
