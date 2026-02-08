package ru.floerka.placeholders.commands.sub;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.floerka.placeholders.api.PlaceholderAPI;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CheckCommand extends AbstractCommand {

    private final RequiredArg<List<String>> text;
    public CheckCommand() {
        super("check", "Parse placeholder text");
        addAliases("parse");
        requirePermission("fplaceholders.check");
        text = withListRequiredArg("name", "", ArgTypes.STRING);
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@NotNull CommandContext commandContext) {
        List<String> arg = commandContext.get(text);
        if(!arg.isEmpty()) {
            String text = String.join(" ", arg);
            commandContext.sendMessage(Message.raw(PlaceholderAPI.parse(text)));
        }
        return null;
    }
}
