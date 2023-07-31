package com.joostmsoftware.c3blocks;

import com.joostmsoftware.c3blocks.config.C3Config;
import lombok.Getter;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C3 implements ModInitializer {
	@Getter
	private static final String modid = "c3blocks";
	public static final String NAMESPACE = "C3Blocks";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Getter
	private static final C3Config config = new C3Config();


	public static Identifier ID(String path) {
		return new Identifier(modid, path);
	}

	public static RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(modid);
	public static ItemGroup GROUP = FabricItemGroup.builder(ID("group")).entries((displayContext, entries) -> {

	}).build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		config.load();

		C3Registry.register();

		RRPCallback.AFTER_VANILLA.register(resources -> resources.add(RESOURCE_PACK));
	}
}
