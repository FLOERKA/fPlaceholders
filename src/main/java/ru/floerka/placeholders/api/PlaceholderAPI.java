package ru.floerka.placeholders.api;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import ru.floerka.placeholders.PluginMain;
import ru.floerka.placeholders.manager.ExpansionManager;
import ru.floerka.placeholders.manager.ScriptManager;
import ru.floerka.placeholders.manager.models.DefaultPlaceholder;
import ru.floerka.placeholders.manager.models.ExecutePlaceholder;
import ru.floerka.placeholders.manager.models.Placeholder;
import ru.floerka.placeholders.manager.models.ScriptPlaceholder;
import ru.floerka.placeholders.parser.PlaceholderParser;
import ru.floerka.placeholders.script.models.ScriptFile;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderAPI {


    public static String parse(PlayerRef playerRef, String text) {
        List<ExecutePlaceholder> executePlaceholders = PlaceholderParser.create(text);
        if(executePlaceholders.isEmpty()) return text;

        List<Placeholder> withExpansions = new ArrayList<>();
        ExpansionManager expansionManager = PluginMain.getInstance().getExpansionManager();
        ScriptManager scriptManager = PluginMain.getInstance().getScriptManager();

        executePlaceholders.forEach(executePlaceholder -> {
            if(executePlaceholder.getPrefix().equalsIgnoreCase("javascript")) {
                ScriptFile scriptFile = scriptManager.get(argsToString(executePlaceholder.getArgs()));
                if(scriptFile != null)
                    withExpansions.add(new ScriptPlaceholder(executePlaceholder, scriptFile));
            } else {
                expansionManager.getExecutor(executePlaceholder.getPrefix()).ifPresent(cp -> {
                    withExpansions.add(new DefaultPlaceholder(executePlaceholder, cp));
                });
            }
        });

        return PlaceholderParser.replace(playerRef, text, withExpansions);
    }

    private static String argsToString(String[] args) {
        return String.join("_", args);
    }

    public static String parse(String text) {
        return parse(null, text);
    }
}
