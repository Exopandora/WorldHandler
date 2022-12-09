package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.impl.RecipeCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class ContentRecipes extends Content
{
	private final RecipeCommandBuilder builderRecipe = new RecipeCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderRecipe, null);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<Recipe<?>> recipes = Minecraft.getInstance().player.getRecipeBook().getCollections().stream()
				.flatMap(recipe -> recipe.getRecipes().stream())
				.filter(recipe -> !recipe.isSpecial())
				.collect(Collectors.toList());
		
		MenuPageList<Recipe<?>> list = new MenuPageList<Recipe<?>>(x, y, recipes, 114, 20, 3, container, new ILogicPageList<Recipe<?>>()
		{
			@Override
			public MutableComponent translate(Recipe<?> recipe)
			{
				if(!ItemStack.EMPTY.equals(recipe.getResultItem()))
				{
					return (MutableComponent) recipe.getResultItem().getHoverName();
				}
				
				return Component.literal(recipe.getId().toString());
			}
			
			@Override
			public MutableComponent toTooltip(Recipe<?> recipe)
			{
				return Component.literal(recipe.getId().toString());
			}
			
			@Override
			public void onClick(Recipe<?> recipe)
			{
				ContentRecipes.this.builderRecipe.recipe().set(recipe.getId());
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Recipe<?> recipe, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(recipe), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "recipe";
			}
		});
		
		container.addMenu(list);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.recipes.give"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderRecipe, RecipeCommandBuilder.Label.GIVE);
			container.initButtons();
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.recipes.take"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderRecipe, RecipeCommandBuilder.Label.TAKE);
			container.initButtons();
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.items.recipes");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.items.recipes");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.RECIPES;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderRecipe.targets().setTarget(username);
	}
}
