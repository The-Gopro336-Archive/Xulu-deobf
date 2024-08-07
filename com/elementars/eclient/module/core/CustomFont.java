package com.elementars.eclient.module.core;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.font.CFontManager;
import com.elementars.eclient.font.XFontRenderer;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.TextBox;
import dev.xulu.settings.Value;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomFont extends Module {
   // $FF: synthetic field
   public static Value customFontMode;
   // $FF: synthetic field
   public static Value metrics;
   // $FF: synthetic field
   public static Value FONT_STYLE;
   // $FF: synthetic field
   public static Value fontOffset;
   // $FF: synthetic field
   public static Value shadow;
   // $FF: synthetic field
   public static Value FONT;
   // $FF: synthetic field
   public static Value antiAlias;
   // $FF: synthetic field
   public static Value FONT_SIZE;

   public static void updateFont(String var0, int var1, int var2, boolean var3, boolean var4) {
      String var5 = (String)customFontMode.getValue();
      byte var6 = -1;
      switch(var5.hashCode()) {
      case -1955878649:
         if (var5.equals("Normal")) {
            var6 = 0;
         }
         break;
      case 84359069:
         if (var5.equals("Xdolf")) {
            var6 = 1;
         }
      }

      switch(var6) {
      case 0:
         try {
            if (var0.equalsIgnoreCase("Comfortaa Regular")) {
               CFontManager.customFont = new com.elementars.eclient.font.custom.CustomFont(new Font("Comfortaa Regular", var1, var2), var3, var4);
               return;
            }

            CFontManager.customFont = new com.elementars.eclient.font.custom.CustomFont(new Font(Xulu.getCorrectFont(var0), var1, var2), var3, var4);
         } catch (Exception var9) {
            var9.printStackTrace();
         }
         break;
      case 1:
         try {
            if (var0.equalsIgnoreCase("Comfortaa Regular")) {
               CFontManager.xFontRenderer = new XFontRenderer(new Font("Comfortaa Regular", var1, var2 * 2), var3, 8);
               return;
            }

            CFontManager.xFontRenderer = new XFontRenderer(new Font(Xulu.getCorrectFont(var0), var1, var2 * 2), var3, 8);
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

   }

   public CustomFont() {
      super("CustomFont", "Custom in game text rendering", 0, Category.CORE, false);
      customFontMode = this.register(new Value("Mode", this, "Normal", new ArrayList(Arrays.asList("Normal", "Xdolf", "Rainbow"))));
      FONT = this.register(new Value("Font", this, new TextBox("Verdana"))).newValueFilter((var0) -> {
         return Xulu.getCorrectFont(var0.getText()) != null;
      }).withFilterError("Not a font! (Case-sensitive)").onChanged((var0) -> {
         updateFont(((TextBox)var0.getNew()).getText(), (Integer)FONT_STYLE.getValue(), (Integer)FONT_SIZE.getValue(), (Boolean)antiAlias.getValue(), (Boolean)metrics.getValue());
      }).visibleWhen((var0) -> {
         return !((String)customFontMode.getValue()).equalsIgnoreCase("Rainbow");
      });
      FONT_STYLE = this.register(new Value("Font Style", this, 0, 0, 2)).onChanged((var0) -> {
         updateFont(((TextBox)FONT.getValue()).getText(), (Integer)var0.getNew(), (Integer)FONT_SIZE.getValue(), (Boolean)antiAlias.getValue(), (Boolean)metrics.getValue());
      }).visibleWhen((var0) -> {
         return !((String)customFontMode.getValue()).equalsIgnoreCase("Rainbow");
      });
      FONT_SIZE = this.register(new Value("Font Size", this, 18, 5, 50)).onChanged((var0) -> {
         updateFont(((TextBox)FONT.getValue()).getText(), (Integer)FONT_STYLE.getValue(), (Integer)var0.getNew(), (Boolean)antiAlias.getValue(), (Boolean)metrics.getValue());
      }).visibleWhen((var0) -> {
         return !((String)customFontMode.getValue()).equalsIgnoreCase("Rainbow");
      });
      antiAlias = this.register(new Value("Anti Alias", this, true)).visibleWhen((var0) -> {
         return ((String)customFontMode.getValue()).equalsIgnoreCase("Normal") || ((String)customFontMode.getValue()).equalsIgnoreCase("Xdolf");
      }).onChanged((var0) -> {
         updateFont(((TextBox)FONT.getValue()).getText(), (Integer)FONT_STYLE.getValue(), (Integer)FONT_SIZE.getValue(), (Boolean)var0.getNew(), (Boolean)metrics.getValue());
      });
      metrics = this.register(new Value("Metrics", this, true)).visibleWhen((var0) -> {
         return ((String)customFontMode.getValue()).equalsIgnoreCase("Normal");
      }).onChanged((var0) -> {
         updateFont(((TextBox)FONT.getValue()).getText(), (Integer)FONT_STYLE.getValue(), (Integer)FONT_SIZE.getValue(), (Boolean)antiAlias.getValue(), (Boolean)var0.getNew());
      });
      shadow = this.register(new Value("Shadow", this, true)).visibleWhen((var0) -> {
         return ((String)customFontMode.getValue()).equalsIgnoreCase("Normal");
      });
      fontOffset = this.register(new Value("Font Offset", this, 0, -5, 5)).visibleWhen((var0) -> {
         return !((String)customFontMode.getValue()).equalsIgnoreCase("Rainbow");
      });
   }
}
