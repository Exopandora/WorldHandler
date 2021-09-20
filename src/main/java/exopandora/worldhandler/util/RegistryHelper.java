package exopandora.worldhandler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHelper
{
	private static final Map<IForgeRegistry<?>, Function<? extends ForgeRegistryEntry<?>, String>> FORGE = new HashMap<IForgeRegistry<?>, Function<? extends ForgeRegistryEntry<?>, String>>();
	
	static
	{
		registerRegistry(ForgeRegistries.BLOCKS, Block::getDescriptionId);
		registerRegistry(ForgeRegistries.ITEMS, Item::getDescriptionId);
		registerRegistry(ForgeRegistries.MOB_EFFECTS, MobEffect::getDescriptionId);
		registerRegistry(ForgeRegistries.BIOMES, biome ->
		{
			Registry<Biome> registry = RegistryAccess.builtin().registryOrThrow(Registry.BIOME_REGISTRY);
			ResourceLocation resource = registry.getKey(biome);
			String key = "biome." + biome.getRegistryName().getNamespace() + "." + resource.getPath();
			
			return Language.getInstance().has(key) ? key : resource.toString();
		});
		registerRegistry(ForgeRegistries.ENCHANTMENTS, Enchantment::getDescriptionId);
		registerRegistry(ForgeRegistries.ENTITIES, EntityType::getDescriptionId);
		registerRegistry(ForgeRegistries.STAT_TYPES, stat -> "stat." + stat.toString().replace(':', '.'));
	}
	
	private static <T extends ForgeRegistryEntry<T>> void registerRegistry(IForgeRegistry<T> registry, Function<T, String> mapper)
	{
		FORGE.put(registry, mapper);
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public static <T extends ForgeRegistryEntry<T>> String translate(ResourceLocation resource)
	{
		for(IForgeRegistry<?> registry : FORGE.keySet())
		{
			if(registry.containsKey(resource))
			{
				return ((Function<T, String>) FORGE.get(registry)).apply((T) registry.getValue(resource));
			}
		}
		
		return null;
	}
	
	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T entry)
	{
		register(registry, Main.MODID, name, entry);
	}
	
	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String modid, String name, T entry)
	{
		registry.register(entry.setRegistryName(new ResourceLocation(modid, name)));
	}
}
