package exopandora.worldhandler.gui.content.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.builder.impl.SetBlockCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonPiano;
import exopandora.worldhandler.gui.widget.button.GuiButtonPiano.Type;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class ContentNoteEditor extends Content
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	
	private final SetBlockCommandBuilder builderNoteEditor = new SetBlockCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderNoteEditor, SetBlockCommandBuilder.Label.REPLACE);
	
	private boolean isActive;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.isActive ? this.preview : null;
	}
	
	@Override
	public void init(Container container)
	{
		this.isActive = BlockHelper.getFocusedBlock() instanceof NoteBlock;
		this.builderNoteEditor.pos().set(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.isActive)
		{
			BlockPos pos = this.builderNoteEditor.pos().getBlockPos();
			SoundEvent sound = this.getSoundEvent(pos.below());
			
			container.add(new GuiButtonPiano(x - 3 + 15, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.g"), sound, 0.53F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 1)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 2, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.a"), sound, 0.6F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 3)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 3, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.b"), sound, 0.67F, Type.RIGHT, () -> this.setNote(container.getPlayer(), 5)));
			
			container.add(new GuiButtonPiano(x - 3 + 15 * 4, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.c"), sound, 0.7F, Type.LEFT, () -> this.setNote(container.getPlayer(), 6)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 5, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.d"), sound, 0.8F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 8)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 6, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.e"), sound, 0.9F, Type.RIGHT, () -> this.setNote(container.getPlayer(), 10)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 7, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.f"), sound, 0.95F, Type.LEFT, () -> this.setNote(container.getPlayer(), 11)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 8, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.g"), sound, 1.05F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 13)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 9, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.a"), sound, 1.2F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 15)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 10, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.b"), sound, 1.32F, Type.RIGHT, () -> this.setNote(container.getPlayer(), 17)));
			
			container.add(new GuiButtonPiano(x - 3 + 15 * 11, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.c"), sound, 1.4F, Type.LEFT, () -> this.setNote(container.getPlayer(), 18)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 12, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.d"), sound, 1.6F, Type.NORMAL, () -> this.setNote(container.getPlayer(), 20)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 13, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.e"), sound, 1.8F, Type.RIGHT, () -> this.setNote(container.getPlayer(), 22)));
			container.add(new GuiButtonPiano(x - 3 + 15 * 14, y, 14, 92, Component.translatable("gui.worldhandler.blocks.note_block_editor.f"), sound, 1.9F, Type.LEFT, () -> this.setNote(container.getPlayer(), 23)));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15, y, 9, 58, Component.literal("F#"), sound, 0.5F, Type.BLACK, () -> this.setNote(container.getPlayer(), 0)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 2, y, 9, 58, Component.literal("G#"), sound, 0.56F, Type.BLACK, () -> this.setNote(container.getPlayer(), 2)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 3, y, 9, 58, Component.literal("A#"), sound, 0.63F, Type.BLACK, () -> this.setNote(container.getPlayer(), 4)));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 5, y, 9, 58, Component.literal("C#"), sound, 0.75F, Type.BLACK, () -> this.setNote(container.getPlayer(), 7)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 6, y, 9, 58, Component.literal("D#"), sound, 0.85F, Type.BLACK, () -> this.setNote(container.getPlayer(), 9)));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 8, y, 9, 58, Component.literal("F#"), sound, 1.0F, Type.BLACK, () -> this.setNote(container.getPlayer(), 12)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 9, y, 9, 58, Component.literal("G#"), sound, 1.1F, Type.BLACK, () -> this.setNote(container.getPlayer(), 14)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 10, y, 9, 58, Component.literal("A#"), sound, 1.25F, Type.BLACK, () -> this.setNote(container.getPlayer(), 16)));
			
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 12, y, 9, 58, Component.literal("C#"), sound, 1.5F, Type.BLACK, () -> this.setNote(container.getPlayer(), 19)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 13, y, 9, 58, Component.literal("D#"), sound, 1.7F, Type.BLACK, () -> this.setNote(container.getPlayer(), 21)));
			container.add(new GuiButtonPiano(x - 3 - 5 + 15 * 15, y, 9, 58, Component.literal("F#"), sound, 2.0F, Type.BLACK, () -> this.setNote(container.getPlayer(), 24)));
		}
	}
	
	private void setNote(String player, int note)
	{
		SetBlockCommandBuilder builder = new SetBlockCommandBuilder();
		builder.pos().set(this.builderNoteEditor.pos().getBlockPos());
		NoteBlockInstrument instrument = NoteBlockInstrument.byState(Minecraft.getInstance().level.getBlockState(this.builderNoteEditor.pos().getBlockPos().below()));
		BlockState state = Blocks.NOTE_BLOCK.defaultBlockState()
				.setValue(BlockStateProperties.NOTEBLOCK_INSTRUMENT, instrument)
				.setValue(BlockStateProperties.NOTE, note);
		builder.block().set(state);
		CommandHelper.sendCommand(player, builder, SetBlockCommandBuilder.Label.REPLACE);
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.isActive)
		{
			RenderSystem.setShaderTexture(0, NOTE);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			
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
    		
			PoseStack posestack = RenderSystem.getModelViewStack();
			posestack.pushPose();
			posestack.translate(container.width / 2 - 8.5F * scale, container.height / 2 - 15 - 8.5F * scale, 0);
			posestack.scale(scale, scale, scale);
			
			Minecraft.getInstance().getItemRenderer().renderGuiItem(new ItemStack(Items.NOTE_BLOCK), 0, 0);
			
			posestack.popPose();
			RenderSystem.applyModelViewMatrix();
			
			MutableComponent text = Component.translatable("gui.worldhandler.blocks.note_block_editor.look_at_note_block", KeyHandler.KEY_WORLD_HANDLER.getTranslatedKeyMessage());
			Font font = Minecraft.getInstance().font;
			font.draw(matrix, text, x + 116 - font.width(text) / 2, y + 70, Config.getSkin().getLabelColor());
		}
	}
	
	private SoundEvent getSoundEvent(BlockPos blockPos)
	{
		Level level = Minecraft.getInstance().level;
		
		if(level != null)
		{
			return NoteBlockInstrument.byState(level.getBlockState(blockPos)).getSoundEvent();
		}
		
		return null;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.blocks.note_block_editor");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.blocks.note_block_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.NOTE_EDITOR;
	}
}
