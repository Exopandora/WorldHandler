package exopandora.worldhandler.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

@OnlyIn(Dist.CLIENT)
public class RegistryTranslator
{
	private static final Map<IForgeRegistry<?>, Function<?, String>> FORGE = new HashMap<IForgeRegistry<?>, Function<?, String>>();
	private static final Map<IRegistry<?>, Function<?, String>> VANILLA = new HashMap<IRegistry<?>, Function<?, String>>();
	
	static
	{
		register(ForgeRegistries.BLOCKS, Block::getTranslationKey);
		register(ForgeRegistries.ITEMS, Item::getTranslationKey);
		register(ForgeRegistries.POTIONS, Potion::getName);
		register(ForgeRegistries.BIOMES, Biome::getTranslationKey);
		register(ForgeRegistries.ENCHANTMENTS, Enchantment::getName);
		register(ForgeRegistries.ENTITIES, EntityType::getTranslationKey);
		register(IRegistry.field_212623_l, stat -> "stat." + stat.toString().replace(':', '.'));
	}
	
	private static <T extends ForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, Function<T, String> mapper)
	{
		FORGE.put(registry, mapper);
	}
	
	private static <T> void register(IRegistry<T> registry, Function<T, String> mapper)
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
		
		for(IRegistry<?> registry : VANILLA.keySet())
		{
			if(registry.func_212607_c(resource))
			{
				return ((Function<T, String>) VANILLA.get(registry)).apply((T) registry.func_212608_b(resource));
			}
		}
		
		return null;
	}
}
