package exopandora.worldhandler.util;

import java.util.Collection;
import java.util.function.Supplier;

import exopandora.worldhandler.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.RegisterEvent;

public class RegistryHelper
{
	public static <T> void register(RegisterEvent event, ResourceKey<Registry<T>> key, String location, Supplier<T> valueSupplier)
	{
		event.register(key, RegistryHelper.location(location), valueSupplier);
	}
	
	public static ResourceLocation location(String location)
	{
		return ResourceLocation.fromNamespaceAndPath(Main.MODID, location);
	}
	
	public static RegistryAccess registryAccess()
	{
		if(Minecraft.getInstance().level != null)
		{
			return Minecraft.getInstance().level.registryAccess();
		}
		
		return RegistryAccess.EMPTY;
	}
	
	public static Registry<Enchantment> enchantments()
	{
		return RegistryHelper.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
	}
	
	public static Collection<Enchantment> enchantmentValues()
	{
		return RegistryHelper.enchantments().stream().toList();
	}
	
	public static Enchantment getEnchantment(ResourceLocation location)
	{
		return RegistryHelper.enchantments().get(location);
	}
	
	public static ResourceLocation getEnchantmentKey(Enchantment enchantment)
	{
		return RegistryHelper.enchantments().getKey(enchantment);
	}
	
	public static Component getEnchantmentDescription(Enchantment enchantment)
	{
		return enchantment.description();
	}
}
