package io.ckgxrg.i3m;

import net.fabricmc.api.ClientModInitializer;

import io.ckgxrg.i3m.block.I3MBlocks;
import net.minecraft.client.render.RenderLayer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class I3MClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		I3M.LOGGER.info("<I3M - Client>: Registering Blocks.");
		BlockRenderLayerMap.INSTANCE.putBlock(I3MBlocks.SYNKLAMP, RenderLayer.getTranslucent());
	}
}
