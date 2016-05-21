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
        func_149647_a(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "bigLever";
        this.func_149663_c(name);
    }

    @Override
    public void func_180654_a(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.1875F;// 3/16

        switch ((BlockLever.EnumOrientation)worldIn.func_180495_p(pos).func_177229_b(field_176360_a))
        {
            case EAST:
                this.func_149676_a(0.0F, 0.2F, f, f*4.0F, 0.8F, 1.0F - f);
                break;
            case WEST:
                this.func_149676_a(1.0F - f*4.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
                break;
            case SOUTH:
                this.func_149676_a(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f*4.0F);
                break;
            case NORTH:
                this.func_149676_a(f, 0.2F, 1.0F - f*4.0F, 1.0F - f, 0.8F, 1.0F);
                break;
            case UP_Z:
            case UP_X:
                f = 0.25F;
                this.func_149676_a(f, 0.0F, 0.5F - f, 1.0F - f, 0.8F, 0.5F + f);
                break;
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.func_149676_a(f, 0.2F, 0.5F - f, 1.0F - f, 1.0F, 0.5F + f);
        }
    }

    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.field_72995_K) {
            return true;
        }
        else
        {
            state = state.func_177231_a(field_176359_b);
            worldIn.func_180501_a(pos, state, 3);
            worldIn.func_72908_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, "random.click", 0.3F, ((Boolean)state.func_177229_b(field_176359_b)).booleanValue() ? 0.2F : 0.1F);//lower pitch
            worldIn.func_175685_c(pos, this);
            EnumFacing enumfacing = ((BlockLever.EnumOrientation)state.func_177229_b(field_176360_a)).func_176852_c();
            worldIn.func_175685_c(pos.func_177972_a(enumfacing.func_176734_d()), this);
            return true;
        }
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
