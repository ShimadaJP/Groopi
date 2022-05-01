package com.github.shimada.groopi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

public class PlaceholderResolver {

    private final OfflinePlayer player;

    public PlaceholderResolver(OfflinePlayer player) {
        this.player = player;
    }

    public String resolve(String str) {
        return PlaceholderAPI.setPlaceholders(player, "%" + str + "%");
    }
}
