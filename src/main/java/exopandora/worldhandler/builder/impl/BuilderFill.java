package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.core.BlockPos;

public class BuilderFill extends BuilderDoubleBlockPos
{
	public BuilderFill()
	{
		super();
	}
	
	public BuilderFill(BlockResourceLocation block1, EnumBlockFilter filter, BlockResourceLocation block2)
	{
		this(BlockHelper.getPos1(), BlockHelper.getPos2(), block1, filter, block2);
	}
	
	public BuilderFill(BlockPos pos1, BlockPos pos2, BlockResourceLocation block1, EnumBlockFilter handling, BlockResourceLocation block2)
	{
		this.setPosition1(pos1);
		this.setPosition2(pos2);
		this.setBlock1(block1);
		this.setBlockHandling(handling);
		this.setBlock2(block2);
	}
	
	public BuilderFill(CoordinateInt x1, CoordinateInt y1, CoordinateInt z1, CoordinateInt x2, CoordinateInt y2, CoordinateInt z2, BlockResourceLocation block1)
	{
		this.setX1(x1);
		this.setY1(y1);
		this.setZ1(z1);
		this.setX2(x2);
		this.setY2(y2);
		this.setZ2(z2);
		this.setBlock1(block1);
	}
	
	public void setBlock1(String block)
	{
		this.setBlock1(BlockResourceLocation.valueOf(block));
	}
	
	public void setBlock1(BlockResourceLocation resource)
	{
		this.setNode(6, resource);
	}
	
	@Nullable
	public BlockResourceLocation getBlock1()
	{
		return this.getNodeAsBlockResourceLocation(6);
	}
	
	public void setBlockHandling(EnumBlockFilter filter)
	{
		this.setNode(7, filter != null ? filter.toString() : null);
	}
	
	public void setBlock2(String block)
	{
		this.setBlock2(BlockResourceLocation.valueOf(block));
	}
	
	public void setBlock2(BlockResourceLocation resource)
	{
		this.setNode(8, resource);
	}
	
	@Nullable
	public BlockResourceLocation getBlock2()
	{
		return this.getNodeAsBlockResourceLocation(8);
	}
	
	@Nullable
	public EnumBlockFilter getBlockFilter()
	{
		return EnumHelper.valueOf(this.getNodeAsString(7), EnumBlockFilter.class);
	}
	
	public BuilderFill build()
	{
		return new BuilderFill(this.getBlock1(), null, null);
	}
	
	public BuilderFill buildReplace()
	{
		return new BuilderFill(this.getBlock2(), EnumBlockFilter.REPLACE, this.getBlock1());
	}
	
	@Override
	public String getCommandName()
	{
		return "fill";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("x1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("x2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("block", ArgumentType.BLOCK_RESOURCE_LOCATION);
		syntax.addOptional("filter", ArgumentType.STRING);
		syntax.addOptional("block", ArgumentType.BLOCK_RESOURCE_LOCATION, "block");
		
		return syntax;
	}
	
	public static enum EnumBlockFilter
	{
		REPLACE,
		DESTROY,
		KEEP,
		HOLLOW,
		OUTLINE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
