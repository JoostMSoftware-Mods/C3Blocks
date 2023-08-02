package com.joostmsoftware.c3blocks;

import lombok.Getter;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class C3Util {

    @Getter
    public static List<Block> blockEntries = new ArrayList<>();

    public static void addEntry (Block block) {blockEntries.add(block);}
}
