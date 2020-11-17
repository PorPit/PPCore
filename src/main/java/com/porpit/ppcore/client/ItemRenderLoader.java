package com.porpit.ppcore.client;

import com.porpit.ppcore.block.BlockLoader;

public class ItemRenderLoader {
	public ItemRenderLoader() {
		BlockLoader.registerRenders();
	}
}