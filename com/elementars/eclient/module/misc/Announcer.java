package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.events.AnnouncerRegistry;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraftforge.common.MinecraftForge;

public class Announcer extends Module {
   // $FF: synthetic field
   public static Value command;
   // $FF: synthetic field
   public static Value swapHand;
   // $FF: synthetic field
   public static Value blockPlaced;
   // $FF: synthetic field
   public static Value screenShot;
   // $FF: synthetic field
   private AnnouncerRegistry announcerRegistry = new AnnouncerRegistry();
   // $FF: synthetic field
   public static Value pickUpItem;
   // $FF: synthetic field
   public static Value itemDroped;
   // $FF: synthetic field
   public static Value pickBlock;
   // $FF: synthetic field
   public static Value craftedItem;
   // $FF: synthetic field
   public static Value fullScreen;
   // $FF: synthetic field
   public static Value jump;
   // $FF: synthetic field
   public static Value sneak;
   // $FF: synthetic field
   public static Value blockBroke;
   // $FF: synthetic field
   public static Value pauseGame;
   // $FF: synthetic field
   public static Value attack;
   // $FF: synthetic field
   public static Value respawn;
   // $FF: synthetic field
   public static Value smeltedItem;
   // $FF: synthetic field
   public static Value openChat;
   // $FF: synthetic field
   public static Value eatting;
   // $FF: synthetic field
   public static Value openInv;
   // $FF: synthetic field
   public static Value delay;
   // $FF: synthetic field
   public static Value mode;
   // $FF: synthetic field
   public static int delayy;
   // $FF: synthetic field
   public static Value playerList;
   // $FF: synthetic field
   public static Value walk;
   // $FF: synthetic field
   public static Value Perspective;

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this.announcerRegistry);
      Command.sendChatMessage("Announcer ON");
   }

   public Announcer() {
      super("Announcer", "Announce EVERYTHING in chat", 0, Category.MISC, true);
      mode = this.register(new Value("Mode", this, "English", new ArrayList(Arrays.asList("English", "Hebrew"))));
      delay = this.register(new Value("Delay", this, 10, 0, 60));
      walk = this.register(new Value("Walk", this, true));
      craftedItem = this.register(new Value("CraftedItem", this, true));
      pickUpItem = this.register(new Value("PickUpItem", this, true));
      smeltedItem = this.register(new Value("SmeltedItem", this, true));
      respawn = this.register(new Value("Respawn", this, true));
      blockPlaced = this.register(new Value("BlockPlaced", this, true));
      blockBroke = this.register(new Value("BlockBroke", this, true));
      itemDroped = this.register(new Value("ItemDropped", this, true));
      openChat = this.register(new Value("OpenChat", this, true));
      pickBlock = this.register(new Value("PickBlock", this, true));
      command = this.register(new Value("Command", this, true));
      fullScreen = this.register(new Value("FullScreen", this, true));
      pauseGame = this.register(new Value("PauseGame", this, true));
      openInv = this.register(new Value("OpenInv", this, true));
      playerList = this.register(new Value("PlayerList", this, true));
      screenShot = this.register(new Value("ScreenShot", this, true));
      swapHand = this.register(new Value("SwapHand", this, true));
      sneak = this.register(new Value("Sneak", this, true));
      Perspective = this.register(new Value("Perspective", this, true));
      jump = this.register(new Value("Jump", this, true));
      attack = this.register(new Value("Attack", this, true));
      eatting = this.register(new Value("Eating", this, true));
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this.announcerRegistry);
      Command.sendChatMessage("Announcer OFF");
   }

   public void onUpdate() {
      if (delayy > 0) {
         --delayy;
      }

   }
}
