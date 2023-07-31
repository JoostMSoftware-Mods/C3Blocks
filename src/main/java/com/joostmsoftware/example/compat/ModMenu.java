package com.joostmsoftware.example.compat;

import com.joostmsoftware.example.ExampleMod;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.lortseam.completeconfig.gui.cloth.ClothConfigScreenBuilder;

public class ModMenu implements ModMenuApi {
    private static final ClothConfigScreenBuilder builder = new ClothConfigScreenBuilder();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> builder.build(parent, ExampleMod.getConfig());
    }
}
