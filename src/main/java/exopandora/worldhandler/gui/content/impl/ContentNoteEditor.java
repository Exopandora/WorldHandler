package exopandora.worldhandler.gui.content.impl;

import org.lwjgl.input.Keyboard;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderNoteEditor;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.gui.button.GuiButtonKeyboard;
import exopandora.worldhandler.gui.button.GuiButtonKeyboard.Orientation;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentNoteEditor extends Content
{
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
		this.isActive = BlockHelper.isFocusedBlockEqualTo(Blocks.NOTEBLOCK);
		this.builderNoteEditor.setPosition(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		if(this.isActive)
		{
			BlockPos pos = this.builderNoteEditor.getBlockPos();
			SoundEvent sound = this.getSoundEvent(pos.down());
			
			container.add(new GuiButtonKeyboard(4, x - 3 + 15, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.g"), Orientation.NORMAL, 0.53F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(5, x - 3 + 15 * 2, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.a"), Orientation.NORMAL, 0.6F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(6, x - 3 + 15 * 3, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.b"), Orientation.RIGHT, 0.67F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(7, x - 3 + 15 * 4, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.c"), Orientation.LEFT, 0.7F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(8, x - 3 + 15 * 5, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.d"), Orientation.NORMAL, 0.8F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(9, x - 3 + 15 * 6, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.e"), Orientation.RIGHT, 0.9F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(10, x - 3 + 15 * 7, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.f"), Orientation.LEFT, 0.95F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(11, x - 3 + 15 * 8, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.g"), Orientation.NORMAL, 1.05F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(12, x - 3 + 15 * 9, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.a"), Orientation.NORMAL, 1.2F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(13, x - 3 + 15 * 10, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.b"), Orientation.RIGHT, 1.32F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(14, x - 3 + 15 * 11, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.c"), Orientation.LEFT, 1.4F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(15, x - 3 + 15 * 12, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.d"), Orientation.NORMAL, 1.6F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(16, x - 3 + 15 * 13, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.e"), Orientation.RIGHT, 1.8F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(17, x - 3 + 15 * 14, y, 14, 92, I18n.format("gui.worldhandler.blocks.note_block_editor.f"), Orientation.LEFT, 1.9F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(18, x - 3 - 5 + 15, y, 9, 58, "F#", Orientation.BLACK, 0.5F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(19, x - 3 - 5 + 15 * 2, y, 9, 58, "G#", Orientation.BLACK, 0.56F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(20, x - 3 - 5 + 15 * 3, y, 9, 58, "A#", Orientation.BLACK, 0.63F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(21, x - 3 - 5 + 15 * 5, y, 9, 58, "C#", Orientation.BLACK, 0.75F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(22, x - 3 - 5 + 15 * 6, y, 9, 58, "D#", Orientation.BLACK, 0.85F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(23, x - 3 - 5 + 15 * 8, y, 9, 58, "F#", Orientation.BLACK, 1.0F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(24, x - 3 - 5 + 15 * 9, y, 9, 58, "G#", Orientation.BLACK, 1.1F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(25, x - 3 - 5 + 15 * 10, y, 9, 58, "A#", Orientation.BLACK, 1.25F, container, this, pos, sound));
			
			container.add(new GuiButtonKeyboard(26, x - 3 - 5 + 15 * 12, y, 9, 58, "C#", Orientation.BLACK, 1.5F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(27, x - 3 - 5 + 15 * 13, y, 9, 58, "D#", Orientation.BLACK, 1.7F, container, this, pos, sound));
			container.add(new GuiButtonKeyboard(28, x - 3 - 5 + 15 * 15, y, 9, 58, "F#", Orientation.BLACK, 2.0F, container, this, pos, sound));
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 4:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 1));
				break;
			case 5:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 3));
				break;
			case 6:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 5));
				break;
			case 7:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 6));
				break;
			case 8:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 8));
				break;
			case 9:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 10));
				break;
			case 10:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 11));
				break;
			case 11:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 13));
				break;
			case 12:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 15));
				break;
			case 13:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 17));
				break;
			case 14:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 18));
				break;
			case 15:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 20));
				break;
			case 16:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 22));
				break;
			case 17:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 23));
				break;
			case 18:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 0));
				break;
			case 19:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 2));
				break;
			case 20:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 4));
				break;
			case 21:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 7));
				break;
			case 22:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 9));
				break;
			case 23:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 12));
				break;
			case 24:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 14));
				break;
			case 25:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 16));
				break;
			case 26:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 19));
				break;
			case 27:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 21));
				break;
			case 28:
				WorldHandler.sendCommand(this.builderNoteEditor.getBuilderForNote((byte) 24));
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.isActive)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("worldhandler:textures/misc/note.png"));
			
			container.drawTexturedModalRect(x - 1, y - 1, 0, 0, 8, 59);
			container.drawTexturedModalRect(x - 1, y - 1 + 59, 0, 59, 13, 35);
			
			container.drawTexturedModalRect(x - 1 + 232 - 5, y - 1, 18, 0, 7, 59);
			container.drawTexturedModalRect(x - 1 + 232 - 10, y - 1 + 59, 13, 59, 12, 35);
			
			container.drawTexturedModalRect(x - 1 + 8, y - 1, 0, 94, 219, 1);
			container.drawTexturedModalRect(x - 1 + 13, y - 1 + 93, 0, 94, 209, 1);
		}
		else
		{
    		float scale = 4;
    		
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();
    		RenderHelper.enableGUIStandardItemLighting();
            
    		GlStateManager.translate(container.width / 2 - 8 * scale, container.height / 2 - 15 - 8 * scale, 0);
    		GlStateManager.scale(scale, scale, scale);
    		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Blocks.NOTEBLOCK), 0, 0);
            
    		RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
			
			String displayString = I18n.format("gui.worldhandler.blocks.note_block_editor.look_at_note_block", Keyboard.getKeyName(WorldHandler.KEY_WORLD_HANDLER.getKeyCode()));
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			fontRenderer.drawString(displayString, x + 116 - fontRenderer.getStringWidth(displayString) / 2, y + 70, ConfigSkin.getLabelColor());
		}
	}
	
	private SoundEvent getSoundEvent(BlockPos blockPos)
	{
    	IBlockState blockstate = Minecraft.getMinecraft().world.getBlockState(blockPos);
    	Material material = blockstate.getMaterial();
    	Block block = blockstate.getBlock();
    	
    	if(block.equals(Blocks.CLAY))
    	{
    		return SoundEvents.BLOCK_NOTE_FLUTE;
    	}
    	else if(block.equals(Blocks.GOLD_BLOCK))
    	{
    		return SoundEvents.BLOCK_NOTE_BELL;
    	}
    	else if(block.equals(Blocks.WOOL))
    	{
    		return SoundEvents.BLOCK_NOTE_GUITAR;
    	}
    	else if(block.equals(Blocks.PACKED_ICE))
    	{
    		return SoundEvents.BLOCK_NOTE_CHIME;
    	}
    	else if(block.equals(Blocks.BONE_BLOCK))
    	{
    		return SoundEvents.BLOCK_NOTE_XYLOPHONE;
    	}
    	
    	if(material.equals(Material.WOOD))
    	{
    		return SoundEvents.BLOCK_NOTE_BASS;
    	}
    	else if(material.equals(Material.SAND))
    	{
    		return SoundEvents.BLOCK_NOTE_SNARE;
    	}
    	else if(material.equals(Material.GLASS))
    	{
    		return SoundEvents.BLOCK_NOTE_HAT;
    	}
    	else if(material.equals(Material.ROCK))
    	{
    		return SoundEvents.BLOCK_NOTE_BASEDRUM;
    	}
    	
    	return SoundEvents.BLOCK_NOTE_HARP;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.blocks.note_block_editor");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.blocks.note_block_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.NOTE_EDITOR;
	}
}
