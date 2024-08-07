package com.elementars.eclient.module.render;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.ChunkEvent;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.event.events.UnloadChunkEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Helper;
import dev.xulu.settings.Value;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.opengl.GL11;

public class ChunkFinder extends Module {
   // $FF: synthetic field
   private ChunkFinder.LastSetting lastSetting = new ChunkFinder.LastSetting();
   // $FF: synthetic field
   private final Value relative = this.register(new Value("Relative", this, true));
   // $FF: synthetic field
   private PrintWriter logWriter;
   // $FF: synthetic field
   private int list = GL11.glGenLists(1);
   // $FF: synthetic field
   private final Value saveNewChunks = this.register(new Value("Save New Chunks", this, false));
   // $FF: synthetic field
   private final Value alsoSaveNormalCoords = this.register(new Value("Save Normal Coords", this, false));
   // $FF: synthetic field
   private final Value saveInRegionFolder = this.register(new Value("In Region", this, false));
   // $FF: synthetic field
   private final Value saveOption = this.register(new Value("Save Option", this, "extraFolder", new ArrayList(Arrays.asList("extraFolder", "liteLoaderWdl", "nhackWdl"))));
   // $FF: synthetic field
   static ArrayList chunks = new ArrayList();
   // $FF: synthetic field
   private static boolean dirty = true;
   // $FF: synthetic field
   private final Value yOffset = this.register(new Value("Y Offset", this, 0, 0, 256));

   @EventTarget
   public void onChunk(ChunkEvent var1) {
      if (!var1.getPacket().isFullChunk()) {
         chunks.add(var1.getChunk());
         dirty = true;
         if ((Boolean)this.saveNewChunks.getValue()) {
            this.saveNewChunk(var1.getChunk());
         }
      }

   }

   private PrintWriter testAndGetLogWriter() {
      if (this.lastSetting.testChangeAndUpdate()) {
         this.logWriterClose();
         this.logWriterOpen();
      }

      return this.logWriter;
   }

   private boolean isInteger(String var1) {
      try {
         Integer.parseInt(var1);
         return true;
      } catch (NullPointerException | NumberFormatException var4) {
         return false;
      }
   }

