package growthcraft.core.datagen.shared;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BiomeTagLootCondition implements LootItemCondition {
    public static LootItemConditionType TYPE = null;
    private final TagKey<Biome> biomeTag;

    private BiomeTagLootCondition(String tag) {
        this.biomeTag = TagKey.create(Registries.BIOME, new ResourceLocation(tag));
    }
    private BiomeTagLootCondition(TagKey<Biome> tag) {
        this.biomeTag = tag;
    }

    public static BiomeTagLootCondition forTag(TagKey<Biome> biomeTag) {
        return new BiomeTagLootCondition(biomeTag);
    }

    @Override
    public LootItemConditionType getType() { return TYPE; }

    /////////////////////////////////////////

    @Override
    public boolean test(LootContext lootContext) {
        if (this.biomeTag == null) {
            return false;
        }
        ServerLevel serverlevel = lootContext.getLevel();
        Vec3 origin = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        if (origin == null) {
            Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity == null) {
                return  false;
            }
            origin = entity.position();
        }
        return serverlevel.getBiome(BlockPos.containing(origin)).is(this.biomeTag);
    }

    public static BiomeTagLootCondition.Builder getBuilder() { return new BiomeTagLootCondition.Builder(); }

    ////////////////////////////////////////

    public static class Builder implements LootItemCondition.Builder {
        @Nullable
        private TagKey<Biome> biomeTag = null;

        public BiomeTagLootCondition.Builder forTag(TagKey<Biome> biomeTag) {
            this.biomeTag = biomeTag;
            return this;
        }
        public BiomeTagLootCondition build() {
            return new BiomeTagLootCondition(this.biomeTag);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BiomeTagLootCondition> {
        public void serialize(JsonObject jsonObject, BiomeTagLootCondition lootCondition, JsonSerializationContext context) {
            jsonObject.addProperty("biome_tag", lootCondition.biomeTag.location().toString());
        }

        public BiomeTagLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            String module = jsonObject.has("biome_tag") ? GsonHelper.getAsString(jsonObject, "biome_tag") : "";
            return new BiomeTagLootCondition(module);
        }

        public static Serializer create() { return new Serializer(); }
    }
}
