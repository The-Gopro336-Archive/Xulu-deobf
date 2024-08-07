package org.reflections.scanners;

import java.util.function.Predicate;
import org.reflections.Configuration;
import org.reflections.Store;
import org.reflections.vfs.Vfs;

public interface Scanner {
   Scanner filterResultsBy(Predicate var1);

   boolean acceptsInput(String var1);

   boolean acceptResult(String var1);

   void setConfiguration(Configuration var1);

   Object scan(Vfs.File var1, Object var2, Store var3);
}
