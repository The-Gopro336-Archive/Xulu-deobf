package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import io.netty.util.internal.ConcurrentSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BreakESP extends Module {
   // $FF: synthetic field
   private ConcurrentSet test = new ConcurrentSet();
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 70, 0, 255));
   // $FF: synthetic field
   private final Value ignoreSelf = this.register(new Value("Ignore Self", this, true));
   // $FF: synthetic field
   private final Value alphaF = this.register(new Value("Full Alpha", this, 100, 0, 255));
   // $FF: synthetic field
   public ConcurrentSet breaking = new ConcurrentSet();
   // $FF: synthetic field
   float inc;
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value fade = this.register(new Value("Fade Progress", this, true));
   // $FF: synthetic field
   BlockPos pos;
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Outline", "Full"))));
   // $FF: synthetic field
   public static BreakESP INSTANCE;
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value onlyObby = this.register(new Value("Only Obsidian", this, true));
   // $FF: synthetic field
   private Map alphaMap = new HashMap();
   // $FF: synthetic field
   private ArrayList options;

   public BreakESP() {
      super("BreakESP", "Highlights blocks being broken", 0, Category.RENDER, true);
      this.alphaMap.put(0, 28);
      this.alphaMap.put(1, 56);
      this.alphaMap.put(2, 84);
      this.alphaMap.put(3, 112);
      this.alphaMap.put(4, 140);
      this.alphaMap.put(5, 168);
      this.alphaMap.put(6, 196);
      this.alphaMap.put(7, 224);
      this.alphaMap.put(8, 255);
      this.alphaMap.put(9, 255);
   }

   public void onWorldRender(RenderEvent var1) {
      mc.renderGlobal.damagedBlocks.forEach((var1x, var2) -> {
         if (var2 != null) {
            if (!(Boolean)this.ignoreSelf.getValue() || mc.world.getEntityByID(var1x) != mc.player) {
               if (!(Boolean)this.onlyObby.getValue() || mc.world.getBlockState(var2.getPosition()).getBlock() == Blocks.OBSIDIAN) {
                  int var3 = (Boolean)this.fade.getValue() ? (Integer)this.alphaMap.get(var2.getPartialBlockDamage()) : (Integer)this.alpha.getValue();
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
                     XuluTessellator.prepare(7);
                     XuluTessellator.drawBox(var2.getPosition(), (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), var3, 63);
                     XuluTessellator.release();
                  } else {
                     IBlockState var4;
                     Vec3d var5;
                     if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
                        var4 = mc.world.getBlockState(var2.getPosition());
                        var5 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                        XuluTessellator.drawFullBox(var4.getSelectedBoundingBox(mc.world, var2.getPosition()).grow(0.0020000000949949026D).offset(-var5.x, -var5.y, -var5.z), var2.getPosition(), 1.5F, (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), var3, (Integer)this.alphaF.getValue());
                     } else if (((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
                        var4 = mc.world.getBlockState(var2.getPosition());
                        var5 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                        XuluTessellator.drawBoundingBox(var4.getSelectedBoundingBox(mc.world, var2.getPosition()).grow(0.0020000000949949026D).offset(-var5.x, -var5.y, -var5.z), 1.5F, (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), var3);
                     } else {
                        XuluTessellator.prepare(7);
                        XuluTessellator.drawBox(var2.getPosition(), (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), var3, 63);
                        XuluTessellator.release();
                     }
                  }

               }
            }
         }
      });
   }
}
