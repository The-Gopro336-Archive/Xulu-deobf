package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import java.util.UUID;

public class EventPlayerConnect extends Event {
   // $FF: synthetic field
   UUID uuid;
   // $FF: synthetic field
   String name;

   public EventPlayerConnect(UUID var1, String var2) {
      this.uuid = var1;
      this.name = var2;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }

   public static class Leave extends EventPlayerConnect {
      public Leave(UUID var1, String var2) {
         super(var1, var2);
      }
   }

   public static class Join extends EventPlayerConnect {
      public Join(UUID var1, String var2) {
         super(var1, var2);
      }
   }
}
