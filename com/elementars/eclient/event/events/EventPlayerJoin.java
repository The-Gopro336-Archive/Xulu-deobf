package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import com.mojang.authlib.GameProfile;

public class EventPlayerJoin extends Event {
   // $FF: synthetic field
   GameProfile gameProfile;

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public EventPlayerJoin(GameProfile var1) {
      this.gameProfile = var1;
   }
}
