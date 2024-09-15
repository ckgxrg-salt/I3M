package io.ckgxrg.i3m.block;

import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import io.ckgxrg.i3m.I3M;

public class I3MBlocks {

	public static Block SYNKLAMP = register(new SynkLamp(), "synk_lamp", true);
	public static Block XTINGUISHER = register(new Xtinguisher(), "xtinguisher", true);
	public static Block SUPERCONDUCTPOST = register(new SuperconductPost(), "superconduct_post", true);

	public static Block register(Block blk, String name, boolean itemAlso) {
		Identifier id = Identifier.of(I3M.MOD_ID, name);
		if(itemAlso) {
			BlockItem blkItem = new BlockItem(blk, new Item.Settings());
			Registry.register(Registries.ITEM, id, blkItem);
		}
		return Registry.register(Registries.BLOCK, id, blk);
	}
	public static void init() {
		I3M.LOGGER.info("<I3M>: Registering Blocks.");
	}

}
