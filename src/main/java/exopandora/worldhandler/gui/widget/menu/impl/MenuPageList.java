package exopandora.worldhandler.gui.widget.menu.impl;

import java.util.List;
import java.util.Objects;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.menu.Menu;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MenuPageList<T> extends Menu
{
	private final List<T> items;
	private final ILogicPageList<T> logic;
	private final Persistence persistence;
	private final int width;
	private final int height;
	private final int length;
	
	public MenuPageList(int x, int y, List<T> items, int width, int height, int length, Container container, ILogicPageList<T> logic)
	{
		super(x, y);
		this.items = Objects.requireNonNull(items);
		this.width = width;
		this.height = height;
		this.length = length;
		this.logic = Objects.requireNonNull(logic);
		this.items.sort((a, b) -> this.logic.translate(a).getString().compareTo(this.logic.translate(b).getString()));
		this.persistence = container.getContent().getPersistence(logic.getId(), Persistence::new);
		
		if(!this.items.isEmpty())
		{
			this.logic.onClick(this.items.get(this.persistence.getSelectedIndex()));
		}
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		boolean extended = (this.items.size() == this.length + 1);
		
		if(!extended)
		{
			int buttonWidth = (this.width - 4) / 2;
			
			GuiButtonBase left = new GuiButtonBase(this.x, this.y + (this.height + 4) * this.length, buttonWidth + 1, this.height, TextUtils.ARROW_LEFT, () -> this.goLeft(container));
			left.active = this.persistence.getPage() > 0;
			container.addRenderableWidget(left);
			
			GuiButtonBase right = new GuiButtonBase(this.x + 5 + buttonWidth, this.y + (this.height + 4) * this.length, buttonWidth, this.height, TextUtils.ARROW_RIGHT, () -> this.goRight(container));
			right.active = this.persistence.getPage() < this.getTotalPages() - 1;
			container.addRenderableWidget(right);
		}
		
		int length = (extended ? this.length + 1 : this.length);
		
		for(int x = 0; x < length; x++)
		{
			int index = this.persistence.getPage() * length + x;
			GuiButtonBase button;
			
			if(index < this.items.size())
			{
				T item = this.items.get(index);
				MutableComponent text = this.logic.translate(item);
				button = this.logic.onRegister(this.x, this.y + (this.height + 4) * x, this.width, this.height, text, item, () ->
				{
					this.persistence.setSelectedIndex(index);
					this.logic.onClick(item);
				});
				
				if(this.logic.doDisable())
				{
					button.active = this.persistence.getSelectedIndex() != index;
				}
			}
			else
			{
				button = new GuiButtonBase(this.x, this.y + (this.height + 4) * x, this.width, this.height, Component.empty(), null);
				button.active = false;
			}
			
			container.addRenderableWidget(button);
		}
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void draw(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		guiGraphics.drawString(Minecraft.getInstance().font, String.format("%d/%d", this.persistence.getPage() + 1, this.getTotalPages()), this.x, this.y - 11, Config.getSkin().getHeadlineColor(), false);
	}
	
	private void goLeft(Container container)
	{
		int page = this.persistence.getPage();
		
		if(Screen.hasShiftDown())
		{
			this.persistence.setPage(page - Math.min(10, page));
		}
		else
		{
			this.persistence.setPage(page - 1);
		}
		
		container.initButtons();
	}
	
	private void goRight(Container container)
	{
		int page = this.persistence.getPage();
		
		if(Screen.hasShiftDown())
		{
			this.persistence.setPage(page + Math.min(10, this.getTotalPages() - 1 - page));
		}
		else
		{
			this.persistence.setPage(page + 1);
		}
		
		container.initButtons();
	}
	
	private int getTotalPages()
	{
		return Math.max((int) Math.ceil((float) this.items.size() / this.length), 1);
	}
	
	public static class Persistence
	{
		private int page;
		private int selectedIndex;
		
		public Persistence()
		{
			this(0, 0);
		}
		
		public Persistence(int page, int selectedIndex)
		{
			this.page = page;
			this.selectedIndex = selectedIndex;
		}
		
		public int getPage()
		{
			return this.page;
		}
		
		public void setPage(int page)
		{
			this.page = page;
		}
		
		public int getSelectedIndex()
		{
			return this.selectedIndex;
		}
		
		public void setSelectedIndex(int selectedIndex)
		{
			this.selectedIndex = selectedIndex;
		}
	}
}
