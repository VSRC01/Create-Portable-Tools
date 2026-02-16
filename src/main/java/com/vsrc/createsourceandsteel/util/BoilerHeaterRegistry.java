package com.vsrc.createsourceandsteel.util;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.blocks.BaseBurnerBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;


public class BoilerHeaterRegistry {

    public static void registerBoilerHeaters() {
        for (Block block : BurnerUtil.getBurners()) {
            registerBurners(block);
        }
    }

    public static float fromHeatLevel(HeatLevel value) {
        return switch (value) {
            case NONE -> BoilerHeater.NO_HEAT;
            case SMOULDERING -> BoilerHeater.PASSIVE_HEAT;
            case FADING, KINDLED -> 1;
            case SEETHING -> 2;
        };
    }

    public static void registerBurners(Block block) {
        if (!(block instanceof BaseBurnerBlock)) {
            return;
        }
        BoilerHeater.REGISTRY.register(block, (level, pos, state) ->
                fromHeatLevel(state.getValue(BlazeBurnerBlock.HEAT_LEVEL)));
    }

}