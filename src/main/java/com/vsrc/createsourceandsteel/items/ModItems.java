package com.vsrc.createsourceandsteel.items;

import com.simibubi.create.Create;
import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.tools.PortableChainsawTool;
import com.vsrc.createsourceandsteel.tools.PortableDrillTool;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateSourceAndSteel.MODID);

    public static final DeferredItem<PortableDrillTool> PORTABLE_DRILL = ITEMS.register("portable_drill", () -> new PortableDrillTool(Tiers.IRON, new Item.Properties()
            .attributes(PickaxeItem.createAttributes(Tiers.IRON, 2F, -2F)),
            () -> Ingredient.of(BuiltInRegistries.ITEM.get(ResourceLocation.parse("create:andesite_alloy")))

    ));

    public static final DeferredItem<PortableDrillTool> PORTABLE_DIAMOND_DRILL = ITEMS.register("portable_diamond_drill", () -> new PortableDrillTool(Tiers.DIAMOND, new Item.Properties()
            .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 3F, -2F)),
            () -> Ingredient.of(Items.DIAMOND)
    ));

    public static final DeferredItem<PortableChainsawTool> PORTABLE_CHAINSAW = ITEMS.register("portable_chainsaw", () -> new PortableChainsawTool(Tiers.DIAMOND, new Item.Properties()
            .attributes(AxeItem.createAttributes(Tiers.DIAMOND, 6F, -1F)),
            () -> Ingredient.of(Items.IRON_INGOT)
    ));
}
