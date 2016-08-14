package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCoverButton extends BlockButton{

    protected String name;
    public boolean covered;

    protected static float f2 = 3 / 16.0F;
    protected static final AxisAlignedBB COVER_EAST_AABB =  new AxisAlignedBB(0.0F, 0.3125F, 0.25F, f2, 0.6875F, 0.75F);
    protected static final AxisAlignedBB COVER_WEST_AABB =  new AxisAlignedBB(1.0F - f2, 0.3125F, 0.25F, 1.0F, 0.6875F, 0.75F);
    protected static final AxisAlignedBB COVER_NORTH_AABB = new AxisAlignedBB(0.25F, 0.3125F, 1.0F - f2, 0.75F, 0.6875F, 1.0F);
    protected static final AxisAlignedBB COVER_SOUTH_AABB = new AxisAlignedBB(0.25F, 0.3125F, 0.0F, 0.75F, 0.6875F, f2);
    protected static final AxisAlignedBB COVER_UP_AABB =    new AxisAlignedBB(0.25F, 0.0F, 0.3125F, 0.75F, 0.0F + f2, 0.6875F);
    protected static final AxisAlignedBB COVER_DOWN_AABB =  new AxisAlignedBB(0.25F, 1.0F - f2, 0.3125F, 0.75F, 1.0F, 0.6875F);


    public BlockCoverButton(boolean covered) {
        super(false);
        this.covered = covered;
        this.setCreativeTab( covered ? CreativeTabRedControls.RED_CONTROLS_TAB : null);
        name = (covered ? "coverButton" : "uncoverButton");
        this.setUnlocalizedName(name);

    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);//stone button sound
    }

    @Override
    protected void playReleaseSound(World worldIn, BlockPos pos) {
        worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);//stone button sound
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.coverButton);//even if you break it while it's uncovered it should drop a covered button
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if( playerIn.isSneaking() ){ //player interacts with cover
            if( covered ){ //glass cover is over the button
                worldIn.setBlockState(pos, ModBlocks.uncoverButton.getDefaultState().withProperty(POWERED, state.getValue(POWERED)).withProperty(FACING, state.getValue(FACING)), 3);//set to open cover version
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 0.2F, 1.2F);
                //worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.door_open", 0.2F, 1.2F);
            }else{
                worldIn.setBlockState(pos, ModBlocks.coverButton.getDefaultState().withProperty(POWERED, state.getValue(POWERED)).withProperty(FACING, state.getValue(FACING)), 3);//set to closed cover version
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 0.2F, 1.2F);
                //worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.door_close", 0.2F, 1.2F);
            }
            return true;
        }else{ //player tries to press button
            if(!covered){ //button is open to be pressed
                if (((Boolean)state.getValue(POWERED)).booleanValue()) //button is already pressed
                {
                    return true;
                }
                else //button is not pressed yet
                {
                    worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3); //press button
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                    playClickSound(playerIn, worldIn, pos);
                    this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
                    worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
                    worldIn.scheduleUpdate(pos, ModBlocks.coverButton, this.tickRate(worldIn));//the player may re-cover the button before it un-presses, which causes it to stick if no update happens for covered button
                    return true;
                }
            }
            return false;
        }
    }

    private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.updateBlockBounds(state);
    }

    private AxisAlignedBB updateBlockBounds(IBlockState state)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
//        boolean flag = ((Boolean)state.getValue(POWERED)).booleanValue();
//        float f = 0.25F;
//        float f1 = 0.375F;
//
//        float f3 = 0.125F;
//        float f4 = 0.1875F;

        switch (enumfacing)
        {
            case EAST:
                return COVER_EAST_AABB;
            case WEST:
                return COVER_WEST_AABB;
            case SOUTH:
                return COVER_SOUTH_AABB;
            case NORTH:
                return COVER_NORTH_AABB;
            case UP:
                return COVER_UP_AABB;
            case DOWN:
                return COVER_DOWN_AABB;
            default:
                return FULL_BLOCK_AABB;//should never happen
        }
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){  //so glass texture is properly transparent
        return BlockRenderLayer.CUTOUT;
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
