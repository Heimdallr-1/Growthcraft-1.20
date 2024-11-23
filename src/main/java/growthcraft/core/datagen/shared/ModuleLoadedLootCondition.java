package growthcraft.core.datagen.shared;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import growthcraft.core.init.config.OptionalFeatureCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import javax.annotation.Nullable;

public class ModuleLoadedLootCondition  implements LootItemCondition {
    public static LootItemConditionType TYPE = null;
    private final String moduleToCheck;

    private ModuleLoadedLootCondition(String module) {
        this.moduleToCheck = module != null ? module : "";
    }

    public static ModuleLoadedLootCondition isLoaded(String module) {
        return new ModuleLoadedLootCondition(module);
    }

    @Override
    public LootItemConditionType getType() { return TYPE; }

    ////////////////////////////////////////

    @Override
    public boolean test(LootContext lootContext) {
        return OptionalFeatureCondition.testModuleOrModuleFeature(this.moduleToCheck);
    }

    public static ModuleLoadedLootCondition.Builder getBuilder() { return new Builder(); }

    /////////////////////////////////

    public static class Builder implements LootItemCondition.Builder {
        @Nullable
        private String moduleToCheck = "";

        public ModuleLoadedLootCondition.Builder checkIsLoaded(String module) {
            this.moduleToCheck = module;
            return this;
        }
        public ModuleLoadedLootCondition build() {
            return new ModuleLoadedLootCondition(this.moduleToCheck);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ModuleLoadedLootCondition> {
        public void serialize(JsonObject jsonObject, ModuleLoadedLootCondition lootCondition, JsonSerializationContext context) {
            jsonObject.addProperty("module", lootCondition.moduleToCheck);
        }

        public ModuleLoadedLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            String module = jsonObject.has("module") ? GsonHelper.getAsString(jsonObject, "module") : "";
            return new ModuleLoadedLootCondition(module);
        }

        public static Serializer create() { return new Serializer(); }
    }
}
