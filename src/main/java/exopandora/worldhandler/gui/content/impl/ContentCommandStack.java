package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.component.impl.EntityNBT;
import exopandora.worldhandler.builder.impl.BuilderButcher;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderSetBlock;
import exopandora.worldhandler.builder.impl.BuilderSetBlock.EnumMode;
import exopandora.worldhandler.builder.impl.BuilderSummon;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentCommandStack extends ContentChild
{
	private static final int HEAD_LENGTH = 1;
	private static final int TAIL_LENGTH = 2;
	
	private final List<GuiTextFieldTooltip> textfields = new ArrayList<GuiTextFieldTooltip>();
	private int scroll;
	private GuiButtonBase buttonCopy;
	
	private final BuilderSummon builderCommandStack = new BuilderSummon();
    
	public ContentCommandStack()
	{
		this.builderCommandStack.setEntity(EntityType.FALLING_BLOCK.getRegistryName());
		this.builderCommandStack.setX(new CoordinateDouble(0.0D, EnumType.GLOBAL));
		this.builderCommandStack.setY(new CoordinateDouble(0.5D, EnumType.GLOBAL));
		this.builderCommandStack.setZ(new CoordinateDouble(0.0D, EnumType.GLOBAL));
		this.builderCommandStack.setMotion(0.0D, 0.315D, 0.0D);
		this.builderCommandStack.setTime(1);
		this.builderCommandStack.setBlockState(Blocks.ACTIVATOR_RAIL.getDefaultState());
		
		EntityNBT redstoneBlock = new EntityNBT(EntityType.FALLING_BLOCK.getRegistryName());
		redstoneBlock.setTime(1);
		redstoneBlock.setBlockState(Blocks.REDSTONE_BLOCK.getDefaultState());
		this.builderCommandStack.addPassenger(redstoneBlock);
		
		this.addCommand(0);
		
		EntityNBT blockRemover = new EntityNBT(EntityType.COMMAND_BLOCK_MINECART.getRegistryName());
		BuilderSetBlock builder = new BuilderSetBlock(new CoordinateInt(EnumType.GLOBAL), new CoordinateInt(-2, EnumType.GLOBAL), new CoordinateInt(EnumType.GLOBAL), Blocks.REPEATING_COMMAND_BLOCK.getRegistryName(), EnumMode.DESTROY);
		CompoundNBT commandBlock = new CompoundNBT();
		commandBlock.putByte("auto", (byte) 1);
		commandBlock.putString("Command", new BuilderFill(new CoordinateInt(EnumType.GLOBAL), new CoordinateInt(EnumType.GLOBAL), new CoordinateInt(EnumType.GLOBAL), new CoordinateInt(EnumType.GLOBAL), new CoordinateInt(2, EnumType.GLOBAL), new CoordinateInt(EnumType.GLOBAL), new BlockResourceLocation(Blocks.AIR.getRegistryName())).toActualCommand());
		builder.setBlockNBT(commandBlock);
		blockRemover.setCommand(builder.toActualCommand());
		this.builderCommandStack.addPassenger(blockRemover);
		
		EntityNBT entityRemover = new EntityNBT(EntityType.COMMAND_BLOCK_MINECART.getRegistryName());
		entityRemover.setCommand(new BuilderButcher(EntityType.COMMAND_BLOCK_MINECART.getRegistryName(), 1).toActualCommand());
		this.builderCommandStack.addPassenger(entityRemover);
	}
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderCommandStack;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.textfields.clear();
		
		for(int index = 0; index < 3; index++)
		{
			int command = index + this.scroll;
			
			GuiTextFieldTooltip textfield = new GuiTextFieldTooltip(x, y + 24 * index, 232 - 48, 20, I18n.format("gui.worldhandler.command_stack.command_n", command + 1));
			textfield.setValidator(Predicates.notNull());
			textfield.setText(command < this.getCommandCount() ? this.getCommand(command) : null);
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		this.iterate(index ->
		{
			GuiButtonBase buttonUp;
			GuiButtonBase buttonDown;
			GuiButtonBase buttonRemove;
			
			container.add(buttonUp = new GuiButtonIcon(x + 232 - 20 - 24, y + index * 24 - 1, 20, 10, EnumIcon.ARROW_UP, I18n.format("gui.worldhandler.actions.move_up"), () ->
			{
				this.swapCommands(index + this.scroll, index + this.scroll - 1);
				container.init();
			}));
			container.add(buttonDown = new GuiButtonIcon(x + 232 - 20 - 24, y + index * 24 + 11, 20, 10, EnumIcon.ARROW_DOWN, I18n.format("gui.worldhandler.actions.move_down"), () ->
			{
				this.swapCommands(index + this.scroll, index + this.scroll + 1);
				container.init();
			}));
			container.add(buttonRemove = new GuiButtonTooltip(x + 232 - 20, y + index * 24 - 1, 20, 10, "-", I18n.format("gui.worldhandler.command_stack.remove_command"), () ->
			{
				int pos = index + this.scroll;
				this.removeCommand(pos);
				
				if(this.scroll + 3 > this.getCommandCount())
				{
					this.scrollUp();
				}
				
				container.init();
			}));
			container.add(new GuiButtonTooltip(x + 232 - 20, y + index * 24 + 11, 20, 10, "+", I18n.format("gui.worldhandler.command_stack.insert_command"), () ->
			{
				int pos = index + this.scroll + 1;
				this.addCommand(pos);
				
				if(index == 2)
				{
					this.scrollDown();
				}
				
				container.init();
			}));
			container.add(this.textfields.get(index));
			
			buttonRemove.active = this.getCommandCount() > 1;
			buttonUp.active = index + this.scroll > 0;
			buttonDown.active = index + this.scroll + 1 < this.getCommandCount();
		});
		
		container.add(this.buttonCopy = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.command_stack.copy_command"), () -> 
		{
			Minecraft.getInstance().keyboardListener.setClipboardString(this.builderCommandStack.toActualCommand());
		}));
		container.add(buttonScrollUp = new GuiButtonIcon(x + 118, y + 72, 56, 20, EnumIcon.ARROW_UP, I18n.format("gui.worldhandler.actions.move_up"), () ->
		{
			this.scrollUp();
			container.init();
		}));
		container.add(buttonScrollDown = new GuiButtonIcon(x + 118 + 60, y + 72, 54, 20, EnumIcon.ARROW_DOWN, I18n.format("gui.worldhandler.actions.move_down"), () -> 
		{
			this.scrollDown();
			container.init();
		}));
		
		this.updateCopyButton();
		buttonScrollUp.active = this.scroll > 0;
		buttonScrollDown.active = this.scroll < this.getCommandCount() - 3;
	}
	
	@Override
	public void tick(Container container)
	{
		this.iterate(index ->
		{
			this.textfields.get(index).tick();
		});
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.iterate(index ->
		{
			this.textfields.get(index).renderButton(mouseX, mouseY, partialTicks);
		});
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
		this.builderCommandStack.getPassenger(index + HEAD_LENGTH).setCommand(command);
	}
	
	private void addCommand(int index)
	{
		this.builderCommandStack.addPassenger(index + HEAD_LENGTH, new EntityNBT(EntityType.COMMAND_BLOCK_MINECART.getRegistryName()));
	}
	
	private void removeCommand(int index)
	{
		this.builderCommandStack.removePassenger(index + HEAD_LENGTH);
	}
	
	private String getCommand(int index)
	{
		return this.builderCommandStack.getPassenger(index + HEAD_LENGTH).getCommand();
	}
	
	private int getCommandCount()
	{
		return this.builderCommandStack.getPassengerCount() - HEAD_LENGTH - TAIL_LENGTH;
	}
	
	private void swapCommands(int i, int j)
	{
		Collections.swap(this.builderCommandStack.getPassengers(), i + HEAD_LENGTH, j + HEAD_LENGTH);
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.command_stack");
	}
}