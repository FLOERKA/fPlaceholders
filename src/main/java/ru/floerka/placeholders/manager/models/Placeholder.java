package ru.floerka.placeholders.manager.models;

public abstract class Placeholder {

    private final ExecutePlaceholder executePlaceholder;

    public Placeholder(ExecutePlaceholder executePlaceholder) {
        this.executePlaceholder = executePlaceholder;
    }

    public ExecutePlaceholder getExecutePlaceholder() {
        return executePlaceholder;
    }
}
