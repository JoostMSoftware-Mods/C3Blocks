package com.joostmsoftware.c3blocks;

import com.joostmsoftware.c3blocks.config.C3Config;
import net.devtech.arrp.json.blockstate.JState;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class C3Registry {

    public static void register() {
        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            String[] parts = C3Config.COMPRESSED_BLOCKS.get(i).split(":");
            String identifier = parts[0];
            String path = parts[1];
            Identifier id = Identifier.of(identifier, path);
            Block block = Registries.BLOCK.get(id);
            String modelId = identifier + ":block/" + path;

            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + path + '_' + j;

                Block variant = new Block(FabricBlockSettings.copyOf(block));
                BlockItem blockItemVariant = new BlockItem(variant, new Item.Settings());

                Registry.register(Registries.BLOCK, new Identifier(C3.getModid(), newPath), variant);
                Registry.register(Registries.ITEM, new Identifier(C3.getModid(), newPath), blockItemVariant);
                C3Util.addEntry(newPath);

                C3.RESOURCE_PACK.addBlockState(JState.state(JState.variant(JState.model(modelId))), C3.ID(newPath));
            }
        }
    }
}
