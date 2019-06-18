package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemResourceLocation
{
	protected ResourceLocation resource;
	protected CompoundNBT nbt;
	
	public ItemResourceLocation()
	{
		this(null, null);
	}
	
	public ItemResourceLocation(ResourceLocation resource)
	{
		this(resource, null);
	}
	
	public ItemResourceLocation(ResourceLocation resource, CompoundNBT nbt)
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
	
	public CompoundNBT getNBT()
	{
		return this.nbt;
	}
	
	public void setNBT(CompoundNBT nbt)
	{
		this.nbt = nbt;
	}
	
	public ItemResourceLocation get()
	{
		if(this.resource != null)
		{
			return this;
		}
		
		return null;
	}
	
	@Nullable
	public static ItemResourceLocation valueOf(String input)
	{
		int start = input.indexOf("{");
		ResourceLocation resource = new ResourceLocation(input.substring(0, start));
		CompoundNBT nbt = null;
		
		if(start > 0)
		{
			try
			{
				nbt = JsonToNBT.getTagFromJson(input.substring(start, input.lastIndexOf("}") + 1));
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
