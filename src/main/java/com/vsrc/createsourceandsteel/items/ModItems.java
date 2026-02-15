package com.vsrc.createsourceandsteel.items;

import com.vsrc.createsourceandsteel.CreateSourceAndSteel;
import com.vsrc.createsourceandsteel.tools.PortableDrillTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateSourceAndSteel.MODID);

    public static final DeferredItem<PortableDrillTool> PORTABLE_DRILL = ITEMS.register("portable_drill",
            () -> new PortableDrillTool(Tiers.IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, 2F, -2F))
            ));

}
