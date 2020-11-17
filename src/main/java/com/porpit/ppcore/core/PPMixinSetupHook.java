package com.porpit.ppcore.core;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

public class PPMixinSetupHook implements IFMLCallHook {

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public Void call() {
        PPPatchingLoader.logger.info("PPCore has been hooked by Forge, setting up Mixin and plugins");

        MixinBootstrap.init();

        return null;
    }
}
