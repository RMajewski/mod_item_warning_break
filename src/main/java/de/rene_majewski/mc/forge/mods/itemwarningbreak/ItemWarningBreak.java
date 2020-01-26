package de.rene_majewski.mc.forge.mods.itemwarningbreak;

import de.rene_majewski.mc.forge.mods.itemwarningbreak.config.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ItemWarningBreak.MODID)
public class ItemWarningBreak
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "itemwarningbreak";
    public static ItemWarningBreak instance;

    public ItemWarningBreak() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client);

        Config.loadConfig(Config.client, FMLPaths.CONFIGDIR.get().resolve("itemwarningbreak-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }
}
