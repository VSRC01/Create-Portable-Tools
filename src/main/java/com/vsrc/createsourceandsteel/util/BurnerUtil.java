package com.vsrc.createsourceandsteel.util;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.blocks.BaseBurnerBlock;
import com.vsrc.createsourceandsteel.registry.BlockRegistry;
import com.vsrc.createsourceandsteel.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BurnerUtil {

    public static List<Block> getBurners() {
        List<Block> burners = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> blockRegistryObject : BlockRegistry.BLOCKS.getEntries()) {
            burners.add(blockRegistryObject.get());
        }
        return burners;
    }

    public static List<ItemStack> getBurnerStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (Block burner : getBurners()) {
            stacks.add(burner.asItem().getDefaultInstance());
        }
        return stacks;
    }

    public static BlockState getBurnerState(Block block, HeatLevel level) {
        if(block instanceof BaseBurnerBlock burner) {
            return burner.getState(level);
        }
        return null;
    }

    public static int getColor(HeatLevel level) {
        return switch (level) {
            case NONE, SMOULDERING -> 0xffffff;
            case FADING, KINDLED -> 0xcb3d07;
            case SEETHING -> 0x3a9af7;
        };
    }
}
