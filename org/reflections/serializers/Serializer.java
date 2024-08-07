package org.reflections.serializers;

import java.io.File;
import java.io.InputStream;
import org.reflections.Reflections;

public interface Serializer {
   File save(Reflections var1, String var2);

   String toString(Reflections var1);

   Reflections read(InputStream var1);
}
