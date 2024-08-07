package org.newdawn.slick.openal;

public interface Audio {
   int playAsSoundEffect(float var1, float var2, boolean var3, float var4, float var5, float var6);

   boolean setPosition(float var1);

   boolean isPlaying();

   void stop();

   int playAsSoundEffect(float var1, float var2, boolean var3);

   int playAsMusic(float var1, float var2, boolean var3);

   float getPosition();

   int getBufferID();
}
