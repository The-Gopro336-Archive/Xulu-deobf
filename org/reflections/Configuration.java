package org.reflections;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.serializers.Serializer;

public interface Configuration {
   Predicate getInputsFilter();

   MetadataAdapter getMetadataAdapter();

   ExecutorService getExecutorService();

   Set getUrls();

   ClassLoader[] getClassLoaders();

   Set getScanners();

   boolean shouldExpandSuperTypes();

   Serializer getSerializer();
}
