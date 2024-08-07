package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Pair;
import com.elementars.eclient.util.Triplet;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import com.google.common.collect.Maps;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class FeatureList extends Element {
   // $FF: synthetic field
   private Value rsaturation;
   // $FF: synthetic field
   private FeatureList.Rainbow rainbow = new FeatureList.Rainbow();
   // $FF: synthetic field
   private Map animationMap = Maps.newHashMap();
   // $FF: synthetic field
   private Value categoryProfile;
   // $FF: synthetic field
   private List removal = new ArrayList();
   // $FF: synthetic field
   private Value rainbowspeed;
   // $FF: synthetic field
   private Value mode;
   // $FF: synthetic field
   public static Value type;
   // $FF: synthetic field
   private Value box;
   // $FF: synthetic field
   private Value rlightness;
   // $FF: synthetic field
   private Value corner = this.register(new Value("List In Corner", this, false));
   // $FF: synthetic field
   private Value rlist;
   // $FF: synthetic field
   private Value suffix;
   // $FF: synthetic field
   private Value prefix;
   // $FF: synthetic field
   private Value order;
   // $FF: synthetic field
   private Value rspeed;
   // $FF: synthetic field
   private Value boxMode;
   // $FF: synthetic field
   public static Value animation;
   // $FF: synthetic field
   String comp;
   // $FF: synthetic field
   private Value alphab;

   public void onEnable() {
      this.width = 80.0D;
      this.height = 80.0D;
   }

   private int betterCompare(Module var1, String var2) {
      boolean var3 = false;
      int var5 = var1.getName().compareTo(var2);
      this.comp = var2;
      return var5;
   }

   public FeatureList() {
      super("FeatureList");
      animation = this.register(new Value("Animation", this, false));
      type = this.register(new Value("Type", this, "Both", new String[]{"Both", "Enable", "Disable"}));
      this.alphab = this.register(new Value("Alphabetical", this, false));
      this.box = this.register(new Value("Boxes", this, false));
      this.boxMode = this.register(new Value("Box Mode", this, "Tag", new String[]{"Black", "Tag", "Outline"}));
      this.prefix = this.register(new Value("Prefix", this, "None", new String[]{"None", ">", ")", "]", "}", ">(space)", "->", "-", "=", "<", "(", "[", "{"}));
      this.suffix = this.register(new Value("Suffix", this, "None", new String[]{"None", ">", ")", "]", "}", "(space)<", "<-", "-", "=", "<", "(", "[", "{"}));
      this.mode = this.register(new Value("Aligned", this, "Left", new ArrayList(Arrays.asList("Left", "Right"))));
      this.order = this.register(new Value("Ordering", this, "Up", new ArrayList(Arrays.asList("Up", "Down"))));
      this.rlist = this.register(new Value("Color Mode", this, "ClickGui", new String[]{"ClickGui", "Rainbow", "Category"}));
      this.categoryProfile = this.register(new Value("Category Mode", this, "Xulu", new String[]{"Xulu", "Impact", "DotGod"}));
      this.rainbowspeed = this.register(new Value("Rainbow Speed", this, 5, 1, 100));
      this.rspeed = this.register(new Value("Rainbow Size", this, 2, 0, 20));
      this.rsaturation = this.register(new Value("Rainbow Sat.", this, 255, 0, 255));
      this.rlightness = this.register(new Value("Rainbow Light.", this, 255, 0, 255));
   }

   private int getCategoryColor(Module var1) {
      String var2 = (String)this.categoryProfile.getValue();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2100942490:
         if (var2.equals("Impact")) {
            var3 = 1;
         }
         break;
      case 2737510:
         if (var2.equals("Xulu")) {
            var3 = 0;
         }
         break;
      case 2052820627:
         if (var2.equals("DotGod")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         switch(var1.getCategory()) {
         case CORE:
            return (new Color(0, 218, 242)).getRGB();
         case COMBAT:
            return (new Color(222, 57, 11)).getRGB();
         case MOVEMENT:
            return (new Color(189, 28, 173)).getRGB();
         case PLAYER:
            return (new Color(83, 219, 41)).getRGB();
         case RENDER:
            return (new Color(255, 242, 62)).getRGB();
         case MISC:
            return (new Color(255, 143, 15)).getRGB();
         case DUMMY:
            return (new Color(222, 57, 209)).getRGB();
         case HUD:
            return (new Color(255, 0, 123)).getRGB();
         case HIDDEN:
            return -1;
         }
      case 1:
         switch(var1.getCategory()) {
         case CORE:
            return (new Color(0, 218, 242)).getRGB();
         case COMBAT:
            return (new Color(229, 30, 16)).getRGB();
         case MOVEMENT:
            return (new Color(8, 116, 227)).getRGB();
         case PLAYER:
            return (new Color(43, 203, 55)).getRGB();
         case RENDER:
            return (new Color(227, 162, 50)).getRGB();
         case MISC:
            return (new Color(97, 30, 212)).getRGB();
         case DUMMY:
            return (new Color(241, 243, 90)).getRGB();
         case HUD:
            return (new Color(255, 0, 123)).getRGB();
         case HIDDEN:
            return -1;
         }
      case 2:
         switch(var1.getCategory()) {
         case CORE:
            return (new Color(0, 218, 242)).getRGB();
         case COMBAT:
            return (new Color(39, 181, 171)).getRGB();
         case MOVEMENT:
            return (new Color(26, 84, 219)).getRGB();
         case PLAYER:
            return (new Color(219, 184, 190)).getRGB();
         case RENDER:
            return (new Color(169, 204, 83)).getRGB();
         case MISC:
            return (new Color(215, 214, 216)).getRGB();
         case DUMMY:
            return (new Color(222, 57, 209)).getRGB();
         case HUD:
            return (new Color(255, 0, 123)).getRGB();
         case HIDDEN:
            return -1;
         }
      default:
         return -1;
      }
   }

   public void onRender() {
      ScaledResolution var1 = new ScaledResolution(mc);
      double var2 = 3.0D;
      double var4 = (double)(var1.getScaledWidth() - 3) - this.getFrame().width;
      if (!(Boolean)this.corner.getValue()) {
         var2 = this.y + 1.0D;
         var4 = this.x + 1.0D;
      }

      this.rainbow.updateRainbow();
      List var6;
      String[] var7;
      int var8;
      String var9;
      int var10;
      int var11;
      int var12;
      String var13;
      String var14;
      double var17;
      String var19;
      byte var20;
      Module var21;
      String var22;
      double var23;
      String[] var33;
      String var35;
      double var42;
      double var44;
      if (Xulu.CustomFont) {
         var6 = (List)Xulu.MODULE_MANAGER.getModules().stream().filter(Module::isToggledAnim).filter(Module::isDrawn).sorted(Comparator.comparing((var0) -> {
            return Xulu.cFontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var0.getName()).append(var0.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(var0.getHudInfo()).append("]"))))) * -1;
         })).collect(Collectors.toList());
         if ((Boolean)this.alphab.getValue()) {
            var7 = (String[])var6.stream().map(Module::getName).toArray((var0) -> {
               return new String[var0];
            });
            var8 = var6.size();
            var10 = 0;

            label520:
            while(true) {
               if (var10 >= var8) {
                  var6.clear();
                  var33 = var7;
                  var11 = var7.length;
                  var12 = 0;

                  while(true) {
                     if (var12 >= var11) {
                        break label520;
                     }

                     var13 = var33[var12];

                     try {
                        var6.add(Xulu.MODULE_MANAGER.getModuleByName(var13));
                     } catch (Exception var26) {
                     }

                     ++var12;
                  }
               }

               for(var11 = var10 + 1; var11 < var8; ++var11) {
                  if (var7[var10].compareTo(var7[var11]) > 0) {
                     var9 = var7[var10];
                     var7[var10] = var7[var11];
                     var7[var11] = var9;
                  }
               }

               ++var10;
            }
         }

         boolean var27 = true;
         if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
            var2 += 69.0D;
         }

         float var29 = this.rainbow.hue;

         byte var15;
         Iterator var31;
         Module var34;
         for(var31 = var6.iterator(); var31.hasNext(); var29 = (float)((double)var29 + var44 / 100.0D)) {
            var34 = (Module)var31.next();
            var11 = Color.HSBtoRGB(var29, (float)(Integer)this.rsaturation.getValue() / 255.0F, (float)(Integer)this.rlightness.getValue() / 255.0F);
            var35 = (String)this.rlist.getValue();
            byte var36 = -1;
            switch(var35.hashCode()) {
            case 115155230:
               if (var35.equals("Category")) {
                  var36 = 1;
               }
               break;
            case 971074227:
               if (var35.equals("ClickGui")) {
                  var36 = 0;
               }
            }

            switch(var36) {
            case 0:
               var11 = ColorUtil.getClickGUIColor().getRGB();
               break;
            case 1:
               var11 = this.getCategoryColor(var34);
            }

            var14 = (String)this.prefix.getValue();
            var15 = -1;
            switch(var14.hashCode()) {
            case 2433880:
               if (var14.equals("None")) {
                  var15 = 0;
               }
               break;
            case 924274701:
               if (var14.equals(">(space)")) {
                  var15 = 1;
               }
            }

            switch(var15) {
            case 0:
               var35 = "";
               break;
            case 1:
               var35 = "> ";
               break;
            default:
               var35 = (String)this.prefix.getValue();
            }

            var14 = (String)this.suffix.getValue();
            var15 = -1;
            switch(var14.hashCode()) {
            case -1019228271:
               if (var14.equals("(space)<")) {
                  var15 = 1;
               }
               break;
            case 2433880:
               if (var14.equals("None")) {
                  var15 = 0;
               }
            }

            switch(var15) {
            case 0:
               var13 = "";
               break;
            case 1:
               var13 = " <";
               break;
            default:
               var13 = (String)this.suffix.getValue();
            }

            var14 = String.valueOf((new StringBuilder()).append(var35).append(var34.getName()).append(var34.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var34.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var13));
            var42 = ((String)this.mode.getValue()).equalsIgnoreCase("Right") ? var4 - (double)Xulu.cFontRenderer.getStringWidth(var14) + this.getFrame().width - 3.0D : var4;
            var17 = (double)Xulu.cFontRenderer.getStringWidth(var14);
            if ((Boolean)this.box.getValue()) {
               var19 = (String)this.boxMode.getValue();
               var20 = -1;
               switch(var19.hashCode()) {
               case 83834:
                  if (var19.equals("Tag")) {
                     var20 = 1;
                  }
                  break;
               case 64266207:
                  if (var19.equals("Black")) {
                     var20 = 0;
                  }
                  break;
               case 558407714:
                  if (var19.equals("Outline")) {
                     var20 = 2;
                  }
               }

               switch(var20) {
               case 0:
                  Gui.drawRect((int)var42 - 1, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + (int)Xulu.cFontRenderer.getHeight(), 1427181841);
                  break;
               case 1:
                  Gui.drawRect((int)var42 - 1, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + (int)Xulu.cFontRenderer.getHeight(), 1427181841);
                  Gui.drawRect((int)var42 - 1, (int)var2 - 1, (int)var42 + 1, (int)var2 + (int)Xulu.cFontRenderer.getHeight(), var11);
                  break;
               case 2:
                  Gui.drawRect((int)var42 - 1, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + (int)Xulu.cFontRenderer.getHeight(), 1427181841);
                  XuluTessellator.drawRectOutline((double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), (double)((int)var42 - 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 3), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), var11);
                  if (var6.indexOf(var34) == 0) {
                     XuluTessellator.drawRectOutline((double)((int)var42 - 2), (double)((int)var2 - 2), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), (double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), var11);
                     if (var6.indexOf(var34) + 1 < var6.size()) {
                        var21 = (Module)var6.get(var6.indexOf(var34) + 1);
                        var22 = String.valueOf((new StringBuilder()).append(var35).append(var21.getName()).append(var21.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var21.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var13));
                        var23 = (double)Xulu.cFontRenderer.getStringWidth(var22);
                        XuluTessellator.drawRectOutline((double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 - 1.0D, (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight() + 1), (double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 - 1.0D, (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), var11);
                     }
                  } else if (var6.indexOf(var34) + 1 == var6.size()) {
                     XuluTessellator.drawRectOutline((double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight() + 1), (double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), var11);
                  } else {
                     var21 = (Module)var6.get(var6.indexOf(var34) + 1);
                     var22 = String.valueOf((new StringBuilder()).append(var35).append(var21.getName()).append(var21.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var21.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var13));
                     var23 = (double)Xulu.cFontRenderer.getStringWidth(var22);
                     XuluTessellator.drawRectOutline((double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 - 1.0D, (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight() + 1), (double)((int)var42 - 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 - 1.0D, (double)((int)var2 + (int)Xulu.cFontRenderer.getHeight()), var11);
                  }
               }

               var27 = false;
            }

            Xulu.cFontRenderer.drawStringWithShadow(var34.inAnimation.getValue() != Module.Animation.NONE ? "" : var14, var42 + 1.0D, var2, ColorUtils.changeAlpha(var11, (Integer)Global.hudAlpha.getValue()), true);
            if (!this.animationMap.containsKey(var34)) {
               if (var34.inAnimation.getValue() != Module.Animation.NONE) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Right")) {
                     if (var34.inAnimation.getValue() == Module.Animation.ENABLE) {
                        this.animationMap.put(var34, new Triplet(var42 + var17, var2, new Pair(var42, var11)));
                     } else {
                        this.animationMap.put(var34, new Triplet(var42, var2, new Pair(var42 + var17, var11)));
                     }
                  } else if (((String)this.mode.getValue()).equalsIgnoreCase("Left")) {
                     if (var34.inAnimation.getValue() == Module.Animation.ENABLE) {
                        this.animationMap.put(var34, new Triplet(var42 - var17, var2, new Pair(var42, var11)));
                     } else {
                        this.animationMap.put(var34, new Triplet(var42, var2, new Pair(var42 - var17, var11)));
                     }
                  }
               }
            } else {
               ((Pair)((Triplet)this.animationMap.get(var34)).getThird()).setValue(var11);
            }

            var2 += (double)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10);
            var44 = (double)(Integer)this.rspeed.getValue();
         }

         var31 = this.animationMap.keySet().iterator();

         while(var31.hasNext()) {
            var34 = (Module)var31.next();
            Triplet var37 = (Triplet)this.animationMap.get(var34);
            var14 = (String)this.prefix.getValue();
            var15 = -1;
            switch(var14.hashCode()) {
            case 2433880:
               if (var14.equals("None")) {
                  var15 = 0;
               }
               break;
            case 924274701:
               if (var14.equals(">(space)")) {
                  var15 = 1;
               }
            }

            switch(var15) {
            case 0:
               var35 = "";
               break;
            case 1:
               var35 = "> ";
               break;
            default:
               var35 = (String)this.prefix.getValue();
            }

            var14 = (String)this.suffix.getValue();
            var15 = -1;
            switch(var14.hashCode()) {
            case -1019228271:
               if (var14.equals("(space)<")) {
                  var15 = 1;
               }
               break;
            case 2433880:
               if (var14.equals("None")) {
                  var15 = 0;
               }
            }

            switch(var15) {
            case 0:
               var13 = "";
               break;
            case 1:
               var13 = " <";
               break;
            default:
               var13 = (String)this.suffix.getValue();
            }

            var14 = String.valueOf((new StringBuilder()).append(var35).append(var34.getName()).append(var34.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var34.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var13));
            Xulu.cFontRenderer.drawStringWithShadow(var14, (Double)var37.getFirst(), (Double)var37.getSecond(), ColorUtils.changeAlpha((Integer)((Pair)var37.getThird()).getValue(), (Integer)Global.hudAlpha.getValue()), true);
            if (!((Double)var37.getFirst()).equals(((Pair)var37.getThird()).getKey())) {
               if ((Double)var37.getFirst() > (Double)((Pair)var37.getThird()).getKey()) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Left")) {
                     var37.setFirst(((Pair)var37.getThird()).getKey());
                  }

                  var37.setFirst((Double)var37.getFirst() - 1.0D);
               }

               if ((Double)var37.getFirst() < (Double)((Pair)var37.getThird()).getKey()) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Right")) {
                     var37.setFirst(((Pair)var37.getThird()).getKey());
                  }

                  var37.setFirst((Double)var37.getFirst() + 1.0D);
               }
            } else {
               var34.inAnimation.setEnumValue("NONE");
               this.removal.add(var34);
            }
         }

         this.removal.forEach((var1x) -> {
            if (var1x.inAnimation.getValue() == Module.Animation.NONE) {
               this.animationMap.remove(var1x);
            }

         });
         this.removal.clear();
      } else {
         var6 = (List)Xulu.MODULE_MANAGER.getModules().stream().filter(Module::isToggledAnim).filter(Module::isDrawn).sorted(Comparator.comparing((var0) -> {
            return Wrapper.getMinecraft().fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var0.getName()).append(var0.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(var0.getHudInfo()).append("]"))))) * -1;
         })).collect(Collectors.toList());
         if ((Boolean)this.alphab.getValue()) {
            var7 = (String[])((List)var6.stream().map(Module::getName).collect(Collectors.toList())).toArray(new String[0]);
            var8 = var6.size();
            var10 = 0;

            label490:
            while(true) {
               if (var10 >= var8) {
                  var6.clear();
                  var33 = var7;
                  var11 = var7.length;
                  var12 = 0;

                  while(true) {
                     if (var12 >= var11) {
                        break label490;
                     }

                     var13 = var33[var12];

                     try {
                        var6.add(Xulu.MODULE_MANAGER.getModuleByName(var13));
                     } catch (Exception var25) {
                     }

                     ++var12;
                  }
               }

               for(var11 = var10 + 1; var11 < var8; ++var11) {
                  if (var7[var10].compareTo(var7[var11]) > 0) {
                     var9 = var7[var10];
                     var7[var10] = var7[var11];
                     var7[var11] = var9;
                  }
               }

               ++var10;
            }
         }

         float var28 = this.rainbow.hue;
         if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
            var2 += 69.0D;
         }

         Iterator var30;
         Module var32;
         String var39;
         byte var40;
         for(var30 = var6.iterator(); var30.hasNext(); var28 = (float)((double)var28 + var44 / 100.0D)) {
            var32 = (Module)var30.next();
            var10 = Color.HSBtoRGB(var28, (float)(Integer)this.rsaturation.getValue() / 255.0F, (float)(Integer)this.rlightness.getValue() / 255.0F);
            var39 = (String)this.rlist.getValue();
            byte var41 = -1;
            switch(var39.hashCode()) {
            case 115155230:
               if (var39.equals("Category")) {
                  var41 = 1;
               }
               break;
            case 971074227:
               if (var39.equals("ClickGui")) {
                  var41 = 0;
               }
            }

            switch(var41) {
            case 0:
               var10 = ColorUtil.getClickGUIColor().getRGB();
               break;
            case 1:
               var10 = this.getCategoryColor(var32);
            }

            var13 = (String)this.prefix.getValue();
            var40 = -1;
            switch(var13.hashCode()) {
            case 2433880:
               if (var13.equals("None")) {
                  var40 = 0;
               }
               break;
            case 924274701:
               if (var13.equals(">(space)")) {
                  var40 = 1;
               }
            }

            switch(var40) {
            case 0:
               var39 = "";
               break;
            case 1:
               var39 = "> ";
               break;
            default:
               var39 = (String)this.prefix.getValue();
            }

            var13 = (String)this.suffix.getValue();
            var40 = -1;
            switch(var13.hashCode()) {
            case -1019228271:
               if (var13.equals("(space)<")) {
                  var40 = 1;
               }
               break;
            case 2433880:
               if (var13.equals("None")) {
                  var40 = 0;
               }
            }

            switch(var40) {
            case 0:
               var35 = "";
               break;
            case 1:
               var35 = " <";
               break;
            default:
               var35 = (String)this.suffix.getValue();
            }

            boolean var43 = true;
            var14 = String.valueOf((new StringBuilder()).append(var39).append(var32.getName()).append(var32.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var32.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var35));
            var42 = (double)((int)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? var4 - (double)Wrapper.getMinecraft().fontRenderer.getStringWidth(var14) + this.getFrame().width - 3.0D : var4));
            var17 = (double)Wrapper.getMinecraft().fontRenderer.getStringWidth(var14);
            if ((Boolean)this.box.getValue()) {
               var19 = (String)this.boxMode.getValue();
               var20 = -1;
               switch(var19.hashCode()) {
               case 83834:
                  if (var19.equals("Tag")) {
                     var20 = 1;
                  }
                  break;
               case 64266207:
                  if (var19.equals("Black")) {
                     var20 = 0;
                  }
                  break;
               case 558407714:
                  if (var19.equals("Outline")) {
                     var20 = 2;
                  }
               }

               switch(var20) {
               case 0:
                  Gui.drawRect((int)var42 + 2, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, 1427181841);
                  break;
               case 1:
                  Gui.drawRect((int)var42 - 2, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, 1427181841);
                  Gui.drawRect((int)var42 - 2, (int)var2 - 1, (int)var42 + 1, (int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, var10);
                  break;
               case 2:
                  Gui.drawRect((int)var42 + 2, (int)var2 - 1, (int)var42 + (int)var17 + 3, (int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, 1427181841);
                  XuluTessellator.drawRectOutline((double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), (double)((int)var42 + 2), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 3), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), var10);
                  if (var6.indexOf(var32) == 0) {
                     XuluTessellator.drawRectOutline((double)((int)var42 + 1), (double)((int)var2 - 2), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), (double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), var10);
                     if (var6.indexOf(var32) + 1 < var6.size()) {
                        var21 = (Module)var6.get(var6.indexOf(var32) + 1);
                        var22 = String.valueOf((new StringBuilder()).append(var39).append(var21.getName()).append(var21.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var21.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var35));
                        var23 = (double)Wrapper.getMinecraft().fontRenderer.getStringWidth(var22);
                        XuluTessellator.drawRectOutline((double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 + 2.0D, (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT + 1), (double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 + 2.0D, (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), var10);
                     }
                  } else if (var6.indexOf(var32) + 1 == var6.size()) {
                     XuluTessellator.drawRectOutline((double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT + 1), (double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17 + 4), (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), var10);
                  } else {
                     var21 = (Module)var6.get(var6.indexOf(var32) + 1);
                     var22 = String.valueOf((new StringBuilder()).append(var39).append(var21.getName()).append(var21.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var21.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var35));
                     var23 = (double)Wrapper.getMinecraft().fontRenderer.getStringWidth(var22);
                     XuluTessellator.drawRectOutline((double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 + 2.0D, (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT + 1), (double)((int)var42 + 1), (double)((int)var2 - 1), (double)((int)var42 + (int)var17) - var23 + 2.0D, (double)((int)var2 + Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT), var10);
                  }
               }

               var43 = false;
            }

            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var32.inAnimation.getValue() != Module.Animation.NONE ? "" : var14, (float)((int)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? var4 - (double)Wrapper.getMinecraft().fontRenderer.getStringWidth(var14) + this.getFrame().width : var4)), (float)((int)var2), ColorUtils.changeAlpha(var10, (Integer)Global.hudAlpha.getValue()));
            if (!this.animationMap.containsKey(var32)) {
               if (var32.inAnimation.getValue() != Module.Animation.NONE) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Right")) {
                     if (var32.inAnimation.getValue() == Module.Animation.ENABLE) {
                        this.animationMap.put(var32, new Triplet(var42 + var17, var2, new Pair(var42, var10)));
                     } else {
                        this.animationMap.put(var32, new Triplet(var42, var2, new Pair(var42 + var17, var10)));
                     }
                  } else if (((String)this.mode.getValue()).equalsIgnoreCase("Left")) {
                     if (var32.inAnimation.getValue() == Module.Animation.ENABLE) {
                        this.animationMap.put(var32, new Triplet(var42 - var17, var2, new Pair(var42, var10)));
                     } else {
                        this.animationMap.put(var32, new Triplet(var42, var2, new Pair(var42 - var17, var10)));
                     }
                  }
               }
            } else {
               ((Pair)((Triplet)this.animationMap.get(var32)).getThird()).setValue(var10);
            }

            var2 += (double)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10);
            var44 = (double)(Integer)this.rspeed.getValue();
         }

         var30 = this.animationMap.keySet().iterator();

         while(var30.hasNext()) {
            var32 = (Module)var30.next();
            Triplet var38 = (Triplet)this.animationMap.get(var32);
            var13 = (String)this.prefix.getValue();
            var40 = -1;
            switch(var13.hashCode()) {
            case 2433880:
               if (var13.equals("None")) {
                  var40 = 0;
               }
               break;
            case 924274701:
               if (var13.equals(">(space)")) {
                  var40 = 1;
               }
            }

            switch(var40) {
            case 0:
               var39 = "";
               break;
            case 1:
               var39 = "> ";
               break;
            default:
               var39 = (String)this.prefix.getValue();
            }

            var13 = (String)this.suffix.getValue();
            var40 = -1;
            switch(var13.hashCode()) {
            case -1019228271:
               if (var13.equals("(space)<")) {
                  var40 = 1;
               }
               break;
            case 2433880:
               if (var13.equals("None")) {
                  var40 = 0;
               }
            }

            switch(var40) {
            case 0:
               var35 = "";
               break;
            case 1:
               var35 = " <";
               break;
            default:
               var35 = (String)this.suffix.getValue();
            }

            var13 = String.valueOf((new StringBuilder()).append(var39).append(var32.getName()).append(var32.getHudInfo() == null ? "" : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("7 [").append(Command.SECTIONSIGN()).append("f").append(var32.getHudInfo()).append(Command.SECTIONSIGN()).append("7]").append(ChatFormatting.RESET))).append(var35));
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var13, ((Double)var38.getFirst()).floatValue(), ((Double)var38.getSecond()).floatValue(), ColorUtils.changeAlpha((Integer)((Pair)var38.getThird()).getValue(), (Integer)Global.hudAlpha.getValue()));
            if (!((Double)var38.getFirst()).equals(((Pair)var38.getThird()).getKey())) {
               if ((Double)var38.getFirst() > (Double)((Pair)var38.getThird()).getKey()) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Left")) {
                     var38.setFirst(((Pair)var38.getThird()).getKey());
                  }

                  var38.setFirst((Double)var38.getFirst() - 1.0D);
               }

               if ((Double)var38.getFirst() < (Double)((Pair)var38.getThird()).getKey()) {
                  if (((String)this.mode.getValue()).equalsIgnoreCase("Right")) {
                     var38.setFirst(((Pair)var38.getThird()).getKey());
                  }

                  var38.setFirst((Double)var38.getFirst() + 1.0D);
               }
            } else {
               var32.inAnimation.setEnumValue("NONE");
               this.removal.add(var32);
            }
         }

         this.removal.forEach((var1x) -> {
            if (var1x.inAnimation.getValue() == Module.Animation.NONE) {
               this.animationMap.remove(var1x);
            }

         });
         this.removal.clear();
      }

   }

   public String getTitle(String var1) {
      var1 = String.valueOf((new StringBuilder()).append(Character.toUpperCase(var1.toLowerCase().charAt(0))).append(var1.toLowerCase().substring(1)));
      return var1;
   }

   public class Rainbow {
      // $FF: synthetic field
      public int rgb;
      // $FF: synthetic field
      public int g;
      // $FF: synthetic field
      public int a;
      // $FF: synthetic field
      public int b;
      // $FF: synthetic field
      float hue = 0.01F;
      // $FF: synthetic field
      public int r;

      public void updateRainbow() {
         this.rgb = Color.HSBtoRGB(this.hue, (float)(Integer)((FeatureList)Xulu.MODULE_MANAGER.getModuleT(FeatureList.class)).rsaturation.getValue() / 255.0F, (float)(Integer)((FeatureList)Xulu.MODULE_MANAGER.getModuleT(FeatureList.class)).rlightness.getValue() / 255.0F);
         this.a = this.rgb >>> 24 & 255;
         this.r = this.rgb >>> 16 & 255;
         this.g = this.rgb >>> 8 & 255;
         this.b = this.rgb & 255;
         this.hue += (float)(Integer)((FeatureList)Xulu.MODULE_MANAGER.getModuleT(FeatureList.class)).rainbowspeed.getValue() / 10000.0F;
         if (this.hue > 1.0F) {
            --this.hue;
         }

      }
   }
}
