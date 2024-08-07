package org.newdawn.slick.tests.xml;

import org.newdawn.slick.util.xml.ObjectTreeParser;
import org.newdawn.slick.util.xml.SlickXMLException;

public class ObjectParserTest {
   public static void main(String[] var0) throws SlickXMLException {
      ObjectTreeParser var1 = new ObjectTreeParser("org.newdawn.slick.tests.xml");
      var1.addElementMapping("Bag", ItemContainer.class);
      GameData var2 = (GameData)var1.parse("testdata/objxmltest.xml");
      var2.dump("");
   }
}
