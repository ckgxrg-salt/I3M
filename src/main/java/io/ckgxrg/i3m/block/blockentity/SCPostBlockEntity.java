package io.ckgxrg.i3m.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import io.ckgxrg.i3m.block.SuperconductPost;

public class SCPostBlockEntity extends MQTTBlockEntity {
	
	public static final int MAX_CAPACITY = 6;

	public SCPostBlockEntity(BlockPos pos, BlockState state) {
		super(I3MBlockEntities.SCPOST, pos, state);
		this.value = "0";
	}

	/* Update method for Xtinguisher, which will convert the real-life mass into
	 * Minecraft blockstates, then apply it to the block.
	 *
	 * 超导桩的更新方法, 能够根据现实世界质量判断充电桩状态, 
	 * 然后将其应用到方块。*/
	@Override
	public void update(String value) {
		super.update(value);
		sync();
	}
	// Usually we don't need to invoke this alone.
	@Override
	public void sync() {
		SuperconductPost.Status status;
		if(Integer.valueOf(this.value) == 0) status = SuperconductPost.Status.EMPTY;
		else if(Integer.valueOf(this.value) < MAX_CAPACITY) status = SuperconductPost.Status.PARTIAL;
		else status = SuperconductPost.Status.FULL;
		World world = this.getWorld();
		BlockPos pos = this.getPos();
		world.setBlockState(pos, world.getBlockState(pos).with(SuperconductPost.STATUS, status));
	}

}
