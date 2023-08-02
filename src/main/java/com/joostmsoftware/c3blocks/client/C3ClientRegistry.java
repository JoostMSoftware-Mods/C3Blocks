package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.C3Util;
import com.joostmsoftware.c3blocks.config.C3Config;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

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
            }
        }
        blockList.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
    }
}
