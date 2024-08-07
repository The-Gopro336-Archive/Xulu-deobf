package com.elementars.eclient.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class FontInit {
   public void initFonts() {
      try {
         GraphicsEnvironment var1 = GraphicsEnvironment.getLocalGraphicsEnvironment();
         var1.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/Comfortaa-Regular.ttf")));
         var1.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/GOTHIC.TTF")));
         var1.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/MODERN SPACE.ttf")));
      } catch (FontFormatException | IOException var3) {
         var3.printStackTrace();
      }

   }
}
