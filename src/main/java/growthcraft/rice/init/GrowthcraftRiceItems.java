package growthcraft.rice.init;

import growthcraft.core.init.config.OptionalFeatureCondition;
import growthcraft.lib.item.GrowthcraftBowlFoodItem;
import growthcraft.lib.item.GrowthcraftFoodItem;
import growthcraft.lib.item.GrowthcraftItem;
import growthcraft.rice.item.CultivatorItem;
import growthcraft.rice.item.RiceSeedItem;
import growthcraft.rice.shared.Reference;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GrowthcraftRiceItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, Reference.MODID
    );

    public static final RegistryObject<CultivatorItem> CULTIVATOR = ITEMS.register(
      Reference.UnlocalizedName.CULTIVATOR,
      CultivatorItem::new
    );

    public static final RegistryObject<GrowthcraftItem> KNIFE = ITEMS.register(
            Reference.UnlocalizedName.KNIFE,
            GrowthcraftItem::new
    );
    
    public static final RegistryObject<RiceSeedItem> RICE_GRAINS = ITEMS.register(
            Reference.UnlocalizedName.RICE_GRAINS,
            RiceSeedItem::new
    );

    public static final RegistryObject<GrowthcraftItem> RICE = ITEMS.register(
            Reference.UnlocalizedName.RICE,
            GrowthcraftItem::new
    );

    public static final RegistryObject<GrowthcraftFoodItem> RICE_COOKED = ITEMS.register(
            Reference.UnlocalizedName.RICE_COOKED,
            () -> new GrowthcraftFoodItem(6, 0.4F, 64)
    );

    public static final RegistryObject<GrowthcraftItem> RICE_STALK = ITEMS.register(
            Reference.UnlocalizedName.RICE_STALK,
            RiceSeedItem::new
    );

    public static final RegistryObject<GrowthcraftFoodItem> SUSHI_ROLL = ITEMS.register(
            Reference.UnlocalizedName.SUSHI_ROLL,
            () -> new GrowthcraftFoodItem(3, 0.4F, 64)
    );
    
    public static final RegistryObject<GrowthcraftFoodItem> ONIGIRI = ITEMS.register(
            Reference.UnlocalizedName.ONIGIRI,
            () -> new GrowthcraftFoodItem(8, 0.5F, 64)
    );
    
    public static final RegistryObject<GrowthcraftBowlFoodItem> CHICKEN_RICE = ITEMS.register(
            Reference.UnlocalizedName.CHICKEN_RICE,
            () -> new GrowthcraftBowlFoodItem(12, 0.8F, 8)
    );

    public static final RegistryObject<GrowthcraftItem> YEAST_SEISHU = ITEMS.register(
            Reference.UnlocalizedName.YEAST_SEISHU, GrowthcraftItem::new
    );

    public static void registerCompostables() {
        float chanceLow = 0.1F;
        float chanceMedium = 0.4F;
        float chanceHigh = 0.85F;
        float definitely = 1.0F;
        // Add rice as a compostable
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.RICE.get(), chanceLow);
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.RICE_GRAINS.get(), chanceMedium);
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.RICE_COOKED.get(), chanceMedium);
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.RICE_STALK.get(), chanceMedium);
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.SUSHI_ROLL.get(), chanceHigh);
        ComposterBlock.COMPOSTABLES.put(GrowthcraftRiceItems.YEAST_SEISHU.get(), definitely);
    }

    public static boolean includeInCreativeTab(Supplier<Item> item) {
        if (item.get() instanceof BucketItem) {
            return OptionalFeatureCondition.testModuleOrModuleFeature("rice/beverages");
        }
        return OptionalFeatureCondition.testModuleOrModuleFeature("rice");
    }

    private GrowthcraftRiceItems() {
        /* Prevent default public constructor */
    }
}
