package com.joostmsoftware.c3blocks;

import com.joostmsoftware.c3blocks.config.C3Config;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class C3Util {

    @Getter
    private static final List<String> completeC3BlockEntries = new ArrayList<>();

    public List<String> getCompressedBlockEntries() {
        return C3Config.COMPRESSED_BLOCKS;
    }

    public static void addEntry(String entry) {
        completeC3BlockEntries.add(entry);
    }

}
