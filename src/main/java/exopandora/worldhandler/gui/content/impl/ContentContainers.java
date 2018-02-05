package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.impl.BuilderGive;
import exopandora.worldhandler.builder.impl.BuilderSetblock;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentContainers extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(1, x, y + 96, 232, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(3, x + 24, y, 208, 20, Blocks.CRAFTING_TABLE.getLocalizedName()));
		container.add(new GuiButtonWorldHandler(4, x + 24, y + 24, 208, 20, Blocks.ENDER_CHEST.getLocalizedName()));
		container.add(new GuiButtonWorldHandler(5, x + 24, y + 48, 208, 20, Blocks.ANVIL.getLocalizedName()));
		container.add(new GuiButtonWorldHandler(6, x + 24, y + 72, 208, 20, Blocks.ENCHANTING_TABLE.getLocalizedName()));
		
		container.add(new GuiButtonItem(7, x, y, 20, 20, new ItemStack(Blocks.CRAFTING_TABLE)));
		container.add(new GuiButtonItem(8, x, y + 24, 20, 20, new ItemStack(Blocks.ENDER_CHEST)));
		container.add(new GuiButtonItem(9, x, y + 48, 20, 20, new ItemStack(Blocks.ANVIL)));
		container.add(new GuiButtonItem(10, x, y + 72, 20, 20, new ItemStack(Blocks.ENCHANTING_TABLE)));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 3:
				BlockHelper.setBlockNearPlayer(Blocks.CRAFTING_TABLE, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				Minecraft.getMinecraft().setIngameFocus();
				break;
			case 4:
				BlockHelper.setBlockNearPlayer(Blocks.ENDER_CHEST, (byte) 2, (byte) 5, (byte) 3, (byte) 4);
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				Minecraft.getMinecraft().setIngameFocus();
				break;
			case 5:
				BlockHelper.setBlockNearPlayer(Blocks.ANVIL, (byte) 1, (byte) 0, (byte) 1, (byte) 0);
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				Minecraft.getMinecraft().setIngameFocus();
				break;
			case 6:
				BlockHelper.setBlockNearPlayer(Blocks.ENCHANTING_TABLE, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
				
				int direction = MathHelper.floor((double) (Minecraft.getMinecraft().player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
				double angle = direction * Math.PI / 2;
				double sin = Math.sin(angle);
				double cos = Math.cos(angle);
				
				for(int x = -2; x <= 2; x++)
				{
					for(int y = 0; y <= 1; y++)
					{
						for(int z = 1; z <= 4; z++)
						{
							if(!(x >= -1 && x <= 1 && z < 4))
							{
								WorldHandler.sendCommand(new BuilderSetblock(new Coordinate(x * cos - z * sin, true), new Coordinate(y, true), new Coordinate(x * sin + z * cos, true), Blocks.BOOKSHELF.getRegistryName(), ConfigSettings.getMode()));
							}
						}
					}
				}
				
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				Minecraft.getMinecraft().setIngameFocus();
				break;
			case 7:
				WorldHandler.sendCommand(new BuilderGive(container.getPlayer(), Blocks.CRAFTING_TABLE.getRegistryName()));
				break;
			case 8:
				WorldHandler.sendCommand(new BuilderGive(container.getPlayer(), Blocks.ENDER_CHEST.getRegistryName()));
				break;
			case 9:
				WorldHandler.sendCommand(new BuilderGive(container.getPlayer(), Blocks.ANVIL.getRegistryName()));
				break;
			case 10:
				WorldHandler.sendCommand(new BuilderGive(container.getPlayer(), Blocks.ENCHANTING_TABLE.getRegistryName()));
				break;
			default:
				break;
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.containers");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.containers");
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
