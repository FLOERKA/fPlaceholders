package ru.floerka.placeholders.parser;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import ru.floerka.placeholders.api.CustomPlaceholder;
import ru.floerka.placeholders.manager.models.DefaultPlaceholder;
import ru.floerka.placeholders.manager.models.ExecutePlaceholder;
import ru.floerka.placeholders.manager.models.Placeholder;
import ru.floerka.placeholders.manager.models.ScriptPlaceholder;
import ru.floerka.placeholders.script.ScriptStarter;
import ru.floerka.placeholders.script.models.ScriptArgs;
import ru.floerka.placeholders.script.models.ScriptFile;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderParser {

    public static List<ExecutePlaceholder> create(String from) {
        if(from == null || from.isEmpty() || (!from.contains("%") && (!from.contains("{") || !from.contains("}")))) {
            return Collections.emptyList();
        }

        Pattern pattern = Pattern.compile("[%{]([^%{}]+)[%}]");
        Matcher matcher = pattern.matcher(from);

        List<ExecutePlaceholder> placeholders = new ArrayList<>();
        while (matcher.find()) {
            String placeholderText = matcher.group(1);
            if(!placeholderText.contains("_")) continue;
            String[] splitText = placeholderText.split("_");
            String prefix = splitText[0];
            String args = splitText[1];

            String[] argsSplit;
            if(args.contains("_")) {
                argsSplit = args.split("_");
            } else {
                argsSplit = new String[]{args};
            }
            placeholders.add(new ExecutePlaceholder(matcher.group(), prefix, argsSplit));
        }

        return placeholders;
    }

    public static String replace(PlayerRef playerRef, String text, List<Placeholder> placeholders) {
        for(Placeholder placeholder : placeholders) {
            String fullText = placeholder.getExecutePlaceholder().getFullText();
            if(placeholder instanceof DefaultPlaceholder defaultPlaceholder) {
                CustomPlaceholder expansion = defaultPlaceholder.getExpansion();
                String toReplace;
                if(playerRef == null) {
                    toReplace = expansion.onServerRequest(placeholder.getExecutePlaceholder());
                } else {
                    toReplace = expansion.onPlayerRequest(playerRef, placeholder.getExecutePlaceholder());
                }
                if(toReplace == null) {
                    toReplace = "Unknown Placeholder";
                }
                text = text.replace(fullText, toReplace);
            } else if(placeholder instanceof ScriptPlaceholder scriptPlaceholder) {
                ScriptFile scriptFile = scriptPlaceholder.getScriptFile();
                String toReplace = ScriptStarter.getResponse(scriptFile, ScriptArgs.create().add("player", playerRef));
                if(toReplace != null) {
                    text = text.replace(fullText, toReplace);
                }
            }
        }
        return text;
    }
}
