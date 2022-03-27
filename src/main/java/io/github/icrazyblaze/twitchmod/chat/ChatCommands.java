package io.github.icrazyblaze.twitchmod.chat;

import io.github.icrazyblaze.twitchmod.CommandHandlers;
import io.github.icrazyblaze.twitchmod.integration.IntegrationWrapper;
import io.github.icrazyblaze.twitchmod.util.PlayerHelper;
import io.github.icrazyblaze.twitchmod.util.files.BlacklistSystem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

import static io.github.icrazyblaze.twitchmod.util.EffectInstanceHelper.effect;

/**
 * This class is where all the commands are registered for use in ChatPicker.
 *
 * @see io.github.icrazyblaze.twitchmod.chat.ChatPicker
 */
public class ChatCommands {
    static final Map<String, Runnable> commandMap = new HashMap<>();
    static boolean commandHasExecuted = false;

    /**
     * Adds a command to a list that ChatPicker checks.
     * The {@link #registerCommand} method takes two arguments: a runnable, and any number of command aliases.
     * <pre>
     * {@code
     *     registerCommand(() -> CommandHandlers.myCommand(), "mycommand", "mycommandalias");
     * }
     * </pre>
     * If an entry with the same runnable or alias has already been registered, it will be replaced.
     * IDEA will swap the lambda for a method reference wherever possible.
     *
     * @param runnable The function linked to the command
     * @param keys     Aliases for the command
     * @see ChatPicker
     */
    public static void registerCommand(Runnable runnable, String... keys) {

        /*
        This code is used to add multiple aliases for commands using hashmaps.
        Thank you gigaherz, very cool!
        */
        for (String key : keys) {

            // Don't register exactly the same command every time
            if (commandMap.containsKey(key) && commandMap.containsValue(runnable)) {
                commandMap.replace(key, runnable);
            } else {
                commandMap.put(key, runnable);
            }

        }

    }

