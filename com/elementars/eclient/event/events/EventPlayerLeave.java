package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import com.mojang.authlib.GameProfile;

public class EventPlayerLeave extends Event {
   // $FF: synthetic field
   GameProfile gameProfile;

   public EventPlayerLeave(GameProfile var1) {
      this.gameProfile = var1;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }
}
