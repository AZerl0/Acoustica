package com.azerl0.acoustica.command;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.api.speaker.Speaker;
import com.azerl0.acoustica.speaker.SpeakerImpl;
import com.azerl0.acoustica.speaker.SpeakerManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class SpeakerCommand {

    public static void register(Commands registrar) {
        registrar.register(
                literal("speaker")
                        .requires(source -> source.getSender().hasPermission("acoustica.speaker"))
                        .then(literal("count")
                                .executes(SpeakerCommand::count))
                        .then(literal("create")
                                .executes(SpeakerCommand::create))
                        .then(literal("remove")
                                .executes(SpeakerCommand::remove))
                        .then(literal("start")
                                .executes(SpeakerCommand::start))
                        .then(literal("stop")
                                .executes(SpeakerCommand::stop))
                        .then(literal("pause")
                                .executes(SpeakerCommand::pause))
                        .build(),
                "Speaker command"
        );
    }

    private static int count(CommandContext<CommandSourceStack> context) {
        int count = getSpeakerManager().getSpeakers().size();
        context.getSource().getSender().sendMessage(count + "speakers");
        return Command.SINGLE_SUCCESS;
    }

    private static int create(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getSender() instanceof Player player) {
            getSpeakerManager().createSpeaker(player.getLocation());
            player.sendMessage("create speaker");
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int remove(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getSender() instanceof Player player) {
            for (SpeakerImpl speakerImpl : getSpeakerManager().getNearbySpeakers(player.getLocation(), 3)) {
                speakerImpl.remove();
                player.sendMessage("remove speaker");
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int start(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getSender() instanceof Player player) {
            for (SpeakerImpl speakerImpl : getSpeakerManager().getNearbySpeakers(player.getLocation(), 3)) {
                speakerImpl.start();
                player.sendMessage("start");
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int stop(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getSender() instanceof Player player) {
            for (Speaker speaker : getSpeakerManager().getNearbySpeakers(player.getLocation(), 3)) {
                speaker.stop();
                player.sendMessage("stop");
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int pause(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getSender() instanceof Player player) {
            for (Speaker speaker : getSpeakerManager().getNearbySpeakers(player.getLocation(), 3)) {
                speaker.pause();
                player.sendMessage("pause");
            }
        }
        return Command.SINGLE_SUCCESS;
    }



    private static SpeakerManager getSpeakerManager() {
        return Acoustica.getInstance().getSpeakerManager();
    }
}
