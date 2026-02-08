package ru.floerka.placeholders;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.floerka.placeholders.commands.PlaceholderCommand;
import ru.floerka.placeholders.manager.ExpansionManager;
import ru.floerka.placeholders.manager.ScriptManager;

@Getter
public class PluginMain extends JavaPlugin {

    private static PluginMain instance;
    private static final HytaleLogger logger = HytaleLogger.getLogger();

    private ExpansionManager expansionManager;
    private ScriptManager scriptManager;


    public PluginMain(@NotNull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        super.setup();
        getCommandRegistry().registerCommand(new PlaceholderCommand());
    }

    @Override
    protected void start() {
        super.start();

        loadManagers();
    }

    public void loadManagers() {
        expansionManager = new ExpansionManager();
        scriptManager = new ScriptManager();
    }

    public static PluginMain getInstance() {
        return instance;
    }

    @NotNull
    public static HytaleLogger getPluginLogger() {
        return logger;
    }
}
