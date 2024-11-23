package growthcraft.rice.init.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

public class GrowthcraftRiceConfig {

    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER;
    public static final String SERVER_CONFIG = "growthcraft-rice-server.toml";

    private static ForgeConfigSpec.BooleanValue moduleEnabled;
    private static ForgeConfigSpec.BooleanValue featureEnabledBeverages;

    static {
        initServerConfig(SERVER_BUILDER);
        SERVER = SERVER_BUILDER.build();
    }

    private GrowthcraftRiceConfig() {
        /* Prevent generation of public constructor */
    }

    public static void loadConfig() {
        loadConfig(SERVER, FMLPaths.CONFIGDIR.get().resolve(SERVER_CONFIG).toString());
    }

    private static void loadConfig(ForgeConfigSpec configSpec, String path) {
        final CommentedFileConfig fileConfig = CommentedFileConfig.builder(
                new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();

        fileConfig.load();
        configSpec.setConfig(fileConfig);
    }

    private static void initServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("_master_switch_");
        moduleEnabled = builder
                .comment("This master-switch lets you disable the entire Rice module - seeds dropping from grass, farming tool and products.")
                .define("module_enabled", true);
        featureEnabledBeverages = builder
                .comment("In case master-switch is turned off, this exception allows you to still make rice wine and sake (assuming some other mod provides rice).")
                .define("feature_exception_beverages", true);
        builder.pop();  // master_switch
    }

    //////////////////////////////////

    public static Boolean getFeatureEnabledBeverages()
    {
        return featureEnabledBeverages.get();
    }

    public static Boolean getModuleEnabled()
    {
        return moduleEnabled.get();
    }
}
