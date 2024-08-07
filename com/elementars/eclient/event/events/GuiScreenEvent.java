package com.elementars.eclient.event.events;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenEvent {
   // $FF: synthetic field
   private GuiScreen screen;

   public GuiScreen getScreen() {
      return this.screen;
   }

   public void setScreen(GuiScreen var1) {
      this.screen = var1;
   }

   public GuiScreenEvent(GuiScreen var1) {
      this.screen = var1;
   }

   public static class Displayed extends GuiScreenEvent {
      public Displayed(GuiScreen var1) {
         super(var1);
      }
   }

   public static class Closed extends GuiScreenEvent {
      public Closed(GuiScreen var1) {
         super(var1);
      }
   }
}
