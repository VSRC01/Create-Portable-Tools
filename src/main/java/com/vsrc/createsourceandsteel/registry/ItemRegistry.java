package com.vsrc.createsourceandsteel.registry;

import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.items.HeatUpgradeItem;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateSourceAndSteel.MODID);

    public static final DeferredItem<HeatUpgradeItem> HEAT_UPGRADE =
            ITEMS.registerItem("heat_upgrade", HeatUpgradeItem::new);

    public static final DeferredItem<BlockItem> SOURCE_BURNER_ITEM =
            ITEMS.registerSimpleBlockItem("source_burner", BlockRegistry.SOURCE_BURNER);

}
