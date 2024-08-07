package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class HoleHud extends Element {
   // $FF: synthetic field
   BlockPos SOUTH = new BlockPos(0, 0, 1);
   // $FF: synthetic field
   BlockPos EAST = new BlockPos(1, 0, 0);
   // $FF: synthetic field
   BlockPos NORTH = new BlockPos(0, 0, -1);
   // $FF: synthetic field
   BlockPos WEST = new BlockPos(-1, 0, 0);

   public void onRender() {
      if (mc.player != null && mc.world != null) {
         switch(mc.getRenderViewEntity().getHorizontalFacing()) {
         case NORTH:
            this.itemrender(this.getNorth(), (int)this.x, (int)this.y);
            break;
         case EAST:
            this.itemrender(this.getEast(), (int)this.x, (int)this.y);
            break;
         case SOUTH:
            this.itemrender(this.getSouth(), (int)this.x, (int)this.y);
            break;
         case WEST:
            this.itemrender(this.getWest(), (int)this.x, (int)this.y);
         }

      }
   }

   private List getEast() {
      BlockPos var1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      ArrayList var2 = new ArrayList(Arrays.asList(this.isBrockOrObby(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)).getBlock()) : new ItemStack(Items.AIR)));
      return var2;
   }

   public void onEnable() {
      this.width = 48.0D;
      this.height = 48.0D;
      super.onEnable();
   }

   private static void preitemrender() {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
   }

   private static void postitemrender() {
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   private List getNorth() {
      BlockPos var1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      ArrayList var2 = new ArrayList(Arrays.asList(this.isBrockOrObby(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)).getBlock()) : new ItemStack(Items.AIR)));
      return var2;
   }

   private boolean isBrockOrObby(BlockPos var1) {
      return mc.world.getBlockState(var1).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(var1).getBlock() == Blocks.OBSIDIAN;
   }

   private List getWest() {
      BlockPos var1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      ArrayList var2 = new ArrayList(Arrays.asList(this.isBrockOrObby(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)).getBlock()) : new ItemStack(Items.AIR)));
      return var2;
   }

   private List getSouth() {
      BlockPos var1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      ArrayList var2 = new ArrayList(Arrays.asList(this.isBrockOrObby(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.WEST.x, this.WEST.y, this.WEST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)) ? new ItemStack(mc.world.getBlockState(var1.add(this.EAST.x, this.EAST.y, this.EAST.z)).getBlock()) : new ItemStack(Items.AIR)));
      return var2;
   }

   public HoleHud() {
      super("HoleHud");
   }

   private void itemrender(List var1, int var2, int var3) {
      ArrayList var4 = new ArrayList(Arrays.asList(new Pair(var2 + 16, var3), new Pair(var2 + 32, var3 + 16), new Pair(var2 + 16, var3 + 32), new Pair(var2, var3 + 16)));

      for(int var5 = 0; var5 < 4; ++var5) {
         preitemrender();
         InvPreview.mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)var1.get(var5), (Integer)((Pair)var4.get(var5)).getKey(), (Integer)((Pair)var4.get(var5)).getValue());
         postitemrender();
      }

   }
}
