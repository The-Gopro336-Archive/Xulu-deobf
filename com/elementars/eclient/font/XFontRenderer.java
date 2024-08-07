package com.elementars.eclient.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class XFontRenderer extends FontRenderer {
   // $FF: synthetic field
   private XFont italicFont;
   // $FF: synthetic field
   private boolean bidi;
   // $FF: synthetic field
   private String colorcodeIdentifiers = "0123456789abcdefklmnor";
   // $FF: synthetic field
   private final int[] colorCode = new int[32];
   // $FF: synthetic field
   private XFont boldItalicFont;
   // $FF: synthetic field
   public final Random fontRandom = new Random();
   // $FF: synthetic field
   private XFont font;
   // $FF: synthetic field
   private XFont boldFont;
   // $FF: synthetic field
   private final Color[] customColorCodes = new Color[256];

   private int sizeStringToWidth(String var1, int var2) {
      int var3 = var1.length();
      int var4 = 0;
      int var5 = 0;
      int var6 = -1;

      for(boolean var7 = false; var5 < var3; ++var5) {
         char var8 = var1.charAt(var5);
         switch(var8) {
         case '\n':
            --var5;
            break;
         case ' ':
            var6 = var5;
         default:
            var4 += this.getStringWidth(Character.toString(var8));
            if (var7) {
               ++var4;
            }
            break;
         case '§':
            if (var5 < var3 - 1) {
               ++var5;
               char var9 = var1.charAt(var5);
               if (var9 != 'l' && var9 != 'L') {
                  if (var9 == 'r' || var9 == 'R' || isFormatColor(var9)) {
                     var7 = false;
                  }
               } else {
                  var7 = true;
               }
            }
         }

         if (var8 == '\n') {
            ++var5;
            var6 = var5;
            break;
         }

         if (var4 > var2) {
            break;
         }
      }

      return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4, boolean var5) {
      if (var5) {
         this.drawStringWithShadow(var1, var2 - this.getStringWidth(var1) / 2, var3, var4);
      } else {
         this.drawString(var1, var2 - this.getStringWidth(var1) / 2, var3, var4);
      }

   }

   public XFontRenderer(Font var1, boolean var2, int var3) {
      super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
      this.setFont(var1, var2, var3);
      this.customColorCodes[113] = new Color(0, 90, 163);
      this.colorcodeIdentifiers = this.setupColorcodeIdentifier();
      this.setupMinecraftColorcodes();
      this.FONT_HEIGHT = this.getHeight();
   }

   private void setupMinecraftColorcodes() {
      for(int var1 = 0; var1 < 32; ++var1) {
         int var2 = (var1 >> 3 & 1) * 85;
         int var3 = (var1 >> 2 & 1) * 170 + var2;
         int var4 = (var1 >> 1 & 1) * 170 + var2;
         int var5 = (var1 >> 0 & 1) * 170 + var2;
         if (var1 == 6) {
            var3 += 85;
         }

         if (var1 >= 16) {
            var3 /= 4;
            var4 /= 4;
            var5 /= 4;
         }

         this.colorCode[var1] = (var3 & 255) << 16 | (var4 & 255) << 8 | var5 & 255;
      }

   }

   private void drawLine(double var1, double var3, double var5, double var7, float var9) {
      GL11.glDisable(3553);
      GL11.glLineWidth(var9);
      GL11.glBegin(1);
      GL11.glVertex2d(var1, var3);
      GL11.glVertex2d(var5, var7);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public String getFontName() {
      return this.font.getFont().getFontName();
   }

   public Color getColor(int var1, float var2) {
      return new Color((float)(var1 >> 16) / 255.0F, (float)(var1 >> 8 & 255) / 255.0F, (float)(var1 & 255) / 255.0F, var2);
   }

   private static boolean isFormatSpecial(char var0) {
      return var0 >= 'k' && var0 <= 'o' || var0 >= 'K' && var0 <= 'O' || var0 == 'r' || var0 == 'R';
   }

   public List listFormattedStringToWidth(String var1, int var2) {
      return Arrays.asList(this.wrapFormattedStringToWidth(var1, var2).split("\n"));
   }

   public int getColorCode(char var1) {
      return this.colorCode["0123456789abcdef".indexOf(var1)];
   }

   public int getStringWidth(String var1) {
      if (var1 == null) {
         return 0;
      } else if (!var1.contains("§")) {
         return this.font.getStringWidth(var1) / 2;
      } else {
         String[] var2 = var1.split("§");
         XFont var3 = this.font;
         int var4 = 0;
         boolean var5 = false;
         boolean var6 = false;

         for(int var7 = 0; var7 < var2.length; ++var7) {
            if (var2[var7].length() > 0) {
               if (var7 == 0) {
                  var4 += var3.getStringWidth(var2[var7]);
               } else {
                  String var8 = var2[var7].substring(1);
                  char var9 = var2[var7].charAt(0);
                  int var10 = this.colorcodeIdentifiers.indexOf(var9);
                  if (var10 != -1) {
                     if (var10 < 16) {
                        var5 = false;
                        var6 = false;
                     } else if (var10 != 16) {
                        if (var10 == 17) {
                           var5 = true;
                        } else if (var10 != 18 && var10 != 19) {
                           if (var10 == 20) {
                              var6 = true;
                           } else if (var10 == 21) {
                              var5 = false;
                              var6 = false;
                           }
                        }
                     }
                  }

                  if (var5 && var6) {
                     var3 = this.boldItalicFont;
                  } else if (var5) {
                     var3 = this.boldFont;
                  } else if (var6) {
                     var3 = this.italicFont;
                  } else {
                     var3 = this.font;
                  }

                  var4 += var3.getStringWidth(var8);
               }
            }
         }

         return var4 / 2;
      }
   }

   public int getHeight() {
      return this.font.getHeight() / 2;
   }

   public void setBidiFlag(boolean var1) {
      this.bidi = var1;
   }

   protected String wrapFormattedStringToWidth(String var1, int var2) {
      int var3 = this.sizeStringToWidth(var1, var2);
      if (var1.length() <= var3) {
         return var1;
      } else {
         String var4 = var1.substring(0, var3);
         char var5 = var1.charAt(var3);
         boolean var6 = var5 == ' ' || var5 == '\n';
         String var7 = String.valueOf((new StringBuilder()).append(getFormatFromString(var4)).append(var1.substring(var3 + (var6 ? 1 : 0))));
         return String.valueOf((new StringBuilder()).append(var4).append("\n").append(this.wrapFormattedStringToWidth(var7, var2)));
      }
   }

   public XFont getFont() {
      return this.font;
   }

   public int drawString(String var1, float var2, float var3, int var4) {
      return this.drawString(var1, var2, var3, var4, false);
   }

   public static String getFormatFromString(String var0) {
      String var1 = "";
      int var2 = -1;
      int var3 = var0.length();

      while((var2 = var0.indexOf(167, var2 + 1)) != -1) {
         if (var2 < var3 - 1) {
            char var4 = var0.charAt(var2 + 1);
            if (isFormatColor(var4)) {
               var1 = String.valueOf((new StringBuilder()).append("§").append(var4));
            } else if (isFormatSpecial(var4)) {
               var1 = String.valueOf((new StringBuilder()).append(var1).append("§").append(var4));
            }
         }
      }

      return var1;
   }

   public String trimStringToWidth(String var1, int var2, boolean var3) {
      StringBuilder var4 = new StringBuilder();
      int var5 = 0;
      int var6 = var3 ? var1.length() - 1 : 0;
      int var7 = var3 ? -1 : 1;
      boolean var8 = false;
      boolean var9 = false;

      for(int var10 = var6; var10 >= 0 && var10 < var1.length() && var5 < var2; var10 += var7) {
         char var11 = var1.charAt(var10);
         int var12 = this.getStringWidth(Character.toString(var11));
         if (var8) {
            var8 = false;
            if (var11 != 'l' && var11 != 'L') {
               if (var11 == 'r' || var11 == 'R') {
                  var9 = false;
               }
            } else {
               var9 = true;
            }
         } else if (var12 < 0) {
            var8 = true;
         } else {
            var5 += var12;
            if (var9) {
               ++var5;
            }
         }

         if (var5 > var2) {
            break;
         }

         if (var3) {
            var4.insert(0, var11);
         } else {
            var4.append(var11);
         }
      }

      return String.valueOf(var4);
   }

   public void drawCenteredStringXY(String var1, int var2, int var3, int var4, boolean var5) {
      this.drawCenteredString(var1, var2, var3 - this.getHeight() / 2, var4, var5);
   }

   public void setAntiAliasing(boolean var1) {
      this.font.setAntiAlias(var1);
      this.boldFont.setAntiAlias(var1);
      this.italicFont.setAntiAlias(var1);
      this.boldItalicFont.setAntiAlias(var1);
   }

   public void setFont(Font var1, boolean var2, int var3) {
      synchronized(this) {
         this.font = new XFont(var1, var2, var3);
         this.boldFont = new XFont(var1.deriveFont(1), var2, var3);
         this.italicFont = new XFont(var1.deriveFont(2), var2, var3);
         this.boldItalicFont = new XFont(var1.deriveFont(3), var2, var3);
         this.FONT_HEIGHT = this.getHeight();
      }
   }

   private static boolean isFormatColor(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'f' || var0 >= 'A' && var0 <= 'F';
   }

   public int getSize() {
      return this.font.getFont().getSize();
   }

   public void drawStringWithShadow(String var1, int var2, int var3, int var4) {
      this.drawString(var1, (float)var2 + 0.75F, (float)var3 + 0.75F, var4, true);
      this.drawString(var1, (float)var2, (float)var3, var4, false);
   }

   public int drawString(String var1, float var2, float var3, int var4, boolean var5) {
      int var6 = 0;
      String[] var7 = var1.split("\n");

      for(int var8 = 0; var8 < var7.length; ++var8) {
         var6 = this.drawLine(var7[var8], var2, var3 + (float)(var8 * this.getHeight()), var4, var5);
      }

      return var6;
   }

   public boolean isAntiAliasing() {
      return this.font.isAntiAlias() && this.boldFont.isAntiAlias() && this.italicFont.isAntiAlias() && this.boldItalicFont.isAntiAlias();
   }

   public int getCharWidth(char var1) {
      return this.getStringWidth(Character.toString(var1));
   }

   public List formatString(String var1, double var2) {
      ArrayList var4 = new ArrayList();
      String var5 = "";
      char var6 = '\uffff';

      for(int var7 = 0; var7 < var1.toCharArray().length; ++var7) {
         char var8 = var1.toCharArray()[var7];
         if (var8 == 167 && var7 < var1.toCharArray().length - 1) {
            var6 = var1.toCharArray()[var7 + 1];
         }

         if ((double)this.getStringWidth(String.valueOf((new StringBuilder()).append(var5).append(var8))) < var2) {
            var5 = String.valueOf((new StringBuilder()).append(var5).append(var8));
         } else {
            var4.add(var5);
            var5 = var6 == -1 ? String.valueOf(var8) : String.valueOf((new StringBuilder()).append("§").append(var6).append(String.valueOf(var8)));
         }
      }

      if (!var5.equals("")) {
         var4.add(var5);
      }

      return var4;
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4) {
      this.drawStringWithShadow(var1, var2 - this.getStringWidth(var1) / 2, var3, var4);
   }

   public boolean getBidiFlag() {
      return this.bidi;
   }

   private int drawLine(String var1, float var2, float var3, int var4, boolean var5) {
      if (var1 == null) {
         return 0;
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated((double)var2 - 1.5D, (double)var3 + 0.5D, 0.0D);
         boolean var6 = GL11.glGetBoolean(3042);
         GlStateManager.enableAlpha();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3553);
         if ((var4 & -67108864) == 0) {
            var4 |= -16777216;
         }

         float var7 = (float)(var4 >> 16 & 255) / 255.0F;
         float var8 = (float)(var4 >> 8 & 255) / 255.0F;
         float var9 = (float)(var4 & 255) / 255.0F;
         float var10 = (float)(var4 >> 24 & 255) / 255.0F;
         Color var11 = new Color(var7, var8, var9, var10);
         if (var1.contains("§")) {
            String[] var12 = var1.split("§");
            Color var13 = var11;
            XFont var14 = this.font;
            int var15 = 0;
            boolean var16 = false;
            boolean var17 = false;
            boolean var18 = false;
            boolean var19 = false;
            boolean var20 = false;

            for(int var21 = 0; var21 < var12.length; ++var21) {
               if (var12[var21].length() > 0) {
                  if (var21 == 0) {
                     var14.drawString(var12[var21], (double)var15, 0.0D, var13, var5);
                     var15 += var14.getStringWidth(var12[var21]);
                  } else {
                     String var22 = var12[var21].substring(1);
                     char var23 = var12[var21].charAt(0);
                     int var24 = this.colorcodeIdentifiers.indexOf(var23);
                     if (var24 != -1) {
                        if (var24 < 16) {
                           int var25 = this.colorCode[var24];
                           var13 = this.getColor(var25, var10);
                           var17 = false;
                           var18 = false;
                           var16 = false;
                           var20 = false;
                           var19 = false;
                        } else if (var24 == 16) {
                           var16 = true;
                        } else if (var24 == 17) {
                           var17 = true;
                        } else if (var24 == 18) {
                           var19 = true;
                        } else if (var24 == 19) {
                           var20 = true;
                        } else if (var24 == 20) {
                           var18 = true;
                        } else if (var24 == 21) {
                           var17 = false;
                           var18 = false;
                           var16 = false;
                           var20 = false;
                           var19 = false;
                           var13 = var11;
                        } else if (var24 > 21) {
                           Color var28 = this.customColorCodes[var23];
                           var13 = new Color((float)var28.getRed() / 255.0F, (float)var28.getGreen() / 255.0F, (float)var28.getBlue() / 255.0F, var10);
                        }
                     }

                     if (var17 && var18) {
                        this.boldItalicFont.drawString(var16 ? this.toRandom(var14, var22) : var22, (double)var15, 0.0D, var13, var5);
                        var14 = this.boldItalicFont;
                     } else if (var17) {
                        this.boldFont.drawString(var16 ? this.toRandom(var14, var22) : var22, (double)var15, 0.0D, var13, var5);
                        var14 = this.boldFont;
                     } else if (var18) {
                        this.italicFont.drawString(var16 ? this.toRandom(var14, var22) : var22, (double)var15, 0.0D, var13, var5);
                        var14 = this.italicFont;
                     } else {
                        this.font.drawString(var16 ? this.toRandom(var14, var22) : var22, (double)var15, 0.0D, var13, var5);
                        var14 = this.font;
                     }

                     float var29 = (float)this.font.getHeight() / 16.0F;
                     int var26 = var14.getStringHeight(var22);
                     if (var19) {
                        this.drawLine((double)var15 / 2.0D + 1.0D, (double)(var26 / 3), (double)(var15 + var14.getStringWidth(var22)) / 2.0D + 1.0D, (double)(var26 / 3), var29);
                     }

                     if (var20) {
                        this.drawLine((double)var15 / 2.0D + 1.0D, (double)(var26 / 2), (double)(var15 + var14.getStringWidth(var22)) / 2.0D + 1.0D, (double)(var26 / 2), var29);
                     }

                     var15 += var14.getStringWidth(var22);
                  }
               }
            }
         } else {
            this.font.drawString(var1, 0.0D, 0.0D, var11, var5);
         }

         if (!var6) {
            GL11.glDisable(3042);
         }

         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         return (int)(var2 + (float)this.getStringWidth(var1));
      }
   }

   public String trimStringToWidth(String var1, int var2) {
      return this.trimStringToWidth(var1, var2, false);
   }

   public int getStringHeight(String var1) {
      return var1 == null ? 0 : this.font.getStringHeight(var1) / 2;
   }

   public List wrapWords(String var1, double var2) {
      ArrayList var4 = new ArrayList();
      if ((double)this.getStringWidth(var1) > var2) {
         String[] var5 = var1.split(" ");
         String var6 = "";
         char var7 = '\uffff';
         String[] var8 = var5;
         int var9 = var5.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String var11 = var8[var10];

            for(int var12 = 0; var12 < var11.toCharArray().length; ++var12) {
               char var13 = var11.toCharArray()[var12];
               if (var13 == 167 && var12 < var11.toCharArray().length - 1) {
                  var7 = var11.toCharArray()[var12 + 1];
               }
            }

            if ((double)this.getStringWidth(String.valueOf((new StringBuilder()).append(var6).append(var11).append(" "))) < var2) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(var11).append(" "));
            } else {
               var4.add(var6);
               var6 = var7 == -1 ? String.valueOf((new StringBuilder()).append(var11).append(" ")) : String.valueOf((new StringBuilder()).append("§").append(var7).append(var11).append(" "));
            }
         }

         if (!var6.equals("")) {
            if ((double)this.getStringWidth(var6) < var2) {
               var4.add(var7 == -1 ? String.valueOf((new StringBuilder()).append(var6).append(" ")) : String.valueOf((new StringBuilder()).append("§").append(var7).append(var6).append(" ")));
               var6 = "";
            } else {
               Iterator var15 = this.formatString(var6, var2).iterator();

               while(var15.hasNext()) {
                  String var16 = (String)var15.next();
                  var4.add(var16);
               }
            }
         }
      } else {
         var4.add(var1);
      }

      return var4;
   }

   public void onResourceManagerReload(IResourceManager var1) {
   }

   private String setupColorcodeIdentifier() {
      String var1 = "0123456789abcdefklmnor";

      for(int var2 = 0; var2 < this.customColorCodes.length; ++var2) {
         if (this.customColorCodes[var2] != null) {
            var1 = String.valueOf((new StringBuilder()).append(var1).append((char)var2));
         }
      }

      return var1;
   }

   private String toRandom(XFont var1, String var2) {
      String var3 = "";
      String var4 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";
      char[] var5 = var2.toCharArray();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         char var8 = var5[var7];
         if (ChatAllowedCharacters.isAllowedCharacter(var8)) {
            int var9 = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".length());
            var3 = String.valueOf((new StringBuilder()).append(var3).append("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".toCharArray()[var9]));
         }
      }

      return var3;
   }
}
