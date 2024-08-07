package com.elementars.eclient.module.render;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.OutlineUtils;
import com.elementars.eclient.util.OutlineUtils2;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class StorageESP extends Module {
   // $FF: synthetic field
   private final Value save = this.register(new Value("Save coords above threshold", this, false));
   // $FF: synthetic field
   private int count;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Full", "Outline", "Shader"))));
   // $FF: synthetic field
   private static Value future;
   // $FF: synthetic field
   private final Value all = this.register(new Value("All Tile Entities", this, false));
   // $FF: synthetic field
   private final Value threshold = this.register(new Value("Threshold", this, 200, 1, 2000));
   // $FF: synthetic field
   private int delay;
   // $FF: synthetic field
   Random random = new Random();
   // $FF: synthetic field
   private final Value width = this.register(new Value("Line Width", this, 1.0F, 1.0F, 10.0F));

   public String getHudInfo() {
      long var1 = mc.world.loadedTileEntityList.stream().filter((var0) -> {
         return var0 instanceof TileEntityChest;
      }).count();
      if ((Boolean)this.save.getValue() && (int)var1 >= (Integer)this.threshold.getValue() && this.delay == 0) {
         Wrapper.getFileManager().saveStorageESP(String.valueOf((new StringBuilder()).append(LocalDate.now().toString()).append("-").append(this.random.nextInt(9999)).append(" - ").append(this.count)), String.valueOf((new StringBuilder()).append((int)mc.player.posX).append(" ").append((int)mc.player.posY).append(" ").append((int)mc.player.posZ)), String.valueOf(var1));
         this.delay = 4000;
         ++this.count;
      }

      return String.valueOf((new StringBuilder()).append("Chests: ").append(var1));
   }

   private int getEntityColor(Entity var1) {
      if (var1 instanceof EntityMinecartChest) {
         return ColorUtils.Colors.ORANGE;
      } else if (var1 instanceof EntityMinecartHopper) {
         return ColorUtils.Colors.DARK_RED;
      } else {
         return var1 instanceof EntityItemFrame && ((EntityItemFrame)var1).getDisplayedItem().getItem() instanceof ItemShulkerBox ? ColorUtils.Colors.YELLOW : -1;
      }
   }

   public static void renderColor(float var0) {
      RenderHelper.enableStandardItemLighting();
      Iterator var1 = Wrapper.getMinecraft().world.loadedTileEntityList.iterator();

      while(true) {
         TileEntity var2;
         do {
            if (!var1.hasNext()) {
               return;
            }

            var2 = (TileEntity)var1.next();
         } while(!(var2 instanceof TileEntityChest) && !(var2 instanceof TileEntityEnderChest) && !(var2 instanceof TileEntityShulkerBox) && !(var2 instanceof TileEntityFurnace) && !(var2 instanceof TileEntityHopper));

         if ((Boolean)future.getValue()) {
            OutlineUtils2.setColor(new Color(getTileEntityColorF(var2)));
         } else {
            OutlineUtils2.setColor(new Color(getTileEntityColor(var2)));
         }

         TileEntityRendererDispatcher.instance.render(var2, (double)var2.getPos().getX() - mc.renderManager.renderPosX, (double)var2.getPos().getY() - mc.renderManager.renderPosY, (double)var2.getPos().getZ() - mc.renderManager.renderPosZ, var0);
      }
   }

   int changeAlpha(int var1, int var2) {
      var1 &= 16777215;
      return var2 << 24 | var1;
   }

   public StorageESP() {
      super("StorageESP", "Highlights storage blocks", 0, Category.RENDER, true);
      future = this.register(new Value("Future Colors", this, false));
      this.count = 1;
   }

   public static int getTileEntityColor(TileEntity var0) {
      if (!(var0 instanceof TileEntityChest) && !(var0 instanceof TileEntityDispenser)) {
         if (var0 instanceof TileEntityShulkerBox) {
            return ColorUtils.toRGBA(255, 0, 95, 255);
         } else if (var0 instanceof TileEntityEnderChest) {
            return ColorUtils.Colors.PURPLE;
         } else if (var0 instanceof TileEntityFurnace) {
            return ColorUtils.Colors.GRAY;
         } else {
            return var0 instanceof TileEntityHopper ? ColorUtils.Colors.DARK_RED : -1;
         }
      } else {
         return ColorUtils.Colors.ORANGE;
      }
   }

   public static int getTileEntityColorF(TileEntity var0) {
      if (var0 instanceof TileEntityChest) {
         return ColorUtils.toRGBA(200, 200, 101, 255);
      } else if (var0 instanceof TileEntityShulkerBox) {
         return ColorUtils.toRGBA(180, 21, 99, 255);
      } else if (var0 instanceof TileEntityEnderChest) {
         return ColorUtils.toRGBA(155, 0, 200, 255);
      } else if (var0 instanceof TileEntityFurnace) {
         return ColorUtils.Colors.GRAY;
      } else {
         return var0 instanceof TileEntityHopper ? ColorUtils.Colors.GRAY : -1;
      }
   }

   public static void renderNormal(float var0) {
      RenderHelper.enableStandardItemLighting();
      Iterator var1 = Wrapper.getMinecraft().world.loadedTileEntityList.iterator();

      while(true) {
         TileEntity var2;
         do {
            if (!var1.hasNext()) {
               return;
            }

            var2 = (TileEntity)var1.next();
         } while(!(var2 instanceof TileEntityChest) && !(var2 instanceof TileEntityEnderChest) && !(var2 instanceof TileEntityShulkerBox));

         GL11.glPushMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         TileEntityRendererDispatcher.instance.render(var2, (double)var2.getPos().getX() - mc.renderManager.renderPosX, (double)var2.getPos().getY() - mc.renderManager.renderPosY, (double)var2.getPos().getZ() - mc.renderManager.renderPosZ, var0);
         GL11.glPopMatrix();
      }
   }

   public void onWorldRender(RenderEvent var1) {
      if (this.delay > 0) {
         --this.delay;
      }

      ArrayList var2 = new ArrayList();
      GlStateManager.pushMatrix();
      if (((String)this.mode.getValue()).equalsIgnoreCase("Shader")) {
         OutlineUtils.checkSetupFBO();
      }

      Iterator var3 = Wrapper.getWorld().loadedTileEntityList.iterator();

      while(var3.hasNext()) {
         TileEntity var4 = (TileEntity)var3.next();
         BlockPos var5 = var4.getPos();
         int var6;
         if ((Boolean)future.getValue()) {
            var6 = getTileEntityColorF(var4);
         } else {
            var6 = getTileEntityColor(var4);
         }

         int var7 = 63;
         if (var4 instanceof TileEntityChest) {
            TileEntityChest var8 = (TileEntityChest)var4;
            if (var8.adjacentChestZNeg != null) {
               var7 = ~(var7 & 4);
            }

            if (var8.adjacentChestXPos != null) {
               var7 = ~(var7 & 32);
            }

            if (var8.adjacentChestZPos != null) {
               var7 = ~(var7 & 8);
            }

            if (var8.adjacentChestXNeg != null) {
               var7 = ~(var7 & 16);
            }
         }

         if (var6 != -1) {
            var2.add(new StorageESP.Triplet(var4, var6, var7));
         }
      }

      var3 = var2.iterator();

      while(var3.hasNext()) {
         StorageESP.Triplet var10 = (StorageESP.Triplet)var3.next();

         try {
            if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               XuluTessellator.drawBox(((TileEntity)var10.getFirst()).getPos(), this.changeAlpha((Integer)var10.getSecond(), 100), (Integer)var10.getThird());
               XuluTessellator.release();
            } else {
               IBlockState var11;
               Vec3d var12;
               if (((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
                  var11 = mc.world.getBlockState(((TileEntity)var10.getFirst()).getPos());
                  var12 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.prepare(7);
                  XuluTessellator.drawBoundingBox(var11.getSelectedBoundingBox(mc.world, ((TileEntity)var10.getFirst()).getPos()).grow(0.0020000000949949026D).offset(-var12.x, -var12.y, -var12.z), 1.5F, (Integer)var10.getSecond());
                  XuluTessellator.release();
               } else if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
                  var11 = mc.world.getBlockState(((TileEntity)var10.getFirst()).getPos());
                  var12 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.drawFullBox2(var11.getSelectedBoundingBox(mc.world, ((TileEntity)var10.getFirst()).getPos()).grow(0.0020000000949949026D).offset(-var12.x, -var12.y, -var12.z), ((TileEntity)var10.getFirst()).getPos(), 1.5F, this.changeAlpha((Integer)var10.getSecond(), 100));
               } else if (!((String)this.mode.getValue()).equalsIgnoreCase("Shader")) {
                  XuluTessellator.prepare(7);
                  XuluTessellator.drawBox(((TileEntity)var10.getFirst()).getPos(), this.changeAlpha((Integer)var10.getSecond(), 100), (Integer)var10.getThird());
                  XuluTessellator.release();
               }
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.enableTexture2D();
   }

   public class Triplet {
      // $FF: synthetic field
      private final Object third;
      // $FF: synthetic field
      private final Object first;
      // $FF: synthetic field
      private final Object second;

      public Object getThird() {
         return this.third;
      }

      public Object getFirst() {
         return this.first;
      }

      public Object getSecond() {
         return this.second;
      }

      public Triplet(Object var2, Object var3, Object var4) {
         this.first = var2;
         this.second = var3;
         this.third = var4;
      }
   }
}
