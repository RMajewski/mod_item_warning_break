package de.rene_majewski.mc.forge.mods.itemwarningbreak.config;

import de.rene_majewski.mc.forge.mods.itemwarningbreak.ItemWarningBreak;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static ForgeConfigSpec.DoubleValue warn_1_volume;
    public static ForgeConfigSpec.DoubleValue warn_1_pitch;
    public static ForgeConfigSpec.IntValue warn_1_remaining;
    public static ForgeConfigSpec.BooleanValue warn_1_play_sound;

    public static ForgeConfigSpec.DoubleValue warn_2_volume;
    public static ForgeConfigSpec.DoubleValue warn_2_pitch;
    public static ForgeConfigSpec.IntValue warn_2_remaining;
    public static ForgeConfigSpec.BooleanValue warn_2_play_sound;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.comment(ItemWarningBreak.MODID + " Configuration");

        warn_1_remaining = builder
                .comment("Value from which warning 1 is to be display.")
                .defineInRange(ItemWarningBreak.MODID + ".warn_1_remaining", 10, 1, 2000);

        warn_1_play_sound = builder
                .comment("Should the sound be played in warning 1?")
                .define(ItemWarningBreak.MODID + ".warn_1_play_sound", true);

        warn_1_volume = builder
                .comment("Volume at which the sound for warning 1 is to be played.")
                .defineInRange(ItemWarningBreak.MODID + ".warn_1_volume", 1.0, 0.0, 1.0);

        warn_1_pitch = builder
                .comment("Pitch for warning 2 sound")
                .defineInRange(ItemWarningBreak.MODID + ".warn_1_pitch", 1.0, 0.0, 1.0);

        warn_2_remaining = builder
                .comment("Value from which warning 1 is to be display.")
                .defineInRange(ItemWarningBreak.MODID + ".warn_2_remaining", 50, 1, 2000);

        warn_2_play_sound = builder
                .comment("Should the sound be played in warning 1?")
                .define(ItemWarningBreak.MODID + ".warn_2_play_sound", true);

        warn_2_volume = builder
                .comment("Volume at which the sound for warning 1 is to be played.")
                .defineInRange(ItemWarningBreak.MODID + ".warn_2_volume", 1.0, 0.0, 1.0);

        warn_2_pitch = builder
                .comment("Pitch for warning 1 sound")
                .defineInRange(ItemWarningBreak.MODID + ".warn_2_pitch", 1.0, 0.0, 1.0);
    }
}
