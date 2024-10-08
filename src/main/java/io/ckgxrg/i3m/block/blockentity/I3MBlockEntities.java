package io.ckgxrg.i3m.block.blockentity;

import io.ckgxrg.i3m.block.I3MBlocks;
import io.ckgxrg.i3m.I3M;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class I3MBlockEntities {

	public static final BlockEntityType<SynkLampBlockEntity> SYNKLAMP =
		register("synk_lamp", BlockEntityType.Builder.create(SynkLampBlockEntity::new, I3MBlocks.SYNKLAMP).build());
	public static final BlockEntityType<XtinguisherBlockEntity> XTINGUISHER =
		register("xtinguisher", BlockEntityType.Builder.create(XtinguisherBlockEntity::new, I3MBlocks.XTINGUISHER).build());
	public static final BlockEntityType<SCPostBlockEntity> SCPOST =
		register("superconduct_post", BlockEntityType.Builder.create(SCPostBlockEntity::new, I3MBlocks.SUPERCONDUCTPOST).build());

	public static <T extends BlockEntityType<?>> T register(String name, T blockEntityType) {
  		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(I3M.MOD_ID, name), blockEntityType);
  	}

	public static void init() {
		I3M.LOGGER.info("<I3M>: Registering Block Entities.");
	}
}
