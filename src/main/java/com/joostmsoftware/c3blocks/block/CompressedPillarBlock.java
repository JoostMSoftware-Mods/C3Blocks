package com.joostmsoftware.c3blocks.block;

import net.minecraft.block.PillarBlock;

public class CompressedPillarBlock extends PillarBlock implements ICompressed{
    private final int compressionLevel;
    public CompressedPillarBlock(Settings settings, int compressionLevel) {
        super(settings);
        this.compressionLevel = compressionLevel;
    }

    @Override
    public int getCompressedTier() {
        return this.compressionLevel;
    }
}
