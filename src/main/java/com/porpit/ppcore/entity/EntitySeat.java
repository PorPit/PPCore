package com.porpit.ppcore.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySeat  extends Entity {
    public int blockX;
    public int blockY;
    public int blockZ;

    public EntitySeat(World world)
    {
        super(world);
        this.noClip = true;
        this.width = 0.01F;
        this.height = 0.01F;
    }

    public EntitySeat(World world, EntityPlayer player, double x, double y, double z, double yOffset) {
        super(world);
        this.width = 0.01F;
        this.height = 0.01F;

        this.blockX = ((int)x);
        this.blockY = ((int)y);
        this.blockZ = ((int)z);

        setPosition(x + 0.5D, y + yOffset, z + 0.5D);
    }

    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }

    public void onEntityUpdate()
    {
        if ((!isBeingRidden()) || (this.world.isAirBlock(new BlockPos(this.posX, this.posY, this.posZ))))
            setDead();
    }

    public double getYOffset()
    {
        return this.height * 0.0D;
    }

    protected void entityInit()
    {
    }

    protected void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    protected void writeEntityToNBT(NBTTagCompound compound)
    {
    }
}
