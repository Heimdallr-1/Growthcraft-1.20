package growthcraft.lib.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeightedSeedReplacementModifier extends LootModifier {
    private final Item item;
    private final int percentToReplace, percentToVoid;

	public WeightedSeedReplacementModifier(LootItemCondition[] conditionsIn, Item item, int percentToReplace, int percentToVoid) {
        super(conditionsIn);
        this.item = item;
        this.percentToReplace = percentToReplace;
        this.percentToVoid = percentToVoid;
	}

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        int itemsToRemove = 0;
        for (int i = 0; i < generatedLoot.size() - itemsToRemove; i++) {
            if (generatedLoot.get(i).is(Tags.Items.SEEDS)) {
                int roll = context.getRandom().nextInt(100);
                if (roll < percentToReplace) {
                    generatedLoot.set(i, new ItemStack(this.item)); // replace
                }
                else if (roll < percentToReplace + percentToVoid) {
                    int lastIndex = generatedLoot.size() - itemsToRemove - 1;
                    generatedLoot.set(i, generatedLoot.get(lastIndex));
                    itemsToRemove += 1;
                }
            }
        }
        generatedLoot.size(generatedLoot.size() - itemsToRemove);
        return generatedLoot;
    }

    /////////////// technicalities below /////////////////////

    public static final Supplier<Codec<WeightedSeedReplacementModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
                    .and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("percent_to_replace").forGetter(m -> m.percentToReplace))
                    .and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("percent_to_void").forGetter(m -> m.percentToVoid))
                    .apply(inst, WeightedSeedReplacementModifier::new)));;

    @Override
    public Codec<? extends IGlobalLootModifier> codec() { return CODEC.get(); }
}
