package io.ckgxrg.i3m.block;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.entity.BlockEntity;

import io.ckgxrg.i3m.block.blockentity.MQTTBlockEntity;

public abstract class MQTTBlock extends BlockWithEntity {

	/* Indicates whether this block has subscribed to a MQTT topic.
	 * 该方块状态指定该MQTT方块是否已经订阅了一个MQTT主题。*/
	public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

	public MQTTBlock(AbstractBlock.Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(CONNECTED, false));
	}

	/* These method provide default impl for BlockWithEntity.
	 * 这些方法提供对具有方块实体方块的默认实现。*/
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> manager) {
		manager.add(CONNECTED);
	}; 
	
	@Override
    	protected BlockRenderType getRenderType(BlockState state) {
        	return BlockRenderType.MODEL;
    	}
	
	/* Should the NBT value be manually updated, right-click the block to manually apply the change.
	 * 若手动修改NBT数据, 可通过右击强制刷新数据。*/
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		BlockEntity e = world.getBlockEntity(pos);
		if(e instanceof MQTTBlockEntity)
			((MQTTBlockEntity) e).sync();
		return ActionResult.PASS;
	}
}
