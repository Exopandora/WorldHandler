package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.impl.BuilderGive;
import exopandora.worldhandler.builder.impl.BuilderSetBlock;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonItem;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ContentContainers extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 232, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 24, y, 208, 20, Blocks.CRAFTING_TABLE.getName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.CRAFTING_TABLE);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 24, 208, 20, Blocks.ENDER_CHEST.getName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.ENDER_CHEST);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 48, 208, 20, Blocks.ANVIL.getName(), () ->
		{
			BlockHelper.setBlockNearPlayer(container.getPlayer(), Blocks.ANVIL);
			ActionHelper.backToGame();
		}));
		container.add(new GuiButtonBase(x + 24, y + 72, 208, 20, Blocks.ENCHANTING_TABLE.getName(), () ->
		{
			double angle = Minecraft.getInstance().player.getDirection().get2DDataValue() * Math.PI / 2;
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
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.containers");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.containers");
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
