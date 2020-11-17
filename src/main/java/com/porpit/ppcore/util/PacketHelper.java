package com.porpit.ppcore.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;

public class PacketHelper {
    public static void sendPacketWithinDistance(EntityPlayerMP player, boolean longDistance, double x, double y, double z, Packet<?> packetIn)
    {
        BlockPos blockpos = player.getPosition();
        double d0 = blockpos.distanceSq(x, y, z);

        if (d0 <= 1024.0D || longDistance && d0 <= 262144.0D)
        {
            player.connection.sendPacket(packetIn);
        }
    }
}
