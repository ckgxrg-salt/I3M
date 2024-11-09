package io.ckgxrg.i3m;

import io.ckgxrg.i3m.block.I3MBlocks;
import io.ckgxrg.i3m.block.blockentity.I3MBlockEntities;
import io.ckgxrg.i3m.command.I3MCommands;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I3M implements ModInitializer {
  public static final String MOD_ID = "i3m";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    LOGGER.info("<I3M>: Improved IoT In Minecraft Initialised.");
    I3MBlocks.init();
    I3MBlockEntities.init();
    I3MCommands.init();
  }
}
