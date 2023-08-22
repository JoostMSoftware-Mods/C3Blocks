package com.joostmsoftware.c3blocks.client;

import com.joostmsoftware.c3blocks.C3;
import com.joostmsoftware.c3blocks.util.C3Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import pers.solid.brrp.v1.fabric.api.RRPCallback;

import java.util.List;

public class C3Client implements ClientModInitializer {

    private static final List<Block> blockList = C3Util.getBlockEntries();
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, C3.ID("group"));

    @Override
    public void onInitializeClient() {
        C3ClientRegistry.registerClient();
        RRPCallback.AFTER_VANILLA.register(resources -> resources.add(C3.PACK));

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.c3blocks.group"))
                .icon(() -> new ItemStack(blockList.get(0)))
                .entries((context, entries) -> blockList.forEach(block -> entries.add(block.asItem()))).build());

        C3.LOGGER.info("Loaded all client entries!");
    }
}
