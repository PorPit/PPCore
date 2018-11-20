package com.porpit.ppcore.core;

import com.porpit.ppcore.transform.TransformerNames;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class PPPatchingLoader implements IFMLLoadingPlugin {

	public static File location;

	@Override
	public String[] getASMTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return PPDummy.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		location = (File) data.get("coremodLocation");
		TransformerNames.obfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}