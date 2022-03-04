package io.github.ennuil.okzoomer.events;

import io.github.ennuil.okzoomer.config.OkZoomerConfigManager;
import io.github.ennuil.okzoomer.utils.OwoUtils;
import io.github.ennuil.okzoomer.utils.ZoomUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

// The event that makes sure to load the config and puts any load-once options in effect if enabled through the config file
public class LoadConfigEvent {
    public static void registerEvent() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            // Attempt to load the config if it hasn't been loaded yet, which is unlikely due to extra keybinds
            if (!OkZoomerConfigManager.isConfigLoaded.isPresent()) {
                OkZoomerConfigManager.loadModConfig();
            }

            // If it failed, stop. With the default settings, the conflicting key check will destroy the failed config
            if (OkZoomerConfigManager.isConfigLoaded.isPresent() && !OkZoomerConfigManager.isConfigLoaded.get()) return;
            
            // uwu
            if (OkZoomerConfigManager.configInstance.tweaks().getPrintOwoOnStart()) {
                OwoUtils.printOwo();
            }

            // This handles the unbinding of the "Save Toolbar Activator" key
            if (OkZoomerConfigManager.configInstance.tweaks().getUnbindConflictingKey()) {
                ZoomUtils.unbindConflictingKey(client, false);
                OkZoomerConfigManager.configInstance.tweaks().setUnbindConflictingKey(false);
                OkZoomerConfigManager.saveModConfig();
            }
        });
    }
}