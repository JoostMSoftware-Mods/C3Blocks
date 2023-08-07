package com.joostmsoftware.c3blocks.block;

import net.minecraft.block.Block;

public class CompressedBlock extends Block implements ICompression{
    private final int compressionTier;

    public CompressedBlock(Settings settings, int CompressionTier) {
        super(settings);
        compressionTier = CompressionTier;
    }

    @Override
    public int getCompressionLevel() {
        return compressionTier;
    }
}
