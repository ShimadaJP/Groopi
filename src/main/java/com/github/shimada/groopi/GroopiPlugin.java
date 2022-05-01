package com.github.shimada.groopi;

import org.bukkit.plugin.java.JavaPlugin;

public class GroopiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new GroopiExpansion().register();
    }
}
