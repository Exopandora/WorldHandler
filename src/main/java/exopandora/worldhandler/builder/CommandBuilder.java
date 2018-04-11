package exopandora.worldhandler.builder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.Syntax.SyntaxEntry;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.builder.types.Level;
import exopandora.worldhandler.builder.types.TargetSelector;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	
	protected void setNode(int index, NBTTagCompound nbt)
	{
		this.set(index, nbt, Type.NBT);
	}
	
	protected void setNode(int index, Coordinate coordinate)
	{
		this.set(index, coordinate, Type.COORDINATE);
	}
	
	protected void setNode(int index, TargetSelector target)
	{
		this.set(index, target, Type.TARGET_SELECTOR);
	}
	
	protected void setNode(int index, Level level)
	{
		this.set(index, level, Type.LEVEL);
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
	
	protected Coordinate getNodeAsCoordinate(int index)
	{
		return this.get(index, Type.COORDINATE);
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
	protected Level getNodeAsLevel(int index)
	{
		return this.get(index, Type.LEVEL);
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
			this.command = syntax.getSyntaxEntries().stream().map(entry -> new AbstractMap.SimpleEntry<SyntaxEntry, String>(entry, entry.toString())).collect(Collectors.toList());
		}
	}
	
	@Override
	public String toCommand()
	{
		return "/" + this.getCommandName() + " " + String.join(" ", this.command.stream().map(entry -> this.isDefaultEntry(entry) ? entry.getKey().toString() : entry.getValue()).collect(Collectors.toList()));
	}
	
	@Override
	public String toActualCommand()
	{
		List<String> command = new ArrayList<String>();
		
		for(Entry<SyntaxEntry, String> entry : this.command)
		{
			if(!entry.getKey().isRequired() && (entry.getKey().toString().equals(entry.getValue()) || this.isDefaultEntry(entry)))
			{
				break;
			}
			
			command.add(entry.getValue());
		}
		
		return "/" + this.getCommandName() + " " + String.join(" ", command);
	}
}
