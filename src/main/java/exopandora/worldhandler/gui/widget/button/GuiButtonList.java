package exopandora.worldhandler.gui.widget.button;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.impl.ILogicMapped;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class GuiButtonList<T> extends GuiButtonTooltip
{
	private final ILogicMapped<T> logic;
	private final Persistence persistence;
	private final List<T> items;
	
	public GuiButtonList(int x, int y, List<T> items, int widthIn, int heightIn, Container container, ILogicMapped<T> logic)
	{
		super(x, y, widthIn, heightIn, TextComponent.EMPTY, null, null);
		this.items = items;
		this.logic = logic;
		this.persistence = container.getContent().getPersistence(this.logic.getId(), Persistence::new);
		this.init();
	}
	
	private void init()
	{
		this.logic.onInit(this.items.get(this.persistence.getIndex()));
		this.updateMessage();
	}
	
	@Override
	public void renderButton(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(matrix, Minecraft.getInstance(), mouseX, mouseY);
		this.updateMessage();
		
		Font font = Minecraft.getInstance().font;
		
		if(this.getMessage() != null && !this.getMessage().getString().isEmpty())
		{
			Component leftArrow = this.isHoveringLeft(mouseX, mouseY) ? TextUtils.ARROW_LEFT_BOLD : TextUtils.ARROW_LEFT;
			Component rightArrow = this.isHoveringRight(mouseX, mouseY) ? TextUtils.ARROW_RIGHT_BOLD : TextUtils.ARROW_RIGHT;
			
			int maxWidth = Math.max(0, this.width - font.width("<   >"));
			int spaceWidth = font.width(" ");
			
			Component display = TextUtils.stripText((MutableComponent) this.getMessage(), maxWidth, font);
			int yPos = this.y + (this.height - 8) / 2;
			
			GuiComponent.drawCenteredString(matrix, font, display, this.x + this.width / 2, yPos, this.getFGColor());
			GuiComponent.drawCenteredString(matrix, font, leftArrow, this.x + this.width / 2 - maxWidth / 2 - spaceWidth, yPos, this.getFGColor());
			GuiComponent.drawCenteredString(matrix, font, rightArrow, this.x + this.width / 2 + maxWidth / 2 + spaceWidth, yPos, this.getFGColor());
		}
	}
	
	@Override
	public void renderTooltip(Screen screen, PoseStack matrix, int mouseX, int mouseY)
	{
		this.tooltip = this.logic.formatTooltip(this.items.get(this.persistence.getIndex()), this.persistence.getIndex() + 1, this.items.size());
		super.renderTooltip(screen, matrix, mouseX, mouseY);
	}
	
	@Override
	public void onClick(double mouseX, double mouseY)
	{
		int max = this.items.size() - 1;
		int index = this.persistence.getIndex();
		
		if(this.isHoveringLeft(mouseX, mouseY))
		{
			if(Screen.hasShiftDown())
			{
				if(index < 10)
				{
					this.persistence.setIndex((index - 9 + max) % max);
				}
				else
				{
					this.persistence.decrementIndex(10);
				}
			}
			else
			{
				if(index > 0)
				{
					this.persistence.decrementIndex();
				}
				else
				{
					this.persistence.setIndex(max);
				}
			}
		}
		else if(this.isHoveringRight(mouseX, mouseY))
		{
			if(Screen.hasShiftDown())
			{
				if(index > max - 10)
				{
					this.persistence.setIndex((index + 9 - max) % max);
				}
				else
				{
					this.persistence.incrementIndex(10);
				}
			}
			else
			{
				if(index < max)
				{
					this.persistence.incrementIndex();
				}
				else
				{
					this.persistence.setIndex(0);
				}
			}
		}
		
		this.logic.onClick(this.items.get(this.persistence.getIndex()));
	}
	
	private void updateMessage()
	{
		this.setMessage(this.logic.translate(this.items.get(this.persistence.getIndex())));
	}
	
	private boolean isHoveringLeft(double mouseX, double mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.x && mouseX < this.x + Math.ceil(this.width / 2);
	}
	
	private boolean isHoveringRight(double mouseX, double mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.x + Math.ceil(this.width / 2) && mouseX < this.x + this.width;
	}
	
	private boolean isHoveringVertical(double mouseY)
	{
		return mouseY >= this.y && mouseY < this.y + this.height;
	}
	
	public static class Persistence
	{
		private int index;
		
		public Persistence()
		{
			this(0);
		}
		
		public Persistence(int index)
		{
			this.index = index;
		}
		
		public void setIndex(int index)
		{
			this.index = index;
		}
		
		public int getIndex()
		{
			return this.index;
		}
		
		public void incrementIndex()
		{
			this.index++;
		}
		
		public void incrementIndex(int amount)
		{
			this.index += amount;
		}
		
		public void decrementIndex()
		{
			this.index--;
		}
		
		public void decrementIndex(int amount)
		{
			this.index -= amount;
		}
	}
}
