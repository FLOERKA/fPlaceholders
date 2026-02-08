package ru.floerka.placeholders.manager;

import ru.floerka.placeholders.PluginMain;
import ru.floerka.placeholders.api.CustomPlaceholder;
import ru.floerka.placeholders.manager.exceptions.PlaceholderRegisterException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class ExpansionManager {

    private final List<CustomPlaceholder> registeredPlaceholders;

    public ExpansionManager() {
        this.registeredPlaceholders = new CopyOnWriteArrayList<>();
        registerPluginFiles();
    }

    public <T extends CustomPlaceholder> void register(T expansion, boolean onlyForCheck) throws PlaceholderRegisterException {
        if(hasSimilarPlaceholder(expansion)) {
            throw new PlaceholderRegisterException("Have any placeholder with similar prefix!");
        }
        if(!onlyForCheck) {
            PluginMain.getPluginLogger().at(Level.INFO).log("Register new placeholder (" + expansion.getAuthor()+":"+expansion.getPrefix()+")");
            registeredPlaceholders.add(expansion);
        }
    }

    private void registerPluginFiles() {
        try {
            Path directory = Path.of("mods/fPlaceholders/plugins");
            if(Files.notExists(directory) || !Files.isDirectory(directory)) {
                Files.createDirectories(directory);
            }

            File directoryFile = directory.toFile();
            File[] plugins = directoryFile.listFiles();
            if(plugins != null) {
                for(File plugin: plugins) {

                    if(!plugin.getName().endsWith(".jar")) continue;
                    loadJar(plugin);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadJar(File file) throws Exception {
        URL jarUrl = file.toURI().toURL();

        URLClassLoader loader = new URLClassLoader(
                new URL[]{jarUrl},
                this.getClass().getClassLoader()
        );

        try (JarFile jar = new JarFile(file)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class")) {
                    String className = name.replace("/", ".").replace(".class", "");

                    try {
                        Class<?> cls = Class.forName(className, true, loader);

                        if (isExpansion(cls)) {
                            Object instance = cls.getDeclaredConstructor().newInstance();
                            registeredPlaceholders.add((CustomPlaceholder) instance);
                            PluginMain.getPluginLogger().at(Level.INFO).log("Register new plugin (" + file.getName()+")");
                        }
                    } catch (Throwable ignore) {}
                }
            }
        }
    }

    private boolean isExpansion(Class<?> cls) {
        return CustomPlaceholder.class.isAssignableFrom(cls) && !cls.isInterface() && !java.lang.reflect.Modifier.isAbstract(cls.getModifiers());
    }

    private <T extends CustomPlaceholder> boolean hasSimilarPlaceholder(T expansion) {
        return registeredPlaceholders.stream().anyMatch(pl -> pl.getPrefix().equals(expansion.getPrefix()));
    }

    public List<CustomPlaceholder> getRegisteredPlaceholders() {
        return registeredPlaceholders;
    }

    public Optional<CustomPlaceholder> getExecutor(String prefix) {
        return registeredPlaceholders.stream().filter(cp -> cp.getPrefix().equals(prefix)).findAny();
    }
}
