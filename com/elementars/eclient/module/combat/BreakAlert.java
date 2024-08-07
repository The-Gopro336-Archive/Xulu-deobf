package com.elementars.eclient.module.combat;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.settings.Value;
import io.netty.util.internal.ConcurrentSet;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

public class BreakAlert extends Module {
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   private ConcurrentSet test;
   // $FF: synthetic field
   private final Value ignoreSelf = this.register(new Value("Ignore Self", this, true));
   // $FF: synthetic field
   public BlockPos[] xd;
   // $FF: synthetic field
   boolean testy;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Chat", new ArrayList(Arrays.asList("Chat", "Text", "Dot"))));
   // $FF: synthetic field
   int delay;
   // $FF: synthetic field
   private ConcurrentSet breaking;
   // $FF: synthetic field
   private final Value color;

   public void onUpdate() {
      if (this.delay > 0) {
         --this.delay;
      }

      this.test.clear();
      if (mc.world != null) {
         mc.world.playerEntities.forEach((var1x) -> {
            RayTraceResult var2;
            if ((Boolean)this.ignoreSelf.getValue()) {
               if (!var1x.getName().equalsIgnoreCase(mc.player.getName()) && var1x.isSwingInProgress && var1x.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
                  var2 = var1x.rayTrace(5.0D, mc.getRenderPartialTicks());
                  if (var2 != null && var2.typeOfHit == Type.BLOCK && mc.world.getBlockState(var2.getBlockPos()).getBlock() != Blocks.BEDROCK) {
                     this.test.add(var2.getBlockPos());
                  }
               }
            } else if (var1x.isSwingInProgress && var1x.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
               var2 = var1x.rayTrace(5.0D, mc.getRenderPartialTicks());
               if (var2 != null && var2.typeOfHit == Type.BLOCK && mc.world.getBlockState(var2.getBlockPos()).getBlock() != Blocks.BEDROCK) {
                  this.test.add(var2.getBlockPos());
               }
            }

         });
         this.breaking.removeIf((var1x) -> {
            return !this.test.contains(var1x);
         });
         this.breaking.addAll(this.test);
         this.testy = false;
         BlockPos[] var1 = this.xd;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            BlockPos var4 = var1[var3];
            BlockPos var5 = (new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).add(var4.x, var4.y, var4.z);
            if (!this.breaking.isEmpty() && this.breaking.contains(var5)) {
               this.testy = true;
            }
         }

         if (this.testy && ((String)this.mode.getValue()).equalsIgnoreCase("Chat")) {
            if (this.delay == 0) {
               if ((Boolean)this.watermark.getValue()) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append("Your feet are being mined!")));
               } else {
                  Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append("Your feet are being mined!")));
               }
            }

            this.delay = 100;
         }

      }
   }

   public BreakAlert() {
      super("BreakAlert", "Alerts you when your feet blocks are being broken", 0, Category.COMBAT, true);
      this.color = this.register(new Value("Color", this, "White", ColorTextUtils.colors));
      this.breaking = new ConcurrentSet();
      this.test = new ConcurrentSet();
      this.xd = new BlockPos[]{new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)};
   }

   public void onRender() {
      if (this.testy && ((String)this.mode.getValue()).equalsIgnoreCase("dot")) {
         GlStateManager.pushMatrix();
         Gui.drawRect(mc.displayWidth / 4 - 3, mc.displayHeight / 4 - 3, mc.displayWidth / 4 + 4, mc.displayHeight / 4 + 4, (new Color(255, 0, 0, 255)).getRGB());
         GlStateManager.popMatrix();
      } else if (this.testy && ((String)this.mode.getValue()).equalsIgnoreCase("Text")) {
         GlStateManager.pushMatrix();
         mc.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append("Your feet are being mined!")), (float)(mc.displayWidth / 4 - mc.fontRenderer.getStringWidth("Your feet are being mined!") / 2), (float)(mc.displayHeight / 4 - mc.fontRenderer.FONT_HEIGHT / 2), ColorUtils.Colors.RED);
         GlStateManager.popMatrix();
      }

   }
}