   private void logWriterOpen() {
      String var1 = this.getPath().toString();

      try {
         this.logWriter = new PrintWriter(new BufferedWriter(new FileWriter(var1, true)), true);
         String var2 = "timestamp,ChunkX,ChunkZ";
         if ((Boolean)this.alsoSaveNormalCoords.getValue()) {
            var2 = String.valueOf((new StringBuilder()).append(var2).append(",x coordinate,z coordinate"));
         }

         this.logWriter.println(var2);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.err.print(String.valueOf((new StringBuilder()).append("some exception happened when trying to start the logging -> ").append(var3.getMessage())));
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("onLogStart: ").append(var3.getMessage())));
      }

   }

   public void onDisable() {
      this.logWriterClose();
      chunks.clear();
   }

   public void destroy() {
      GL11.glDeleteLists(1, 1);
   }

   private String getNHackInetName() {
      String var1 = mc.getCurrentServerData().serverIP;
      if (SystemUtils.IS_OS_WINDOWS) {
         var1 = var1.replace(":", "_");
      }

      if (this.hasNoPort(var1)) {
         var1 = String.valueOf((new StringBuilder()).append(var1).append("_25565"));
      }

      return var1;
   }

   private String getNewChunkInfo(Chunk var1) {
      String var2 = String.format("%d,%d,%d", System.currentTimeMillis(), var1.x, var1.z);
      if ((Boolean)this.alsoSaveNormalCoords.getValue()) {
         var2 = String.valueOf((new StringBuilder()).append(var2).append(String.format(",%d,%d", var1.x * 16 + 8, var1.z * 16 + 8)));
      }

      return var2;
   }

   public void saveNewChunk(Chunk var1) {
      this.saveNewChunk(this.testAndGetLogWriter(), this.getNewChunkInfo(var1));
   }

   private boolean hasNoPort(String var1) {
      if (!var1.contains("_")) {
         return true;
      } else {
         String[] var2 = var1.split("_");
         String var3 = var2[var2.length - 1];
         return !this.isInteger(var3);
      }
   }

   private Path makeMultiplayerDirectory() {
      File var1 = Minecraft.getMinecraft().gameDir;
      String var2;
      if (((String)this.saveOption.getValue()).equalsIgnoreCase("liteLoaderWdl")) {
         var2 = mc.getCurrentServerData().serverName;
         var1 = new File(var1, "saves");
         var1 = new File(var1, var2);
      } else if (((String)this.saveOption.getValue()).equalsIgnoreCase("nhackWdl")) {
         var2 = this.getNHackInetName();
         var1 = new File(var1, "config");
         var1 = new File(var1, "wdl-saves");
         var1 = new File(var1, var2);
         if (!var1.exists()) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("nhack wdl directory doesnt exist: ").append(var2)));
            Command.sendChatMessage("creating the directory now. It is recommended to update the ip");
         }
      } else {
         var2 = String.valueOf((new StringBuilder()).append(mc.getCurrentServerData().serverName).append("-").append(mc.getCurrentServerData().serverIP));
         if (SystemUtils.IS_OS_WINDOWS) {
            var2 = var2.replace(":", "_");
         }

         var1 = new File(var1, "KAMI_NewChunks");
         var1 = new File(var1, var2);
      }

      return var1.toPath();
   }

   public void onWorldRender(RenderEvent var1) {
      double var4;
      double var6;
      if (dirty) {
         GL11.glNewList(this.list, 4864);
         GL11.glPushMatrix();
         GL11.glEnable(2848);
         GL11.glDisable(2929);
         GL11.glDisable(3553);
         GL11.glDepthMask(false);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(1.0F);
         Iterator var2 = chunks.iterator();

         while(var2.hasNext()) {
            Chunk var3 = (Chunk)var2.next();
            var4 = (double)(var3.x * 16);
            var6 = 0.0D;
            double var8 = (double)(var3.z * 16);
            GL11.glColor3f(0.6F, 0.1F, 0.2F);
            GL11.glBegin(2);
            GL11.glVertex3d(var4, var6, var8);
            GL11.glVertex3d(var4 + 16.0D, var6, var8);
            GL11.glVertex3d(var4 + 16.0D, var6, var8 + 16.0D);
            GL11.glVertex3d(var4, var6, var8 + 16.0D);
            GL11.glVertex3d(var4, var6, var8);
            GL11.glEnd();
         }

         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDisable(2848);
         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEndList();
         dirty = false;
      }

      double var10 = mc.getRenderManager().renderPosX;
      var4 = (Boolean)this.relative.getValue() ? 0.0D : -mc.getRenderManager().renderPosY;
      var6 = mc.getRenderManager().renderPosZ;
      GL11.glTranslated(-var10, var4 + (double)(Integer)this.yOffset.getValue(), -var6);
      GL11.glCallList(this.list);
      GL11.glTranslated(var10, -(var4 + (double)(Integer)this.yOffset.getValue()), var6);
   }

   private void saveNewChunk(PrintWriter var1, String var2) {
      var1.println(var2);
   }

   private void logWriterClose() {
      if (this.logWriter != null) {
         this.logWriter.close();
         this.logWriter = null;
      }

   }

   public ChunkFinder() {
      super("ChunkFinder", "Finds new chunks", 0, Category.RENDER, true);
   }

   @EventTarget
   private void onUnload(UnloadChunkEvent var1) {
      dirty = chunks.remove(var1.getChunk());
   }

   private Path getPath() {
      File var1 = null;
      int var2 = mc.player.dimension;
      if (mc.isSingleplayer()) {
         try {
            var1 = mc.getIntegratedServer().getWorld(var2).getChunkSaveLocation();
         } catch (Exception var7) {
            var7.printStackTrace();
            System.err.print(String.valueOf((new StringBuilder()).append("some exception happened when getting canonicalFile -> ").append(var7.getMessage())));
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("onGetPath: ").append(var7.getMessage())));
         }

         if (var1.toPath().relativize(mc.gameDir.toPath()).getNameCount() != 2) {
            var1 = var1.getParentFile();
         }
      } else {
         var1 = this.makeMultiplayerDirectory().toFile();
      }

      if (var2 != 0) {
         var1 = new File(var1, String.valueOf((new StringBuilder()).append("DIM").append(var2)));
      }

      if ((Boolean)this.saveInRegionFolder.getValue()) {
         var1 = new File(var1, "region");
      }

      var1 = new File(var1, "newChunkLogs");
      String var3 = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date());
      var1 = new File(var1, String.valueOf((new StringBuilder()).append(mc.getSession().getUsername()).append("_").append(var3).append(".csv")));
      Path var4 = var1.toPath();

      try {
         if (!Files.exists(var4, new LinkOption[0])) {
            Files.createDirectories(var4.getParent());
            Files.createFile(var4);
         }
      } catch (IOException var6) {
         var6.printStackTrace();
         System.err.print(String.valueOf((new StringBuilder()).append("some exception happened when trying to make the file -> ").append(var6.getMessage())));
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("onCreateFile: ").append(var6.getMessage())));
      }

      return var4;
   }

   private static enum SaveOption {
      // $FF: synthetic field
      nhackWdl,
      // $FF: synthetic field
      extraFolder,
      // $FF: synthetic field
      liteLoaderWdl;
   }

   private class LastSetting {
      // $FF: synthetic field
      boolean lastSaveNormal;
      // $FF: synthetic field
      String lastSaveOption;
      // $FF: synthetic field
      String ip;
      // $FF: synthetic field
      boolean lastInRegion;
      // $FF: synthetic field
      int dimension;

      private LastSetting() {
      }

      private void update() {
         this.lastSaveOption = (String)ChunkFinder.this.saveOption.getValue();
         this.lastInRegion = (Boolean)ChunkFinder.this.saveInRegionFolder.getValue();
         this.lastSaveNormal = (Boolean)ChunkFinder.this.alsoSaveNormalCoords.getValue();
         this.dimension = Helper.mc.player.dimension;
         this.ip = Helper.mc.getCurrentServerData().serverIP;
      }

      public boolean testChange() {
         if (!((String)ChunkFinder.this.saveOption.getValue()).equals(this.lastSaveOption)) {
            return true;
         } else if ((Boolean)ChunkFinder.this.saveInRegionFolder.getValue() != this.lastInRegion) {
            return true;
         } else if ((Boolean)ChunkFinder.this.alsoSaveNormalCoords.getValue() != this.lastSaveNormal) {
            return true;
         } else if (this.dimension != Helper.mc.player.dimension) {
            return true;
         } else {
            return !Helper.mc.getCurrentServerData().serverIP.equals(this.ip);
         }
      }

      // $FF: synthetic method
      LastSetting(Object var2) {
         this();
      }

      public boolean testChangeAndUpdate() {
         if (this.testChange()) {
            this.update();
            return true;
         } else {
            return false;
         }
      }
   }
}
