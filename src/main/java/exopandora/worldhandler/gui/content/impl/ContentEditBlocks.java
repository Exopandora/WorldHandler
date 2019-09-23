package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.brigadier.StringReader;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderClone;
import exopandora.worldhandler.builder.impl.BuilderClone.EnumMask;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderWH;
import exopandora.worldhandler.command.CommandWH.StringBlockPredicateArgument;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.logic.ILogicMapped;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
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
	
	private GuiTextFieldTooltip filterField;
	
	private final BuilderFill builderFill = BlockHelper.addPositionObservers(new BuilderFill(), builder -> builder::setPosition1, builder -> builder::setPosition2);
	private final BuilderClone builderClone = BlockHelper.addPositionObservers(new BuilderClone(), builder -> builder::setPosition1, builder -> builder::setPosition2);
	private final BuilderWH builderWH = new BuilderWH();
	
	private String block1;
	private String block2;
	private String filter;
	
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
		this.x1Field.func_212954_a(text ->
		{
			BlockHelper.setPos1(BlockHelper.setX(BlockHelper.getPos1(), this.parseCoordinate(text)));
		});
		
		this.y1Field = new GuiTextFieldTooltip(x + 118, y + 24, 55, 20);
		this.y1Field.setValidator(this.getCoordinatePredicate("Y1"));
		this.y1Field.setText("Y1: " + BlockHelper.getPos1().getY());
		this.y1Field.func_212954_a(text ->
		{
			BlockHelper.setPos1(BlockHelper.setY(BlockHelper.getPos1(), this.parseCoordinate(text)));
		});
		
		this.z1Field = new GuiTextFieldTooltip(x + 118, y + 48, 55, 20);
		this.z1Field.setValidator(this.getCoordinatePredicate("Z1"));
		this.z1Field.setText("Z1: " + BlockHelper.getPos1().getZ());
		this.z1Field.func_212954_a(text ->
		{
			BlockHelper.setPos1(BlockHelper.setZ(BlockHelper.getPos1(), this.parseCoordinate(text)));
		});
		
		this.x2Field = new GuiTextFieldTooltip(x + 118 + 59, y, 55, 20);
		this.x2Field.setValidator(this.getCoordinatePredicate("X2"));
		this.x2Field.setText("X2: " + BlockHelper.getPos2().getX());
		this.x2Field.func_212954_a(text ->
		{
			BlockHelper.setPos2(BlockHelper.setX(BlockHelper.getPos2(), this.parseCoordinate(text)));
		});
		
		this.y2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 24, 55, 20);
		this.y2Field.setValidator(this.getCoordinatePredicate("Y2"));
		this.y2Field.setText("Y2: " + BlockHelper.getPos2().getY());
		this.y2Field.func_212954_a(text ->
		{
			BlockHelper.setPos2(BlockHelper.setY(BlockHelper.getPos2(), this.parseCoordinate(text)));
		});
		
		this.z2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 48, 55, 20);
		this.z2Field.setValidator(this.getCoordinatePredicate("Z2"));
		this.z2Field.setText("Z2: " + BlockHelper.getPos2().getZ());
		this.z2Field.func_212954_a(text ->
		{
			BlockHelper.setPos2(BlockHelper.setZ(BlockHelper.getPos2(), this.parseCoordinate(text)));
		});
		
		this.block1Field = new GuiTextFieldTooltip(x + 118, y, 114, 20, this.selectedPage.equals("fill") ? I18n.format("gui.worldhandler.edit_blocks.fill.block_id_to_fill") : I18n.format("gui.worldhandler.edit_blocks.replace.block_id_replace"));
		this.block1Field.setValidator(Predicates.notNull());
		this.block1Field.setText(this.block1);
		this.block1Field.func_212954_a(text ->
		{
			this.block1 = text;
			this.builderFill.setBlock1(this.block1);
			container.initButtons();
		});
		
		this.block2Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace.block_id_place"));
		this.block2Field.setValidator(Predicates.notNull());
		this.block2Field.setText(this.block2);
		this.block2Field.func_212954_a(text ->
		{
			this.block2 = text;
			this.builderFill.setBlock2(this.block2);
			container.initButtons();
		});
		
		this.filterField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.edit_blocks.clone.filter"));
		this.filterField.setValidator(Predicates.notNull());
		this.filterField.setText(this.filter);
		this.filterField.func_212954_a(text ->
		{
			this.filter = text;
			this.builderClone.setFilter(this.filter);
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.edit_blocks.coordinates"), () ->
		{
			this.selectedPage = "coordinates";
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.edit_blocks.fill"), () ->
		{
			this.selectedPage = "fill";
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace"), () ->
		{
			this.selectedPage = "replace";
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.clone"), () ->
		{
			this.selectedPage = "clone";
			container.init();
		}));
		
		int yOffset1 = 0;
		int yOffset2 = 0;
		int xOffset2 = 0;
		int width1 = 0;
		int width2 = 0;
		
		if(this.selectedPage.equals("coordinates"))
		{
			button1.active = false;
			
			yOffset1 = 72;
			yOffset2 = 72;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			container.add(this.x1Field);
			container.add(this.y1Field);
			container.add(this.z1Field);
			container.add(this.x2Field);
			container.add(this.y2Field);
			container.add(this.z2Field);
		}
		else if(this.selectedPage.equals("fill"))
		{
			button2.active = false;
			
			yOffset1 = 24;
			yOffset2 = 48;
			width1 = 114;
			width2 = 114;
			xOffset2 = 0;
			
			container.add(this.block1Field);
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.fill"), () ->
			{
				CommandHelper.sendCommand(this.builderFill.getBuilderForFill());
			}));
			button1.active = ResourceHelper.isRegistered(this.builderFill.getBlock1(), ForgeRegistries.BLOCKS);
		}
		else if(this.selectedPage.equals("replace"))
		{
			button3.active = false;
			
			yOffset1 = 48;
			yOffset2 = 48;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			container.add(this.block1Field);
			container.add(this.block2Field);
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.replace"), () ->
			{
				CommandHelper.sendCommand(this.builderFill.getBuilderForReplace());
			}));
			button1.active = ResourceHelper.isRegistered(this.builderFill.getBlock1(), ForgeRegistries.BLOCKS) && ResourceHelper.isRegistered(this.builderFill.getBlock2(), ForgeRegistries.BLOCKS);
		}
		else if(this.selectedPage.equals("clone"))
		{
			button4.active = false;
			
			yOffset1 = 48;
			yOffset2 = 48;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			if(EnumMask.FILTERED.equals(this.builderClone.getMask()))
			{
				this.builderClone.setFilter(this.filter);
				container.add(this.filterField);
			}
			else
			{
				this.builderClone.setFilter(null);
				container.add(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, null, null));
				button1.active = false;
			}
			
			container.add(new GuiButtonList<EnumMask>(x + 118, y, Arrays.asList(EnumMask.values()), 114, 20, container, new ILogicMapped<EnumMask>()
			{
				@Override
				public String translate(EnumMask item)
				{
					return I18n.format("gui.worldhandler.edit_blocks.clone.mode." + item.toString());
				}
				
				@Override
				public String toTooltip(EnumMask item)
				{
					return item.toString();
				}
				
				@Override
				public void onClick(EnumMask item)
				{
					ContentEditBlocks.this.builderClone.setMask(item);
					container.init();
				}
				
				@Override
				public String getId()
				{
					return "mask";
				}
			}));
			
			container.add(button2 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.edit_blocks.clone"), () ->
			{
				CommandHelper.sendCommand(this.builderClone);
			}));
			
			try
			{
				if(EnumMask.FILTERED.equals(this.builderClone.getMask()))
				{
					StringBlockPredicateArgument.blockPredicate().parse(new StringReader(this.builderClone.getFilter()));
				}
			}
			catch(Exception e)
			{
				button2.active = false;
			}
		}
		
		container.add(new GuiButtonBase(x + 118, y + yOffset1, width1, 20, I18n.format("gui.worldhandler.edit_blocks.pos.set_pos_1"), () ->
		{
			BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
			container.init();
		}));
		container.add(new GuiButtonBase(x + 118 + xOffset2, y + yOffset2, width2, 20, I18n.format("gui.worldhandler.edit_blocks.pos.set_pos_2"), () ->
		{
			BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
			container.init();
		}));
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.selectedPage.equals("coordinates"))
		{
			this.x1Field.tick();
			this.y1Field.tick();
			this.z1Field.tick();
			
			this.x2Field.tick();
			this.y2Field.tick();
			this.z2Field.tick();
		}
		else if(this.selectedPage.equals("fill"))
		{
			this.block1Field.tick();
		}
		else if(this.selectedPage.equals("replace"))
		{
			this.block1Field.tick();
			this.block2Field.tick();
		}
		else if(this.selectedPage.equals("clone"))
		{
			if(EnumMask.FILTERED.equals(this.builderClone.getMask()))
			{
				this.filterField.tick();
			}
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedPage.equals("coordinates"))
		{
			this.x1Field.renderButton(mouseX, mouseY, partialTicks);
			this.y1Field.renderButton(mouseX, mouseY, partialTicks);
			this.z1Field.renderButton(mouseX, mouseY, partialTicks);
			
			this.x2Field.renderButton(mouseX, mouseY, partialTicks);
			this.y2Field.renderButton(mouseX, mouseY, partialTicks);
			this.z2Field.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.selectedPage.equals("fill"))
		{
			this.block1Field.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.selectedPage.equals("replace"))
		{
			this.block1Field.renderButton(mouseX, mouseY, partialTicks);
			this.block2Field.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.selectedPage.equals("clone"))
		{
			if(EnumMask.FILTERED.equals(this.builderClone.getMask()))
			{
				this.filterField.renderButton(mouseX, mouseY, partialTicks);
			}
		}
	}
	
	private Predicate<String> getCoordinatePredicate(String coordinate)
	{
		return string -> string.matches(coordinate + ": [-]?[0-9]*");
	}
	
	private int parseCoordinate(String input)
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
