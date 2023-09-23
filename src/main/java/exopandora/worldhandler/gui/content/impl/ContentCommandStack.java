package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.argument.Coordinate;
import exopandora.worldhandler.builder.argument.TargetArgument.SelectorTypes;
import exopandora.worldhandler.builder.argument.tag.EntityTag;
import exopandora.worldhandler.builder.impl.FillCommandBuilder;
import exopandora.worldhandler.builder.impl.KillCommandBuilder;
import exopandora.worldhandler.builder.impl.SetBlockCommandBuilder;
import exopandora.worldhandler.builder.impl.SummonCommandBuilder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiHintTextField;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentCommandStack extends ContentChild
{
	private static final int HEAD_LENGTH = 1;
	private static final int TAIL_LENGTH = 2;
	private static final Component PLUS = Component.literal("+");
	private static final Component MINUS = Component.literal("-");
	
	private final List<GuiHintTextField> textfields = new ArrayList<GuiHintTextField>();
	private int scroll;
	private GuiButtonBase buttonCopy;
	
	private final SummonCommandBuilder builderCommandStack = new SummonCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderCommandStack, SummonCommandBuilder.Label.SUMMON_POS_NBT);
	private final EntityTag activatorRail = new EntityTag();
	
	public ContentCommandStack()
	{
		this.builderCommandStack.entity().set(EntityType.FALLING_BLOCK);
		this.builderCommandStack.pos().setX(new Coordinate.Doubles(Coordinate.Type.RELATIVE));
		this.builderCommandStack.pos().setY(new Coordinate.Doubles(0.5, Coordinate.Type.RELATIVE));
		this.builderCommandStack.pos().setZ(new Coordinate.Doubles(Coordinate.Type.RELATIVE));
		
		this.activatorRail.setMotion(0.0D, 0.315D, 0.0D);
		this.activatorRail.setTime(1);
		this.activatorRail.setBlockState(Blocks.ACTIVATOR_RAIL.defaultBlockState());
		this.builderCommandStack.nbt().addTagProvider(this.activatorRail);
		
		EntityTag redstoneBlock = new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.FALLING_BLOCK));
		redstoneBlock.setTime(1);
		redstoneBlock.setBlockState(Blocks.REDSTONE_BLOCK.defaultBlockState());
		this.activatorRail.addPassenger(redstoneBlock);
		
		this.addCommand(0);
		
		EntityTag blockRemover = new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.COMMAND_BLOCK_MINECART));
		SetBlockCommandBuilder builder = new SetBlockCommandBuilder();
		builder.pos().setX(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		builder.pos().setY(new Coordinate.Ints(-2, Coordinate.Type.RELATIVE));
		builder.pos().setZ(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		builder.block().set(Blocks.REPEATING_COMMAND_BLOCK);
		CompoundTag commandBlock = new CompoundTag();
		commandBlock.putByte("auto", (byte) 1);
		FillCommandBuilder fill = new FillCommandBuilder();
		fill.from().setX(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		fill.from().setY(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		fill.from().setZ(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		fill.to().setX(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		fill.to().setY(new Coordinate.Ints(2, Coordinate.Type.RELATIVE));
		fill.to().setZ(new Coordinate.Ints(Coordinate.Type.RELATIVE));
		fill.block().set(Blocks.AIR);
		commandBlock.putString("Command", fill.toCommand(FillCommandBuilder.Label.FILL, false));
		builder.block().setTag(commandBlock);
		blockRemover.setCommand(builder.toCommand(SetBlockCommandBuilder.Label.DESTROY, false));
		this.activatorRail.addPassenger(blockRemover);
		
		EntityTag entityRemover = new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.COMMAND_BLOCK_MINECART));
		KillCommandBuilder kill = new KillCommandBuilder();
		kill.targets().setSelectorType(SelectorTypes.ALL_ENTITIES);
		kill.targets().setType(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.COMMAND_BLOCK_MINECART));
		kill.targets().setDistanceMax(1.0D);
		entityRemover.setCommand(kill.toCommand(KillCommandBuilder.Label.KILL_TARGETS, false));
		this.activatorRail.addPassenger(entityRemover);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.textfields.clear();
		
		for(int index = 0; index < 3; index++)
		{
			int command = index + this.scroll;
			
			GuiHintTextField textfield = new GuiHintTextField(x, y + 24 * index, 232 - 48, 20, Component.translatable("gui.worldhandler.command_stack.command_n", command + 1));
			textfield.setFilter(Predicates.notNull());
			textfield.setValue(command < this.getCommandCount() ? this.getCommand(command) : null);
			textfield.setResponder(text ->
			{
				this.setCommand(command, text);
				this.updateCopyButton();
			});
			this.textfields.add(textfield);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase buttonScrollUp;
		GuiButtonBase buttonScrollDown;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		this.iterate(index ->
		{
			GuiButtonBase buttonUp;
			GuiButtonBase buttonDown;
			GuiButtonBase buttonRemove;
			
			container.addRenderableWidget(buttonUp = new GuiButtonIcon(x + 232 - 20 - 24, y + index * 24 - 1, 20, 10, EnumIcon.ARROW_UP, Component.translatable("gui.worldhandler.actions.move_up"), () ->
			{
				this.swapCommands(index + this.scroll, index + this.scroll - 1);
				container.init();
			}));
			container.addRenderableWidget(buttonDown = new GuiButtonIcon(x + 232 - 20 - 24, y + index * 24 + 11, 20, 10, EnumIcon.ARROW_DOWN, Component.translatable("gui.worldhandler.actions.move_down"), () ->
			{
				this.swapCommands(index + this.scroll, index + this.scroll + 1);
				container.init();
			}));
			container.addRenderableWidget(buttonRemove = new GuiButtonTooltip(x + 232 - 20, y + index * 24 - 1, 20, 10, MINUS, Component.translatable("gui.worldhandler.command_stack.remove_command"), () ->
			{
				int pos = index + this.scroll;
				this.removeCommand(pos);
				
				if(this.scroll + 3 > this.getCommandCount())
				{
					this.scrollUp();
				}
				
				container.init();
			}));
			container.addRenderableWidget(new GuiButtonTooltip(x + 232 - 20, y + index * 24 + 11, 20, 10, PLUS, Component.translatable("gui.worldhandler.command_stack.insert_command"), () ->
			{
				int pos = index + this.scroll + 1;
				this.addCommand(pos);
				
				if(index == 2)
				{
					this.scrollDown();
				}
				
				container.init();
			}));
			container.addRenderableWidget(this.textfields.get(index));
			
			buttonRemove.active = this.getCommandCount() > 1;
			buttonUp.active = index + this.scroll > 0;
			buttonDown.active = index + this.scroll + 1 < this.getCommandCount();
		});
		
		container.addRenderableWidget(this.buttonCopy = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.command_stack.copy_command"), () -> 
		{
			Minecraft.getInstance().keyboardHandler.setClipboard(this.builderCommandStack.toCommand(SummonCommandBuilder.Label.SUMMON_POS_NBT, false));
		}));
		container.addRenderableWidget(buttonScrollUp = new GuiButtonIcon(x + 118, y + 72, 56, 20, EnumIcon.ARROW_UP, Component.translatable("gui.worldhandler.actions.move_up"), () ->
		{
			this.scrollUp();
			container.init();
		}));
		container.addRenderableWidget(buttonScrollDown = new GuiButtonIcon(x + 118 + 60, y + 72, 54, 20, EnumIcon.ARROW_DOWN, Component.translatable("gui.worldhandler.actions.move_down"), () -> 
		{
			this.scrollDown();
			container.init();
		}));
		
		this.updateCopyButton();
		buttonScrollUp.active = this.scroll > 0;
		buttonScrollDown.active = this.scroll < this.getCommandCount() - 3;
	}
	
	private void iterate(Consumer<Integer> consumer)
	{
		for(int x = 0; x < this.textfields.size() && x + this.scroll < this.getCommandCount(); x++)
		{
			consumer.accept(x);
		}
	}
	
	private void scrollUp()
	{
		this.scroll = Math.max(0, this.scroll - (Screen.hasShiftDown() ? 10 : 1));
	}
	
	private void scrollDown()
	{
		this.scroll = Math.min(this.getCommandCount() - 3, this.scroll + (Screen.hasShiftDown() ? 10 : 1));
	}
	
	private void updateCopyButton()
	{
		boolean active = false;
		
		for(int x = 0; x < this.getCommandCount() && !active; x++)
		{
			String command = this.getCommand(x);
			
			if(command != null && !command.isEmpty())
			{
				active = true;
			}
		}
		
		this.buttonCopy.active = active;
	}
	
	private void setCommand(int index, String command)
	{
		EntityTag entity = this.activatorRail.getPassenger(index + HEAD_LENGTH);
		
		if(entity != null)
		{
			entity.setCommand(command);
		}
	}
	
	private void addCommand(int index)
	{
		this.activatorRail.addPassenger(index + HEAD_LENGTH, new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.COMMAND_BLOCK_MINECART)));
	}
	
	private void removeCommand(int index)
	{
		this.activatorRail.removePassenger(index + HEAD_LENGTH);
	}
	
	@Nullable
	private String getCommand(int index)
	{
		EntityTag entity = this.activatorRail.getPassenger(index + HEAD_LENGTH);
		
		if(entity != null)
		{
			return entity.getCommand();
		}
		
		return null;
	}
	
	private int getCommandCount()
	{
		return this.activatorRail.getPassengerCount() - HEAD_LENGTH - TAIL_LENGTH;
	}
	
	private void swapCommands(int i, int j)
	{
		Collections.swap(this.activatorRail.getPassengers(), i + HEAD_LENGTH, j + HEAD_LENGTH);
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.command_stack");
	}
}
