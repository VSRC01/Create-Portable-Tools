package com.vsrc.createsourceandsteel.util;

import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> DRILL_MINEABLE = createTag("drill_mineable");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CreateSourceAndSteel.MODID, name));
        }
    }
}
