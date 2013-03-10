package nurseangel.WordBlocks;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import nurseangel.WordBlocks.proxy.CommonProxy;
import cpw.mods.fml.common.FMLLog;
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

	public static boolean isTest = false;

	// コンストラクタ的なもの
	@Mod.PreInit
	public void myPreInitMethod(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		int blockIdStart = 1270;

		try {
			cfg.load();
			figurePlusBlockID = cfg.getBlock("figurePlusBlockID", blockIdStart++).getInt();
			figureMinusBlockID = cfg.getBlock("figureMinusBlockID", blockIdStart++).getInt();
			wordAtoMBlockID = cfg.getBlock("wordAtoMBlockID", blockIdStart++).getInt();
			wordNtoZBlockID = cfg.getBlock("wordNtoZBlockID", blockIdStart++).getInt();
			wordSymbolBlockID = cfg.getBlock("wordSymbolBlockID", blockIdStart++).getInt();
			wordSymbol2BlockID = cfg.getBlock("wordSymbol2BlockID", blockIdStart++).getInt();

			isTest = cfg.get(Configuration.CATEGORY_GENERAL, "isTest", false, "Always false").getBoolean(false);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, Reference.MOD_NAME + " configuration loadding failed");
		} finally {
			cfg.save();
		}

		proxy.registerRenderers();
	}

	// load()なもの
	@Mod.Init
	public void myInitMethod(FMLInitializationEvent event) {

		// 各ブロックをセット
		if (figurePlusBlockID > 1) {
			this.addFigurePlusBlock();
		}
		if (figureMinusBlockID > 1) {
			this.addFigureMinusBlock();
		}
		if (wordAtoMBlockID > 1) {
			this.addWordAtoMBlock();
		}
		if (wordNtoZBlockID > 1) {
			this.addWordNtoZBlock();
		}
		if (wordSymbolBlockID > 1) {
			this.addWordSymbolBlock();
		}
		if (wordSymbol2BlockID > 1) {
			this.addWordSymbol2Block();
		}

		if (isTest) {
			this.addTestRecipes();
		}
	}

	// Block
	public static int figurePlusBlockID, figureMinusBlockID, wordAtoMBlockID, wordNtoZBlockID, wordSymbolBlockID, wordSymbol2BlockID;
	public static BlockWordBlock blockFigurePlus, blockFigureMinus, blockWordAtoM, blockWordNtoZ, blockWordSymbol, blockWordSymbol2;

	// 数字+ブロック
	private void addFigurePlusBlock() {
		blockFigurePlus = new BlockWordBlock(figurePlusBlockID, 0, 9);
		blockFigurePlus.setBlockName("blockFigurePlus");
		GameRegistry.registerBlock(blockFigurePlus, "blockFigurePlus");
		LanguageRegistry.addName(blockFigurePlus, "Figure Plus Block");

		GameRegistry.addRecipe(new ItemStack(blockFigurePlus, 1), new Object[] { "F", "I", "W", 'F', Item.feather, 'I', new ItemStack(Item.dyePowder, 1, 0),
				'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
			GameRegistry.addRecipe(new ItemStack(blockFigurePlus, 1), new Object[] { "D", 'D', Block.dirt });
		}
	}

	// 数字-ブロック
	private void addFigureMinusBlock() {
		blockFigureMinus = new BlockWordBlock(figureMinusBlockID, 16, 9);
		blockFigureMinus.setBlockName("blockFigureMinus");
		GameRegistry.registerBlock(blockFigureMinus, "blockFigureMinus");
		LanguageRegistry.addName(blockFigureMinus, "Figure Minus Block");

		GameRegistry.addRecipe(new ItemStack(blockFigureMinus, 1), new Object[] { "FFF", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
			GameRegistry.addRecipe(new ItemStack(blockFigureMinus, 1), new Object[] { "DD", 'D', Block.dirt });
		}
	}

	// AからM
	private void addWordAtoMBlock() {
		blockWordAtoM = new BlockWordBlock(wordAtoMBlockID, 32, 12);
		blockWordAtoM.setBlockName("blockWordAtoM");
		GameRegistry.registerBlock(blockWordAtoM, "blockWordAtoM");
		LanguageRegistry.addName(blockWordAtoM, "Word AtoM Block");

		GameRegistry.addRecipe(new ItemStack(blockWordAtoM, 1), new Object[] { "FF ", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordAtoM, 1), new Object[] { "DDD", 'D', Block.dirt });
		}
	}

	// NからZ
	private void addWordNtoZBlock() {
		blockWordNtoZ = new BlockWordBlock(wordNtoZBlockID, 48, 12);
		blockWordNtoZ.setBlockName("blockWordNtoZ");
		GameRegistry.registerBlock(blockWordNtoZ, "blockWordNtoZ");
		LanguageRegistry.addName(blockWordNtoZ, "Word NtoZ Block");

		GameRegistry.addRecipe(new ItemStack(blockWordNtoZ, 1), new Object[] { "F F", " I ", " W ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordNtoZ, 1), new Object[] { "D D", 'D', Block.dirt });
		}
	}

	// 記号1
	private void addWordSymbolBlock() {
		blockWordSymbol = new BlockWordBlock(wordSymbolBlockID, 64, 5);
		blockWordSymbol.setBlockName("blockWordSymbol");
		GameRegistry.registerBlock(blockWordSymbol, "blockWordSymbol");
		LanguageRegistry.addName(blockWordSymbol, "Word Symbol Block");

		GameRegistry.addRecipe(new ItemStack(blockWordSymbol, 1), new Object[] { "FF ", "II ", "WW ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
			GameRegistry.addRecipe(new ItemStack(blockWordSymbol, 1), new Object[] { "D  ", "D  ", 'D', Block.dirt });
		}
	}

	// 記号2
	private void addWordSymbol2Block() {
		blockWordSymbol2 = new BlockWordBlock(wordSymbol2BlockID, 80, 10);
		blockWordSymbol2.setBlockName("blockWordSymbol2");
		GameRegistry.registerBlock(blockWordSymbol2, "blockWordSymbol2");
		LanguageRegistry.addName(blockWordSymbol2, "Word Symbol2 Block");

		GameRegistry.addRecipe(new ItemStack(blockWordSymbol2, 1), new Object[] { "F F", "II ", "WW ", 'F', Item.feather, 'I',
				new ItemStack(Item.dyePowder, 1, 0), 'W', new ItemStack(Block.cloth, 1, 0) });

		if (isTest) {
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