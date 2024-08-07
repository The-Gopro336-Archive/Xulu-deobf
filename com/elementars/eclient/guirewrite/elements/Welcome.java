package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import net.minecraft.client.gui.ScaledResolution;

public class Welcome extends Element {
   // $FF: synthetic field
   private final Value center = this.register(new Value("Center", this, false));
   // $FF: synthetic field
   public static String text = "Welcome NAME";
   // $FF: synthetic field
   private final Value dynamic = this.register(new Value("Dynamic", this, false));
   // $FF: synthetic field
   private final Value holiday = this.register(new Value("Holiday", this, true));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));

   public void onMiddleClick() {
      WelcomeNotes.initTextBox();
   }

   public Welcome() {
      super("Welcome");
   }

   public void onRender() {
      if (mc.player != null) {
         int var1 = ColorUtil.getClickGUIColor().getRGB();
         if ((Boolean)this.rainbow.getValue()) {
            var1 = Xulu.rgb;
         }

         String var2 = text.replaceAll("NAME", mc.player.getName());
         if ((Boolean)this.dynamic.getValue()) {
            var2 = this.getTimeMessage().replaceAll("NAME", mc.player.getName());
         }

         if ((Boolean)this.holiday.getValue() && this.getHoliday() != null) {
            var2 = this.getHoliday().replaceAll("NAME", mc.player.getName());
         }

         this.width = (double)(fontRenderer.getStringWidth(var2) + 2);
         this.height = (double)(fontRenderer.FONT_HEIGHT + 2);
         String var3 = var2.replaceAll("&", String.valueOf('§'));
         if ((Boolean)this.center.getValue()) {
            ScaledResolution var4 = new ScaledResolution(mc);
            if (Xulu.CustomFont) {
               Xulu.cFontRenderer.drawStringWithShadow(var3, (double)((float)var4.getScaledWidth() / 2.0F - (float)Xulu.cFontRenderer.getStringWidth(var3) / 2.0F + 1.0F), this.y + 1.0D, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
            } else {
               Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var3, (float)var4.getScaledWidth() / 2.0F - (float)Xulu.cFontRenderer.getStringWidth(var3) / 2.0F + 1.0F, (float)this.y + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
            }
         } else if (Xulu.CustomFont) {
            Xulu.cFontRenderer.drawStringWithShadow(var3, this.x + 1.0D, this.y + 1.0D, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
         } else {
            float var10002 = (float)this.x + 1.0F;
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var3, var10002, (float)this.y + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
         }

      }
   }

   private String getHoliday() {
      int var1 = Integer.valueOf((new SimpleDateFormat("MM")).format(new Date()));
      int var2 = Integer.valueOf((new SimpleDateFormat("dd")).format(new Date()));
      switch(var1) {
      case 1:
         if (var2 == 1) {
            return "Happy New Years NAME!";
         }
         break;
      case 2:
         if (var2 == 14) {
            return "Happy Valentines Day NAME!";
         }
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         break;
      case 10:
         if (var2 == 31) {
            return "Happy Halloween NAME! (spooky)";
         }
         break;
      case 11:
         LocalDate var3 = Year.of(Integer.valueOf((new SimpleDateFormat("yyyy")).format(new Date()))).atMonth(Month.NOVEMBER).atDay(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
         if (var3.getDayOfMonth() == var2) {
            return "Happy Thanksgiving NAME!";
         }
      case 12:
         if (var2 == 25) {
            return "Happy X-mas NAME!";
         }
      }

      return null;
   }

   public static void handleWelcome(String var0) {
      text = var0;
   }

   private String getTimeMessage() {
      String var1 = (new SimpleDateFormat("k")).format(new Date());
      int var2 = Integer.valueOf(var1);
      if (var2 < 6) {
         return "Good Night NAME";
      } else if (var2 < 12) {
         return "Good Morning NAME";
      } else if (var2 < 17) {
         return "Good Afternoon NAME";
      } else {
         return var2 < 20 ? "Good Evening NAME" : "Good Night NAME";
      }
   }
}
