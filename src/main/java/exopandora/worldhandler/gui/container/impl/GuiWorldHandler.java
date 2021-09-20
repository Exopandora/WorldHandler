package exopandora.worldhandler.gui.container.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.menu.IMenu;
import exopandora.worldhandler.gui.widget.IContainerWidget;
import exopandora.worldhandler.gui.widget.IContainerWidget.EnumLayer;
import exopandora.worldhandler.gui.widget.WidgetCommandSyntax;
import exopandora.worldhandler.gui.widget.WidgetNameField;
import exopandora.worldhandler.gui.widget.WidgetShortcuts;
import exopandora.worldhandler.gui.widget.WidgetTabRenderer;
import exopandora.worldhandler.gui.widget.WidgetWatch;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.TextComponent;

public class GuiWorldHandler extends Container
{
	private static String player = Minecraft.getInstance().getUser().getName();
	private final static List<IContainerWidget> WIDGETS = Util.make(Lists.newArrayList(), widgets ->
	{
		widgets.add(new WidgetTabRenderer());
		widgets.add(new WidgetWatch());
		widgets.add(new WidgetNameField());
		widgets.add(new WidgetCommandSyntax());
		widgets.add(new WidgetShortcuts());
	});
	
	private final Content content;
	
	public GuiWorldHandler(Content content) throws Exception
	{
		super(content.getTitle());
		this.content = content;
		this.content.init(this);
	}
	
