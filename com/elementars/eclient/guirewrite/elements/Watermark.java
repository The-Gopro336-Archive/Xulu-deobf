package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;

public class Watermark extends Element {
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value text = this.register(new Value("Mode", this, "Xulu", new String[]{"Xulu", "PK Client", "WideHack"})).onChanged((var1) -> {
      this.width = (double)(fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append((String)var1.getNew()).append(" ").append("v1.5.2"))) + 2);
   });

   public void onEnable() {
      this.width = (double)(fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append((String)this.text.getValue()).append(" ").append("v1.5.2"))) + 2);
      this.height = (double)(fontRenderer.FONT_HEIGHT + 2);
      super.onEnable();
   }

   public void onRender() {
      int var1 = ColorUtil.getClickGUIColor().getRGB();
      if ((Boolean)this.rainbow.getValue()) {
         var1 = Xulu.rgb;
      }

      if (Xulu.CustomFont) {
         Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append((String)this.text.getValue()).append(" ").append("v1.5.2")), this.x + 1.0D, this.y + 1.0D, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      } else {
         Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append((String)this.text.getValue()).append(" ").append("v1.5.2")), (float)this.x + 1.0F, (float)this.y + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      }

   }

   public Watermark() {
      super("Watermark");
   }
}
