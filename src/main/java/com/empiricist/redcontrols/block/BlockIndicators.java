package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.utility.ChatHelper;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;


public class BlockIndicators extends BlockBundledReceiver{

    //public static int defaultMeta; //so it renders the right side as item
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger VERTICAL = PropertyInteger.create("vertical", 0, 2);

    public BlockIndicators(){
        super(Material.rock);
        name = "indicatorPanel";
        this.setUnlocalizedName(name);
        //defaultMeta = 3;
        //setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VERTICAL, 1));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityIndicators();
    }


    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(world, pos, entity)).withProperty(VERTICAL, getVerticalFromEntity(world, pos, entity)), 2);
        updateTE(world, pos);
//        boolean closeH = (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F);
//
//        double d0 = entity.posY + 1.82D - (double) entity.yOffset;
//
//        if (closeH && (d0 - (double) y > 2.0D)) {
//            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
//            return;
//        } else if (closeH && ((double) y - d0 > 0.0D)) {
//            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
//            return;
//        }
//
//
//        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//        int m = (l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0))));
//        world.setBlockMetadataWithNotify(x, y, z, m, 2);
//
//        updateTE(world, pos);
    }

    public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        return entityIn.getHorizontalFacing().getOpposite();
    }

    public static int getVerticalFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F) //up or down
        {
            double d0 = entityIn.posY + (double)entityIn.getEyeHeight();

            if (d0 - (double)clickedBlock.getY() > 2.0D)
            {
                return 2;
            }

            if ((double)clickedBlock.getY() - d0 > 0.0D)
            {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return (state.getValue(VERTICAL) << 2) + state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);//lower 2 bits handle horizontal direction
        int vertical = ((meta >> 2) & 3)%3;//upper 2 bits handle up/flat/down (mod 3 is for safety I guess)
        return getDefaultState().withProperty(FACING, facing).withProperty(VERTICAL, vertical);
    }

//    @Override
//    public boolean isOpaqueCube(){
//        return false;
//    }

    @Override
    public BlockState createBlockState() {
        return new BlockState( this, new IProperty[]{VERTICAL, FACING} );
    }

//    @Override
//    public void getSubBlocks(Item item, CreativeTabs tab, List list){
//        list.add(new ItemStack(item, 1, defaultMeta));
//    }
//
//    @Override
//    public int damageDropped( int meta ){
//        return defaultMeta;
//    }


/*
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //top and bottom have different texture
        return (side == meta ) ? this.blockIcon : Blocks.stone_slab.getIcon(0,0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        //super.registerBlockIcons(reg);
        //LogHelper.warn(Reference.MOD_ID + ":panel");
        this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":panel");
    }
    */

//    //public boolean canRenderInPass(int pass)
//    {
//        return true;
//    }


}
