package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.util.ItemPredicateParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemArgument extends TagArgument
{
	private Item item;
	
	protected ItemArgument()
	{
		super();
	}
	
	public void set(@Nullable Item item)
	{
		this.item = item;
	}
	
	public void set(@Nullable ResourceLocation item)
	{
		if(item != null)
		{
			this.set(ForgeRegistries.ITEMS.getValue(item));
		}
		else
		{
			this.item = null;
		}
	}
	
	public Item getItem()
	{
		return this.item;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			try
			{
				ItemPredicateParser parser = new ItemPredicateParser(string);
				parser.parse(false);
				parser.getItem().ifPresentOrElse(item ->
				{
					this.item = item;
					this.setTag(parser.getNbt());
				}, () ->
				{
					this.item = null;
					this.setTag(null);
				});
			}
			catch(CommandSyntaxException e)
			{
				this.item = null;
				this.setTag(null);
			}
		}
		else
		{
			this.item = null;
			this.setTag(null);
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.item == null)
		{
			return null;
		}
		
		String tag = super.serialize();
		
		if(tag != null)
		{
			return ForgeRegistries.ITEMS.getKey(this.item).toString() + tag;
		}
		
		return ForgeRegistries.ITEMS.getKey(this.item).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.item == null;
	}
}
