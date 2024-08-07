package com.elementars.eclient.macro;

import com.elementars.eclient.command.CommandManager;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.network.play.client.CPacketChatMessage;

public class Macro {
   // $FF: synthetic field
   private String macro;
   // $FF: synthetic field
   private int key;

   public String getMacro() {
      return this.macro;
   }

   public void runMacro() {
      if (this.macro.startsWith(".")) {
         CommandManager.runCommand(this.macro.substring(1));
      } else if (Wrapper.getMinecraft().getConnection() != null) {
         Wrapper.getMinecraft().getConnection().sendPacket(new CPacketChatMessage(this.macro));
      }

   }

   public Macro(String var1, int var2) {
      this.macro = var1;
      this.key = var2;
   }

   public int getKey() {
      return this.key;
   }
}
