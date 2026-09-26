package com.azerl0.acoustica.command;

import com.azerl0.acoustica.Acoustica;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class AcousticaCommand {
    public static void register(Commands registrar) {
        registrar.register(
                literal("acoustica")
                        .requires(source -> source.getSender().hasPermission("acoustica.acoustica"))
                        .then(literal("version").executes(AcousticaCommand::version))
                        .then(literal("reloadconfig").executes(AcousticaCommand::reloadConfig))

                        .build(),
                "Acoustica command"
        );
    }

    private static int version(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Acoustica version: " + Acoustica.getInstance().getPluginMeta().getVersion());
        return Command.SINGLE_SUCCESS;
    }

    private static int reloadConfig(CommandContext<CommandSourceStack> context) {
        Acoustica.getInstance().getConfigManager().reload();
        return Command.SINGLE_SUCCESS;
    }
}
