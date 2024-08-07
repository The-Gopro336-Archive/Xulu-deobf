package com.elementars.eclient.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.lwjgl.opengl.GL20;

public class GLSLSandboxShader {
   // $FF: synthetic field
   private final int mouseUniform;
   // $FF: synthetic field
   private final int resolutionUniform;
   // $FF: synthetic field
   private final int programId;
   // $FF: synthetic field
   private final int timeUniform;

   public void useShader(int var1, int var2, float var3, float var4, float var5) {
      GL20.glUseProgram(this.programId);
      GL20.glUniform2f(this.resolutionUniform, (float)var1, (float)var2);
      GL20.glUniform2f(this.mouseUniform, var3 / (float)var1, 1.0F - var4 / (float)var2);
      GL20.glUniform1f(this.timeUniform, var5);
   }

   private int createShader(String var1, InputStream var2, int var3) throws IOException {
      int var4 = GL20.glCreateShader(var3);
      GL20.glShaderSource(var4, this.readStreamToString(var2));
      GL20.glCompileShader(var4);
      int var5 = GL20.glGetShaderi(var4, 35713);
      if (var5 == 0) {
         System.err.println(GL20.glGetShaderInfoLog(var4, GL20.glGetShaderi(var4, 35716)));
         System.err.println(String.valueOf((new StringBuilder()).append("Caused by ").append(var1)));
         throw new IllegalStateException(String.valueOf((new StringBuilder()).append("Failed to compile shader: ").append(var1)));
      } else {
         return var4;
      }
   }

   private String readStreamToString(InputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = new byte[512];

      int var4;
      while((var4 = var1.read(var3, 0, var3.length)) != -1) {
         var2.write(var3, 0, var4);
      }

      return new String(var2.toByteArray(), StandardCharsets.UTF_8);
   }

   public GLSLSandboxShader(String var1) throws IOException {
      int var2 = GL20.glCreateProgram();
      GL20.glAttachShader(var2, this.createShader("/shaders/passthrough.vsh", GLSLSandboxShader.class.getResourceAsStream("/shaders/passthrough.vsh"), 35633));
      GL20.glAttachShader(var2, this.createShader(var1, GLSLSandboxShader.class.getResourceAsStream(var1), 35632));
      GL20.glLinkProgram(var2);
      int var3 = GL20.glGetProgrami(var2, 35714);
      if (var3 == 0) {
         System.err.println(GL20.glGetProgramInfoLog(var2, GL20.glGetProgrami(var2, 35716)));
         throw new IllegalStateException("Shader failed to link");
      } else {
         this.programId = var2;
         GL20.glUseProgram(var2);
         this.timeUniform = GL20.glGetUniformLocation(var2, "time");
         this.mouseUniform = GL20.glGetUniformLocation(var2, "mouse");
         this.resolutionUniform = GL20.glGetUniformLocation(var2, "resolution");
         GL20.glUseProgram(0);
      }
   }
}
