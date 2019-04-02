package exopandora.worldhandler.gui.content.element.impl;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.format.EnumColor;
import exopandora.worldhandler.format.text.ColoredString;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.logic.ILogicColorMenu;
import exopandora.worldhandler.gui.logic.ILogicMapped;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementColorMenu extends Element
{
	private static final List<EnumColor> COLORS = new ArrayList<EnumColor>();
	
	static
	{
		COLORS.add(EnumColor.DEFAULT);
		COLORS.add(EnumColor.YELLOW);
		COLORS.add(EnumColor.GOLD);
		COLORS.add(EnumColor.DARK_RED);
		COLORS.add(EnumColor.RED);
		COLORS.add(EnumColor.LIGHT_PURPLE);
		COLORS.add(EnumColor.DARK_PURPLE);
		COLORS.add(EnumColor.BLUE);
		COLORS.add(EnumColor.DARK_BLUE);
		COLORS.add(EnumColor.DARK_AQUA);
		COLORS.add(EnumColor.AQUA);
		COLORS.add(EnumColor.GREEN);
		COLORS.add(EnumColor.DARK_GREEN);
		COLORS.add(EnumColor.BLACK);
		COLORS.add(EnumColor.DARK_GRAY);
		COLORS.add(EnumColor.GRAY);
		COLORS.add(EnumColor.WHITE);
	}
	
	private GuiTextFieldTooltip textField;
	private final ColoredString string;
	private final ILogicColorMenu logic;
	private final String translationKey;
	
	public ElementColorMenu(int x, int y, String translationKey, ColoredString string)
	{
		this(x, y, translationKey, string, new ILogicColorMenu(){});
	}
	
	public ElementColorMenu(int x, int y, String translationKey, ColoredString string, ILogicColorMenu logic)
	{
		super(x, y);
		this.translationKey = translationKey;
		this.string = string;
		this.logic = logic;
	}
	
	@Override
	public void initGui(Container container)
	{
		this.textField = new GuiTextFieldTooltip(this.x + 118, this.y, 114, 20, I18n.format(this.translationKey));
		this.textField.setValidator(this.logic::validate);
		this.textField.setTextFormatter(this.string::textFormatter);
		this.textField.setText(this.string.getText());
		this.textField.setTextAcceptHandler((id, text) ->
		{
			this.string.setText(text);
		});
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(this.textField);
		
		if(this.logic.doDrawButtons())
		{
			container.add(new GuiButtonList<EnumColor>(this.x + 118, this.y + 24, COLORS, 114, 20, container, new ILogicMapped<EnumColor>()
			{
				@Override
				public String translate(EnumColor item)
				{
					return item + I18n.format("gui.worldhandler.color") + ": " + I18n.format("gui.worldhandler.color." + item.getFormat());
				}
				
				@Override
				public String toTooltip(EnumColor item)
				{
					return null;
				}
				
				@Override
				public String formatTooltip(EnumColor item, int index, int max)
				{
					return null;
				}
				
				@Override
				public void onClick(EnumColor item)
				{
					ElementColorMenu.this.string.setColor(item);
				}
				
				@Override
				public String getId()
				{
					return ElementColorMenu.this.logic.getId();
				}
			}));
			
			container.add(new GuiButtonBase(this.x + 118, this.y + 48, 20, 20, (this.string.isItalic() ? ChatFormatting.ITALIC : ChatFormatting.RESET) + "I", () ->
			{
				this.string.setItalic(!this.string.isItalic());
				container.initGui();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 - 1, this.y + 48, 20, 20, (this.string.isBold() ? ChatFormatting.BOLD : ChatFormatting.RESET) + "B", () ->
			{
				this.string.setBold(!this.string.isBold());
				container.initGui();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, (this.string.isUnderlined() ? ChatFormatting.UNDERLINE : ChatFormatting.RESET) + "U", () ->
			{
				this.string.setUnderlined(!this.string.isUnderlined());
				container.initGui();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, (this.string.isStriked() ? ChatFormatting.STRIKETHROUGH : ChatFormatting.RESET) + "S", () ->
			{
				this.string.setStriked(!this.string.isStriked());
				container.initGui();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, (this.string.isObfuscated() ? ChatFormatting.OBFUSCATED : ChatFormatting.RESET) + "O", () ->
			{
				this.string.setObfuscated(!this.string.isObfuscated());
				container.initGui();
			}));
		}
	}
	
	@Override
	public void tick()
	{
		this.textField.tick();
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks)
	{
		this.textField.drawTextField(mouseX, mouseY, partialTicks);
	}
}
