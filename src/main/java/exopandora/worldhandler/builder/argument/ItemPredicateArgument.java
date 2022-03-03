package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemPredicateArgument extends TagArgument
{
	private ResourceLocation resource;
	private boolean isTag;
	
	protected ItemPredicateArgument()
	{
		super();
	}
	
	public void set(@Nullable ResourceLocation resource)
	{
		this.resource = resource;
	}
	
	public void set(@Nullable Item item)
	{
		if(item != null)
		{
			this.resource = item.getRegistryName();
		}
		else
		{
			this.resource = null;
		}
		
		this.isTag = false;
	}
	
	@Nullable
	public ResourceLocation getResourceLocation()
	{
		return this.resource;
	}
	
	public boolean isTag()
	{
		return this.isTag;
	}
	
	@Override
	public void deserialize(@Nullable String predicate)
	{
		if(predicate == null)
		{
			this.reset();
		}
		else
		{
			try
			{
				ItemParser parser = new ItemParser(new StringReader(predicate), true).parse();
				
				if(parser.getItem() != null)
				{
					this.resource = parser.getItem().getRegistryName();
					this.setTag(parser.getNbt());
					this.isTag = false;
				}
				else
				{
					this.resource = parser.getTag().location();
					this.setTag(parser.getNbt());
					this.isTag = true;
				}
			}
			catch(CommandSyntaxException e)
			{
				this.reset();
			}
		}
	}
    
	private void reset()
	{
		this.resource = null;
		this.setTag(null);
		this.isTag = false;
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.resource == null || this.resource.getPath().isEmpty())
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		if(this.isTag)
		{
			builder.append('#');
		}
		
		builder.append(this.resource.toString());
		
		String nbt = super.serialize();
		
		if(nbt != null)
		{
			builder.append(nbt);
		}
		
		return builder.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.resource == null;
	}
}
