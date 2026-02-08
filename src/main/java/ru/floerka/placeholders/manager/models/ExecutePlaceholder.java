package ru.floerka.placeholders.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExecutePlaceholder {

    private final String fullText;
    private final String prefix;
    private final String[] args;
}
