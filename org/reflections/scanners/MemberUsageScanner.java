package org.reflections.scanners;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import org.reflections.ReflectionsException;
import org.reflections.Store;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.Utils;

public class MemberUsageScanner extends AbstractScanner {
   // $FF: synthetic field
   private ClassPool classPool;

   private ClassPool getClassPool() {
      if (this.classPool == null) {
         synchronized(this) {
            this.classPool = new ClassPool();
            ClassLoader[] var2 = this.getConfiguration().getClassLoaders();
            if (var2 == null) {
               var2 = ClasspathHelper.classLoaders();
            }

            ClassLoader[] var3 = var2;
            int var4 = var2.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ClassLoader var6 = var3[var5];
               this.classPool.appendClassPath(new LoaderClassPath(var6));
            }
         }
      }

      return this.classPool;
   }

   void scanMember(CtBehavior var1, final Store var2) throws CannotCompileException {
      final String var3 = String.valueOf((new StringBuilder()).append(var1.getDeclaringClass().getName()).append(".").append(var1.getMethodInfo().getName()).append("(").append(this.parameterNames(var1.getMethodInfo())).append(")"));
      var1.instrument(new ExprEditor() {
         public void edit(ConstructorCall var1) throws CannotCompileException {
            try {
               MemberUsageScanner.this.put(var2, String.valueOf((new StringBuilder()).append(var1.getConstructor().getDeclaringClass().getName()).append(".<init>(").append(MemberUsageScanner.this.parameterNames(var1.getConstructor().getMethodInfo())).append(")")), var1.getLineNumber(), var3);
            } catch (NotFoundException var4) {
               throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Could not find member ").append(var1.getClassName()).append(" in ").append(var3)), var4);
            }
         }

         public void edit(FieldAccess var1) throws CannotCompileException {
            try {
               MemberUsageScanner.this.put(var2, String.valueOf((new StringBuilder()).append(var1.getField().getDeclaringClass().getName()).append(".").append(var1.getFieldName())), var1.getLineNumber(), var3);
            } catch (NotFoundException var3x) {
               throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Could not find member ").append(var1.getFieldName()).append(" in ").append(var3)), var3x);
            }
         }

         public void edit(NewExpr var1) throws CannotCompileException {
            try {
               MemberUsageScanner.this.put(var2, String.valueOf((new StringBuilder()).append(var1.getConstructor().getDeclaringClass().getName()).append(".<init>(").append(MemberUsageScanner.this.parameterNames(var1.getConstructor().getMethodInfo())).append(")")), var1.getLineNumber(), var3);
            } catch (NotFoundException var3x) {
               throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Could not find new instance usage in ").append(var3)), var3x);
            }
         }

         public void edit(MethodCall var1) throws CannotCompileException {
            try {
               MemberUsageScanner.this.put(var2, String.valueOf((new StringBuilder()).append(var1.getMethod().getDeclaringClass().getName()).append(".").append(var1.getMethodName()).append("(").append(MemberUsageScanner.this.parameterNames(var1.getMethod().getMethodInfo())).append(")")), var1.getLineNumber(), var3);
            } catch (NotFoundException var3x) {
               throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Could not find member ").append(var1.getClassName()).append(" in ").append(var3)), var3x);
            }
         }
      });
   }

   public void scan(Object var1, Store var2) {
      try {
         CtClass var3 = this.getClassPool().get(this.getMetadataAdapter().getClassName(var1));
         CtConstructor[] var4 = var3.getDeclaredConstructors();
         int var5 = var4.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            CtConstructor var7 = var4[var6];
            this.scanMember(var7, var2);
         }

         CtMethod[] var9 = var3.getDeclaredMethods();
         var5 = var9.length;

         for(var6 = 0; var6 < var5; ++var6) {
            CtMethod var10 = var9[var6];
            this.scanMember(var10, var2);
         }

         var3.detach();
      } catch (Exception var8) {
         throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Could not scan method usage for ").append(this.getMetadataAdapter().getClassName(var1))), var8);
      }
   }

   String parameterNames(MethodInfo var1) {
      return Utils.join(this.getMetadataAdapter().getParameterNames(var1), ", ");
   }

   private void put(Store var1, String var2, int var3, String var4) {
      if (this.acceptResult(var2)) {
         this.put(var1, var2, String.valueOf((new StringBuilder()).append(var4).append(" #").append(var3)));
      }

   }
}
