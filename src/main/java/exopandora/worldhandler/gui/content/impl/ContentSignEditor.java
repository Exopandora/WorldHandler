package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.argument.PrimitiveArgument.Linkage;
import exopandora.worldhandler.builder.argument.tag.TextTag;
import exopandora.worldhandler.builder.impl.DataCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicColorMenu;
import exopandora.worldhandler.gui.widget.menu.impl.MenuColorField;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SignBlock;

public class ContentSignEditor extends Content
{
	private final DataCommandBuilder builderSignEditor = new DataCommandBuilder();
	private final TextTag[] texts = new TextTag[4];
	private final CommandPreview preview = new CommandPreview(this.builderSignEditor, DataCommandBuilder.Label.MERGE_BLOCK);
	
	private GuiTextFieldTooltip commandField;
	
	private int selectedLine;
	private boolean editColor;
	private boolean isActive;
	
	public ContentSignEditor()
	{
		this.builderSignEditor.linkage().set(Linkage.MERGE);
		
		for(int x = 0; x < this.texts.length; x++)
		{
			TextTag text = new TextTag(x + 1);
			this.texts[x] = text;
			this.builderSignEditor.nbt().addTagProvider(text);
		}
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.isActive ? this.preview : null;
	}
	
	@Override
	public void init(Container container)
	{
		this.isActive = BlockHelper.getFocusedBlock() instanceof SignBlock;
		this.builderSignEditor.targetPos().set(BlockHelper.getFocusedBlockPos());
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		if(this.isActive)
		{
			this.commandField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.commmand"));
			this.commandField.setFilter(Predicates.notNull());
			this.commandField.setValue(this.texts[this.selectedLine].getComponent().getCommand());
			this.commandField.moveCursorToEnd();
			this.commandField.setResponder(text ->
			{
				this.texts[this.selectedLine].getComponent().setCommand(text);
				container.initButtons();
			});
			
			MenuColorField colors = new MenuColorField(x, y, "gui.worldhandler.blocks.sign_editor.text_line_" + (this.selectedLine + 1), this.texts[this.selectedLine].getComponent(), new ILogicColorMenu()
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
			
			container.addMenu(colors);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.isActive)
		{
			container.addRenderableWidget(button1 = new GuiButtonBase(x, y, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.text_line_1"), () ->
			{
				this.selectedLine = 0;
				container.init();
			}));
			container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 24, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.text_line_2"), () ->
			{
				this.selectedLine = 1;
				container.init();
			}));
			container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 48, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.text_line_3"), () ->
			{
				this.selectedLine = 2;
				container.init();
			}));
			container.addRenderableWidget(button4 = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.text_line_4"), () ->
			{
				this.selectedLine = 3;
				container.init();
			}));
			
			if(this.editColor)
			{
				container.addRenderableWidget(new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.generic.done"), () -> this.toggleEditColor(container)));
			}
			else
			{
				container.addRenderableWidget(this.commandField);
				container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.blocks.sign_editor.format_text_line"), () -> this.toggleEditColor(container)));
				container.addRenderableWidget(new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.actions.place_command_block"), () ->
				{
					CommandHelper.sendCommand(container.getPlayer(), this.builderSignEditor, DataCommandBuilder.Label.MERGE_BLOCK, Arrays.stream(this.texts).anyMatch(text -> text.getComponent().isStyled()));
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
			
			MutableComponent text = Component.translatable("gui.worldhandler.blocks.sign_editor.look_at_sign", KeyHandler.KEY_WORLD_HANDLER.getTranslatedKeyMessage());
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
		return Component.translatable("gui.worldhandler.title.blocks.sign_editor");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.blocks.sign_editor");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SIGN_EDITOR;
	}
}
