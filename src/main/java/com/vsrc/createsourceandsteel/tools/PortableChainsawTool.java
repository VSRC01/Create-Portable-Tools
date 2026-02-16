package com.vsrc.createsourceandsteel.tools;

import com.simibubi.create.content.equipment.armor.BacktankUtil;

import com.simibubi.create.content.kinetics.saw.TreeCutter;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class PortableChainsawTool extends DiggerItem {

    private final Supplier<Ingredient> repairIngredient;

    public PortableChainsawTool(Tier tier, Properties properties, Supplier<Ingredient> repairIngredient) {
        super(tier, BlockTags.MINEABLE_WITH_AXE, properties);
        this.repairIngredient = repairIngredient;
    }


    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return this.getTier().getSpeed();
        }

        return 1.0F;

    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!BacktankUtil.canAbsorbDamage(player, maxUses())) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }

            if (TreeCutter.isLog(state)) {
                TreeCutter.Tree tree = TreeCutter.findTree(level, pos, state);
                if (tree != TreeCutter.NO_TREE) {
                    tree.destroyBlocks(level, stack, null, (blockPos, dropStack) ->
                            Block.popResource(level, blockPos, dropStack));
                }

            }

            return true;
        }

        return false;
    }


    private int maxUses() {
        return this.getTier().getUses();
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

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility) || super.canPerformAction(stack, itemAbility);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        Optional<BlockState> strippedState = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));

        if (strippedState.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!level.isClientSide) {
                level.setBlock(pos, strippedState.get(), 11);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

}
