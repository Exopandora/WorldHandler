package exopandora.worldhandler.gui.content.impl;


import com.google.common.base.Predicates;
import com.mojang.blaze3d.platform.GlStateManager;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderSignEditor;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementColorMenu;
import exopandora.worldhandler.gui.logic.ILogicColorMenu;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentSignEditor extends Content
{
	private int selectedLine = 0;
	private boolean editColor;
	
	private GuiTextFieldTooltip commandField;
	
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
		this.isActive = BlockHelper.getFocusedBlock() instanceof AbstractSignBlock;
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
			this.commandField.func_212954_a(text ->
			{
				this.builderSignEditor.setCommand(this.selectedLine, text);
				container.initButtons();
			});
			
			ElementColorMenu colors = new ElementColorMenu(x, y, "gui.worldhandler.blocks.sign_editor.text_line_" + (this.selectedLine + 1), this.builderSignEditor.getColoredString(this.selectedLine), new ILogicColorMenu()
			{
				@Override
				public boolean validate(String text)
				{
					return Minecraft.getInstance().fontRenderer.getStringWidth(text) <= 90;
				}
				
				@Override
				public boolean doDrawButtons()
				{
					return ContentSignEditor.this.editColor;
				}
				
				@Override
				public String getId()
				{
					return "color" + ContentSignEditor.this.selectedLine;
				}
			});
			
			container.add(colors);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.isActive)
		{
			container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_1"), () ->
			{
				this.selectedLine = 0;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_2"), () ->
			{
				this.selectedLine = 1;
				container.init();
			}));
			container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_3"), () ->
			{
				this.selectedLine = 2;
				container.init();
			}));
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.text_line_4"), () ->
			{
				this.selectedLine = 3;
				container.init();
			}));
			
			if(this.editColor)
			{
				container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.done"), () -> this.toggleEditColor(container)));
			}
			else
			{
				container.add(this.commandField);
				container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.blocks.sign_editor.format_text_line"), () -> this.toggleEditColor(container)));
				container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block"), () ->
				{
					CommandHelper.sendCommand(this.builderSignEditor, this.builderSignEditor.isSpecial());
				}));
			}
			
			button1.active = this.selectedLine != 0;
			button2.active = this.selectedLine != 1;
			button3.active = this.selectedLine != 2;
			button4.active = this.selectedLine != 3;
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.editColor)
		{
			
		}
	}
	
	private void toggleEditColor(Container container)
	{
		this.editColor = !this.editColor;
		container.init();
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.isActive)
		{
			if(!this.editColor)
			{
				this.commandField.renderButton(mouseX, mouseY, partialTicks);
			}
		}
		else
		{
    		float scale = 4;
			
			GlStateManager.color3f(1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();
    		RenderHelper.enableGUIStandardItemLighting();
            
    		GlStateManager.translatef(container.width / 2 - 8.5F * scale, container.height / 2 - 15 - 8.5F * scale, 0);
    		GlStateManager.scalef(scale, scale, scale);
    		Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(new ItemStack(Items.field_222071_kr), 0, 0);
            
    		RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
			
			String displayString = I18n.format("gui.worldhandler.blocks.sign_editor.look_at_sign", WorldHandler.KEY_WORLD_HANDLER.getLocalizedName());
			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			fontRenderer.drawString(displayString, x + 116 - fontRenderer.getStringWidth(displayString) / 2, y + 70, Config.getSkin().getLabelColor());
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
