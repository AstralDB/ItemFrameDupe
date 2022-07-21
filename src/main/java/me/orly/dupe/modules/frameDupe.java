package me.orly.dupe.modules;

import me.orly.dupe.FrameDupe;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class frameDupe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFilters = settings.createGroup("Filters");

    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
        .name("Range")
        .description("range")
        .min(0)
        .sliderMin(0)
        .defaultValue(5)
        .sliderMax(6)
        .max(6)
        .build()
    );

    private final Setting<Integer> shulkersonly = sgGeneral.add(new IntSetting.Builder()
        .name("Shulkers")
        .description("Only uses shulks ")
        .defaultValue(1)
        .sliderMax(10)
        .build()
    );

    private final Setting<Integer> turns = sgGeneral.add(new IntSetting.Builder()
        .name("Turns")
        .description("Turns ")
        .min(0)
        .sliderMin(0)
        .defaultValue(1)
        .sliderMax(3)
        .max(3)
        .build()
    );

    private final Setting<Integer> ticks = sgGeneral.add(new IntSetting.Builder()
        .name("Ticks")
        .description("ticks")
        .min(0)
        .sliderMin(0)
        .defaultValue(10)
        .sliderMax(20)
        .max(20)
        .build()
    );

    public frameDupe() {
        super(FrameDupe.CATEGORY, "Frame-Dupe", "A dupe exploit on the anarchy server sweetanarchy.net");
    }

    private int timeout_ticks = 0;

    @EventHandler
    public void onTick(TickEvent.Post tickEvent) {
        if (mc.player != null && mc.world != null) {

            int shulker_slot = getShulkerSlot();

            if (shulker_slot != -1) {
                mc.player.getInventory().selectedSlot = shulker_slot;
            }

            for (Entity frame : mc.world.getEntities()) {
                if (frame instanceof ItemFrameEntity) {
                    if (mc.player.distanceTo(frame) <= range.getDefaultValue()) {
                        if (timeout_ticks >= ticks.getDefaultValue()) {
                            if (((ItemFrameEntity) frame).getHeldItemStack().getItem() == Items.AIR && !mc.player.getMainHandStack().isEmpty()) {
                                mc.player.interact(frame, mc.player.preferredHand);
                            }
                            if (((ItemFrameEntity) frame).getHeldItemStack().getItem() != Items.AIR) {
                                for (int i = 0; i < turns.getDefaultValue(); i++) {
                                    mc.player.interact(frame, mc.player.preferredHand);
                                }
                                mc.player.attack(frame);
                                timeout_ticks = 0;
                            }
                        }
                        ++timeout_ticks;
                    }
                }
            }
        }
    }
    private int getShulkerSlot() {
        int shulker_slot = -1;
        for (int i = 0; i < 9; i++) {
            Item item = mc.player.getInventory().getStack(i).getItem();
        }
        return shulker_slot;
    }
}
