package com.azerl0.acoustica.command;

import com.azerl0.acoustica.songitem.SongItem;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;

public class SongItemCommand {

    public static void register(Commands registrar) {
        registrar.register(
                literal("songitem")
                        .requires(source -> source.getSender().hasPermission("acoustica.songitem"))
                        .then(literal("setsong")
                                .then(argument("songId", StringArgumentType.word())
                                        .executes(SongItemCommand::setSong)))

                        .build(),
                "Song item command"
        );
    }

    private static int setSong(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (sender instanceof Player player) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (itemStack.isEmpty()) {
                player.sendMessage("このコマンドは手にアイテムを持って実行する必要があります");
            } else {
                String songId = context.getArgument("songId", String.class);
                SongItem.setSongId(itemStack, songId);
                player.sendMessage("songId: " + songId + "を設定しました");
            }
        } else {
            sender.sendMessage("このコマンドはプレイヤーからしか実行できません");
        }

        return Command.SINGLE_SUCCESS;
    }
}
