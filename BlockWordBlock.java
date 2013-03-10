package nurseangel.WordBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWordBlock extends Block {

	// 使用するブロックのメタデータの個数 (0-9で10、A-Mで13)
	private int blockIndexMax;

	/**
	 * コンストラクタ
	 *
	 * @param int ブロックID
	 * @param String
	 *            素材ファイル名(MinecraftForgeClient.preloadTextureしたもの)
	 * @param int 使用するテクスチャの開始地点
	 * @param int 使用するテクスチャ数(↑からの個数)
	 */
	public BlockWordBlock(int par1, int blockIndexMin, int blockIndexMax) {
		super(par1, Material.wood);

		setTextureFile(Reference.TEXTURE_FILE);

		// 使用するテクスチャの開始番号と使用数
		this.blockIndexInTexture = blockIndexMin;
		this.blockIndexMax = blockIndexMax;

		setHardness(1.0F);
		setLightValue(1.0F);
		setRequiresSelfNotify();

		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * 使用するテクスチャ 設置時
	 *
	 * @param IBlockAccess
	 * @param int i,j,k XYZ
	 * @param int 方向
	 *
	 */
	@Override
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int i, int j, int k, int side) {
		// 上下は変更無し
		if (side < 2) {
			return this.blockIndexInTexture;
		}
		// その他 上下(=無地)+1から+16まで使用可能
		int m = par1IBlockAccess.getBlockMetadata(i, j, k);
		return this.blockIndexInTexture + m + 1;
	}

	//

	/**
	 * 使用するテクスチャ番号。手持ち時
	 *
	 * @param int 方向
	 * @param int メタデータ
	 */
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (side < 2) {
			return this.blockIndexInTexture;
		}
		return this.blockIndexInTexture + 1;
	}

	/**
	 * 右クリック時に呼ばれる
	 *
	 * @param World
	 * @param int i,j,k XYZ
	 * @param EntityPlayer
	 * @param int/float par6,par7,par8,par9 ???
	 */
	@Override
	public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (entityplayer.isSneaking()) {
			// シフトが押されていれば通常の右クリック
			super.onBlockActivated(par1World, i, j, k, entityplayer, par6, par7, par8, par9);
			return false;
		} else {
			// 押されていなければこのブロックの右クリック
			this.blockRightClick(par1World, i, j, k);
			return true;
		}
	}

	/**
	 * 左クリック時に呼ばれる
	 *
	 * @param World
	 * @param int i,j,k XYZ
	 * @param EntityPlayer
	 */
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()) {
			// シフトが押されていれば通常の左クリック
			super.onBlockClicked(world, i, j, k, entityplayer);
			return;
		} else {
			// 押されていなければこのブロックの左クリック
			this.blockLeftClick(world, i, j, k);
			return;
		}
	}

	/**
	 * 何tickごとに呼ばれるか。 これだけでは動かず、scheduleBlockUpdateでセットしないといけない。
	 * つまり、これ指定する意味あんまりないんじゃね。
	 */
	@Override
	public int tickRate() {
		return 4;
	}

	/**
	 * 隣接ブロックに変化があったときに呼ばれる。どの方向から呼ばれたかは、調べないとわからない
	 *
	 * @param world
	 * @param int i,j,k XYZ
	 * @param int 呼び出したブロックのBlockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {

		// 隣接ブロックがソウルサンドだった場合
		if (par5 == Block.slowSand.blockID) {
			// メタデータをリセットして終了
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
			return;
		}

		/*
		 * レッドストーンパウダー経由による入力があった場合、同一?tick上で何故か何度も呼ばれてしまう
		 * そこでレッドストーン入力がなされた場合は4tick後にupdateTickを呼び出すようにする
		 * scheduleBlockUpdateは何回呼んでもupdateTickが呼ばれるのは一度だけみたい
		 *
		 * ここらへんはディスペンサーのコピペ
		 */

		// 引数のブロックがレッドストーン動力を伝える材質であれば
		if (par5 > 0 && Block.blocksList[par5].canProvidePower()) {
			// 自分もしくはその下のブロックにレッドストーン動力が来ていれば
			boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
			if (var6) {
				// 4tick後にupdateTickを呼び出す
				par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
			}
		}
	}

	/**
	 * scheduleBlockUpdate()でセットすると、tickRate()後に呼び出される ディスペンサーのコピペ
	 *
	 * @param world
	 * @param int i,j,k XYZ
	 * @param Random
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote
				&& (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4))) {
			this.blockRightClick(par1World, par2, par3, par4);
		}

	}

	/**
	 * 左クリックした
	 *
	 * @param world
	 * @param int i,j,k XYZ
	 */
	public void blockLeftClick(World world, int i, int j, int k) {
		// メタデータ
		int m = world.getBlockMetadata(i, j, k);
		// メタデータを-1する、ただし0であれば最大値に
		if (m <= 0) {
			m = blockIndexMax;
		} else {
			m--;
		}
		world.setBlockMetadataWithNotify(i, j, k, m);
		return;
	}

	/**
	 * 右クリックした
	 *
	 * @param world
	 * @param int i,j,k XYZ
	 */
	public void blockRightClick(World world, int i, int j, int k) {
		// メタデータ
		int m = world.getBlockMetadata(i, j, k);
		// メタデータを+1する、ただし最大値であれば0に
		if (m >= blockIndexMax) {
			m = 0;
		} else {
			m++;
		}
		world.setBlockMetadataWithNotify(i, j, k, m);
		return;
	}

}
