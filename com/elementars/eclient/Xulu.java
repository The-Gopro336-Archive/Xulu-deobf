package com.elementars.eclient;

import com.elementars.eclient.cape.Capes;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.command.CommandManager;
import com.elementars.eclient.event.EventManager;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.AnnouncerRegistry;
import com.elementars.eclient.event.events.EventKey;
import com.elementars.eclient.event.events.KeyRegistry;
import com.elementars.eclient.font.CFontManager;
import com.elementars.eclient.font.FontInit;
import com.elementars.eclient.font.XFontRenderer;
import com.elementars.eclient.font.custom.CustomFont;
import com.elementars.eclient.font.rainbow.RainbowCycle;
import com.elementars.eclient.guirewrite.HUD;
import com.elementars.eclient.macro.MacroManager;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ConnectionUtil;
import com.elementars.eclient.util.GLSLSandboxShader;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.LagCompensator;
import com.elementars.eclient.util.LagUtil;
import com.elementars.eclient.util.TaskScheduler;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.newgui.NewGUI;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.ValueManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;

@Mod(
   modid = "eclient",
   name = "Xulu",
   version = "v1.5.2"
)
public class Xulu implements Helper {
   // $FF: synthetic field
   public static GLSLSandboxShader backgroundShader;
   // $FF: synthetic field
   public static final String name = "Xulu";
   // $FF: synthetic field
   public static final EventManager EVENT_MANAGER = new EventManager();
   // $FF: synthetic field
   private int yCount;
   // $FF: synthetic field
   public static final String creator = "Elementars";
   // $FF: synthetic field
   public static long initTime;
   // $FF: synthetic field
   public static final String id = "eclient";
   // $FF: synthetic field
   public static int rgb;
   // $FF: synthetic field
   public static boolean CustomFont;
   // $FF: synthetic field
   public static DecimalFormat df = new DecimalFormat("##,###,###,###,##0.00");
   // $FF: synthetic field
   public static final MacroManager MACRO_MANAGER = new MacroManager();
   // $FF: synthetic field
   public static final String version = "v1.5.2";
   // $FF: synthetic field
   private int endY;
   // $FF: synthetic field
   private DiscordRP discordRP = new DiscordRP();
   // $FF: synthetic field
   public static final TaskScheduler TASK_SCHEDULER = new TaskScheduler();
   // $FF: synthetic field
   public static RainbowCycle rainbowCycle = new RainbowCycle();
   // $FF: synthetic field
   public static CFontManager cFontRenderer;
   // $FF: synthetic field
   @Instance
   public static Xulu INSTANCE;
   // $FF: synthetic field
   public static HUD hud;
   // $FF: synthetic field
   private boolean shownLag = false;
   // $FF: synthetic field
   public static final ModuleManager MODULE_MANAGER = new ModuleManager();
   // $FF: synthetic field
   public Minecraft mc = Minecraft.getMinecraft();
   // $FF: synthetic field
   private int beginY;
   // $FF: synthetic field
   public static final CommandManager COMMAND_MANAGER = new CommandManager();
   // $FF: synthetic field
   public static NewGUI newGUI;
   // $FF: synthetic field
   public static final ValueManager VALUE_MANAGER = new ValueManager();

