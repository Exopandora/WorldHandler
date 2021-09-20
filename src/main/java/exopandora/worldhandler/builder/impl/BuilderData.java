package exopandora.worldhandler.builder.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.TargetSelector;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class BuilderData extends BuilderBlockPos
{
	private final TargetSelector targetSelector = new TargetSelector();
	
	public BuilderData()
	{
		super(2);
	}
	
	public BuilderData(EnumMode mode, ResourceLocation entity, CompoundTag nbt)
	{
		this();
		this.setMode(mode);
		this.setEntity(entity);
		this.setNBT(nbt);
	}
	
	public BuilderData(EnumMode mode, BlockPos pos, CompoundTag nbt)
	{
		this();
		this.setMode(mode);
		this.setPosition(pos);
		this.setNBT(nbt);
	}
	
	public void setMode(EnumMode mode)
	{
		EnumTarget target = this.getTarget();
		
		this.updateSyntax(this.getSyntax(mode, target));
		this.setMode0(mode);
		this.setTarget0(target);
	}
	
	private void setMode0(EnumMode mode)
	{
		if(mode != null)
		{
			this.setNode(0, mode.toString());
		}
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(0), EnumMode.class);
	}
	
	public void setTarget(EnumTarget target)
	{
		EnumMode mode = this.getMode();
		
		this.updateSyntax(this.getSyntax(mode, target));
		this.setMode0(mode);
		this.setTarget0(target);
	}
	
	private void setTarget0(EnumTarget target)
	{
		if(target != null)
		{
			this.setNode(1, target.toString());
		}
	}
	
	@Nullable
	public EnumTarget getTarget()
	{
		return EnumHelper.valueOf(this.getNodeAsString(1), EnumTarget.class);
	}
	
	private void setEntity(String entity)
	{
		this.ensureTarget(EnumTarget.ENTITY);
		
		if(entity != null)
		{
			this.targetSelector.set("type", entity);
		}
		
		this.setNode(2, this.targetSelector);
	}
	
	public void setEntity(ResourceLocation entity)
	{
		this.setEntity(entity.toString());
	}
	
	@Nonnull
	public ResourceLocation getEntity()
	{
		return this.targetSelector.<ResourceLocation>get("type");
	}
	
	public void setPath(String path)
	{
		if(this.getMode() == null || !this.getMode().equals(EnumMode.REMOVE))
		{
			this.setMode(EnumMode.REMOVE);
		}
		
		switch(this.getTarget())
		{
			case BLOCK:
				this.setNode(5, path);
				break;
			case ENTITY:
				this.setNode(3, path);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void setNBT(CompoundTag nbt)
	{
		if(this.getMode() == null || !this.getMode().equals(EnumMode.MERGE))
		{
			this.setMode(EnumMode.MERGE);
		}
		
		EnumTarget target = this.getTarget();
		
		if(target != null)
		{
			switch(target)
			{
				case BLOCK:
					this.setNode(5, nbt);
					break;
				case ENTITY:
					this.setNode(3, nbt);
					break;
				default:
					break;
			}
		}
	}
	
	@Nullable
	public CompoundTag getNBT()
	{
		if(this.getMode() != null && this.getMode().equals(EnumMode.MERGE))
		{
			switch(this.getTarget())
			{
				case BLOCK:
					return this.getNodeAsNBT(5);
				case ENTITY:
					return this.getNodeAsNBT(3);
				default:
					break;
			}
		}
		
		return null;
	}
	
	@Override
	public String getCommandName()
	{
		return "data";
	}
	
	@Nullable
	private final CommandSyntax getSyntax(EnumMode mode, EnumTarget target)
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired(mode != null ? mode.toString() : "mode", ArgumentType.STRING);
		syntax.addRequired(target != null ? target.toString() : "target", ArgumentType.STRING);
		
		if(target != null)
		{
			switch(target)
			{
				case BLOCK:
					syntax.addRequired("x", ArgumentType.COORDINATE_INT);
					syntax.addRequired("y", ArgumentType.COORDINATE_INT);
					syntax.addRequired("z", ArgumentType.COORDINATE_INT);
					break;
				case ENTITY:
					syntax.addRequired("entity", ArgumentType.TARGET_SELECTOR);
					break;
				default:
					break;
			}
			
			switch(mode)
			{
				case GET:
					break;
				case MERGE:
					syntax.addRequired("nbt", ArgumentType.NBT);
					break;
				case REMOVE:
					syntax.addRequired("path", ArgumentType.STRING);
					break;
				default:
					break;
			}
		}
		else
		{
			syntax.addOptional("...", ArgumentType.STRING);
		}
		
		return syntax;
	}
	
	private void ensureTarget(EnumTarget target)
	{
		if(this.getTarget() == null || !target.equals(this.getTarget()))
		{
			this.setTarget(target);
		}
	}
	
	@Override
	public void setX(CoordinateInt x)
	{
		this.ensureTarget(EnumTarget.BLOCK);
		super.setX(x);
	}
	
	@Override
	public void setY(CoordinateInt y)
	{
		this.ensureTarget(EnumTarget.BLOCK);
		super.setY(y);
	}
	
	@Override
	public void setZ(CoordinateInt z)
	{
		this.ensureTarget(EnumTarget.BLOCK);
		super.setZ(z);
	}
	
	@Override
	@Nonnull
	public CoordinateInt getXCoordinate()
	{
		this.ensureTarget(EnumTarget.BLOCK);
		return super.getXCoordinate();
	}
	
	@Override
	@Nonnull
	public CoordinateInt getYCoordinate()
	{
		this.ensureTarget(EnumTarget.BLOCK);
		return super.getYCoordinate();
	}
	
	@Override
	@Nonnull
	public CoordinateInt getZCoordinate()
	{
		this.ensureTarget(EnumTarget.BLOCK);
		return super.getZCoordinate();
	}
	
	@Override
	@Nonnull
	public BlockPos getBlockPos()
	{
		this.ensureTarget(EnumTarget.BLOCK);
		return super.getBlockPos();
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("get|merge|remove", ArgumentType.STRING);
		syntax.addRequired("block|entity", ArgumentType.STRING);
		syntax.addOptional("...", ArgumentType.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		GET,
		MERGE,
		REMOVE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	public static enum EnumTarget
	{
		BLOCK,
		ENTITY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
