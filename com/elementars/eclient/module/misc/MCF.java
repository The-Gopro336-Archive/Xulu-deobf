package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventMiddleClick;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

public class MCF extends Module {
   // $FF: synthetic field
   private final Value message = this.register(new Value("Send Message", this, true));

   public MCF() {
      super("MCF", "Middle Click Friends", 0, Category.MISC, true);
   }

   @EventTarget
   public void onMiddleClick(EventMiddleClick var1) {
      RayTraceResult var2 = mc.objectMouseOver;
      if (var2.typeOfHit == Type.ENTITY) {
         Entity var3 = var2.entityHit;
         if (var3 instanceof EntityPlayer) {
            String var4 = ((EntityPlayer)var3).getDisplayNameString();
            if (Friends.isFriend(var4)) {
               Friends.delFriend(var4);
               if ((Boolean)this.message.getValue()) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var4).append("&r has been unfriended.")));
               }
            } else {
               Friends.addFriend(var4);
               if ((Boolean)this.message.getValue()) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var4).append("&r has been friended.")));
               }
            }
         }
      }

   }
}
