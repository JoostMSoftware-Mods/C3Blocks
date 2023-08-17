package com.joostmsoftware.c3blocks.block;

import net.minecraft.block.Block;

public class CompressedBlock extends Block implements ICompressed{
    private final int compressionTier;

    public CompressedBlock(Settings settings, int CompressionTier) {
        super(settings);
        compressionTier = CompressionTier;
    }

    @Override
    public int getCompressedTier() {
        return compressionTier;
    }
}
