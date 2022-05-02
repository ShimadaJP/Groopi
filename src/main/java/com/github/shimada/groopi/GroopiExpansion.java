package com.github.shimada.groopi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class GroopiExpansion extends PlaceholderExpansion {

    private ClassLoader classLoader;

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        try {
            if (classLoader == null) {
                classLoader = new GroovyDownloader().download();
            }

            // prepare classes and methods
            Class<?> importCustomizerClass = Class.forName("org.codehaus.groovy.control.customizers.ImportCustomizer", true, classLoader);
            Class<?> compilationCustomizerClass = Class.forName("org.codehaus.groovy.control.customizers.CompilationCustomizer", true, classLoader);
            Class<?> compilationCustomizersClass = Class.forName("[Lorg.codehaus.groovy.control.customizers.CompilationCustomizer;", true, classLoader);
            Class<?> compilerConfigurationClass = Class.forName("org.codehaus.groovy.control.CompilerConfiguration", true, classLoader);
            Class<?> shellClass = Class.forName("groovy.lang.GroovyShell", true, classLoader);

            Method addStarImports = importCustomizerClass.getMethod("addStarImports", String[].class);
            Method addCompilationCustomizers = compilerConfigurationClass.getMethod("addCompilationCustomizers", compilationCustomizersClass);
            Method setVariable = shellClass.getMethod("setVariable", String.class, Object.class);
            Method evaluate = shellClass.getMethod("evaluate", String.class);

            // configurations
            Object importCustomizer = importCustomizerClass.getConstructor().newInstance();
            addStarImports.invoke(importCustomizer, (Object) new String[]{"org.bukkit"});

            Object[] compilationCustomizers = (Object[]) Array.newInstance(compilationCustomizerClass, 1);
            compilationCustomizers[0] = importCustomizer;

            Object compilerConfiguration = compilerConfigurationClass.getConstructor().newInstance();
            addCompilationCustomizers.invoke(compilerConfiguration, (Object) compilationCustomizers);

            // shell
            Object shell = shellClass.getConstructor(compilerConfigurationClass).newInstance(compilerConfiguration);
            setVariable.invoke(shell, "player", player);
            setVariable.invoke(shell, "resolver", new PlaceholderResolver(player));
            Object result = evaluate.invoke(shell, params.replace("\\p", "%"));
            if (result == null) {
                return "null";
            } else {
                return result.toString();
            }

        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException("Could not resolve placeholder", e);
            }
        }
    }

    @Override
    public String getIdentifier() {
        return "groopi";
    }

    @Override
    public String getAuthor() {
        return "ShimadaJP";
    }

    @Override
    public String getVersion() {
        return "1.0.3";
    }
}
