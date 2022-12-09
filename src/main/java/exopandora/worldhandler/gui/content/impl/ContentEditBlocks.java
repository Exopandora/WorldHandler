package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.impl.CloneCommandBuilder;
import exopandora.worldhandler.builder.impl.CloneCommandBuilder.Label;
import exopandora.worldhandler.builder.impl.FillCommandBuilder;
import exopandora.worldhandler.builder.impl.WHCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicMapped;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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
	
	private final FillCommandBuilder builderFill = new FillCommandBuilder();
	private final CloneCommandBuilder builderClone = new CloneCommandBuilder();
	private final WHCommandBuilder builderWH = new WHCommandBuilder();
	
	private String block1;
	private String block2;
	private String filter;
	private Page page = Page.COORDINATES;
	private Mask mask = Mask.FILTERED;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		if(Page.COORDINATES.equals(this.page))
		{
			return new CommandPreview(this.builderWH, null);
		}
		else if(Page.FILL.equals(this.page) || Page.REPLACE.equals(this.page))
		{
			return new CommandPreview(this.builderFill, FillCommandBuilder.Label.FILL);
		}
		else if(Page.CLONE.equals(this.page))
		{
			return new CommandPreview(this.builderClone, this.mask.getLabel());
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.updatePositions();
		
		this.x1Field = new GuiTextFieldTooltip(x + 118, y, 55, 20);
		this.x1Field.setFilter(this.coordinatePredicate("X1"));
		this.x1Field.setValue("X1: " + BlockHelper.pos1().getX());
		this.x1Field.setResponder(text -> BlockHelper.pos1().setX(this.parseCoordinate(text)));
		
		this.y1Field = new GuiTextFieldTooltip(x + 118, y + 24, 55, 20);
		this.y1Field.setFilter(this.coordinatePredicate("Y1"));
		this.y1Field.setValue("Y1: " + BlockHelper.pos1().getY());
		this.y1Field.setResponder(text -> BlockHelper.pos1().setY(this.parseCoordinate(text)));
		
		this.z1Field = new GuiTextFieldTooltip(x + 118, y + 48, 55, 20);
		this.z1Field.setFilter(this.coordinatePredicate("Z1"));
		this.z1Field.setValue("Z1: " + BlockHelper.pos1().getZ());
		this.z1Field.setResponder(text -> BlockHelper.pos1().setZ(this.parseCoordinate(text)));
		
		this.x2Field = new GuiTextFieldTooltip(x + 118 + 59, y, 55, 20);
		this.x2Field.setFilter(this.coordinatePredicate("X2"));
		this.x2Field.setValue("X2: " + BlockHelper.pos2().getX());
		this.x2Field.setResponder(text -> BlockHelper.pos2().setX(this.parseCoordinate(text)));
		
		this.y2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 24, 55, 20);
		this.y2Field.setFilter(this.coordinatePredicate("Y2"));
		this.y2Field.setValue("Y2: " + BlockHelper.pos2().getY());
		this.y2Field.setResponder(text -> BlockHelper.pos2().setY(this.parseCoordinate(text)));
		
		this.z2Field = new GuiTextFieldTooltip(x + 118 + 59, y + 48, 55, 20);
		this.z2Field.setFilter(this.coordinatePredicate("Z2"));
		this.z2Field.setValue("Z2: " + BlockHelper.pos2().getZ());
		this.z2Field.setResponder(text -> BlockHelper.pos2().setZ(this.parseCoordinate(text)));
		
		this.block1Field = new GuiTextFieldTooltip(x + 118, y, 114, 20, Page.FILL.equals(this.page) ? Component.translatable("gui.worldhandler.edit_blocks.fill.block_id_to_fill") : Component.translatable("gui.worldhandler.edit_blocks.replace.block_id_replace"));
		this.block1Field.setFilter(Predicates.notNull());
		this.block1Field.setValue(this.block1);
		this.block1Field.setResponder(text ->
		{
			this.block1 = text;
			this.builderFill.block().deserialize(this.block1.replace(' ', '_'));
			container.initButtons();
		});
		
		this.block2Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.replace.block_id_place"));
		this.block2Field.setFilter(Predicates.notNull());
		this.block2Field.setValue(this.block2);
		this.block2Field.setResponder(text ->
		{
			this.block2 = text;
			this.builderFill.filter().deserialize(this.block2.replace(' ', '_'));
			container.initButtons();
		});
		
		this.filterField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.clone.filter"));
		this.filterField.setFilter(Predicates.notNull());
		this.filterField.setValue(this.filter);
		this.filterField.setResponder(text ->
		{
			this.filter = text;
			this.builderClone.filter().deserialize(this.filter.replace(' ', '_'));
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
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x, y, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.coordinates"), () ->
		{
			this.page = Page.COORDINATES;
			container.init();
		}));
		container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 24, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.fill"), () ->
		{
			this.page = Page.FILL;
			container.init();
		}));
		container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 48, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.replace"), () ->
		{
			this.page = Page.REPLACE;
			container.init();
		}));
		container.addRenderableWidget(button4 = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.clone"), () ->
		{
			this.page = Page.CLONE;
			container.init();
		}));
		
		int yOffset1 = 0;
		int yOffset2 = 0;
		int xOffset2 = 0;
		int width1 = 0;
		int width2 = 0;
		
		if(Page.COORDINATES.equals(this.page))
		{
			button1.active = false;
			
			yOffset1 = 72;
			yOffset2 = 72;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			container.addRenderableWidget(this.x1Field);
			container.addRenderableWidget(this.y1Field);
			container.addRenderableWidget(this.z1Field);
			container.addRenderableWidget(this.x2Field);
			container.addRenderableWidget(this.y2Field);
			container.addRenderableWidget(this.z2Field);
		}
		else if(Page.FILL.equals(this.page))
		{
			button2.active = false;
			
			yOffset1 = 24;
			yOffset2 = 48;
			width1 = 114;
			width2 = 114;
			xOffset2 = 0;
			
			container.addRenderableWidget(this.block1Field);
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.fill"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderFill, FillCommandBuilder.Label.FILL);
			}));
			button1.active = this.builderFill.block().hasValue();
		}
		else if(Page.REPLACE.equals(this.page))
		{
			button3.active = false;
			
			yOffset1 = 48;
			yOffset2 = 48;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			container.addRenderableWidget(this.block1Field);
			container.addRenderableWidget(this.block2Field);
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.replace"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderFill, FillCommandBuilder.Label.REPLACE);
			}));
			button1.active = this.builderFill.block().hasValue() && this.builderFill.filter().hasValue();
		}
		else if(Page.CLONE.equals(this.page))
		{
			button4.active = false;
			
			yOffset1 = 48;
			yOffset2 = 48;
			width1 = 56;
			width2 = 56;
			xOffset2 = 58;
			
			if(Mask.FILTERED.equals(this.mask))
			{
				container.addRenderableWidget(this.filterField);
			}
			else
			{
				container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, Component.empty(), null));
				button1.active = false;
			}
			
			container.addRenderableWidget(new GuiButtonList<Mask>(x + 118, y, Arrays.asList(Mask.values()), 114, 20, container, new ILogicMapped<Mask>()
			{
				@Override
				public MutableComponent translate(Mask mask)
				{
					return Component.translatable("gui.worldhandler.edit_blocks.clone.mode." + mask.toString());
				}
				
				@Override
				public MutableComponent toTooltip(Mask mask)
				{
					return Component.literal(mask.toString());
				}
				
				@Override
				public void onClick(Mask mask)
				{
					ContentEditBlocks.this.mask = mask;
					container.init();
				}
				
				@Override
				public String getId()
				{
					return "mask";
				}
			}));
			
			container.addRenderableWidget(button2 = new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.edit_blocks.clone"), () ->
			{
				System.out.println(this.builderClone.toCommand(this.mask.getLabel(), false));
				CommandHelper.sendCommand(container.getPlayer(), this.builderClone, this.mask.getLabel());
			}));
			
			if(Mask.FILTERED.equals(this.mask))
			{
				button2.active = this.builderClone.filter().hasValue();
			}
		}
		
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + yOffset1, width1, 20, Component.translatable("gui.worldhandler.edit_blocks.pos.set_pos_1"), () ->
		{
			BlockHelper.pos1().set(BlockHelper.getFocusedBlockPos());
			this.updatePositions();
			container.init();
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 118 + xOffset2, y + yOffset2, width2, 20, Component.translatable("gui.worldhandler.edit_blocks.pos.set_pos_2"), () ->
		{
			BlockHelper.pos2().set(BlockHelper.getFocusedBlockPos());
			this.updatePositions();
			container.init();
		}));
	}
	
	private void updatePositions()
	{
		this.builderFill.from().set(BlockHelper.pos1());
		this.builderFill.to().set(BlockHelper.pos2());
		this.builderClone.begin().set(BlockHelper.pos1());
		this.builderClone.end().set(BlockHelper.pos2());
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.COORDINATES.equals(this.page))
		{
			this.x1Field.tick();
			this.y1Field.tick();
			this.z1Field.tick();
			
			this.x2Field.tick();
			this.y2Field.tick();
			this.z2Field.tick();
		}
		else if(Page.FILL.equals(this.page))
		{
			this.block1Field.tick();
		}
		else if(Page.REPLACE.equals(this.page))
		{
			this.block1Field.tick();
			this.block2Field.tick();
		}
		else if(Page.CLONE.equals(this.page))
		{
			this.builderClone.destination().set(Minecraft.getInstance().player.blockPosition());	
			
			if(Mask.FILTERED.equals(this.mask))
			{
				this.filterField.tick();
			}
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.COORDINATES.equals(this.page))
		{
			this.x1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.y1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.z1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			
			this.x2Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.y2Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.z2Field.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.FILL.equals(this.page))
		{
			this.block1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.REPLACE.equals(this.page))
		{
			this.block1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.block2Field.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.CLONE.equals(this.page))
		{
			if(Mask.FILTERED.equals(this.mask))
			{
				this.filterField.renderButton(matrix, mouseX, mouseY, partialTicks);
			}
		}
	}
	
	private Predicate<String> coordinatePredicate(String coordinate)
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
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.blocks.edit_blocks");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.blocks.edit_blocks");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.EDIT_BLOCKS;
	}
	
	private static enum Page
	{
		COORDINATES,
		FILL,
		REPLACE,
		CLONE;
	}
	
	private static enum Mask
	{
		FILTERED(CloneCommandBuilder.Label.FILTERED),
		MASKED(CloneCommandBuilder.Label.MASKED),
		REPLACE(CloneCommandBuilder.Label.REPLACE);
		
		private final CloneCommandBuilder.Label label;
		
		private Mask(Label label)
		{
			this.label = label;
		}
		
		public CloneCommandBuilder.Label getLabel()
		{
			return this.label;
		}
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
