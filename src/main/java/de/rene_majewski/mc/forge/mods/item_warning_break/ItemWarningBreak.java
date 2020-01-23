package de.rene_majewski.mc.forge.mods.item_warning_break;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ItemWarningBreak.MODID)
public class ItemWarningBreak
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "itemwarningbreak";

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientEvents
    {
        private static ItemStack previousStack = ItemStack.EMPTY;

        private static boolean isAboutToBreak(ItemStack stack)
        {
            return stack.isDamageable() && (stack.getDamage() + 1) >= stack.getMaxDamage() && (!Screen.hasControlDown());
        }

        public static int adjustedDurability(ItemStack stack, int remaining)
        {
            int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);

            double chance = 1.0 / (unbreaking + 1);
            if (stack.getItem() instanceof ArmorItem)
            {
                chance *= 0.4;
            }

            double durability_coef = 1 / chance;

            return MathHelper.floor(remaining * durability_coef);
        }

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event)
        {
            if (event.phase == TickEvent.Phase.END)
            {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player == null || player.isCreative())
                {
                    return;
                }

                ItemStack stack = player.getHeldItemMainhand();

                if (!ItemStack.areItemStacksEqual(previousStack, stack))
                {
                    previousStack = stack;

                    if (stack.isDamageable())
                    {
                        int remaining = stack.getMaxDamage() - stack.getDamage();
                        int uses = adjustedDurability(stack, remaining);

                        if (remaining <= 10)
                        {
                            TranslationTextComponent tc;
                            tc = new TranslationTextComponent("text.itemwarningbreak.warning", remaining);
                            Minecraft.getInstance().ingameGUI.setOverlayMessage(tc, false);
                        }
                    }
                }
            }
        }
    }
}
