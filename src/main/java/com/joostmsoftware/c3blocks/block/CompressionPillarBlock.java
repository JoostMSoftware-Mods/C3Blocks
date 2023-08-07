package com.joostmsoftware.c3blocks.block;

import net.minecraft.block.PillarBlock;

public class CompressionPillarBlock extends PillarBlock implements ICompression {
    private final int compressionLevel;

    public CompressionPillarBlock(Settings settings, int compressionLevel) {
        super(settings);
        this.compressionLevel = compressionLevel;
    }

    @Override
    public int getCompressionLevel() {
        return compressionLevel;
    }
}
