package io.ckgxrg.i3m.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.sound.BlockSoundGroup;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.block.ShapeContext;

import io.ckgxrg.i3m.block.blockentity.SCPostBlockEntity;

public class SuperconductPost extends MQTTBlock {
	
	public enum Status implements StringIdentifiable {
		EMPTY("empty"),
		PARTIAL("partial"),
		FULL("full");

		String name;
		public String asString() { return name; }
		private Status(String name) { this.name = name; }
	}
	
	// The status of the SuperconductPost
	public static final EnumProperty<Status> STATUS = EnumProperty.of("status", Status.class, Status.EMPTY, Status.PARTIAL, Status.FULL);
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> manager) {
		super.appendProperties(manager);
		manager.add(STATUS);
	};

	public SuperconductPost() {
		super(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CHAIN).nonOpaque());
	}

	@Override
    	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        	return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.8125f, 1f, 0.8125f);
    	}

	@Override
    	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        	return new SCPostBlockEntity(pos, state);
    	}
	@Override
    	protected MapCodec<? extends SuperconductPost> getCodec() {
        	return createCodec(settings -> {
			return new SuperconductPost();
		});
    	}

}
