package com.porpit.ppcore.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class FakePlayerLoader
{
    private static GameProfile gameProfile;
    private static WeakReference<EntityPlayerMP> fakePlayer;

    public FakePlayerLoader()
    {
        gameProfile = new GameProfile(UUID.fromString("C3F2EF82-E759-53EA-9D69-0D6E394A00B8"), "[PPCore]");
        fakePlayer = new WeakReference<EntityPlayerMP>(null);
    }

    public static WeakReference<EntityPlayerMP> getFakePlayer(WorldServer server)
    {
        if (fakePlayer.get() == null)
        {
            fakePlayer = new WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
        }
        else
        {
            fakePlayer.get().world = server;
        }
        return fakePlayer;
    }
}