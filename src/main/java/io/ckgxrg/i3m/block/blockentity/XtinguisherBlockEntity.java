package io.ckgxrg.i3m.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.ckgxrg.i3m.block.Xtinguisher;

public class XtinguisherBlockEntity extends MQTTBlockEntity {
	
	public static final int ABSENSE_MASS_CAP = 100;
	public static final int MALFUNCTION_MASS_CAP = 600;

	public XtinguisherBlockEntity(BlockPos pos, BlockState state) {
		super(I3MBlockEntities.XTINGUISHER, pos, state);
		this.value = "0.0";
	}

	/* Update method for SynkLamp, which will convert the real-life brightness into
	 * Minecraft luminance, then apply it to the block.
	 *
	 * 灭火器X型的更新方法, 能够根据现实世界质量判断灭火器状态, 
	 * 然后将其应用到方块。*/
	@Override
	public void update(String value) {
		super.update(value);
		sync();
	}
	// Usually we don't need to invoke this alone.
	@Override
	public void sync() {
		Xtinguisher.Status status = Xtinguisher.Status.GOOD;
		if(Double.valueOf(this.value) < ABSENSE_MASS_CAP) status = Xtinguisher.Status.ABSENSE;
		else if(Double.valueOf(this.value) < MALFUNCTION_MASS_CAP) status = Xtinguisher.Status.MALFUNCTION;
		World world = this.getWorld();
		BlockPos pos = this.getPos();
		world.setBlockState(pos, world.getBlockState(pos).with(Xtinguisher.STATUS, status));
	}

}
