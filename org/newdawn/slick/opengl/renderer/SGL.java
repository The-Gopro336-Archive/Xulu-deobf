package org.newdawn.slick.opengl.renderer;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface SGL {
   // $FF: synthetic field
   int GL_COLOR_SUM_EXT = 33880;
   // $FF: synthetic field
   int GL_RGBA16 = 32859;
   // $FF: synthetic field
   int GL_BGRA = 32993;
   // $FF: synthetic field
   int GL_QUADS = 7;
   // $FF: synthetic field
   int GL_ONE_MINUS_SRC_ALPHA = 771;
   // $FF: synthetic field
   int GL_LINE_WIDTH = 2849;
   // $FF: synthetic field
   int GL_TEXTURE_WRAP_S = 10242;
   // $FF: synthetic field
   int GL_SRC_ALPHA = 770;
   // $FF: synthetic field
   int GL_COLOR_CLEAR_VALUE = 3106;
   // $FF: synthetic field
   int GL_NOTEQUAL = 517;
   // $FF: synthetic field
   int GL_ONE = 1;
   // $FF: synthetic field
   int GL_RGBA8 = 6408;
   // $FF: synthetic field
   int GL_DEPTH_BUFFER_BIT = 256;
   // $FF: synthetic field
   int GL_CLIP_PLANE0 = 12288;
   // $FF: synthetic field
   int GL_TEXTURE_ENV_MODE = 8704;
   // $FF: synthetic field
   int GL_SRC_COLOR = 768;
   // $FF: synthetic field
   int GL_TEXTURE_WRAP_T = 10243;
   // $FF: synthetic field
   int GL_CLAMP = 10496;
   // $FF: synthetic field
   int GL_COLOR_BUFFER_BIT = 16384;
   // $FF: synthetic field
   int GL_TEXTURE_2D = 3553;
   // $FF: synthetic field
   int GL_RGB = 6407;
   // $FF: synthetic field
   int GL_MAX_TEXTURE_SIZE = 3379;
   // $FF: synthetic field
   int GL_POINTS = 0;
   // $FF: synthetic field
   int GL_TRIANGLES = 4;
   // $FF: synthetic field
   int GL_POINT_SMOOTH = 2832;
   // $FF: synthetic field
   int GL_TRIANGLE_FAN = 6;
   // $FF: synthetic field
   int GL_ALWAYS = 519;
   // $FF: synthetic field
   int GL_TEXTURE_ENV = 8960;
   // $FF: synthetic field
   int GL_MODULATE = 8448;
   // $FF: synthetic field
   int GL_CLIP_PLANE1 = 12289;
   // $FF: synthetic field
   int GL_LINE_SMOOTH = 2848;
   // $FF: synthetic field
   int GL_EQUAL = 514;
   // $FF: synthetic field
   int GL_NEAREST = 9728;
   // $FF: synthetic field
   int GL_UNSIGNED_BYTE = 5121;
   // $FF: synthetic field
   int GL_DEPTH_TEST = 2929;
   // $FF: synthetic field
   int GL_LINES = 1;
   // $FF: synthetic field
   int GL_TEXTURE_MIN_FILTER = 10241;
   // $FF: synthetic field
   int GL_RGBA = 6408;
   // $FF: synthetic field
   int GL_MODELVIEW_MATRIX = 2982;
   // $FF: synthetic field
   int GL_COMPILE_AND_EXECUTE = 4865;
   // $FF: synthetic field
   int GL_COMPILE = 4864;
   // $FF: synthetic field
   int GL_MIRROR_CLAMP_TO_EDGE_EXT = 34627;
   // $FF: synthetic field
   int GL_LINE_STRIP = 3;
   // $FF: synthetic field
   int GL_SCISSOR_TEST = 3089;
   // $FF: synthetic field
   int GL_ONE_MINUS_DST_ALPHA = 773;
   // $FF: synthetic field
   int GL_TEXTURE_MAG_FILTER = 10240;
   // $FF: synthetic field
   int GL_CLIP_PLANE3 = 12291;
   // $FF: synthetic field
   int GL_LINEAR = 9729;
   // $FF: synthetic field
   int GL_CLIP_PLANE2 = 12290;
   // $FF: synthetic field
   int GL_DST_ALPHA = 772;
   // $FF: synthetic field
   int GL_BLEND = 3042;
   // $FF: synthetic field
   int GL_POLYGON_SMOOTH = 2881;
   // $FF: synthetic field
   int GL_ONE_MINUS_SRC_COLOR = 769;

   void glVertex2f(float var1, float var2);

   void glGetError();

   void glEnd();

   void glPointSize(float var1);

   void initDisplay(int var1, int var2);

   void glTexParameteri(int var1, int var2, int var3);

   void glScissor(int var1, int var2, int var3, int var4);

   void glReadPixels(int var1, int var2, int var3, int var4, int var5, int var6, ByteBuffer var7);

   void glClearColor(float var1, float var2, float var3, float var4);

   void glPopMatrix();

   void glSecondaryColor3ubEXT(byte var1, byte var2, byte var3);

   float[] getCurrentColor();

   void flush();

   void glGenTextures(IntBuffer var1);

   void glGetTexImage(int var1, int var2, int var3, int var4, ByteBuffer var5);

   void glClearDepth(float var1);

   void setGlobalAlphaScale(float var1);

   void glClear(int var1);

   void enterOrtho(int var1, int var2);

   void glRotatef(float var1, float var2, float var3, float var4);

   void glGetFloat(int var1, FloatBuffer var2);

   void glBlendFunc(int var1, int var2);

   void glTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, ByteBuffer var9);

   void glEnable(int var1);

   void glDeleteTextures(IntBuffer var1);

   void glCallList(int var1);

   void glNewList(int var1, int var2);

   void glColorMask(boolean var1, boolean var2, boolean var3, boolean var4);

   boolean canTextureMirrorClamp();

   boolean canSecondaryColor();

   void glTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, ByteBuffer var9);

   void glTexEnvi(int var1, int var2, int var3);

   void glLoadMatrix(FloatBuffer var1);

   void glBegin(int var1);

   void glLineWidth(float var1);

   void glDeleteLists(int var1, int var2);

   void glLoadIdentity();

   int glGenLists(int var1);

   void glTranslatef(float var1, float var2, float var3);

   void glClipPlane(int var1, DoubleBuffer var2);

   void glBindTexture(int var1, int var2);

   void glTexCoord2f(float var1, float var2);

   void glScalef(float var1, float var2, float var3);

   void glGetInteger(int var1, IntBuffer var2);

   void glPushMatrix();

   void glDepthFunc(int var1);

   void glDepthMask(boolean var1);

   void glDisable(int var1);

   void glEndList();

   void glVertex3f(float var1, float var2, float var3);

   void glCopyTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

   void glColor4f(float var1, float var2, float var3, float var4);
}
