package com.elementars.eclient.util;

import dev.xulu.settings.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public interface Helper {
   // $FF: synthetic field
   FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
   // $FF: synthetic field
   EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
   // $FF: synthetic field
   World world = Minecraft.getMinecraft().world;
   // $FF: synthetic field
   EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
   // $FF: synthetic field
   Minecraft mc = Minecraft.getMinecraft();
   // $FF: synthetic field
   FileManager fileManager = Wrapper.getFileManager();
}
