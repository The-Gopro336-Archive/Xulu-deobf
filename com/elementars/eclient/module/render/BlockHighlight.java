package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.RainbowUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHighlight extends Module {
   // $FF: synthetic field
   private final Value alphaF = this.register(new Value("Alpha Full", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 255, 0, 255));
   // $FF: synthetic field
   private static BlockPos position;
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value width = this.register(new Value("Width", this, 1.0F, 1.0F, 10.0F));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Outline", new String[]{"Solid", "Outline", "Full"}));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 255, 0, 255));

   public void onWorldRender(RenderEvent var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      RayTraceResult var3 = var2.objectMouseOver;
      if (var3 != null && var3.typeOfHit == Type.BLOCK) {
         BlockPos var4 = var3.getBlockPos();
         IBlockState var5 = var2.world.getBlockState(var4);
         if (var5.getMaterial() != Material.AIR && var2.world.getWorldBorder().contains(var4)) {
            Vec3d var6 = MathUtil.interpolateEntity(var2.player, var2.getRenderPartialTicks());
            int var7 = (Integer)this.red.getValue();
            int var8 = (Integer)this.green.getValue();
            int var9 = (Integer)this.blue.getValue();
            if ((Boolean)this.rainbow.getValue()) {
               var7 = RainbowUtils.r;
               var8 = RainbowUtils.g;
               var9 = RainbowUtils.b;
            }

            if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               XuluTessellator.drawBox(var4, var7, var8, var9, (Integer)this.alpha.getValue(), 63);
               XuluTessellator.release();
            } else if (((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
               XuluTessellator.drawBoundingBox(var5.getSelectedBoundingBox(var2.world, var4).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), (Float)this.width.getValue(), var7, var8, var9, (Integer)this.alpha.getValue());
            } else if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
               XuluTessellator.drawFullBox(var5.getSelectedBoundingBox(var2.world, var4).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), var4, (Float)this.width.getValue(), var7, var8, var9, (Integer)this.alpha.getValue(), (Integer)this.alphaF.getValue());
            }
         }
      }

   }

   @SubscribeEvent
   public void onDrawBlockHighlight(DrawBlockHighlightEvent var1) {
      if (mc.player != null && mc.world != null && (mc.playerController.getCurrentGameType().equals(GameType.SURVIVAL) || mc.playerController.getCurrentGameType().equals(GameType.CREATIVE))) {
         var1.setCanceled(true);
      }
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
      position = null;
   }

   public BlockHighlight() {
      super("BlockHighlight", "Highlights block you're looking at", 0, Category.RENDER, true);
   }
}
