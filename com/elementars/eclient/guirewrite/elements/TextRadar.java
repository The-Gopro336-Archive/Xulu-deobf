package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.combat.PopCounter;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.module.render.ExtraTab;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

public class TextRadar extends Element {
   // $FF: synthetic field
   DecimalFormat decimalFormat = new DecimalFormat("#.#");
   // $FF: synthetic field
   private final Value pops = this.register(new Value("Pop Count", this, true));

   public void onEnable() {
      this.width = 80.0D;
      this.height = 80.0D;
      super.onEnable();
   }

   public void onRender() {
      float var1 = (float)this.y;
      Iterator var2 = mc.world.playerEntities.iterator();

      while(true) {
         EntityPlayer var3;
         do {
            if (!var2.hasNext()) {
               return;
            }

            var3 = (EntityPlayer)var2.next();
         } while(var3.getName().equals(mc.player.getName()));

         if (Xulu.CustomFont) {
            Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("- ").append(Friends.isFriend(var3.getName()) ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)ExtraTab.INSTANCE.color.getValue()).substring(1)).append(var3.getName())) : var3.getName()).append(" ").append(ChatFormatting.RED).append(this.decimalFormat.format((double)var3.getHealth())).append(" ").append(ChatFormatting.DARK_GREEN).append((int)mc.player.getDistance(var3)).append(PopCounter.INSTANCE.popMap.containsKey(var3) && (Boolean)this.pops.getValue() ? String.valueOf((new StringBuilder()).append(" ").append(ChatFormatting.DARK_RED).append("-").append(PopCounter.INSTANCE.popMap.get(var3))) : "")), (double)((float)this.x), (double)var1, ColorUtils.changeAlpha(ColorUtils.Colors.WHITE, (Integer)Global.hudAlpha.getValue()));
         } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("- ").append(Friends.isFriend(var3.getName()) ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)ExtraTab.INSTANCE.color.getValue()).substring(1)).append(var3.getName())) : var3.getName()).append(" ").append(ChatFormatting.RED).append(this.decimalFormat.format((double)var3.getHealth())).append(" ").append(ChatFormatting.DARK_GREEN).append((int)mc.player.getDistance(var3)).append(PopCounter.INSTANCE.popMap.containsKey(var3) && (Boolean)this.pops.getValue() ? String.valueOf((new StringBuilder()).append(" ").append(ChatFormatting.DARK_RED).append("-").append(PopCounter.INSTANCE.popMap.get(var3))) : "")), (float)this.x, var1, ColorUtils.changeAlpha(ColorUtils.Colors.WHITE, (Integer)Global.hudAlpha.getValue()));
         }

         var1 += 10.0F;
      }
   }

   public TextRadar() {
      super("TextRadar");
   }
}
