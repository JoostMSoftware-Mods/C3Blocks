package com.joostmsoftware.c3blocks.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

import java.util.List;

@SuppressWarnings("unused")
@ConfigEntries(includeAll = true)
public class C3Config extends Config {
    public C3Config() {
        super(ConfigOptions.mod("c3blocks"));
    }

    @ConfigEntry(comment = "Adjust here your blocks you want to add/remove using this config. Modded blocks supported")
    public static List<String> COMPRESSED_BLOCKS = List.of(
            "minecraft:stone",
            "minecraft:dirt",
            "minecraft:grass_block"
    );
}
