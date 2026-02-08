package ru.floerka.placeholders.api;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import ru.floerka.placeholders.PluginMain;
import ru.floerka.placeholders.manager.exceptions.PlaceholderRegisterException;
import ru.floerka.placeholders.manager.models.ExecutePlaceholder;

public abstract class CustomPlaceholder {

    public abstract String getAuthor();
    public abstract String getPrefix();


    public String onServerRequest(ExecutePlaceholder placeholder) {
        return null;
    }
    public String onPlayerRequest(PlayerRef playerRef, ExecutePlaceholder placeholder) {
        if(!playerRef.isValid())
            return onServerRequest(placeholder);
        return null;
    }

    public void register() {
        PluginMain.getInstance().getExpansionManager().register(this, false);
    }

    public boolean canRegister() {
        try {
            PluginMain.getInstance().getExpansionManager().register(this, true);
            return true;
        } catch (PlaceholderRegisterException ignore) {
            return false;
        }
    }

}
