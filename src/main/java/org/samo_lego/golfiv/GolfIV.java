package org.samo_lego.golfiv;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.samo_lego.golfiv.commands.GolfCommand;
import org.samo_lego.golfiv.storage.GolfConfig;

import java.io.File;

public class GolfIV implements ModInitializer {

	/**
	 * GolfIV config.
	 */
	public static GolfConfig golfConfig;

	/**
	 * Initializes GolfIV.
	 */
	@Override
	public void onInitialize() {
		golfConfig = GolfConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/GolfIV_config.json"));

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			GolfCommand.registerCommand(dispatcher);
		});
	}
}
