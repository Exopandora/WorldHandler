package exopandora.worldhandler.gui.category;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.usercontent.UsercontentConfig;
import exopandora.worldhandler.usercontent.UsercontentLoader;
import exopandora.worldhandler.usercontent.model.JsonTab;
import exopandora.worldhandler.util.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class Category
{
	public static IForgeRegistry<Category> REGISTRY;
	public static final ResourceKey<Registry<Category>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Main.MODID, "category"));
	public static final Map<String, List<String>> DEFAULT_CATEGORIES = new CategoriesBuilder()
		.add("main", "main", "containers", "multiplayer")
		.add("entities", "summon", "butcher")
		.add("items", "custom_item", "enchantment", "recipes")
		.add("blocks", "edit_blocks", "sign_editor", "note_editor")
		.add("world", "world", "gamerules", "locate")
		.add("player", "player", "experience", "advancements")
		.add("scoreboard", "scoreboard_objectives", "scoreboard_teams", "scoreboard_players")
		.build();
	
	private final List<ResourceLocation> contents;
	
	public Category()
	{
		this.contents = Lists.newArrayList();
	}
	
	public Category(List<ResourceLocation> contents)
	{
		this.contents = contents;
	}
	
	public Category(ResourceLocation... contents)
	{
		this(Lists.newArrayList(contents));
	}
	
	public Category add(int index, ResourceLocation content)
	{
		this.contents.add(Math.min(index, this.getSize()), content);
		return this;
	}
	
	public Category add(int index, String key)
	{
		return this.add(index, new ResourceLocation(Main.MODID, key));
	}
	
	public List<ResourceLocation> getContents()
	{
		return this.contents;
	}
	
	public int getSize()
	{
		return this.contents.size();
	}
	
	@Nullable
	public Content getContent(int index)
	{
		return Content.REGISTRY.getValue(this.contents.get(index));
	}
	
	@SubscribeEvent
	public static void createRegistry(NewRegistryEvent event)
	{
		event.create(new RegistryBuilder<Category>()
			.setName(REGISTRY_KEY.location())
			.disableSaving()
			.disableSync(), registry -> REGISTRY = registry);
	}
	
	@SubscribeEvent
	public static void register(RegisterEvent event)
	{
		if(event.getRegistryKey().equals(REGISTRY_KEY))
		{
			for(Entry<String, List<String>> entry : UsercontentLoader.CATEGORIES.entrySet())
			{
				RegistryHelper.register(event, REGISTRY_KEY, entry.getKey(), () ->
				{
					var keys = entry.getValue().stream()
						.map(key -> new ResourceLocation(Main.MODID, key))
						.collect(Collectors.toList());
					return new Category(keys);
				});
			}
			
			for(UsercontentConfig config : UsercontentLoader.CONFIGS)
			{
				if(config.getContent().getGui() != null && config.getContent().getGui().getTab() != null)
				{
					Category.registerCategory(event, config.getId(), config.getContent().getGui().getTab());
				}
			}
		}
	}
	
	private static void registerCategory(RegisterEvent event, String id, JsonTab tab)
	{
		if(tab.getCategory() != null && !tab.getCategory().isEmpty())
		{
			if(!Categories.isRegistered(tab.getCategory()))
			{
				RegistryHelper.register(event, REGISTRY_KEY, tab.getCategory(), () -> new Category(new ResourceLocation(Main.MODID, id)));
			}
			else
			{
				Categories.getRegisteredCategory(tab.getCategory()).add(tab.getCategoryIndex(), id);
			}
		}
	}
	
	private static class CategoriesBuilder
	{
		private final Map<String, List<String>> categories = new HashMap<String, List<String>>();
		
		public CategoriesBuilder add(String category, String... contents)
		{
			this.categories.put(category, Collections.unmodifiableList(Lists.newArrayList(contents)));
			return this;
		}
		
		public Map<String, List<String>> build()
		{
			return Collections.unmodifiableMap(categories);
		}
	}
}
