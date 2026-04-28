package exopandora.worldhandler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

public class TranslationHelper
{
	private static final Map<Registry<?>, Function<?, String>> FORGE = new HashMap<Registry<?>, Function<?, String>>();
	
	static
	{
		registerRegistry(BuiltInRegistries.BLOCK, Block::getDescriptionId);
		registerRegistry(BuiltInRegistries.ITEM, Item::getDescriptionId);
		registerRegistry(BuiltInRegistries.MOB_EFFECT, MobEffect::getDescriptionId);
		registerRegistry(BuiltInRegistries.ENTITY_TYPE, EntityType::getDescriptionId);
		registerRegistry(BuiltInRegistries.STAT_TYPE, stat -> "stat." + stat.toString().replace(':', '.'));
		registerRegistry(BuiltInRegistries.VILLAGER_PROFESSION, profession ->
		{
			ResourceLocation profName = BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession);
			return EntityType.VILLAGER.getDescriptionId() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath();
		});
	}
	
	private static <T> void registerRegistry(Registry<T> registry, Function<T, String> mapper)
	{
		FORGE.put(registry, mapper);
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public static <T> String translate(ResourceLocation resource)
	{
		if(RegistryHelper.getEnchantment(resource) != null)
		{
			return RegistryHelper.getEnchantmentDescription(RegistryHelper.getEnchantment(resource)).getString();
		}
		
		for(Registry<?> registry : FORGE.keySet())
		{
			if(registry.containsKey(resource))
			{
				return ((Function<T, String>) FORGE.get(registry)).apply((T) registry.get(resource));
			}
		}
		
		return null;
	}
}
