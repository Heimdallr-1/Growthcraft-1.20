package growthcraft.core.init;

import com.mojang.serialization.Codec;

import growthcraft.core.shared.Reference;
import growthcraft.lib.loot.AddItemModifier;
import growthcraft.lib.loot.AddItemReplaceSeedsModifier;
import growthcraft.lib.loot.AddLootTableModifier;
import growthcraft.lib.loot.WeightedSeedReplacementModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GrowthcraftLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Reference.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_REPLACING_SEEDS = LOOT_MODIFIER_SERIALIZERS.register("add_item_replacing_seeds", AddItemReplaceSeedsModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> WEIGHTED_SEED_REPLACER  = LOOT_MODIFIER_SERIALIZERS.register("weighted_seed_replacer", WeightedSeedReplacementModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE  = LOOT_MODIFIER_SERIALIZERS.register("add_loot_table", AddLootTableModifier.CODEC);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
