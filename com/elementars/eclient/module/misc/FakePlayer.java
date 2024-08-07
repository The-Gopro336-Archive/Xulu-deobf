package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Module {
   public void onDisable() {
      mc.world.removeEntityFromWorld(-100);
   }

   public FakePlayer() {
      super("FakePlayer", "Spawns a fake player", 0, Category.MISC, true);
   }

   public void onEnable() {
      if (mc.world != null) {
         EntityOtherPlayerMP var1 = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "jared2013"));
         var1.copyLocationAndAnglesFrom(mc.player);
         var1.rotationYawHead = mc.player.rotationYawHead;
         mc.world.addEntityToWorld(-100, var1);
      }
   }
}
