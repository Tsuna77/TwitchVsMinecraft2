package io.github.icrazyblaze.twitchmod.gui;

import io.github.icrazyblaze.twitchmod.Main;
import io.github.icrazyblaze.twitchmod.util.timers.TimerSystem;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Thanks Silk!
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MOD_ID)
public class DeathTimerOverlay {

    @SubscribeEvent
    public static void onRenderGui(RenderGameOverlayEvent.Post event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }

        if (TimerSystem.deathTimerEnabled) {

            String text = "TIMER: " + TimerSystem.deathTimerSeconds;

            Minecraft.getInstance().font.drawShadow(event.getMatrixStack(), text, 4, 4, Integer.parseInt("AA0000", 16));

        }

    }

}