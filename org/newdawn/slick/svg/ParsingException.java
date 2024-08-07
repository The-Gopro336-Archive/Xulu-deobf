package org.newdawn.slick.svg;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;

public class ParsingException extends SlickException {
   public ParsingException(String var1, String var2) {
      super(String.valueOf((new StringBuilder()).append("(").append(var1).append(") ").append(var2)));
   }

   public ParsingException(String var1, String var2, Throwable var3) {
      super(String.valueOf((new StringBuilder()).append("(").append(var1).append(") ").append(var2)), var3);
   }

   public ParsingException(Element var1, String var2) {
      super(String.valueOf((new StringBuilder()).append("(").append(var1.getAttribute("id")).append(") ").append(var2)));
   }

   public ParsingException(Element var1, String var2, Throwable var3) {
      super(String.valueOf((new StringBuilder()).append("(").append(var1.getAttribute("id")).append(") ").append(var2)), var3);
   }
}
