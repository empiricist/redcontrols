package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLever;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class BlockSwitch extends BlockButton{

    protected String name;

    public BlockSwitch(){
        super(false);
        this.func_149647_a(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "toggleSwitch";
        this.func_149663_c(name);
    }

    @Override
    public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random){}

    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float fx, float fy, float fz)
    {
        if (((Boolean)state.func_177229_b(field_176584_b)).booleanValue()) //I have no idea why they would need to do all of this, but I'll leave it in in case there's a reason
        {
            worldIn.func_180501_a(pos, state.func_177226_a(field_176584_b, Boolean.valueOf(false)), 3);
            worldIn.func_175704_b(pos, pos);
            worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.click", 0.3F, 0.6F);
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.func_177229_b(field_176585_a));
            //worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            return true;
        }
        else
        {
            worldIn.func_180501_a(pos, state.func_177226_a(field_176584_b, Boolean.valueOf(true)), 3);
            worldIn.func_175704_b(pos, pos);
            worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.click", 0.3F, 0.6F);
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.func_177229_b(field_176585_a));
            //worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            return true;
        }
//        int meta = world.getBlockMetadata(x, y, z);
//        int j1 = meta & 7; //orientation?
//        int k1 = 8 - (meta & 8); //state?
//
//        k1 = (k1==0?0:8);
//        //else
//        {
//            world.setBlockMetadataWithNotify(x, y, z, j1 + k1, 3);
//            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
//            world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
//            notifyUpdate(world, x, y, z, j1);
//            //world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
//            return true;
//        }
    }

    public void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
        worldIn.func_175685_c(pos, this);
        worldIn.func_175685_c(pos.func_177972_a(facing.func_176734_d()), this);
    }

    @Override
    public String func_149739_a(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_149739_a()));
    }
/*
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister){
        //this assumes file name is same as name
        blockIcon = iconRegister.registerIcon( getUnwrappedUnlocalizedName( this.getUnlocalizedName() ) );
        onIcon = iconRegister.registerIcon( Reference.MOD_ID + ":toggleSwitchOn" );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return ((meta & 8) == 0)? blockIcon : onIcon;
    }
*/
    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

    public String getName(){
        return name;
    }

}
