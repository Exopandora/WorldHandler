package exopandora.worldhandler.gui.category;

import exopandora.worldhandler.Main;
import net.minecraft.resources.ResourceLocation;

public class Categories
{
	public static final Category MAIN = Categories.getRegisteredCategory("main");
	public static final Category ENTITIES = Categories.getRegisteredCategory("entities");
	public static final Category ITEMS = Categories.getRegisteredCategory("items");
	public static final Category BLOCKS = Categories.getRegisteredCategory("blocks");
	public static final Category WORLD = Categories.getRegisteredCategory("world");
	public static final Category PLAYER = Categories.getRegisteredCategory("player");
	public static final Category SCOREBOARD = Categories.getRegisteredCategory("scoreboard");
	
	public static Category getRegisteredCategory(String name)
	{
		Category category = Category.REGISTRY.getValue(new ResourceLocation(Main.MODID, name));
		
		if(category == null)
		{
			throw new IllegalStateException("Requested missing category: " + name);
		}
		
		return category;
	}
	
	public static boolean isRegistered(String name)
	{
		return Category.REGISTRY.containsKey(new ResourceLocation(Main.MODID, name));
	}
}
