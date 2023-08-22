package com.joostmsoftware.c3blocks;

import com.joostmsoftware.c3blocks.config.C3Config;
import com.joostmsoftware.c3blocks.registry.C3Registry;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.brrp.v1.api.RuntimeResourcePack;

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

	public static RuntimeResourcePack PACK = RuntimeResourcePack.create(ID("pack"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		config.load();

		C3Registry.registerCommon();

//		PACK.setSidedRegenerationCallback(ResourceType.SERVER_DATA, () -> {
//			PACK.clearResources(ResourceType.SERVER_DATA);
//		});


		LOGGER.info("Loaded all common entries! Have fun with C3");
	}
}
