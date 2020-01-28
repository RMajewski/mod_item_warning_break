package de.rene_majewski.mc.forge.mods.itemwarningbreak;

import de.rene_majewski.mc.forge.mods.itemwarningbreak.config.ConfigHolder;
import de.rene_majewski.mc.forge.mods.itemwarningbreak.proxy.ClientProxy;
import de.rene_majewski.mc.forge.mods.itemwarningbreak.proxy.IProxy;
import de.rene_majewski.mc.forge.mods.itemwarningbreak.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
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

    public static final IProxy PROXY = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public ItemWarningBreak() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> {
            final ModLoadingContext modLoadingContext = ModLoadingContext.get();

            modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
            ConfigHolder.loadConfig(ConfigHolder.CLIENT_SPEC, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml"));

            PROXY.registerConfigGui(modLoadingContext);
        });
    }
}
