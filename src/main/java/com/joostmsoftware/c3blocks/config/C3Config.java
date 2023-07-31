package com.joostmsoftware.c3blocks.config;

import com.joostmsoftware.c3blocks.C3;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

import java.util.List;

@SuppressWarnings("unused")
@ConfigEntries(includeAll = true)
public class C3Config extends Config {
    public C3Config() {
        super(ConfigOptions.mod(C3.getModid()));
    }

    @ConfigEntry(comment = """
            Set here your max compression level you want.\s
            Min value is 1 and max value is 9.\s
            These values can be changed upon feedback from users.\s
            """)
    @ConfigEntry.BoundedInteger(min = 1, max = 9)
    public static int MaxCompressionLevel = 9;

    @ConfigEntry(comment = """
            Adjust here your blocks you want to add/remove using this config.\s
            Modded blocks supported, just add them here.\s
            """)
    public static List<String> COMPRESSED_BLOCKS = List.of(
            "minecraft:stone",
            "minecraft:dirt",
            "minecraft:grass_block"
    );
}
