package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.EntityUtil;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class VisualRange extends Module {
   // $FF: synthetic field
   public ArrayList names;
   // $FF: synthetic field
   private final Value color = this.register(new Value("Color", this, false));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Send Message", this, false));
   // $FF: synthetic field
   public ArrayList removal;
   // $FF: synthetic field
   private final Value ignoreFriends = this.register(new Value("Ignore Friends", this, false));
   // $FF: synthetic field
   private final Value sf = this.register(new Value("No Friend Send", this, false));
   // $FF: synthetic field
   private final Value vr = this.register(new Value("VisualRange", this, true));
   // $FF: synthetic field
   private final Value selectcolor;
   // $FF: synthetic field
   public ArrayList names2;
   // $FF: synthetic field
   private int delay;
   // $FF: synthetic field
   private final Value delayN = this.register(new Value("Delay", this, 15, 1, 30));
   // $FF: synthetic field
   private final Value message = this.register(new Value("Message", this, "hello uwu", new ArrayList(Arrays.asList("Change this in the settings"))));
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));

   private void testLeave() {
      this.names.forEach((var1) -> {
         if (!this.names2.contains(var1)) {
            this.removal.add(var1);
         }

      });
      this.removal.forEach((var1) -> {
         this.names.remove(var1);
      });
      this.removal.clear();
   }

   private void sendMessage(Entity var1) {
      if ((Boolean)this.mode.getValue() && this.delay == 0) {
         if ((Boolean)this.sf.getValue() && Friends.isFriend(var1.getName())) {
            return;
         }

         mc.player.sendChatMessage(String.valueOf((new StringBuilder()).append("/msg ").append(var1.getName()).append(" ").append((String)this.message.getValue())));
         this.delay = (Integer)this.delayN.getValue() * 20;
      }

      if ((Boolean)this.vr.getValue()) {
         if ((Boolean)this.ignoreFriends.getValue() && Friends.isFriend(var1.getName())) {
            return;
         }

         if ((Boolean)this.watermark.getValue()) {
            if ((Boolean)this.color.getValue()) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.selectcolor.getValue())).append(var1.getName()).append(" entered visual range")));
            } else {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append(var1.getName()).append(" entered visual range")));
            }
         } else if ((Boolean)this.color.getValue()) {
            Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.selectcolor.getValue())).append(var1.getName()).append(" entered visual range")));
         } else {
            Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(var1.getName()).append(" entered visual range")));
         }
      }

   }

   public void onUpdate() {
      if (this.delay > 0) {
         --this.delay;
      }

   }

   public void onWorldRender(RenderEvent var1) {
      this.names2.clear();
      Minecraft.getMinecraft().world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter((var0) -> {
         return !EntityUtil.isFakeLocalPlayer(var0);
      }).filter((var0) -> {
         return var0 instanceof EntityPlayer;
      }).filter((var0) -> {
         return !(var0 instanceof EntityPlayerSP);
      }).forEach(this::testName);
      this.testLeave();
   }

   private void testName(Entity var1) {
      this.names2.add(var1.getName());
      if (!this.names.contains(var1.getName())) {
         this.sendMessage(var1);
         this.names.add(var1.getName());
      }

   }

   public VisualRange() {
      super("VisualRange", "Alerts people appearing in your visual range", 0, Category.MISC, true);
      this.selectcolor = this.register(new Value("Color Pick", this, "LightGreen", ColorTextUtils.colors));
      this.names = new ArrayList();
      this.names2 = new ArrayList();
      this.removal = new ArrayList();
   }
}
