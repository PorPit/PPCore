package com.porpit.ppcore.common;

import com.porpit.ppcore.PPCore;
import com.porpit.ppcore.entity.EntityLoader;
import com.porpit.ppcore.entity.FakePlayerLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new EntityLoader();
        new FakePlayerLoader();
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}