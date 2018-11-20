package com.porpit.ppcore.core;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class PPDummy extends DummyModContainer {
	public static final String modid = "PPDummy";
	public static final String version = "1.0.0";

	public PPDummy() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = modid;
		meta.name = "PPDummy";
		meta.version = version;
		meta.credits = "PorPit";
		meta.authorList.add("PorPit");
		meta.description = "";
		meta.url = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {

		bus.register(this);
		return true;
	}
}