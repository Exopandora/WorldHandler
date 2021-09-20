package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;

public class ItemResourceLocation
{
	protected ResourceLocation resource;
	protected CompoundTag nbt;
	
	public ItemResourceLocation()
	{
		this(null, null);
	}
	
	public ItemResourceLocation(ResourceLocation resource)
	{
		this(resource, null);
	}
	
	public ItemResourceLocation(ResourceLocation resource, CompoundTag nbt)
	{
		this.resource = resource;
		this.nbt = nbt;
	}
	
	public ResourceLocation getResourceLocation()
	{
		return this.resource;
	}
	
	public void setResourceLocation(ResourceLocation resource)
	{
		this.resource = resource;
	}
	
	public CompoundTag getNBT()
	{
		return this.nbt;
	}
	
	public void setNBT(CompoundTag nbt)
	{
		this.nbt = nbt;
	}
	
	@Nullable
	public static ItemResourceLocation valueOf(String input)
	{
		int start = input.indexOf("{");
		ResourceLocation resource = new ResourceLocation(input.substring(0, start));
		CompoundTag nbt = null;
		
		if(start > 0)
		{
			try
			{
				nbt = TagParser.parseTag(input.substring(start, input.lastIndexOf("}") + 1));
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
		}
		
		return new ItemResourceLocation(resource, nbt);
	}
	
	@Override
	public String toString()
	{
		if(this.resource != null)
		{
			StringBuilder builder = new StringBuilder(this.resource.toString());
			
			if(this.nbt != null)
			{
				builder.append(this.nbt.toString());
			}
			
			return builder.toString();
		}
		
		return null;
	}
}
