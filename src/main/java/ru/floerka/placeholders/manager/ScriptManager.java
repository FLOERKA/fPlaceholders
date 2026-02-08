package ru.floerka.placeholders.manager;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import lombok.Getter;
import ru.floerka.placeholders.PluginMain;
import ru.floerka.placeholders.script.ScriptStarter;
import ru.floerka.placeholders.script.models.ScriptArgs;
import ru.floerka.placeholders.script.models.ScriptFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class ScriptManager {

    @Getter
    private final Map<String, ScriptFile> scriptFiles;

    public ScriptManager() {
        this.scriptFiles = new ConcurrentHashMap<>();
        boot();
    }


    private void boot() {
        List<ScriptFile> paths = ScriptStarter.getFiles();
        if(paths.isEmpty()) return;
        paths.forEach(script -> {
            PluginMain.getPluginLogger().at(Level.INFO).log("Register new javascript placeholder (%javascript_" + script.getName() + "%)");
            scriptFiles.put(script.getName(), script);
        });
    }

    @Deprecated
    public String execute(String prefix, PlayerRef playerRef) {
        ScriptFile scriptFile = scriptFiles.getOrDefault(prefix, null);
        if(scriptFile == null) return null;
        return ScriptStarter.getResponse(scriptFile, ScriptArgs.create().add("player", playerRef));
    }

    public ScriptFile get(String prefix) {
        return scriptFiles.getOrDefault(prefix, null);
    }

}
