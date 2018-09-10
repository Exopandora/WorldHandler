package exopandora.worldhandler.gui.content.element.impl;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.format.text.ColoredString;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.logic.ColorListButtonLogic;
import exopandora.worldhandler.gui.button.persistence.ButtonValue;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.logic.ILogicColorMenu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class ElementColorMenu extends Element
{
	private GuiTextFieldTooltip textField;
	private GuiButtonList colorList;
	private final Content content;
	private final ColoredString string;
	private final int[] ids;
	private final ILogicColorMenu logic;
	private final String translationKey;
	
	public ElementColorMenu(Content content, int x, int y, String translationKey, ColoredString string, int[] ids)
	{
		this(content, x, y, translationKey, string, ids, new ILogicColorMenu(){});
	}
	
	public ElementColorMenu(Content content, int x, int y, String translationKey, ColoredString string, int[] ids, ILogicColorMenu logic)
	{
		super(x, y);
		this.content = content;
		this.translationKey = translationKey;
		this.string = string;
		this.ids = ids;
		this.logic = logic;
	}
	
	@Override
	public void initGui(Container container)
	{
		if(this.logic.drawTextfield())
		{
			this.textField = new GuiTextFieldTooltip(this.x + 118, this.y, 114, 20, I18n.format(this.translationKey));
			this.textField.setValidator(this.logic.getValidator());
			this.textField.setText(this.string.getTextFieldString());
		}
	}
	
	@Override
	public void initButtons(Container container)
	{
		if(this.logic.drawButtons())
		{
			container.add(this.colorList = new GuiButtonList(this.ids[0], this.x + 118, this.y + 24, 114, 20, this.content, new ColorListButtonLogic()
			{
				@Override
				public void actionPerformed(Container container, GuiButton button, ButtonValue<Integer> values)
				{
					string.setColor(values.getIndex());
				}
				
				@Override
				public String getId()
				{
					return logic.getId();
				}
			}));
			
			container.add(new GuiButtonWorldHandler(this.ids[1], this.x + 118, this.y + 48, 20, 20, (this.string.isItalic() ? ChatFormatting.ITALIC : ChatFormatting.RESET) + "I"));
			container.add(new GuiButtonWorldHandler(this.ids[2], this.x + 118 + 24 - 1, this.y + 48, 20, 20, (this.string.isBold() ? ChatFormatting.BOLD : ChatFormatting.RESET) + "B"));
			container.add(new GuiButtonWorldHandler(this.ids[3], this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, (this.string.isUnderlined() ? ChatFormatting.UNDERLINE : ChatFormatting.RESET) + "U"));
			container.add(new GuiButtonWorldHandler(this.ids[4], this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, (this.string.isStriked() ? ChatFormatting.STRIKETHROUGH : ChatFormatting.RESET) + "S"));
			container.add(new GuiButtonWorldHandler(this.ids[5], this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, (this.string.isObfuscated() ? ChatFormatting.OBFUSCATED : ChatFormatting.RESET) + "O"));
		}
	}
	
	@Override
	public boolean actionPerformed(Container container, GuiButton button)
	{
		if(button.id == this.ids[0])
		{
			this.colorList.actionPerformed(container, button);
			container.initGui();
			return true;
		}
		else if(button.id == this.ids[1])
		{
			this.string.setItalic(!this.string.isItalic());
			container.initGui();
			return true;
		}
		else if(button.id == this.ids[2])
		{
			this.string.setBold(!this.string.isBold());
			container.initGui();
			return true;
		}
		else if(button.id == this.ids[3])
		{
			this.string.setUnderlined(!this.string.isUnderlined());
			container.initGui();
			return true;
		}
		else if(button.id == this.ids[4])
		{
			this.string.setStriked(!this.string.isStriked());
			container.initGui();
			return true;
		}
		else if(button.id == this.ids[5])
		{
			this.string.setObfuscated(!this.string.isObfuscated());
			container.initGui();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void draw()
	{
		if(this.logic.drawTextfield())
		{
			this.textField.drawTextBox();
		}
	}
	
	@Override
	public void keyTyped(Container container, char charTyped, int keyCode)
	{
		if(this.logic.drawTextfield())
		{
			if(this.textField.canType(charTyped))
			{
				if(this.textField.textboxKeyTyped(charTyped, keyCode))
				{
					this.string.setText(this.textField.getText());
					container.initButtons();
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.logic.drawTextfield())
		{
			this.textField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
}
