package com.empiricist.redcontrols.client;

import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.ITEBundledLights;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRButtons extends TileEntitySpecialRenderer{
    private ResourceLocation[] lightTextures;

    public TESRButtons(){
        lightTextures = new ResourceLocation[16];
        for (int i=0; i<lightTextures.length; i++){
            lightTextures[i] = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/light" + i + ".png");
        }
    }

    @Override
    public void func_180535_a(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        //LogHelper.info(textureRL.toString());

        if(te instanceof ITEBundledLights){
            ITEBundledLights teb = (ITEBundledLights) te;

            boolean[] states = teb.getSignalsArray();
            IBlockState state = te.func_145831_w().func_180495_p(te.func_174877_v());

            for( int i = 0; i < states.length; i++){
                if( !states[i] ){
                    continue;//don't render this one
                }

                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240f, 240f);
                GL11.glTranslated(x, y, z);

                this.func_147499_a(lightTextures[i]);
                final double stopZFighting = 0.01;

                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g); //no idea why mode is 7, just copying beacon

                switch( state.func_177229_b(BlockSwitches.VERTICAL) ){ //does this work?
                    case 0: //bottom
                        GL11.glScaled(1, -1, -1); //flip
                        GL11.glTranslated(0, 0, -1);
                        switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                            case 0: //south
                                worldrenderer.func_181662_b(1, stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                break;
                            case 1: //west
                                worldrenderer.func_181662_b(1, stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                GL11.glRotated(90, 0, 1, 0);
                                GL11.glTranslated(-1, 0, 0);
                                break;
                            case 2: //north
                                worldrenderer.func_181662_b(1, stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                GL11.glRotated(180, 0, 1, 0);
                                GL11.glTranslated(-1, 0, -1);
                                break;
                            case 3: //east
                                worldrenderer.func_181662_b(1, stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                GL11.glRotated(270, 0, 1, 0);
                                GL11.glTranslated(0, 0, -1);
                                break;
                        }
                        break;
                    case 1: //horizontal
                        switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                            case 0: //south
                                worldrenderer.func_181662_b(1, 0, 1+stopZFighting).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1, 1+stopZFighting).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1, 1+stopZFighting).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 0, 1+stopZFighting).func_181673_a(0, 1).func_181675_d();
                                break;
                            case 1: //west
                                worldrenderer.func_181662_b(-stopZFighting, 0, 1).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(-stopZFighting, 1, 1).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(-stopZFighting, 1, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(-stopZFighting, 0, 0).func_181673_a(0, 1).func_181675_d();
                                break;
                            case 2: //north
                                worldrenderer.func_181662_b(0, 0, -stopZFighting).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(0, 1, -stopZFighting).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(1, 1, -stopZFighting).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(1, 0, -stopZFighting).func_181673_a(0, 1).func_181675_d();
                                break;
                            case 3: //east
                                worldrenderer.func_181662_b(1+stopZFighting, 0, 0).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(1+stopZFighting, 1, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(1+stopZFighting, 1, 1).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(1+stopZFighting, 0, 1).func_181673_a(0, 1).func_181675_d();
                                break;
                        }
                        break;
                    case 2: //top
                        switch(  state.func_177229_b(BlockSwitches.FACING ).func_176736_b() ){
                            case 0: //south
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                break;
                            case 1: //west
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                GL11.glRotated(270, 0, 1, 0);
                                GL11.glTranslated(0, 0, -1);
                                break;
                            case 2: //north
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                GL11.glRotated(180, 0, 1, 0);
                                GL11.glTranslated(-1, 0, -1);
                                break;
                            case 3: //east
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 1).func_181673_a(0, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 1).func_181673_a(1, 1).func_181675_d();
                                worldrenderer.func_181662_b(1, 1+stopZFighting, 0).func_181673_a(1, 0).func_181675_d();
                                worldrenderer.func_181662_b(0, 1+stopZFighting, 0).func_181673_a(0, 0).func_181675_d();
                                GL11.glRotated(90, 0, 1, 0);
                                GL11.glTranslated(-1, 0, 0);
                                break;
                        }
                        break;
                }

                tessellator.func_78381_a();

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();

            }
        }
    }
}
