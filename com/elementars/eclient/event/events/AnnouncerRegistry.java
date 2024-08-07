package com.elementars.eclient.event.events;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.misc.Announcer;
import com.elementars.eclient.util.AnnouncerUtil;
import java.text.DecimalFormat;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AnnouncerRegistry {
   // $FF: synthetic field
   static int blocksWalkerCounter;
   // $FF: synthetic field
   int lastWalkedAmount = 0;
   // $FF: synthetic field
   static int commandDelay = 0;
   // $FF: synthetic field
   BlockPos blockPos;
   // $FF: synthetic field
   static long lastPositionUpdate;
   // $FF: synthetic field
   static int dropItemDelay = 0;
   // $FF: synthetic field
   static int jumpDelay = 0;
   // $FF: synthetic field
   static int inventoryDelay = 0;
   // $FF: synthetic field
   Item heldBlock;
   // $FF: synthetic field
   int kek = 0;
   // $FF: synthetic field
   static int blockPlacedDelay = 0;
   // $FF: synthetic field
   static int chatDelay = 0;
   // $FF: synthetic field
   static int perspectivesDelay = 0;
   // $FF: synthetic field
   Minecraft minecraft = Minecraft.getMinecraft();
   // $FF: synthetic field
   static int attackDelay = 0;
   // $FF: synthetic field
   String heldItem = "";
   // $FF: synthetic field
   static int playerListDelay = 0;
   // $FF: synthetic field
   static double lastPositionZ;
   // $FF: synthetic field
   private static double speed;
   // $FF: synthetic field
   static double lastPositionY;
   // $FF: synthetic field
   static int blockBrokeDelay = 0;
   // $FF: synthetic field
   static double lastPositionX;
   // $FF: synthetic field
   static int pauseDelay = 0;
   // $FF: synthetic field
   String blockName = "";
   // $FF: synthetic field
   static int crouchedDelay = 0;
   // $FF: synthetic field
   static int eattingDelay = 0;
   // $FF: synthetic field
   static Module announcer;
   // $FF: synthetic field
   static int itemPickUpDelay = 0;

   @SubscribeEvent
   public void onMouseClicked(MouseEvent var1) {
      if (announcer.isToggled() && Mouse.getEventButtonState() && Mouse.getEventButton() == 0 && this.minecraft.objectMouseOver.entityHit != null && attackDelay >= 300 && (Boolean)Announcer.attack.getValue()) {
         this.sendMessage(AnnouncerUtil.getAttacked(TextFormatting.getTextWithoutFormattingCodes(this.minecraft.objectMouseOver.entityHit.getName()), this.minecraft.player.getHeldItemMainhand().getDisplayName()));
         attackDelay = 0;
      }

   }

   @SubscribeEvent
   public void itemPickedUp(EntityItemPickupEvent var1) {
      if (announcer.isToggled() && itemPickUpDelay >= 150 && (Boolean)Announcer.pickUpItem.getValue()) {
         ItemStack var2 = var1.getItem().getItem();
         this.sendMessage(AnnouncerUtil.getPickedUp(var2.getDisplayName()));
         itemPickUpDelay = 0;
      }

   }

   @SubscribeEvent
   public void itemSmelted(ItemSmeltedEvent var1) {
      if (var1.player instanceof EntityPlayerSP && announcer.isToggled() && (Boolean)Announcer.smeltedItem.getValue()) {
         this.sendMessage(AnnouncerUtil.getSmelted(String.valueOf((new StringBuilder()).append(var1.smelting.getCount()).append(" ").append(var1.smelting.getDisplayName()))));
      }

   }

   @SubscribeEvent
   public void livingUpdate(PlayerTickEvent var1) {
      if (var1.player instanceof EntityPlayerSP) {
         if (this.kek >= 1 && announcer.isToggled() && this.minecraft.world.getBlockState(this.blockPos).getBlock() instanceof BlockAir && blockBrokeDelay >= 300 && (Boolean)Announcer.blockBroke.getValue()) {
            this.sendMessage(AnnouncerUtil.getBlockBreak(this.blockName));
            blockBrokeDelay = 0;
            this.kek = 0;
         }

         if (announcer.isToggled()) {
            this.heldItem = this.minecraft.player.inventory.getCurrentItem().getDisplayName();
            this.heldBlock = this.minecraft.player.inventory.getCurrentItem().getItem();
            ++blocksWalkerCounter;
            ++dropItemDelay;
            ++itemPickUpDelay;
            ++blockPlacedDelay;
            ++blockBrokeDelay;
            ++chatDelay;
            ++commandDelay;
            ++pauseDelay;
            ++inventoryDelay;
            ++playerListDelay;
            ++perspectivesDelay;
            ++crouchedDelay;
            ++jumpDelay;
            ++attackDelay;
            ++eattingDelay;
            if (lastPositionUpdate + 30000L < System.currentTimeMillis() && (Boolean)Announcer.walk.getValue()) {
               double var2 = lastPositionX - this.minecraft.player.lastTickPosX;
               double var4 = lastPositionY - this.minecraft.player.lastTickPosY;
               double var6 = lastPositionZ - this.minecraft.player.lastTickPosZ;
               speed = Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
               if (speed > 0.0D && speed <= 5000.0D) {
                  this.sendMessage(AnnouncerUtil.getMove((new DecimalFormat("#")).format(speed)));
                  lastPositionUpdate = System.currentTimeMillis();
                  lastPositionX = this.minecraft.player.lastTickPosX;
                  lastPositionY = this.minecraft.player.lastTickPosY;
                  lastPositionZ = this.minecraft.player.lastTickPosZ;
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void playerBlockPlaced(RightClickBlock var1) {
      if (announcer.isToggled() && blockPlacedDelay >= 150 && (Boolean)Announcer.blockPlaced.getValue() && var1.getItemStack().getItem() instanceof ItemBlock) {
         this.sendMessage(AnnouncerUtil.getPlaced(var1.getItemStack().getDisplayName()));
         blockPlacedDelay = 0;
      }

   }

   @SubscribeEvent
   public void onKeyInput(KeyInputEvent var1) {
      if (announcer.isToggled()) {
         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindDrop.getKeyCode() && !this.heldItem.equals("Air") && dropItemDelay >= 150 && (Boolean)Announcer.itemDroped.getValue()) {
            this.sendMessage(AnnouncerUtil.getDropped(this.heldItem));
            dropItemDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindChat.getKeyCode() && chatDelay >= 150 && (Boolean)Announcer.openChat.getValue()) {
            this.sendMessage(AnnouncerUtil.getChat());
            chatDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindPickBlock.getKeyCode() && (Boolean)Announcer.pickBlock.getValue()) {
            this.sendMessage(AnnouncerUtil.getPickBlock());
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindCommand.getKeyCode() && commandDelay >= 150 && (Boolean)Announcer.command.getValue()) {
            this.sendMessage(AnnouncerUtil.getConsole());
            commandDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindFullscreen.getKeyCode() && (Boolean)Announcer.fullScreen.getValue()) {
            this.sendMessage(AnnouncerUtil.getFullScreen());
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == 1 && pauseDelay >= 150 && (Boolean)Announcer.pauseGame.getValue()) {
            this.sendMessage(AnnouncerUtil.getPause());
            pauseDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindInventory.getKeyCode() && inventoryDelay >= 150 && (Boolean)Announcer.openInv.getValue()) {
            this.sendMessage(AnnouncerUtil.getInventory());
            inventoryDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindPlayerList.getKeyCode() && playerListDelay >= 150 && (Boolean)Announcer.playerList.getValue()) {
            this.sendMessage(AnnouncerUtil.getPlayerList());
            playerListDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindScreenshot.getKeyCode() && (Boolean)Announcer.screenShot.getValue()) {
            this.sendMessage(AnnouncerUtil.getScreenShot());
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindSwapHands.getKeyCode() && (Boolean)Announcer.swapHand.getValue()) {
            this.sendMessage(AnnouncerUtil.getSwappedHands());
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindSneak.getKeyCode() && crouchedDelay >= 150 && (Boolean)Announcer.sneak.getValue()) {
            this.sendMessage(AnnouncerUtil.getCrouched());
            crouchedDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindTogglePerspective.getKeyCode() && perspectivesDelay >= 150 && (Boolean)Announcer.Perspective.getValue()) {
            this.sendMessage(AnnouncerUtil.getPerspectives());
            perspectivesDelay = 0;
         }

         if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == this.minecraft.gameSettings.keyBindJump.getKeyCode() && jumpDelay >= 300 && (Boolean)Announcer.jump.getValue()) {
            this.sendMessage(AnnouncerUtil.getJumped());
            jumpDelay = 0;
         }

         if (this.minecraft.player.isHandActive() && eattingDelay >= 300 && (Boolean)Announcer.eatting.getValue() && (this.minecraft.player.getHeldItemMainhand().getItem() instanceof ItemFood || this.minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold)) {
            this.sendMessage(AnnouncerUtil.getAte(this.minecraft.player.getHeldItemMainhand().getDisplayName()));
            eattingDelay = 0;
         }
      }

   }

   @SubscribeEvent
   public void itemCrafted(ItemCraftedEvent var1) {
      if (var1.player instanceof EntityPlayerSP && announcer.isToggled() && (Boolean)Announcer.craftedItem.getValue()) {
         this.sendMessage(AnnouncerUtil.getCraft(String.valueOf((new StringBuilder()).append(var1.crafting.getCount()).append(" ").append(var1.crafting.getDisplayName()))));
      }

   }

   private void sendMessage(String var1) {
      if (Announcer.delayy == 0) {
         this.minecraft.player.sendChatMessage(var1);
         Announcer.delayy = (Integer)Announcer.delay.getValue() & 20;
      }

   }

   public static void initAnnouncer() {
      announcer = Xulu.MODULE_MANAGER.getModuleByName("Announcer");
   }

   @SubscribeEvent
   public void playerRespawn(PlayerRespawnEvent var1) {
      if (announcer.isToggled() && (Boolean)Announcer.respawn.getValue()) {
         this.sendMessage(AnnouncerUtil.getRespawn());
      }

   }

   @SubscribeEvent
   public void playerBlockBroke(LeftClickBlock var1) {
      this.blockPos = var1.getPos();
      this.blockName = this.minecraft.world.getBlockState(var1.getPos()).getBlock().getLocalizedName();
      this.kek = 1;
   }
}
