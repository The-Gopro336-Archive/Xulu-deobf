package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;

public class AllowInteractEvent extends Event {
   // $FF: synthetic field
   public boolean usingItem;

   public AllowInteractEvent(boolean var1) {
      this.usingItem = var1;
   }
}
