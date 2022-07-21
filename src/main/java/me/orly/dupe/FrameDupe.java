package me.orly.dupe;

import me.orly.dupe.commands.pluginCredits;
import me.orly.dupe.modules.frameDupe;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class FrameDupe extends MeteorAddon {
	public static final Logger LOG = LoggerFactory.getLogger(FrameDupe.class);
	public static final Category CATEGORY = new Category("SweetAnarchy");

	@Override
	public void onInitialize() {
		LOG.info("Initializing Item Frame Dupe");

		// Required when using @EventHandler
		MeteorClient.EVENT_BUS.registerLambdaFactory("meteordevelopment.addons.template", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

		// Modules
		Modules.get().add(new frameDupe());

		// Commands
		Commands.get().add(new pluginCredits());
	}

	@Override
	public void onRegisterCategories() {
		Modules.registerCategory(CATEGORY);
	}
}
