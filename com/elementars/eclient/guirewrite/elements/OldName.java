package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;

public class OldName extends Element {
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));

   public void onEnable() {
      this.width = (double)(fontRenderer.getStringWidth("Elementars.com") + 2);
      this.height = (double)(fontRenderer.FONT_HEIGHT + 2);
      super.onEnable();
   }

   public void onRender() {
      int var1 = ColorUtil.getClickGUIColor().getRGB();
      if ((Boolean)this.rainbow.getValue()) {
         var1 = Xulu.rgb;
      }

      if (Xulu.CustomFont) {
         Xulu.cFontRenderer.drawStringWithShadow("Elementars.com", this.x + 1.0D, this.y + 1.0D, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      } else {
         float var10002 = (float)this.x + 1.0F;
         Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("Elementars.com", var10002, (float)this.y + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      }

   }

   public OldName() {
      super("OldName");
   }
}
