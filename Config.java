package mods.nurseangel.wordblocks;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	// ID
	public static int figurePlusBlockID, figureMinusBlockID, wordAtoMBlockID, wordNtoZBlockID, wordSymbolBlockID, wordSymbol2BlockID;

	public static boolean isTest = false;

	/**
	 * コンストラクタ
	 *
	 * @param cfg
	 * @return
	 */
	public Config(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		readConfig(cfg);
	}

	/**
	 * コンフィグファイルから読み込み
	 *
	 * @param cfg
	 */
	private void readConfig(Configuration cfg) {

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
	}

}