package com.kirbosoftware.c3blocks;

import com.kirbosoftware.c3blocks.config.C3Config;
import com.kirbosoftware.c3blocks.registry.C3Registry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C3 implements ModInitializer {

	public static final String MODID = "c3blocks";
	public static final String NAMESPACE = "C3Blocks";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	public static String getModid() {
		return MODID;
	}

	public static Identifier ID(String path) {
		return new Identifier(MODID, path);
	}

	public static RuntimeResourcePack PACK = RuntimeResourcePack.create(ID("pack"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		AutoConfig.register(C3Config.class, Toml4jConfigSerializer::new);
		C3Registry.registerCommon();

		C3.PACK.dump();
		LOGGER.info("Loaded all common entries! Have fun with C3");
	}
}
