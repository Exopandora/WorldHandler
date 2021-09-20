package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
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

public class BuilderUsercontent extends CommandBuilder
{
	private final String name;
	private final CommandSyntax syntax;
	
	public BuilderUsercontent(String name, CommandSyntax syntax)
	{
		this.name = name;
		this.syntax = syntax;
		this.updateSyntax(this.syntax);
	}
	
	public void set(int index, String object)
	{
		if(index < this.syntax.getArguments().size() && index >= 0)
		{
			Argument argument = this.syntax.getArguments().get(index);
			ArgumentType type = argument.getType();
			
			switch(type)
			{
				case STRING:
					this.setNode(index, type.<String>parseOfDefault(object, (String) argument.getDefault()));
					break;
				case BLOCK_RESOURCE_LOCATION:
					this.setNode(index, type.<BlockResourceLocation>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case BOOLEAN:
					this.setNode(index, type.<Boolean>parseOfDefault(object, (Boolean) argument.getDefault()));
					break;
				case BYTE:
					this.setNode(index, type.<Byte>parseOfDefault(object, ((Double) argument.getDefault()).byteValue()));
					break;
				case COORDINATE_DOUBLE:
					this.setNode(index, type.<CoordinateDouble>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case COORDINATE_INT:
					this.setNode(index, type.<CoordinateInt>parseOfDefault(object,  type.parse((String) argument.getDefault())));
					break;
				case DOUBLE:
					this.setNode(index, type.<Double>parseOfDefault(object, (Double) argument.getDefault()));
					break;
				case FLOAT:
					this.setNode(index, type.<Float>parseOfDefault(object, ((Double) argument.getDefault()).floatValue()));
					break;
				case GREEDY_STRING:
					this.setNode(index, type.<GreedyString>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case INT:
					this.setNode(index, type.<Integer>parseOfDefault(object, ((Double) argument.getDefault()).intValue()));
					break;
				case ITEM_RESOURCE_LOCATION:
					this.setNode(index, type.<ItemResourceLocation>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case LONG:
					this.setNode(index, type.<Long>parseOfDefault(object, ((Double) argument.getDefault()).longValue()));
					break;
				case NBT:
					this.setNode(index, type.<CompoundTag>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case RESOURCE_LOCATION:
					this.setNode(index, type.<ResourceLocation>parseOfDefault(object, type.parse((String) argument.getDefault())));
					break;
				case SHORT:
					this.setNode(index, type.<Short>parseOfDefault(object, ((Double) argument.getDefault()).shortValue()));
					break;
				case TARGET_SELECTOR:
					this.setNode(index, type.<TargetSelector>parseOfDefault(object, type.<TargetSelector>parse((String) argument.getDefault())));
					break;
				default:
					break;
			}
		}
	}
	
	@Nullable
	public String get(int index)
	{
		if(index < this.syntax.getArguments().size() && index >= 0)
		{
			Argument argument = this.syntax.getArguments().get(index);
			
			switch(argument.getType())
			{
				case BLOCK_RESOURCE_LOCATION:
					return this.getNodeAsBlockResourceLocation(index).toString();
				case BOOLEAN:
					return String.valueOf(this.getNodeAsBoolean(index));
				case BYTE:
					return String.valueOf(this.getNodeAsByte(index));
				case COORDINATE_DOUBLE:
					return this.getNodeAsCoordinateDouble(index).toString();
				case COORDINATE_INT:
					return this.getNodeAsCoordinateInt(index).toString();
				case DOUBLE:
					return String.valueOf(this.getNodeAsDouble(index));
				case FLOAT:
					return String.valueOf(this.getNodeAsFloat(index));
				case GREEDY_STRING:
					return this.getNodeAsGreedyString(index);
				case INT:
					return String.valueOf(this.getNodeAsInt(index));
				case ITEM_RESOURCE_LOCATION:
					return this.getNodeAsItemResourceLocation(index).toString();
				case LONG:
					return String.valueOf(this.getNodeAsLong(index));
				case NBT:
					return this.getNodeAsNBT(index).toString();
				case RESOURCE_LOCATION:
					return this.getNodeAsResourceLocation(index).toString();
				case SHORT:
					return String.valueOf(this.getNodeAsShort(index));
				case STRING:
					return this.getNodeAsString(index);
				case TARGET_SELECTOR:
					return this.getNodeAsTargetSelector(index).toString();
				default:
					break;
			}
		}
		
		return null;
	}
	
	public void setPlayerName(String username)
	{
		for(int x = 0; x < this.syntax.getArguments().size(); x++)
		{
			if(ArgumentType.PLAYER.equals(this.syntax.getArguments().get(x).getType()))
			{
				this.setPlayerName(x, username);
			}
		}
	}
	
	@Override
	public String getCommandName()
	{
		return this.name;
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		return this.syntax;
	}
}
