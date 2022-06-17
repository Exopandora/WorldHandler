package exopandora.worldhandler.util;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockPredicateParser
{
	private static final ResourceLocation AIR_RESOURCE_LOCATION = ForgeRegistries.BLOCKS.getKey(Blocks.AIR);
	private final StringReader reader;
	private final Map<String, String> vagueProperties = Maps.newHashMap();
	private ResourceLocation block = new ResourceLocation("");
	@Nullable
	private CompoundTag nbt;
	private boolean isTag;
	
	public BlockPredicateParser(String string)
	{
		this(new StringReader(string));
	}
	
	public BlockPredicateParser(StringReader reader)
	{
		this.reader = reader;
	}
	
	public void parse(boolean allowTags) throws CommandSyntaxException
	{
		if(this.reader.canRead() && this.reader.peek() == '#')
		{
			if(!allowTags)
			{
				throw BlockStateParser.ERROR_NO_TAGS_ALLOWED.createWithContext(this.reader);
			}
			
			this.readTag();
			
			if(this.reader.canRead() && this.reader.peek() == '[')
			{
				this.readVagueProperties();
			}
		}
		else	
		{
			this.readBlock();
			
			if(this.reader.canRead() && this.reader.peek() == '[')
			{
				this.readVagueProperties();
			}
		}
		
		if(this.reader.canRead() && this.reader.peek() == '{')
		{
			this.readNbt();
		}
	}
	
	private void readBlock() throws CommandSyntaxException
	{
		this.block = ResourceLocation.read(this.reader);
	}
	
	private void readTag() throws CommandSyntaxException
	{
		this.reader.expect('#');
		this.block = ResourceLocation.read(this.reader);
		this.isTag = true;
	}
	
	private void readVagueProperties() throws CommandSyntaxException
	{
		this.reader.skip();
		this.reader.skipWhitespace();
		int cursor = -1;
		
		while(true)
		{
			if(this.reader.canRead() && this.reader.peek() != ']')
			{
				this.reader.skipWhitespace();
				int j = this.reader.getCursor();
				String propertyKey = this.reader.readString();
				
				if(this.vagueProperties.containsKey(propertyKey))
				{
					this.reader.setCursor(j);
					throw BlockStateParser.ERROR_DUPLICATE_PROPERTY.createWithContext(this.reader, this.block.toString(), propertyKey);
				}
				
				this.reader.skipWhitespace();
				
				if(!this.reader.canRead() || this.reader.peek() != '=')
				{
					this.reader.setCursor(j);
					throw BlockStateParser.ERROR_EXPECTED_VALUE.createWithContext(this.reader, this.block.toString(), propertyKey);
				}
				
				this.reader.skip();
				this.reader.skipWhitespace();
				cursor = this.reader.getCursor();
				String propertyValue = this.reader.readString();
				this.vagueProperties.put(propertyKey, propertyValue);
				this.reader.skipWhitespace();
				
				if(!this.reader.canRead())
				{
					continue;
				}
				
				cursor = -1;
				
				if(this.reader.peek() == ',')
				{
					this.reader.skip();
					continue;
				}
				
				if(this.reader.peek() != ']')
				{
					throw BlockStateParser.ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
				}
			}
			
			if(this.reader.canRead())
			{
				this.reader.skip();
				return;
			}
			
			if(cursor >= 0)
			{
				this.reader.setCursor(cursor);
			}
			
			throw BlockStateParser.ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
		}
	}
	
	private void readNbt() throws CommandSyntaxException
	{
		this.nbt = new TagParser(this.reader).readStruct();
	}
	
	public Optional<Block> getBlock()
	{
		Block block = ForgeRegistries.BLOCKS.getValue(this.block);
		
		if(Blocks.AIR.equals(block) && !AIR_RESOURCE_LOCATION.equals(this.block))
		{
			return Optional.empty();
		}
		
		return Optional.ofNullable(block);
	}
	
	public Map<String, String> getVagueProperties()
	{
		return this.vagueProperties;
	}
	
	public ResourceLocation getResourceLocation()
	{
		return this.block;
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
