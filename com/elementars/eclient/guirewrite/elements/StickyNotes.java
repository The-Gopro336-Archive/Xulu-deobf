package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ListHelper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;

public class StickyNotes extends Element {
   // $FF: synthetic field
   public static String[] text = new String[]{"Put text here"};
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   public static String saveText;

   public void onMiddleClick() {
      TextNotes.initTextBox();
   }

   public static void processText(String var0) {
      text = var0.split("@");
      saveText = var0;
   }

   public StickyNotes() {
      super("StickyNotes");
   }

   public void onRender() {
      this.width = (double)(fontRenderer.getStringWidth(ListHelper.longest(text)) + 2);
      this.height = (double)((fontRenderer.FONT_HEIGHT + 1) * text.length + 1);
      int var1 = ColorUtil.getClickGUIColor().getRGB();
      if ((Boolean)this.rainbow.getValue()) {
         var1 = Xulu.rgb;
      }

      double var2 = this.y;
      String[] var4 = text;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4[var6];
         String var8 = var7.replaceAll("&", String.valueOf('§'));
         if (Xulu.CustomFont) {
            Xulu.cFontRenderer.drawStringWithShadow(var8, this.x + 1.0D, var2 + 1.0D, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
         } else {
            fontRenderer.drawStringWithShadow(var8, (float)this.x + 1.0F, (float)var2 + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
         }

         var2 += 10.0D;
      }

   }
}
