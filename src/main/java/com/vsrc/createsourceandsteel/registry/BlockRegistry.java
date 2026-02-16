package com.vsrc.createsourceandsteel.registry;

import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.blocks.SourceBurnerBlock;
import com.vsrc.createsourceandsteel.blocks.entity.SourceBurnerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateSourceAndSteel.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateSourceAndSteel.MODID);

    public static final DeferredBlock<SourceBurnerBlock> SOURCE_BURNER =
            BLOCKS.registerBlock("source_burner", SourceBurnerBlock::new, Blocks.IRON_BLOCK.properties());

    public static final Supplier<BlockEntityType<SourceBurnerBlockEntity>> SOURCE_BURNER_ENTITY =
            BLOCK_ENTITY_TYPES.register("source_burner", () -> Builder.of(SourceBurnerBlockEntity::new, SOURCE_BURNER.get()).build(null)
            );


}