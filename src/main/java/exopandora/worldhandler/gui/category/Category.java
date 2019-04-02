package exopandora.worldhandler.gui.category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public class Category extends ForgeRegistryEntry<Category>
{
	public static final String NAMESPACE = String.join("_", new String[] {Main.MODID, "category"});
	public static final IRegistry<Category> REGISTRY = IRegistry.func_212610_a(NAMESPACE, new RegistryNamespacedDefaultedByKey<Category>(new ResourceLocation(NAMESPACE, "main"))); 
	
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
		this.contents = Arrays.asList(contents);
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
	
	public static void registerCategories()
	{
		registerCategory(0, "main", new Category(Contents.MAIN, Contents.CONTAINERS, Contents.MULTIPLAYER));
		registerCategory(1, "entities", new Category(Contents.SUMMON));
		registerCategory(2, "items", new Category(Contents.CUSTOM_ITEM, Contents.ENCHANTMENT, Contents.RECIPES));
		registerCategory(3, "blocks", new Category(Contents.EDIT_BLOCKS, Contents.SIGN_EDITOR, Contents.NOTE_EDITOR));
		registerCategory(4, "world", new Category(Contents.WORLD_INFO, Contents.GAMERULES));
		registerCategory(5, "player", new Category(Contents.PLAYER, Contents.EXPERIENCE, Contents.ADVANCEMENTS));
		registerCategory(6, "scoreboard", new Category(Contents.SCOREBOARD_OBJECTIVES, Contents.SCOREBOARD_TEAMS, Contents.SCOREBOARD_PLAYERS));
	}
	
    private static void registerCategory(int id, String textualID, Category category)
    {
    	registerCategory(id, new ResourceLocation(Main.MODID, textualID), category);
    }
    
    private static void registerCategory(int id, ResourceLocation textualID, Category category)
    {
        REGISTRY.register(id, textualID, category);
    }
}
