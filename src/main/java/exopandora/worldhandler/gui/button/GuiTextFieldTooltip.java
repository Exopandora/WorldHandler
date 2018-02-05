package exopandora.worldhandler.gui.button;

import com.google.common.base.Predicate;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTextFieldTooltip
{
	private GuiTextField textfield;
	private String display;
	
	public GuiTextFieldTooltip(int x, int y, int width, int height, String display)
	{
		this.textfield = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, x, y, width, height);
		this.textfield.setMaxStringLength(Integer.MAX_VALUE);
		this.display = display;
	}
	
	public GuiTextFieldTooltip(int x, int y, int width, int height)
	{
		this(x, y, width, height, null);
	}
	
	public void setGuiResponder(GuiPageButtonList.GuiResponder responder)
	{
		this.textfield.setGuiResponder(responder);
	}
	
	public void updateCursorCounter()
	{
		this.textfield.updateCursorCounter();
	}
	
	public void setText(String text)
	{
		this.textfield.setText(text);
	}
	
	public String getText()
	{
		return this.textfield.getText();
	}
	
	public boolean isEmpty()
	{
		if(this.textfield.getText() != null)
		{
			return this.textfield.getText().matches("(\u00A7[a-f0-9k-or])+");
		}
		
		return true;
	}
	
	public boolean canType(char charTyped)
	{
		return (!this.isEmpty() || charTyped != '\b') && this.textfield.isFocused();
	}
	
	public String getSelectedText()
	{
		return this.textfield.getSelectedText();
	}
	
	public void setValidator(Predicate<String> validator)
	{
		this.textfield.setValidator(validator);;
	}
	
	public void writeText(String text)
	{
		this.textfield.writeText(text);
	}
	
	public void deleteWords(int num)
	{
		this.textfield.deleteWords(num);
	}
	
	public void deleteFromCursor(int num)
	{
		this.textfield.deleteFromCursor(num);
	}
	
	public int getId()
	{
		return this.textfield.getId();
	}
	
	public int getNthWordFromCursor(int numWords)
	{
		return this.textfield.getNthWordFromCursor(numWords);
	}
	
	public int getNthWordFromPos(int n, int pos)
	{
		return this.textfield.getNthWordFromPos(n, pos);
	}
	
	public int getNthWordFromPosWS(int n, int pos, boolean skipWs)
	{
		return this.textfield.getNthWordFromPosWS(n, pos, skipWs);
	}
	
	public void moveCursorBy(int num)
	{
		this.textfield.moveCursorBy(num);
	}
	
	public void setCursorPosition(int pos)
	{
		this.textfield.setCursorPosition(pos);
	}
	
	public void setCursorPositionZero()
	{
		this.textfield.setCursorPositionZero();
	}
	
	public void setCursorPositionEnd()
	{
		this.textfield.setCursorPositionEnd();
	}
	
	public boolean textboxKeyTyped(char typedChar, int keyCode)
	{
		return this.textfield.textboxKeyTyped(typedChar, keyCode);
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.textfield.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void drawTextBox()
	{
		this.textfield.drawTextBox();
		
		if(this.textfield.getVisible())
		{
            int x = this.textfield.getEnableBackgroundDrawing() ? this.textfield.x + 4 : this.textfield.x;
            int y = this.textfield.getEnableBackgroundDrawing() ? this.textfield.y + (this.textfield.height - 8) / 2 : this.textfield.y;
			
			if(ChatFormatting.stripFormatting(this.textfield.getText()).isEmpty() && !this.textfield.isFocused() && this.display != null)
			{
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.display, (float) x, (float) y, 0x7F7F7F);
			}
		}
	}
	
	public void setMaxStringLength(int length)
	{
		this.textfield.setMaxStringLength(length);
	}
	
	public int getMaxStringLength()
	{
		return this.textfield.getMaxStringLength();
	}
	
	public int getCursorPosition()
	{
		return this.textfield.getCursorPosition();
	}
	
	public boolean getEnableBackgroundDrawing()
	{
		return this.textfield.getEnableBackgroundDrawing();
	}
	
	public void setEnableBackgroundDrawing(boolean enableBackgroundDrawing)
	{
		this.textfield.setEnableBackgroundDrawing(enableBackgroundDrawing);
	}
	
	public void setTextColor(int color)
	{
		this.textfield.setTextColor(color);
	}
	
	public void setDisabledTextColour(int color)
	{
		this.textfield.setDisabledTextColour(color);
	}
	
	public void setFocused(boolean focused)
	{
		this.textfield.setFocused(focused);
	}
	
	public boolean isFocused()
	{
		return this.textfield.isFocused();
	}
	
	public void setEnabled(boolean enabled)
	{
		this.textfield.setEnabled(enabled);
	}
	
	public int getSelectionEnd()
	{
		return this.textfield.getSelectionEnd();
	}
	
	public int getWidth()
	{
		return this.textfield.getWidth();
	}
	
	public void setSelectionPos(int position)
	{
		this.textfield.setSelectionPos(position);
	}
	
	public void setCanLoseFocus(boolean canLoseFocus)
	{
		this.textfield.setCanLoseFocus(canLoseFocus);
	}
	
	public boolean getVisible()
	{
		return this.textfield.getVisible();
	}
	
	public void setVisible(boolean visible)
	{
		this.textfield.setVisible(visible);
	}
	
	public void setDisplay(String display)
	{
		this.display = display;
	}
	
	public String getDisplay()
	{
		return this.display;
	}
	
	public void setPosition(int x, int y)
	{
		this.textfield.x = x;
		this.textfield.y = y;
	}
	
	public void setWidth(int width)
	{
		this.textfield.width = width;
	}
	
	public void setHeight(int height)
	{
		this.textfield.height = height;
	}
}
