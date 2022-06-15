package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.util.ItemPredicateParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

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
			this.resource = ForgeRegistries.ITEMS.getKey(item);
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
				ItemPredicateParser parser = new ItemPredicateParser(predicate);
				parser.parse(true);
				this.resource = parser.getResourceLocation();
				this.setTag(parser.getNbt());
				this.isTag = parser.isTag();
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
