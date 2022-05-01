package com.github.shimada.groopi;

import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroopiExpansionTests {

    private static final String PLAYER_NAME = "Notch";
    private static final UUID PLAYER_UUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5");

    @Test
    public void testOnRequest() {
        GroopiExpansion expansion = new GroopiExpansion();

        OfflinePlayer player = mock(OfflinePlayer.class);
        when(player.getName()).thenReturn(PLAYER_NAME);
        when(player.getUniqueId()).thenReturn(PLAYER_UUID);

        // normal
        assertEquals("4", expansion.onRequest(player, "2 * 2"));
        assertEquals("200", expansion.onRequest(player, "2 * 100"));
        assertEquals("%", expansion.onRequest(player, "\"\\p\""));
        assertEquals("null", expansion.onRequest(player, "null"));

        // player variable
        assertEquals(PLAYER_NAME, expansion.onRequest(player, "player.name"));
        assertEquals(PLAYER_UUID.toString(), expansion.onRequest(player, "player.uniqueId"));
    }
}
