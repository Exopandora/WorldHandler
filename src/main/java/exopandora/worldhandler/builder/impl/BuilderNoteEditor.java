package exopandora.worldhandler.builder.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderNoteEditor extends BuilderSetBlock
{
	public BuilderNoteEditor()
	{
		this.setBlock(Blocks.NOTE_BLOCK.getRegistryName());
		this.setMode("replace");
	}
	
	public BuilderNoteEditor(int note)
	{
		this();
		this.setNote(note);
	}
	
	public BuilderNoteEditor(int note, BlockPos pos)
	{
		this(note);
		this.setPosition(pos);
		this.withState(BlockStateProperties.NOTE_BLOCK_INSTRUMENT, NoteBlockInstrument.byState(Minecraft.getInstance().world.getBlockState(pos.down())));
	}
	
	public void setNote(int note)
	{
		this.withState(BlockStateProperties.NOTE_0_24, note);
	}
	
	public BuilderNoteEditor getBuilderForNote(int note)
	{
		return new BuilderNoteEditor(note, this.getBlockPos());
	}
}
