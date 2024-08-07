package com.elementars.eclient.module.render;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import dev.xulu.settings.Value;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class ExtraTab extends Module {
   // $FF: synthetic field
   public final Value color;
   // $FF: synthetic field
   public static ExtraTab INSTANCE;
   // $FF: synthetic field
   public final Value ecolor;
   // $FF: synthetic field
   public final Value tabSize = this.register(new Value("Players", this, 80, 1, 1000));

   public static String getPlayerName(NetworkPlayerInfo var0) {
      String var1 = var0.getDisplayName() != null ? var0.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(var0.getPlayerTeam(), var0.getGameProfile().getName());
      if (Friends.isFriend(var1)) {
         return String.format(String.valueOf((new StringBuilder()).append("%s").append(ColorTextUtils.getColor((String)INSTANCE.color.getValue()).substring(1)).append("%s")), Command.SECTIONSIGN(), var1);
      } else {
         return Enemies.isEnemy(var1) ? String.format(String.valueOf((new StringBuilder()).append("%s").append(ColorTextUtils.getColor((String)INSTANCE.ecolor.getValue()).substring(1)).append("%s")), Command.SECTIONSIGN(), var1) : var1;
      }
   }

   public ExtraTab() {
      super("ExtraTab", "Expands tab menu", 0, Category.RENDER, true);
      this.color = this.register(new Value("Friend Color", this, "LightGreen", ColorTextUtils.colors));
      this.ecolor = this.register(new Value("Enemy Color", this, "LightRed", ColorTextUtils.colors));
      INSTANCE = this;
   }
}
