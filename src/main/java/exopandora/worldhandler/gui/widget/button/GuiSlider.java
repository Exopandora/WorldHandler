package exopandora.worldhandler.gui.widget.button;

import java.util.Objects;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.ILogic;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class GuiSlider extends GuiButtonBase
{
	private static final ResourceLocation SLIDER_SPRITE = new ResourceLocation("widget/slider");
	private static final ResourceLocation SLIDER_HANDLE_SPRITE = new ResourceLocation("widget/slider_handle");
	private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_handle_highlighted");
	private final Persistence persistence;
	private final ILogicSlider logic;
	private final Container container;
	
	public GuiSlider(int x, int y, int width, int height, double min, double max, double start, Container container, ILogicSlider logic)
	{
		super(x, y, width, height, Component.empty(), null);
		this.logic = Objects.requireNonNull(logic);
		this.container = Objects.requireNonNull(container);
		this.persistence = this.container.getContent().getPersistence(this.logic.getId(), () -> new Persistence(min, max, min == max ? 0.0 : ((start - min) / (max - min))));
		this.persistence.validate(min, max);
		this.logic.onChangeSliderValue(this.persistence.getIntValue());
		this.updateDisplayString();
	}
	
	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton(guiGraphics);
		guiGraphics.blitSprite(this.getSprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		guiGraphics.blitSprite(this.getHandleSprite(), this.getX() + (int) (this.persistence.getValue() * (float) (this.width - 8)), this.getY(), 8, this.getHeight());
		RenderUtils.resetColor(guiGraphics);
		RenderSystem.disableBlend();
	}
	
	protected ResourceLocation getSprite()
	{
		return SLIDER_SPRITE;
	}
	
	protected ResourceLocation getHandleSprite()
	{
		return this.isHoveredOrFocused() ? SLIDER_HANDLE_HIGHLIGHTED_SPRITE : SLIDER_HANDLE_SPRITE;
	}
	
	@Override
	public void onClick(double mouseX, double mouseY)
	{
		this.updateSlider(mouseX);
	}
	
	@Override
	protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY)
	{
		this.updateSlider(mouseX);
		super.onDrag(mouseX, mouseY, deltaX, deltaY);
	}
	
	protected void updateSlider(double mouseX)
	{
		this.persistence.setValue((mouseX - (this.getX() + 4)) / (float) (this.width - 8));
		
		if(this.persistence.getValue() < 0.0F)
		{
			this.persistence.setValue(0.0F);
		}
		
		if(this.persistence.getValue() > 1.0F)
		{
			this.persistence.setValue(1.0F);
		}
		
		this.updateDisplayString();
		this.logic.onChangeSliderValue(this.persistence.getIntValue());
	}
	
	private void updateDisplayString()
	{
		int value = this.persistence.getIntValue();
		MutableComponent suffix = this.logic.formatValue(value).append(this.logic.formatSuffix(value));
		Font fontRenderer = Minecraft.getInstance().font;
		MutableComponent text = TextUtils.stripText(this.logic.formatPrefix(value), this.width - fontRenderer.width(suffix) - 2, fontRenderer).append(suffix);
		this.setMessage(text);
	}
	
	public static interface ILogicSlider extends ILogic
	{
		MutableComponent formatPrefix(int value);
		MutableComponent formatSuffix(int value);
		MutableComponent formatValue(int value);
		
		void onChangeSliderValue(int value);
	}
	
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
		
		public int getIntValue()
		{
			return (int) Math.round(this.min + (this.max - this.min) * this.value);
		}
		
		public void setIntValue(int value)
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
