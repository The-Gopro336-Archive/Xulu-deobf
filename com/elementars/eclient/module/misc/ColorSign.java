package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.GuiScreenEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;

public class ColorSign extends Module {
   @EventTarget
   private void onGui(GuiScreenEvent.Displayed var1) {
      if (var1.getScreen() instanceof GuiEditSign && this.isToggled()) {
         var1.setScreen(new ColorSign.KamiGuiEditSign(((GuiEditSign)var1.getScreen()).tileSign));
      }

   }

   public ColorSign() {
      super("ColorSign", "Allows writing with colors on signs", 0, Category.MISC, true);
   }

   private class KamiGuiEditSign extends GuiEditSign {
      public KamiGuiEditSign(TileEntitySign var2) {
         super(var2);
      }

      protected void keyTyped(char var1, int var2) throws IOException {
         super.keyTyped(var1, var2);
         String var3 = ((TextComponentString)this.tileSign.signText[this.editLine]).getText();
         var3 = var3.replace("&", String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("")));
         this.tileSign.signText[this.editLine] = new TextComponentString(var3);
      }

      public void initGui() {
         super.initGui();
      }

      protected void actionPerformed(GuiButton var1) throws IOException {
         if (var1.id == 0) {
            this.tileSign.signText[this.editLine] = new TextComponentString(this.tileSign.signText[this.editLine].getFormattedText().replaceAll(String.valueOf((new StringBuilder()).append("(").append(Command.SECTIONSIGN()).append(")(.)")), "$1$1$2$2"));
         }

         super.actionPerformed(var1);
      }
   }
}
