package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderClone;
import exopandora.worldhandler.builder.impl.BuilderClone.EnumMask;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderWH;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.storage.ButtonStorage;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentEditBlocks extends Content
{
	private GuiTextFieldTooltip x1Field;
	private GuiTextFieldTooltip y1Field;
	private GuiTextFieldTooltip z1Field;
	
	private GuiTextFieldTooltip x2Field;
	private GuiTextFieldTooltip y2Field;
	private GuiTextFieldTooltip z2Field;
	
	private GuiTextFieldTooltip block1Field;
	private GuiTextFieldTooltip block2Field;
	
	private final BuilderFill builderFill = new BuilderFill().addPositionObservers();
	private final BuilderClone builderClone = new BuilderClone().addPositionObservers();
	private final BuilderWH builderWH = new BuilderWH();
	
	private String block1;
	private String block2;
	
	private GuiButtonList cloneButton;
	
	private String selectedPage = "coordinates";
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		if(this.selectedPage.equals("coordinates"))
		{
			return this.builderWH;
		}
		else if(this.selectedPage.equals("fill") || this.selectedPage.equals("replace"))
		{
			return this.builderFill;
		}
		else if(this.selectedPage.equals("clone"))
		{
			return this.builderClone;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.x1Field = new GuiTextFieldTooltip(x + 118, y, 55, 20);
		this.x1Field.setValidator(this.getCoordinatePredicate("X1"));
		this.x1Field.setText("X1: " + BlockHelper.getPos1().getX());
		
		this.y1Field = new GuiTextFieldTooltip(x + 118, y + 24, 55, 20);
		this.y1Field.setValidator(this.getCoordinatePredicate("Y1"));
		this.y1Field.setText("Y1: " + BlockHelper.getPos1().getY());
		
		this.z1Field = new GuiTextFieldTooltip(x + 118, y + 48, 55, 20);
		this.z1Field.setValidator(this.getCoordinatePredicate("Z1"));
		this.z1Field.setText("Z1: " + BlockHelper.getPos1().getZ());
		
		this.x2Field = new GuiTextFieldTooltip(x + 118 + 59, y, 55, 20);
		this.x2Field.setValidator(this.getCoordinatePredicate("X2"));
		this.x2Field.setText("X2: " + BlockHelper.getPos2().getX());
		
		this.y2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 24, 55, 20);
		this.y2Field.setValidator(this.getCoordinatePredicate("Y2"));
		this.y2Field.setText("Y2: " + BlockHelper.getPos2().getY());
		
		this.z2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 48, 55, 20);
		this.z2Field.setValidator(this.getCoordinatePredicate("Z2"));
		this.z2Field.setText("Z2: " + BlockHelper.getPos2().getZ());
		
		this.block1Field = new GuiTextFieldTooltip(x + 118, y, 114, 20, this.selectedPage.equals("fill") ? I18n.format("gui.worldhandler.edit_blocks.fill.block_id_to_fill") : I18n.format("gui.worldhandler.edit_blocks.replace.block_id_replace"));
		this.block1Field.setValidator(Predicates.notNull());
		this.block1Field.setText(this.block1);
		
		this.block2Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace.block_id_place"));
		this.block2Field.setValidator(Predicates.notNull());
		this.block2Field.setText(this.block2);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(2, x, y, 114, 20, I18n.format("gui.worldhandler.edit_blocks.coordinates")));
		container.add(button4 = new GuiButtonWorldHandler(3, x, y + 24, 114, 20, I18n.format("gui.worldhandler.edit_blocks.fill")));
		container.add(button5 = new GuiButtonWorldHandler(4, x, y + 48, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace")));
		container.add(button6 = new GuiButtonWorldHandler(5, x, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.clone")));
		
		int yOffset1 = 0;
		int yOffset2 = 0;
		int xOffset2 = 0;
		int width1 = 0;
		int width2 = 0;
		
		if(this.selectedPage.equals("coordinates"))
		{
			button3.enabled = false;
			
			yOffset1 = 72;
			yOffset2 = 72;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
		}
		else if(this.selectedPage.equals("fill"))
		{
			button4.enabled = false;
			
			yOffset1 = 24;
			yOffset2 = 48;
			width1 = 114;
			width2 = 114;
			xOffset2 = 0;
			
			container.add(button3 = new GuiButtonWorldHandler(10, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.fill")));
			button3.enabled = ResourceHelper.isRegisteredBlock(this.builderFill.getBlock1String());
		}
		else if(this.selectedPage.equals("replace"))
		{
			button5.enabled = false;
			
			yOffset1 = 48;
			yOffset2 = 48;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			container.add(button3 = new GuiButtonWorldHandler(8, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace")));
			button3.enabled = ResourceHelper.isRegisteredBlock(this.builderFill.getBlock1String()) && ResourceHelper.isRegisteredBlock(this.builderFill.getBlock2String());
		}
		else if(this.selectedPage.equals("clone"))
		{
			button6.enabled = false;
			
			yOffset1 = 24;
			yOffset2 = 48;
			width1 = 114;
			width2 = 114;
			xOffset2 = 0;
			
			container.add(this.cloneButton = new GuiButtonList(9, x + 118, y, 114, 20, EnumTooltip.TOP_RIGHT, this, new IListButtonLogic<EnumMask>()
			{
				@Override
				public void actionPerformed(Container container, GuiButton button, ButtonStorage<EnumMask> storage)
				{
					builderClone.setMask(storage.getObject());
				}
				
				@Override
				public int getMax()
				{
					return EnumMask.values().length;
				}
				
				@Override
				public EnumMask getObject(int index)
				{
					return EnumMask.values()[index];
				}
				
				@Override
				public String getDisplayString(ButtonStorage<EnumMask> storage)
				{
					return I18n.format("gui.worldhandler.edit_blocks.clone.mode." + storage.getObject().toString());
				}
				
				@Override
				public String getId()
				{
					return "mask";
				}
			}));
			
			container.add(new GuiButtonWorldHandler(11, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.clone")));
		}
		
		container.add(new GuiButtonWorldHandler(6, x + 118, y + yOffset1, width1, 20, I18n.format("gui.worldhandler.edit_blocks.pos.set_pos_1")));
		container.add(new GuiButtonWorldHandler(7, x + 118 + xOffset2, y + yOffset2, width2, 20, I18n.format("gui.worldhandler.edit_blocks.pos.set_pos_2")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				this.selectedPage = "coordinates";
				container.initGui();
				break;
			case 3:
				this.selectedPage = "fill";
				container.initGui();
				break;
			case 4:
				this.selectedPage = "replace";
				container.initGui();
				break;
			case 5:
				this.selectedPage = "clone";
				container.initGui();
				break;
			case 6:
				BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
				container.initGui();
				break;
			case 7:
				BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
				container.initGui();
				break;
			case 8:
				WorldHandler.sendCommand(this.builderFill.getBuilderForReplace());
				break;
			case 9:
				this.cloneButton.actionPerformed(container, button);
				break;
			case 10:
				WorldHandler.sendCommand(this.builderFill.getBuilderForFill());
				break;
			case 11:
				WorldHandler.sendCommand(this.builderClone);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedPage.equals("coordinates"))
		{
			this.x1Field.drawTextBox();
			this.y1Field.drawTextBox();
			this.z1Field.drawTextBox();
			
			this.x2Field.drawTextBox();
			this.y2Field.drawTextBox();
			this.z2Field.drawTextBox();
		}
		else if(this.selectedPage.equals("fill"))
		{
			this.block1Field.drawTextBox();
		}
		else if(this.selectedPage.equals("replace"))
		{
			this.block1Field.drawTextBox();
			this.block2Field.drawTextBox();
		}
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.x1Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos1(BlockHelper.setX(BlockHelper.getPos1(), this.filterCoordinateText(this.x1Field.getText())));
		}
		
		if(this.y1Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos1(BlockHelper.setY(BlockHelper.getPos1(), this.filterCoordinateText(this.y1Field.getText())));
		}
		
		if(this.z1Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos1(BlockHelper.setZ(BlockHelper.getPos1(), this.filterCoordinateText(this.z1Field.getText())));
		}
		
		if(this.x2Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos2(BlockHelper.setX(BlockHelper.getPos2(), this.filterCoordinateText(this.x2Field.getText())));
		}
		
		if(this.y2Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos2(BlockHelper.setY(BlockHelper.getPos2(), this.filterCoordinateText(this.y2Field.getText())));
		}
		
		if(this.z2Field.textboxKeyTyped(typedChar, keyCode))
		{
			BlockHelper.setPos2(BlockHelper.setZ(BlockHelper.getPos2(), this.filterCoordinateText(this.z2Field.getText())));
		}
		
		if(this.block1Field.textboxKeyTyped(typedChar, keyCode))
		{
			this.block1 = this.block1Field.getText();
			this.builderFill.setBlock1(this.block1);
			container.initButtons();
		}
		
		if(this.block2Field.textboxKeyTyped(typedChar, keyCode))
		{
			this.block2 = this.block2Field.getText();
			this.builderFill.setBlock2(this.block2);
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.selectedPage.equals("coordinates"))
		{
			this.x1Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.y1Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.z1Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.x2Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.y2Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.z2Field.mouseClicked(mouseX, mouseY, mouseButton);
		}
		else if(this.selectedPage.equals("fill"))
		{
			this.block1Field.mouseClicked(mouseX, mouseY, mouseButton);
		}
		else if(this.selectedPage.equals("replace"))
		{
			this.block1Field.mouseClicked(mouseX, mouseY, mouseButton);
			this.block2Field.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	private Predicate<String> getCoordinatePredicate(String coordinate)
	{
		return string -> string.matches(coordinate + ": [-]?[0-9]*");
	}
	
	private int filterCoordinateText(String input)
	{
		if(input != null)
		{
			String[] split = input.split(": ", 2);
			
			if(split[1].matches("[-]?[0-9]+"))
			{
				return Integer.parseInt(split[1]);
			}
		}
		
		return 0;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.blocks.edit_blocks");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.blocks.edit_blocks");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.EDIT_BLOCKS;
	}
}
