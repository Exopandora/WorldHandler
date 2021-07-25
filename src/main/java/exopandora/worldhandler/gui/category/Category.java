package exopandora.worldhandler.gui.category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.usercontent.UsercontentConfig;
import exopandora.worldhandler.usercontent.UsercontentLoader;
import exopandora.worldhandler.usercontent.model.JsonTab;
import exopandora.worldhandler.util.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent.NewRegistry;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@OnlyIn(Dist.CLIENT)
public class Category extends ForgeRegistryEntry<Category>
{
	public static IForgeRegistry<Category> REGISTRY;
	
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
	
	public Category(String... keys)
	{
		this(Arrays.stream(keys).map(key -> new ResourceLocation(Main.MODID, key)).collect(Collectors.toList()));
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
	public static void createRegistry(NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Category>()
				.setType(Category.class)
				.setName(new ResourceLocation(Main.MODID + "_category"))
				.disableSaving()
				.disableSync()
				.create();
	}
	
	@SubscribeEvent
	public static void register(Register<Category> event)
	{
		RegistryHelper.register(event.getRegistry(), "main", new Category("main", "containers", "multiplayer"));
		RegistryHelper.register(event.getRegistry(), "entities", new Category("summon", "butcher"));
		RegistryHelper.register(event.getRegistry(), "items", new Category("custom_item", "enchantment", "recipes"));
		RegistryHelper.register(event.getRegistry(), "blocks", new Category("edit_blocks", "sign_editor", "note_editor"));
		RegistryHelper.register(event.getRegistry(), "world", new Category("world", "gamerules"));
		RegistryHelper.register(event.getRegistry(), "player", new Category("player", "experience", "advancements"));
		RegistryHelper.register(event.getRegistry(), "scoreboard", new Category("scoreboard_objectives", "scoreboard_teams", "scoreboard_players"));
		
		for(UsercontentConfig config : UsercontentLoader.CONFIGS)
		{
			if(config.getContent().getGui() != null && config.getContent().getGui().getTab() != null)
			{
				Category.registerCategory(event.getRegistry(), config.getId(), config.getContent().getGui().getTab());
			}
		}
	}
	
	private static void registerCategory(IForgeRegistry<Category> registry, String id, JsonTab tab)
	{
		if(tab.getCategory() != null && !tab.getCategory().isEmpty())
		{
			if(!Categories.isRegistered(tab.getCategory()))
			{
				RegistryHelper.register(registry, tab.getCategory(), new Category(id));
			}
			else
			{
				Categories.getRegisteredCategory(tab.getCategory()).add(tab.getCategoryIndex(), id);
			}
		}
	}
}
