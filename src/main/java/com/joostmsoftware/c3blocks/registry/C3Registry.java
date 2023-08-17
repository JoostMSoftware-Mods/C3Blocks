package com.joostmsoftware.c3blocks.registry;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.block.CompressedPillarBlock;
import com.joostmsoftware.c3blocks.item.CompressedBlockItem;
import com.joostmsoftware.c3blocks.util.C3Util;
import com.joostmsoftware.c3blocks.block.CompressedBlock;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class C3Registry {

    public static void registerCommon() {

        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            Identifier id = new Identifier(C3Config.COMPRESSED_BLOCKS.get(i));
            Block block = Registry.BLOCK.get(id);
            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + id.getPath() + '_' + j;
                Block variant;
                if (block instanceof PillarBlock) {
                    variant = new CompressedPillarBlock(FabricBlockSettings.copyOf(block), j - 1);
                } else {
                    variant = new CompressedBlock(FabricBlockSettings.copyOf(block), j - 1);
                }
                C3Util.addEntry(variant);
                Registry.register(Registry.BLOCK, new Identifier(C3.getModid(), newPath), variant);
                BlockItem blockItem = new CompressedBlockItem(variant, new Item.Settings(), j, block);
                Registry.register(Registry.ITEM, new Identifier(C3.getModid(), newPath), blockItem);

                // Runtime recipes
                if (j == 1) {
                    C3.RESOURCE_PACK.addRecipe(C3.ID(newPath), JRecipe.shaped(
                            JPattern.pattern("XXX", "XXX", "XXX"),
                            JKeys.keys().key("X", JIngredient.ingredient().item(block.asItem())),
                            JResult.item(blockItem)
                    ));
                } else {
                    String neededMat = "compressed_" + id.getPath() + '_' + (j- 1) ;
                    Block temp = Registry.BLOCK.get(C3.ID(neededMat));

                    C3.RESOURCE_PACK.addRecipe(C3.ID(newPath), JRecipe.shaped(
                            JPattern.pattern("XXX", "XXX", "XXX"),
                            JKeys.keys().key("X", JIngredient.ingredient().item(temp.asItem())),
                            JResult.item(blockItem)
                    ));
                }

                if (j == 1) {
                    C3.RESOURCE_PACK.addRecipe(C3.ID(newPath + "_to_" + id.getPath()), JRecipe.shapeless(
                            JIngredients.ingredients().add(JIngredient.ingredient().item(blockItem)),
                            JResult.itemStack(block.asItem(), 9)
                    ));
                } else {
                    String neededMat = "compressed_" + id.getPath() + '_' + (j - 1);
                    Block temp = Registry.BLOCK.get(C3.ID(neededMat));

                    C3.RESOURCE_PACK.addRecipe(C3.ID(newPath + "_to_" + neededMat), JRecipe.shapeless(
                            JIngredients.ingredients().add(JIngredient.ingredient().item(blockItem)),
                            JResult.itemStack(temp.asItem(), 9)
                    ));
                }

                // Runtime block drops aka lootdrops
                C3.RESOURCE_PACK.addLootTable(C3.ID(newPath), JLootTable
                        .loot("minecraft:block")
                        .pool(JLootTable.pool().rolls(1)
                                .entry(
                                        new JEntry().type("minecraft:item").name(C3.ID(newPath).toString())
                                )
                                .condition(
                                        new JCondition().condition("minecraft:survives_explosion")
                                )
                        )
                );
            }
        }
    }
}
