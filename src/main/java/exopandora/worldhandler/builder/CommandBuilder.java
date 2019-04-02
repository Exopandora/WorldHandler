package exopandora.worldhandler.builder;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.Syntax.SyntaxEntry;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.builder.types.ItemResourceLocation;
import exopandora.worldhandler.builder.types.TargetSelector;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CommandBuilder implements ICommandBuilderSyntax
{
	private List<Entry<SyntaxEntry, String>> command;
	
	public CommandBuilder()
	{
		this.updateSyntax(this.getSyntax());
	}
	
	protected void setNode(int index, String node)
	{
		this.set(index, node != null ? (node.isEmpty() ? null : node) : null, Type.STRING);
	}
	
	protected void setNode(int index, GreedyString node)
	{
		this.set(index, node != null ? (node.isEmpty() ? null : node) : null, Type.GREEDY_STRING);
	}
	
	protected void setNode(int index, boolean node)
	{
		this.set(index, node, Type.BOOLEAN);
	}
	
	protected void setNode(int index, short node)
	{
		this.set(index, node, Type.SHORT);
	}
	
	protected void setNode(int index, byte node)
	{
		this.set(index, node, Type.BYTE);
	}
	
	protected void setNode(int index, int node)
	{
		this.set(index, node, Type.INT);
	}
	
	protected void setNode(int index, float node)
	{
		this.set(index, node, Type.FLOAT);
	}
	
	protected void setNode(int index, double node)
	{
		this.set(index, node, Type.DOUBLE);
	}
	
	protected void setNode(int index, long node)
	{
		this.set(index, node, Type.LONG);
	}
	
	protected void setNode(int index, ResourceLocation node)
	{
		this.set(index, node, Type.RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, CoordinateInt coordinate)
	{
		this.set(index, coordinate, Type.COORDINATE_INT);
	}
	
	protected void setNode(int index, CoordinateDouble coordinate)
	{
		this.set(index, coordinate, Type.COORDINATE_DOUBLE);
	}
	
	protected void setNode(int index, TargetSelector target)
	{
		this.set(index, target, Type.TARGET_SELECTOR);
	}
	
	protected void setNode(int index, ItemResourceLocation resource)
	{
		this.set(index, resource != null ? resource.get() : null, Type.ITEM_RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, BlockResourceLocation resource)
	{
		this.set(index, resource != null ? resource.get() : null, Type.BLOCK_RESOURCE_LOCATION);
	}
	
	protected void setNode(int index, NBTTagCompound nbt)
	{
		this.set(index, nbt, Type.NBT);
	}
	
	private void set(int index, Object value, Type type)
	{
		if(index < this.command.size())
		{
			SyntaxEntry entry = this.command.get(index).getKey();
			Type expected = entry.getType();
			boolean flag = expected.equals(type);
			
			if(value != null && flag)
			{
				this.command.get(index).setValue(value.toString());
			}
			else
			{
				this.command.get(index).setValue(entry.toString());
				
				if(!flag)
				{
					this.warn("set", expected, type, index);
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
		return this.get(index, Type.STRING);
	}
	
	@Nullable
	protected String getNodeAsGreedyString(int index)
	{
		return this.get(index, Type.GREEDY_STRING);
	}
	
	protected boolean getNodeAsBoolean(int index)
	{
		return this.get(index, Type.BOOLEAN);
	}
	
	protected short getNodeAsShort(int index)
	{
		return this.get(index, Type.SHORT);
	}
	
	protected byte getNodeAsByte(int index)
	{
		return this.get(index, Type.BYTE);
	}
	
	protected int getNodeAsInt(int index)
	{
		return this.get(index, Type.INT);
	}
	
	protected float getNodeAsFloat(int index)
	{
		return this.get(index, Type.FLOAT);
	}
	
	protected double getNodeAsDouble(int index)
	{
		return this.get(index, Type.DOUBLE);
	}
	
	protected long getNodeAsLong(int index)
	{
		return this.get(index, Type.LONG);
	}
	
	protected CoordinateInt getNodeAsCoordinateInt(int index)
	{
		return this.get(index, Type.COORDINATE_INT);
	}
	
	protected CoordinateDouble getNodeAsCoordinateDouble(int index)
	{
		return this.get(index, Type.COORDINATE_DOUBLE);
	}
	
	@Nullable
	protected ResourceLocation getNodeAsResourceLocation(int index)
	{
		return this.get(index, Type.RESOURCE_LOCATION);
	}
	
	protected TargetSelector getNodeAsTargetSelector(int index)
	{
		return this.get(index, Type.TARGET_SELECTOR);
	}
	
	@Nullable
	protected ItemResourceLocation getNodeAsItemResourceLocation(int index)
	{
		return this.get(index, Type.ITEM_RESOURCE_LOCATION);
	}
	
	@Nullable
	protected BlockResourceLocation getNodeAsBlockResourceLocation(int index)
	{
		return this.get(index, Type.BLOCK_RESOURCE_LOCATION);
	}
	
	@Nullable
	protected NBTTagCompound getNodeAsNBT(int index)
	{
		return this.get(index, Type.NBT);
	}
	
	@Nullable
	private <T> T get(int index, Type type)
	{
		if(index < this.command.size())
		{
			Entry<SyntaxEntry, String> entry = this.command.get(index);
			Type expected = entry.getKey().getType();
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
	
	private void warn(String function, Type expected, Type type, int index)
	{
		WorldHandler.LOGGER.warn("[" + function.toUpperCase() + "] Expected \"" + expected + "\" instead of \"" + type + "\" at index \"" + index + "\" for command \"" + this.getCommandName() + "\"");
	}
	
	private boolean isDefaultEntry(Entry<SyntaxEntry, String> entry)
	{
		return entry.getKey().getDefault() != null ? entry.getValue().equals(entry.getKey().getDefault().toString()) : false;
	}
	
	protected void updateSyntax(Syntax syntax)
	{
		if(syntax != null)
		{
			this.command = syntax.getSyntaxEntries().stream().map(entry -> new SimpleEntry<SyntaxEntry, String>(entry, entry.toString())).collect(Collectors.toList());
		}
	}
	
	@Override
	public String toCommand()
	{
		CommandString command = new CommandString(this.getCommandName());
		
		for(Entry<SyntaxEntry, String> entry : this.command)
		{
			if(this.isDefaultEntry(entry))
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
		
		for(Entry<SyntaxEntry, String> entry : this.command)
		{
			if(!entry.getKey().isRequired() && (entry.getKey().toString().equals(entry.getValue()) || this.isDefaultEntry(entry)))
			{
				break;
			}
			
			command.append(entry.getValue());
		}
		
		return command.toString();
	}
}
