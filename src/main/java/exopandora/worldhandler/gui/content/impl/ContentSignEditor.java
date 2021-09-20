package exopandora.worldhandler.gui.content.impl;


import com.google.common.base.Predicates;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderSignEditor;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicColorMenu;
import exopandora.worldhandler.gui.menu.impl.MenuColorField;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SignBlock;

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
		this.isActive = BlockHelper.getFocusedBlock() instanceof SignBlock;
		this.builderSignEditor.setPosition(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		if(this.isActive)
		{
			this.commandField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.commmand"));
			this.commandField.setFilter(Predicates.notNull());
			this.commandField.setValue(this.builderSignEditor.getCommand(this.selectedLine));
			this.commandField.moveCursorToEnd();
			this.commandField.setResponder(text ->
			{
				this.builderSignEditor.setCommand(this.selectedLine, text);
				container.initButtons();
			});
			
			MenuColorField colors = new MenuColorField(x, y, "gui.worldhandler.blocks.sign_editor.text_line_" + (this.selectedLine + 1), this.builderSignEditor.getColoredString(this.selectedLine), new ILogicColorMenu()
			{
				@Override
				public boolean validate(String text)
				{
					return Minecraft.getInstance().font.width(text) <= 90;
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.isActive)
		{
			container.add(button1 = new GuiButtonBase(x, y, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.text_line_1"), () ->
			{
				this.selectedLine = 0;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.text_line_2"), () ->
			{
				this.selectedLine = 1;
				container.init();
			}));
			container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.text_line_3"), () ->
			{
				this.selectedLine = 2;
				container.init();
			}));
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.text_line_4"), () ->
			{
				this.selectedLine = 3;
				container.init();
			}));
			
			if(this.editColor)
			{
				container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.generic.done"), () -> this.toggleEditColor(container)));
			}
			else
			{
				container.add(this.commandField);
				container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.blocks.sign_editor.format_text_line"), () -> this.toggleEditColor(container)));
				container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () ->
				{
					CommandHelper.sendCommand(container.getPlayer(), this.builderSignEditor, this.builderSignEditor.isSpecial());
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
		if(this.isActive && !this.editColor)
		{
			this.commandField.tick();
		}
	}
	
	private void toggleEditColor(Container container)
	{
		this.editColor = !this.editColor;
		container.init();
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.isActive)
		{
			if(!this.editColor)
			{
				this.commandField.renderButton(matrix, mouseX, mouseY, partialTicks);
			}
		}
		else
		{
			float scale = 4;
			
			PoseStack posestack = RenderSystem.getModelViewStack();
			posestack.pushPose();
			posestack.translate(container.width / 2 - 8.5F * scale, container.height / 2 - 15 - 8.5F * scale, 0);
			posestack.scale(scale, scale, scale);
			
			Minecraft.getInstance().getItemRenderer().renderGuiItem(new ItemStack(Items.OAK_SIGN), 0, 0);
			
			posestack.popPose();
			RenderSystem.applyModelViewMatrix();
			
			TranslatableComponent text = new TranslatableComponent("gui.worldhandler.blocks.sign_editor.look_at_sign", KeyHandler.KEY_WORLD_HANDLER.getTranslatedKeyMessage());
			Font font = Minecraft.getInstance().font;
			font.draw(matrix, text, x + 116 - font.width(text) / 2, y + 70, Config.getSkin().getLabelColor());
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.BLOCKS;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.blocks.sign_editor");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.blocks.sign_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SIGN_EDITOR;
	}
}
