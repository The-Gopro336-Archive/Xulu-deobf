package com.elementars.eclient.module.combat;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.settings.Value;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DurabilityAlert extends Module {
   // $FF: synthetic field
   private final Value ignoreself = this.register(new Value("Ignore Self", this, false));
   // $FF: synthetic field
   ConcurrentHashMap players = new ConcurrentHashMap();
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   private final Value color;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Chat", new String[]{"Chat", "Notification"}));
   // $FF: synthetic field
   private final Value ignorefriends = this.register(new Value("Ignore Friends", this, false));

   public void onUpdate() {
      Iterator var1 = mc.world.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if ((Boolean)this.ignoreself.getValue() && var2.getName().equalsIgnoreCase(mc.player.getName())) {
            return;
         }

         if ((Boolean)this.ignorefriends.getValue() && Friends.isFriend(var2.getName())) {
            return;
         }

         Iterator var3 = var2.getArmorInventoryList().iterator();

         while(var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            if (var4 != null && var4.getItem().getDurabilityForDisplay(var4) > 0.75D && !this.players.containsKey(var2.getName())) {
               if (((String)this.mode.getValue()).equalsIgnoreCase("Chat")) {
                  if ((Boolean)this.watermark.getValue()) {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var2.getName()).append(" has low durability!")));
                  } else {
                     Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var2.getName()).append(" has low durability!")));
                  }
               }

               this.players.put(var2.getName(), 1500);
            }
         }
      }

      this.players.forEach((var1x, var2x) -> {
         if (var2x <= 0) {
            this.players.remove(var1x);
         } else {
            this.players.put(var1x, var2x - 1);
         }

      });
   }

   public DurabilityAlert() {
      super("DurabilityAlert", "Alerts when someone has low durability", 0, Category.COMBAT, true);
      this.color = this.register(new Value("Color", this, "White", ColorTextUtils.colors));
   }

   public void onRender() {
      if (((String)this.mode.getValue()).equalsIgnoreCase("Notification")) {
         ScaledResolution var1 = new ScaledResolution(mc);
         int var2 = (int)((double)(var1.getScaledHeight() / 2) - (double)(var1.getScaledHeight() / 2) * 0.9D) - mc.fontRenderer.FONT_HEIGHT / 2;
         Iterator var3 = this.players.keySet().iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if ((Integer)this.players.get(var4) > 1000) {
               mc.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append(var4).append(" has low durability!")), (float)(var1.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var4).append(" has low durability!"))) / 2), (float)var2, ColorUtils.Colors.RED);
               var2 += 10;
            }
         }
      }

   }
}
