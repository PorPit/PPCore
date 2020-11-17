package com.porpit.ppcore.core;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class PPMixinPlugin implements IMixinConfigPlugin {


    private boolean spongePresent;

    @Override
    public void onLoad(String mixinPackage) {
        PPPatchingLoader.logger.info("Loading configuration");


        try {
            Class.forName("org.spongepowered.mod.SpongeCoremod");

            this.spongePresent = true;
        } catch (Exception e) {
            this.spongePresent = false;
        }

        if (this.spongePresent) {
            PPPatchingLoader.logger.info("Sponge has been detected on the classpath! Sponge mixins will be used.");
            PPPatchingLoader.logger.warn("Please keep in mind that Sponge support is **experimental** (although supported). We cannot currently" +
                    "detect if you are using Sponge's async lighting feature, so please disable it if you have not already.");
        }
    }

    @Override
    public String getRefMapperConfig() {
        if (Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE) {
            return null;
        }

        return "mixins.ppcore.refmap.json";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (this.spongePresent) {
            if (mixinClassName.endsWith("$Vanilla")) {
                PPPatchingLoader.logger.info("Disabled mixin '{}' as we are in a Sponge environment", mixinClassName);
                return false;
            }
        } else {
            if (mixinClassName.endsWith("$Sponge")) {
                PPPatchingLoader.logger.info("Disabled mixin '{}' as we are in a basic Forge environment", mixinClassName);
                return false;
            }
        }

        // Do not apply common transformations if we are not in a common environment!
        return !targetClassName.startsWith("net.minecraft.common") || MixinEnvironment.getCurrentEnvironment().getSide() == MixinEnvironment.Side.CLIENT;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
