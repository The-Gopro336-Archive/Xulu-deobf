package org.newdawn.slick.openal;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.util.Log;

public class OggInputStream extends InputStream implements AudioInputStream {
   // $FF: synthetic field
   private Packet packet;
   // $FF: synthetic field
   private int total;
   // $FF: synthetic field
   private SyncState syncState;
   // $FF: synthetic field
   boolean inited;
   // $FF: synthetic field
   private ByteBuffer pcmBuffer;
   // $FF: synthetic field
   private Block vorbisBlock;
   // $FF: synthetic field
   int bytes;
   // $FF: synthetic field
   private Page page;
   // $FF: synthetic field
   private StreamState streamState;
   // $FF: synthetic field
   private int convsize = 16384;
   // $FF: synthetic field
   private boolean endOfStream;
   // $FF: synthetic field
   private byte[] convbuffer;
   // $FF: synthetic field
   boolean endOfBitStream;
   // $FF: synthetic field
   private Info oggInfo;
   // $FF: synthetic field
   private InputStream input;
   // $FF: synthetic field
   boolean bigEndian;
   // $FF: synthetic field
   private int readIndex;
   // $FF: synthetic field
   byte[] buffer;
   // $FF: synthetic field
   private DspState dspState;
   // $FF: synthetic field
   private Comment comment;

   public OggInputStream(InputStream var1) throws IOException {
      this.convbuffer = new byte[this.convsize];
      this.oggInfo = new Info();
      this.syncState = new SyncState();
      this.streamState = new StreamState();
      this.page = new Page();
      this.packet = new Packet();
      this.comment = new Comment();
      this.dspState = new DspState();
      this.vorbisBlock = new Block(this.dspState);
      this.bytes = 0;
      this.bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
      this.endOfBitStream = true;
      this.inited = false;
      this.pcmBuffer = BufferUtils.createByteBuffer(2048000);
      this.input = var1;
      this.total = var1.available();
      this.init();
   }

   public int available() {
      return this.endOfStream ? 0 : 1;
   }

