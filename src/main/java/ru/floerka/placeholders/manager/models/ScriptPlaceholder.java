package ru.floerka.placeholders.manager.models;

import ru.floerka.placeholders.script.models.ScriptFile;

public class ScriptPlaceholder extends Placeholder {

    private final ScriptFile scriptFile;
    public ScriptPlaceholder(ExecutePlaceholder executePlaceholder, ScriptFile scriptFile) {
        super(executePlaceholder);
        this.scriptFile = scriptFile;
    }

    public ScriptFile getScriptFile() {
        return scriptFile;
    }
}
