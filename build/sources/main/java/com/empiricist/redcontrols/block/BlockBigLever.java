package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBigLever extends BlockLever {

    protected String name;

    public BlockBigLever(){
        super();
        setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "bigLever";
        this.setUnlocalizedName(name);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.1875F;// 3/16

        switch ((BlockLever.EnumOrientation)worldIn.getBlockState(pos).getValue(FACING))
        {
            case EAST:
                this.setBlockBounds(0.0F, 0.2F, f, f*4.0F, 0.8F, 1.0F - f);
                break;
            case WEST:
                this.setBlockBounds(1.0F - f*4.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
                break;
            case SOUTH:
                this.setBlockBounds(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f*4.0F);
                break;
            case NORTH:
                this.setBlockBounds(f, 0.2F, 1.0F - f*4.0F, 1.0F - f, 0.8F, 1.0F);
                break;
            case UP_Z:
            case UP_X:
                f = 0.25F;
                this.setBlockBounds(f, 0.0F, 0.5F - f, 1.0F - f, 0.8F, 0.5F + f);
                break;
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.setBlockBounds(f, 0.2F, 0.5F - f, 1.0F - f, 1.0F, 0.5F + f);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        else
        {
            state = state.cycleProperty(POWERED);
            worldIn.setBlockState(pos, state, 3);
            worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)state.getValue(POWERED)).booleanValue() ? 0.2F : 0.1F);//lower pitch
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumfacing = ((BlockLever.EnumOrientation)state.getValue(FACING)).getFacing();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
            return true;
        }
    }

    public String getName(){
        return name;
    }

    @Override
    public String getUnlocalizedName(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }
}
