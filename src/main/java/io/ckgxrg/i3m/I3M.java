package io.ckgxrg.i3m;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I3M implements ModInitializer {
	public static final String MOD_ID = "i3m";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("<I3M>: Improved IoT In Minecraft Initialised.");
	}
}
