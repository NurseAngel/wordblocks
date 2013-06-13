package mods.nurseangel.wordblocks;

import mods.nurseangel.wordblocks.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModWordBlocks {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	Config config;

	// コンストラクタ的なもの
	@Mod.PreInit
	public void myPreInitMethod(FMLPreInitializationEvent event) {
		config = new Config(event);
	}

	// load()なもの
	@Mod.Init
	public void myInitMethod(FMLInitializationEvent event) {

		// 各ブロックをセット
		if (config.figurePlusBlockID > 1) {
			this.addFigurePlusBlock(config.figurePlusBlockID);
		}
		if (config.figureMinusBlockID > 1) {
			this.addFigureMinusBlock(config.figureMinusBlockID);
		}
		if (config.wordAtoMBlockID > 1) {
			this.addWordAtoMBlock(config.wordAtoMBlockID);
		}
		if (config.wordNtoZBlockID > 1) {
			this.addWordNtoZBlock(config.wordNtoZBlockID);
		}
		if (config.wordSymbolBlockID > 1) {
			this.addWordSymbolBlock(config.wordSymbolBlockID);
		}
		if (config.wordSymbol2BlockID > 1) {
			this.addWordSymbol2Block(config.wordSymbol2BlockID);
		}

		if (config.isTest) {
			this.addTestRecipes();
		}
	}

	// Block
	public static BlockWordBlock blockFigurePlus, blockFigureMinus, blockWordAtoM, blockWordNtoZ, blockWordSymbol, blockWordSymbol2;

	// 数字+ブロック
	private void addFigurePlusBlock(int blockId) {
		blockFigurePlus = new BlockWordBlock(blockId, "NumPlus", 9);
		blockFigurePlus.setUnlocalizedName("blockFigurePlus");
		GameRegistry.registerBlock(blockFigurePlus, "blockFigurePlus");
		LanguageRegistry.addName(blockFigurePlus, "Figure Plus Block");

		GameRegistry.addRecipe(new ItemStack(blockFigurePlus, 1), new Object[] { "F", "I", "W", 'F', Item.feather, 'I', new ItemStack(Item.dyePowder, 1, 0),
				'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockFigurePlus, 1), new Object[] { "D", 'D', Block.dirt });
		}
	}

	// 数字-ブロック
	private void addFigureMinusBlock(int blockId) {
		blockFigureMinus = new BlockWordBlock(blockId, "NumMinus", 9);
		blockFigureMinus.setUnlocalizedName("blockFigureMinus");
		GameRegistry.registerBlock(blockFigureMinus, "blockFigureMinus");
		LanguageRegistry.addName(blockFigureMinus, "Figure Minus Block");

		GameRegistry.addRecipe(new ItemStack(blockFigureMinus, 1), new Object[] { "FFF", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockFigureMinus, 1), new Object[] { "DD", 'D', Block.dirt });
		}
	}

	// AからM
	private void addWordAtoMBlock(int blockId) {
		blockWordAtoM = new BlockWordBlock(blockId, "AtoM", 12);
		blockWordAtoM.setUnlocalizedName("blockWordAtoM");
		GameRegistry.registerBlock(blockWordAtoM, "blockWordAtoM");
		LanguageRegistry.addName(blockWordAtoM, "Word AtoM Block");

		GameRegistry.addRecipe(new ItemStack(blockWordAtoM, 1), new Object[] { "FF ", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordAtoM, 1), new Object[] { "DDD", 'D', Block.dirt });
		}
	}

	// NからZ
	private void addWordNtoZBlock(int blockId) {
		blockWordNtoZ = new BlockWordBlock(blockId, "NtoZ", 12);
		blockWordNtoZ.setUnlocalizedName("blockWordNtoZ");
		GameRegistry.registerBlock(blockWordNtoZ, "blockWordNtoZ");
		LanguageRegistry.addName(blockWordNtoZ, "Word NtoZ Block");

		GameRegistry.addRecipe(new ItemStack(blockWordNtoZ, 1), new Object[] { "F F", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordNtoZ, 1), new Object[] { "D D", 'D', Block.dirt });
		}
	}

	// 記号1
	private void addWordSymbolBlock(int blockId) {
		blockWordSymbol = new BlockWordBlock(blockId, "Symbol1", 5);
		blockWordSymbol.setUnlocalizedName("blockWordSymbol");
		GameRegistry.registerBlock(blockWordSymbol, "blockWordSymbol");
		LanguageRegistry.addName(blockWordSymbol, "Word Symbol Block");

		GameRegistry.addRecipe(new ItemStack(blockWordSymbol, 1), new Object[] { "FF ", "II ", "WW ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordSymbol, 1), new Object[] { "D  ", "D  ", 'D', Block.dirt });
		}
	}

	// 記号2
	private void addWordSymbol2Block(int blockId) {
		blockWordSymbol2 = new BlockWordBlock(blockId, "Symbol2", 10);
		blockWordSymbol2.setUnlocalizedName("blockWordSymbol2");
		GameRegistry.registerBlock(blockWordSymbol2, "blockWordSymbol2");
		LanguageRegistry.addName(blockWordSymbol2, "Word Symbol2 Block");

		GameRegistry.addRecipe(new ItemStack(blockWordSymbol2, 1), new Object[] { "F F", "II ", "WW ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (config.isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordSymbol2, 1), new Object[] { "D  ", "DDD", 'D', Block.dirt });
		}
	}

	private void addTestRecipes() {
		GameRegistry.addRecipe(new ItemStack(Block.slowSand, 64), new Object[] { "DD", "DD", "DD", 'D', Block.dirt });
		GameRegistry.addRecipe(new ItemStack(Block.pistonStickyBase, 64), new Object[] { "DDD", "DDD", 'D', Block.dirt });
		GameRegistry.addRecipe(new ItemStack(Block.lever, 64), new Object[] { "D D", "DDD", 'D', Block.dirt });
		GameRegistry.addRecipe(new ItemStack(Block.redstoneWire, 64), new Object[] { "DDD", "D  ", 'D', Block.dirt });
		GameRegistry.addRecipe(new ItemStack(Block.dirt, 64), new Object[] { "DD", "DD", 'D', Block.dirt });
	}
}