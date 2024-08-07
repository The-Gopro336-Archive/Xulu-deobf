package dev.xulu.newgui;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.elements.menu.ElementSlider;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.ValueManager;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class NewGUI extends GuiScreen {
   // $FF: synthetic field
   public static ArrayList rpanels;
   // $FF: synthetic field
   public static ArrayList panels;
   // $FF: synthetic field
   private ModuleButton mb = null;
   // $FF: synthetic field
   public ValueManager setmgr;

   public static Panel getPanelByName(String var0) {
      Iterator var1 = getPanels().iterator();

      Panel var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Panel)var1.next();
      } while(!var2.title.equalsIgnoreCase(var0));

      return var2;
   }

   public void closeAllSettings() {
      Iterator var1 = rpanels.iterator();

      while(true) {
         Panel var2;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var1.hasNext()) {
                           return;
                        }

                        var2 = (Panel)var1.next();
                     } while(var2 == null);
                  } while(!var2.visible);
               } while(!var2.extended);
            } while(var2.Elements == null);
         } while(var2.Elements.size() <= 0);

         ModuleButton var4;
         for(Iterator var3 = var2.Elements.iterator(); var3.hasNext(); var4.extended = false) {
            var4 = (ModuleButton)var3.next();
         }
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      Iterator var4 = rpanels.iterator();

      label144:
      while(true) {
         Panel var5;
         do {
            do {
               do {
                  if (!var4.hasNext()) {
                     ScaledResolution var15 = new ScaledResolution(this.mc);
                     this.mb = null;
                     Iterator var16 = rpanels.iterator();

                     Panel var17;
                     Iterator var18;
                     ModuleButton var19;
                     label105:
                     while(var16.hasNext()) {
                        var17 = (Panel)var16.next();
                        if (var17 != null && var17.visible && var17.extended && var17.Elements != null && var17.Elements.size() > 0) {
                           var18 = var17.Elements.iterator();

                           while(var18.hasNext()) {
                              var19 = (ModuleButton)var18.next();
                              if (var19.listening) {
                                 this.mb = var19;
                                 break label105;
                              }
                           }
                        }
                     }

                     if (this.mb != null) {
                        drawRect(0, 0, this.width, this.height, -2012213232);
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)(var15.getScaledWidth() / 2), (float)(var15.getScaledHeight() / 2), 0.0F);
                        GL11.glScalef(4.0F, 4.0F, 0.0F);
                        FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0.0D, -10.0D, -1);
                        GL11.glScalef(0.5F, 0.5F, 0.0F);
                        FontUtil.drawTotalCenteredStringWithShadow(String.valueOf((new StringBuilder()).append("Press 'ESCAPE' to unbind ").append(this.mb.mod.getName()).append(this.mb.mod.getKey() > -1 ? String.valueOf((new StringBuilder()).append(" (").append(Keyboard.getKeyName(this.mb.mod.getKey())).append(")")) : "")), 0.0D, 0.0D, -1);
                        GL11.glScalef(0.25F, 0.25F, 0.0F);
                        FontUtil.drawTotalCenteredStringWithShadow("by HeroCode", 0.0D, 20.0D, -1);
                        GL11.glPopMatrix();
                     }

                     var16 = panels.iterator();

                     while(true) {
                        do {
                           if (!var16.hasNext()) {
                              super.drawScreen(var1, var2, var3);
                              return;
                           }

                           var17 = (Panel)var16.next();
                        } while(!var17.extended);

                        var18 = var17.Elements.iterator();

                        while(var18.hasNext()) {
                           var19 = (ModuleButton)var18.next();
                           if (!(var19.mod instanceof Element) && var19.isHovered(var1, var2)) {
                              if ((Boolean)NewGui.customfont.getValue()) {
                                 Gui.drawRect(var1 + 6, var2 + 6, var1 + Xulu.cFontRenderer.getStringWidth(var19.mod.getDesc()) + 11, (int)((float)var2 + Xulu.cFontRenderer.getHeight() + 10.0F), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, (Integer)NewGui.bgAlpha.getValue()));
                                 if ((Boolean)NewGui.outline.getValue()) {
                                    XuluTessellator.drawRectOutline((double)(var1 + 6), (double)(var2 + 6), (double)(var1 + Xulu.cFontRenderer.getStringWidth(var19.mod.getDesc()) + 11), (double)((int)((float)var2 + Xulu.cFontRenderer.getHeight() + 10.0F)), 1.0D, ColorUtils.changeAlpha(ColorUtil.getClickGUIColor().getRGB(), 225));
                                 }

                                 Xulu.cFontRenderer.drawStringWithShadow(var19.mod.getDesc(), (double)(var1 + 8), (double)(var2 + 7), ColorUtils.Colors.WHITE);
                              } else {
                                 Gui.drawRect(var1 + 6, var2 + 6, var1 + Wrapper.getMinecraft().fontRenderer.getStringWidth(var19.mod.getDesc()) + 11, var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT + 10, ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, (Integer)NewGui.bgAlpha.getValue()));
                                 if ((Boolean)NewGui.outline.getValue()) {
                                    XuluTessellator.drawRectOutline((double)(var1 + 6), (double)(var2 + 6), (double)(var1 + Wrapper.getMinecraft().fontRenderer.getStringWidth(var19.mod.getDesc()) + 11), (double)(var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT + 10), 1.0D, ColorUtils.changeAlpha(ColorUtil.getClickGUIColor().getRGB(), 225));
                                 }

                                 Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var19.mod.getDesc(), (float)(var1 + 9), (float)(var2 + 9), ColorUtils.Colors.WHITE);
                              }
                           }
                        }
                     }
                  }

                  var5 = (Panel)var4.next();
                  var5.drawScreen(var1, var2, var3);
               } while(!var5.extended);
            } while(!var5.visible);
         } while(var5.Elements == null);

         Iterator var6 = var5.Elements.iterator();

         while(true) {
            ModuleButton var7;
            do {
               do {
                  do {
                     if (!var6.hasNext()) {
                        continue label144;
                     }

                     var7 = (ModuleButton)var6.next();
                  } while(!var7.extended);
               } while(var7.menuelements == null);
            } while(var7.menuelements.isEmpty());

            double var8 = var7.height + 1.0D;
            Color var10 = ColorUtil.getClickGUIColor().darker();
            if ((Boolean)NewGui.rainbowgui.getValue()) {
               var10 = (new Color(Xulu.rgb)).darker();
            }

            int var11 = (new Color(var10.getRed(), var10.getGreen(), var10.getBlue(), 170)).getRGB();
            Iterator var12 = var7.menuelements.iterator();

            while(var12.hasNext()) {
               dev.xulu.newgui.elements.Element var13 = (dev.xulu.newgui.elements.Element)var12.next();
               if (var13.set.isVisible()) {
                  var13.offset = var8;
                  var13.update();
                  var13.drawScreen(var1, var2, var3);
                  var8 += var13.height;
               }
            }
         }
      }
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (this.mb == null) {
         Iterator var4 = rpanels.iterator();

         label62:
         while(true) {
            Panel var5;
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        var4 = rpanels.iterator();

                        while(var4.hasNext()) {
                           var5 = (Panel)var4.next();
                           var5.mouseReleased(var1, var2, var3);
                        }

                        super.mouseReleased(var1, var2, var3);
                        return;
                     }

                     var5 = (Panel)var4.next();
                  } while(!var5.extended);
               } while(!var5.visible);
            } while(var5.Elements == null);

            Iterator var6 = var5.Elements.iterator();

            while(true) {
               ModuleButton var7;
               do {
                  if (!var6.hasNext()) {
                     continue label62;
                  }

                  var7 = (ModuleButton)var6.next();
               } while(!var7.extended);

               Iterator var8 = var7.menuelements.iterator();

               while(var8.hasNext()) {
                  dev.xulu.newgui.elements.Element var9 = (dev.xulu.newgui.elements.Element)var8.next();
                  if (var9.set.isVisible()) {
                     var9.mouseReleased(var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   protected void keyTyped(char var1, int var2) {
      Iterator var3 = rpanels.iterator();

      while(true) {
         Panel var4;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           try {
                              super.keyTyped(var1, var2);
                           } catch (IOException var8) {
                              var8.printStackTrace();
                           }

                           return;
                        }

                        var4 = (Panel)var3.next();
                     } while(var4 == null);
                  } while(!var4.visible);
               } while(!var4.extended);
            } while(var4.Elements == null);
         } while(var4.Elements.size() <= 0);

         Iterator var5 = var4.Elements.iterator();

         while(var5.hasNext()) {
            ModuleButton var6 = (ModuleButton)var5.next();

            try {
               if (var6.keyTyped(var1, var2)) {
                  return;
               }
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }
   }

   public static ArrayList getPanels() {
      return panels;
   }

   public void onGuiClosed() {
      if (this.mc.entityRenderer.getShaderGroup() != null) {
         this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
      }

      Iterator var1 = rpanels.iterator();

      label53:
      while(true) {
         Panel var2;
         do {
            do {
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  var2 = (Panel)var1.next();
               } while(!var2.extended);
            } while(!var2.visible);
         } while(var2.Elements == null);

         Iterator var3 = var2.Elements.iterator();

         while(true) {
            ModuleButton var4;
            do {
               if (!var3.hasNext()) {
                  continue label53;
               }

               var4 = (ModuleButton)var3.next();
            } while(!var4.extended);

            Iterator var5 = var4.menuelements.iterator();

            while(var5.hasNext()) {
               dev.xulu.newgui.elements.Element var6 = (dev.xulu.newgui.elements.Element)var5.next();
               if (var6 instanceof ElementSlider) {
                  ((ElementSlider)var6).dragging = false;
               }
            }
         }
      }
   }

   public NewGUI() {
      this.setmgr = Xulu.VALUE_MANAGER;
      panels = new ArrayList();
      double var1 = 100.0D;
      double var3 = 13.0D;
      double var5 = 10.0D;
      double var7 = 10.0D;
      double var9 = var3 + 10.0D;
      Category[] var11 = Category.values();
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         final Category var14 = var11[var13];
         if (var14 != Category.HIDDEN && var14 != Category.HUD) {
            boolean var15 = true;
            Iterator var16 = Xulu.MODULE_MANAGER.getModules().iterator();

            while(var16.hasNext()) {
               Module var17 = (Module)var16.next();
               if (var17.getCategory().equals(var14)) {
                  var15 = false;
               }
            }

            if (!var15) {
               String var20 = String.valueOf((new StringBuilder()).append(Character.toUpperCase(var14.name().toLowerCase().charAt(0))).append(var14.name().toLowerCase().substring(1)));
               panels.add(new Panel(var20, var5, var7, var1, var3, false, this) {
                  public void setup() {
                     Iterator var1 = Xulu.MODULE_MANAGER.getModules().iterator();

                     while(var1.hasNext()) {
                        Module var2 = (Module)var1.next();
                        if (var2.getCategory().equals(var14)) {
                           this.Elements.add(new ModuleButton(var2, this));
                        }
                     }

                  }
               });
               var7 += var9;
            }
         }
      }

      rpanels = new ArrayList();
      Iterator var18 = panels.iterator();

      while(var18.hasNext()) {
         Panel var19 = (Panel)var18.next();
         if (!var19.Elements.isEmpty()) {
            rpanels.add(var19);
         }
      }

      Collections.reverse(rpanels);
   }

   public void mouseClicked(int var1, int var2, int var3) {
      if (this.mb == null) {
         Iterator var4 = rpanels.iterator();

         label73:
         while(true) {
            Panel var5;
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        var4 = rpanels.iterator();

                        do {
                           if (!var4.hasNext()) {
                              try {
                                 super.mouseClicked(var1, var2, var3);
                              } catch (IOException var10) {
                                 var10.printStackTrace();
                              }

                              return;
                           }

                           var5 = (Panel)var4.next();
                        } while(!var5.mouseClicked(var1, var2, var3));

                        return;
                     }

                     var5 = (Panel)var4.next();
                  } while(!var5.extended);
               } while(!var5.visible);
            } while(var5.Elements == null);

            Iterator var6 = var5.Elements.iterator();

            while(true) {
               ModuleButton var7;
               do {
                  if (!var6.hasNext()) {
                     continue label73;
                  }

                  var7 = (ModuleButton)var6.next();
               } while(!var7.extended);

               Iterator var8 = var7.menuelements.iterator();

               while(var8.hasNext()) {
                  dev.xulu.newgui.elements.Element var9 = (dev.xulu.newgui.elements.Element)var8.next();
                  if (var9.set.isVisible() && var9.mouseClicked(var1, var2, var3)) {
                     return;
                  }
               }
            }
         }
      }
   }

   public void handleMouseInput() throws IOException {
      byte var1 = 5;
      Iterator var2;
      Panel var3;
      if (Mouse.getEventDWheel() > 0) {
         for(var2 = rpanels.iterator(); var2.hasNext(); var3.y += (double)var1) {
            var3 = (Panel)var2.next();
         }
      }

      if (Mouse.getEventDWheel() < 0) {
         for(var2 = rpanels.iterator(); var2.hasNext(); var3.y -= (double)var1) {
            var3 = (Panel)var2.next();
         }
      }

      super.handleMouseInput();
   }

   public void initGui() {
      if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer && (Boolean)NewGui.blur.getValue()) {
         if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
         }

         this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
      }

   }
}
