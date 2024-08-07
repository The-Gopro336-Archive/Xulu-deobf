package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.render.ImageESP;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class Logo extends Element {
   // $FF: synthetic field
   private ResourceLocation logo;

   public Logo() {
      super("Logo");
      this.onLoad();
   }

   private BufferedImage getImage(Object var1, Logo.ThrowingFunction var2) {
      try {
         return (BufferedImage)var2.apply(var1);
      } catch (IOException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public void onRender() {
      mc.renderEngine.bindTexture(this.logo);
      GlStateManager.color(255.0F, 255.0F, 255.0F);
      Gui.drawScaledCustomSizeModalRect((int)this.x + 4, (int)this.y + 4, 7.0F, 7.0F, (int)this.width - 7, (int)this.height - 7, (int)this.width, (int)this.height, (float)this.width, (float)this.height);
   }

   public void onEnable() {
      this.width = 32.0D;
      this.height = 32.0D;
   }

   private void onLoad() {
      BufferedImage var1 = null;

      try {
         if (this.getFile("/images/xulutransparent.png") != null) {
            var1 = this.getImage(this.getFile("/images/xulutransparent.png"), ImageIO::read);
         }

         if (var1 == null) {
            this.LOGGER.warn("Failed to load image");
         } else {
            DynamicTexture var2 = new DynamicTexture(var1);
            var2.loadTexture(mc.getResourceManager());
            this.logo = mc.getTextureManager().getDynamicTextureLocation("XULU_LOGO_TRANSPARENT", var2);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private InputStream getFile(String var1) {
      return ImageESP.class.getResourceAsStream(var1);
   }

   @FunctionalInterface
   private interface ThrowingFunction {
      Object apply(Object var1) throws IOException;
   }
}
