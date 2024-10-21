package growthcraft.core.init.config;

import com.google.gson.JsonObject;
import growthcraft.apiary.init.config.GrowthcraftApiaryConfig;
import growthcraft.apples.init.config.GrowthcraftApplesConfig;
import growthcraft.cellar.init.config.GrowthcraftCellarConfig;
import growthcraft.core.Growthcraft;
import growthcraft.core.shared.Reference;
import growthcraft.milk.init.config.GrowthcraftMilkConfig;
import growthcraft.rice.init.config.GrowthcraftRiceConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/* *
* This is to be used in recipes and worlgen jsons to turn off things player does not want.
* It's a normal forge condition, like forge:mod_loaded
*
* Will be registrated as growthcraft:config_value    (type)
* Two fields. First one is called "name" and is one of our module names.
* Second field is called "name" and can be any boolean from config files. Separate categories using dots.
* Working examples: "core", "other.crowbars_enabled"              "milk", "loot_modifiers.stomachLootEnabled"            "apiary", "beeBox.replicateFlowers"
*  */
public class BooleanFromConfigFileCondition implements ICondition {
    public static final ResourceLocation DEFAULT_ID = new ResourceLocation(Reference.MODID, "config_value"); // normally should be unknown to this, but we'll only have one type and we need to instantiate manually for datagen. doesn't matter 1.21 will do away with this.
    private final String optionName, module;
    private final ResourceLocation conditionId; // internal; removed in 1.21
    private static final Map<String, NightConfigWrapper> wrappers = new HashMap<>();

    public BooleanFromConfigFileCondition(String module, String name) {
        this(DEFAULT_ID, module, name);
    }

    private BooleanFromConfigFileCondition(ResourceLocation id, String module, String name) {
        this.conditionId = id;
        this.optionName = name;
        this.module = module;
    }

    @Override
    public ResourceLocation getID() { return this.conditionId; }

    @Override
    public boolean test(IContext context) {
        return testConfigValue(this.module, this.optionName);
    }

    public static boolean testConfigValue(String module, String optionName) {
        if (wrappers.isEmpty()) {
            wrappers.put("core", new NightConfigWrapper(GrowthcraftConfig.SERVER.getValues()));
            wrappers.put("apiary", new NightConfigWrapper(GrowthcraftApiaryConfig.SERVER.getValues()));
            wrappers.put("apples", new NightConfigWrapper(GrowthcraftApplesConfig.SERVER.getValues()));
            wrappers.put("cellar", new NightConfigWrapper(GrowthcraftCellarConfig.SERVER.getValues()));
            wrappers.put("milk", new NightConfigWrapper(GrowthcraftMilkConfig.SERVER.getValues()));
            wrappers.put("rice", new NightConfigWrapper(GrowthcraftRiceConfig.SERVER.getValues()));
        }
        if (!wrappers.containsKey(module)) {
            Growthcraft.LOGGER.error("Growthcraft condition error: invalid module " + module + " in one of the json files.");
            return false;
        }
        Optional<Object> valueHolder = wrappers.get(module).getConfigElement(optionName.split("\\."));
        if (valueHolder.isPresent() && valueHolder.get() instanceof ForgeConfigSpec.BooleanValue value) {
            return value.get();
        }
        else {
            Growthcraft.LOGGER.error("Growthcraft condition error: invalid config path " + optionName + " in one of the json files.");
            return false;
        }
    }

    /////////////////////////////////////////////////////

    public static class Serializer implements IConditionSerializer<BooleanFromConfigFileCondition> {
        private final ResourceLocation conditionId;

        public Serializer(ResourceLocation id)
        {
            this.conditionId = id;
        }

        @Override
        public void write(JsonObject json, BooleanFromConfigFileCondition condition) {
            json.addProperty("module", condition.module);
            json.addProperty("name", condition.optionName);
        }

        @Override
        public BooleanFromConfigFileCondition read(JsonObject json) {
            return new BooleanFromConfigFileCondition(this.conditionId, json.getAsJsonPrimitive("module").getAsString(), json.getAsJsonPrimitive("name").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return this.conditionId;
        }
    }
}
