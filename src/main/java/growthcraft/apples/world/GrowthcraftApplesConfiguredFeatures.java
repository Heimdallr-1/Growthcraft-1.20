package growthcraft.apples.world;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;

public class GrowthcraftApplesConfiguredFeatures {
	
	private GrowthcraftApplesConfiguredFeatures() {
		/* Prevent generation of public constructor */
	}

    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_KEY = registerKey(Reference.UnlocalizedName.APPLE_TREE);

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, APPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                // Trunk block
                BlockStateProvider.simple(GrowthcraftApplesBlocks.APPLE_WOOD_LOG.get()),
                // Trunk placer (baseHeight, heightRandA, heightRandB)
                new CherryTrunkPlacer(6, 1, 0, new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()), UniformInt.of(2, 4), UniformInt.of(-4, -3), UniformInt.of(-1, 0)),
                // Leaves block
                BlockStateProvider.simple(GrowthcraftApplesBlocks.APPLE_TREE_LEAVES.get()),
                // Leaves placer (radius, offset, height)
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0F, 0F),
                // Spawn Area Saturation (limit, lowerSize, upperSize)
                new TwoLayersFeatureSize(1, 0, 2)).build()
        );

    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration
    ) {
        context.register(key, new ConfiguredFeature(feature, configuration));
    }

}
