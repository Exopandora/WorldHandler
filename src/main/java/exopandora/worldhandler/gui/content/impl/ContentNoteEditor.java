package exopandora.worldhandler.gui.content.impl;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderNoteEditor;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonPiano;
import exopandora.worldhandler.gui.button.GuiButtonPiano.Type;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.RenderUtils;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentNoteEditor extends Content
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	private final BuilderNoteEditor builderNoteEditor = new BuilderNoteEditor();
	
	private boolean isActive;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.isActive ? this.builderNoteEditor : null;
	}
	
	@Override
	public void init(Container container)
	{
		this.isActive = BlockHelper.getFocusedBlock() instanceof NoteBlock;
		this.builderNoteEditor.setPosition(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.isActive)
		{
			BlockPos pos = this.builderNoteEditor.getBlockPos();
			SoundEvent sound = this.getSoundEvent(pos.down());
			
			container.add(new GuiButtonPiano(x - 3 + 15, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.g"), sound, 0.53F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(1));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 2, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.a"), sound, 0.6F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(3));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 3, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.b"), sound, 0.67F, Type.RIGHT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(5));
			}));
			
			container.add(new GuiButtonPiano(x - 3 + 15 * 4, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.c"), sound, 0.7F, Type.LEFT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(6));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 5, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.d"), sound, 0.8F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(8));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 6, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.e"), sound, 0.9F, Type.RIGHT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(10));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 7, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.f"), sound, 0.95F, Type.LEFT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(11));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 8, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.g"), sound, 1.05F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(13));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 9, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.a"), sound, 1.2F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(15));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 10, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.b"), sound, 1.32F, Type.RIGHT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(17));
			}));
			
			container.add(new GuiButtonPiano(x - 3 + 15 * 11, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.c"), sound, 1.4F, Type.LEFT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(18));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 12, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.d"), sound, 1.6F, Type.NORMAL, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(20));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 13, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.e"), sound, 1.8F, Type.RIGHT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(22));
			}));
			container.add(new GuiButtonPiano(x - 3 + 15 * 14, y, 14, 92, new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.f"), sound, 1.9F, Type.LEFT, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(23));
			}));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15, y, 9, 58, new StringTextComponent("F#"), sound, 0.5F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(0));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 2, y, 9, 58, new StringTextComponent("G#"), sound, 0.56F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(2));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 3, y, 9, 58, new StringTextComponent("A#"), sound, 0.63F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(4));
			}));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 5, y, 9, 58, new StringTextComponent("C#"), sound, 0.75F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(7));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 6, y, 9, 58, new StringTextComponent("D#"), sound, 0.85F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(9));
			}));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 8, y, 9, 58, new StringTextComponent("F#"), sound, 1.0F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(12));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 9, y, 9, 58, new StringTextComponent("G#"), sound, 1.1F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(14));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 10, y, 9, 58, new StringTextComponent("A#"), sound, 1.25F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(16));
			}));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 12, y, 9, 58, new StringTextComponent("C#"), sound, 1.5F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(19));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 13, y, 9, 58, new StringTextComponent("D#"), sound, 1.7F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(21));
			}));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 15, y, 9, 58, new StringTextComponent("F#"), sound, 2.0F, Type.BLACK, () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderNoteEditor.getBuilderForNote(24));
			}));
		}
	}
	
	@Override
	public void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.isActive)
		{
			RenderUtils.color(1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().getTextureManager().bindTexture(NOTE);
			
			container.blit(matrix, x - 1, y - 1, 0, 0, 8, 59);
			container.blit(matrix, x - 1, y - 1 + 59, 0, 59, 13, 35);
			
			container.blit(matrix, x - 1 + 232 - 5, y - 1, 18, 0, 7, 59);
			container.blit(matrix, x - 1 + 232 - 10, y - 1 + 59, 13, 59, 12, 35);
			
			container.blit(matrix, x - 1 + 8, y - 1, 0, 94, 219, 1);
			container.blit(matrix, x - 1 + 13, y - 1 + 93, 0, 94, 209, 1);
		}
		else
		{
    		float scale = 4;
    		
            matrix.push();
            matrix.translate(container.width / 2 - 8 * scale, container.height / 2 - 15 - 8 * scale, 0);
            matrix.scale(scale, scale, scale);
            
    		RenderUtils.renderItemIntoGUI(matrix, new ItemStack(Blocks.NOTE_BLOCK), 0, 0);
    		matrix.pop();
			
    		TranslationTextComponent text = new TranslationTextComponent("gui.worldhandler.blocks.note_block_editor.look_at_note_block", KeyHandler.KEY_WORLD_HANDLER.func_238171_j_());
			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			fontRenderer.func_243248_b(matrix, text, x + 116 - fontRenderer.func_238414_a_(text) / 2, y + 70, Config.getSkin().getLabelColor());
		}
	}
	
	private SoundEvent getSoundEvent(BlockPos blockPos)
	{
    	return NoteBlockInstrument.byState(Minecraft.getInstance().world.getBlockState(blockPos)).getSound();
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.blocks.note_block_editor");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.blocks.note_block_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.NOTE_EDITOR;
	}
}
