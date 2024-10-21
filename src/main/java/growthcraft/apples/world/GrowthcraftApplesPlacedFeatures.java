package growthcraft.apples.world;

import java.util.List;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.Reference;
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

public class GrowthcraftApplesPlacedFeatures {
	
	private GrowthcraftApplesPlacedFeatures() {
		/* Prevent generation of public constructor */
	}

    public static final ResourceKey<PlacedFeature> APPLE_TREE_PLACED_KEY = createKey(Reference.UnlocalizedName.APPLE_TREE + "_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, APPLE_TREE_PLACED_KEY, configuredFeatures.getOrThrow(GrowthcraftApplesConfiguredFeatures.APPLE_TREE_KEY),
        		 VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 1), GrowthcraftApplesBlocks.APPLE_TREE_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Reference.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
