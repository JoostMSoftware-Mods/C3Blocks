package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.util.C3Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import pers.solid.brrp.v1.fabric.api.RRPCallback;

import java.util.List;

public class C3Client implements ClientModInitializer {

    private static final List<Block> blockList = C3Util.getBlockEntries();

    @Override
    public void onInitializeClient() {
        C3ClientRegistry.registerClient();
        RRPCallback.BEFORE_VANILLA.register(resources -> resources.add(C3.PACK));

        ItemGroup GROUP = FabricItemGroup.builder(C3.ID("group"))
                .icon(() -> new ItemStack(blockList.get(2)))
                .entries((displayContext, entries) -> blockList.forEach(block -> entries.add(block.asItem())))
                .build();

        C3.LOGGER.info("Loaded all client entries!");
    }
}
