package com.kirbosoftware.c3blocks.block;

import net.minecraft.block.Block;

public class CompressionBlock extends Block implements ICompression {
    private final int compressionTier;

    public CompressionBlock(Settings settings, int CompressionTier) {
        super(settings);
        compressionTier = CompressionTier;
    }

    @Override
    public int getCompressionLevel() {
        return compressionTier;
    }
}
