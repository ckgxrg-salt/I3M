package io.ckgxrg.i3m.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.ckgxrg.i3m.block.SynkLamp;

public class SynkLampBlockEntity extends MQTTBlockEntity {
	
	public static final double LIGHT_RESISTOR_UPPER_CAP = 150;

	// 0-15, Minecraft unit
	int luminance;

	public SynkLampBlockEntity(BlockPos pos, BlockState state) {
		super(I3MBlockEntities.SYNKLAMP, pos, state);
		luminance = 0;
		this.value = "0.0";
	}

	/* Update method for SynkLamp, which will convert the real-life brightness into
	 * Minecraft luminance, then apply it to the block.
	 *
	 * 同步灯的更新方法, 能够将现实世界亮度(光敏电阻)转化为Minecraft标度(0-15), 
	 * 然后将其应用到方块。*/
	@Override
	public void update(String value) {
		super.update(value);
		sync();
	}
	// Usually we don't need to invoke this alone.
	@Override
	public void sync() {
		luminance = (int)Math.floor(Double.valueOf(this.value) * 15d / LIGHT_RESISTOR_UPPER_CAP);
		if(luminance > 15) luminance = 15;
		if(luminance < 0) luminance = 0;
		World world = this.getWorld();
		BlockPos pos = this.getPos();
		world.setBlockState(pos, world.getBlockState(pos).with(SynkLamp.BRIGHTNESS, luminance));
	}

}
