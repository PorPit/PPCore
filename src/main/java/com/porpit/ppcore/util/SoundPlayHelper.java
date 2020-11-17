package com.porpit.ppcore.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldServer;

public class SoundPlayHelper {

    public static void playSound(WorldServer worldServer, SoundEvent soundIn, SoundCategory categoryIn, double xIn, double yIn, double zIn, float volumeIn, float pitchIn) {
        SPacketSoundEffect soundEffect = new SPacketSoundEffect(soundIn, categoryIn, xIn, yIn, zIn, volumeIn, pitchIn);
        for (int i = 0; i < worldServer.playerEntities.size(); ++i) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) worldServer.playerEntities.get(i);
            PacketHelper.sendPacketWithinDistance(entityplayermp, false, xIn, yIn, zIn, soundEffect);
        }
    }

}
