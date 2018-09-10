package exopandora.worldhandler.gui.button;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.persistence.ButtonValue;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonList<T> extends GuiButtonWorldHandler
{
	private final IListButtonLogic<T> logic;
	private final ButtonValue<T> persistence;
	private int mouseX;
	private int mouseY;
	
	public GuiButtonList(int id, int x, int y, int width, int height, Content content, IListButtonLogic<T> logic)
	{
		this(id, x, y, width, height, null, content, logic);
	}
	
	public GuiButtonList(int id, int x, int y, int width, int height, EnumTooltip tooltipType, Content content, IListButtonLogic<T> logic)
	{
		super(id, x, y, width, height, null, null, tooltipType);
		this.logic = logic;
		this.persistence = content.getPersistence(this.logic.getId());
		this.updatePersistenceObject();
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
	{
		super.drawBackground(minecraft, mouseX, mouseY);
		
		if(this.visible)
		{
			FontRenderer fontRenderer = minecraft.fontRenderer;
			
			this.mouseX = mouseX;
			this.mouseY = mouseY;
			
			this.displayString = this.logic.getDisplayString(this.persistence);
			
			if(this.displayString != null && !this.displayString.isEmpty())
			{
				String leftArrow = this.isHoveringLeft(mouseX, mouseY) ? ChatFormatting.BOLD + "<" + ChatFormatting.RESET : "<";
				String rightArrow = this.isHoveringRight(mouseX, mouseY) ? ChatFormatting.BOLD + ">" + ChatFormatting.RESET : ">";
				
				int leftArrowWidth = fontRenderer.getStringWidth(leftArrow);
				int rightArrowWidth = fontRenderer.getStringWidth(rightArrow);
				
	            int maxWidth = Math.max(0, this.width - (fontRenderer.getStringWidth("<   >")));
	            int spaceWidth = fontRenderer.getCharWidth(' ');
	            
	            String display = TextFormatting.shortenString(this.displayString, maxWidth, fontRenderer);
	            int displayWidth = fontRenderer.getStringWidth(display);
	            int yPos = this.y + (this.height - 8) / 2;
	            
				this.drawCenteredString(fontRenderer, display, this.x + this.width / 2, yPos, this.getTextColor());
				this.drawCenteredString(fontRenderer, leftArrow, this.x + this.width / 2 - maxWidth / 2 - spaceWidth, yPos, this.getTextColor());
				this.drawCenteredString(fontRenderer, rightArrow, this.x + this.width / 2 + maxWidth / 2 + spaceWidth, yPos, this.getTextColor());
			}
			
			this.isActive = true;
		}
	}
	
	@Override
	public void drawTooltip(int mouseX, int mouseY, int width, int height)
	{
		if(this.tooltipType != null)
		{
			this.displayTooltip = this.logic.getTooltipString(this.persistence);
		}
		
		super.drawTooltip(mouseX, mouseY, width, height);
	}
	
	private boolean isHoveringLeft(int mouseX, int mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.x && mouseX < this.x + Math.ceil(this.width / 2);
	}
	
	private boolean isHoveringRight(int mouseX, int mouseY)
	{
		return this.isHoveringVertical(mouseY) && mouseX >= this.x + Math.ceil(this.width / 2) && mouseX < this.x + this.width;
	}
	
	private boolean isHoveringVertical(int mouseY)
	{
		return mouseY >= this.y && mouseY < this.y + this.height;
	}
	
	public void actionPerformed(Container container, GuiButton button)
	{
		int max = this.logic.getMax() - 1;
		int index = this.persistence.getIndex();
		
		if(this.isHoveringLeft(this.mouseX, this.mouseY))
		{
			if(GuiScreen.isShiftKeyDown())
			{
				if(index < 10)
				{
					this.persistence.setIndex(max - (9 - index));
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
		else if(this.isHoveringRight(this.mouseX, this.mouseY))
		{
			if(GuiScreen.isShiftKeyDown())
			{
				if(index > max - 10)
				{
					this.persistence.setIndex(9 - (max - index));
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
		
		this.updatePersistenceObject();
		this.logic.actionPerformed(container, button, this.persistence);
	}
	
	private void updatePersistenceObject()
	{
		this.persistence.setObject(this.logic.getObject(this.persistence.getIndex()));
	}
	
	public IListButtonLogic<T> getLogic()
	{
		return this.logic;
	}
}
