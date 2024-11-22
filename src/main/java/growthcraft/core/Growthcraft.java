package growthcraft.core;

import growthcraft.core.datagen.shared.BiomeTagLootCondition;
import growthcraft.core.datagen.shared.ModuleLoadedLootCondition;
import growthcraft.core.init.*;
import growthcraft.core.init.config.BooleanFromConfigFileCondition;
import growthcraft.core.init.config.GrowthcraftConfig;
import growthcraft.core.init.config.OptionalFeatureCondition;
import growthcraft.core.shared.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Growthcraft {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    public Growthcraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetupEvent);
        modEventBus.addListener(this::registrationHandler);

        GrowthcraftConfig.loadConfig();

        GrowthcraftBlocks.BLOCKS.register(modEventBus);
        GrowthcraftItems.ITEMS.register(modEventBus);
        GrowthcraftBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        GrowthcraftCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        GrowthcraftLootModifiers.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetupEvent(final FMLClientSetupEvent event) {
        // Do nothing for now ...
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Do nothing
    }

    private void registrationHandler(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
            // not same things but we just need them registered once and early enough.
            CraftingHelper.register(new OptionalFeatureCondition.Serializer(OptionalFeatureCondition.DEFAULT_ID)); // normal registry in 1.21
            CraftingHelper.register(new BooleanFromConfigFileCondition.Serializer(BooleanFromConfigFileCondition.DEFAULT_ID)); // normal registry in 1.21
            ModuleLoadedLootCondition.TYPE = Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, new ResourceLocation(Reference.MODID, "module_loaded_loot_check"), new LootItemConditionType(ModuleLoadedLootCondition.Serializer.create()));
            BiomeTagLootCondition.TYPE = Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, new ResourceLocation(Reference.MODID, "biome_loot_check"), new LootItemConditionType(BiomeTagLootCondition.Serializer.create()));
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do nothing
    }
}
