package com.porpit.ppcore.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

public class ParticleHelper {


    public static void playParticles(WorldServer worldServer, EnumParticleTypes particleType, boolean longDistance, float x, float y, float z, float xOffset, float yOffset, float zOffset, float particleSpeed, int numberOfParticles, int... argumentsIn){{
        SPacketParticles spacketparticles = new SPacketParticles(particleType, longDistance, (float)x, (float)y, (float)z, (float)xOffset, (float)yOffset, (float)zOffset, (float)particleSpeed, numberOfParticles, argumentsIn);

        for (int i = 0; i < worldServer.playerEntities.size(); ++i)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)worldServer.playerEntities.get(i);
           PacketHelper.sendPacketWithinDistance(entityplayermp, longDistance, x, y, z, spacketparticles);
        }}
    }


}
