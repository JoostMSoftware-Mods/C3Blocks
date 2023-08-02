package com.joostmsoftware.c3blocks;

import com.joostmsoftware.c3blocks.block.CompressedBlock;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class C3Registry {

    public static void registerCommon() {

        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            Identifier id = new Identifier(C3Config.COMPRESSED_BLOCKS.get(i));
            Block block = Registries.BLOCK.get(id);
            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + id.getPath() + '_' + j;
                Block variant = new CompressedBlock(FabricBlockSettings.copyOf(block), j - 1);
                C3Util.addEntry(variant);
                Registry.register(Registries.BLOCK, new Identifier(C3.getModid(), newPath), variant);
                Item blockItem = new BlockItem(variant, new Item.Settings());
                Registry.register(Registries.ITEM, new Identifier(C3.getModid(), newPath), blockItem);
            }
        }
    }
}
