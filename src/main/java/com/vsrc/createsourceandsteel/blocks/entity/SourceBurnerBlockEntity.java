package com.vsrc.createsourceandsteel.blocks.entity;

import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.blocks.SourceBurnerBlock;
import com.vsrc.createsourceandsteel.registry.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


import java.util.List;


public class SourceBurnerBlockEntity extends BaseBurnerBlockEntity {

    public static final int MAX_SOURCE_CAP = 10000;
    public static final int SOURCE_COST = 2;
    public static final double SOURCE_MULTIPLIER_1 = 1.5;
    public static final double SOURCE_MULTIPLIER_2 = 2;
    public static final double MAX_HEAT = 300;
    public static final double UPGRADED_MAX_HEAT = 400;
    public static final double HEATING_RATE = 2;
    public static final double COOLING_RATE = 1;
    public double source_cost;
    public boolean upgraded;
    public final SourceStorage source = new SourceStorage(1000,100,100);

    public SourceBurnerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.SOURCE_BURNER_ENTITY.get(), pos, state);
        this.max_heat = MAX_HEAT;
        this.source_cost = SOURCE_COST;
        this.upgraded = false;

    }

    public void loadAdditional(@NotNull CompoundTag nbt, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.upgraded = nbt.getBoolean("upgraded");
    }

    public void saveAdditional(@NotNull CompoundTag nbt, @NotNull HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        //nbt.putInt("source", this.source.);
        nbt.putBoolean("upgraded", this.upgraded);
    }

    public @NotNull CompoundTag getUpdateTag(@NotNull HolderLookup.Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        nbt.putBoolean("upgraded", this.upgraded);
        return nbt;
    }

    public void setUpgrade(boolean upgraded) {
        this.upgraded = upgraded;
        this.updateBlockState();
    }

    public void updateBlockState() {
        super.updateBlockState();
        this.setBlockUpgraded(this.upgraded);
    }

    public void setBlockUpgraded(boolean upgraded) {
        boolean state = this.getUpgradedFromBlock();
        if (state != upgraded) {
            assert this.level != null;
            this.level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(SourceBurnerBlock.UPGRADED, upgraded));
            this.setChanged();
        }
    }

    public boolean getUpgradedFromBlock() {
        BlockState state = this.getBlockState();
        return state.hasProperty(SourceBurnerBlock.UPGRADED) ? state.getValue(SourceBurnerBlock.UPGRADED) : false;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        ++this.ticksExisted;

        this.max_heat = upgraded ? UPGRADED_MAX_HEAT : MAX_HEAT;
        super.tick(level,pos,state);

        this.source_cost = SOURCE_COST * switch (getHeatLevelFromBlock()) {
            case NONE, SMOULDERING -> 1;
            case FADING, KINDLED -> SOURCE_MULTIPLIER_1;
            case SEETHING -> SOURCE_MULTIPLIER_2;
        };
        if (!level.isClientSide()) {

            double prevHeat = this.heat;
            if (this.source.getSource() > (int)this.source_cost) {
                this.source.extractSource((int)this.source_cost, false);
            } else {

                this.canWork = false;
            }
            if (this.ticksExisted % 20 == 0) {
                if (this.canWork) {
                    this.heat += HEATING_RATE;
                } else {
                    this.heat -= COOLING_RATE;
                }
                this.canWork = true;
            }
            this.heat = Mth.clamp(this.heat, 0.0, max_heat);
            if (this.heat != prevHeat) {
                this.setChanged();
                this.updateBlockState();
            }

        }

    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip,isPlayerSneaking);
        if(getUpgradedFromBlock()) {
            forGoggles(tooltip, Lang.builder(CreateSourceAndSteel.MODID).translate("burner.status.upgraded").style(ChatFormatting.BLUE), 1);
        }

        tooltip.add(Component.empty());
        forGoggles(tooltip, Lang.builder(CreateSourceAndSteel.MODID).translate("burner.energy.title").style(ChatFormatting.GRAY), 0);
        LangBuilder builder = Lang.builder(CreateSourceAndSteel.MODID).text(LangNumberFormat.format((int)this.source_cost))
                .translate("burner.source.unit")
                .style(ChatFormatting.AQUA)
                .space()
                .add(Lang.builder(CreateSourceAndSteel.MODID).translate("burner.per_tick").style(ChatFormatting.DARK_GRAY));
        forGoggles(tooltip, builder, 1);
        return added;
    }

}