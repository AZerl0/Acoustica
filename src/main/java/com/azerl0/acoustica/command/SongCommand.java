package com.azerl0.acoustica.command;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;

public class SongCommand {
    public static void register(Commands registrar) {
        registrar.register(
                literal("song")
                        .requires(source -> source.getSender().hasPermission("acoustica.song"))
                        .then(literal("list")
                                .executes(SongCommand::list))
                        .then(literal("reload").executes(SongCommand::reload))
                        .then(literal("info").then(argument("songId", StringArgumentType.word())
                                .executes(SongCommand::info)))

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

    private static int info(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        String id = context.getArgument("songId", String.class);
        Song song = getSongManager().getSong(id);
        if (song == null) {
            sender.sendMessage(String.format("There is no song with the ID \"%s\".", id));
        } else {
            sender.sendMessage(getSongInfoMessage(song));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static SongManager getSongManager() {
        return Acoustica.getInstance().getSongManager();
    }

    private static Component getSongInfoMessage(@NotNull Song song) {
        Component message = Component.text("Song info (id: " + song.getId() + ")").color(NamedTextColor.AQUA);
        message = message.append(Component.text("\n- name: ").append(song.getSongName())).color(NamedTextColor.WHITE);
        message = message.append(Component.text("\n- fileName: " + song.getFileName())).color(NamedTextColor.WHITE);
        message = message.append(Component.text("\n- volume: " + song.getVolume())).color(NamedTextColor.WHITE);
        message = message.append(Component.text("\n- notes: " + song.getNotes().size())).color(NamedTextColor.WHITE);
        message = message.append(Component.text("\n- advancements: ")).color(NamedTextColor.WHITE);
        for (String advancementKey : song.getAdvancementKeys()) {
            message = message.append(Component.text("\n    - " + advancementKey)).color(NamedTextColor.WHITE);
        }
        return message;
    }
}
