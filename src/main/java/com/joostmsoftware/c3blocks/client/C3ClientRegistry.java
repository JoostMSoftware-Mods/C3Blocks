package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.util.C3Util;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.brrp.v1.api.LanguageProvider;
import pers.solid.brrp.v1.model.ModelJsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C3ClientRegistry {

    private static final List<Block> blockList = C3Util.getBlockEntries();
    private static final Map<String, String> langMap = new HashMap<>();

    public static void registerClient() {
        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            Identifier id = new Identifier(C3Config.COMPRESSED_BLOCKS.get(i));
            Block block = Registry.BLOCK.get(id);
            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + id.getPath() + '_' + j;
                String newModelId = C3.getModid() + ":block/" + newPath;
                if (block instanceof PillarBlock) {
                    String parentString = "block/" + "compressed_log";
                    C3.PACK.addModel(
                            C3.ID("block/" + newPath),
                            ModelJsonBuilder.create(C3.ID(newPath))
                                    .parent(C3.ID(parentString))
                                    .addTexture("side", id.getNamespace() + ":block/" + id.getPath())
                                    .addTexture("overlay", "c3blocks:block/compressed_overlay_" + j)
                                    .addTexture("end", id.getNamespace() + ":block/" + id.getPath() + "_top")
                    );

                    C3.PACK.addModel(
                            C3.ID("block/" + newPath + "_horizontal"),
                            ModelJsonBuilder.create(C3.ID(newPath + "_horizontal"))
                                    .parent(C3.ID(parentString + "_horizontal"))
                                    .addTexture("side", id.getNamespace() + ":block/" + id.getPath())
                                    .addTexture("overlay", "c3blocks:block/compressed_overlay_" + j)
                                    .addTexture("end", id.getNamespace() + ":block/" + id.getPath() + "_top")
                    );

                    C3.PACK.addBlockState(
                            C3.ID(newPath),
                            BlockStateModelGenerator.createAxisRotatedBlockState(block, C3.ID("block/" + newPath), C3.ID("block/" + newPath + "_horizontal")));
                } else {
                    C3.PACK.addModel(C3.ID("block/" + newPath), ModelJsonBuilder.create(
                            C3.ID(newPath))
                            .parent(C3.ID("block/" + "default_compressed"))
                            .addTexture("all", id.getNamespace() + ":block/" + id.getPath())
                            .addTexture("overlay", "c3blocks:block/compressed_overlay_" + j)
                    );
                    C3.PACK.addBlockState(C3.ID(newPath), BlockStateModelGenerator.createSingletonBlockState(block, C3.ID("block/" + newPath)));
                }
                C3.PACK.addModel(C3.ID("item/" + newPath), ModelJsonBuilder.create(C3.ID("block/" + newPath)));
                langMap.put("block.c3blocks." + newPath, capitalizeString(newPath).replaceAll("[^a-zA-Z0-9]", " ") + "x");
            }
        }
        langMap.put("itemGroup.c3blocks.group", "C3 Blocks");
        langMap.put("config.c3blocks.MaxCompressionLevel", "Max Compression Level");
        langMap.put("config.c3blocks.COMPRESSED_BLOCKS", "Compressed Blocks Entries");
        langMap.put("item.c3blocks.tooltip", "%s of %s blocks");
        C3.PACK.addLang(C3.ID("en_us"), LanguageProvider.create(langMap));
        blockList.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
        blockList.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
    }

    /**
     * A method to capitalize a string and remove anything what's between it.
     * <a href="https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string">Source</a>
     * @param string the string you want to capitalize.
     * @return a capitalised string.
     */
    private static @NotNull String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '_') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
