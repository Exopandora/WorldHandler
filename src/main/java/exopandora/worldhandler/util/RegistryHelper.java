package exopandora.worldhandler.util;

import java.util.function.Supplier;

import exopandora.worldhandler.Main;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Registry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;

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
	
	public static <T> void register(RegisterEvent event, ResourceKey<Registry<T>> key, String location, Supplier<T> valueSupplier)
	{
		event.register(key, new ResourceLocation(Main.MODID, location), valueSupplier);
	}
}
