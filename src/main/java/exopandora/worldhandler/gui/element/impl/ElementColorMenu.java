package exopandora.worldhandler.gui.element.impl;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.element.Element;
import exopandora.worldhandler.gui.logic.ILogicColorMenu;
import exopandora.worldhandler.gui.logic.ILogicMapped;
import exopandora.worldhandler.util.MutableStringTextComponent;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementColorMenu extends Element
{
	private static final List<TextFormatting> COLORS = new ArrayList<TextFormatting>();
	
	static
	{
		COLORS.add(TextFormatting.RESET);
		COLORS.add(TextFormatting.YELLOW);
		COLORS.add(TextFormatting.GOLD);
		COLORS.add(TextFormatting.DARK_RED);
		COLORS.add(TextFormatting.RED);
		COLORS.add(TextFormatting.LIGHT_PURPLE);
		COLORS.add(TextFormatting.DARK_PURPLE);
		COLORS.add(TextFormatting.BLUE);
		COLORS.add(TextFormatting.DARK_BLUE);
		COLORS.add(TextFormatting.DARK_AQUA);
		COLORS.add(TextFormatting.AQUA);
		COLORS.add(TextFormatting.GREEN);
		COLORS.add(TextFormatting.DARK_GREEN);
		COLORS.add(TextFormatting.BLACK);
		COLORS.add(TextFormatting.DARK_GRAY);
		COLORS.add(TextFormatting.GRAY);
		COLORS.add(TextFormatting.WHITE);
	}
	
	private GuiTextFieldTooltip textField;
	private final MutableStringTextComponent string;
	private final ILogicColorMenu logic;
	private final String translationKey;
	
	public ElementColorMenu(int x, int y, String translationKey, MutableStringTextComponent string)
	{
		this(x, y, translationKey, string, new ILogicColorMenu(){});
	}
	
	public ElementColorMenu(int x, int y, String translationKey, MutableStringTextComponent string, ILogicColorMenu logic)
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
		this.textField.setTextFormatter(this.string::formatter);
		this.textField.setText(this.string.getText());
		this.textField.setResponder(text ->
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
			container.add(new GuiButtonList<TextFormatting>(this.x + 118, this.y + 24, COLORS, 114, 20, container, new ILogicMapped<TextFormatting>()
			{
				@Override
				public String translate(TextFormatting item)
				{
					return item + I18n.format("gui.worldhandler.color") + ": " + I18n.format("gui.worldhandler.color." + item.getFriendlyName());
				}
				
				@Override
				public String toTooltip(TextFormatting item)
				{
					return null;
				}
				
				@Override
				public String formatTooltip(TextFormatting item, int index, int max)
				{
					return null;
				}
				
				@Override
				public void onClick(TextFormatting item)
				{
					ElementColorMenu.this.string.getStyle().setColor(item);
				}
				
				@Override
				public String getId()
				{
					return ElementColorMenu.this.logic.getId();
				}
			}));
			
			container.add(new GuiButtonBase(this.x + 118, this.y + 48, 20, 20, (this.string.getStyle().getItalic() ? TextFormatting.ITALIC : TextFormatting.RESET) + "I", () ->
			{
				this.string.getStyle().setItalic(!this.string.getStyle().getItalic());
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 - 1, this.y + 48, 20, 20, (this.string.getStyle().getBold() ? TextFormatting.BOLD : TextFormatting.RESET) + "B", () ->
			{
				this.string.getStyle().setBold(!this.string.getStyle().getBold());
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, (this.string.getStyle().getUnderlined() ? TextFormatting.UNDERLINE : TextFormatting.RESET) + "U", () ->
			{
				this.string.getStyle().setUnderlined(!this.string.getStyle().getUnderlined());
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, (this.string.getStyle().getStrikethrough() ? TextFormatting.STRIKETHROUGH : TextFormatting.RESET) + "S", () ->
			{
				this.string.getStyle().setStrikethrough(!this.string.getStyle().getStrikethrough());
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, (this.string.getStyle().getObfuscated() ? TextFormatting.OBFUSCATED : TextFormatting.RESET) + "O", () ->
			{
				this.string.getStyle().setObfuscated(!this.string.getStyle().getObfuscated());
				container.init();
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
		this.textField.renderButton(mouseX, mouseY, partialTicks);
	}
}
