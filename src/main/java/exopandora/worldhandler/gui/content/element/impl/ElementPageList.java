package exopandora.worldhandler.gui.content.element.impl;

import java.util.List;
import java.util.Objects;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementPageList<T> extends Element
{
	private final List<T> items;
	private final ILogicPageList<T> logic;
	private final Persistence persistence;
	private final int width;
	private final int height;
	private final int length;
	
	public ElementPageList(int x, int y, List<T> items, int width, int height, int length, Container container, ILogicPageList<T> logic)
	{
		super(x, y);
		this.items = Objects.requireNonNull(items);
		this.width = width;
		this.height = height;
		this.length = length;
		this.logic = Objects.requireNonNull(logic);
		this.items.sort((a, b) -> this.logic.translate(a).compareTo(this.logic.translate(b)));
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
			
			GuiButtonBase left = new GuiButtonBase(this.x, this.y + (this.height + 4) * this.length, buttonWidth + 1, this.height, "<", () -> this.goLeft(container));
			left.enabled = this.persistence.getPage() > 0;
			container.add(left);
			
			GuiButtonBase right = new GuiButtonBase(this.x + 5 + buttonWidth, this.y + (this.height + 4) * this.length, buttonWidth, this.height, ">", () -> this.goRight(container));
			right.enabled = this.persistence.getPage() < this.getTotalPages() - 1;
			container.add(right);
		}
		
		int length = (extended ? this.length + 1 : this.length);
		
		for(int x = 0; x < length; x++)
		{
			int index = this.persistence.getPage() * length + x;
			GuiButtonBase button;
			
			if(index < this.items.size())
			{
				T item = this.items.get(index);
				String text = TextFormatting.shortenString(this.logic.translate(item), this.width, Minecraft.getInstance().fontRenderer);
				button = this.logic.onRegister(this.x, this.y + (this.height + 4) * x, this.width, this.height, text, item, () ->
				{
					this.persistence.setSelectedIndex(index);
					this.logic.onClick(item);
				});
				
				if(this.logic.doDisable())
				{
					button.enabled = this.persistence.getSelectedIndex() != index;
				}
			}
			else
			{
				button = new GuiButtonBase(this.x, this.y + (this.height + 4) * x, this.width, this.height, null, null);
				button.enabled = false;
			}
			
			container.add(button);
		}
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks)
	{
		Minecraft.getInstance().fontRenderer.drawString(String.format("%d/%d", this.persistence.getPage() + 1, this.getTotalPages()), this.x, this.y - 11, Config.getSkin().getHeadlineColor());
	}
	
	private void goLeft(Container container)
	{
		int page = this.persistence.getPage();
		
		if(GuiScreen.isShiftKeyDown())
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
		
		if(GuiScreen.isShiftKeyDown())
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
		return (int) Math.ceil((float) this.items.size() / this.length);
	}
	
	@OnlyIn(Dist.CLIENT)
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
