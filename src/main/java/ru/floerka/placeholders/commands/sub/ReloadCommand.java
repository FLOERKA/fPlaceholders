package ru.floerka.placeholders.commands.sub;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.floerka.placeholders.PluginMain;

import java.util.concurrent.CompletableFuture;

public class ReloadCommand extends AbstractCommand {
    public ReloadCommand() {
        super("reload", "");
        requirePermission("fplaceholders.reload");
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@NotNull CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("Start reload..."));
        PluginMain.getInstance().loadManagers();
        commandContext.sendMessage(Message.raw("Success reload!"));
        return null;
    }
}
