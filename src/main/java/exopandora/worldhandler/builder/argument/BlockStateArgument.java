package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.util.BlockPredicateParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockStateArgument extends TagArgument
{
	private BlockState state;
	
	protected BlockStateArgument()
	{
		super();
	}
	
	public void set(@Nullable ResourceLocation block)
	{
		if(block != null)
		{
			this.set(ForgeRegistries.BLOCKS.getValue(block));
		}
		else
		{
			this.state = null;
		}
	}
	
	public void set(@Nullable Block block)
	{
		if(block != null)
		{
			this.state = block.defaultBlockState();
		}
		else
		{
			this.state = null;
		}
	}
	
	public void set(@Nullable BlockState state)
	{
		this.state = state;
	}
	
	public void set(@Nullable BlockState state, CompoundTag tag)
	{
		this.set(state);
		this.setTag(tag);
	}
	
	@Nullable
	public BlockState getBlockState()
	{
		return this.state;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string == null)
		{
			this.reset();
		}
		else
		{
			try
			{
				BlockPredicateParser parser = new BlockPredicateParser(string);
				parser.parse(false);
				parser.getBlock().ifPresentOrElse(block ->
				{
					this.state = block.defaultBlockState();
					this.setTag(parser.getNbt());
				}, this::reset);
			}
			catch(CommandSyntaxException e)
			{
				this.reset();
			}
		}
	}
	
	private void reset()
	{
		this.state = null;
		this.setTag(null);
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.state == null)
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder(this.state.toString());
		String block = this.state.getBlock().toString();
		builder.replace(0, block.length(), ForgeRegistries.BLOCKS.getKey(this.state.getBlock()).toString());
		String nbt = super.serialize();
		
		if(nbt != null && this.state.hasBlockEntity())
		{
			builder.append(nbt);
		}
		
		return builder.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.state == null;
	}
}
