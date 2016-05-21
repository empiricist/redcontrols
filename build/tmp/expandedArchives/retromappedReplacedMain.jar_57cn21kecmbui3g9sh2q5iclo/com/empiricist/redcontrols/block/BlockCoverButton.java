package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCoverButton extends BlockButton{

    protected String name;
    public boolean covered;

    public BlockCoverButton(boolean covered) {
        super(false);
        this.covered = covered;
        this.func_149647_a( covered ? CreativeTabRedControls.RED_CONTROLS_TAB : null);
        name = (covered ? "coverButton" : "uncoverButton");
        this.func_149663_c(name);

    }

    @Override
    public Item func_180660_a(IBlockState state, Random rand, int fortune) {
        return Item.func_150898_a(ModBlocks.coverButton);//even if you break it while it's uncovered it should drop a covered button
    }


    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if( playerIn.func_70093_af() ){ //player interacts with cover
            if( covered ){ //glass cover is over the button
                worldIn.func_180501_a(pos, ModBlocks.uncoverButton.func_176223_P().func_177226_a(field_176584_b, state.func_177229_b(field_176584_b)).func_177226_a(field_176585_a, state.func_177229_b(field_176585_a)), 3);//set to open cover version
                worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.door_open", 0.2F, 1.2F);
            }else{
                worldIn.func_180501_a(pos, ModBlocks.coverButton.func_176223_P().func_177226_a(field_176584_b, state.func_177229_b(field_176584_b)).func_177226_a(field_176585_a, state.func_177229_b(field_176585_a)), 3);//set to closed cover version
                worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.door_close", 0.2F, 1.2F);
            }
            return true;
        }else{ //player tries to press button
            if(!covered){ //button is open to be pressed
                if (((Boolean)state.func_177229_b(field_176584_b)).booleanValue()) //button is already pressed
                {
                    return true;
                }
                else //button is not pressed yet
                {
                    worldIn.func_180501_a(pos, state.func_177226_a(field_176584_b, Boolean.valueOf(true)), 3); //press button
                    worldIn.func_175704_b(pos, pos);
                    worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.click", 0.3F, 0.6F);
                    this.notifyNeighbors(worldIn, pos, (EnumFacing)state.func_177229_b(field_176585_a));
                    worldIn.func_175684_a(pos, this, this.func_149738_a(worldIn));
                    worldIn.func_175684_a(pos, ModBlocks.coverButton, this.func_149738_a(worldIn));//the player may re-cover the button before it un-presses, which causes it to stick if no update happens for covered button
                    return true;
                }
            }
            return false;
        }
    }

    private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
        worldIn.func_175685_c(pos, this);
        worldIn.func_175685_c(pos.func_177972_a(facing.func_176734_d()), this);
    }


    public void func_180654_a(IBlockAccess worldIn, BlockPos pos)
    {
        this.updateBlockBounds(worldIn.func_180495_p(pos));
    }

    private void updateBlockBounds(IBlockState state)
    {
        EnumFacing enumfacing = (EnumFacing)state.func_177229_b(field_176585_a);
        boolean flag = ((Boolean)state.func_177229_b(field_176584_b)).booleanValue();
        float f = 0.25F;
        float f1 = 0.375F;
        float f2 = 3 / 16.0F;
        float f3 = 0.125F;
        float f4 = 0.1875F;

        switch (enumfacing)
        {
            case EAST:
                this.func_149676_a(0.0F, 0.3125F, 0.25F, f2, 0.6875F, 0.75F);
                break;
            case WEST:
                this.func_149676_a(1.0F - f2, 0.3125F, 0.25F, 1.0F, 0.6875F, 0.75F);
                break;
            case SOUTH:
                this.func_149676_a(0.25F, 0.3125F, 0.0F, 0.75F, 0.6875F, f2);
                break;
            case NORTH:
                this.func_149676_a(0.25F, 0.3125F, 1.0F - f2, 0.75F, 0.6875F, 1.0F);
                break;
            case UP:
                this.func_149676_a(0.25F, 0.0F, 0.3125F, 0.75F, 0.0F + f2, 0.6875F);
                break;
            case DOWN:
                this.func_149676_a(0.25F, 1.0F - f2, 0.3125F, 0.75F, 1.0F, 0.6875F);
        }
    }


    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer func_180664_k(){  //so glass texture is properly transparent
        return EnumWorldBlockLayer.CUTOUT;
    }


    public String getName(){
        return name;
    }

    @Override
    public String func_149739_a(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_149739_a()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

}
