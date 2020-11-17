package com.porpit.ppcore.util;

import com.porpit.ppcore.entity.EntitySeat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class SeatHelper {
    public static void seat(World world, double x, double y, double z, EntityPlayer player, double yOffset)
    {
        if ((!player.isSneaking()) &&
                (!isEntityAt(world, x, y + yOffset, z, player))) {
            EntitySeat seat = new EntitySeat(world, player, x, y, z, yOffset);
            world.spawnEntity(seat);
            player.dismountRidingEntity();
            player.startRiding(seat);
        }
    }
    public static boolean isEntityAt(World world, double x, double y, double z, EntityPlayer player)
    {
        List<EntitySeat> entityList = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1.0D, 1.0D, 1.0D));
        for (EntitySeat seat : entityList) {
            if ((seat.blockX == x) && (seat.blockY == y) && (seat.blockZ == z)) {
                if (!seat.isBeingRidden()) {
                    player.startRiding(seat);
                }
                return true;
            }
        }
        return false;
    }
}
