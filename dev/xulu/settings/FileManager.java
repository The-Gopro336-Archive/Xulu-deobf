package dev.xulu.settings;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.dummy.DummyMod;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.enemy.Enemy;
import com.elementars.eclient.font.CFontManager;
import com.elementars.eclient.friend.Friend;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.friend.Nicknames;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.guirewrite.Frame;
import com.elementars.eclient.guirewrite.HUD;
import com.elementars.eclient.guirewrite.elements.StickyNotes;
import com.elementars.eclient.guirewrite.elements.Welcome;
import com.elementars.eclient.macro.Macro;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.render.Search;
import com.elementars.eclient.module.render.Waypoints;
import com.elementars.eclient.module.render.Xray;
import com.elementars.eclient.util.FontHelper;
import com.elementars.eclient.util.NumberUtils;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.NewGUI;
import dev.xulu.newgui.Panel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class FileManager {
   // $FF: synthetic field
   public File EclientSettings;
   // $FF: synthetic field
   public File Eclient;
   // $FF: synthetic field
   public File EclientCache;
   // $FF: synthetic field
   public File EclientStorageESP;

   public void loadFriends() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Friends.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               Friends.addFriend(var5);
            } catch (Exception var7) {
            }
         }

         var4.close();
      } catch (Exception var8) {
         var8.printStackTrace();
         this.saveFriends();
      }

   }

   public void saveWelcomeMessage() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Welcome.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         var2.write(String.valueOf((new StringBuilder()).append(Welcome.text).append("\r\n")));
         var2.close();
      } catch (Exception var3) {
      }

   }

   public void saveStorageESP(String var1, String var2, String var3) {
      try {
         File var4 = new File(this.EclientStorageESP.getAbsolutePath(), String.valueOf((new StringBuilder()).append(var1).append(".txt")));
         BufferedWriter var5 = new BufferedWriter(new FileWriter(var4));
         var5.write(String.valueOf((new StringBuilder()).append(var2).append(" with ").append(var3).append(" chests")));
         var5.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public void saveNewGui() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "NewGui.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = NewGUI.panels.iterator();

         while(var3.hasNext()) {
            Panel var4 = (Panel)var3.next();

            try {
               var2.write(String.valueOf((new StringBuilder()).append(var4.title).append(":").append(var4.x).append(":").append(var4.y).append(":").append(var4.extended)));
               var2.write("\r\n");
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void loadHacks() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Modules.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            Iterator var6 = Xulu.MODULE_MANAGER.getModules().iterator();

            while(var6.hasNext()) {
               Module var7 = (Module)var6.next();

               try {
                  if (var7.getName().equals(var5)) {
                     var7.initToggle(true);
                  }

                  if (var7 instanceof Element) {
                     ((Element)var7).getFrame().pinned = true;
                  }
               } catch (Exception var9) {
               }
            }
         }

         var4.close();
      } catch (Exception var10) {
         var10.printStackTrace();
         this.saveHacks();
      }

   }

   public void saveFont() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Font.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         var2.write(String.valueOf((new StringBuilder()).append("Normal:").append(CFontManager.customFont.getFont().getFontName().equalsIgnoreCase("Comfortaa Regular") ? "Comfortaa" : CFontManager.customFont.getFont().getFontName()).append(":").append(CFontManager.customFont.getFont().getSize()).append(":").append(CFontManager.customFont.getFont().getStyle()).append(":").append(CFontManager.customFont.isAntiAlias()).append(":").append(CFontManager.customFont.isFractionalMetrics()).append("\r\n")));
         var2.write(String.valueOf((new StringBuilder()).append("Xdolf:").append(CFontManager.xFontRenderer.getFont().getFont().getFontName().equalsIgnoreCase("Comfortaa Regular") ? "Comfortaa" : CFontManager.xFontRenderer.getFont().getFont().getFontName()).append(":").append(CFontManager.xFontRenderer.getFont().getFont().getSize()).append(":").append(CFontManager.xFontRenderer.getFont().getFont().getStyle()).append(":").append(CFontManager.xFontRenderer.isAntiAliasing()).append(":NULL\r\n")));
         var2.close();
      } catch (Exception var3) {
      }

   }

   public void savePrefix() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Prefix.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         var2.write(Command.getPrefix());
         var2.write("\r\n");
         var2.close();
      } catch (Exception var3) {
      }

   }

   public void loadFont() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Font.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               String var9 = var6.split(":")[2];
               String var10 = var6.split(":")[3];
               String var11 = var6.split(":")[4];
               String var12 = var6.split(":")[5];
               int var13 = Integer.parseInt(var9);
               int var14 = Integer.parseInt(var10);
               boolean var15 = Boolean.parseBoolean(var11);
               boolean var16 = !var12.equalsIgnoreCase("null") && Boolean.parseBoolean(var12);
               if (var7.equalsIgnoreCase("Normal")) {
                  FontHelper.setCFontRenderer(var8, var14, var13, var15, var16);
               } else if (var7.equalsIgnoreCase("Xdolf")) {
                  FontHelper.setXdolfFontRenderer(var8, var14, var13, var15);
               } else {
                  System.out.println("Invalid Font Type!");
               }
            } catch (Exception var17) {
            }
         }

         var4.close();
      } catch (Exception var18) {
         var18.printStackTrace();
         this.saveFont();
      }

   }

   public void loadWaypoints() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Waypoints.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               String var9 = var6.split(":")[2];
               String var10 = var6.split(":")[3];
               String var11 = var6.split(":")[4];
               int var12 = Integer.parseInt(var8);
               int var13 = Integer.parseInt(var9);
               int var14 = Integer.parseInt(var10);
               int var15 = Integer.parseInt(var11);
               Waypoints.WAYPOINTS.add(new Waypoints.Waypoint(UUID.randomUUID(), var7, new BlockPos(var12, var13, var14), (AxisAlignedBB)null, var15));
            } catch (Exception var17) {
            }
         }

         var4.close();
      } catch (Exception var18) {
         var18.printStackTrace();
         this.saveDrawn();
      }

   }

   public FileManager() {
      this.Eclient = new File(String.valueOf((new StringBuilder()).append(Wrapper.getMinecraft().gameDir).append(File.separator).append("Xulu")));
      if (!this.Eclient.exists()) {
         this.Eclient.mkdirs();
      }

      this.EclientSettings = new File(String.valueOf((new StringBuilder()).append(Wrapper.getMinecraft().gameDir).append(File.separator).append("Xulu").append(File.separator).append("Xulu Settings")));
      if (!this.EclientSettings.exists()) {
         this.EclientSettings.mkdirs();
      }

      this.EclientStorageESP = new File(String.valueOf((new StringBuilder()).append(Wrapper.getMinecraft().gameDir).append(File.separator).append("Xulu").append(File.separator).append("StorageESP Logs")));
      if (!this.EclientStorageESP.exists()) {
         this.EclientStorageESP.mkdirs();
      }

   }

   public void loadWelcomeMessage() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Welcome.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               Welcome.handleWelcome(var6);
            } catch (Exception var7) {
            }
         }

         var4.close();
      } catch (Exception var8) {
         var8.printStackTrace();
         this.saveWelcomeMessage();
      }

   }

   public void saveStickyNote() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Note.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         var2.write(String.valueOf((new StringBuilder()).append(StickyNotes.saveText).append("\r\n")));
         var2.close();
      } catch (Exception var3) {
      }

   }

   public void saveHacks() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Modules.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xulu.MODULE_MANAGER.getModules().iterator();

         while(var3.hasNext()) {
            Module var4 = (Module)var3.next();

            try {
               if (var4.isToggled() && !var4.getName().matches("null") && !var4.getName().equals("Log Out Spot") && !var4.getName().equals("Freecam") && !var4.getName().equals("Blink") && !var4.getName().equals("Join/Leave msgs") && !var4.getName().equals("Elytra +") && !var4.getName().equals("Sound")) {
                  var2.write(var4.getName());
                  var2.write("\r\n");
               }
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void loadEnemies() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Enemies.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               Enemies.addEnemy(var5);
            } catch (Exception var8) {
            }
         }

         var4.close();
      } catch (Exception var9) {
         var9.printStackTrace();
         this.saveEnemies();
      }

   }

   public void saveXray() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Xray.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xray.getBLOCKS().iterator();

         while(var3.hasNext()) {
            Block var4 = (Block)var3.next();

            try {
               var2.write(((ResourceLocation)Block.REGISTRY.getNameForObject(var4)).getPath());
               var2.write("\r\n");
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void saveSettingsList() {
      File var1;
      BufferedWriter var2;
      Iterator var3;
      Value var4;
      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Slider.txt");
         var2 = new BufferedWriter(new FileWriter(var1));
         var3 = Xulu.VALUE_MANAGER.getValues().iterator();

         while(var3.hasNext()) {
            var4 = (Value)var3.next();
            if (var4.isNumber()) {
               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getValue().toString()).append(":").append(var4.getParentMod().getName()).append("\r\n")));
            }
         }

         var2.close();
      } catch (Exception var8) {
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Check.txt");
         var2 = new BufferedWriter(new FileWriter(var1));
         var3 = Xulu.VALUE_MANAGER.getValues().iterator();

         while(var3.hasNext()) {
            var4 = (Value)var3.next();
            if (var4.isToggle()) {
               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getValue().toString()).append(":").append(var4.getParentMod().getName()).append("\r\n")));
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Combo.txt");
         var2 = new BufferedWriter(new FileWriter(var1));
         var3 = Xulu.VALUE_MANAGER.getValues().iterator();

         while(var3.hasNext()) {
            var4 = (Value)var3.next();
            if (var4.isMode()) {
               if (((String)var4.getValue()).contains(":")) {
                  var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(";").append(var4.getValue().toString()).append(";").append(var4.getParentMod().getName()).append("\r\n")));
               }

               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getValue().toString()).append(":").append(var4.getParentMod().getName()).append("\r\n")));
            }

            if (var4.isEnum()) {
               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getValue().toString()).append(":").append(var4.getParentMod().getName()).append("\r\n")));
            }
         }

         var2.close();
      } catch (Exception var6) {
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "TextBox.txt");
         var2 = new BufferedWriter(new FileWriter(var1));
         var3 = Xulu.VALUE_MANAGER.getValues().iterator();

         while(var3.hasNext()) {
            var4 = (Value)var3.next();
            if (var4.isText()) {
               if (((TextBox)var4.getValue()).getText().contains(":")) {
                  var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(";").append(((TextBox)var4.getValue()).getText()).append(";").append(var4.getParentMod().getName()).append("\r\n")));
               }

               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(((TextBox)var4.getValue()).getText()).append(":").append(var4.getParentMod().getName()).append("\r\n")));
            }
         }

         var2.close();
      } catch (Exception var5) {
      }

   }

   public void loadHUD() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "HUD.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               String var9 = var6.split(":")[2];
               double var10 = Double.parseDouble(var8);
               double var12 = Double.parseDouble(var9);
               if (var7.equalsIgnoreCase(Xulu.hud.hudPanel.title)) {
                  Xulu.hud.hudPanel.x = var10;
                  Xulu.hud.hudPanel.y = var12;
               }

               Frame var14 = HUD.getframeByName(var7);
               if (var14 != null) {
                  var14.x = var10;
                  var14.y = var12;
               }

               Element var15 = (Element)Xulu.MODULE_MANAGER.getModuleByName(var7);
               if (var15 != null) {
                  var15.setX(var10);
                  var15.setY(var12);
               }
            } catch (Exception var16) {
            }
         }

         var4.close();
      } catch (Exception var17) {
         var17.printStackTrace();
         this.saveHUD();
      }

   }

   public void saveSearch() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Search.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Search.getBLOCKS().iterator();

         while(var3.hasNext()) {
            Block var4 = (Block)var3.next();

            try {
               var2.write(((ResourceLocation)Block.REGISTRY.getNameForObject(var4)).getPath());
               var2.write("\r\n");
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void saveHUD() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "HUD.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = HUD.frames.iterator();

         while(var3.hasNext()) {
            Frame var4 = (Frame)var3.next();
            var2.write(String.valueOf((new StringBuilder()).append(var4.title).append(":").append(var4.x).append(":").append(var4.y)));
            var2.write("\r\n");
         }

         var2.write(String.valueOf((new StringBuilder()).append(Xulu.hud.hudPanel.title).append(":").append(Xulu.hud.hudPanel.x).append(":").append(Xulu.hud.hudPanel.y)));
         var2.write("\r\n");
         var2.close();
      } catch (Exception var5) {
      }

   }

   public void loadNicks() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Nicknames.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               Nicknames.addNickname(var7, var8);
            } catch (Exception var9) {
            }
         }

         var4.close();
      } catch (Exception var10) {
         var10.printStackTrace();
         this.saveFriends();
      }

   }

   public void loadXray() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Xray.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
         Xray.getBLOCKS().clear();

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               Xray.getBLOCKS().add(Objects.requireNonNull(Block.getBlockFromName(var5)));
            } catch (NullPointerException var7) {
            }
         }

         var4.close();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public void saveMacros() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Macros.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xulu.MACRO_MANAGER.getMacros().iterator();

         while(var3.hasNext()) {
            Macro var4 = (Macro)var3.next();
            var2.write(String.valueOf((new StringBuilder()).append(var4.getMacro()).append(":").append(var4.getKey()).append("\r\n")));
         }

         var2.close();
      } catch (Exception var5) {
      }

   }

   public void loadStickyNote() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Note.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               StickyNotes.processText(var7);
            } catch (Exception var8) {
            }
         }

         var4.close();
      } catch (Exception var9) {
         var9.printStackTrace();
         this.saveStickyNote();
      }

   }

   public void saveDummy() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Dummy.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xulu.MODULE_MANAGER.getModules().iterator();

         while(var3.hasNext()) {
            Module var4 = (Module)var3.next();
            if (var4.getCategory() == Category.DUMMY) {
               try {
                  var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getHudInfo() == null ? "null" : var4.getHudInfo())));
                  var2.write("\r\n");
               } catch (Exception var6) {
               }
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void loadBinds() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Binds.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               int var9 = Integer.parseInt(var8);
               Module var10 = Xulu.MODULE_MANAGER.getModuleByName(var7);
               if (var10 != null) {
                  var10.setKey(var9);
               }
            } catch (Exception var11) {
            }
         }

         var4.close();
      } catch (Exception var12) {
         var12.printStackTrace();
         this.saveBinds();
      }

   }

   public boolean appendTextFile(String var1, String var2) {
      try {
         Path var3 = Paths.get(var2);
         Files.write(var3, Collections.singletonList(var1), StandardCharsets.UTF_8, Files.exists(var3, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
         return true;
      } catch (IOException var4) {
         System.out.println(String.valueOf((new StringBuilder()).append("WARNING: Unable to write file: ").append(var2)));
         return false;
      }
   }

   public void loadSearch() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Search.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
         Search.getBLOCKS().clear();

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               Search.getBLOCKS().add(Objects.requireNonNull(Block.getBlockFromName(var5)));
            } catch (NullPointerException var7) {
            }
         }

         var4.close();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public void saveDrawn() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Drawn.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xulu.MODULE_MANAGER.getModules().iterator();

         while(var3.hasNext()) {
            Module var4 = (Module)var3.next();
            var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.isDrawn())));
            var2.write("\r\n");
         }

         var2.close();
      } catch (Exception var5) {
      }

   }

   public void saveEnemies() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Enemies.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Enemies.getEnemies().iterator();

         while(var3.hasNext()) {
            Enemy var4 = (Enemy)var3.next();

            try {
               var2.write(var4.getUsername());
               var2.write("\r\n");
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void saveWaypoints() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Waypoints.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Waypoints.WAYPOINTS.iterator();

         while(var3.hasNext()) {
            Waypoints.Waypoint var4 = (Waypoints.Waypoint)var3.next();
            var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getPos().x).append(":").append(var4.getPos().y).append(":").append(var4.getPos().z).append(":").append(var4.getDimension())));
            var2.write("\r\n");
         }

         var2.close();
      } catch (Exception var5) {
      }

   }

   public void writeCrash(String var1) {
      try {
         SimpleDateFormat var2 = new SimpleDateFormat("MM_dd_yyyy-HH_mm_ss");
         Date var3 = new Date();
         File var4 = new File(this.Eclient.getAbsolutePath(), "crashlog-".concat(var2.format(var3)).concat(".xen"));
         BufferedWriter var5 = new BufferedWriter(new FileWriter(var4));
         var5.write(var1);
         var5.close();
      } catch (Exception var6) {
      }

   }

   public void saveBinds() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Binds.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Xulu.MODULE_MANAGER.getModules().iterator();

         while(var3.hasNext()) {
            Module var4 = (Module)var3.next();

            try {
               var2.write(String.valueOf((new StringBuilder()).append(var4.getName()).append(":").append(var4.getKey())));
               var2.write("\r\n");
            } catch (Exception var6) {
            }
         }

         var2.close();
      } catch (Exception var7) {
      }

   }

   public void saveNicks() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Nicknames.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Nicknames.getAliases().forEach((var1x, var2x) -> {
            try {
               var2.write(String.valueOf((new StringBuilder()).append(var1x).append(":").append(var2x)));
               var2.write("\r\n");
            } catch (Exception var4) {
            }

         });
         var2.close();
      } catch (Exception var3) {
      }

   }

   public void saveFriends() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Friends.txt");
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         Iterator var3 = Friends.getFriends().iterator();

         while(var3.hasNext()) {
            Friend var4 = (Friend)var3.next();

            try {
               var2.write(var4.getUsername());
               var2.write("\r\n");
            } catch (Exception var7) {
            }
         }

         var2.close();
      } catch (Exception var8) {
      }

   }

   public void loadPrefix() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Prefix.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            Command.setPrefix(var5);
         }

         var4.close();
      } catch (Exception var6) {
         var6.printStackTrace();
         this.savePrefix();
      }

   }

   public void loadSettingsList() {
      File var1;
      FileInputStream var2;
      DataInputStream var3;
      BufferedReader var4;
      String var5;
      String var6;
      String var7;
      String var8;
      String var9;
      Value var10;
      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Slider.txt");
         var2 = new FileInputStream(var1.getAbsolutePath());
         var3 = new DataInputStream(var2);
         var4 = new BufferedReader(new InputStreamReader(var3));

         while((var5 = var4.readLine()) != null) {
            try {
               var6 = var5.trim();
               var7 = var6.split(":")[0];
               var8 = var6.split(":")[1];
               var9 = var6.split(":")[2];
               var10 = Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleByName(var9), var7);
               Object var11 = 0;
               if (var10.getValue() instanceof Double) {
                  var11 = NumberUtils.createDouble(var8);
               } else if (var10.getValue() instanceof Integer) {
                  var11 = NumberUtils.createInteger(var8);
               } else if (var10.getValue() instanceof Float) {
                  var11 = NumberUtils.createFloat(var8);
               } else if (var10.getValue() instanceof Long) {
                  var11 = NumberUtils.createLong(var8);
               } else if (var10.getValue() instanceof Short) {
                  var11 = NumberUtils.createShort(var8);
               }

               var10.setValue(var11);
            } catch (Exception var16) {
            }
         }

         var4.close();
      } catch (Exception var20) {
         var20.printStackTrace();
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Check.txt");
         var2 = new FileInputStream(var1.getAbsolutePath());
         var3 = new DataInputStream(var2);
         var4 = new BufferedReader(new InputStreamReader(var3));

         while((var5 = var4.readLine()) != null) {
            try {
               var6 = var5.trim();
               var7 = var6.split(":")[0];
               var8 = var6.split(":")[1];
               var9 = var6.split(":")[2];
               var10 = Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleByName(var9), var7);
               var10.setValue(Boolean.parseBoolean(var8));
            } catch (Exception var15) {
            }
         }

         var4.close();
      } catch (Exception var19) {
         var19.printStackTrace();
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "Combo.txt");
         var2 = new FileInputStream(var1.getAbsolutePath());
         var3 = new DataInputStream(var2);
         var4 = new BufferedReader(new InputStreamReader(var3));

         while((var5 = var4.readLine()) != null) {
            try {
               var6 = var5.trim();
               if (var6.contains(";")) {
                  var7 = var6.split(";")[0];
                  var8 = var6.split(";")[1];
                  var9 = var6.split(";")[2];
               } else {
                  var7 = var6.split(":")[0];
                  var8 = var6.split(":")[1];
                  var9 = var6.split(":")[2];
               }

               var10 = Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleByName(var9), var7);
               if (var10.isEnum()) {
                  var10.setEnumValue(var8);
               } else {
                  var10.setValue(var8);
               }
            } catch (Exception var14) {
            }
         }

         var4.close();
      } catch (Exception var18) {
         var18.printStackTrace();
      }

      try {
         var1 = new File(this.EclientSettings.getAbsolutePath(), "TextBox.txt");
         var2 = new FileInputStream(var1.getAbsolutePath());
         var3 = new DataInputStream(var2);
         var4 = new BufferedReader(new InputStreamReader(var3));

         while((var5 = var4.readLine()) != null) {
            try {
               var6 = var5.trim();
               if (var6.contains(";")) {
                  var7 = var6.split(";")[0];
                  var8 = var6.split(";")[1];
                  var9 = var6.split(";")[2];
               } else {
                  var7 = var6.split(":")[0];
                  var8 = var6.split(":")[1];
                  var9 = var6.split(":")[2];
               }

               var10 = Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleByName(var9), var7);
               var10.setValue(new TextBox(var8));
            } catch (Exception var13) {
            }
         }

         var4.close();
      } catch (Exception var17) {
         var17.printStackTrace();
      }

   }

   public void loadMacros() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Macros.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               int var9 = Integer.valueOf(var8);
               if (!Xulu.MACRO_MANAGER.getMacros().contains(new Macro(var7, var9))) {
                  Xulu.MACRO_MANAGER.addMacro(var7, var9);
               }
            } catch (Exception var10) {
            }
         }

         var4.close();
      } catch (Exception var11) {
         var11.printStackTrace();
         this.saveMacros();
      }

   }

   public void loadDummy() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Dummy.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               if (var8.equalsIgnoreCase("null")) {
                  Xulu.MODULE_MANAGER.getModules().add(new DummyMod(var7));
               } else {
                  Xulu.MODULE_MANAGER.getModules().add(new DummyMod(var7, var8));
               }
            } catch (Exception var9) {
            }
         }

         var4.close();
      } catch (Exception var10) {
         var10.printStackTrace();
         this.saveDummy();
      }

   }

   public void loadDrawn() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "Drawn.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               boolean var9 = Boolean.parseBoolean(var8);
               Module var10 = Xulu.MODULE_MANAGER.getModuleByName(var7);
               var10.setDrawn(var9);
            } catch (Exception var11) {
            }
         }

         var4.close();
      } catch (Exception var12) {
         var12.printStackTrace();
         this.saveDrawn();
      }

   }

   public void loadNewGui() {
      try {
         File var1 = new File(this.Eclient.getAbsolutePath(), "NewGui.txt");
         FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
         DataInputStream var3 = new DataInputStream(var2);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));

         String var5;
         while((var5 = var4.readLine()) != null) {
            try {
               String var6 = var5.trim();
               String var7 = var6.split(":")[0];
               String var8 = var6.split(":")[1];
               String var9 = var6.split(":")[2];
               String var10 = var6.split(":")[3];
               double var11 = Double.parseDouble(var8);
               double var13 = Double.parseDouble(var9);
               boolean var15 = Boolean.parseBoolean(var10);
               Panel var16 = NewGUI.getPanelByName(var7);
               if (var16 != null) {
                  var16.x = var11;
                  var16.y = var13;
                  var16.extended = var15;
               }
            } catch (Exception var17) {
            }
         }

         var4.close();
      } catch (Exception var18) {
         var18.printStackTrace();
         this.saveNewGui();
      }

   }

   public String determineNumber(Object var1) {
      if (var1 instanceof Integer) {
         return "INTEGER";
      } else if (var1 instanceof Float) {
         return "FLOAT";
      } else {
         return var1 instanceof Double ? "DOUBLE" : "INVALID";
      }
   }
}
