package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderRecipe;
import exopandora.worldhandler.builder.impl.BuilderRecipe.EnumMode;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
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
		List<Recipe<?>> recipes = Minecraft.getInstance().player.getRecipeBook().getCollections().stream()
				.flatMap(recipe -> recipe.getRecipes().stream())
				.filter(recipe -> !recipe.isSpecial())
				.collect(Collectors.toList());
		
		MenuPageList<Recipe<?>> list = new MenuPageList<Recipe<?>>(x, y, recipes, 114, 20, 3, container, new ILogicPageList<Recipe<?>>()
		{
			@Override
			public MutableComponent translate(Recipe<?> item)
			{
				if(!item.getResultItem().equals(ItemStack.EMPTY))
				{
					return (MutableComponent) item.getResultItem().getHoverName();
				}
				
				return new TextComponent(item.getId().toString());
			}
			
			@Override
			public MutableComponent toTooltip(Recipe<?> item)
			{
				return new TextComponent(item.getId().toString());
			}
			
			@Override
			public void onClick(Recipe<?> item)
			{
				ContentRecipes.this.builderRecipe.setRecipe(item);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Recipe<?> item, ActionHandler actionHandler)
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
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.recipes.give"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderRecipe.build(EnumMode.GIVE));
			container.initButtons();
		}));
		container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.recipes.take"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderRecipe.build(EnumMode.TAKE));
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
		return new TranslatableComponent("gui.worldhandler.title.items.recipes");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.items.recipes");
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
