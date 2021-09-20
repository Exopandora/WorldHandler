package exopandora.worldhandler.gui.widget.button;

import java.util.Objects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.ILogic;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class GuiSlider extends GuiButtonBase
{
	private final Persistence persistence;
	private final ILogicSlider logic;
	private final Container container;
	
	public GuiSlider(int x, int y, int widthIn, int heightIn, double min, double max, double start, Container container, ILogicSlider logic)
	{
		super(x, y, widthIn, heightIn, TextComponent.EMPTY, null);
		this.logic = Objects.requireNonNull(logic);
		this.container = Objects.requireNonNull(container);
		this.persistence = this.container.getContent().getPersistence(this.logic.getId(), () -> new Persistence(min, max, min == max ? 0.0 : ((start - min) / (max - min))));
		this.persistence.validate(min, max);
		this.logic.onChangeSliderValue(this.persistence.getValueInt());
		this.updateDisplayString();
	}
	
	@Override
	protected void renderBg(PoseStack matrix, Minecraft minecraft, int mouseX, int mouseY)
	{
		super.renderBg(matrix, minecraft, mouseX, mouseY);
		
		int hovered = super.getYImage(this.isHovered());
		int textureOffset = (Config.getSkin().getTextureType().equals("resourcepack") ? 46 : 0) + hovered * 20;
		
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton();
		
		this.blit(matrix, this.x + (int) (this.persistence.getValue() * (float) (this.width - 8)), this.y, 0, textureOffset, 4, 20);
		this.blit(matrix, this.x + (int) (this.persistence.getValue() * (float) (this.width - 8)) + 4, this.y, 196, textureOffset, 4, 20);
		
		RenderSystem.disableBlend();
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
		this.persistence.setValue((mouseX - (this.x + 4)) / (float) (this.width - 8));
		
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
	
	@Override
	protected int getYImage(boolean mouseOver)
	{
		return 0;
	}
	
	private void updateDisplayString()
	{
		int value = this.persistence.getValueInt();
		MutableComponent suffix = this.logic.formatValue(value).append(this.logic.formatSuffix(value));
		Font fontRenderer = Minecraft.getInstance().font;
		MutableComponent text = TextUtils.stripText(this.logic.formatPrefix(value), this.width - fontRenderer.width(suffix), fontRenderer).append(suffix);
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
