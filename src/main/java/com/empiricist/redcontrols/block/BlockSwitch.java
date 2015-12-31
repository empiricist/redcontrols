package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLever;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;
import static net.minecraftforge.common.util.ForgeDirection.EAST;

public class BlockSwitch extends BlockButton{

    @SideOnly(Side.CLIENT)
    public IIcon onIcon;

    public BlockSwitch(){
        super(false);
        this.setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        setBlockName("toggleSwitch");
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random){}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return ((meta & 8) == 0)? blockIcon : onIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz)
    {
        int meta = world.getBlockMetadata(x, y, z);
        int j1 = meta & 7; //orientation?
        int k1 = 8 - (meta & 8); //state?

        k1 = (k1==0?0:8);
        //else
        {
            world.setBlockMetadataWithNotify(x, y, z, j1 + k1, 3);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
            notifyUpdate(world, x, y, z, j1);
            //world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            return true;
        }
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess worldAccess, int x, int y, int z, int side)
    {
        int meta = worldAccess.getBlockMetadata(x, y, z);

        if ((meta & 8) == 0)
        {
            return 0;
        }
        else
        {
            int face = meta & 7;
            return face == 6 && side == 1 ? 15 : (face == 5 && side == 0 ? 15 : (face == 4 && side == 2 ? 15 : (face == 3 && side == 3 ? 15 : (face == 2 && side == 4 ? 15 : (face == 1 && side == 5 ? 15 : 0)))));
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
//        if(!world.isRemote){ LogHelper.info("CanPlaceBlockOnSide " + ((dir == UP && world.isSideSolid(x, y-1, z, UP)) ||
//                (dir == DOWN && world.isSideSolid(x, y+1, z, DOWN)) ||
//                super.canPlaceBlockOnSide(world, x, y, z, side)));}
        return (dir == UP && world.isSideSolid(x, y-1, z, UP)) ||
                (dir == DOWN && world.isSideSolid(x, y+1, z, DOWN)) ||
                super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)//only used for neighbor update checks
    {
//        if(!world.isRemote){ LogHelper.info("CanPlaceBlockAt " + ((world.isSideSolid(x, y-1, z, UP)) ||
//                (world.isSideSolid(x, y+1, z, DOWN)) ||
//                super.canPlaceBlockAt(world, x, y ,z)));}
            return (world.isSideSolid(x, y-1, z, UP)) ||
                (world.isSideSolid(x, y+1, z, DOWN)) ||
                super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        int face = world.getBlockMetadata(x, y, z);
        int state = face & 8;
        face &= 7;

        ForgeDirection dir = ForgeDirection.getOrientation(side);
        if (dir == DOWN && world.isSideSolid(x, y+1, z, DOWN))
        {
            face = 5;
        }else  if (dir == UP && world.isSideSolid(x, y-1, z, UP))
        {
            face = 6;
        }else{
            face = super.onBlockPlaced(world,x,y,z,side,hitX,hitY,hitZ,meta) - state;
        }
        return face + state;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
            return;
        }

        int l = world.getBlockMetadata(x, y, z) & 7;
        boolean flag = false;

        if (!world.isSideSolid(x - 1, y, z, EAST) && l == 1){
            flag = true;
        } else if (!world.isSideSolid(x + 1, y, z, WEST) && l == 2){
            flag = true;
        } else if (!world.isSideSolid(x, y, z - 1, SOUTH) && l == 3){
            flag = true;
        }else if (!world.isSideSolid(x, y, z + 1, NORTH) && l == 4){
            flag = true;
        }else if (!world.isSideSolid(x, y + 1, z, DOWN) && l == 5){
            flag = true;
        }else if (!world.isSideSolid(x, y - 1, z, UP) && l == 6){
            flag = true;
        }

        if (flag)
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    private void notifyUpdate(World world, int x, int y, int z, int meta)
    {
        world.notifyBlocksOfNeighborChange(x, y, z, this);

        if (meta == 1)
        {
            world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
        }
        else if (meta == 2)
        {
            world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
        }
        else if (meta == 3)
        {
            world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
        }
        else if (meta == 4)
        {
            world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
        }
        else if (meta == 5)
        {
            world.notifyBlocksOfNeighborChange(x, y+1, z, this);
        }
        else if (meta == 6)
        {
            world.notifyBlocksOfNeighborChange(x, y-1, z, this);
        }
        else
        {
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldAccess, int x, int y, int z)
    {
        int meta = worldAccess.getBlockMetadata(x, y, z);
        if((meta&7) > 4){
            int side = meta & 7;
            boolean state = (meta & 8) > 0;
            float f = 0.375F;
            float f1 = 0.625F;
            float f2 = 0.1875F;
            float f3 = 0.125F;

            if (state)
            {
                f3 = 0.0625F;
            }

            if (side == 6)//on top of block
            {
                this.setBlockBounds(0.5F - f2, 0.0F, f, 0.5F + f2, f3, f1);
            }else if (side == 5)//on bottom of block
            {
                this.setBlockBounds(0.5F - f2, 1.0F - f3, f, 0.5F + f2, 1.0F, f1 );
            }

//            if (side == 1)
//            {
//                this.setBlockBounds(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
//            }
//            else if (side == 2)
//            {
//                this.setBlockBounds(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
//            }
//            else if (side == 3)
//            {
//                this.setBlockBounds(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
//            }
//            else if (side == 4)
//            {
//                this.setBlockBounds(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
//            }
        }else{
            super.setBlockBoundsBasedOnState(worldAccess,x,y,z);
        }
    }


    @Override
    public String getUnlocalizedName(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister){
        //this assumes file name is same as name
        blockIcon = iconRegister.registerIcon( getUnwrappedUnlocalizedName( this.getUnlocalizedName() ) );
        onIcon = iconRegister.registerIcon( Reference.MOD_ID + ":toggleSwitchOn" );
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

}
