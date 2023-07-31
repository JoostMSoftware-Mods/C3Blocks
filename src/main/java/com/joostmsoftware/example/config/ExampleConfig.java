package com.joostmsoftware.example.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

@SuppressWarnings("unused")
@ConfigEntries(includeAll = true)
public class ExampleConfig extends Config {
    public ExampleConfig() {
        super(ConfigOptions.mod("modid"));
    }

    @ConfigEntry(comment = "Test entry")
    public static Boolean TOGGLE_TEST_ENTRY = false;
}
