package org.newdawn.slick.opengl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PNGDecoder {
   // $FF: synthetic field
   private byte[] transPixel;
   // $FF: synthetic field
   public static PNGDecoder.Format LUMINANCE = new PNGDecoder.Format(1, false);
   // $FF: synthetic field
   private int bitdepth;
   // $FF: synthetic field
   private static final int tRNS = 1951551059;
   // $FF: synthetic field
   private final InputStream input;
   // $FF: synthetic field
   private static final byte COLOR_INDEXED = 3;
   // $FF: synthetic field
   private final CRC32 crc;
   // $FF: synthetic field
   private static final byte COLOR_GREYSCALE = 0;
   // $FF: synthetic field
   public static PNGDecoder.Format ABGR = new PNGDecoder.Format(4, true);
   // $FF: synthetic field
   private int chunkLength;
   // $FF: synthetic field
   private static final byte COLOR_TRUECOLOR = 2;
   // $FF: synthetic field
   private final byte[] buffer;
   // $FF: synthetic field
   private static final int PLTE = 1347179589;
   // $FF: synthetic field
   private static final int IEND = 1229278788;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   public static PNGDecoder.Format ALPHA = new PNGDecoder.Format(1, true);
   // $FF: synthetic field
   private static final byte COLOR_GREYALPHA = 4;
   // $FF: synthetic field
   private byte[] palette;
   // $FF: synthetic field
   public static PNGDecoder.Format RGB = new PNGDecoder.Format(3, false);
   // $FF: synthetic field
   private static final byte[] SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
   // $FF: synthetic field
   private static final int IDAT = 1229209940;
   // $FF: synthetic field
   private static final int IHDR = 1229472850;
   // $FF: synthetic field
   private int bytesPerPixel;
   // $FF: synthetic field
   public static PNGDecoder.Format BGRA = new PNGDecoder.Format(4, true);
   // $FF: synthetic field
   private byte[] paletteA;
   // $FF: synthetic field
   public static PNGDecoder.Format RGBA = new PNGDecoder.Format(4, true);
   // $FF: synthetic field
   private int chunkType;
   // $FF: synthetic field
   private int chunkRemaining;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private int colorType;
   // $FF: synthetic field
   private static final byte COLOR_TRUEALPHA = 6;
   // $FF: synthetic field
   public static PNGDecoder.Format LUMINANCE_ALPHA = new PNGDecoder.Format(2, true);

   public void decode(ByteBuffer var1, int var2, PNGDecoder.Format var3) throws IOException {
      int var4 = var1.position();
      int var5 = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
      byte[] var6 = new byte[var5 + 1];
      byte[] var7 = new byte[var5 + 1];
      byte[] var8 = this.bitdepth < 8 ? new byte[this.width + 1] : null;
      Inflater var9 = new Inflater();

      try {
         for(int var10 = 0; var10 < this.height; ++var10) {
            this.readChunkUnzip(var9, var6, 0, var6.length);
            this.unfilter(var6, var7);
            var1.position(var4 + var10 * var2);
            switch(this.colorType) {
            case 0:
               if (var3 != LUMINANCE && var3 != ALPHA) {
                  throw new UnsupportedOperationException("Unsupported format for this image");
               }

               this.copy(var1, var6);
               break;
            case 1:
            case 5:
            default:
               throw new UnsupportedOperationException("Not yet implemented");
            case 2:
               if (var3 == ABGR) {
                  this.copyRGBtoABGR(var1, var6);
               } else if (var3 == RGBA) {
                  this.copyRGBtoRGBA(var1, var6);
               } else if (var3 == BGRA) {
                  this.copyRGBtoBGRA(var1, var6);
               } else {
                  if (var3 != RGB) {
                     throw new UnsupportedOperationException("Unsupported format for this image");
                  }

                  this.copy(var1, var6);
               }
               break;
            case 3:
               switch(this.bitdepth) {
               case 1:
                  this.expand1(var6, var8);
                  break;
               case 2:
                  this.expand2(var6, var8);
                  break;
               case 3:
               case 5:
               case 6:
               case 7:
               default:
                  throw new UnsupportedOperationException("Unsupported bitdepth for this image");
               case 4:
                  this.expand4(var6, var8);
                  break;
               case 8:
                  var8 = var6;
               }

               if (var3 == ABGR) {
                  this.copyPALtoABGR(var1, var8);
               } else if (var3 == RGBA) {
                  this.copyPALtoRGBA(var1, var8);
               } else {
                  if (var3 != BGRA) {
                     throw new UnsupportedOperationException("Unsupported format for this image");
                  }

                  this.copyPALtoBGRA(var1, var8);
               }
               break;
            case 4:
               if (var3 != LUMINANCE_ALPHA) {
                  throw new UnsupportedOperationException("Unsupported format for this image");
               }

               this.copy(var1, var6);
               break;
            case 6:
               if (var3 == ABGR) {
                  this.copyRGBAtoABGR(var1, var6);
               } else if (var3 == RGBA) {
                  this.copy(var1, var6);
               } else if (var3 == BGRA) {
                  this.copyRGBAtoBGRA(var1, var6);
               } else {
                  if (var3 != RGB) {
                     throw new UnsupportedOperationException("Unsupported format for this image");
                  }

                  this.copyRGBAtoRGB(var1, var6);
               }
            }

            byte[] var11 = var6;
            var6 = var7;
            var7 = var11;
         }
      } finally {
         var9.end();
      }

   }

   private void unfilterSub(byte[] var1) {
      int var2 = this.bytesPerPixel;
      int var3 = var2 + 1;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         var1[var3] += var1[var3 - var2];
      }

   }

   private int readChunk(byte[] var1, int var2, int var3) throws IOException {
      if (var3 > this.chunkRemaining) {
         var3 = this.chunkRemaining;
      }

      this.readFully(var1, var2, var3);
      this.crc.update(var1, var2, var3);
      this.chunkRemaining -= var3;
      return var3;
   }

   private void expand1(byte[] var1, byte[] var2) {
      int var3 = 1;
      int var4 = var2.length;

      while(var3 < var4) {
         int var5 = var1[1 + (var3 >> 3)] & 255;
         switch(var4 - var3) {
         default:
            var2[var3 + 7] = (byte)(var5 & 1);
         case 7:
            var2[var3 + 6] = (byte)(var5 >> 1 & 1);
         case 6:
            var2[var3 + 5] = (byte)(var5 >> 2 & 1);
         case 5:
            var2[var3 + 4] = (byte)(var5 >> 3 & 1);
         case 4:
            var2[var3 + 3] = (byte)(var5 >> 4 & 1);
         case 3:
            var2[var3 + 2] = (byte)(var5 >> 5 & 1);
         case 2:
            var2[var3 + 1] = (byte)(var5 >> 6 & 1);
         case 1:
            var2[var3] = (byte)(var5 >> 7);
            var3 += 8;
         }
      }

   }

   private void unfilter(byte[] var1, byte[] var2) throws IOException {
      switch(var1[0]) {
      case 0:
         break;
      case 1:
         this.unfilterSub(var1);
         break;
      case 2:
         this.unfilterUp(var1, var2);
         break;
      case 3:
         this.unfilterAverage(var1, var2);
         break;
      case 4:
         this.unfilterPaeth(var1, var2);
         break;
      default:
         throw new IOException(String.valueOf((new StringBuilder()).append("invalide filter type in scanline: ").append(var1[0])));
      }

   }

   private void unfilterAverage(byte[] var1, byte[] var2) {
      int var3 = this.bytesPerPixel;

      int var4;
      for(var4 = 1; var4 <= var3; ++var4) {
         var1[var4] += (byte)((var2[var4] & 255) >>> 1);
      }

      for(int var5 = var1.length; var4 < var5; ++var4) {
         var1[var4] += (byte)((var2[var4] & 255) + (var1[var4 - var3] & 255) >>> 1);
      }

   }

   private void copyPALtoRGBA(ByteBuffer var1, byte[] var2) {
      int var3;
      int var4;
      int var5;
      byte var6;
      byte var7;
      byte var8;
      byte var9;
      if (this.paletteA != null) {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = this.paletteA[var5];
            var1.put(var6).put(var7).put(var8).put(var9);
         }
      } else {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = -1;
            var1.put(var6).put(var7).put(var8).put(var9);
         }
      }

   }

   public int getHeight() {
      return this.height;
   }

   private void readIHDR() throws IOException {
      this.checkChunkLength(13);
      this.readChunk(this.buffer, 0, 13);
      this.width = this.readInt(this.buffer, 0);
      this.height = this.readInt(this.buffer, 4);
      this.bitdepth = this.buffer[8] & 255;
      this.colorType = this.buffer[9] & 255;
      label43:
      switch(this.colorType) {
      case 0:
         if (this.bitdepth != 8) {
            throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported bit depth: ").append(this.bitdepth)));
         }

         this.bytesPerPixel = 1;
         break;
      case 1:
      case 5:
      default:
         throw new IOException(String.valueOf((new StringBuilder()).append("unsupported color format: ").append(this.colorType)));
      case 2:
         if (this.bitdepth != 8) {
            throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported bit depth: ").append(this.bitdepth)));
         }

         this.bytesPerPixel = 3;
         break;
      case 3:
         switch(this.bitdepth) {
         case 1:
         case 2:
         case 4:
         case 8:
            this.bytesPerPixel = 1;
            break label43;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported bit depth: ").append(this.bitdepth)));
         }
      case 4:
         if (this.bitdepth != 8) {
            throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported bit depth: ").append(this.bitdepth)));
         }

         this.bytesPerPixel = 2;
         break;
      case 6:
         if (this.bitdepth != 8) {
            throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported bit depth: ").append(this.bitdepth)));
         }

         this.bytesPerPixel = 4;
      }

      if (this.buffer[10] != 0) {
         throw new IOException("unsupported compression method");
      } else if (this.buffer[11] != 0) {
         throw new IOException("unsupported filtering method");
      } else if (this.buffer[12] != 0) {
         throw new IOException("unsupported interlace method");
      }
   }

   private void copyPALtoABGR(ByteBuffer var1, byte[] var2) {
      int var3;
      int var4;
      int var5;
      byte var6;
      byte var7;
      byte var8;
      byte var9;
      if (this.paletteA != null) {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = this.paletteA[var5];
            var1.put(var9).put(var8).put(var7).put(var6);
         }
      } else {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = -1;
            var1.put(var9).put(var8).put(var7).put(var6);
         }
      }

   }

   private void copyRGBAtoBGRA(ByteBuffer var1, byte[] var2) {
      int var3 = 1;

      for(int var4 = var2.length; var3 < var4; var3 += 4) {
         var1.put(var2[var3 + 2]).put(var2[var3 + 1]).put(var2[var3 + 0]).put(var2[var3 + 3]);
      }

   }

   private void copyRGBtoABGR(ByteBuffer var1, byte[] var2) {
      if (this.transPixel != null) {
         byte var3 = this.transPixel[1];
         byte var4 = this.transPixel[3];
         byte var5 = this.transPixel[5];
         int var6 = 1;

         for(int var7 = var2.length; var6 < var7; var6 += 3) {
            byte var8 = var2[var6];
            byte var9 = var2[var6 + 1];
            byte var10 = var2[var6 + 2];
            byte var11 = -1;
            if (var8 == var3 && var9 == var4 && var10 == var5) {
               var11 = 0;
            }

            var1.put(var11).put(var10).put(var9).put(var8);
         }
      } else {
         int var12 = 1;

         for(int var13 = var2.length; var12 < var13; var12 += 3) {
            var1.put((byte)-1).put(var2[var12 + 2]).put(var2[var12 + 1]).put(var2[var12]);
         }
      }

   }

   public PNGDecoder(InputStream var1) throws IOException {
      this.input = var1;
      this.crc = new CRC32();
      this.buffer = new byte[4096];
      this.readFully(this.buffer, 0, SIGNATURE.length);
      if (!checkSignature(this.buffer)) {
         throw new IOException("Not a valid PNG file");
      } else {
         this.openChunk(1229472850);
         this.readIHDR();
         this.closeChunk();

         while(true) {
            this.openChunk();
            switch(this.chunkType) {
            case 1229209940:
               if (this.colorType == 3 && this.palette == null) {
                  throw new IOException("Missing PLTE chunk");
               }

               return;
            case 1347179589:
               this.readPLTE();
               break;
            case 1951551059:
               this.readtRNS();
            }

            this.closeChunk();
         }
      }
   }

   private void copyPALtoBGRA(ByteBuffer var1, byte[] var2) {
      int var3;
      int var4;
      int var5;
      byte var6;
      byte var7;
      byte var8;
      byte var9;
      if (this.paletteA != null) {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = this.paletteA[var5];
            var1.put(var8).put(var7).put(var6).put(var9);
         }
      } else {
         var3 = 1;

         for(var4 = var2.length; var3 < var4; ++var3) {
            var5 = var2[var3] & 255;
            var6 = this.palette[var5 * 3 + 0];
            var7 = this.palette[var5 * 3 + 1];
            var8 = this.palette[var5 * 3 + 2];
            var9 = -1;
            var1.put(var8).put(var7).put(var6).put(var9);
         }
      }

   }

   private void unfilterPaeth(byte[] var1, byte[] var2) {
      int var3 = this.bytesPerPixel;

      int var4;
      for(var4 = 1; var4 <= var3; ++var4) {
         var1[var4] += var2[var4];
      }

      for(int var5 = var1.length; var4 < var5; ++var4) {
         int var6 = var1[var4 - var3] & 255;
         int var7 = var2[var4] & 255;
         int var8 = var2[var4 - var3] & 255;
         int var9 = var6 + var7 - var8;
         int var10 = var9 - var6;
         if (var10 < 0) {
            var10 = -var10;
         }

         int var11 = var9 - var7;
         if (var11 < 0) {
            var11 = -var11;
         }

         int var12 = var9 - var8;
         if (var12 < 0) {
            var12 = -var12;
         }

         if (var10 <= var11 && var10 <= var12) {
            var8 = var6;
         } else if (var11 <= var12) {
            var8 = var7;
         }

         var1[var4] += (byte)var8;
      }

   }

   private void unfilterUp(byte[] var1, byte[] var2) {
      int var3 = this.bytesPerPixel;
      int var4 = 1;

      for(int var5 = var1.length; var4 < var5; ++var4) {
         var1[var4] += var2[var4];
      }

   }

   private static boolean checkSignature(byte[] var0) {
      for(int var1 = 0; var1 < SIGNATURE.length; ++var1) {
         if (var0[var1] != SIGNATURE[var1]) {
            return false;
         }
      }

      return true;
   }

   private void expand2(byte[] var1, byte[] var2) {
      int var3 = 1;
      int var4 = var2.length;

      while(var3 < var4) {
         int var5 = var1[1 + (var3 >> 2)] & 255;
         switch(var4 - var3) {
         default:
            var2[var3 + 3] = (byte)(var5 & 3);
         case 3:
            var2[var3 + 2] = (byte)(var5 >> 2 & 3);
         case 2:
            var2[var3 + 1] = (byte)(var5 >> 4 & 3);
         case 1:
            var2[var3] = (byte)(var5 >> 6);
            var3 += 4;
         }
      }

   }

   private void openChunk(int var1) throws IOException {
      this.openChunk();
      if (this.chunkType != var1) {
         throw new IOException(String.valueOf((new StringBuilder()).append("Expected chunk: ").append(Integer.toHexString(var1))));
      }
   }

   private void readChunkUnzip(Inflater var1, byte[] var2, int var3, int var4) throws IOException {
      try {
         do {
            int var5 = var1.inflate(var2, var3, var4);
            if (var5 <= 0) {
               if (var1.finished()) {
                  throw new EOFException();
               }

               if (!var1.needsInput()) {
                  throw new IOException(String.valueOf((new StringBuilder()).append("Can't inflate ").append(var4).append(" bytes")));
               }

               this.refillInflater(var1);
            } else {
               var3 += var5;
               var4 -= var5;
            }
         } while(var4 > 0);

      } catch (DataFormatException var6) {
         throw (IOException)((IOException)(new IOException("inflate error")).initCause(var6));
      }
   }

   private void copyRGBAtoRGB(ByteBuffer var1, byte[] var2) {
      int var3 = 1;

      for(int var4 = var2.length; var3 < var4; var3 += 4) {
         var1.put(var2[var3]).put(var2[var3 + 1]).put(var2[var3 + 2]);
      }

   }

   private void copyRGBtoBGRA(ByteBuffer var1, byte[] var2) {
      if (this.transPixel != null) {
         byte var3 = this.transPixel[1];
         byte var4 = this.transPixel[3];
         byte var5 = this.transPixel[5];
         int var6 = 1;

         for(int var7 = var2.length; var6 < var7; var6 += 3) {
            byte var8 = var2[var6];
            byte var9 = var2[var6 + 1];
            byte var10 = var2[var6 + 2];
            byte var11 = -1;
            if (var8 == var3 && var9 == var4 && var10 == var5) {
               var11 = 0;
            }

            var1.put(var10).put(var9).put(var8).put(var11);
         }
      } else {
         int var12 = 1;

         for(int var13 = var2.length; var12 < var13; var12 += 3) {
            var1.put(var2[var12 + 2]).put(var2[var12 + 1]).put(var2[var12]).put((byte)-1);
         }
      }

   }

   public PNGDecoder.Format decideTextureFormat(PNGDecoder.Format var1) {
      switch(this.colorType) {
      case 0:
         if (var1 != LUMINANCE && var1 != ALPHA) {
            return LUMINANCE;
         }

         return var1;
      case 1:
      case 5:
      default:
         throw new UnsupportedOperationException("Not yet implemented");
      case 2:
         if (var1 != ABGR && var1 != RGBA && var1 != BGRA && var1 != RGB) {
            return RGB;
         }

         return var1;
      case 3:
         if (var1 != ABGR && var1 != RGBA && var1 != BGRA) {
            return RGBA;
         }

         return var1;
      case 4:
         return LUMINANCE_ALPHA;
      case 6:
         return var1 != ABGR && var1 != RGBA && var1 != BGRA && var1 != RGB ? RGBA : var1;
      }
   }

   private void skip(long var1) throws IOException {
      while(var1 > 0L) {
         long var3 = this.input.skip(var1);
         if (var3 < 0L) {
            throw new EOFException();
         }

         var1 -= var3;
      }

   }

   private void copy(ByteBuffer var1, byte[] var2) {
      var1.put(var2, 1, var2.length - 1);
   }

   private void readtRNS() throws IOException {
      switch(this.colorType) {
      case 0:
         this.checkChunkLength(2);
         this.transPixel = new byte[2];
         this.readChunk(this.transPixel, 0, 2);
      case 1:
      default:
         break;
      case 2:
         this.checkChunkLength(6);
         this.transPixel = new byte[6];
         this.readChunk(this.transPixel, 0, 6);
         break;
      case 3:
         if (this.palette == null) {
            throw new IOException("tRNS chunk without PLTE chunk");
         }

         this.paletteA = new byte[this.palette.length / 3];
         Arrays.fill(this.paletteA, (byte)-1);
         this.readChunk(this.paletteA, 0, this.paletteA.length);
      }

   }

   private int readInt(byte[] var1, int var2) {
      return var1[var2] << 24 | (var1[var2 + 1] & 255) << 16 | (var1[var2 + 2] & 255) << 8 | var1[var2 + 3] & 255;
   }

   public boolean isRGB() {
      return this.colorType == 6 || this.colorType == 2 || this.colorType == 3;
   }

   private void openChunk() throws IOException {
      this.readFully(this.buffer, 0, 8);
      this.chunkLength = this.readInt(this.buffer, 0);
      this.chunkType = this.readInt(this.buffer, 4);
      this.chunkRemaining = this.chunkLength;
      this.crc.reset();
      this.crc.update(this.buffer, 4, 4);
   }

   private void expand4(byte[] var1, byte[] var2) {
      int var3 = 1;
      int var4 = var2.length;

      while(var3 < var4) {
         int var5 = var1[1 + (var3 >> 1)] & 255;
         switch(var4 - var3) {
         default:
            var2[var3 + 1] = (byte)(var5 & 15);
         case 1:
            var2[var3] = (byte)(var5 >> 4);
            var3 += 2;
         }
      }

   }

   private void readFully(byte[] var1, int var2, int var3) throws IOException {
      do {
         int var4 = this.input.read(var1, var2, var3);
         if (var4 < 0) {
            throw new EOFException();
         }

         var2 += var4;
         var3 -= var4;
      } while(var3 > 0);

   }

   private void copyRGBAtoABGR(ByteBuffer var1, byte[] var2) {
      int var3 = 1;

      for(int var4 = var2.length; var3 < var4; var3 += 4) {
         var1.put(var2[var3 + 3]).put(var2[var3 + 2]).put(var2[var3 + 1]).put(var2[var3]);
      }

   }

   public boolean hasAlpha() {
      return this.colorType == 6 || this.paletteA != null || this.transPixel != null;
   }

   private void closeChunk() throws IOException {
      if (this.chunkRemaining > 0) {
         this.skip((long)(this.chunkRemaining + 4));
      } else {
         this.readFully(this.buffer, 0, 4);
         int var1 = this.readInt(this.buffer, 0);
         int var2 = (int)this.crc.getValue();
         if (var2 != var1) {
            throw new IOException("Invalid CRC");
         }
      }

      this.chunkRemaining = 0;
      this.chunkLength = 0;
      this.chunkType = 0;
   }

   public int getWidth() {
      return this.width;
   }

   private void refillInflater(Inflater var1) throws IOException {
      while(this.chunkRemaining == 0) {
         this.closeChunk();
         this.openChunk(1229209940);
      }

      int var2 = this.readChunk(this.buffer, 0, this.buffer.length);
      var1.setInput(this.buffer, 0, var2);
   }

   private void readPLTE() throws IOException {
      int var1 = this.chunkLength / 3;
      if (var1 >= 1 && var1 <= 256 && this.chunkLength % 3 == 0) {
         this.palette = new byte[var1 * 3];
         this.readChunk(this.palette, 0, this.palette.length);
      } else {
         throw new IOException("PLTE chunk has wrong length");
      }
   }

   private void copyRGBtoRGBA(ByteBuffer var1, byte[] var2) {
      if (this.transPixel != null) {
         byte var3 = this.transPixel[1];
         byte var4 = this.transPixel[3];
         byte var5 = this.transPixel[5];
         int var6 = 1;

         for(int var7 = var2.length; var6 < var7; var6 += 3) {
            byte var8 = var2[var6];
            byte var9 = var2[var6 + 1];
            byte var10 = var2[var6 + 2];
            byte var11 = -1;
            if (var8 == var3 && var9 == var4 && var10 == var5) {
               var11 = 0;
            }

            var1.put(var8).put(var9).put(var10).put(var11);
         }
      } else {
         int var13 = 1;

         for(int var14 = var2.length; var13 < var14; var13 += 3) {
            var1.put(var2[var13]).put(var2[var13 + 1]).put(var2[var13 + 2]).put((byte)-1);
         }
      }

   }

   private void checkChunkLength(int var1) throws IOException {
      if (this.chunkLength != var1) {
         throw new IOException("Chunk has wrong size");
      }
   }

   public static class Format {
      // $FF: synthetic field
      final int numComponents;
      // $FF: synthetic field
      final boolean hasAlpha;

      private Format(int var1, boolean var2) {
         this.numComponents = var1;
         this.hasAlpha = var2;
      }

      public int getNumComponents() {
         return this.numComponents;
      }

      // $FF: synthetic method
      Format(int var1, boolean var2, Object var3) {
         this(var1, var2);
      }

      public boolean isHasAlpha() {
         return this.hasAlpha;
      }
   }
}
