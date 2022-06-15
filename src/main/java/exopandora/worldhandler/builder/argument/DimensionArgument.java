package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionArgument implements IDeserializableArgument
{
	private ResourceKey<Level> dimension;
	
	protected DimensionArgument()
	{
		super();
	}
	
	public void set(@Nullable ResourceKey<Level> dimension)
	{
		this.dimension = dimension;
	}
	
	public void set(@Nullable ResourceLocation dimension)
	{
		if(dimension != null)
		{
			this.set(ResourceKey.create(Registry.DIMENSION_REGISTRY, dimension));
		}
		else
		{
			this.dimension = null;
		}
	}
	
	@Nullable
	public ResourceKey<Level> getDimension()
	{
		return this.dimension;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		this.set(ResourceHelper.stringToResourceLocation(string));
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.dimension == null)
		{
			return null;
		}
		
		return this.dimension.location().toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return false;
	}
	
	public static DimensionArgument dimension()
	{
		return new DimensionArgument();
	}
}
