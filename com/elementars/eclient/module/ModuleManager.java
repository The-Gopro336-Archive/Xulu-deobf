package com.elementars.eclient.module;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.guirewrite.HudEditor;
import com.elementars.eclient.guirewrite.elements.Armor;
import com.elementars.eclient.guirewrite.elements.CraftingPreview;
import com.elementars.eclient.guirewrite.elements.Crystals;
import com.elementars.eclient.guirewrite.elements.Exp;
import com.elementars.eclient.guirewrite.elements.FeatureList;
import com.elementars.eclient.guirewrite.elements.Gapples;
import com.elementars.eclient.guirewrite.elements.GodInfo;
import com.elementars.eclient.guirewrite.elements.HoleHud;
import com.elementars.eclient.guirewrite.elements.Info;
import com.elementars.eclient.guirewrite.elements.InvPreview;
import com.elementars.eclient.guirewrite.elements.Logo;
import com.elementars.eclient.guirewrite.elements.Obsidian;
import com.elementars.eclient.guirewrite.elements.OldName;
import com.elementars.eclient.guirewrite.elements.Player;
import com.elementars.eclient.guirewrite.elements.Potions;
import com.elementars.eclient.guirewrite.elements.PvPInfo;
import com.elementars.eclient.guirewrite.elements.StickyNotes;
import com.elementars.eclient.guirewrite.elements.Target;
import com.elementars.eclient.guirewrite.elements.TextRadar;
import com.elementars.eclient.guirewrite.elements.TheGoons;
import com.elementars.eclient.guirewrite.elements.Totems;
import com.elementars.eclient.guirewrite.elements.Watermark;
import com.elementars.eclient.guirewrite.elements.Welcome;
import com.elementars.eclient.module.combat.AntiChainPop;
import com.elementars.eclient.module.combat.Aura;
import com.elementars.eclient.module.combat.AutoArmor;
import com.elementars.eclient.module.combat.AutoCrystal;
import com.elementars.eclient.module.combat.AutoEz;
import com.elementars.eclient.module.combat.AutoFeetBlock;
import com.elementars.eclient.module.combat.AutoRepair;
import com.elementars.eclient.module.combat.AutoTotem;
import com.elementars.eclient.module.combat.AutoTrap;
import com.elementars.eclient.module.combat.AutoWeb;
import com.elementars.eclient.module.combat.BreakAlert;
import com.elementars.eclient.module.combat.CityBlocker;
import com.elementars.eclient.module.combat.Criticals;
import com.elementars.eclient.module.combat.DurabilityAlert;
import com.elementars.eclient.module.combat.EXPFast;
import com.elementars.eclient.module.combat.FastBow;
import com.elementars.eclient.module.combat.FuckedDetect;
import com.elementars.eclient.module.combat.HoleBlocker;
import com.elementars.eclient.module.combat.HoleFill;
import com.elementars.eclient.module.combat.MiddleClickPearl;
import com.elementars.eclient.module.combat.Offhand;
import com.elementars.eclient.module.combat.PearlAlert;
import com.elementars.eclient.module.combat.PopCounter;
import com.elementars.eclient.module.combat.SelfWeb;
import com.elementars.eclient.module.combat.Sharp32kDetect;
import com.elementars.eclient.module.combat.StrengthDetect;
import com.elementars.eclient.module.combat.Surround;
import com.elementars.eclient.module.core.CustomFont;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.module.core.TitleScreenShader;
import com.elementars.eclient.module.misc.Announcer;
import com.elementars.eclient.module.misc.AntiDeathScreen;
import com.elementars.eclient.module.misc.AntiSound;
import com.elementars.eclient.module.misc.AntiSpam;
import com.elementars.eclient.module.misc.Australia;
import com.elementars.eclient.module.misc.AutoQMain;
import com.elementars.eclient.module.misc.AutoWither;
import com.elementars.eclient.module.misc.Avoid;
import com.elementars.eclient.module.misc.CameraClip;
import com.elementars.eclient.module.misc.ColorSign;
import com.elementars.eclient.module.misc.CoordLogger;
import com.elementars.eclient.module.misc.CrashExploit;
import com.elementars.eclient.module.misc.CustomChat;
import com.elementars.eclient.module.misc.DonkeyAlert;
import com.elementars.eclient.module.misc.FakePlayer;
import com.elementars.eclient.module.misc.FakeVanilla;
import com.elementars.eclient.module.misc.FovSlider;
import com.elementars.eclient.module.misc.HopperNuker;
import com.elementars.eclient.module.misc.LiquidInteract;
import com.elementars.eclient.module.misc.MCF;
import com.elementars.eclient.module.misc.MobOwner;
import com.elementars.eclient.module.misc.NoEntityTrace;
import com.elementars.eclient.module.misc.NoPacketKick;
import com.elementars.eclient.module.misc.PortalChat;
import com.elementars.eclient.module.misc.SkinFlicker;
import com.elementars.eclient.module.misc.Time;
import com.elementars.eclient.module.misc.Timer;
import com.elementars.eclient.module.misc.VisualRange;
import com.elementars.eclient.module.movement.ElytraFly;
import com.elementars.eclient.module.movement.Flight;
import com.elementars.eclient.module.movement.ForgeFly;
import com.elementars.eclient.module.movement.GuiMove;
import com.elementars.eclient.module.movement.HoleTP;
import com.elementars.eclient.module.movement.Jesus;
import com.elementars.eclient.module.movement.LongJump;
import com.elementars.eclient.module.movement.NoSlowDown;
import com.elementars.eclient.module.movement.Sprint;
import com.elementars.eclient.module.movement.Step;
import com.elementars.eclient.module.movement.Strafe;
import com.elementars.eclient.module.movement.Velocity;
import com.elementars.eclient.module.player.AntiVoid;
import com.elementars.eclient.module.player.AutoReplenish;
import com.elementars.eclient.module.player.AutoWalk;
import com.elementars.eclient.module.player.Blink;
import com.elementars.eclient.module.player.FastFall;
import com.elementars.eclient.module.player.Freecam;
import com.elementars.eclient.module.player.ItemSpoof;
import com.elementars.eclient.module.player.Multitask;
import com.elementars.eclient.module.player.NoBreakAnimation;
import com.elementars.eclient.module.player.PacketSwing;
import com.elementars.eclient.module.player.SelfLogoutSpot;
import com.elementars.eclient.module.player.Speedmine;
import com.elementars.eclient.module.player.TpsSync;
import com.elementars.eclient.module.player.XCarry;
import com.elementars.eclient.module.render.AntiFog;
import com.elementars.eclient.module.render.Arrows;
import com.elementars.eclient.module.render.BlockHighlight;
import com.elementars.eclient.module.render.BossStack;
import com.elementars.eclient.module.render.BreakESP;
import com.elementars.eclient.module.render.Cape;
import com.elementars.eclient.module.render.Chams;
import com.elementars.eclient.module.render.Chat;
import com.elementars.eclient.module.render.ChunkFinder;
import com.elementars.eclient.module.render.Compass;
import com.elementars.eclient.module.render.ESP;
import com.elementars.eclient.module.render.EnchantColor;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.module.render.ExtraTab;
import com.elementars.eclient.module.render.FullBright;
import com.elementars.eclient.module.render.HellenKeller;
import com.elementars.eclient.module.render.HoleESP;
import com.elementars.eclient.module.render.ImageESP;
import com.elementars.eclient.module.render.ItemESP;
import com.elementars.eclient.module.render.LogoutSpots;
import com.elementars.eclient.module.render.Nametags;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.module.render.NoHurtCam;
import com.elementars.eclient.module.render.NoRender;
import com.elementars.eclient.module.render.OffhandSwing;
import com.elementars.eclient.module.render.OutlineESP;
import com.elementars.eclient.module.render.Pathfind;
import com.elementars.eclient.module.render.Search;
import com.elementars.eclient.module.render.SecretShaders;
import com.elementars.eclient.module.render.ShulkerPreview;
import com.elementars.eclient.module.render.Skeleton;
import com.elementars.eclient.module.render.StorageESP;
import com.elementars.eclient.module.render.ToolTips;
import com.elementars.eclient.module.render.Tracers;
import com.elementars.eclient.module.render.Trajectories;
import com.elementars.eclient.module.render.ViewmodelChanger;
import com.elementars.eclient.module.render.VoidESP;
import com.elementars.eclient.module.render.Waypoints;
import com.elementars.eclient.module.render.Xray;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import java.util.ArrayList;
import me.memeszz.aurora.module.modules.movement.FastSwim;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ModuleManager {
   // $FF: synthetic field
   private static ArrayList modules = new ArrayList();

   public static void onUpdate() {
      Xulu.MODULE_MANAGER.getModules().stream().filter(Module::isToggled).forEach(Module::onUpdate);
      NewGui.resetGui();
      Xulu.TASK_SCHEDULER.onUpdate();
   }

   public Module getModuleT(Class var1) {
      return (Module)modules.stream().filter((var1x) -> {
         return var1x.getClass() == var1;
      }).map((var0) -> {
         return var0;
      }).findFirst().orElse((Object)null);
   }

   public void init() {
      modules.add(new Global());
      modules.add(new CustomFont());
      modules.add(new TitleScreenShader());
      modules.add(new AntiChainPop());
      modules.add(new Aura());
      modules.add(new AutoArmor());
      modules.add(new AutoCrystal());
      modules.add(new AutoEz());
      modules.add(new AutoFeetBlock());
      modules.add(new AutoRepair());
      modules.add(new AutoTotem());
      modules.add(new AutoTrap());
      modules.add(new AutoWeb());
      modules.add(new BreakAlert());
      modules.add(new CityBlocker());
      modules.add(new Criticals());
      modules.add(new DurabilityAlert());
      modules.add(new EXPFast());
      modules.add(new FastBow());
      modules.add(new FuckedDetect());
      modules.add(new HoleBlocker());
      modules.add(new HoleFill());
      modules.add(new MiddleClickPearl());
      modules.add(new Offhand());
      modules.add(new PearlAlert());
      modules.add(new PopCounter());
      modules.add(new SelfWeb());
      modules.add(new Sharp32kDetect());
      modules.add(new StrengthDetect());
      modules.add(new Surround());
      modules.add(new Announcer());
      modules.add(new AntiDeathScreen());
      modules.add(new AntiSound());
      modules.add(new AntiSpam());
      modules.add(new Australia());
      modules.add(new AutoQMain());
      modules.add(new AutoWither());
      modules.add(new Avoid());
      modules.add(new CameraClip());
      modules.add(new ColorSign());
      modules.add(new CoordLogger());
      modules.add(new CrashExploit());
      modules.add(new CustomChat());
      modules.add(new DonkeyAlert());
      modules.add(new FakePlayer());
      modules.add(new FakeVanilla());
      modules.add(new FovSlider());
      modules.add(new HopperNuker());
      modules.add(new LiquidInteract());
      modules.add(new MCF());
      modules.add(new MobOwner());
      modules.add(new NoEntityTrace());
      modules.add(new NoPacketKick());
      modules.add(new PortalChat());
      modules.add(new SkinFlicker());
      modules.add(new Time());
      modules.add(new Timer());
      modules.add(new VisualRange());
      modules.add(new ElytraFly());
      modules.add(new FastSwim());
      modules.add(new Flight());
      modules.add(new ForgeFly());
      modules.add(new GuiMove());
      modules.add(new HoleTP());
      modules.add(new Jesus());
      modules.add(new LongJump());
      modules.add(new NoSlowDown());
      modules.add(new Sprint());
      modules.add(new Step());
      modules.add(new Strafe());
      modules.add(new Velocity());
      modules.add(new AntiVoid());
      modules.add(new AutoReplenish());
      modules.add(new AutoWalk());
      modules.add(new Blink());
      modules.add(new FastFall());
      modules.add(new Freecam());
      modules.add(new ItemSpoof());
      modules.add(new Multitask());
      modules.add(new NoBreakAnimation());
      modules.add(new PacketSwing());
      modules.add(new SelfLogoutSpot());
      modules.add(new Speedmine());
      modules.add(new TpsSync());
      modules.add(new XCarry());
      modules.add(new AntiFog());
      modules.add(new Arrows());
      modules.add(new BlockHighlight());
      modules.add(new BossStack());
      modules.add(new BreakESP());
      modules.add(new Cape());
      modules.add(new Chams());
      modules.add(new Chat());
      modules.add(new ChunkFinder());
      modules.add(new Compass());
      modules.add(new EnchantColor());
      modules.add(new NewGui());
      modules.add(new ESP());
      modules.add(new ExeterGui());
      modules.add(new ExtraTab());
      modules.add(new FullBright());
      modules.add(new HellenKeller());
      modules.add(new HoleESP());
      modules.add(new ImageESP());
      modules.add(new ItemESP());
      modules.add(new LogoutSpots());
      modules.add(new Nametags());
      modules.add(new NoHurtCam());
      modules.add(new NoRender());
      modules.add(new OffhandSwing());
      modules.add(new OutlineESP());
      modules.add(new Pathfind());
      modules.add(new Search());
      modules.add(new SecretShaders());
      modules.add(new PvPInfo());
      modules.add(new ShulkerPreview());
      modules.add(new Skeleton());
      modules.add(new StorageESP());
      modules.add(new ToolTips());
      modules.add(new Tracers());
      modules.add(new Trajectories());
      modules.add(new ViewmodelChanger());
      modules.add(new VoidESP());
      modules.add(new Waypoints());
      modules.add(new Xray());
      modules.add(new HudEditor());
      modules.add(new Totems());
      modules.add(new Obsidian());
      modules.add(new Crystals());
      modules.add(new Gapples());
      modules.add(new Exp());
      modules.add(new InvPreview());
      modules.add(new CraftingPreview());
      modules.add(new TextRadar());
      modules.add(new FeatureList());
      modules.add(new Player());
      modules.add(new Welcome());
      modules.add(new OldName());
      modules.add(new TheGoons());
      modules.add(new Potions());
      modules.add(new StickyNotes());
      modules.add(new HoleHud());
      modules.add(new Info());
      modules.add(new Armor());
      modules.add(new GodInfo());
      modules.add(new Watermark());
      modules.add(new Logo());
      modules.add(new Target());
   }

   public ArrayList getModules() {
      return modules;
   }

   /** @deprecated */
   @Deprecated
   public Module getModuleByName(String var1) {
      return (Module)modules.stream().filter((var1x) -> {
         return var1x.getName().equalsIgnoreCase(var1);
      }).findFirst().orElse((Object)null);
   }

   public static boolean isModuleEnabled(String var0) {
      Module var1 = (Module)modules.stream().filter((var1x) -> {
         return var1x.getName().equalsIgnoreCase(var0);
      }).findFirst().orElse((Object)null);
      return var1 != null ? var1.isToggled() : false;
   }

   public static void onWorldRender(RenderWorldLastEvent var0) {
      Minecraft.getMinecraft().profiler.startSection("eclient");
      Minecraft.getMinecraft().profiler.startSection("setup");
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      GlStateManager.disableDepth();
      GlStateManager.glLineWidth(1.0F);
      Vec3d var1 = EntityUtil.getInterpolatedPos(Wrapper.getPlayer(), var0.getPartialTicks());
      RenderEvent var2 = new RenderEvent(XuluTessellator.INSTANCE, var1);
      var2.resetTranslation();
      Minecraft.getMinecraft().profiler.endSection();
      Xulu.MODULE_MANAGER.getModules().stream().filter(Module::isToggled).forEach((var1x) -> {
         Minecraft.getMinecraft().profiler.startSection(var1x.getName());
         var1x.onWorldRender(var2);
         Minecraft.getMinecraft().profiler.endSection();
      });
      Minecraft.getMinecraft().profiler.startSection("release");
      GlStateManager.glLineWidth(1.0F);
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableDepth();
      GlStateManager.enableCull();
      XuluTessellator.releaseGL();
      Minecraft.getMinecraft().profiler.endSection();
      Minecraft.getMinecraft().profiler.endSection();
   }

   public static void onRender() {
      Xulu.MODULE_MANAGER.getModules().stream().filter(Module::isToggled).forEach(Module::onRender);
   }

   public Module getModule(Class var1) {
      return (Module)modules.stream().filter((var1x) -> {
         return var1x.getClass() == var1;
      }).findFirst().orElse((Object)null);
   }
}
