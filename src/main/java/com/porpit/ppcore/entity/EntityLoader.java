package com.porpit.ppcore.entity;

import com.porpit.ppcore.PPCore;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityLoader {
	private static int nextID = 0;

	public EntityLoader() {
		registerEntity(EntitySeat.class, "Seat", 80, 3, true);
	}

	protected static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
                                       int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(new ResourceLocation(PPCore.MODID+":seat"),entityClass, name, nextID++, PPCore.instance, trackingRange,
				updateFrequency, sendsVelocityUpdates);
	}

}