package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.component.impl.ComponentTag;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderNoteEditor extends BuilderBlockdata
{
	private final ComponentTag<Byte> note;
	
	public BuilderNoteEditor()
	{
		this.note = this.registerNBTComponent(new ComponentTag<Byte>("note"));
	}
	
	public BuilderNoteEditor(byte note)
	{
		this();
		this.setNote(note);
	}
	
	public BuilderNoteEditor(byte note, BlockPos pos)
	{
		this(note);
		this.setPosition(pos);
	}
	
	public void setNote(byte note)
	{
		this.note.setValue(note);
	}
	
	public BuilderNoteEditor getBuilderForNote(byte note)
	{
		return new BuilderNoteEditor(note, this.getBlockPos());
	}
}
