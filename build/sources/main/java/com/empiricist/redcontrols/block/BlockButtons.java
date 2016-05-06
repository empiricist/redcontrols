package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockButtons extends BlockSwitches{

    public BlockButtons(){
        super();
        name = "buttonPanel";
        this.setUnlocalizedName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityButtons();
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float clickX, float clickY, float clickZ){
        world.markBlockForUpdate( pos ); // Makes the server call getDescriptionPacket for a full data sync
        if( activeFace(state) != face){ return false; }

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

            double bezel = 2.0/16;
            int button = -1;
            if( (faceX > bezel) && (faceX < (1 - bezel)) && (faceY > bezel) && (faceY < (1 - bezel))){
                int bx = (int)((faceX - bezel)*16/3);
                int by = (int)((faceY - bezel)*16/3);
                //LogHelper.info("bx " + bx + " by " + by + " button " + button + " bezel " + bezel);
                button = bx + 4 * by;
            }

            TileEntity tile = world.getTileEntity( pos );
            if (tile != null && tile instanceof TileEntityButtons) {
                TileEntityButtons buttonPanel = (TileEntityButtons)tile;
                buttonPanel.setSignal(button, 30);
            }

            world.notifyNeighborsOfStateChange( pos, this );
            //world.markAndNotifyBlock(x,y,z,world.getChunkFromBlockCoords(x,z),this,this,3);
        }


        return true;
    }

}
