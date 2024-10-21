package growthcraft.cellar.world.feature.tree;

import org.jetbrains.annotations.Nullable;

import growthcraft.cellar.world.GrowthcraftCellarConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CorkTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean largeHive) {
        return GrowthcraftCellarConfiguredFeatures.CORK_TREE_KEY;
    }
}
