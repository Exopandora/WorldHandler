package exopandora.worldhandler.util;

import java.util.function.Supplier;

import exopandora.worldhandler.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;

public class RegistryHelper
{
	public static <T> void register(RegisterEvent event, ResourceKey<Registry<T>> key, String location, Supplier<T> valueSupplier)
	{
		event.register(key, new ResourceLocation(Main.MODID, location), valueSupplier);
	}
}
