package growthcraft.cellar.world;

import java.util.List;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.shared.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class GrowthcraftCellarPlacedFeatures {
	
	private GrowthcraftCellarPlacedFeatures() {
		/* Prevent generation of public constructor */
	}
	
    public static final ResourceKey<PlacedFeature> CORK_TREE_PLACED_KEY = createKey(Reference.UnlocalizedName.CORK_TREE + "_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CORK_TREE_PLACED_KEY, configuredFeatures.getOrThrow(GrowthcraftCellarConfiguredFeatures.CORK_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02F, 1), GrowthcraftCellarBlocks.CORK_TREE_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Reference.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
