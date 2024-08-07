package com.elementars.eclient.module.combat;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagList;

public class Sharp32kDetect extends Module {
   // $FF: synthetic field
   private final Value color = this.register(new Value("Color", this, false));
   // $FF: synthetic field
   private Set sword = Collections.newSetFromMap(new WeakHashMap());
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   public static final Minecraft mc = Minecraft.getMinecraft();

   public Sharp32kDetect() {
      super("32kDetect", "Detects held 32ks", 0, Category.COMBAT, true);
   }

   public void onUpdate() {
      Iterator var1 = mc.world.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (!var2.equals(mc.player)) {
            if (this.is32k(var2, var2.itemStackMainHand) && !this.sword.contains(var2)) {
               if ((Boolean)this.watermark.getValue()) {
                  if ((Boolean)this.color.getValue()) {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&4").append(var2.getDisplayNameString()).append(" is holding a 32k")));
                  } else {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayNameString()).append(" is holding a 32k")));
                  }
               } else if ((Boolean)this.color.getValue()) {
                  Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append("&4").append(var2.getDisplayNameString()).append(" is holding a 32k")));
               } else {
                  Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayNameString()).append(" is holding a 32k")));
               }

               this.sword.add(var2);
            }

            if (this.sword.contains(var2) && !this.is32k(var2, var2.itemStackMainHand)) {
               if ((Boolean)this.watermark.getValue()) {
                  if ((Boolean)this.color.getValue()) {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&2").append(var2.getDisplayNameString()).append(" is no longer holding a 32k")));
                  } else {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayNameString()).append(" is no longer holding a 32k")));
                  }
               } else if ((Boolean)this.color.getValue()) {
                  Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append("&2").append(var2.getDisplayNameString()).append(" is no longer holding a 32k")));
               } else {
                  Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayNameString()).append(" is no longer holding a 32k")));
               }

               this.sword.remove(var2);
            }
         }
      }

   }

   private boolean is32k(EntityPlayer var1, ItemStack var2) {
      if (var2.getItem() instanceof ItemSword) {
         NBTTagList var3 = var2.getEnchantmentTagList();
         if (var3 != null) {
            for(int var4 = 0; var4 < var3.tagCount(); ++var4) {
               if (var3.getCompoundTagAt(var4).getShort("lvl") >= 32767) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
