package io.ckgxrg.i3m.block;

import java.util.function.ToIntFunction;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.sound.BlockSoundGroup;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import io.ckgxrg.i3m.block.blockentity.SynkLampBlockEntity;

public class SynkLamp extends MQTTBlock {
	
	// The Brightness of the SynkLamp. 
	public static final IntProperty BRIGHTNESS = IntProperty.of("brightness", 0, 15);	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> manager) {
		super.appendProperties(manager);
		manager.add(BRIGHTNESS);
	};
	
	public SynkLamp() {
		super(AbstractBlock.Settings.create().luminance(currentLuminance()).sounds(BlockSoundGroup.GLASS));
	}

	@Override
    	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        	return new SynkLampBlockEntity(pos, state);
    	}
	@Override
    	protected MapCodec<? extends SynkLamp> getCodec() {
        	return createCodec(settings -> {
			return new SynkLamp();
		});
    	}

	public static ToIntFunction<BlockState> currentLuminance() {
		return state -> state.get(BRIGHTNESS);
	}

}
