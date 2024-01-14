package com.kirbosoftware.c3blocks.client;

import com.kirbosoftware.c3blocks.C3;
import com.kirbosoftware.c3blocks.block.CompressionBlock;
import com.kirbosoftware.c3blocks.block.CompressionPillarBlock;
import com.kirbosoftware.c3blocks.util.C3Util;
import com.kirbosoftware.c3blocks.config.C3Config;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.*;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class C3ClientRegistry {
    private static final List<CompressionBlock> blockList = C3Util.getBlockEntries();
    private static final JLang jLang = new JLang();

    public static void registerClient() {
        for (int i = 0; i < C3Config.COMPRESSED_BLOCKS.size(); i++) {
            Identifier id = new Identifier(C3Config.COMPRESSED_BLOCKS.get(i));
            Block block = Registries.BLOCK.get(id);
            for (int j = 1; j < C3Config.MaxCompressionLevel + 1; j++) {
                String newPath = "compressed_" + id.getPath() + '_' + j;
                String newModelId = C3.getModid() + ":block/" + newPath;
                generateTextures(C3.PACK, new Identifier(id.getNamespace(), "textures/block/" + id.getPath() + ".png"), id.getPath(), j);

                // @TODO add pillar blocks back and rework this

                C3.PACK.addModel(JModel.model().parent("minecraft:block/cube_all")
                        .textures(JModel.textures().var("all", "c3blocks:block/compressed_" + id.getPath() + "_" + j)
                                .var("particle", id.getNamespace() + ":block/" + id.getPath())
                        ), prefixPath(C3.ID(newPath), "block"));

                C3.PACK.addBlockState(JState.state(JState.variant(JState.model(newModelId))), C3.ID(newPath));
                C3.PACK.addModel(JModel.model(prefixPath(C3.ID(newPath), "block")), prefixPath(C3.ID(newPath), "item"));
                jLang.block(C3.ID(newPath), capitalizeString(newPath).replaceAll("[^a-zA-Z0-9]", " ") + "x");
            }
        }
        jLang.entry("itemGroup.c3blocks.group", "C3 Blocks");
        jLang.entry("config.c3blocks.MaxCompressionLevel", "Max Compression Level");
        jLang.entry("config.c3blocks.COMPRESSED_BLOCKS", "Compressed Blocks Entries");
        jLang.entry("item.c3blocks.tooltip", "%s of %s blocks");
        jLang.entry("text.autoconfig.C3Blocks.option.COMPRESSED_BLOCKS", "Compressed blocks entries");
        jLang.entry("text.autoconfig.C3Blocks.option.COMPRESSED_BLOCKS.@Tooltip[0]", "Adjust here your blocks you want to add/remove using this config.");
        jLang.entry("text.autoconfig.C3Blocks.option.COMPRESSED_BLOCKS.@Tooltip[1]", "Modded blocks supported, just add them here.");
        jLang.entry("text.autoconfig.C3Blocks.option.COMPRESSED_BLOCKS.@Tooltip[2]", "Make sure to follow this format: `modid:block`.");
        jLang.entry("text.autoconfig.C3Blocks.option.MaxCompressionLevel", "Max compression level");
        jLang.entry("text.autoconfig.C3Blocks.option.MaxCompressionLevel.@Tooltip[0]", "Set here your max compression level you want.");
        jLang.entry("text.autoconfig.C3Blocks.option.MaxCompressionLevel.@Tooltip[1]", "Min value is 1 and max value is 9.");
        jLang.entry("text.autoconfig.C3Blocks.option.MaxCompressionLevel.@Tooltip[2]", "These values can be changed upon feedback from users.");
        C3.PACK.addLang(C3.ID("en_us"), jLang);

        blockList.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
    }

    /**
     * A method to capitalize a string and remove anything what's between it.
     * <a href="https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string">Source</a>
     *
     * @param string the string you want to capitalize.
     * @return a capitalised string.
     */
    private static @NotNull String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '_') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    private static Identifier prefixPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + '/' + identifier.getPath());
    }

    private static BufferedImage getImage(Identifier texture) {
        MinecraftClient client = MinecraftClient.getInstance();
        ResourceManager manager = client.getResourceManager();
        Optional<Resource> resOpt = manager.getResource(texture);
        NativeImage natImg;
        try {
            natImg = NativeImage.read(resOpt.get().getInputStream().readAllBytes());
            if (natImg.getFormat() != NativeImage.Format.RGBA) {
                throw new Exception("fck"); // So it will default to the missingno texture in the catch block
            }
        } catch (Exception e) {
            e.printStackTrace();
            SpriteContents spriteContents = MissingSprite.createSpriteContents();
            natImg = spriteContents.image;
        }

        int iw = natImg.getWidth(), ih = natImg.getHeight();
        BufferedImage bufferedImg = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < iw; x++) {
            for (int y = 0; y < ih; y++) {
                // Convert the color from ABGR to ARGB
                int color = natImg.getColor(x, y);
                int r = (color >> 0) & 255;
                int g = (color >> 8) & 255;
                int b = (color >> 16) & 255;
                int a = (color >> 24) & 255;
                color = (a << 24) + (r << 16) + (g << 8) + (b << 0);
                bufferedImg.setRGB(x, y, color);
            }
        }
        return bufferedImg;
    }

    private static void generateTextures(RuntimeResourcePack rrp, Identifier baseId, String name, int tier) {
        Identifier texture = new Identifier("c3blocks", "textures/block/compressed_" + name + "_" + tier + ".png");
        rrp.addLazyResource(ResourceType.CLIENT_RESOURCES, texture, (pack, id) -> {
            BufferedImage base = getImage(baseId);
            BufferedImage overlay = getImage(new Identifier("c3blocks", "textures/block/compressed_overlay_" + tier + ".png"));

            int iw = Math.max(base.getWidth(), overlay.getWidth());
            int ih = Math.max(base.getHeight(), overlay.getHeight());
            BufferedImage result = new BufferedImage(iw, ih, BufferedImage.TYPE_4BYTE_ABGR);

            Graphics gfx = result.getGraphics();
            gfx.drawImage(base, 0, 0, iw, ih, null);
            gfx.drawImage(overlay, 0, 0, iw, ih, null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(result, "png", baos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return baos.toByteArray();
        });
    }
}
