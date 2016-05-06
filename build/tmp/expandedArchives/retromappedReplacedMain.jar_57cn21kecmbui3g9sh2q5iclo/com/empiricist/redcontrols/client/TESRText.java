package com.empiricist.redcontrols.client;

import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.block.BlockText;
import com.empiricist.redcontrols.tileentity.ITEBundledLights;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class TESRText extends TileEntitySpecialRenderer {

    @Override
    public void func_180535_a(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        //Tessellator tessellator = Tessellator.instance;

        if (te instanceof TileEntityText) {
            TileEntityText tet = (TileEntityText) te;

            IBlockState state = te.func_145831_w().func_180495_p(te.func_174877_v());

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240f, 240f);
            GL11.glTranslated(x, y, z);

            FontRenderer fontrenderer = this.func_147498_b();

            String s = tet.getText();

            double stopZFighting = 0.01;
            switch( state.func_177229_b(BlockText.VERTICAL) ){ //does this work?
                case 0: //bottom
                    switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                        case 0: //south
                            GL11.glTranslated(0, -stopZFighting, 1);
                            GL11.glRotated(90, 1, 0, 0);  //note this also rotates coordinate system
                            break;
                        case 1: //west
                            GL11.glTranslated(0, -stopZFighting, 0);
                            GL11.glRotated(-90, 0, 1, 0);
                            GL11.glRotated(90, 1, 0, 0);
                            break;
                        case 2: //north
                            GL11.glTranslated(1, -stopZFighting, 0);
                            GL11.glRotated(180, 0, 1, 0);
                            GL11.glRotated(90, 1, 0, 0);
                            break;
                        case 3: //east
                            GL11.glTranslated(1, -stopZFighting, 1);
                            GL11.glRotated(90, 0, 1, 0);
                            GL11.glRotated(90, 1, 0, 0);
                            break;
                    }
                    break;
                case 1: //horizontal
                    switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                        case 0: //south
                            GL11.glTranslated(0, 1, 1+stopZFighting);
                            break;
                        case 1: //west
                            GL11.glTranslated(-stopZFighting, 1, 0);
                            GL11.glRotated(-90, 0, 1, 0);
                            break;
                        case 2: //north
                            GL11.glTranslated(1, 1, -stopZFighting);
                            GL11.glRotated(180, 0, 1, 0);
                            break;
                        case 3: //east
                            GL11.glTranslated(1+stopZFighting, 1, 1);
                            GL11.glRotated(90, 0, 1, 0);
                            break;
                    }
                    break;
                case 2: //top
                    switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                        case 0: //south
                            GL11.glTranslated(0, 1+stopZFighting, 0);
                            GL11.glRotated(-90, 1, 0, 0);
                            break;
                        case 1: //west
                            GL11.glTranslated(1, 1+stopZFighting, 0);
                            GL11.glRotated(-90, 0, 1, 0);
                            GL11.glRotated(-90, 1, 0, 0);
                            break;
                        case 2: //north
                            GL11.glTranslated(1, 1+stopZFighting, 1);
                            GL11.glRotated(180, 0, 1, 0);
                            GL11.glRotated(-90, 1, 0, 0);
                            break;
                        case 3: //east
                            GL11.glTranslated(0, 1+stopZFighting, 1);
                            GL11.glRotated(90, 0, 1, 0);
                            GL11.glRotated(-90, 1, 0, 0);
                            break;
                    }
                    break;
            }
//
//                switch(te.getBlockMetadata()){
//                    case 0:  //-y
//                        GL11.glTranslated(0, -stopZFighting, 1);
//                        GL11.glRotated(90, 1, 0, 0);  //note this also rotates coordinate system
//                        break;
//                    case 1:  //+y
//                        GL11.glTranslated(0, 1+stopZFighting, 0);
//                        GL11.glRotated(-90, 1, 0, 0);
//                        break;
//                    case 2:  //-z
//                        GL11.glTranslated(1, 1, -stopZFighting);
//                        GL11.glRotated(180, 0, 1, 0);
//                        break;
//                    case 3:  //+z
//                        GL11.glTranslated(0, 1, 1+stopZFighting);
//                        break;
//                    case 4:  //-x
//                        GL11.glTranslated(-stopZFighting, 1, 0);
//                        GL11.glRotated(-90, 0, 1, 0);
//                        break;
//                    case 5:  //+x
//                        GL11.glTranslated(1+stopZFighting, 1, 1);
//                        GL11.glRotated(90, 0, 1, 0);
//                        break;
//            }
            GL11.glScalef(0.125f, -0.125f, 0.125f);
            fontrenderer.func_78276_b(s, 4-fontrenderer.func_78256_a(s) / 2, 0, tet.getColor());//string, x, y, color

            //GL11.glDepthMask(true);
            //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
}
