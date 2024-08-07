package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.PotionUtil;
import com.elementars.eclient.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class Potions extends Element {
   // $FF: synthetic field
   Value onlysw = this.register(new Value("Only Str & Weak", this, false));
   // $FF: synthetic field
   Value pc = this.register(new Value("Use Potion Color", this, true));
   // $FF: synthetic field
   Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   DecimalFormat df = new DecimalFormat("00");
   // $FF: synthetic field
   Value order = this.register(new Value("Order", this, "Up", new ArrayList(Arrays.asList("Up", "Down"))));
   // $FF: synthetic field
   Value align = this.register(new Value("Align", this, "Left", new ArrayList(Arrays.asList("Left", "Right"))));
   // $FF: synthetic field
   Value green = this.register(new Value("Green", this, 0, 0, 255));
   // $FF: synthetic field
   Value red = this.register(new Value("Red", this, 0, 0, 255));

   public void onEnable() {
      this.width = 50.0D;
      this.height = 50.0D;
   }

   public void onRender() {
      float var1 = (float)this.y;
      float var2 = (float)this.x;
      if (mc.player != null) {
         ArrayList var3 = new ArrayList();
         if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
            var1 = (float)((double)var1 + (this.height - 10.0D));
         }

         Iterator var4 = mc.player.getActivePotionEffects().iterator();

         while(true) {
            PotionEffect var5;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               var5 = (PotionEffect)var4.next();
            } while((Boolean)this.onlysw.getValue() && var5.getPotion() != MobEffects.STRENGTH && var5.getPotion() != MobEffects.WEAKNESS);

            String var6 = String.valueOf((new StringBuilder()).append(PotionUtil.getPotionName(var5.getPotion())).append(" ").append(var5.getAmplifier() + 1 != 1 ? String.valueOf((new StringBuilder()).append(var5.getAmplifier() + 1).append(" ")) : "").append(ChatFormatting.GRAY).append(this.getTime(var5.getDuration() / 20).length() == 1 ? "0" : "").append(this.getTime(var5.getDuration() / 20)));
            int var7 = (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB();
            if ((Boolean)this.pc.getValue()) {
               var7 = var5.getPotion().getLiquidColor();
            }

            if (Xulu.CustomFont) {
               Xulu.cFontRenderer.drawStringWithShadow(var6, (((String)this.align.getValue()).equalsIgnoreCase("Right") ? (double)(var2 - (float)Xulu.cFontRenderer.getStringWidth(var6)) + this.getFrame().width : (double)var2) + 1.0D, (double)(var1 + 1.0F), ColorUtils.changeAlpha(var7, (Integer)Global.hudAlpha.getValue()));
            } else {
               Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var6, (float)(((String)this.align.getValue()).equalsIgnoreCase("Right") ? (double)(var2 - (float)Wrapper.getMinecraft().fontRenderer.getStringWidth(var6)) + this.getFrame().width : (double)var2) + 1.0F, var1 + 1.0F, ColorUtils.changeAlpha(var7, (Integer)Global.hudAlpha.getValue()));
            }

            var3.add(var6);
            var1 += (float)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10);
         }
      }
   }

   public Potions() {
      super("Potions");
   }

   public String getTime(int var1) {
      int var2;
      for(var2 = 0; var1 > 59; ++var2) {
         var1 -= 60;
      }

      return String.valueOf((new StringBuilder()).append(var2).append(":").append(this.df.format((long)var1)));
   }
}
