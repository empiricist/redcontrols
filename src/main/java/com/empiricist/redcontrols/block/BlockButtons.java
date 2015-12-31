package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockButtons extends BlockSwitches{

    public BlockButtons(){
        super();
        this.setBlockName("buttonPanel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityButtons();
    }


    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float clickX, float clickY, float clickZ){
        world.markBlockForUpdate(x, y, z); // Makes the server call getDescriptionPacket for a full data sync
        if(world.getBlockMetadata(x,y,z) != face){ return false; }

        if(!world.isRemote){
            double faceX = 0;
            double faceY = 0;
            switch (face) {
                case 0:
                    faceX = clickX;
                    faceY = clickZ;
                    break;
                case 1:
                    faceX = clickX;
                    faceY = clickZ;
                    break;
                case 2:
                    faceX = 1 - clickX;
                    faceY = 1 - clickY;
                    break;
                case 3:
                    faceX = clickX;
                    faceY = 1 - clickY;
                    break;
                case 4:
                    faceX = clickZ;
                    faceY = 1 - clickY;
                    break;
                case 5:
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

            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof TileEntityButtons) {
                TileEntityButtons buttonPanel = (TileEntityButtons)tile;
                buttonPanel.setSignal(button, 30);
            }

            world.notifyBlocksOfNeighborChange(x, y, z, this);
            //world.markAndNotifyBlock(x,y,z,world.getChunkFromBlockCoords(x,z),this,this,3);
        }


        return true;
    }

}
