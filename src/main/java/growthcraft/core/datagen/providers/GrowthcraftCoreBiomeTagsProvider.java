package growthcraft.core.datagen.providers;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import growthcraft.apples.init.GrowthcraftApplesTags;
import growthcraft.cellar.init.GrowthcraftCellarTags;
import growthcraft.cellar.shared.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GrowthcraftCoreBiomeTagsProvider extends BiomeTagsProvider{

	public GrowthcraftCoreBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, completableFuture, Reference.MODID, existingFileHelper);
	}

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    	tag(GrowthcraftCellarTags.Biomes.HAS_CORK_TREE).add(Biomes.DARK_FOREST);
    	tag(GrowthcraftCellarTags.Biomes.HAS_CORK_TREE).add(Biomes.MEADOW);
    	tag(GrowthcraftApplesTags.Biomes.HAS_APPLE_TREE).add(Biomes.FOREST);
    	tag(GrowthcraftApplesTags.Biomes.HAS_APPLE_TREE).add(Biomes.PLAINS);
    }
}
