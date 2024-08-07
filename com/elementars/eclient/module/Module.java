package com.elementars.eclient.module;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.guirewrite.elements.FeatureList;
import com.elementars.eclient.util.Helper;
import dev.xulu.settings.Bind;
import dev.xulu.settings.Value;
import net.minecraft.client.settings.KeyBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Module implements Helper {
   // $FF: synthetic field
   public final Value bind = this.register(new Value("Bind", this, new Bind(0)));
   // $FF: synthetic field
   private boolean toggled;
   // $FF: synthetic field
   private Category category;
   // $FF: synthetic field
   private String displayName;
   // $FF: synthetic field
   public final Value inAnimation;
   // $FF: synthetic field
   private boolean drawn;
   // $FF: synthetic field
   public KeyBinding keybind;
   // $FF: synthetic field
   public static Module instance;
   // $FF: synthetic field
   private String desc;
   // $FF: synthetic field
   protected final Logger LOGGER = LogManager.getLogger("Xulu");
   // $FF: synthetic field
   private String name;

   public void onRender() {
   }

   public boolean isToggledAnim() {
      return this.toggled || this.inAnimation.getValue() == Module.Animation.DISABLE;
   }

   public String getDisplayName() {
      return this.displayName == null ? this.name : this.displayName;
   }

   public void onToggle() {
   }

   public void setDisplayName(String var1) {
      this.displayName = var1;
   }

   public static Module getModule(Class var0) {
      return Xulu.MODULE_MANAGER.getModule(var0);
   }

   protected void sendRawDebugMessage(String var1) {
      Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append("&b[").append(this.name).append("]:&r ").append(var1)));
   }

   public void setCategory(Category var1) {
      this.category = var1;
   }

   public void toggle() {
      this.toggled = !this.toggled;
      this.onToggle();
      if (this.toggled) {
         if ((Boolean)FeatureList.animation.getValue() && (((String)FeatureList.type.getValue()).equalsIgnoreCase("Enable") || ((String)FeatureList.type.getValue()).equalsIgnoreCase("Both"))) {
            this.inAnimation.setEnumValue("ENABLE");
         }

         this.onEnableR();
      } else {
         if ((Boolean)FeatureList.animation.getValue() && (((String)FeatureList.type.getValue()).equalsIgnoreCase("Disable") || ((String)FeatureList.type.getValue()).equalsIgnoreCase("Both"))) {
            this.inAnimation.setEnumValue("DISABLE");
         }

         this.onDisableR();
      }

   }

   public static Module getModuleT(Class var0) {
      return Xulu.MODULE_MANAGER.getModuleT(var0);
   }

   public String getDesc() {
      return this.desc;
   }

   protected void sendDebugMessage(String var1) {
      Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b[").append(this.name).append("]:&r ").append(var1)));
   }

   public void setKey(int var1) {
      ((Bind)this.bind.getValue()).setNum(var1);
   }

   public String getName() {
      return this.name;
   }

   public Module(String var1, String var2, int var3, Category var4, boolean var5) {
      this.inAnimation = new Value("In Animation", this, Module.Animation.NONE, Module.Animation.values());
      this.name = var1;
      this.desc = var2;
      ((Bind)this.bind.getValue()).setNum(var3);
      this.category = var4;
      this.toggled = false;
      this.drawn = var5;
      this.setup();
      instance = this;
   }

   public void onEnable() {
   }

   public void initToggle(boolean var1) {
      this.toggled = var1;
      this.onToggle();
      if (this.toggled) {
         this.onEnableR();
      } else {
         this.onDisableR();
      }

   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String getHudInfo() {
      return null;
   }

   public void onUpdate() {
   }

   public int getKey() {
      return ((Bind)this.bind.getValue()).getNum();
   }

   public void onDisable() {
   }

   public boolean isDrawn() {
      return this.drawn;
   }

   public void disable() {
      if (this.toggled) {
         this.toggle();
      }

   }

   public void onEnableR() {
      Xulu.EVENT_MANAGER.register(this);
      this.onEnable();
   }

   public Value register(Value var1) {
      Xulu.VALUE_MANAGER.register(var1);
      return var1;
   }

   public Category getCategory() {
      return this.category;
   }

   public void onWorldRender(RenderEvent var1) {
   }

   public void onDisableR() {
      Xulu.EVENT_MANAGER.unregister(this);
      this.onDisable();
   }

   public void setup() {
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public void destroy() {
   }

   public void setDrawn(boolean var1) {
      this.drawn = var1;
   }

   public static enum Animation {
      // $FF: synthetic field
      ENABLE,
      // $FF: synthetic field
      DISABLE,
      // $FF: synthetic field
      NONE;
   }
}
