package growthcraft.cellar.datagen;

import java.util.concurrent.CompletableFuture;

import growthcraft.cellar.datagen.providers.GrowthcraftCellarLootTableProvider;
import growthcraft.cellar.datagen.providers.GrowthcraftCellarRecipes;
import growthcraft.cellar.datagen.providers.GrowthcraftCellarWorldGenProvider;
import growthcraft.cellar.shared.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GrowthcraftCellarDataGenerators {

	private GrowthcraftCellarDataGenerators() {
		/* Prevent generation of public constructor. */
	}
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		
		generator.addProvider(event.includeServer(), new GrowthcraftCellarRecipes(packOutput));
		generator.addProvider(event.includeServer(), new GrowthcraftCellarLootTableProvider(packOutput));
		generator.addProvider(event.includeServer(), new GrowthcraftCellarWorldGenProvider(packOutput, lookupProvider));
//		generator.addProvider(event.includeServer(), new GrowthcraftCellarGlobalLootModifiersProvider(packOutput));
	}
}
