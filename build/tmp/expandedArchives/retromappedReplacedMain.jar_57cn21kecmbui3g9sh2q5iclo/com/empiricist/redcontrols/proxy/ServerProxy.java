package com.empiricist.redcontrols.proxy;

public class ServerProxy extends CommonProxy{
    @Override
    public void registerKeyBindings(){};//server does nothing, keys are clientside
    public void registerTESR(){} //renderers are also clientside
    public void registerModels(){}
}
