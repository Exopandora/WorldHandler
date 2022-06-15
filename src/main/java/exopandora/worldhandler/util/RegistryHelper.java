package exopandora.worldhandler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

public class RegistryHelper
{
	private static final Map<IForgeRegistry<?>, Function<?, String>> FORGE = new HashMap<IForgeRegistry<?>, Function<?, String>>();
	
	static
	{
		registerRegistry(ForgeRegistries.BLOCKS, Block::getDescriptionId);
		registerRegistry(ForgeRegistries.ITEMS, Item::getDescriptionId);
		registerRegistry(ForgeRegistries.MOB_EFFECTS, MobEffect::getDescriptionId);
		registerRegistry(ForgeRegistries.BIOMES, biome ->
		{
			ResourceLocation resource = ForgeRegistries.BIOMES.getKey(biome);
			String key = "biome." + resource.getNamespace() + "." + resource.getPath();
			return Language.getInstance().has(key) ? key : resource.toString();
		});
		registerRegistry(ForgeRegistries.ENCHANTMENTS, Enchantment::getDescriptionId);
		registerRegistry(ForgeRegistries.ENTITIES, EntityType::getDescriptionId);
		registerRegistry(ForgeRegistries.STAT_TYPES, stat -> "stat." + stat.toString().replace(':', '.'));
	}
	
	private static <T> void registerRegistry(IForgeRegistry<T> registry, Function<T, String> mapper)
	{
		FORGE.put(registry, mapper);
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
		
		return null;
	}
	
	public static <T> void register(RegisterEvent event, ResourceKey<Registry<T>> key, String location, Supplier<T> valueSupplier)
	{
		event.register(key, new ResourceLocation(Main.MODID, location), valueSupplier);
	}
}
