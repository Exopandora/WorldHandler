package exopandora.worldhandler.gui.content.impl;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderSignEditor;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementColorMenu;
import exopandora.worldhandler.gui.content.element.logic.ILogicColorMenu;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentSignEditor extends Content
{
	private static final ResourceLocation LOGO = new ResourceLocation(Main.MODID, "textures/logo.png");
	
	private int selectedLine = 0;
	private boolean editColor;
	
	private GuiTextFieldTooltip commandField;
	private GuiTextFieldTooltip textlineField;
	
	private final BuilderSignEditor builderSignEditor = new BuilderSignEditor();
	
	private boolean isActive;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.isActive ? this.builderSignEditor : null;
	}
	
	@Override
	public void init(Container container)
	{
		this.isActive = BlockHelper.isFocusedBlockEqualTo(Blocks.STANDING_SIGN) || BlockHelper.isFocusedBlockEqualTo(Blocks.WALL_SIGN);
		this.builderSignEditor.setPosition(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		if(this.isActive)
		{			
			this.commandField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.commmand"));
			this.commandField.setValidator(Predicates.notNull());
			this.commandField.setText(this.builderSignEditor.getCommand(this.selectedLine));
			this.commandField.setCursorPositionEnd();
			
			ElementColorMenu colors = new ElementColorMenu(this, x, y, "gui.worldhandler.blocks.sign_editor.text_line_" + (this.selectedLine + 1), this.builderSignEditor.getColoredString(this.selectedLine), new int[] {8, 9, 10, 11, 12, 13}, new ILogicColorMenu()
			{
				@Override
				public Predicate<String> getValidator()
				{
					return string -> Minecraft.getMinecraft().fontRenderer.getStringWidth(string) <= 90;
				}
				
				@Override
				public boolean drawButtons()
				{
					return editColor;
				}
				
				@Override
				public String getId()
				{
					return "color" + selectedLine;
				}
			});
			
			container.add(colors);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		if(this.isActive)
		{
			container.add(button3 = new GuiButtonWorldHandler(3, x, y, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_1")));
			container.add(button4 = new GuiButtonWorldHandler(4, x, y + 24, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_2")));
			container.add(button5 = new GuiButtonWorldHandler(5, x, y + 48, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_3")));
			container.add(button6 = new GuiButtonWorldHandler(6, x, y + 72, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_4")));
			
			if(this.editColor)
			{
				container.add(new GuiButtonWorldHandler(7, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.done")));
			}
			else
			{
				container.add(new GuiButtonWorldHandler(7, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.format_text_line")));
				container.add(new GuiButtonWorldHandler(2, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block")));
			}
			
			button3.enabled = this.selectedLine != 0;
			button4.enabled = this.selectedLine != 1;
			button5.enabled = this.selectedLine != 2;
			button6.enabled = this.selectedLine != 3;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button) throws Exception
	{
		switch(button.id)
		{
			case 2:
				WorldHandler.sendCommand(this.builderSignEditor, this.builderSignEditor.isSpecial());
				break;
			case 3:
				this.selectedLine = 0;
				container.initGui();
				break;
			case 4:
				this.selectedLine = 1;
				container.initGui();
				break;
			case 5:
				this.selectedLine = 2;
				container.initGui();
				break;
			case 6:
				this.selectedLine = 3;
				container.initGui();
				break;
			case 7:
				this.editColor = !this.editColor;
				container.initGui();
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
			if(!this.editColor)
			{
				this.commandField.drawTextBox();
			}
		}
		else
		{
    		float scale = 4;
			
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();
    		RenderHelper.enableGUIStandardItemLighting();
            
    		GlStateManager.translate(container.width / 2 - 8.5F * scale, container.height / 2 - 15 - 8.5F * scale, 0);
    		GlStateManager.scale(scale, scale, scale);
    		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.SIGN), 0, 0);
            
    		RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
			
			String displayString = I18n.format("gui.worldhandler.blocks.sign_editor.look_at_sign", Keyboard.getKeyName(WorldHandler.KEY_WORLD_HANDLER.getKeyCode()));
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			fontRenderer.drawString(displayString, x + 116 - fontRenderer.getStringWidth(displayString) / 2, y + 70, ConfigSkin.getLabelColor());
		}
	}
	
	@Override
	public void keyTyped(Container container, char charTyped, int keyCode)
	{
		if(this.isActive)
		{
			if(this.commandField.textboxKeyTyped(charTyped, keyCode))
			{
				this.builderSignEditor.setCommand(this.selectedLine, this.commandField.getText());
				container.initButtons();
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.isActive)
		{
			this.commandField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.blocks.sign_editor");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.blocks.sign_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SIGN_EDITOR;
	}
}
