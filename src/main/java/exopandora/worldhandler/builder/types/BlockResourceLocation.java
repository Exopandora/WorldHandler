package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockResourceLocation extends ItemResourceLocation
{
	private BlockState state;
	
	public BlockResourceLocation()
	{
		this(null);
	}
	
	public BlockResourceLocation(ResourceLocation resource)
	{
		this(resource, null, null);
	}
	
	public BlockResourceLocation(ResourceLocation resource, BlockState state, CompoundTag nbt)
	{
		super(resource, nbt);
		this.state = this.findState(state, resource);
	}
	
	private BlockState findState(BlockState state, ResourceLocation resource)
	{
		boolean matchOld = this.state != null && this.state.getBlock().getRegistryName().equals(resource);
		boolean matchNew = state != null && state.getBlock().getRegistryName().equals(resource);
		
		if(matchNew)
		{
			return state;
		}
		
		if(matchOld)
		{
			return this.state;
		}
		
		if(resource != null && ForgeRegistries.BLOCKS.containsKey(resource))
		{
			return ForgeRegistries.BLOCKS.getValue(resource).defaultBlockState();
		}

		return null;
	}
	
	@Override
	public void setResourceLocation(ResourceLocation resource)
	{
		super.setResourceLocation(resource);
		this.state = this.findState(null, resource);
	}
	
	@Nullable
	public BlockState getState()
	{
		return this.state;
	}
	
	public <T extends Comparable<T>> void setProperty(Property<T> property, T value)
	{
		if(this.state != null && this.state.hasProperty(property))
		{
			this.state = this.state.setValue(property, value);
		}
	}
	
	@Nullable
	public static BlockResourceLocation valueOf(String input)
	{
		if(input != null)
		{
			BlockStateParser parser = new BlockStateParser(new StringReader(input), false);
			
			try
			{
				parser.parse(true);
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
			
			BlockState state = parser.getState();
			
			if(state != null)
			{
				return new BlockResourceLocation(state.getBlock().getRegistryName(), state, parser.getNbt());
			}
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		if(this.resource != null && this.state != null)
		{
			StringBuilder builder = new StringBuilder(this.state.toString());
			String block = this.state.getBlock().toString();
			builder.replace(0, block.length(), this.resource.toString());
			
			if(this.nbt != null)
			{
				builder.append(this.nbt.toString());
			}
			
			return builder.toString();
		}
		
		return null;
	}
}
