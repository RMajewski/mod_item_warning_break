package de.rene_majewski.mc.forge.mods.itemwarningbreak.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.rene_majewski.mc.forge.mods.itemwarningbreak.ItemWarningBreak;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class Config {
    private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec client;

    static {
        WarningConfig.init(client_builder);

        client = client_builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        ItemWarningBreak.LOGGER.info("Loading config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();

        ItemWarningBreak.LOGGER.info("Build config: " + path);

        file.load();
        ItemWarningBreak.LOGGER.info("Loaded config: " + path);

        config.setConfig(file);
    }
}
