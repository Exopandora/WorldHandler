package exopandora.worldhandler.gui.button;

import java.util.Objects;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.logic.ILogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiSlider extends GuiButtonBase
{
	private final Persistence persistence;
	private final ILogicSlider logic;
	private final Container container;
	
	private boolean dragging;
	
	public GuiSlider(int x, int y, int widthIn, int heightIn, double min, double max, double start, Container container, ILogicSlider logic)
	{
		this(0, x, y, widthIn, heightIn, min, max, start, container, logic);
	}
	
	public GuiSlider(int id, int x, int y, int widthIn, int heightIn, double min, double max, double start, Container container, ILogicSlider logic)
	{
		super(id, x, y, widthIn, heightIn, null, null);
		this.logic = Objects.requireNonNull(logic);
		this.container = Objects.requireNonNull(container);
		this.persistence = this.container.getContent().getPersistence(this.logic.getId(), () -> new Persistence(min, max, min == max ? 0.0 : ((start - min) / (max - min))));
		this.persistence.validate(min, max);
		this.logic.onChangeSliderValue(this.persistence.getValueInt());
		this.updateDisplayString();
	}
	
	@Override
	protected void renderBg(Minecraft minecraft, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			if(this.dragging)
			{
				this.persistence.setValue((mouseX - (this.x + 4)) / (float) (this.width - 8));
				this.updateSlider();
			}
			
			int xOffset = Config.getSkin().getTextureType().equals("resourcepack") ? 0 : -46;
			
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.color4f(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
			
			this.drawTexturedModalRect(this.x + (int) (this.persistence.getValue() * (float) (this.width - 8)), this.y, 0, 66 + xOffset, 4, 20);
			this.drawTexturedModalRect(this.x + (int) (this.persistence.getValue() * (float) (this.width - 8)) + 4, this.y, 196, 66 + xOffset, 4, 20);
			
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	public void onClick(double mouseX, double mouseY)
	{
		this.persistence.setValue((mouseX - (this.x + 4)) / (this.width - 8));
		this.updateSlider();
		this.dragging = true;
	}
	
	@Override
	public void onRelease(double mouseX, double mouseY)
	{
		super.onRelease(mouseX, mouseY);
		this.dragging = false;
	}
	
	@Override
	protected int getHoverState(boolean mouseOver)
	{
		return 0;
	}
	
	private void updateSlider()
	{
		if(this.persistence.getValue() < 0.0F)
		{
			this.persistence.setValue(0.0F);
		}
		
		if(this.persistence.getValue() > 1.0F)
		{
			this.persistence.setValue(1.0F);
		}
		
		this.updateDisplayString();
		this.logic.onChangeSliderValue(this.persistence.getValueInt());
	}
	
	private void updateDisplayString()
	{
		int value = this.persistence.getValueInt();
		String suffix = this.logic.formatValue(value) + this.logic.formatSuffix(value);
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		this.displayString = TextFormatting.shortenString(this.logic.formatPrefix(value), this.width - fontRenderer.getStringWidth(suffix), fontRenderer) + suffix;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static interface ILogicSlider extends ILogic
	{
		String formatPrefix(int value);
		String formatSuffix(int value);
		String formatValue(int value);
		
		void onChangeSliderValue(int value);
	}
	
	@OnlyIn(Dist.CLIENT)
	public class Persistence
	{
		private double min;
		private double max;
		private double value;
		
		private Persistence(double min, double max)
		{
			this.min = min;
			this.max = max;
		}
		
		public Persistence(double min, double max, double value)
		{
			this(min, max);
			this.value = value;
		}
		
		public double getMin()
		{
			return this.min;
		}
		
		public double getMax()
		{
			return this.max;
		}
		
		public double getValue()
		{
			return this.value;
		}
		
		public void setValue(double value)
		{
			this.value = value;
		}
		
		public int getValueInt()
		{
			return (int) Math.round(this.min + (this.max - this.min) * this.value);
		}
		
		public void setValueInt(int value)
		{
			this.value = this.intToValue(value);
		}
		
		public void validate(double min, double max)
		{
			if(this.getMin() != min || this.getMax() != max)
			{
				this.min = min;
				this.max = max;
			}
		}
		
		private double intToValue(int value)
		{
			if(this.min == this.max)
			{
				return 0;
			}
			
			return (value - this.min) / (this.max - this.min);
		}
	}
}
