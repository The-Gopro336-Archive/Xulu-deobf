package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiFog extends Module {
   // $FF: synthetic field
   private final Value b = this.register(new Value("Blue", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value b1 = this.register(new Value("Nether Blue", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value g1 = this.register(new Value("Nether Green", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value color = this.register(new Value("Color fog", this, true));
   // $FF: synthetic field
   private final Value r2 = this.register(new Value("End Red", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private ArrayList options;
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value r = this.register(new Value("Red", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value g = this.register(new Value("Green", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value r1 = this.register(new Value("Nether Red", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value g2 = this.register(new Value("End Green", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value b2 = this.register(new Value("End Blue", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value clear = this.register(new Value("Remove fog", this, true));

   public AntiFog() {
      super("AntiFog", "Prevents fog", 0, Category.RENDER, true);
   }

   @SubscribeEvent
   public void onFogColor(FogColors var1) {
      if ((Boolean)this.color.getValue()) {
         if ((Boolean)this.rainbow.getValue()) {
            var1.setRed((float)(new Color(Xulu.rgb)).getRed() / 255.0F);
            var1.setGreen((float)(new Color(Xulu.rgb)).getGreen() / 255.0F);
            var1.setBlue((float)(new Color(Xulu.rgb)).getBlue() / 255.0F);
         } else if (mc.player.dimension == 0) {
            var1.setRed((Float)this.r.getValue());
            var1.setGreen((Float)this.g.getValue());
            var1.setBlue((Float)this.b.getValue());
         } else if (mc.player.dimension == -1) {
            var1.setRed((Float)this.r1.getValue());
            var1.setGreen((Float)this.g1.getValue());
            var1.setBlue((Float)this.b1.getValue());
         } else if (mc.player.dimension == 1) {
            var1.setRed((Float)this.r2.getValue());
            var1.setGreen((Float)this.g2.getValue());
            var1.setBlue((Float)this.b2.getValue());
         }
      }

   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onFogDensity(FogDensity var1) {
      if ((Boolean)this.clear.getValue()) {
         var1.setDensity(0.0F);
         var1.setCanceled(true);
      }

   }
}
