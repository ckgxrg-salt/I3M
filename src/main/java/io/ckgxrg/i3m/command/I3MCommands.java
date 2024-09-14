package io.ckgxrg.i3m.command;

import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.text.Text;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import io.ckgxrg.i3m.I3M;

public class I3MCommands {
	
	public static void init() {
		I3M.LOGGER.info("<I3M>: Registering Commands.");
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
			dispatcher.register(literal("i3m").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("/i3m invoked"), false);
				return 1;
			}));
		});
	}

}