   private void readPCM() throws IOException {
      boolean var1 = false;

      while(true) {
         if (this.endOfBitStream) {
            if (!this.getPageAndPacket()) {
               this.syncState.clear();
               this.endOfStream = true;
               return;
            }

            this.endOfBitStream = false;
         }

         if (!this.inited) {
            this.inited = true;
            return;
         }

         float[][][] var2 = new float[1][][];
         int[] var3 = new int[this.oggInfo.channels];

         while(!this.endOfBitStream) {
            int var4;
            label135:
            while(true) {
               label133:
               while(true) {
                  if (this.endOfBitStream) {
                     break label135;
                  }

                  var4 = this.syncState.pageout(this.page);
                  if (var4 == 0) {
                     break label135;
                  }

                  if (var4 == -1) {
                     Log.error("Corrupt or missing data in bitstream; continuing...");
                  } else {
                     this.streamState.pagein(this.page);

                     while(true) {
                        do {
                           var4 = this.streamState.packetout(this.packet);
                           if (var4 == 0) {
                              if (this.page.eos() != 0) {
                                 this.endOfBitStream = true;
                              }

                              if (!this.endOfBitStream && var1) {
                                 return;
                              }
                              continue label133;
                           }
                        } while(var4 == -1);

                        if (this.vorbisBlock.synthesis(this.packet) == 0) {
                           this.dspState.synthesis_blockin(this.vorbisBlock);
                        }

                        int var5;
                        while((var5 = this.dspState.synthesis_pcmout(var2, var3)) > 0) {
                           float[][] var6 = var2[0];
                           int var7 = var5 < this.convsize ? var5 : this.convsize;

                           int var8;
                           for(var8 = 0; var8 < this.oggInfo.channels; ++var8) {
                              int var9 = var8 * 2;
                              int var10 = var3[var8];

                              for(int var11 = 0; var11 < var7; ++var11) {
                                 int var12 = (int)((double)var6[var8][var10 + var11] * 32767.0D);
                                 if (var12 > 32767) {
                                    var12 = 32767;
                                 }

                                 if (var12 < -32768) {
                                    var12 = -32768;
                                 }

                                 if (var12 < 0) {
                                    var12 |= 32768;
                                 }

                                 if (this.bigEndian) {
                                    this.convbuffer[var9] = (byte)(var12 >>> 8);
                                    this.convbuffer[var9 + 1] = (byte)var12;
                                 } else {
                                    this.convbuffer[var9] = (byte)var12;
                                    this.convbuffer[var9 + 1] = (byte)(var12 >>> 8);
                                 }

                                 var9 += 2 * this.oggInfo.channels;
                              }
                           }

                           var8 = 2 * this.oggInfo.channels * var7;
                           if (var8 >= this.pcmBuffer.remaining()) {
                              Log.warn(String.valueOf((new StringBuilder()).append("Read block from OGG that was too big to be buffered: ").append(var8)));
                           } else {
                              this.pcmBuffer.put(this.convbuffer, 0, var8);
                           }

                           var1 = true;
                           this.dspState.synthesis_read(var7);
                        }
                     }
                  }
               }
            }

            if (!this.endOfBitStream) {
               this.bytes = 0;
               var4 = this.syncState.buffer(4096);
               if (var4 >= 0) {
                  this.buffer = this.syncState.data;

                  try {
                     this.bytes = this.input.read(this.buffer, var4, 4096);
                  } catch (Exception var13) {
                     Log.error("Failure during vorbis decoding");
                     Log.error((Throwable)var13);
                     this.endOfStream = true;
                     return;
                  }
               } else {
                  this.bytes = 0;
               }

               this.syncState.wrote(this.bytes);
               if (this.bytes == 0) {
                  this.endOfBitStream = true;
               }
            }
         }

         this.streamState.clear();
         this.vorbisBlock.clear();
         this.dspState.clear();
         this.oggInfo.clear();
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int getLength() {
      return this.total;
   }

   private void init() throws IOException {
      this.initVorbis();
      this.readPCM();
   }

   private void initVorbis() {
      this.syncState.init();
   }

   public void close() throws IOException {
   }

   public boolean atEnd() {
      return this.endOfStream && this.readIndex >= this.pcmBuffer.position();
   }

   public int read() throws IOException {
      if (this.readIndex >= this.pcmBuffer.position()) {
         this.pcmBuffer.clear();
         this.readPCM();
         this.readIndex = 0;
      }

      if (this.readIndex >= this.pcmBuffer.position()) {
         return -1;
      } else {
         int var1 = this.pcmBuffer.get(this.readIndex);
         if (var1 < 0) {
            var1 += 256;
         }

         ++this.readIndex;
         return var1;
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         try {
            int var5 = this.read();
            if (var5 < 0) {
               if (var4 == 0) {
                  return -1;
               }

               return var4;
            }

            var1[var4] = (byte)var5;
         } catch (IOException var6) {
            Log.error((Throwable)var6);
            return var4;
         }
      }

      return var3;
   }

   private boolean getPageAndPacket() {
      int var1 = this.syncState.buffer(4096);
      this.buffer = this.syncState.data;
      if (this.buffer == null) {
         this.endOfStream = true;
         return false;
      } else {
         try {
            this.bytes = this.input.read(this.buffer, var1, 4096);
         } catch (Exception var5) {
            Log.error("Failure reading in vorbis");
            Log.error((Throwable)var5);
            this.endOfStream = true;
            return false;
         }

         this.syncState.wrote(this.bytes);
         if (this.syncState.pageout(this.page) != 1) {
            if (this.bytes < 4096) {
               return false;
            } else {
               Log.error("Input does not appear to be an Ogg bitstream.");
               this.endOfStream = true;
               return false;
            }
         } else {
            this.streamState.init(this.page.serialno());
            this.oggInfo.init();
            this.comment.init();
            if (this.streamState.pagein(this.page) < 0) {
               Log.error("Error reading first page of Ogg bitstream data.");
               this.endOfStream = true;
               return false;
            } else if (this.streamState.packetout(this.packet) != 1) {
               Log.error("Error reading initial header packet.");
               this.endOfStream = true;
               return false;
            } else if (this.oggInfo.synthesis_headerin(this.comment, this.packet) < 0) {
               Log.error("This Ogg bitstream does not contain Vorbis audio data.");
               this.endOfStream = true;
               return false;
            } else {
               int var2 = 0;

               while(var2 < 2) {
                  label84:
                  while(true) {
                     int var3;
                     do {
                        if (var2 >= 2) {
                           break label84;
                        }

                        var3 = this.syncState.pageout(this.page);
                        if (var3 == 0) {
                           break label84;
                        }
                     } while(var3 != 1);

                     this.streamState.pagein(this.page);

                     while(var2 < 2) {
                        var3 = this.streamState.packetout(this.packet);
                        if (var3 == 0) {
                           break;
                        }

                        if (var3 == -1) {
                           Log.error("Corrupt secondary header.  Exiting.");
                           this.endOfStream = true;
                           return false;
                        }

                        this.oggInfo.synthesis_headerin(this.comment, this.packet);
                        ++var2;
                     }
                  }

                  var1 = this.syncState.buffer(4096);
                  this.buffer = this.syncState.data;

                  try {
                     this.bytes = this.input.read(this.buffer, var1, 4096);
                  } catch (Exception var4) {
                     Log.error("Failed to read Vorbis: ");
                     Log.error((Throwable)var4);
                     this.endOfStream = true;
                     return false;
                  }

                  if (this.bytes == 0 && var2 < 2) {
                     Log.error("End of file before finding all Vorbis headers!");
                     this.endOfStream = true;
                     return false;
                  }

                  this.syncState.wrote(this.bytes);
               }

               this.convsize = 4096 / this.oggInfo.channels;
               this.dspState.synthesis_init(this.oggInfo);
               this.vorbisBlock.init(this.dspState);
               return true;
            }
         }
      }
   }

   public int getRate() {
      return this.oggInfo.rate;
   }

   public int getChannels() {
      return this.oggInfo.channels;
   }
}