	@Override
	public void init()
	{
		ActionHelper.tryRun(() ->
		{
			this.widgetButtons.clear();
			this.menus.clear();
			this.clearWidgets();
			
			final int x = this.getContentX();
			final int y = this.getContentY();
			
			this.content.onPlayerNameChanged(this.getPlayer());
			this.content.initGui(this, x, y);
			
			for(IMenu menu : this.menus)
			{
				menu.initGui(this);
			}
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled())
				{
					widget.initGui(this, x, y);
				}
			}
			
			this.initButtons();
		});
	}
	
	@Override
	public void initButtons()
	{
		ActionHelper.tryRun(() ->
		{
			this.clearWidgets();
			this.content.initButtons(this, this.getContentX(), this.getContentY());
			this.widgetButtons.forEach(w -> this.addRenderableWidget(w));
			
			int x = this.getContentX();
			int y = this.getContentY();
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled())
				{
					widget.initButtons(this, x, y);
				}
			}
			
			for(IMenu menu : this.menus)
			{
				menu.initButtons(this);
			}
		});
	}
	
	@Override
	public void tick()
	{
		ActionHelper.tryRun(() ->
		{
			this.content.tick(this);
			
			for(IMenu menu : this.menus)
			{
				menu.tick();
			}
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled())
				{
					widget.tick(this);
				}
			}
		});
	}
	
	@Override
	public int getBackgroundX()
	{
		return (this.width - this.getBackgroundWidth()) / 2;
	}
	
	@Override
	public int getBackgroundY()
	{
		return (this.height - this.getBackgroundHeight()) / 2 + 10;
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		ActionHelper.tryRun(() ->
		{
			final int backgroundX = this.getBackgroundX();
			final int backgroundY = this.getBackgroundY();
			
			if(Config.getSkin().drawBackground())
			{
				this.setBlitOffset(-1);
				super.renderBackground(stack);
			}
			
			RenderSystem.enableBlend();
			RenderUtils.colorDefaultBackground();
			
			this.bindBackground();
			this.blit(stack, backgroundX, backgroundY, 0, 0, this.getBackgroundWidth(), this.getBackgroundHeight());
			
			final String label = Main.MC_VERSION + "-" + Main.MOD_VERSION;
			final int versionWidth = this.width - this.font.width(label) - 2;
			final int versionHeight = this.height - 10;
			this.font.draw(stack, label, versionWidth, versionHeight, Config.getSkin().getLabelColor() + 0x33000000);
			
			int x = this.getContentX();
			int y = this.getContentY();
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled() && EnumLayer.BACKGROUND == widget.getLayer())
				{
					widget.drawScreen(stack, this, x, y, mouseX, mouseY, partialTicks);
				}
			}
			
			final int maxWidth = this.getBackgroundWidth() - 18 - this.font.width(this.getPlayer()) - (Config.getSettings().watch() ? 9 : 0);
			this.font.draw(stack, TextUtils.stripText(this.content.getTitle(), maxWidth, this.font), backgroundX + 7, backgroundY + 7, Config.getSkin().getLabelColor());
			
			for(int i = 0; i < this.renderables.size(); i++)
			{
				this.renderables.get(i).render(stack, mouseX, mouseY, partialTicks);
			}
			
			this.content.drawScreen(stack, this, x, y, mouseX, mouseY, partialTicks);
			
			for(IMenu menu : this.menus)
			{
				menu.draw(stack, mouseX, mouseY, partialTicks);
			}
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled() && EnumLayer.FOREGROUND == widget.getLayer())
				{
					widget.drawScreen(stack, this, x, y, mouseX, mouseY, partialTicks);
				}
			}
			
			if(Config.getSettings().tooltips())
			{
				for(Widget button : this.renderables)
				{
					if(button instanceof GuiButtonTooltip)
					{
						((GuiButtonTooltip) button).renderTooltip(this, stack, mouseX, mouseY);
					}
				}
			}
			
			if(mouseX >= versionWidth && mouseY >= versionHeight)
			{
				stack.pushPose();
				stack.translate(versionWidth - 12, versionHeight + 12, 0);
				
				this.renderTooltip(stack, new TextComponent(label), 0, 0);
				
				stack.popPose();
			}
			
			RenderSystem.disableBlend();
		});
	}
	
	@Override
	public void mouseMoved(double xPos, double mouseY)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled())
			{
				widget.mouseMoved(xPos, mouseY);
			}
		}
		
		this.content.mouseMoved(xPos, mouseY);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int keyCode)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.mouseClicked(mouseX, mouseY, keyCode))
			{
				return true;
			}
		}
		
		if(this.content.mouseClicked(mouseX, mouseY, keyCode))
		{
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, keyCode);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int keyCode)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.mouseReleased(mouseX, mouseY, keyCode))
			{
				return true;
			}
		}
		
		if(this.content.mouseReleased(mouseX, mouseY, keyCode))
		{
			return true;
		}
		
		return super.mouseReleased(mouseX, mouseY, keyCode);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int keyCode, double deltaX, double deltaY)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.mouseDragged(mouseX, mouseY, keyCode, deltaX, deltaY))
			{
				return true;
			}
		}
		
		if(this.content.mouseDragged(mouseX, mouseY, keyCode, deltaX, deltaY))
		{
			return true;
		}
		
		return super.mouseDragged(mouseX, mouseY, keyCode, deltaX, deltaY);
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double distance)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.mouseScrolled(mouseX, mouseY, distance))
			{
				return true;
			}
		}
		
		if(this.content.mouseScrolled(mouseX, mouseY, distance))
		{
			return true;
		}
		
		return super.mouseScrolled(mouseX, mouseY, distance);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		boolean focused = this.getFocused() != null;
		
		if(focused && this.getFocused() instanceof AbstractWidget)
		{
			focused = ((AbstractWidget) this.getFocused()).isFocused();
		}
		
		if(!focused && KeyHandler.KEY_WORLD_HANDLER.matches(keyCode, scanCode) && KeyHandler.KEY_WORLD_HANDLER.getKeyModifier().isActive(null))
		{
			Minecraft.getInstance().setScreen(null);
			return true;
		}
		
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.keyPressed(keyCode, scanCode, modifiers))
			{
				return true;
			}
		}
		
		if(this.content.keyPressed(keyCode, scanCode, modifiers))
		{
			return true;
		}
		
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.keyReleased(keyCode, scanCode, modifiers))
			{
				return true;
			}
		}
		
		if(this.content.keyReleased(keyCode, scanCode, modifiers))
		{
			return true;
		}
		
		return super.keyReleased(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean charTyped(char charTyped, int keyCode)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.charTyped(charTyped, keyCode))
			{
				return true;
			}
		}
		
		if(this.content.charTyped(charTyped, keyCode))
		{
			return true;
		}
		
		return super.charTyped(charTyped, keyCode);
	}
	
	@Override
	public boolean changeFocus(boolean focus)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.changeFocus(focus))
			{
				return true;
			}
		}
		
		if(this.content.changeFocus(focus))
		{
			return true;
		}
		
		return super.changeFocus(focus);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY)
	{
		for(IContainerWidget widget : WIDGETS)
		{
			if(widget.isEnabled() && widget.isMouseOver(mouseX, mouseY))
			{
				return true;
			}
		}
		
		if(this.content.isMouseOver(mouseX, mouseY))
		{
			return true;
		}
		
		return super.isMouseOver(mouseX, mouseY);
	}
	
	private int getContentX()
	{
		return this.getBackgroundX() + 8;
	}
	
	private int getContentY()
	{
		return this.getBackgroundY() + 33;
	}
	
	@Override
	public void onClose()
	{
		ActionHelper.tryRun(this.content::onGuiClosed);
		super.onClose();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return Config.getSettings().pause();
	}
	
	@Override
	public String getPlayer()
	{
		return GuiWorldHandler.player;
	}
	
	@Override
	public void setPlayer(String player)
	{
		GuiWorldHandler.player = player;
	}
	
	@Override
	public Content getContent()
	{
		return this.content;
	}
	
//	@Override
//	public boolean shouldCloseOnEsc()
//	{
//		return true;
//	}
	
	@Override
	public int getBackgroundWidth()
	{
		return 248;
	}
	
	@Override
	public int getBackgroundHeight()
	{
		return 166;
	}
	
	@Override
	public void bindBackground()
	{
		RenderSystem.setShaderTexture(0, ResourceHelper.backgroundTexture());
	}
}