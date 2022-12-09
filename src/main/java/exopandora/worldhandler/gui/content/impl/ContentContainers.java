package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.argument.Coordinate;
import exopandora.worldhandler.builder.impl.GiveCommandBuilder;
import exopandora.worldhandler.builder.impl.SetBlockCommandBuilder;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ContentContainers extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 232, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiButtonBase(x + 24, y, 208, 20, Blocks.CRAFTING_TABLE.getName(), () -> this.placeBlock(container.getPlayer(), Blocks.CRAFTING_TABLE)));
		container.addRenderableWidget(new GuiButtonBase(x + 24, y + 24, 208, 20, Blocks.ENDER_CHEST.getName(), () -> this.placeBlock(container.getPlayer(), Blocks.ENDER_CHEST)));
		container.addRenderableWidget(new GuiButtonBase(x + 24, y + 48, 208, 20, Blocks.ANVIL.getName(), () -> this.placeBlock(container.getPlayer(), Blocks.ANVIL)));
		container.addRenderableWidget(new GuiButtonBase(x + 24, y + 72, 208, 20, Blocks.ENCHANTING_TABLE.getName(), () ->
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
						else
						{
							continue;
						}
						
						SetBlockCommandBuilder builder = new SetBlockCommandBuilder();
						builder.pos().setX(new Coordinate.Ints(cx, Coordinate.Type.RELATIVE));
						builder.pos().setY(new Coordinate.Ints(yOffset, Coordinate.Type.RELATIVE));
						builder.pos().setZ(new Coordinate.Ints(cz, Coordinate.Type.RELATIVE));
						builder.block().set(block);
						
						switch(Config.getSettings().getBlockPlacingMode())
						{
							case KEEP:
								CommandHelper.sendCommand(container.getPlayer(), builder, SetBlockCommandBuilder.Label.KEEP);
								break;
							case REPLACE:
								CommandHelper.sendCommand(container.getPlayer(), builder, SetBlockCommandBuilder.Label.REPLACE);
								break;
							case DESTROY:
								CommandHelper.sendCommand(container.getPlayer(), builder, SetBlockCommandBuilder.Label.DESTROY);
								break;
						}
					}
				}
			}
			
			ActionHelper.backToGame();
		}));
		
		container.addRenderableWidget(new GuiButtonItem(x, y, 20, 20, new ItemStack(Blocks.CRAFTING_TABLE), () -> this.giveItem(container.getPlayer(), Items.CRAFTING_TABLE)));
		container.addRenderableWidget(new GuiButtonItem(x, y + 24, 20, 20, new ItemStack(Blocks.ENDER_CHEST), () -> this.giveItem(container.getPlayer(), Items.ENDER_CHEST)));
		container.addRenderableWidget(new GuiButtonItem(x, y + 48, 20, 20, new ItemStack(Blocks.ANVIL), () -> this.giveItem(container.getPlayer(), Items.ANVIL)));
		container.addRenderableWidget(new GuiButtonItem(x, y + 72, 20, 20, new ItemStack(Blocks.ENCHANTING_TABLE), () -> this.giveItem(container.getPlayer(), Items.ENCHANTING_TABLE)));
	}
	
	private void giveItem(String player, Item item)
	{
		GiveCommandBuilder builder = new GiveCommandBuilder();
		builder.targets().setTarget(player);
		builder.item().set(item);
		CommandHelper.sendCommand(player, builder, GiveCommandBuilder.Label.GIVE);
	}
	
	private void placeBlock(String player, Block block)
	{
		BlockHelper.setBlockNearPlayer(player, block);
		ActionHelper.backToGame();
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.containers");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.containers");
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