    /**
     * Commands are registered here from {@link ChatPicker#doCommand}.
     */
    public static void initCommands() {

        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.POISON, 400, 0)), "poison");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.HUNGER, 400, 255)), "faim");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.MOVEMENT_SLOWDOWN, 400, 5)), "ralenti");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.BLINDNESS, 400, 0)), "aveugle");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.MOVEMENT_SPEED, 400, 10)), "vitesse");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.CONFUSION, 400, 0)), "nausee");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.DIG_SLOWDOWN, 400, 0)), "fatigue");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.LEVITATION, 200, 1)), "levitation", "ibelieveicanfly");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.LEVITATION, 400, 255)), "pastomber", "flotter");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.HEALTH_BOOST, 400, 1), effect(MobEffects.REGENERATION, 400, 1)), "regen", "heal", "health");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.SATURATION, 200, 255)), "repus", "bouffe");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.JUMP, 400, 2)), "jumpjump");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.DIG_SPEED, 400, 2)), "hate", "diggydiggy");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.BAD_OMEN, 400, 0)), "badomen", "pillager", "raid");
        registerCommand(() -> CommandHandlers.addPotionEffects(effect(MobEffects.FIRE_RESISTANCE, 800, 0), effect(MobEffects.DAMAGE_RESISTANCE, 800, 4)), "resistance");
        registerCommand(CommandHandlers::giveRandomPotionEffect, "aleatoire", "poporoulette");
        registerCommand(() -> PlayerHelper.player().removeAllEffects(), "elixir", "lait");
        registerCommand(CommandHandlers::setOnFire, "feu", "brule");
        registerCommand(CommandHandlers::floorIsLava, "lave", "floorislava");
        registerCommand(CommandHandlers::placeWater, "eau");
        registerCommand(CommandHandlers::placeSponge, "eponge");
        registerCommand(() -> CommandHandlers.deathTimer(60), "timer60");
        registerCommand(() -> CommandHandlers.deathTimer(30), "timer30");
        registerCommand(CommandHandlers::drainHealth, "drain", "abobo");
        registerCommand(CommandHandlers::spawnAnvil, "enclume"); // Gaiet's favourite command <3
        registerCommand(() -> CommandHandlers.spawnMobBehind(EntityType.CREEPER.create(PlayerHelper.player().level)), "creeper");
        registerCommand(() -> CommandHandlers.spawnMobBehind(EntityType.ZOMBIE.create(PlayerHelper.player().level)), "zombie");
        registerCommand(() -> CommandHandlers.spawnMob(EntityType.ENDERMAN.create(PlayerHelper.player().level)), "enderman");
        registerCommand(() -> CommandHandlers.spawnMobBehind(EntityType.WITCH.create(PlayerHelper.player().level)), "sorciere");
        registerCommand(() -> CommandHandlers.spawnMobBehind(EntityType.SKELETON.create(PlayerHelper.player().level)), "squelette");
        registerCommand(() -> CommandHandlers.spawnMobBehind(EntityType.SLIME.create(PlayerHelper.player().level)), "slime");
        registerCommand(CommandHandlers::spawnArmorStand, "armorstand", "armourstand", "boo");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 1.0F, 1.0F), "pchhh", "behindyou");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.ZOMBIE_AMBIENT, SoundSource.HOSTILE, 1.0F, 1.0F), "greuh", "bruh");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.SKELETON_AMBIENT, SoundSource.HOSTILE, 1.0F, 1.0F), "bruitos", "spook");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.WITCH_AMBIENT, SoundSource.HOSTILE, 1.0F, 1.0F), "hinhin", "hehe");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.GHAST_WARN, SoundSource.HOSTILE, 10.0F, 1.0F), "ghastscare", "yikes");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.PHANTOM_AMBIENT, SoundSource.HOSTILE, 10.0F, 1.0F), "phantomscare", "needsleep");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.WITHER_AMBIENT, SoundSource.HOSTILE, 10.0F, 1.0F), "witherscare", "wither");
        registerCommand(CommandHandlers::pigmanScare, "pigmanscare", "aggro");
        registerCommand(CommandHandlers::elderGuardianScare, "guardian", "guardianscare");
        registerCommand(() -> CommandHandlers.playSound(SoundEvents.ANVIL_FALL, SoundSource.BLOCKS, 1.0F, 1.0F), "bruitenclume");
        registerCommand(CommandHandlers::spawnLightning, "foudre");
        registerCommand(CommandHandlers::spawnFireball, "bouledefeu");
        registerCommand(() -> CommandHandlers.oresExplode = true, "oresexplode");
        registerCommand(() -> CommandHandlers.placeBedrockOnBreak = true, "bedrock");
        registerCommand(() -> CommandHandlers.burnVillagersOnInteract = true, "villagersburn", "burnthemall");
        registerCommand(() -> CommandHandlers.destroyWorkbenchesOnInteract = true, "nocrafting", "breakworkbench");
        registerCommand(CommandHandlers::breakBlock, "break");
        registerCommand(CommandHandlers::dismount, "dismount", "getoff");
        registerCommand(CommandHandlers::dropItem, "drop", "throw");
        registerCommand(() -> PlayerHelper.player().getInventory().dropAll(), "dropall");
        registerCommand(CommandHandlers::infestBlock, "silverfish"); // crash when in water or sand
        registerCommand(CommandHandlers::setRainAndThunder, "pluie", "makeitrain");
        registerCommand(() -> CommandHandlers.setDifficulty(Difficulty.HARD), "hard", "hardmode");
        registerCommand(() -> CommandHandlers.setDifficulty(Difficulty.EASY), "easy");
        registerCommand(() -> CommandHandlers.setDifficulty(Difficulty.NORMAL), "normal");
        registerCommand(() -> CommandHandlers.setDifficulty(Difficulty.PEACEFUL), "peaceful", "peacefulmode");
        registerCommand(CommandHandlers::placeChest, "coffre", "lootbox");
        registerCommand(() -> CommandHandlers.setTime(1000), "jour", "setday");
        registerCommand(() -> CommandHandlers.setTime(13000), "nuit", "setnight");
        registerCommand(CommandHandlers::placeCobweb, "cobweb", "stuck", "gbj");
        registerCommand(CommandHandlers::setSpawn, "spawnpoint", "setspawn");
        registerCommand(() -> CommandHandlers.surroundPlayer(Blocks.GLASS.defaultBlockState()), "glass");
        registerCommand(CommandHandlers::enchantItem, "enchant");
        registerCommand(CommandHandlers::curseArmour, "bind", "curse");
        registerCommand(CommandHandlers::startWritingBook, "book", "chatlog");
        registerCommand(CommandHandlers::toggleCrouch, "togglecrouch", "crouch");
        registerCommand(CommandHandlers::toggleSprint, "togglesprint", "sprint");
        registerCommand(CommandHandlers::pumpkin, "pumpkin");
        registerCommand(CommandHandlers::chorusTeleport, "chorusfruit", "chorus");
        registerCommand(CommandHandlers::realTeleport, "teleport");
        registerCommand(() -> CommandHandlers.changeDurability(false), "damage", "damageitem");
        registerCommand(() -> CommandHandlers.changeDurability(true), "repair", "repairitem");

    }

    /**
     * Commands that are registered here need to be re-added to the command registry every time a command is checked because they have changing ("dynamic") arguments.
     *
     * @param argString the argument for the command
     * @param sender    the name of the command sender
     */
    public static void initDynamicCommands(String argString, String sender) {

        registerCommand(() -> CommandHandlers.itemRoulette(sender), "itemroulette", "roulette");
        registerCommand(() -> CommandHandlers.shuffleInventory(sender), "shuffle");
        registerCommand(() -> CommandHandlers.showMessagebox(argString), "messagebox");
        registerCommand(() -> CommandHandlers.messagesList.add(argString), "addmessage");
        registerCommand(() -> CommandHandlers.placeSign(argString), "sign");
        registerCommand(() -> CommandHandlers.renameItem(argString), "rename");
        registerCommand(() -> CommandHandlers.rollTheDice(sender), "rtd", "roll", "dice");
        registerCommand(() -> FrenzyVote.vote(sender), "frenzy", "frenzymode", "suddendeath");

        // Mod dynamic commands
        IntegrationWrapper.initModDynamicCommands(sender);

    }

    /**
     * @return a sorted list of all the currently registered commands and their aliases
     */
    public static List<String> getRegisteredCommands() {

        List<String> commandList = new ArrayList<>();

        for (String key : commandMap.keySet()) {

            if (!BlacklistSystem.isBlacklisted(key)) {
                commandList.add(key);
            }

        }

        Collections.sort(commandList);
        return commandList;

    }

}
