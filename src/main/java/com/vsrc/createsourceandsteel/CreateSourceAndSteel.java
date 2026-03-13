package com.vsrc.createsourceandsteel;

import com.vsrc.createsourceandsteel.items.ModCreativeModeTabs;
import com.vsrc.createsourceandsteel.items.ModItems;
import com.vsrc.createsourceandsteel.registry.BlockRegistry;
import com.vsrc.createsourceandsteel.registry.ItemRegistry;
import com.vsrc.createsourceandsteel.tools.PortableDrillTool;
import com.vsrc.createsourceandsteel.util.BoilerHeaterRegistry;
import com.vsrc.createsourceandsteel.event.Events;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.equipment.armor.BacktankUtil;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import net.minecraft.world.entity.player.Player;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateSourceAndSteel.MODID)
public class CreateSourceAndSteel {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createsourceandsteel";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateSourceAndSteel(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockRegistry.BLOCK_ENTITY_TYPES.register( modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        modEventBus.register(Events.class);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        BoilerHeaterRegistry.registerBoilerHeaters();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();

        
        if (stack.getItem() instanceof PortableDrillTool drill) {
            Player player = event.getEntity();
            
            var backtanks = BacktankUtil.getAllWithAir(player);
            
            if (!backtanks.isEmpty()) {
                ItemStack backtank = backtanks.get(0);
                float air = (float) BacktankUtil.getAir(backtank);
                float maxAir = (float) BacktankUtil.maxAir(backtank);
                

                float efficiency = 0.4F + (3.6F * (air / maxAir));
                
                event.setNewSpeed(event.getOriginalSpeed() * efficiency);
            } else {
                event.setNewSpeed(event.getOriginalSpeed() * 0.25F);
            }
        }
    }
}
