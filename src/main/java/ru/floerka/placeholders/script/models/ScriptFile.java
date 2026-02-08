package ru.floerka.placeholders.script.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

@AllArgsConstructor
@Getter
public class ScriptFile {
    private Path path;
    private String name;
}