   public static void setXdolfFontRendererDefault() {
      try {
         CFontManager.xFontRenderer = new XFontRenderer(new Font("Verdana", 0, 36), true, 8);
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static void load() {
      fileManager.loadHacks();
      fileManager.loadBinds();
      fileManager.loadDrawn();
      fileManager.loadSettingsList();
      fileManager.loadMacros();
      fileManager.loadPrefix();
      fileManager.loadNewGui();
      fileManager.loadFriends();
      fileManager.loadEnemies();
      fileManager.loadHUD();
      fileManager.loadFont();
      fileManager.loadStickyNote();
      fileManager.loadWelcomeMessage();
      fileManager.loadDummy();
      fileManager.loadXray();
      fileManager.loadSearch();
      fileManager.loadWaypoints();
      fileManager.loadNicks();
      System.out.println("Xulu Loaded!");
   }

   public static void setXdolfFontRenderer(String var0, int var1) {
      try {
         if (getCorrectFont(var0) == null && !var0.equalsIgnoreCase("Xulu")) {
            Command.sendChatMessage("Invalid font!");
            return;
         }

         CFontManager.xFontRenderer = new XFontRenderer(new Font(getCorrectFont(var0), 0, var1), true, 8);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private String getFacing(String var1) {
      String var2 = getTitle(var1);
      String var3;
      if (var1.equalsIgnoreCase("North")) {
         var3 = " §7[§r-Z§7]";
      } else if (var1.equalsIgnoreCase("East")) {
         var3 = " §7[§r+X§7]";
      } else if (var1.equalsIgnoreCase("South")) {
         var3 = " §7[§r+Z§7]";
      } else if (var1.equalsIgnoreCase("West")) {
         var3 = " §7[§r-X§7]";
      } else {
         var3 = " ERROR";
      }

      return String.valueOf((new StringBuilder()).append(var2).append(var3));
   }

   public static void setCFontRenderer(String var0, int var1) {
      try {
         if (getCorrectFont(var0) == null) {
            Command.sendChatMessage("Invalid font!");
            return;
         }

         if (var0.equalsIgnoreCase("Comfortaa Regular")) {
            CFontManager.customFont = new CustomFont(new Font("Comfortaa Regular", 0, var1), true, false);
            return;
         }

         CFontManager.customFont = new CustomFont(new Font(getCorrectFont(var0), 0, var1), true, false);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   @EventTarget
   public void onKey(EventKey var1) {
      MACRO_MANAGER.runMacros(var1.getKey());
      MODULE_MANAGER.getModules().stream().filter((var1x) -> {
         return var1x.getKey() == var1.getKey();
      }).forEach(Module::toggle);
   }

   public static String getTitle(String var0) {
      var0 = String.valueOf((new StringBuilder()).append(Character.toUpperCase(var0.toLowerCase().charAt(0))).append(var0.toLowerCase().substring(1)));
      return var0;
   }

   @SubscribeEvent
   public void onRenderGui(Post var1) {
      CustomFont = MODULE_MANAGER.getModule(com.elementars.eclient.module.core.CustomFont.class).isToggled();
      if ((float)this.beginY != (CustomFont ? -cFontRenderer.getHeight() : (float)(-fontRenderer.FONT_HEIGHT))) {
         this.beginY = CustomFont ? (int)(-cFontRenderer.getHeight()) : -fontRenderer.FONT_HEIGHT;
      }

      if ((float)this.endY != 3.0F) {
         this.endY = 3;
      }

      ScaledResolution var2 = new ScaledResolution(this.mc);
      Xulu.Rainbow.updateRainbow();
      rgb = Xulu.Rainbow.rgb;
      if (var1.getType() == ElementType.HOTBAR) {
         if (!(Boolean)VALUE_MANAGER.getValueByName("Rainbow Watermark").getValue()) {
            rgb = ColorUtil.getClickGUIColor().getRGB();
         }

         String var3 = this.mc.player.getName();
         int var4 = var2.getScaledHeight() - 3;
         String var5;
         if ((Boolean)Global.coordinates.getValue()) {
            var5 = String.valueOf((new StringBuilder()).append(df.format(this.mc.player.posX)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posY)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posZ)).append(ChatFormatting.GRAY).append(" [").append(ChatFormatting.RESET).append(this.mc.player.dimension == -1 ? String.valueOf((new StringBuilder()).append(df.format(this.mc.player.posX * 8.0D)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posY)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posZ * 8.0D))) : String.valueOf((new StringBuilder()).append(df.format(this.mc.player.posX / 8.0D)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posY)).append(ChatFormatting.GRAY).append(", ").append(ChatFormatting.RESET).append(df.format(this.mc.player.posZ / 8.0D)))).append(ChatFormatting.GRAY).append("]"));
            if (CustomFont) {
               cFontRenderer.drawStringWithShadow(var5, 3.0D, (double)((float)var4 - cFontRenderer.getHeight() - 1.0F - (float)(this.mc.ingameGUI.getChatGUI().getChatOpen() ? 11 : 0)), ColorUtils.changeAlpha(Color.white.getRGB(), (Integer)Global.hudAlpha.getValue()));
            } else {
               fontRenderer.drawStringWithShadow(var5, 3.0F, (float)(var4 - fontRenderer.FONT_HEIGHT - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 11 : 0)), ColorUtils.changeAlpha(Color.white.getRGB(), (Integer)Global.hudAlpha.getValue()));
            }
         }

         if ((Boolean)Global.direction.getValue()) {
            if (CustomFont) {
               cFontRenderer.drawStringWithShadow(this.getFacing(this.mc.player.getHorizontalFacing().getName().toUpperCase()), 3.0D, (double)((float)var4 - cFontRenderer.getHeight() - (float)((Boolean)Global.coordinates.getValue() ? 11 : 1) - (float)(this.mc.ingameGUI.getChatGUI().getChatOpen() ? 11 : 0)), ColorUtils.changeAlpha(Color.white.getRGB(), (Integer)Global.hudAlpha.getValue()));
            } else {
               fontRenderer.drawStringWithShadow(this.getFacing(this.mc.player.getHorizontalFacing().getName().toUpperCase()), 3.0F, (float)(var4 - fontRenderer.FONT_HEIGHT - ((Boolean)Global.coordinates.getValue() ? 10 : 0) - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 11 : 0)), ColorUtils.changeAlpha(Color.white.getRGB(), (Integer)Global.hudAlpha.getValue()));
            }
         }

         if ((Boolean)Global.showLag.getValue() && !this.mc.isIntegratedServerRunning()) {
            if (LagUtil.INSTANCE.getLastTimeDiff() != 0L && LagUtil.INSTANCE.getLastTimeDiff() > 5000L) {
               var5 = String.format("Server has been lagging for %01.1fs", (float)(LagUtil.INSTANCE.getLastTimeDiff() - 1000L) / 1000.0F);
               if (!this.shownLag) {
                  this.yCount = this.beginY;
               }

               if (CustomFont) {
                  cFontRenderer.drawStringWithShadow(var5, (double)(var2.getScaledWidth() / 2 - cFontRenderer.getStringWidth(var5) / 2), (double)this.yCount, ((String)Global.lagColor.getValue()).equalsIgnoreCase("Default") ? Color.LIGHT_GRAY.getRGB() : ColorUtil.getClickGUIColor().getRGB());
               } else {
                  fontRenderer.drawStringWithShadow(var5, (float)(var2.getScaledWidth() / 2 - fontRenderer.getStringWidth(var5) / 2), (float)this.yCount, ((String)Global.lagColor.getValue()).equalsIgnoreCase("Default") ? Color.LIGHT_GRAY.getRGB() : ColorUtil.getClickGUIColor().getRGB());
               }

               this.shownLag = true;
               if (this.yCount != this.endY) {
                  ++this.yCount;
               }
            } else if (this.shownLag) {
               var5 = "Server has been lagging for 0.0s";
               if (CustomFont) {
                  cFontRenderer.drawStringWithShadow(var5, (double)(var2.getScaledWidth() / 2 - cFontRenderer.getStringWidth(var5) / 2), (double)this.yCount, ((String)Global.lagColor.getValue()).equalsIgnoreCase("Default") ? Color.LIGHT_GRAY.getRGB() : ColorUtil.getClickGUIColor().getRGB());
               } else {
                  fontRenderer.drawStringWithShadow(var5, (float)(var2.getScaledWidth() / 2 - fontRenderer.getStringWidth(var5) / 2), (float)this.yCount, ((String)Global.lagColor.getValue()).equalsIgnoreCase("Default") ? Color.LIGHT_GRAY.getRGB() : ColorUtil.getClickGUIColor().getRGB());
               }

               if (this.yCount != this.beginY) {
                  --this.yCount;
               } else {
                  this.shownLag = false;
               }
            }
         }

      }
   }

   public static String getCorrectFont(String var0) {
      String[] var1 = getFonts();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1[var3];
         if (var4.equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      return null;
   }

   public void stopClient() {
      save();
      MODULE_MANAGER.getModules().forEach(Module::destroy);
      EVENT_MANAGER.unregister(this);
   }

   public static void setcFontRendererDefault() {
      try {
         CFontManager.customFont = new CustomFont(new Font("Verdana", 0, 18), true, false);
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static String[] getFonts() {
      return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
   }

   public static void save() {
      fileManager.saveHacks();
      fileManager.saveBinds();
      fileManager.saveDrawn();
      fileManager.saveSettingsList();
      fileManager.saveMacros();
      fileManager.savePrefix();
      fileManager.saveNewGui();
      fileManager.saveFriends();
      fileManager.saveEnemies();
      fileManager.saveHUD();
      fileManager.saveFont();
      fileManager.saveStickyNote();
      fileManager.saveWelcomeMessage();
      fileManager.saveDummy();
      fileManager.saveXray();
      fileManager.saveSearch();
      fileManager.saveWaypoints();
      fileManager.saveNicks();
      System.out.println("Xulu Saved!");
   }

   @EventHandler
   public void init(FMLInitializationEvent var1) {
      System.out.println("[Xulu] Starting client, v1.5.2, created by Elementars");
      Display.setTitle("Xulu v1.5.2");
      this.discordRP.start();
      ColorTextUtils.initColors();
      Capes.getUsersCape();
      FontInit var2 = new FontInit();
      var2.initFonts();
      MODULE_MANAGER.init();
      COMMAND_MANAGER.init();
      cFontRenderer = new CFontManager();
      fileManager.loadDummy();
      newGUI = new NewGUI();
      hud = new HUD();
      HUD.registerElements();
      hud.refreshPanel();
      LagCompensator.INSTANCE = new LagCompensator();
      LagUtil.INSTANCE = new LagUtil();
      ConnectionUtil.INSTANCE = new ConnectionUtil();
      fileManager.loadHacks();
      fileManager.loadDrawn();
      fileManager.loadSettingsList();
      fileManager.loadBinds();
      fileManager.loadMacros();
      fileManager.loadPrefix();
      fileManager.loadNewGui();
      fileManager.loadFriends();
      fileManager.loadEnemies();
      fileManager.loadHUD();
      fileManager.loadFont();
      fileManager.loadStickyNote();
      fileManager.loadWelcomeMessage();
      fileManager.loadXray();
      fileManager.loadSearch();
      fileManager.loadWaypoints();
      fileManager.loadNicks();
      EVENT_BUS.register(new KeyRegistry());
      EVENT_BUS.register(this);
      AnnouncerRegistry.initAnnouncer();
      EVENT_MANAGER.register(this);
      rgb = Color.HSBtoRGB(0.01F, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
   }

   public static class Rainbow {
      // $FF: synthetic field
      public static int g;
      // $FF: synthetic field
      public static int b;
      // $FF: synthetic field
      public static int r;
      // $FF: synthetic field
      private static int rgb;
      // $FF: synthetic field
      public static int a;
      // $FF: synthetic field
      static float hue = 0.01F;

      public static void updateRainbow() {
         rgb = Color.HSBtoRGB(hue, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
         a = rgb >>> 24 & 255;
         r = rgb >>> 16 & 255;
         g = rgb >>> 8 & 255;
         b = rgb & 255;
         hue += (float)(Integer)Global.rainbowspeed2.getValue() / 100000.0F;
         if (hue > 1.0F) {
            --hue;
         }

      }
   }
}
