package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.impl.BuilderGive;
import exopandora.worldhandler.builder.impl.BuilderSetBlock;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentContainers extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 232, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 24, y, 208, 20, Blocks.CRAFTING_TABLE.getTranslatedName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.CRAFTING_TABLE);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 24, 208, 20, Blocks.ENDER_CHEST.getTranslatedName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.ENDER_CHEST);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 48, 208, 20, Blocks.ANVIL.getTranslatedName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.ANVIL);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 72, 208, 20, Blocks.ENCHANTING_TABLE.getTranslatedName(), () ->
		{
			double angle = Minecraft.getInstance().player.getHorizontalFacing().getHorizontalIndex() * Math.PI / 2;
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			
			for(int xOffset = -2; xOffset <= 2; xOffset++)
			{
				for(int yOffset = 0; yOffset <= 1; yOffset++)
				{
					for(int zOffset = 1; zOffset <= 4; zOffset++)
					{
						Block block = null;
						int cx = (int) Math.round(xOffset * cos - zOffset * sin);
						int cz = (int) Math.round(xOffset * sin + zOffset * cos);
						
						if(!(xOffset >= -1 && xOffset <= 1 && zOffset < 4))
						{
							block = Blocks.BOOKSHELF;
						}
						else if(xOffset == 0 && yOffset == 0 && zOffset == 2)
						{
							block = Blocks.ENCHANTING_TABLE;
						}
						
						if(block != null)
						{
							CommandHelper.sendCommand(container.getPlayer(), new BuilderSetBlock(new CoordinateInt(cx, EnumType.GLOBAL), new CoordinateInt(yOffset, EnumType.GLOBAL), new CoordinateInt(cz, EnumType.GLOBAL), block.getRegistryName(), Config.getSettings().getBlockPlacingMode()));
						}
					}
				}
			}
			
			ActionHelper.backToGame();
		}));
		
		container.add(new GuiButtonItem(x, y, 20, 20, new ItemStack(Blocks.CRAFTING_TABLE), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), new BuilderGive(container.getPlayer(), Blocks.CRAFTING_TABLE.getRegistryName()));
		}));
		container.add(new GuiButtonItem(x, y + 24, 20, 20, new ItemStack(Blocks.ENDER_CHEST), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), new BuilderGive(container.getPlayer(), Blocks.ENDER_CHEST.getRegistryName()));
		}));
		container.add(new GuiButtonItem(x, y + 48, 20, 20, new ItemStack(Blocks.ANVIL), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), new BuilderGive(container.getPlayer(), Blocks.ANVIL.getRegistryName()));
		}));
		container.add(new GuiButtonItem(x, y + 72, 20, 20, new ItemStack(Blocks.ENCHANTING_TABLE), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), new BuilderGive(container.getPlayer(), Blocks.ENCHANTING_TABLE.getRegistryName()));
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.containers");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.containers");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.CONTAINERS;
	}
	
	@Override
	public Content getBackContent()
	{
		return null;
	}
}
