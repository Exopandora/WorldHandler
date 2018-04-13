package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.gui.button.logic.ISliderResponder;
import exopandora.worldhandler.gui.button.storage.ButtonStorage;
import exopandora.worldhandler.gui.button.storage.SliderStorage;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSlider<T> extends GuiButton
{
	private boolean isMouseDown;
	private boolean isActive;
	
	private final Object key;
	private final String name;
	private final ISliderResponder responder;
	private final Container frame;
	private final ButtonStorage<SliderStorage> storage;
	
	public GuiSlider(Content container, Container frame, Object key, int x, int y, int width, int height, String name, double min, double max, double start, ISliderResponder responder)
	{
		super(Integer.MAX_VALUE, x, y, width, height, null);
		this.frame = frame;
		this.key = key;
		this.name = name;
        this.responder = responder;
		this.storage = container.getStorage(key);
		this.initStorage(Math.round(min), Math.round(max), Math.round(start));
		this.displayString = this.getDisplayString();
	}
	
	private void initStorage(double min, double max, double start)
	{
		if(this.storage.getObject() == null)
		{
			if(min == max)
			{
				this.storage.setObject(new SliderStorage(min, max, 0.0D));
			}
			else
			{
				this.storage.setObject(new SliderStorage(min, max, (start - min) / (max - min)));
			}
		}
		else if(this.storage.getObject().getMin() != min || this.storage.getObject().getMax() != max)
		{
			this.storage.setObject(new SliderStorage(min, max, (int) MathHelper.clamp(this.getValue(), min, max)));
		}
	}
	
	private void setPosition(double position)
	{
		this.storage.getObject().setPosition(position);
	}
	
	private double getPosition()
	{
		return this.storage.getObject().getPosition();
	}
	
	private void setValue(int value)
	{
		this.storage.getObject().setValue(value);
	}
	
	private int getValue()
	{
		return this.storage.getObject().getValue();
	}
	
	private String getDisplayString()
	{
		return this.responder.getText(this.key, I18n.format(this.name), this.getValue());
	}

	@Override
	protected int getHoverState(boolean mouseOver)
	{
		return 0;
	}
	
	@Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
    {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
		GlStateManager.color((float) ConfigSkin.getButtonRed() / 255, (float) ConfigSkin.getButtonGreen() / 255, (float) ConfigSkin.getButtonBlue() / 255, (float) ConfigSkin.getButtonAlpha() / 255);
        
    	Minecraft.getMinecraft().renderEngine.bindTexture(ResourceHelper.getButtonTexture());
    	
        if(ConfigSkin.getTextureType().equals("resourcepack"))
        {
            this.drawTexturedModalRect(this.x, this.y, 0, 46, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46, this.width / 2, this.height);
        }
        else
        {
            this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 0, this.width / 2, this.height);
        }
        
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        
        this.mouseDragged(minecraft, mouseX, mouseY);
    }
	
	private void update(int mouseX, int mouseY)
	{
		float sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
		
		if(sliderValue < 0.0F)
		{
			sliderValue = 0.0F;
		}
		
		if(sliderValue > 1.0F)
		{
			sliderValue = 1.0F;
		}
		
		this.setPosition(sliderValue);
		this.displayString = this.getDisplayString();
		this.responder.setValue(this.key, this.getValue());
	}
	
	@Override
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			if(this.isMouseDown)
			{
				this.update(mouseX, mouseY);
			}
			
            int textureXOffset = ConfigSkin.getTextureType().equals("resourcepack") ? 0 : -46;
			
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
    		GlStateManager.color((float) ConfigSkin.getButtonRed() / 255, (float) ConfigSkin.getButtonGreen() / 255, (float) ConfigSkin.getButtonBlue() / 255, (float) ConfigSkin.getButtonAlpha() / 255);
    		
    		this.drawTexturedModalRect(this.x + (int) (this.getPosition() * (float) (this.width - 8)), this.y, 0, 66 + textureXOffset, 4, 20);
			this.drawTexturedModalRect(this.x + (int) (this.getPosition() * (float) (this.width - 8)) + 4, this.y, 196, 66 + textureXOffset, 4, 20);
			
    		GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            
            int color = 0xE0E0E0;
            
            if(!this.enabled)
            {
                color = 0xA0A0A0;
            }
            else if(this.hovered)
            {
                color = 0xFFFFA0;
            }
            
            this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
		}
		
		this.isActive = true;
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if(super.mousePressed(mc, mouseX, mouseY) && this.isActive)
		{
			this.update(mouseX, mouseY);
			this.isMouseDown = true;
			
			return true;
		}
		
		return false;
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY)
	{
		this.isMouseDown = false;
        
        if(this.frame != null)
        {
        	this.frame.initGui();
        }
	}
}