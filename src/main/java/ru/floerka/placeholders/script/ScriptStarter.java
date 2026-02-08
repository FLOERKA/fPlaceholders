package ru.floerka.placeholders.script;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import ru.floerka.placeholders.api.PlaceholderAPI;
import ru.floerka.placeholders.script.models.ScriptArgs;
import ru.floerka.placeholders.script.models.ScriptFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ScriptStarter {



    public static List<ScriptFile> getFiles() {
        List<ScriptFile> files = new ArrayList<>();
        try {
            Path path = Path.of("mods/fPlaceholders/javascript");
            if(Files.notExists(path) || !Files.isDirectory(path)) {
                Files.createDirectories(path);
            }
            File directory = path.toFile();
            File[] scripts = directory.listFiles();
            if(scripts != null) {
                for(File script : scripts) {
                    if(!script.getName().endsWith(".js")) continue;
                    files.add(new ScriptFile(script.toPath(), script.getName().replace(".js", "")));
                }
            }
            //Files.walk(path).filter(p -> p.endsWith(".js")).forEach(p -> files.add(new ScriptFile(p, p.getFileName().toString().replace(".js", ""))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    public static String getResponse(ScriptFile scriptFile, ScriptArgs scriptArgs) {
        File script = scriptFile.getPath().toFile();
        if(!script.exists() || !script.isFile() || !script.getName().endsWith(".js")) return null;
        try (Context context = Context.newBuilder("js")
                .option("engine.WarnInterpreterOnly", "false")
                .allowHostAccess(HostAccess.ALL)
                .build()) {

            Value bindings = context.getBindings("js");
            bindings.putMember("papi", (Function<String, String>) (identifier) -> PlaceholderAPI.parse((PlayerRef) scriptArgs.get("player"), identifier));
            scriptArgs.getMap().forEach(bindings::putMember);

            Source source = Source.newBuilder("js", script).build();
            Value value = context.eval(source);
            if(value.isString()) {
                return value.asString();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
