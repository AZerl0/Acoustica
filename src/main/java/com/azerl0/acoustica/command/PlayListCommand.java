package com.azerl0.acoustica.command;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.playlist.PlayListManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class PlayListCommand {
    public static void register(Commands registrar) {
        registrar.register(
                literal("playlist")
                        .requires(source -> source.getSender().hasPermission("acoustica.playlist"))
                        .then(literal("count")
                                .executes(PlayListCommand::count))
                        .build(),
                "Play list command"
        );
    }

    private static int count(CommandContext<CommandSourceStack> context) {
        int count = getPlayListManager().getAllPlayList().size();
        context.getSource().getSender().sendMessage(count + "playlists");
        return Command.SINGLE_SUCCESS;
    }

    private static PlayListManager getPlayListManager() {
        return Acoustica.getInstance().getPlayListManager();
    }
}
