package com.vsrc.createsourceandsteel.items;

import com.hollingsworth.arsnouveau.common.block.ModBlock;
import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.blocks.SourceBurnerBlock;
import com.vsrc.createsourceandsteel.registry.BlockRegistry;
import com.vsrc.createsourceandsteel.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSourceAndSteel.MODID);

    public static final Supplier<CreativeModeTab> CREATE_SOURCE_AND_STEEL = CREATIVE_MODE_TAB.register("create_source_and_steel", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.createsourceandsteel.create_ses"))
            .icon(() -> new ItemStack(ModItems.PORTABLE_DIAMOND_DRILL.get()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.PORTABLE_DRILL.get());
                output.accept(ModItems.PORTABLE_DIAMOND_DRILL.get());
                output.accept(ModItems.PORTABLE_CHAINSAW.get());
                output.accept(BlockRegistry.SOURCE_BURNER.get());
            })

            .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
