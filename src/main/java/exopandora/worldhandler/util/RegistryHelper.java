package exopandora.worldhandler.util;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.registries.VanillaRegistries;

public class RegistryHelper
{
	private static final RegistryHelper INSTANCE = new RegistryHelper();
	private static Provider provider;
	
	public static void init()
	{
		provider = VanillaRegistries.createLookup();
	}
	
	public static Provider getLookupProvider()
	{
		return provider;
	}
	
	public static RegistryHelper getInstance()
	{
		return INSTANCE;
	}
}
