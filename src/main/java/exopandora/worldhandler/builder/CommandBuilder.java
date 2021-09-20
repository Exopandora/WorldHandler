package exopandora.worldhandler.builder;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.CommandSyntax.Argument;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.builder.types.ItemResourceLocation;
import exopandora.worldhandler.builder.types.TargetSelector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public abstract class CommandBuilder implements ICommandBuilderSyntax
{
	private List<Entry<Argument, String>> command;
	
	public CommandBuilder()
	{
		this.updateSyntax(this.getSyntax());
	}
	
	protected void setNode(int index, String node)
	{
		this.set(index, node != null && !node.isEmpty() ? node : null, ArgumentType.STRING);
	}
	
	protected void setNode(int index, GreedyString node)
	{
		this.set(index, node != null && !node.isEmpty() ? node : null, ArgumentType.GREEDY_STRING);
	}
	
	protected void setNode(int index, boolean node)
	{
		this.set(index, node, ArgumentType.BOOLEAN);
	}
	
	protected void setNode(int index, short node)
	{
		this.set(index, node, ArgumentType.SHORT);
	}
	
	protected void setNode(int index, byte node)
	{
		this.set(index, node, ArgumentType.BYTE);
	}
	
	protected void setNode(int index, int node)
	{
		this.set(index, node, ArgumentType.INT);
	}
	
	protected void setNode(int index, float node)
	{
		this.set(index, node, ArgumentType.FLOAT);
	}
	
	protected void setNode(int index, double node)
	{
		this.set(index, node, ArgumentType.DOUBLE);
	}
	
	protected void setNode(int index, long node)
	{
		this.set(index, node, ArgumentType.LONG);
	}
	
	protected void setNode(int index, ResourceLocation node)
	{
		this.set(index, node, ArgumentType.RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, CoordinateInt coordinate)
	{
		this.set(index, coordinate, ArgumentType.COORDINATE_INT);
	}
	
	protected void setNode(int index, CoordinateDouble coordinate)
	{
		this.set(index, coordinate, ArgumentType.COORDINATE_DOUBLE);
	}
	
	protected void setNode(int index, TargetSelector target)
	{
		this.set(index, target, ArgumentType.TARGET_SELECTOR);
	}
	
	protected void setNode(int index, ItemResourceLocation resource)
	{
		this.set(index, resource, ArgumentType.ITEM_RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, BlockResourceLocation resource)
	{
		this.set(index, resource, ArgumentType.BLOCK_RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, CompoundTag nbt)
	{
		this.set(index, nbt, ArgumentType.NBT);
	}
	
	protected void setPlayerName(int index, String username)
	{
		this.set(index, username != null && !username.isEmpty() ? username : null, ArgumentType.PLAYER);
	}
	
	private void set(int index, Object value, ArgumentType type)
	{
		if(index < this.command.size())
		{
			Argument entry = this.command.get(index).getKey();
			ArgumentType expectedType = entry.getType();
			boolean typeMatch = expectedType.equals(type);
			
			if(value != null && typeMatch)
			{
				this.command.get(index).setValue(value.toString());
			}
			else
			{
				this.command.get(index).setValue(entry.toString());
				
				if(!typeMatch)
				{
					this.warn("set", expectedType, type, index);
				}
			}
		}
		else
		{
			WorldHandler.LOGGER.warn("Tried to set invalid index \"" + index + "\" for command \"" + this.getCommandName() + "\"");
		}
	}
	
	@Nullable
	protected String getNodeAsString(int index)
	{
		return this.get(index, ArgumentType.STRING);
	}
	
	@Nullable
	protected String getNodeAsGreedyString(int index)
	{
		return this.get(index, ArgumentType.GREEDY_STRING);
	}
	
	protected boolean getNodeAsBoolean(int index)
	{
		return this.get(index, ArgumentType.BOOLEAN);
	}
	
	protected short getNodeAsShort(int index)
	{
		return this.get(index, ArgumentType.SHORT);
	}
	
	protected byte getNodeAsByte(int index)
	{
		return this.get(index, ArgumentType.BYTE);
	}
	
	protected int getNodeAsInt(int index)
	{
		return this.get(index, ArgumentType.INT);
	}
	
	protected float getNodeAsFloat(int index)
	{
		return this.get(index, ArgumentType.FLOAT);
	}
	
	protected double getNodeAsDouble(int index)
	{
		return this.get(index, ArgumentType.DOUBLE);
	}
	
	protected long getNodeAsLong(int index)
	{
		return this.get(index, ArgumentType.LONG);
	}
	
	@Nonnull
	protected CoordinateInt getNodeAsCoordinateInt(int index)
	{
		return this.get(index, ArgumentType.COORDINATE_INT);
	}
	
	@Nonnull
	protected CoordinateDouble getNodeAsCoordinateDouble(int index)
	{
		return this.get(index, ArgumentType.COORDINATE_DOUBLE);
	}
	
	@Nullable
	protected ResourceLocation getNodeAsResourceLocation(int index)
	{
		return this.get(index, ArgumentType.RESOURCE_LOCATION);
	}
	
	@Nonnull
	protected TargetSelector getNodeAsTargetSelector(int index)
	{
		return this.get(index, ArgumentType.TARGET_SELECTOR);
	}
	
	@Nullable
	protected ItemResourceLocation getNodeAsItemResourceLocation(int index)
	{
		return this.get(index, ArgumentType.ITEM_RESOURCE_LOCATION);
	}
	
	@Nullable
	protected BlockResourceLocation getNodeAsBlockResourceLocation(int index)
	{
		return this.get(index, ArgumentType.BLOCK_RESOURCE_LOCATION);
	}
	
	@Nullable
	protected CompoundTag getNodeAsNBT(int index)
	{
		return this.get(index, ArgumentType.NBT);
	}
	
	@Nullable
	private <T> T get(int index, ArgumentType type)
	{
		if(index < this.command.size())
		{
			Entry<Argument, String> entry = this.command.get(index);
			ArgumentType expected = entry.getKey().getType();
			String value = entry.getValue();
			
			if(expected.equals(type))
			{
				if(value.equals(entry.getKey().toString()))
				{
					return null;
				}
				
				return expected.<T>parse(value);
			}
			
			this.warn("get", expected, type, index);
			
			return type.<T>parse(value);
		}
		
		return null;
	}
	
	private void warn(String function, ArgumentType expected, ArgumentType type, int index)
	{
		WorldHandler.LOGGER.warn("[" + function.toUpperCase() + "] Expected \"" + expected + "\" instead of \"" + type + "\" at index \"" + index + "\" for command \"" + this.getCommandName() + "\"");
	}
	
	private boolean isDefaultArgument(Argument argument, String value)
	{
		return argument.getDefault() != null ? value.equals(argument.getDefault().toString()) : value == null;
	}
	
	protected void updateSyntax(CommandSyntax syntax)
	{
		if(syntax != null)
		{
			this.command = syntax.getArguments().stream().map(entry -> new SimpleEntry<Argument, String>(entry, entry.toString())).collect(Collectors.toList());
		}
	}
	
	@Override
	public String toCommand()
	{
		CommandString command = new CommandString(this.getCommandName());
		
		for(Entry<Argument, String> entry : this.command)
		{
			if(this.isDefaultArgument(entry.getKey(), entry.getValue()))
			{
				command.append(entry.getKey().toString());
			}
			else
			{
				command.append(entry.getValue());
			}
		}
		
		return command.toString();
	}
	
	@Override
	public String toActualCommand()
	{
		CommandString command = new CommandString(this.getCommandName());
		
		for(Entry<Argument, String> entry : this.command)
		{
			if(!entry.getKey().isRequired() && (entry.getKey().toString().equals(entry.getValue()) || this.isDefaultArgument(entry.getKey(), entry.getValue())))
			{
				break;
			}
			
			if(entry.getKey().isRequired() && entry.getKey().toString().equals(entry.getValue()) && entry.getKey().getDefault() != null)
			{
				command.append(entry.getKey().getDefault().toString());
			}
			else
			{
				command.append(entry.getValue());
			}
		}
		
		return command.toString();
	}
}
