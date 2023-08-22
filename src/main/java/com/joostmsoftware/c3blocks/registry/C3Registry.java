package com.joostmsoftware.c3blocks.registry;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.block.CompressedPillarBlock;
import com.joostmsoftware.c3blocks.item.CompressedBlockItem;
import com.joostmsoftware.c3blocks.util.C3Util;
import com.joostmsoftware.c3blocks.block.CompressedBlock;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;

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
                    C3.PACK.addRecipe(
                            C3.ID(newPath),
                            new ShapedRecipeJsonBuilder.ShapedRecipeJsonProvider(
                                  C3.ID(newPath),
                                  blockItem,
                                  1,
                                  "",
                                    List.of("XXX", "XXX", "XXX"),
                                    Map.of('X', Ingredient.ofItems(block.asItem())),
                                    Advancement.Builder.create().criterion("has_block", new AdvancementCriterion()),
                                    C3.ID("has_block")
                            )
                    );
                } else {
                    String neededMat = "compressed_" + id.getPath() + '_' + (j- 1) ;
                    Block temp = Registry.BLOCK.get(C3.ID(neededMat));

                    C3.PACK.addRecipe(
                            C3.ID(newPath),
                            new ShapedRecipeJsonBuilder.ShapedRecipeJsonProvider(
                                    C3.ID(newPath),
                                    blockItem,
                                    1,
                                    "",
                                    List.of("XXX", "XXX", "XXX"),
                                    Map.of('X', Ingredient.ofItems(temp.asItem())),
                                    Advancement.Builder.create().criterion("has_block", new AdvancementCriterion()),
                                    C3.ID("has_block")
                            )
                    );
                }

                if (j == 1) {
                    C3.PACK.addRecipe(C3.ID(
                            newPath + "to_" + id.getPath()),
                            new ShapelessRecipeJsonBuilder.ShapelessRecipeJsonProvider(
                                    C3.ID(newPath + "_to_" + id.getPath()),
                                    block.asItem(),
                                    9,
                                    "",
                                    List.of(Ingredient.ofItems(blockItem)),
                                    Advancement.Builder.create().criterion("has_block", new AdvancementCriterion()),
                                    C3.ID("has_block")
                            ));
                } else {
                    String neededMat = "compressed_" + id.getPath() + '_' + (j - 1);
                    Block temp = Registry.BLOCK.get(C3.ID(neededMat));

                    C3.PACK.addRecipe(C3.ID(
                                    newPath + "to_" + id.getPath()),
                            new ShapelessRecipeJsonBuilder.ShapelessRecipeJsonProvider(
                                    C3.ID(newPath + "_to_" + id.getPath()),
                                    temp.asItem(),
                                    9,
                                    "",
                                    List.of(Ingredient.ofItems(blockItem)),
                                    Advancement.Builder.create().criterion("has_block", new AdvancementCriterion()),
                                    C3.ID("has_block")
                            ));
                }

//                // Runtime block drops aka lootdrops
                C3.PACK.addLootTable(
                        C3.ID(newPath),
                        FabricBlockLootTableProvider.drops(variant));
//                C3.RESOURCE_PACK.addLootTable(C3.ID(newPath), JLootTable
//                        .loot("minecraft:block")
//                        .pool(JLootTable.pool().rolls(1)
//                                .entry(
//                                        new JEntry().type("minecraft:item").name(C3.ID(newPath).toString())
//                                )
//                                .condition(
//                                        new JCondition().condition("minecraft:survives_explosion")
//                                )
//                        )
//                );
            }
        }
    }
}
