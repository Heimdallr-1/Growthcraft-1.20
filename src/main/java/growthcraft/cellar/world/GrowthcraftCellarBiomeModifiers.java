package growthcraft.cellar.world;

import growthcraft.cellar.init.GrowthcraftCellarTags;
import growthcraft.cellar.shared.Reference;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class GrowthcraftCellarBiomeModifiers {
	
	private GrowthcraftCellarBiomeModifiers() {
		/* Prevent generation of public constructor */
	}
	
    public static final ResourceKey<BiomeModifier> ADD_CORK_TREE = registerKey("add_cork_tree");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        
        context.register(ADD_CORK_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(GrowthcraftCellarTags.Biomes.HAS_CORK_TREE),
                HolderSet.direct(placedFeatures.getOrThrow(GrowthcraftCellarPlacedFeatures.CORK_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }
	
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Reference.MODID, name));
    }
}
