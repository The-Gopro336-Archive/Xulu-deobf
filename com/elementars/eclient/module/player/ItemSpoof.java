package com.elementars.eclient.module.player;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public final class ItemSpoof extends Module {
   // $FF: synthetic field
   public EnumHand hand;
   // $FF: synthetic field
   public float facingX;
   // $FF: synthetic field
   private boolean send;
   // $FF: synthetic field
   private Entity entity;
   // $FF: synthetic field
   public float facingZ;
   // $FF: synthetic field
   public EnumFacing placedBlockDirection;
   // $FF: synthetic field
   public float facingY;
   // $FF: synthetic field
   public BlockPos position;

   @EventTarget
   public void sendPacket(EventSendPacket var1) {
      Minecraft var3;
      if (var1.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
         if (this.send) {
            this.send = false;
            return;
         }

         CPacketPlayerTryUseItemOnBlock var2 = (CPacketPlayerTryUseItemOnBlock)var1.getPacket();
         this.position = var2.getPos();
         this.placedBlockDirection = var2.getDirection();
         this.hand = var2.getHand();
         this.facingX = var2.getFacingX();
         this.facingY = var2.getFacingY();
         this.facingZ = var2.getFacingZ();
         if (this.position != null) {
            var1.setCancelled(true);
            var3 = Minecraft.getMinecraft();
            var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
            this.send = true;
            var3.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(this.position, this.placedBlockDirection, this.hand, this.facingX, this.facingY, this.facingZ));
            var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
         }
      }

      if (var1.getPacket() instanceof CPacketPlayerTryUseItem) {
         if (this.send) {
            this.send = false;
            return;
         }

         CPacketPlayerTryUseItem var4 = (CPacketPlayerTryUseItem)var1.getPacket();
         this.hand = var4.getHand();
         if (this.hand != null) {
            var1.setCancelled(true);
            var3 = Minecraft.getMinecraft();
            var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
            this.send = true;
            var3.player.connection.sendPacket(new CPacketPlayerTryUseItem(this.hand));
            var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
         }
      }

      if (var1.getPacket() instanceof CPacketUseEntity) {
         if (this.send) {
            this.send = false;
            return;
         }

         CPacketUseEntity var5 = (CPacketUseEntity)var1.getPacket();
         if (var5.getAction() == Action.ATTACK) {
            var3 = Minecraft.getMinecraft();
            this.entity = var5.getEntityFromWorld(var3.world);
            if (this.entity != null) {
               var1.setCancelled(true);
               var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
               this.send = true;
               var3.player.connection.sendPacket(new CPacketUseEntity(this.entity));
               var3.playerController.windowClick(var3.player.inventoryContainer.windowId, 9, var3.player.inventory.currentItem, ClickType.SWAP, var3.player);
            }
         }
      }

   }

   public ItemSpoof() {
      super("ItemSpoof", "Allows you to display a different item server-side(Use the top left slot in your inventory)", 0, Category.PLAYER, true);
   }
}
