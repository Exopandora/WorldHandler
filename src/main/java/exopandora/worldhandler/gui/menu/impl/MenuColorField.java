package exopandora.worldhandler.gui.menu.impl;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.util.MutableStringTextComponent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MenuColorField extends Menu
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
	
	public MenuColorField(int x, int y, String translationKey, MutableStringTextComponent string)
	{
		this(x, y, translationKey, string, new ILogicColorMenu(){});
	}
	
	public MenuColorField(int x, int y, String translationKey, MutableStringTextComponent string, ILogicColorMenu logic)
	{
		super(x, y);
		this.translationKey = translationKey;
		this.string = string;
		this.logic = logic;
	}
	
	@Override
	public void initGui(Container container)
	{
		this.textField = new GuiTextFieldTooltip(this.x + 118, this.y, 114, 20, new TranslationTextComponent(this.translationKey));
		this.textField.setValidator(this.logic::validate);
		this.textField.setTextFormatter(this.string::formatter);
		this.textField.setText(this.string.getUnformattedComponentText());
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
				public IFormattableTextComponent translate(TextFormatting item)
				{
					return new TranslationTextComponent("gui.worldhandler.color").func_240699_a_(item).func_240702_b_(": ").func_230529_a_(new TranslationTextComponent("gui.worldhandler.color." + item.getFriendlyName()));
				}
				
				@Override
				public IFormattableTextComponent toTooltip(TextFormatting item)
				{
					return null;
				}
				
				@Override
				public IFormattableTextComponent formatTooltip(TextFormatting item, int index, int max)
				{
					return null;
				}
				
				@Override
				public void onClick(TextFormatting item)
				{
					MenuColorField.this.string.func_240699_a_(item);
				}
				
				@Override
				public String getId()
				{
					return MenuColorField.this.logic.getId();
				}
			}));
			
			container.add(new GuiButtonBase(this.x + 118, this.y + 48, 20, 20, new StringTextComponent("I").func_240703_c_(Style.field_240709_b_.func_240722_b_(this.string.getStyle().getItalic())), () ->
			{
				this.string.func_240703_c_(this.string.getStyle().func_240722_b_(!this.string.getStyle().getItalic()));
				container.func_231160_c_();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 - 1, this.y + 48, 20, 20, new StringTextComponent("B").func_240703_c_(Style.field_240709_b_.func_240713_a_(this.string.getStyle().getBold())), () ->
			{
				this.string.func_240703_c_(this.string.getStyle().func_240713_a_(!this.string.getStyle().getBold()));
				container.func_231160_c_();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, new StringTextComponent("U").func_240703_c_(Style.field_240709_b_.setUnderlined(this.string.getStyle().getUnderlined())), () ->
			{
				this.string.func_240703_c_(this.string.getStyle().setUnderlined(!this.string.getStyle().getUnderlined()));
				container.func_231160_c_();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, new StringTextComponent("S").func_240703_c_(Style.field_240709_b_.setStrikethrough(this.string.getStyle().getStrikethrough())), () ->
			{
				this.string.func_240703_c_(this.string.getStyle().setStrikethrough(!this.string.getStyle().getStrikethrough()));
				container.func_231160_c_();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, new StringTextComponent("O").func_240703_c_(Style.field_240709_b_.setObfuscated(this.string.getStyle().getObfuscated())), () ->
			{
				this.string.func_240703_c_(this.string.getStyle().setObfuscated(!this.string.getStyle().getObfuscated()));
				container.func_231160_c_();
			}));
		}
	}
	
	@Override
	public void tick()
	{
		this.textField.tick();
	}
	
	@Override
	public void draw(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.textField.func_230431_b_(matrix, mouseX, mouseY, partialTicks); //renderButton
	}
}
