package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class AutoQMain extends Module {
   // $FF: synthetic field
   private int wait;
   // $FF: synthetic field
   private final Value message = this.register(new Value("Debug Messages", this, false));
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Seconds", this, 120, 10, 600));

   public void onEnable() {
      this.wait = 12000;
   }

   public void onUpdate() {
      if (mc.getCurrentServerData() == null || mc.getCurrentServerData() != null && !mc.getCurrentServerData().serverIP.equals("2b2t.org")) {
         this.wait = 0;
         Command.sendChatMessage("Server not 2b2t.org, disabling...");
         this.disable();
      } else {
         if (this.wait <= 0) {
            mc.player.sendChatMessage("/queue main");
            if ((Boolean)this.message.getValue()) {
               Command.sendChatMessage("Did /queue main");
            }

            this.wait = (Integer)this.delay.getValue();
         }

         --this.wait;
      }

   }

   public AutoQMain() {
      super("AutoQMain", "Automatically sends /queue main", 0, Category.MISC, true);
   }
}
