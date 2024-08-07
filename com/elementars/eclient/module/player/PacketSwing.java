package com.elementars.eclient.module.player;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.item.ItemSword;

public class PacketSwing extends Module {
   public PacketSwing() {
      super("PacketSwing", "Swings with packets lol", 0, Category.PLAYER, true);
   }

   public void onUpdate() {
      if (mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && (double)mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9D) {
         mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0F;
         mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
      }

   }
}
