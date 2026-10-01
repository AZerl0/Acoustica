package com.azerl0.acoustica.command;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class SongCommand {
    public static void register(Commands registrar) {
        registrar.register(
                literal("song")
                        .requires(source -> source.getSender().hasPermission("acoustica.song"))
                        .then(literal("list")
                                .executes(SongCommand::list))
                        .then(literal("reload").executes(SongCommand::reload))

                        .build(),
                "Song command"
        );
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        Component message = Component.text("Songs(" + getSongManager().getSongs().size() + "):").color(NamedTextColor.AQUA);
        for (String songId : getSongManager().getSongs().keySet()) {
            Song song = getSongManager().getSong(songId);
            if (song == null) continue;
            message = message.append(Component.text("\n- ").append(song.getSongName()).append(Component.text(" (" + songId + ")")).color(NamedTextColor.WHITE));
        }

        sender.sendMessage(message);

        return Command.SINGLE_SUCCESS;
    }

    private static int reload(CommandContext<CommandSourceStack> context) {
        Acoustica.getInstance().getConfigManager().reload();
        return Command.SINGLE_SUCCESS;
    }

    private static SongManager getSongManager() {
        return Acoustica.getInstance().getSongManager();
    }

}
