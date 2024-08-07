package org.newdawn.slick.opengl;

import java.io.IOException;
import java.util.ArrayList;

public class CompositeIOException extends IOException {
   // $FF: synthetic field
   private ArrayList exceptions = new ArrayList();

   public String getMessage() {
      String var1 = "Composite Exception: \n";

      for(int var2 = 0; var2 < this.exceptions.size(); ++var2) {
         var1 = String.valueOf((new StringBuilder()).append(var1).append("\t").append(((IOException)this.exceptions.get(var2)).getMessage()).append("\n"));
      }

      return var1;
   }

   public void addException(Exception var1) {
      this.exceptions.add(var1);
   }
}
