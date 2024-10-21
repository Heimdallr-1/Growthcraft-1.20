package growthcraft.cellar.world;

import java.util.OptionalInt;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.shared.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class GrowthcraftCellarConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORK_TREE_KEY = registerKey(Reference.UnlocalizedName.CORK_TREE);
    
    private GrowthcraftCellarConfiguredFeatures() {
    	/* Prevent generation of public constructor */
	}
    
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, CORK_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                // Trunk block
                BlockStateProvider.simple(GrowthcraftCellarBlocks.CORK_WOOD_LOG.get()),
                // Trunk placer (baseHeight, heightRandA, heightRandB)
                new FancyTrunkPlacer(9, 4, 0),
                // Leaves block
                BlockStateProvider.simple(GrowthcraftCellarBlocks.CORK_TREE_LEAVES.get()),
                // Leaves placer (radius, offset, height)
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 3),
                // Spawn Area Saturation (limit, lowerSize, upperSize)
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build()
        );
    }
    
    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration
    ) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
