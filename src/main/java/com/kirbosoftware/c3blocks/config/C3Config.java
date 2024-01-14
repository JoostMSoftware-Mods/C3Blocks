package com.kirbosoftware.c3blocks.config;

import com.kirbosoftware.c3blocks.C3;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.Identifier;

import java.util.List;

@Config(name = C3.MODID)
public class C3Config implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 3)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 9)
    public static int MaxCompressionLevel = 9;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public static List<String> COMPRESSED_BLOCKS = List.of(
            "minecraft:stone",
            "minecraft:dirt",
            "minecraft:sand",
            "minecraft:diorite",
            "minecraft:andesite",
            "minecraft:tuff",
            "minecraft:deepslate",
            "minecraft:granite"
    );

    @Override
    public void validatePostLoad() throws ValidationException {
        if (MaxCompressionLevel < 1 || MaxCompressionLevel > 9) {
            throw new ValidationException("MaxCompressionLevel must be between 1 and 9");
        }

        COMPRESSED_BLOCKS.forEach(s -> {
            if (!Identifier.isValid(s)) {
                throw new RuntimeException("The given entry is not valid");
            }
        });
    }
}
