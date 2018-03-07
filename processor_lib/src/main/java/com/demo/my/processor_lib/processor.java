package com.demo.my.processor_lib;

import com.demo.my.annotation_lib.version;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by lenovo on 2018/3/7.
 */

@AutoService(Processor.class)
public class processor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private SourceVersion sourceVersion;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        sourceVersion = processingEnvironment.getSourceVersion();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return super.getSupportedSourceVersion();
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
        Set<String> types = new HashSet<>();
        types.add(version.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty())
            return false;
        Set<? extends Element> versionSet = roundEnvironment.getElementsAnnotatedWith(version.class);
        if (versionSet.isEmpty())
            return false;
        Element next = versionSet.iterator().next();
        version annotation = next.getAnnotation(version.class);

//        StringBuilder sb = new StringBuilder();
//        sb.append("com.demo.my.myapplication;\n")
//                .append("public class Generated {\n" +
//                        "  public  String getVer() {\n" +
//                        "        return \"versionName\";\n" + annotation.name() + "\t ver: " + annotation.ver() + "" +
//                        "    }\n" +
//                        "}");
//        try {
//            JavaFileObject classFile = filer.createClassFile("version");
//            Writer writer = classFile.openWriter();
//            writer.append(sb.toString());
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Processor" + sb.toString());
//        debug(sb.toString());

        MethodSpec.Builder initBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.get(String.class));
//                .addParameter(HashMap.class, "providerMap")
//                .addParameter(HashMap.class, "actionMap")
//                .addStatement("ProviderMappingInit.init(providerMap, actionMap)");
//        for (String moduleName : modules) {
//            initBuilder.addStatement("ProviderMappingInit_" + moduleName + ".init(providerMap, actionMap)");
//        }

        initBuilder.addStatement("return \"versionName:" + annotation.name() +
                ";ver: " + annotation.ver() + "" + "\"");

        TypeSpec providerInit = TypeSpec.classBuilder("ProviderInit")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(initBuilder.build())
                .build();
        try {
            JavaFile.builder("com.demo.my.myapplication", providerInit)
                    .build()
                    .writeTo(filer);
        } catch (Throwable e) {
            e.printStackTrace();
        }
//        messager.printMessage(Diagnostic.Kind.ERROR, String.format("versionName : %s,ver: %s",
//                annotation.name(), annotation.ver() + ""));
        return true;
    }

    private void debug(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
