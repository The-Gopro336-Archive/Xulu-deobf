package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.combat.PopCounter;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ColourHolder;
import com.elementars.eclient.util.Pair;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class Target extends Element {
   // $FF: synthetic field
   BlockPos SOUTH = new BlockPos(0, 0, 1);
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private EntityPlayer target;
   // $FF: synthetic field
   BlockPos WEST = new BlockPos(-1, 0, 0);
   // $FF: synthetic field
   BlockPos NORTH = new BlockPos(0, 0, -1);
   // $FF: synthetic field
   private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
   // $FF: synthetic field
   BlockPos EAST = new BlockPos(1, 0, 0);

   public void onEnable() {
      this.width = 200.0D;
      this.height = 100.0D;
      super.onEnable();
   }

   private List getNorth(EntityPlayer var1) {
      BlockPos var2 = new BlockPos(var1.posX, var1.posY, var1.posZ);
      ArrayList var3 = new ArrayList(Arrays.asList(this.isBrockOrObby(var2.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)) ? new ItemStack(mc.world.getBlockState(var2.add(this.NORTH.x, this.NORTH.y, this.NORTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var2.add(this.EAST.x, this.EAST.y, this.EAST.z)) ? new ItemStack(mc.world.getBlockState(var2.add(this.EAST.x, this.EAST.y, this.EAST.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var2.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)) ? new ItemStack(mc.world.getBlockState(var2.add(this.SOUTH.x, this.SOUTH.y, this.SOUTH.z)).getBlock()) : new ItemStack(Items.AIR), this.isBrockOrObby(var2.add(this.WEST.x, this.WEST.y, this.WEST.z)) ? new ItemStack(mc.world.getBlockState(var2.add(this.WEST.x, this.WEST.y, this.WEST.z)).getBlock()) : new ItemStack(Items.AIR)));
      return var3;
   }

   public void onUpdate() {
      if (mc.player != null) {
         ArrayList var1 = new ArrayList(mc.world.playerEntities);
         var1.removeIf((var0) -> {
            return var0 == mc.player;
         });
         var1.sort(Comparator.comparing((var0) -> {
            return mc.player.getDistance(var0);
         }));
         if (!var1.isEmpty()) {
            this.target = (EntityPlayer)var1.get(0);
         } else {
            this.target = null;
         }

         super.onUpdate();
      }
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

   public String getPing(float var1) {
      if (var1 > 200.0F) {
         return "c";
      } else {
         return var1 > 100.0F ? "e" : "a";
      }
   }

   public void onRender() {
      if (mc.player != null && mc.world != null) {
         if (this.target != null) {
            Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, ColorUtils.changeAlpha(Color.BLACK.getRGB(), 50));
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            try {
               GuiInventory.drawEntityOnScreen((int)this.x + 30, (int)this.y + 90, 45, 0.0F, 0.0F, this.target);
            } catch (Exception var13) {
               var13.printStackTrace();
            }

            GlStateManager.popMatrix();
            if ((Boolean)this.cf.getValue()) {
               Xulu.cFontRenderer.drawStringWithShadow(this.target.getName(), this.x + 62.0D, this.y + 3.0D, -1);
               Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(mc.getConnection() != null && mc.getConnection().getPlayerInfo(this.target.entityUniqueID) != null ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(this.getPing((float)mc.getConnection().getPlayerInfo(this.target.entityUniqueID).getResponseTime())).append(mc.getConnection().getPlayerInfo(this.target.entityUniqueID).getResponseTime())) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("-1"))).append("ms")), this.x + 62.0D, this.y + 18.0D, -1);
               Xulu.cFontRenderer.drawStringWithShadow(PopCounter.INSTANCE.popMap.containsKey(this.target) ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("").append(PopCounter.INSTANCE.popMap.get(this.target)).append(" Pops")) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("0 Pops")), this.x + 62.0D, this.y + 33.0D, -1);
               Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(this.getDistance((double)mc.player.getDistance(this.target))).append((int)mc.player.getDistance(this.target)).append(" blocks away")), this.x + 62.0D, this.y + 48.0D, -1);
            } else {
               fontRenderer.drawStringWithShadow(this.target.getName(), (float)this.x + 62.0F, (float)this.y + 3.0F, -1);
               fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(mc.getConnection() != null && mc.getConnection().getPlayerInfo(this.target.entityUniqueID) != null ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(this.getPing((float)mc.getConnection().getPlayerInfo(this.target.entityUniqueID).getResponseTime())).append(mc.getConnection().getPlayerInfo(this.target.entityUniqueID).getResponseTime())) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("-1"))).append("ms")), (float)this.x + 62.0F, (float)this.y + 18.0F, -1);
               fontRenderer.drawStringWithShadow(PopCounter.INSTANCE.popMap.containsKey(this.target) ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("").append(PopCounter.INSTANCE.popMap.get(this.target)).append(" Pops")) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("0 Pops")), (float)this.x + 62.0F, (float)this.y + 33.0F, -1);
               fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(this.getDistance((double)mc.player.getDistance(this.target))).append((int)mc.player.getDistance(this.target)).append(" blocks away")), (float)((int)this.x + 62), (float)((int)this.y + 48), -1);
            }

            float var1 = (float)MathHelper.clamp(MathHelper.ceil(this.target.getHealth()), 0, 20);
            float var2 = (20.0F - var1) / 20.0F;
            float var3 = 1.0F - var2;
            Gui.drawRect((int)this.x, (int)this.y + (int)this.height - 3, (int)(this.x + (double)var3 * this.width), (int)this.y + (int)this.height, ColorUtils.changeAlpha(ColourHolder.toHex((int)(var2 * 255.0F), (int)(var3 * 255.0F), 0), 200));
            this.itemrender(this.getNorth(this.target), (int)this.x + (int)this.width - 52, (int)this.y + 4);
            GlStateManager.pushMatrix();
            GlStateManager.enableTexture2D();
            int var4 = 0;
            Iterator var5 = this.target.inventory.armorInventory.iterator();

            while(var5.hasNext()) {
               ItemStack var6 = (ItemStack)var5.next();
               ++var4;
               if (!var6.isEmpty()) {
                  int var7 = (int)this.x - 90 + (9 - var4) * 20 + 2 - 12 + 60;
                  int var8 = (int)this.y + (int)this.height - 24;
                  GlStateManager.enableDepth();
                  itemRender.zLevel = 200.0F;
                  itemRender.renderItemAndEffectIntoGUI(var6, var7, var8);
                  itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, var6, var7, var8, "");
                  itemRender.zLevel = 0.0F;
                  GlStateManager.enableTexture2D();
                  GlStateManager.disableLighting();
                  GlStateManager.disableDepth();
                  String var9 = var6.getCount() > 1 ? String.valueOf((new StringBuilder()).append(var6.getCount()).append("")) : "";
                  mc.fontRenderer.drawStringWithShadow(var9, (float)(var7 + 19 - 2 - mc.fontRenderer.getStringWidth(var9)), (float)((int)this.y + 9), 16777215);
                  float var10 = ((float)var6.getMaxDamage() - (float)var6.getItemDamage()) / (float)var6.getMaxDamage();
                  float var11 = 1.0F - var10;
                  int var12 = 100 - (int)(var11 * 100.0F);
                  if ((Boolean)this.cf.getValue()) {
                     Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var12).append("")), (double)(var7 + 8 - Xulu.cFontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var12).append(""))) / 2), (double)(var8 - 11), ColourHolder.toHex((int)(var11 * 255.0F), (int)(var10 * 255.0F), 0));
                  } else {
                     fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var12).append("")), (float)(var7 + 9 - fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var12).append(""))) / 2), (float)(var8 - 11), ColourHolder.toHex((int)(var11 * 255.0F), (int)(var10 * 255.0F), 0));
                  }
               }
            }

            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
         }

      }
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

   private boolean isBrockOrObby(BlockPos var1) {
      return mc.world.getBlockState(var1).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(var1).getBlock() == Blocks.OBSIDIAN;
   }

   private void itemrender(List var1, int var2, int var3) {
      ArrayList var4 = new ArrayList(Arrays.asList(new Pair(var2 + 16, var3), new Pair(var2 + 32, var3 + 16), new Pair(var2 + 16, var3 + 32), new Pair(var2, var3 + 16)));

      for(int var5 = 0; var5 < 4; ++var5) {
         preitemrender();
         mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)var1.get(var5), (Integer)((Pair)var4.get(var5)).getKey(), (Integer)((Pair)var4.get(var5)).getValue());
         postitemrender();
      }

   }

   public Target() {
      super("Target");
   }

   private String getDistance(double var1) {
      return var1 < 15.0D ? "c" : "a";
   }
}
