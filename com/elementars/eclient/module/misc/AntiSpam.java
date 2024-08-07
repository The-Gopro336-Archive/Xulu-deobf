package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiSpam extends Module {
   // $FF: synthetic field
   private final Value greenText = this.register(new Value("Green Text", this, true));
   // $FF: synthetic field
   private final Value filterOwn = this.register(new Value("Filter Own", this, true));
   // $FF: synthetic field
   private final Value debug = this.register(new Value("Debug Messages", this, true));
   // $FF: synthetic field
   SPacketChat sPacketChat;
   // $FF: synthetic field
   private final Value discordLinks = this.register(new Value("Discord Links", this, true));
   // $FF: synthetic field
   private final Value webLinks = this.register(new Value("Web Links", this, true));
   // $FF: synthetic field
   private final Value insulters = this.register(new Value("Insulters", this, true));
   // $FF: synthetic field
   private final Value duplicatesTimeout = this.register(new Value("Duplicates Timeout", this, 10, 1, 600));
   // $FF: synthetic field
   private final Value spammers = this.register(new Value("Spammers", this, true));
   // $FF: synthetic field
   private final Value announcers = this.register(new Value("Announcers", this, true));
   // $FF: synthetic field
   private ConcurrentHashMap messageHistory;
   // $FF: synthetic field
   private final Value duplicates = this.register(new Value("Duplicates", this, true));
   // $FF: synthetic field
   private final Value tradeChat = this.register(new Value("Trade Chat", this, true));

   public AntiSpam() {
      super("AntiSpam", "Hides spam", 0, Category.MISC, true);
   }

   @SubscribeEvent
   public void onPacket(ClientChatReceivedEvent var1) {
      if (mc.player != null && this.isToggled() && this.detectSpam(var1.getMessage().getUnformattedText())) {
         var1.setCanceled(true);
      }

   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
      this.messageHistory = null;
   }

   private boolean detectSpam(String var1) {
      if (!(Boolean)this.filterOwn.getValue() && this.findPatterns(AntiSpam.FilterPatterns.ownMessage, var1)) {
         return false;
      } else if ((Boolean)this.greenText.getValue() && this.findPatterns(AntiSpam.FilterPatterns.greenText, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Green Text: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.discordLinks.getValue() && this.findPatterns(AntiSpam.FilterPatterns.discord, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Discord Link: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.webLinks.getValue() && this.findPatterns(AntiSpam.FilterPatterns.webLink, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Web Link: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.tradeChat.getValue() && this.findPatterns(AntiSpam.FilterPatterns.tradeChat, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Trade Chat: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.announcers.getValue() && this.findPatterns(AntiSpam.FilterPatterns.announcer, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Announcer: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.spammers.getValue() && this.findPatterns(AntiSpam.FilterPatterns.spammer, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Spammers: ").append(var1)));
         }

         return true;
      } else if ((Boolean)this.insulters.getValue() && this.findPatterns(AntiSpam.FilterPatterns.insulter, var1)) {
         if ((Boolean)this.debug.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Insulter: ").append(var1)));
         }

         return true;
      } else {
         if ((Boolean)this.duplicates.getValue()) {
            if (this.messageHistory == null) {
               this.messageHistory = new ConcurrentHashMap();
            }

            boolean var2 = false;
            if (this.messageHistory.containsKey(var1) && (System.currentTimeMillis() - (Long)this.messageHistory.get(var1)) / 1000L < (long)(Integer)this.duplicatesTimeout.getValue()) {
               var2 = true;
            }

            this.messageHistory.put(var1, System.currentTimeMillis());
            if (var2) {
               if ((Boolean)this.debug.getValue()) {
                  this.sendDebugMessage(String.valueOf((new StringBuilder()).append("Duplicate: ").append(var1)));
               }

               return true;
            }
         }

         return false;
      }
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
      this.messageHistory = new ConcurrentHashMap();
   }

   private boolean findPatterns(String[] var1, String var2) {
      String[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         if (Pattern.compile(var6).matcher(var2).find()) {
            return true;
         }
      }

      return false;
   }

   private static class FilterPatterns {
      // $FF: synthetic field
      private static final String[] discord = new String[]{"discord.gg"};
      // $FF: synthetic field
      private static final String[] tradeChat = new String[]{"buy", "sell"};
      // $FF: synthetic field
      private static final String[] spammer = new String[]{"WWE Client's spammer", "Lol get gud", "Future client is bad", "WWE > Future", "WWE > Impact", "Default Message", "IKnowImEZ is a god", "THEREALWWEFAN231 is a god", "WWE Client made by IKnowImEZ/THEREALWWEFAN231", "WWE Client was the first public client to have Path Finder/New Chunks", "WWE Client was the first public client to have color signs", "WWE Client was the first client to have Teleport Finder", "WWE Client was the first client to have Tunneller & Tunneller Back Fill"};
      // $FF: synthetic field
      private static final String[] announcer = new String[]{"I just walked .+ feet!", "I just placed a .+!", "I just attacked .+ with a .+!", "I just dropped a .+!", "I just opened chat!", "I just opened my console!", "I just opened my GUI!", "I just went into full screen mode!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just took a screen shot!", "I just swaped hands!", "I just ducked!", "I just changed perspectives!", "I just jumped!", "I just ate a .+!", "I just crafted .+ .+!", "I just picked up a .+!", "I just smelted .+ .+!", "I just respawned!", "I just attacked .+ with my hands", "I just broke a .+!", "I recently walked .+ blocks", "I just droped a .+ called, .+!", "I just placed a block called, .+!", "Im currently breaking a block called, .+!", "I just broke a block called, .+!", "I just opened chat!", "I just opened chat and typed a slash!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just changed perspectives, now im in .+!", "I just crouched!", "I just jumped!", "I just attacked a entity called, .+ with a .+", "Im currently eatting a peice of food called, .+!", "Im currently using a item called, .+!", "I just toggled full screen mode!", "I just took a screen shot!", "I just swaped hands and now theres a .+ in my main hand and a .+ in my off hand!", "I just used pick block on a block called, .+!", "Ra just completed his blazing ark", "Its a new day yes it is"};
      // $FF: synthetic field
      private static final String[] greenText = new String[]{"^<.+> >"};
      // $FF: synthetic field
      private static final String[] insulter = new String[]{".+ Download WWE utility mod, Its free!", ".+ 4b4t is da best mintscreft serber", ".+ dont abouse", ".+ you cuck", ".+ https://www.youtube.com/channel/UCJGCNPEjvsCn0FKw3zso0TA", ".+ is my step dad", ".+ again daddy!", "dont worry .+ it happens to every one", ".+ dont buy future it's crap, compared to WWE!", "What are you, fucking gay, .+?", "Did you know? .+ hates you, .+", "You are literally 10, .+", ".+ finally lost their virginity, sadly they lost it to .+... yeah, that's unfortunate.", ".+, don't be upset, it's not like anyone cares about you, fag.", ".+, see that rubbish bin over there? Get your ass in it, or I'll get .+ to whoop your ass.", ".+, may I borrow that dirt block? that guy named .+ needs it...", "Yo, .+, btfo you virgin", "Hey .+ want to play some High School RP with me and .+?", ".+ is an Archon player. Why is he on here? Fucking factions player.", "Did you know? .+ just joined The Vortex Coalition!", ".+ has successfully conducted the cactus dupe and duped a itemhand!", ".+, are you even human? You act like my dog, holy shit.", ".+, you were never loved by your family.", "Come on .+, you hurt .+'s feelings. You meany.", "Stop trying to meme .+, you can't do that. kek", ".+, .+ is gay. Don't go near him.", "Whoa .+ didn't mean to offend you, .+.", ".+ im not pvping .+, im WWE'ing .+.", "Did you know? .+ just joined The Vortex Coalition!", ".+, are you even human? You act like my dog, holy shit."};
      // $FF: synthetic field
      private static final String[] ownMessage;
      // $FF: synthetic field
      private static final String[] webLink = new String[]{"http:\\/\\/", "https:\\/\\/"};

      static {
         ownMessage = new String[]{String.valueOf((new StringBuilder()).append("^<").append(AntiSpam.mc.player.getName()).append("> ")), "^To .+: "};
      }
   }
}
