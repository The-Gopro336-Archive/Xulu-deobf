package org.newdawn.slick.openal;

import java.io.IOException;

interface AudioInputStream {
   int getRate();

   boolean atEnd();

   int read(byte[] var1) throws IOException;

   int getChannels();

   int read() throws IOException;

   void close() throws IOException;

   int read(byte[] var1, int var2, int var3) throws IOException;
}
