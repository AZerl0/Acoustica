package com.azerl0.acoustica;

import com.azerl0.acoustica.command.AcousticaCommand;
import com.azerl0.acoustica.command.SongCommand;
import com.azerl0.acoustica.command.SongItemCommand;
import com.azerl0.acoustica.command.SpeakerCommand;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

class AcousticaBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(final BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            AcousticaCommand.register(event.registrar());
            SpeakerCommand.register(event.registrar());
            SongCommand.register(event.registrar());
            SongItemCommand.register(event.registrar());
        }));
    }
}
