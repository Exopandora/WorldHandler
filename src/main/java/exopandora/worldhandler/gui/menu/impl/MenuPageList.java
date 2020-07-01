package exopandora.worldhandler.gui.menu.impl;

import java.util.List;
import java.util.Objects;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
			left.field_230693_o_ = this.persistence.getPage() > 0;
			container.add(left);
			
			GuiButtonBase right = new GuiButtonBase(this.x + 5 + buttonWidth, this.y + (this.height + 4) * this.length, buttonWidth, this.height, TextUtils.ARROW_RIGHT, () -> this.goRight(container));
			right.field_230693_o_ = this.persistence.getPage() < this.getTotalPages() - 1;
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
				IFormattableTextComponent text = TextUtils.stripText(this.logic.translate(item), this.width, Minecraft.getInstance().fontRenderer);
				button = this.logic.onRegister(this.x, this.y + (this.height + 4) * x, this.width, this.height, text, item, () ->
				{
					this.persistence.setSelectedIndex(index);
					this.logic.onClick(item);
				});
				
				if(this.logic.doDisable())
				{
					button.field_230693_o_ = this.persistence.getSelectedIndex() != index;
				}
			}
			else
			{
				button = new GuiButtonBase(this.x, this.y + (this.height + 4) * x, this.width, this.height, StringTextComponent.field_240750_d_, null);
				button.field_230693_o_ = false;
			}
			
			container.add(button);
		}
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void draw(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		Minecraft.getInstance().fontRenderer.func_238421_b_(matrix, String.format("%d/%d", this.persistence.getPage() + 1, this.getTotalPages()), this.x, this.y - 11, Config.getSkin().getHeadlineColor());
	}
	
	private void goLeft(Container container)
	{
		int page = this.persistence.getPage();
		
		if(Screen.func_231173_s_())
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
		
		if(Screen.func_231173_s_())
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
