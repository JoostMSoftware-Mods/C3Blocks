package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.util.C3Util;
import net.devtech.arrp.api.RRPCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;

public class C3Client implements ClientModInitializer {
    private static final List<Block> blockList = C3Util.getBlockEntries();

    @Override
    public void onInitializeClient() {
        C3ClientRegistry.registerClient();
        RRPCallback.BEFORE_VANILLA.register(resources -> resources.add(C3.RESOURCE_PACK));

        FabricItemGroupBuilder.create(C3.ID("group"))
                .icon(()-> new ItemStack(blockList.get(0)))
                .appendItems(itemStacks -> blockList.forEach(block -> itemStacks.add(block.asItem().getDefaultStack())))
                .build();

        C3.LOGGER.info("Loaded all client entries!");
    }
}
