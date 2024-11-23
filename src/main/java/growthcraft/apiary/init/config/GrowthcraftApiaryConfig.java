package growthcraft.apiary.init.config;

import growthcraft.core.init.config.GrowthcraftConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

public class GrowthcraftApiaryConfig {

    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER;

    public static final String SERVER_CONFIG = "growthcraft-apiary-server.toml";

    private static final String CATEGORY_BEE_BOX = "beeBox";

    private static ForgeConfigSpec.IntValue flowerReplicationRange;
    private static ForgeConfigSpec.IntValue beeBoxCycleUpdateTicks;
    private static ForgeConfigSpec.BooleanValue doFlowerReplication;
    private static ForgeConfigSpec.IntValue beeBreedingChance;
    private static ForgeConfigSpec.IntValue flowerReplicationChance;
    private static ForgeConfigSpec.IntValue flowerSaturationPercent;

    private static ForgeConfigSpec.BooleanValue moduleEnabled;
    private static ForgeConfigSpec.BooleanValue featureEnabledBeverages;
    private static ForgeConfigSpec.BooleanValue featureEnabledBasicWax;

    static {
        initServerConfig(SERVER_BUILDER);
        SERVER = SERVER_BUILDER.build();
    }

    private GrowthcraftApiaryConfig() {
        /* Prevent generation of public constructor */
    }

    public static void loadConfig() {
        loadConfig(SERVER, FMLPaths.CONFIGDIR.get().resolve(SERVER_CONFIG).toString());
    }

    public static void loadConfig(ForgeConfigSpec configSpec, String path) {
        GrowthcraftConfig.loadConfig(configSpec, path);
    }

    public static void initServerConfig(ForgeConfigSpec.Builder specBuilder) {
        specBuilder.push("_master_switch_");
        moduleEnabled = specBuilder
                .comment("This master-switch lets you disable the entire Apiary module - bees-as-items and bee boxes, colored wax, flower duplication. Since Minecraft includes bees as of 1.15, we actually expect you to disable this module.")
                .define("module_enabled", true);
        featureEnabledBeverages = specBuilder
                .comment("In case master-switch is turned off, this exception allows you to still make mead from vanilla honeycombs.")
                .define("feature_exception_beverages", true);
        featureEnabledBasicWax = specBuilder
                .comment("In case master-switch is turned off, this exception allows you to still make wax (uncolored kind), as it is usable in milk module (not essential, though).")
                .define("feature_exception_basic_wax", false);
        specBuilder.pop();  // master_switch

        specBuilder.push(CATEGORY_BEE_BOX);
        flowerReplicationRange = specBuilder
                .comment("Set the range for the bee box to look for flowers.")
                .defineInRange("flowerRange", 4, 0, 18);
        beeBoxCycleUpdateTicks = specBuilder
                .comment("Set the process time for the bee box to update. Default is once a minute.")
                .defineInRange("maxProcessingTime", 1200, 200, 1728000);
        beeBreedingChance = specBuilder
                .comment("Set the percentage chance to increment bee population in the Bee Box.")
                .defineInRange("chanceBeeIncrement", 33, 1, 100);
        flowerReplicationChance = specBuilder
                .comment("Set the percentage chance to replicate a flower near by.")
                .defineInRange("chanceReplicateFlower", 10, 1, 100);
        doFlowerReplication = specBuilder
                .comment("Set to false to disable flower replication by the bee box")
                .define("replicateFlowers", true);
        flowerSaturationPercent = specBuilder
                .comment("Set the percentage of the area to be filled with replicated flowers.")
                .defineInRange("flowerReplicationAreaPercent", 100, 1, 100);
        specBuilder.pop();  // bee_box
    }

    public static int getBeeBoxFlowerRange() {
        return flowerReplicationRange.get();
    }

    public static int getBeeBoxMaxProcessingTime() {
        return beeBoxCycleUpdateTicks.get();
    }

    public static boolean shouldReplicateFlowers() {
        return doFlowerReplication.get();
    }

    public static int getChanceToIncreaseBees() {
        return beeBreedingChance.get();
    }

    public static int getChanceToReplicateFlowers() {
        return flowerReplicationChance.get();
    }

    public static int getFlowerReplicationAreaPercent() {
        return flowerSaturationPercent.get();
    }

    ///////////////

    public static Boolean getFeatureEnabledBeverages()
    {
        return featureEnabledBeverages.get();
    }

    public static Boolean getFeatureEnabledBasicWax() {
        return featureEnabledBasicWax.get();
    }

    public static Boolean getModuleEnabled()
    {
        return moduleEnabled.get();
    }
}