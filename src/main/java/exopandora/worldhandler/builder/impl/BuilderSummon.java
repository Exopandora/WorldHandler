package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class BuilderSummon extends BuilderEntity
{
	public BuilderSummon()
	{
		this.setX(new CoordinateDouble(0.0, EnumType.LOCAL));
		this.setY(new CoordinateDouble(0.0, EnumType.LOCAL));
		this.setZ(new CoordinateDouble(2.0, EnumType.LOCAL));
	}
	
	public void setEntity(ResourceLocation entity)
	{
		this.setNode(0, entity);
	}
	
	public ResourceLocation getEntity()
	{
		return this.getNodeAsResourceLocation(0);
	}
	
	public void setX(CoordinateDouble x)
	{
		this.setNode(1, x);
	}
	
	public CoordinateDouble getX()
	{
		return this.getNodeAsCoordinateDouble(1);
	}
	
	public void setY(CoordinateDouble y)
	{
		this.setNode(2, y);
	}
	
	public CoordinateDouble getY()
	{
		return this.getNodeAsCoordinateDouble(2);
	}
	
	public void setZ(CoordinateDouble z)
	{
		this.setNode(3, z);
	}
	
	public CoordinateDouble getZ()
	{
		return this.getNodeAsCoordinateDouble(3);
	}
	
	@Override
	public void setNBT(CompoundTag nbt)
	{
		this.setNode(4, nbt);
	}
	
	@Override
	public String getCommandName()
	{
		return "summon";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("entity_name", ArgumentType.RESOURCE_LOCATION);
		syntax.addOptional("x", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("y", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("z", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("nbt", ArgumentType.NBT);
		
		return syntax;
	}
}
