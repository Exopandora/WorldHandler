package exopandora.worldhandler.gui.container.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiWorldHandler extends Container
{
	private static String player = Minecraft.getInstance().getSession().getUsername();
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
			this.buttons.clear();
			this.children.clear();
			
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
			this.buttons.clear();
			this.children.clear();
			this.content.initButtons(this, this.getContentX(), this.getContentY());
			this.widgetButtons.forEach(this::add);
			
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
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		ActionHelper.tryRun(() ->
		{
			final int backgroundX = this.getBackgroundX();
			final int backgroundY = this.getBackgroundY();
			
			if(Config.getSkin().drawBackground())
			{
				this.setBlitOffset(-1);
				super.renderBackground(matrix);
			}
			
			RenderSystem.enableBlend();
			RenderUtils.colorDefaultBackground();
			
			this.bindBackground();
			this.blit(matrix, backgroundX, backgroundY, 0, 0, this.getBackgroundWidth(), this.getBackgroundHeight());
			
			final String label = Main.MC_VERSION + "-" + Main.MOD_VERSION;
			final int versionWidth = this.width - this.font.getStringWidth(label) - 2;
			final int versionHeight = this.height - 10;
			this.font.drawString(matrix, label, versionWidth, versionHeight, Config.getSkin().getLabelColor() + 0x33000000);
			
			int x = this.getContentX();
			int y = this.getContentY();
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled() && EnumLayer.BACKGROUND == widget.getLayer())
				{
					widget.drawScreen(matrix, this, x, y, mouseX, mouseY, partialTicks);
				}
			}
			
			final int maxWidth = this.getBackgroundWidth() - 18 - this.font.getStringWidth(this.getPlayer()) - (Config.getSettings().watch() ? 9 : 0);
			this.font.func_243248_b(matrix, TextUtils.stripText(this.content.getTitle(), maxWidth, this.font), backgroundX + 7, backgroundY + 7, Config.getSkin().getLabelColor());
			
			for(int i = 0; i < this.buttons.size(); i++)
			{
				this.buttons.get(i).render(matrix, mouseX, mouseY, partialTicks);
			}
			
			this.content.drawScreen(matrix, this, x, y, mouseX, mouseY, partialTicks);
			
			for(IMenu menu : this.menus)
			{
				menu.draw(matrix, mouseX, mouseY, partialTicks);
			}
			
			for(IContainerWidget widget : WIDGETS)
			{
				if(widget.isEnabled() && EnumLayer.FOREGROUND == widget.getLayer())
				{
					widget.drawScreen(matrix, this, x, y, mouseX, mouseY, partialTicks);
				}
			}
			
			if(Config.getSettings().tooltips())
			{
				for(Widget button : this.buttons)
				{
					if(button instanceof GuiButtonTooltip)
					{
						((GuiButtonTooltip) button).renderTooltip(this, matrix, mouseX, mouseY);
					}
				}
			}
			
			if(mouseX >= versionWidth && mouseY >= versionHeight)
			{
				matrix.push();
				matrix.translate(versionWidth - 12, versionHeight + 12, 0);
				
				this.renderTooltip(matrix, new StringTextComponent(label), 0, 0);
				
				matrix.pop();
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
		boolean focused = this.getListener() != null;
		
		if(focused && this.getListener() instanceof Widget)
		{
			focused = ((Widget) this.getListener()).isFocused();
		}
		
		if(!focused && KeyHandler.isPressed(KeyHandler.KEY_WORLD_HANDLER, keyCode))
		{
			this.onClose();
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
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return true;
	}
	
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
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.backgroundTexture());
	}
}