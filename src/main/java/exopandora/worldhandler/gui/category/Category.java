package exopandora.worldhandler.gui.category;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@OnlyIn(Dist.CLIENT)
public class Category extends ForgeRegistryEntry<Category>
{
	public static final IForgeRegistry<Category> REGISTRY = new RegistryBuilder<Category>()
			.setType(Category.class)
			.setName(new ResourceLocation(String.join("_", new String[] {Main.MODID, "category"})))
			.disableSync()
			.disableSaving()
			.create();
	
	private final List<Content> contents;
	
	public Category()
	{
		this.contents = new ArrayList<Content>();
	}
	
	public Category(List<Content> contents)
	{
		this.contents = contents;
	}
	
	public Category(Content... contents)
	{
		this.contents = Lists.newArrayList(contents);
	}
	
	public Category add(Content content)
	{
		this.contents.add(content);
		return this;
	}
	
	public List<Content> getContents()
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
		return this.contents.get(index);
	}
	
	public static void register()
	{
		Category.register("main", new Category(Contents.MAIN, Contents.CONTAINERS, Contents.MULTIPLAYER));
		Category.register("entities", new Category(Contents.SUMMON));
		Category.register("items", new Category(Contents.CUSTOM_ITEM, Contents.ENCHANTMENT, Contents.RECIPES));
		Category.register("blocks", new Category(Contents.EDIT_BLOCKS, Contents.SIGN_EDITOR, Contents.NOTE_EDITOR));
		Category.register("world", new Category(Contents.WORLD_INFO, Contents.GAMERULES));
		Category.register("player", new Category(Contents.PLAYER, Contents.EXPERIENCE, Contents.ADVANCEMENTS));
		Category.register("scoreboard", new Category(Contents.SCOREBOARD_OBJECTIVES, Contents.SCOREBOARD_TEAMS, Contents.SCOREBOARD_PLAYERS));
	}
	
    private static void register(String name, Category category)
    {
    	Category.register(new ResourceLocation(Main.MODID, name), category);
    }
    
    private static void register(ResourceLocation name, Category category)
    {
    	category.setRegistryName(name);
        REGISTRY.register(category);
    }
}
