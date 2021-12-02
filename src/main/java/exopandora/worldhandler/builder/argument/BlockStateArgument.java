package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
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
	public void deserialize(@Nullable String block)
	{
		if(block == null)
		{
			this.reset();
		}
		else
		{
			try
			{
				BlockStateParser parser = new BlockStateParser(new StringReader(block), false).parse(true);
				this.state = parser.getState();
				this.setTag(parser.getNbt());
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
		builder.replace(0, block.length(), this.state.getBlock().getRegistryName().toString());
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
