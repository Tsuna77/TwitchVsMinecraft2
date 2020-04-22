package com.icrazyblaze.twitchmod;

import com.icrazyblaze.twitchmod.command.TTVCommand;
import com.icrazyblaze.twitchmod.irc.BotConnection;
import com.icrazyblaze.twitchmod.util.Reference;
import com.icrazyblaze.twitchmod.util.TickHandler;
import net.minecraft.command.Commands;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class ForgeEventSubscriber {

    @SubscribeEvent
    public static void serverStarting(FMLServerStartingEvent event) {

        // Set the server reference for BotCommands (used to get player entity)
        BotCommands.defaultServer = event.getServer();
        TickHandler.enabled = true;

        // Register commands
        event.getCommandDispatcher().register(Commands.literal(Reference.MOD_ID)
                .then(TTVCommand.register())
        );

    }

//    @SubscribeEvent
//    public static void init(FMLCommonSetupEvent event) {
//        PacketHandler.registerMessages();
//    }

    @SubscribeEvent
    public static void joinedGame(PlayerEvent.PlayerLoggedInEvent event) {

        // Only the first player joining will trigger this
        if (event.getPlayer() == null || event.getPlayer().getServer().getPlayerList().getPlayers().size() > 1) {
            return;
        }

        Main.updateConfig();

        if (!BotConnection.isConnected()) {
            BotConnection.tryConnect();
        }

    }

    @SubscribeEvent
    public static void serverStopping(FMLServerStoppingEvent event) {
        BotConnection.disconnectBot();
        TickHandler.enabled = false;
    }


}
