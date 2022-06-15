package exopandora.worldhandler.builder.argument;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.builder.argument.tag.ITagProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;

public class TagArgument implements IDeserializableArgument
{
	private List<ITagProvider> providers;
	private CompoundTag tag;
	
	protected TagArgument()
	{
		super();
	}
	
	public void addTagProvider(ITagProvider provider)
	{
		if(this.providers == null)
		{
			this.providers = new ArrayList<ITagProvider>();
		}
		
		this.providers.add(provider);
	}
	
	public void setTag(@Nullable CompoundTag tag)
	{
		this.tag = tag;
	}
	
	@Nullable
	public CompoundTag getTag()
	{
		return this.tag;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			try
	        {
				this.tag = TagParser.parseTag(string);
	        }
	        catch(CommandSyntaxException e)
	        {
	    		this.tag = null;
	        }
		}
		else
		{
    		this.tag = null;
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.tag == null && (this.providers == null || this.providers.isEmpty()))
		{
			return null;
		}
		
		CompoundTag compound = this.tag == null ? new CompoundTag() : this.tag.copy();
		
		if(this.providers != null)
		{
			for(ITagProvider provider : this.providers)
			{
				Tag tag = provider.value();
				
				if(tag != null)
				{
					String key = provider.key();
					
					if(key != null)
					{
						compound.put(key, tag);
					}
					else if(tag instanceof CompoundTag)
					{
						compound.merge((CompoundTag) tag);
					}
				}
			}
		}
		
		if(compound.isEmpty())
		{
			return null;
		}
		
		return compound.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.tag == null && (this.providers == null || this.providers.isEmpty() || this.providers.stream().allMatch(provider -> provider.value() == null));
	}
}
