package exopandora.worldhandler.builder.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class BuilderNoteEditor extends BuilderSetBlock
{
	public BuilderNoteEditor()
	{
		this.setBlock(Blocks.NOTE_BLOCK.getRegistryName());
		this.setMode(EnumMode.REPLACE);
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
		this.setState(BlockStateProperties.NOTEBLOCK_INSTRUMENT, NoteBlockInstrument.byState(Minecraft.getInstance().level.getBlockState(pos.below())));
	}
	
	public void setNote(int note)
	{
		this.setState(BlockStateProperties.NOTE, note);
	}
	
	public BuilderNoteEditor build(int note)
	{
		return new BuilderNoteEditor(note, this.getBlockPos());
	}
}
