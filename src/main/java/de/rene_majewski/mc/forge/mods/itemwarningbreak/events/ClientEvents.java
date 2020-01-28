package de.rene_majewski.mc.forge.mods.itemwarningbreak.events;

import de.rene_majewski.mc.forge.mods.itemwarningbreak.ItemWarningBreak;
import de.rene_majewski.mc.forge.mods.itemwarningbreak.config.ItemWarningBreakModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ItemWarningBreak.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    private static ItemStack previousStack = ItemStack.EMPTY;

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

    public static void displayWarnMessage(int remaining, TextFormatting formatting)
    {
        TranslationTextComponent tc = new TranslationTextComponent("text.itemwarningbreak.warning", remaining);
        tc.applyTextStyle(formatting);
        Minecraft.getInstance().ingameGUI.setOverlayMessage(tc, false);
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

                    if (remaining <= ItemWarningBreakModConfig.warn_1_remaining)
                    {
                        displayWarnMessage(remaining, TextFormatting.RED);

                        if (ItemWarningBreakModConfig.warn_1_play_sound) {
                            ResourceLocation resWarn = new ResourceLocation(ItemWarningBreak.MODID, "warn_1");
                            SoundEvent warnEvent = new SoundEvent(resWarn);
                            player.playSound(
                                    warnEvent,
                                    ItemWarningBreakModConfig.warn_1_volume,
                                    ItemWarningBreakModConfig.warn_1_pitch
                            );
                        }
                    } else if (remaining < ItemWarningBreakModConfig.warn_2_remaining)
                    {
                        displayWarnMessage(remaining, TextFormatting.YELLOW);
                    } else if (remaining == ItemWarningBreakModConfig.warn_2_remaining) {
                        displayWarnMessage(remaining, TextFormatting.YELLOW);

                        if (ItemWarningBreakModConfig.warn_2_play_sound) {
                            ResourceLocation resWarn = new ResourceLocation(ItemWarningBreak.MODID, "warn_2");
                            SoundEvent warnEvent = new SoundEvent(resWarn);
                            player.playSound(
                                    warnEvent,
                                    // WarningConfig.warn_2_volume.get().floatValue(),
                                    // WarningConfig.warn_2_pitch.get().floatValue()
                                    1.0F,
                                    1.0F
                            );
                        }
                    }
                }
            }
        }
    }
}
