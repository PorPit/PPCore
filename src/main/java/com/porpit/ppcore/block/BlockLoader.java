package com.porpit.ppcore.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockLoader {

	public BlockLoader(FMLPreInitializationEvent event) {
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {


	}

	private static void register(Block block, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(name));
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block, int meta, String name) {
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, model);

	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block) {
		registerRender(block, 0, block.getRegistryName().getResourcePath());
	}

}
