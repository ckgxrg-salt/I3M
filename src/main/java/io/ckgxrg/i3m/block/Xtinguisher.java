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

import io.ckgxrg.i3m.block.blockentity.XtinguisherBlockEntity;

public class Xtinguisher extends MQTTBlock {
	
	public enum Status implements StringIdentifiable {
		ABSENSE("absense"),
		MALFUNCTION("malfunction"),
		GOOD("good");

		String name;
		public String asString() { return name; }
		private Status(String name) { this.name = name; }
	}

	// The status of the Xtinguisher
	public static final EnumProperty<Status> STATUS = EnumProperty.of("status", Status.class, Status.GOOD, Status.MALFUNCTION, Status.ABSENSE);
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> manager) {
		super.appendProperties(manager);
		manager.add(STATUS);
	};
	
	public Xtinguisher() {
		super(AbstractBlock.Settings.create().sounds(BlockSoundGroup.ANVIL).nonOpaque().luminance(state -> { return 8; }));
		setDefaultState(getDefaultState().with(STATUS, Status.GOOD));
	}

	@Override
    	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        	return VoxelShapes.cuboid(0.3125f, 0f, 0.3125f, 0.6875f, 0.75f, 0.6875f);
    	}

	@Override
    	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        	return new XtinguisherBlockEntity(pos, state);
    	}
	@Override
    	protected MapCodec<? extends Xtinguisher> getCodec() {
        	return createCodec(settings -> {
			return new Xtinguisher();
		});
    	}

}
