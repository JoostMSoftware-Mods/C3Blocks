package com.kirbosoftware.c3blocks.util;

import com.kirbosoftware.c3blocks.block.CompressionBlock;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class C3Util {

    public static List<CompressionBlock> blockEntries = new ArrayList<>();

    public static void addEntry (CompressionBlock block) {blockEntries.add(block);}

    public static List<CompressionBlock> getBlockEntries() {
        return blockEntries;
    }
}
