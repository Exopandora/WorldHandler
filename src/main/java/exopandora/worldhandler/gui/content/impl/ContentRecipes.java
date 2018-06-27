package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderRecipe;
import exopandora.worldhandler.builder.impl.BuilderRecipe.EnumMode;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentRecipes extends Content
{
	private final BuilderRecipe builderRecipe = new BuilderRecipe();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderRecipe;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		
		for(IRecipe recipe : CraftingManager.REGISTRY)
		{
			if(!recipe.isDynamic())
			{
				recipes.add(recipe);
			}
		}
		
		ElementPageList<IRecipe, String> list = new ElementPageList<IRecipe, String>(x, y, recipes, null, 114, 20, 3, this, new int[] {6, 7, 8}, new ILogicPageList<IRecipe, String>()
		{
			@Override
			public String translate(IRecipe key)
			{
				if(!key.getRecipeOutput().equals(ItemStack.EMPTY))
				{
					return key.getRecipeOutput().getDisplayName();
				}
				
				return key.getRegistryName().toString();
			}
			
			@Override
			public void onClick(IRecipe clicked)
			{
				builderRecipe.setRecipe(clicked.getRegistryName());
			}
			
			@Override
			public String getRegistryName(IRecipe key)
			{
				return key.getRegistryName().toString();
			}
			
			@Override
			public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, IRecipe value, Container container)
			{
				GuiButtonWorldHandler button;
				container.add(button = new GuiButtonWorldHandler(id, x, y, width, height, display, value.getRegistryName().toString(), EnumTooltip.TOP_RIGHT));
				button.enabled = enabled;
			}
			
			@Override
			public IRecipe convert(String object)
			{
				return CraftingManager.REGISTRY.getObject(Type.parseResourceLocation(object));
			}
			
			@Override
			public String getId()
			{
				return "recipe";
			}
		});
		
		container.add(list);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(2, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.recipes.give")));
		container.add(new GuiButtonWorldHandler(3, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.recipes.take")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				WorldHandler.sendCommand(this.builderRecipe.getBuilderForMode(EnumMode.GIVE));
				container.initButtons();
				break;
			case 3:
				WorldHandler.sendCommand(this.builderRecipe.getBuilderForMode(EnumMode.TAKE));
				container.initButtons();
				break;
			default:
				break;
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public String getTitle()
	{
		return "Recipes";
	}
	
	@Override
	public String getTabTitle()
	{
		return "Recipes";
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.RECIPES;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderRecipe.setPlayer(username);
	}
}
