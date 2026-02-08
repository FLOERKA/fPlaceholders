package ru.floerka.placeholders.manager.models;

import ru.floerka.placeholders.api.CustomPlaceholder;

public class DefaultPlaceholder extends Placeholder {

    private final CustomPlaceholder expansion;
    public DefaultPlaceholder(ExecutePlaceholder executePlaceholder, CustomPlaceholder expansion) {
        super(executePlaceholder);
        this.expansion = expansion;
    }

    public CustomPlaceholder getExpansion() {
        return expansion;
    }
}
