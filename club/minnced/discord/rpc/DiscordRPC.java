package club.minnced.discord.rpc;

import com.sun.jna.Library;
import com.sun.jna.Native;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DiscordRPC extends Library {
   // $FF: synthetic field
   int DISCORD_REPLY_YES = 1;
   // $FF: synthetic field
   int DISCORD_REPLY_NO = 0;
   // $FF: synthetic field
   int DISCORD_REPLY_IGNORE = 2;
   // $FF: synthetic field
   DiscordRPC INSTANCE = (DiscordRPC)Native.loadLibrary("discord-rpc", DiscordRPC.class);

   void Discord_RunCallbacks();

   void Discord_RegisterSteamGame(String var1, String var2);

   void Discord_Respond(@Nonnull String var1, int var2);

   void Discord_UpdatePresence(@Nullable DiscordRichPresence var1);

   void Discord_Shutdown();

   void Discord_Register(String var1, String var2);

   void Discord_UpdateConnection();

   void Discord_ClearPresence();

   void Discord_Initialize(@Nonnull String var1, @Nullable DiscordEventHandlers var2, boolean var3, @Nullable String var4);

   void Discord_UpdateHandlers(@Nullable DiscordEventHandlers var1);
}
