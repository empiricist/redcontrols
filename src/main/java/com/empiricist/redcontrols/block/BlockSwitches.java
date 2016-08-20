package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.utility.ChatHelper;
import com.empiricist.redcontrols.utility.LogHelper;
import com.enderio.core.common.util.DyeColor;
import crazypants.enderio.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.redstone.IRedstoneConduit;
import crazypants.enderio.conduit.redstone.RedstoneConduitNetwork;
import crazypants.enderio.conduit.redstone.Signal;
import crazypants.enderio.conduit.redstone.SignalSource;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BlockSwitches extends BlockBundledEmitter {

    //public static int defaultMeta; //so it renders the right side as item
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger VERTICAL = PropertyInteger.create("vertical", 0, 2);

    public BlockSwitches(){
        super(Material.ROCK);
        this.setHardness(3f);
        name = "switchPanel";
        this.setUnlocalizedName(name);
        //defaultMeta = 3;
        //setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VERTICAL, 1));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySwitches();
    }


//    //to tell tileentity it is activated
//    @Override
//    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
//        TileEntity tile = world.getTileEntity(x, y, z);
//        if (tile != null && tile instanceof TileEntityWarpCore) {
//            TileEntityWarpCore warpCore = (TileEntityWarpCore)tile;
//            warpCore.signal = world.isBlockIndirectlyGettingPowered(x, y, z);
//        }
//    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing face, float clickX, float clickY, float clickZ){
        super.onBlockActivated(world, pos, state, playerIn, hand, heldItem, face, clickX, clickY, clickZ);//EIO

        world.notifyBlockUpdate(pos, state, state, 3);//markBlockForUpdate( pos ); // Makes the server call getDescriptionPacket for a full data sync

        if( activeFace(state) != face){ //only does special things if you click on the face with the switches
            return false;
        }

        //if( !world.isRemote ){ ChatHelper.sendText(player, "side: " + face + " f0: " + clickX + " f1: " + clickY + " f2: " + clickZ );}//+ " faceX: " + faceX + " faceY: " + faceY + " button: " + button);}

        if(!world.isRemote){
            double faceX = 0;
            double faceY = 0;

            switch ( face.ordinal() ) {
                case 0: //bottom
                    switch( state.getValue(FACING).getHorizontalIndex() ){
                        case 0: //south
                            faceX = clickX;
                            faceY = 1-clickZ;
                            break;
                        case 1: //west
                            faceX = clickZ;
                            faceY = clickX;
                            break;
                        case 2: //north
                            faceX = 1-clickX;
                            faceY = clickZ;
                            break;
                        case 3: //east
                            faceX = 1-clickZ;
                            faceY = 1-clickX;
                            break;
                    }
                    break;
                case 1: //top
                    switch( state.getValue(FACING).getHorizontalIndex() ){
                        case 0: //south
                            faceX = clickX;
                            faceY = clickZ;
                            break;
                        case 1: //west
                            faceX = clickZ;
                            faceY = 1-clickX;
                            break;
                        case 2: //north
                            faceX = 1-clickX;
                            faceY = 1-clickZ;
                            break;
                        case 3: //east
                            faceX = 1-clickZ;
                            faceY = clickX;
                            break;
                    }
                    break;
                case 2: //north
                    faceX = 1 - clickX;
                    faceY = 1 - clickY;
                    break;
                case 3: //south
                    faceX = clickX;
                    faceY = 1 - clickY;
                    break;
                case 4: //west
                    faceX = clickZ;
                    faceY = 1 - clickY;
                    break;
                case 5: //east
                    faceX = 1 - clickZ;
                    faceY = 1 - clickY;
            }

            //which switch did they click?
            double bezel = 2.0/16;
            int button = -1;
            if( (faceX > bezel) && (faceX < (1 - bezel)) && (faceY > bezel) && (faceY < (1 - bezel))){
                int bx = (int)((faceX - bezel)*16/3);
                int by = (int)((faceY - bezel)*16/3);
                //LogHelper.info("bx " + bx + " by " + by + " button " + button + " bezel " + bezel);
                button = bx + 4 * by;
            }
            if(button != -1){
                //LogHelper.info("Playing sound");
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.2F, 0.7F);
            }

            //tell the tileentity which was clicked
            TileEntity tile = world.getTileEntity( pos );
            if (tile != null && tile instanceof TileEntitySwitches) {
                TileEntitySwitches switchPanel = (TileEntitySwitches)tile;
                switchPanel.setSignal(button, !switchPanel.getSignal(button));  //toggle switch state
                //Minecraft.getMinecraft().getNetHandler().addToSendQueue(warpCore.getDescriptionPacket());
                //world.markBlockForUpdate(x, y, z);

                //player.openGui(RedControls.instance, 0, world, x, y, z);
            }

            world.notifyNeighborsOfStateChange(pos, this);
        }

        return true;//something happened
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(world, pos, entity)).withProperty(VERTICAL, getVerticalFromEntity(world, pos, entity)), 2);
    }


    public EnumFacing activeFace(IBlockState state){ //which face are the buttons on for this state?
        switch (state.getValue(VERTICAL)) {
            case 0:
                return EnumFacing.DOWN;
            case 1:
                return state.getValue(FACING);
            case 2:
                return EnumFacing.UP;
        }
        return EnumFacing.UP;
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
        int vertical = ((meta >> 2) & 3)%3; //upper 2 bits handle up/flat/down (mod 3 is for safety I guess)
        return getDefaultState().withProperty(FACING, facing).withProperty(VERTICAL, vertical);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer( this, new IProperty[]{VERTICAL, FACING} );
    }

    /*
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, defaultMeta));
    }

    @Override
    public int damageDropped( int meta ){
        return defaultMeta;
    }

*/
//    //public boolean canRenderInPass(int pass)
//    {
//        return true;
//    }

//    @Override
//    public boolean isOpaqueCube(){
//        return false;
//    }

    /*--
    @Override
    public void onNeighborBlockChange(World world, int xCoord, int yCoord, int zCoord, Block block) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, block);
        world.setBlockToAir(xCoord, yCoord, zCoord);
    }
    --*/
}
