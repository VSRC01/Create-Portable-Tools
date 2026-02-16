package com.vsrc.createsourceandsteel.tools;

import com.simibubi.create.content.equipment.armor.BacktankUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PortableDrillTool extends DiggerItem {

    private final Supplier<Ingredient> repairIngredient;

    public PortableDrillTool(Tier tier, Properties properties, Supplier<Ingredient> repairIngredient) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
        this.repairIngredient = repairIngredient;
    }


    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return this.getTier().getSpeed();
        }

        return 1.0F;

    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {

        if (!level.isClientSide && entity instanceof Player player) {

            if (!BacktankUtil.canAbsorbDamage(player, maxUses())) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }

            return true;
        }

        return false;
    }

    private int maxUses() {
        return this.getTier().getUses(); // how many blocks the backtank can buffer
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BacktankUtil.isBarVisible(stack, maxUses());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return BacktankUtil.getBarWidth(stack, maxUses());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BacktankUtil.getBarColor(stack, maxUses());
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.repairIngredient.get().test(repair);
    }
}
