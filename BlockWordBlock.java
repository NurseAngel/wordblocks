package mods.nurseangel.wordblocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWordBlock extends Block {

	// 使用するブロックのメタデータの個数
	private int blockIndexMax;
	private String textureName;
	private Icon iconTop;
	private Icon iconBottom;

	/**
	 * コンストラクタ
	 *
	 * @param int ブロックID
	 * @param String
	 *            テクスチャに使うブロック名
	 * @param int 使用するテクスチャ数
	 */
	public BlockWordBlock(int par1, String textureName, int blockIndexMax) {
		super(par1, Material.wood);

		// 使用するテクスチャの開始番号と使用数
		this.blockIndexMax = blockIndexMax;
		this.textureName = textureName;

		setHardness(1.0F);
		setLightValue(1.0F);

		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * 使用するアイコンをセット
	 *
	 * @param iconRegister
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		int loop = 0;
		for (int i = 0; i <= blockIndexMax; i++) {
			// 素材名からアイコンファイル名を作成
			String texturePath = Reference.TEXTURE_PATH + textureName + i;
			icon[i] = iconRegister.registerIcon(texturePath);
		}

		// top,bottom
		String textureTop = Reference.TEXTURE_PATH + textureName + "Top";
		String textureBottom = Reference.TEXTURE_PATH + textureName + "Bottom";
		iconTop = iconRegister.registerIcon(textureTop);
		iconBottom = iconRegister.registerIcon(textureBottom);

	}

	// 横のテクスチャ
	Icon[] icon = new Icon[16];

	/**
	 * アイコンを取得
	 *
	 * @param 取得する方角
	 * @param メタデータ
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int direction, int metadata) {

		// 方向0、1は上下
		if (direction < 2) {
			if (direction == 0) {
				return iconBottom;
			} else {
				return iconTop;
			}
		}

		// 方向2以上はメタデータそのまま
		return icon[metadata];

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
	// @Override
	// public int tickRate() {
	// return 4;
	// }

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
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
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
				par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
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
		world.setBlockMetadataWithNotify(i, j, k, m, 2);
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
		world.setBlockMetadataWithNotify(i, j, k, m, 2);
		return;
	}

}
