package com.elementars.eclient.module.render;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventRenderBlock;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.ArrayList;
import net.minecraft.block.Block;

public class Xray extends Module {
   // $FF: synthetic field
   public static Xray INSTANCE;
   // $FF: synthetic field
   private static final ArrayList BLOCKS = new ArrayList();

   @EventTarget
   public void onRender(EventRenderBlock var1) {
      Block var2 = var1.getBlockState().getBlock();
      if (shouldXray(var2) && mc.getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(var1.getBlockAccess(), var1.getBakedModel(), var1.getBlockState(), var1.getBlockPos(), var1.getBufferBuilder(), var1.isCheckSides(), var1.getRand())) {
         var1.setRenderable(true);
      }

      var1.setCancelled(true);
   }

   public Xray() {
      super("Xray", "See through blocks!", 0, Category.RENDER, true);
      initblocks();
      INSTANCE = this;
   }

   public static void initblocks() {
      BLOCKS.add(Block.getBlockFromName("coal_ore"));
      BLOCKS.add(Block.getBlockFromName("iron_ore"));
      BLOCKS.add(Block.getBlockFromName("gold_ore"));
      BLOCKS.add(Block.getBlockFromName("redstone_ore"));
      BLOCKS.add(Block.getBlockById(74));
      BLOCKS.add(Block.getBlockFromName("lapis_ore"));
      BLOCKS.add(Block.getBlockFromName("diamond_ore"));
      BLOCKS.add(Block.getBlockFromName("emerald_ore"));
      BLOCKS.add(Block.getBlockFromName("quartz_ore"));
      BLOCKS.add(Block.getBlockFromName("clay"));
      BLOCKS.add(Block.getBlockFromName("glowstone"));
      BLOCKS.add(Block.getBlockById(8));
      BLOCKS.add(Block.getBlockById(9));
      BLOCKS.add(Block.getBlockById(10));
      BLOCKS.add(Block.getBlockById(11));
      BLOCKS.add(Block.getBlockFromName("crafting_table"));
      BLOCKS.add(Block.getBlockById(61));
      BLOCKS.add(Block.getBlockById(62));
      BLOCKS.add(Block.getBlockFromName("torch"));
      BLOCKS.add(Block.getBlockFromName("ladder"));
      BLOCKS.add(Block.getBlockFromName("tnt"));
      BLOCKS.add(Block.getBlockFromName("coal_block"));
      BLOCKS.add(Block.getBlockFromName("iron_block"));
      BLOCKS.add(Block.getBlockFromName("gold_block"));
      BLOCKS.add(Block.getBlockFromName("diamond_block"));
      BLOCKS.add(Block.getBlockFromName("emerald_block"));
      BLOCKS.add(Block.getBlockFromName("redstone_block"));
      BLOCKS.add(Block.getBlockFromName("lapis_block"));
      BLOCKS.add(Block.getBlockFromName("fire"));
      BLOCKS.add(Block.getBlockFromName("mossy_cobblestone"));
      BLOCKS.add(Block.getBlockFromName("mob_spawner"));
      BLOCKS.add(Block.getBlockFromName("end_portal_frame"));
      BLOCKS.add(Block.getBlockFromName("enchanting_table"));
      BLOCKS.add(Block.getBlockFromName("bookshelf"));
      BLOCKS.add(Block.getBlockFromName("command_block"));
   }

   public void onDisable() {
      mc.renderGlobal.loadRenderers();
   }

   public void onEnable() {
      mc.renderGlobal.loadRenderers();
   }

   public static boolean shouldXray(Block var0) {
      return BLOCKS.contains(var0);
   }

   public static boolean addBlock(String var0) {
      if (Block.getBlockFromName(var0) != null) {
         BLOCKS.add(Block.getBlockFromName(var0));
         return true;
      } else {
         return false;
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

   public static ArrayList getBLOCKS() {
      return BLOCKS;
   }
}
