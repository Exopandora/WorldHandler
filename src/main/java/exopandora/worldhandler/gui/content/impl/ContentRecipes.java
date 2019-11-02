package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderRecipe;
import exopandora.worldhandler.builder.impl.BuilderRecipe.EnumMode;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		List<IRecipe<?>> recipes = Minecraft.getInstance().player.getRecipeBook().getRecipes().stream()
				.flatMap(recipe -> recipe.getRecipes().stream())
				.filter(recipe -> !recipe.isDynamic())
				.collect(Collectors.toList());
		
		ElementPageList<IRecipe<?>> list = new ElementPageList<IRecipe<?>>(x, y, recipes, 114, 20, 3, container, new ILogicPageList<IRecipe<?>>()
		{
			@Override
			public String translate(IRecipe<?> item)
			{
				if(!item.getRecipeOutput().equals(ItemStack.EMPTY))
				{
					return item.getRecipeOutput().getDisplayName().getFormattedText();
				}
				
				return item.getId().toString();
			}
			
			@Override
			public String toTooltip(IRecipe<?> item)
			{
				return item.getId().toString();
			}
			
			@Override
			public void onClick(IRecipe<?> item)
			{
				ContentRecipes.this.builderRecipe.setRecipe(item);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, IRecipe<?> item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
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
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.recipes.give"), () ->
		{
			CommandHelper.sendCommand(this.builderRecipe.getBuilderForMode(EnumMode.GIVE));
			container.initButtons();
		}));
		container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.recipes.take"), () ->
		{
			CommandHelper.sendCommand(this.builderRecipe.getBuilderForMode(EnumMode.TAKE));
			container.initButtons();
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.items.recipes");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.items.recipes");
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
