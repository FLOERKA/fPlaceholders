package ru.floerka.placeholders.commands;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.floerka.placeholders.commands.sub.CheckCommand;
import ru.floerka.placeholders.commands.sub.ReloadCommand;

import java.util.concurrent.CompletableFuture;

public class PlaceholderCommand extends AbstractCommand {

    public PlaceholderCommand() {
        super("fplaceholers", "main command of fPlaceholders");
        addAliases("place", "papi", "placeholders");
        addSubCommand(new CheckCommand());
        addSubCommand(new ReloadCommand());
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@NotNull CommandContext commandContext) {
        return null;
    }
}
