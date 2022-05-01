package com.github.shimada.groopi;

import groovy.lang.GroovyShell;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class GroopiExpansion extends PlaceholderExpansion {

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        ImportCustomizer importCustomizer = new ImportCustomizer();
        importCustomizer.addStarImports("org.bukkit");

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.addCompilationCustomizers(importCustomizer);

        GroovyShell shell = new GroovyShell(compilerConfiguration);
        shell.setVariable("player", player);
        shell.setVariable("resolver", new PlaceholderResolver(player));
        Object result = shell.evaluate(params.replace("\\p", "%"));
        if (result == null) {
            return "null";
        } else {
            return result.toString();
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
        return "1.0.0";
    }
}
