package org.reflections.adapters;

import java.util.List;
import org.reflections.vfs.Vfs;

public interface MetadataAdapter {
   boolean acceptsInput(String var1);

   String getMethodName(Object var1);

   String getReturnTypeName(Object var1);

   List getFields(Object var1);

   List getParameterNames(Object var1);

   String getSuperclassName(Object var1);

   String getMethodModifier(Object var1);

   Object getOrCreateClassObject(Vfs.File var1) throws Exception;

   String getFieldName(Object var1);

   String getMethodKey(Object var1, Object var2);

   List getFieldAnnotationNames(Object var1);

   String getMethodFullKey(Object var1, Object var2);

   List getMethodAnnotationNames(Object var1);

   List getParameterAnnotationNames(Object var1, int var2);

   List getClassAnnotationNames(Object var1);

   String getClassName(Object var1);

   boolean isPublic(Object var1);

   List getMethods(Object var1);

   List getInterfacesNames(Object var1);
}
