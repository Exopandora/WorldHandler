package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.item.ItemParser;
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
	public void deserialize(@Nullable String item)
	{
		if(item != null)
		{
			try
			{
				ItemParser parser = new ItemParser(new StringReader(item), false).parse();
				this.item = parser.getItem();
				this.setTag(parser.getNbt());
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
			return this.item.getRegistryName().toString() + tag;
		}
		
		return this.item.getRegistryName().toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.item == null;
	}
}
