package com.porpit.ppcore;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = PPCore.MODID, name = PPCore.NAME, version = PPCore.VERSION, acceptedMinecraftVersions = "1.12.2")

public class PPCore {
    public static final String MODID = "ppcore";
    public static final String NAME = "PPCore";
    public static final String VERSION = "1.0.0";
    @Mod.Instance(PPCore.MODID)
    public static PPCore instance;

    public static Logger logger;

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger=event.getModLog();
        logger.info("Loading PPCore");
    }

}
