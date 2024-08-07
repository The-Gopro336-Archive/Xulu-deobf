package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ListHelper;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;

public class TheGoons extends Element {
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Friend Green", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Friend Blue", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Friend Red", this, 255, 0, 255));

   public void onRender() {
      int var1 = ColorUtil.getClickGUIColor().getRGB();
      if ((Boolean)this.rainbow.getValue()) {
         var1 = Xulu.rgb;
      }

      List var2 = (List)mc.world.playerEntities.stream().filter((var0) -> {
         return Friends.isFriend(var0.getName());
      }).map(EntityPlayer::func_70005_c_).collect(Collectors.toList());
      var2.add("The Goons");
      this.width = (double)(fontRenderer.getStringWidth(ListHelper.longest(var2)) + 2);
      this.height = (double)((fontRenderer.FONT_HEIGHT + 1) * var2.size() + 1);
      float var3 = (float)this.y;
      if (Xulu.CustomFont) {
         Xulu.cFontRenderer.drawStringWithShadow("The Goons", (double)((float)this.x + 1.0F), (double)(var3 + 1.0F), ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      } else {
         float var10002 = (float)this.x + 1.0F;
         Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("The Goons", var10002, var3 + 1.0F, ColorUtils.changeAlpha(var1, (Integer)Global.hudAlpha.getValue()));
      }

      var3 += 10.0F;
      Iterator var4 = mc.world.playerEntities.iterator();

      while(var4.hasNext()) {
         EntityPlayer var5 = (EntityPlayer)var4.next();
         if (!var5.getName().equals(mc.player.getName()) && Friends.isFriend(var5.getName())) {
            if (Xulu.CustomFont) {
               Xulu.cFontRenderer.drawStringWithShadow(var5.getName(), (double)((float)this.x + 1.0F), (double)(var3 + 1.0F), (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (Integer)Global.hudAlpha.getValue())).getRGB());
            } else {
               Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var5.getName(), (float)this.x + 1.0F, var3 + 1.0F, (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (Integer)Global.hudAlpha.getValue())).getRGB());
            }

            var3 += 10.0F;
         }
      }

   }

   public TheGoons() {
      super("TheGoons");
   }
}
