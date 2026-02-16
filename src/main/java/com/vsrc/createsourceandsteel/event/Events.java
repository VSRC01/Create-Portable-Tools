package com.vsrc.createsourceandsteel.event;


import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.vsrc.createsourceandsteel.blocks.entity.SourceBurnerBlockEntity;
import com.vsrc.createsourceandsteel.registry.BlockRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class Events {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                CapabilityRegistry.SOURCE_CAPABILITY,
                BlockRegistry.SOURCE_BURNER_ENTITY.get(),
                (be, context) -> {
                    if (be instanceof SourceBurnerBlockEntity burner) {
                        // Sua classe SourceStorage implementa ISourceCap, então funciona perfeitamente
                        return burner.source;
                    }
                    return null;
                }
        );
    }
}
