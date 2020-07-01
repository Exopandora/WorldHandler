package exopandora.worldhandler.gui.button;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.impl.ILogicMapped;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
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
		super(x, y, widthIn, heightIn, StringTextComponent.field_240750_d_, null, null);
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
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		this.func_230441_a_(matrix, Minecraft.getInstance(), mouseX, mouseY); //renderBg
		this.updateMessage();
		
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		if(this.func_230458_i_() != null && !this.func_230458_i_().getString().isEmpty())
		{
			ITextComponent leftArrow = this.isHoveringLeft(mouseX, mouseY) ? TextUtils.ARROW_LEFT_BOLD : TextUtils.ARROW_LEFT;
			ITextComponent rightArrow = this.isHoveringRight(mouseX, mouseY) ? TextUtils.ARROW_RIGHT_BOLD : TextUtils.ARROW_RIGHT;
			
			int maxWidth = Math.max(0, this.field_230688_j_ - fontRenderer.getStringWidth("<   >"));
			int spaceWidth = fontRenderer.getStringWidth(" ");
			
			ITextComponent display = TextUtils.stripText((IFormattableTextComponent) this.func_230458_i_(), maxWidth, fontRenderer);
			int yPos = this.field_230691_m_ + (this.field_230689_k_ - 8) / 2;
			
			this.func_238472_a_(matrix, fontRenderer, display, this.field_230690_l_ + this.field_230688_j_ / 2, yPos, this.getFGColor());
			this.func_238472_a_(matrix, fontRenderer, leftArrow, this.field_230690_l_ + this.field_230688_j_ / 2 - maxWidth / 2 - spaceWidth, yPos, this.getFGColor());
			this.func_238472_a_(matrix, fontRenderer, rightArrow, this.field_230690_l_ + this.field_230688_j_ / 2 + maxWidth / 2 + spaceWidth, yPos, this.getFGColor());
		}
	}
	
	@Override
	public void renderTooltip(MatrixStack matrix, int mouseX, int mouseY)
	{
		this.tooltip = this.logic.formatTooltip(this.items.get(this.persistence.getIndex()), this.persistence.getIndex() + 1, this.items.size());
		super.renderTooltip(matrix, mouseX, mouseY);
	}
	
	@Override
	public void func_230982_a_(double mouseX, double mouseY) //onClick
	{
		int max = this.items.size() - 1;
		int index = this.persistence.getIndex();
		
		if(this.isHoveringLeft(mouseX, mouseY))
		{
			if(Screen.func_231173_s_())
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
			if(Screen.func_231173_s_())
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
		this.func_238482_a_(this.logic.translate(this.items.get(this.persistence.getIndex()))); //setMessage
	}
	
	private boolean isHoveringLeft(double mouseX, double mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.field_230690_l_ && mouseX < this.field_230690_l_ + Math.ceil(this.field_230688_j_ / 2);
	}
	
	private boolean isHoveringRight(double mouseX, double mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.field_230690_l_ + Math.ceil(this.field_230688_j_ / 2) && mouseX < this.field_230690_l_ + this.field_230688_j_;
	}
	
	private boolean isHoveringVertical(double mouseY)
	{
		return mouseY >= this.field_230691_m_ && mouseY < this.field_230691_m_ + this.field_230689_k_;
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
