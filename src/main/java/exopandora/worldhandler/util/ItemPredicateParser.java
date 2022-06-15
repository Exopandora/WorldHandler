package exopandora.worldhandler.util;

import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemPredicateParser
{
	private final StringReader reader;
	private ResourceLocation item = new ResourceLocation("");
	@Nullable
	private CompoundTag nbt;
	private boolean isTag;
	
	public ItemPredicateParser(String string)
	{
		this(new StringReader(string));
	}
	
	public ItemPredicateParser(StringReader reader)
	{
		this.reader = reader;
	}
	
	public void parse(boolean allowTags) throws CommandSyntaxException
	{
		if(this.reader.canRead() && this.reader.peek() == '#')
		{
			if(!allowTags)
			{
				throw ItemParser.ERROR_NO_TAGS_ALLOWED.createWithContext(this.reader);
			}
			
			this.readTag();
		}
		else	
		{
			this.readItem();
		}
		
		if(this.reader.canRead() && this.reader.peek() == '{')
		{
			this.readNbt();
		}
	}
	
	private void readItem() throws CommandSyntaxException
	{
		this.item = ResourceLocation.read(this.reader);
	}
	
	private void readTag() throws CommandSyntaxException
	{
		this.reader.expect('#');
		this.item = ResourceLocation.read(this.reader);
		this.isTag = true;
	}
	
	private void readNbt() throws CommandSyntaxException
	{
		this.nbt = new TagParser(this.reader).readStruct();
	}
	
	public Optional<Item> getItem()
	{
		Item item = ForgeRegistries.ITEMS.getValue(this.item);
		
		if(Items.AIR.equals(item))
		{
			return Optional.empty();
		}
		
		return Optional.ofNullable(item);
	}
	
	public ResourceLocation getResourceLocation()
	{
		return this.item;
	}
	
	public CompoundTag getNbt()
	{
		return this.nbt;
	}
	
	public boolean isTag()
	{
		return this.isTag;
	}
}
