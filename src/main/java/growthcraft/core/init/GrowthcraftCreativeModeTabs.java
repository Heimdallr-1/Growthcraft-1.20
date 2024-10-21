package growthcraft.core.init;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GrowthcraftCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register(Reference.UnlocalizedName.CREATIVE_TAB, () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + Reference.MODID + ".tab"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> GrowthcraftCellarBlocks.FERMENTATION_BARREL_OAK.get().asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                // Add blocks
                GrowthcraftBlocks.BLOCKS.getEntries().forEach(
                        blockRegistryObject -> {
                            if (!GrowthcraftBlocks.excludeBlockItemRegistry(blockRegistryObject.getId())) {
                               try { output.accept(new ItemStack(blockRegistryObject.get())); } catch (Exception e) {}
                            }
                        }
                );
                // Add items
                GrowthcraftItems.ITEMS.getEntries().forEach(itemRegistryObject -> {
                    if (GrowthcraftItems.includeInCreativeTab(itemRegistryObject)) {
                        output.accept(new ItemStack(itemRegistryObject.get()));
                    }
                });
            }).build());
}
