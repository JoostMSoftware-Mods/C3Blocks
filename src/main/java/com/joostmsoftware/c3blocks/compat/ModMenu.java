package com.joostmsoftware.c3blocks.compat;

import com.joostmsoftware.c3blocks.C3;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.lortseam.completeconfig.gui.cloth.ClothConfigScreenBuilder;

public class ModMenu implements ModMenuApi {
    private static final ClothConfigScreenBuilder builder = new ClothConfigScreenBuilder();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> builder.build(parent, C3.getConfig());
    }
}
