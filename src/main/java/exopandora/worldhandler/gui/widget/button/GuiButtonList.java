package exopandora.worldhandler.gui.widget.button;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.impl.ILogicMapped;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonList<T> extends GuiButtonTooltip
{
	private final ILogicMapped<T> logic;
	private final Persistence persistence;
	private final List<T> items;
	
	public GuiButtonList(int x, int y, List<T> items, int widthIn, int heightIn, Container container, ILogicMapped<T> logic)
	{
		super(x, y, widthIn, heightIn, StringTextComponent.EMPTY, null, null);
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
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(matrix, Minecraft.getInstance(), mouseX, mouseY);
		this.updateMessage();
		
		FontRenderer fontRenderer = Minecraft.getInstance().font;
		
		if(this.getMessage() != null && !this.getMessage().getString().isEmpty())
		{
			ITextComponent leftArrow = this.isHoveringLeft(mouseX, mouseY) ? TextUtils.ARROW_LEFT_BOLD : TextUtils.ARROW_LEFT;
			ITextComponent rightArrow = this.isHoveringRight(mouseX, mouseY) ? TextUtils.ARROW_RIGHT_BOLD : TextUtils.ARROW_RIGHT;
			
			int maxWidth = Math.max(0, this.width - fontRenderer.width("<   >"));
			int spaceWidth = fontRenderer.width(" ");
			
			ITextComponent display = TextUtils.stripText((IFormattableTextComponent) this.getMessage(), maxWidth, fontRenderer);
			int yPos = this.y + (this.height - 8) / 2;
			
			AbstractGui.drawCenteredString(matrix, fontRenderer, display, this.x + this.width / 2, yPos, this.getFGColor());
			AbstractGui.drawCenteredString(matrix, fontRenderer, leftArrow, this.x + this.width / 2 - maxWidth / 2 - spaceWidth, yPos, this.getFGColor());
			AbstractGui.drawCenteredString(matrix, fontRenderer, rightArrow, this.x + this.width / 2 + maxWidth / 2 + spaceWidth, yPos, this.getFGColor());
		}
	}
	
	@Override
	public void renderTooltip(Screen screen, MatrixStack matrix, int mouseX, int mouseY)
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
	
	@OnlyIn(Dist.CLIENT)
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
