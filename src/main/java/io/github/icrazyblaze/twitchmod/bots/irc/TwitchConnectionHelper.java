package io.github.icrazyblaze.twitchmod.bots.irc;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.enums.TMIConnectionState;
import io.github.icrazyblaze.twitchmod.CommandHandlers;
import io.github.icrazyblaze.twitchmod.Main;
import io.github.icrazyblaze.twitchmod.config.BotConfig;
import io.github.icrazyblaze.twitchmod.config.ConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * This class is responsible for connecting the IRC bot, which is defined by the TwitchBot class.
 * The tryConnect method will either connect the bot or reconnect if it is already connected.
 */

public class TwitchConnectionHelper {

    private static Thread botThread = null;
    private static TwitchClient twitchClient = null;

    public static TwitchClient getBot() {
        return twitchClient;
    }

    public static boolean login() {

        // Update settings before connecting
        ConfigManager.updateFromConfig();

        if (BotConfig.TWITCH_KEY.isEmpty()) {
            return false;
        }


        if (isConnected()) {

            // Reconnect if already connected
            CommandHandlers.broadcastMessage(new TranslatableComponent("gui.twitchmod.chat.reconnecting").withStyle(ChatFormatting.DARK_PURPLE));
            try {
                twitchClient.getChat().reconnect();
            } catch (Exception e) {
                Main.logger.error(e);
                return false;
            }
            return true;

        } else {
            CommandHandlers.broadcastMessage(new TranslatableComponent("gui.twitchmod.chat.connecting_to", BotConfig.CHANNEL_NAME).withStyle(ChatFormatting.DARK_PURPLE));
        }

        try {

            // Twitch4J setup
            OAuth2Credential credential = new OAuth2Credential("twitch", BotConfig.TWITCH_KEY);
            twitchClient = TwitchClientBuilder.builder()
                    .withEnableChat(true).withChatAccount(credential)
                    .withDefaultEventHandler(SimpleEventHandler.class)
                    .build();


            botThread = new Thread(() -> {

                twitchClient.getChat().joinChannel(BotConfig.CHANNEL_NAME);
                twitchClient.getChat().connect();

            });

            botThread.start();
            return true;

        } catch (Exception e) {
            Main.logger.error(e);
            CommandHandlers.broadcastMessage(new TranslatableComponent("exception.twitchmod.connection_error", e));
            return false;
        }
    }

    public static boolean isConnected() {

        if (twitchClient != null) {
            return twitchClient.getChat().getConnectionState().equals(TMIConnectionState.CONNECTED);
        } else {
            return false;
        }

    }

    public static void disconnectBot() {

        twitchClient.getChat().disconnect();
        botThread.interrupt();

    }

}