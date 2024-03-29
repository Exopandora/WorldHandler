package exopandora.worldhandler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class TranslationHelper
{
	private static final Map<IForgeRegistry<?>, Function<?, String>> FORGE = new HashMap<IForgeRegistry<?>, Function<?, String>>();
	
	static
	{
		registerRegistry(ForgeRegistries.BLOCKS, Block::getDescriptionId);
		registerRegistry(ForgeRegistries.ITEMS, Item::getDescriptionId);
		registerRegistry(ForgeRegistries.MOB_EFFECTS, MobEffect::getDescriptionId);
		registerRegistry(ForgeRegistries.BIOMES, biome -> ForgeRegistries.BIOMES.getKey(biome).toLanguageKey("biome"));
		registerRegistry(ForgeRegistries.ENCHANTMENTS, Enchantment::getDescriptionId);
		registerRegistry(ForgeRegistries.ENTITY_TYPES, EntityType::getDescriptionId);
		registerRegistry(ForgeRegistries.STAT_TYPES, stat -> "stat." + stat.toString().replace(':', '.'));
		registerRegistry(ForgeRegistries.VILLAGER_PROFESSIONS, profession ->
		{
			ResourceLocation profName = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
			return EntityType.VILLAGER.getDescriptionId() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath();
		});
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
}
