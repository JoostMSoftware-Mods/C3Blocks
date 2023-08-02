package com.joostmsoftware.c3blocks.block;

import net.minecraft.block.Block;

public class CompressedBlock extends Block {
    public final int compressionTier;

    public CompressedBlock(Settings settings, int CompressionTier) {
        super(settings);
        compressionTier = CompressionTier;
    }
}
