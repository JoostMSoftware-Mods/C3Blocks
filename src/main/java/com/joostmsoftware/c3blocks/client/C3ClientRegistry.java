package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.C3Util;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class C3ClientRegistry {
    private static Identifier prefixPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + '/' + identifier.getPath());
    }

    private static final List<Block> blockList = C3Util.getBlockEntries();

    public static void registerClient() {
        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            Identifier id = new Identifier(C3Config.COMPRESSED_BLOCKS.get(i));

            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + id.getPath() + '_' + j;
                String newModelId = C3.getModid() + ":block/" + newPath;
                C3.RESOURCE_PACK.addModel(JModel.model().parent(C3.getModid() + ":block/" + "default_compressed")
                        .textures(JModel.textures().var("all", id.getNamespace() + ":block/" + id.getPath())
                                .var("overlay", "c3blocks:block/compressed_overlay_" + j)
                                .particle(id.getNamespace() + ":block/" + id.getPath())
                        ),prefixPath(C3.ID(newPath), "block"));
                C3.RESOURCE_PACK.addModel(JModel.model(prefixPath(C3.ID(newPath), "block")), prefixPath(C3.ID(newPath), "item"));
                C3.RESOURCE_PACK.addBlockState(JState.state(JState.variant(JState.model(newModelId))), C3.ID(newPath));
                C3.RESOURCE_PACK.mergeLang(C3.ID("en_us"),JLang.lang().block(C3.ID(newPath), capitalizeString(newPath).replaceAll("[^a-zA-Z0-9]", " ") + "x"));
            }
        }
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
