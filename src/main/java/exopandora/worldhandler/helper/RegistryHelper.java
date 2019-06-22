package exopandora.worldhandler.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public class RegistryHelper
{
	private static final Map<IForgeRegistry<?>, Function<?, String>> FORGE = new HashMap<IForgeRegistry<?>, Function<?, String>>();
	private static final Map<Registry<?>, Function<?, String>> VANILLA = new HashMap<Registry<?>, Function<?, String>>();
	
	static
	{
		registerRegistry(ForgeRegistries.BLOCKS, Block::getTranslationKey);
		registerRegistry(ForgeRegistries.ITEMS, Item::getTranslationKey);
		registerRegistry(ForgeRegistries.POTIONS, Effect::getName);
		registerRegistry(ForgeRegistries.BIOMES, Biome::getTranslationKey);
		registerRegistry(ForgeRegistries.ENCHANTMENTS, Enchantment::getName);
		registerRegistry(ForgeRegistries.ENTITIES, EntityType::getTranslationKey);
		registerRegistry(Registry.field_212623_l, stat -> "stat." + stat.toString().replace(':', '.'));
	}
	
	private static <T extends ForgeRegistryEntry<T>> void registerRegistry(IForgeRegistry<T> registry, Function<T, String> mapper)
	{
		FORGE.put(registry, mapper);
	}
	
	private static <T> void registerRegistry(Registry<T> registry, Function<T, String> mapper)
	{
		VANILLA.put(registry, mapper);
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public static <T> String translate(ResourceLocation resource)
	{
		for(IForgeRegistry<?> registry : FORGE.keySet())
		{
			if(registry.containsKey(resource))
			{
				return ((Function<T, String>) FORGE.get(registry)).apply((T) registry.getValue(resource));
			}
		}
		
		for(Registry<?> registry : VANILLA.keySet())
		{
			if(registry.containsKey(resource))
			{
				return ((Function<T, String>) VANILLA.get(registry)).apply((T) registry.getOrDefault(resource));
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
