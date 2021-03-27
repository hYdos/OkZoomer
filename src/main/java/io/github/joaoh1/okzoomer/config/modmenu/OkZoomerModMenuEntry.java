package io.github.joaoh1.okzoomer.config.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import io.github.joaoh1.okzoomer.config.OkZoomerConfigSpruceScreen;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class OkZoomerModMenuEntry implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> new OkZoomerConfigSpruceScreen(screen);
	}
}