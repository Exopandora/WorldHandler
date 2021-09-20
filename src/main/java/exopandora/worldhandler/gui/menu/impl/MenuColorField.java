package exopandora.worldhandler.gui.menu.impl;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.MutableTextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class MenuColorField extends Menu
{
	private static final List<ChatFormatting> COLORS = new ArrayList<ChatFormatting>();
	
	static
	{
		COLORS.add(ChatFormatting.RESET);
		COLORS.add(ChatFormatting.YELLOW);
		COLORS.add(ChatFormatting.GOLD);
		COLORS.add(ChatFormatting.DARK_RED);
		COLORS.add(ChatFormatting.RED);
		COLORS.add(ChatFormatting.LIGHT_PURPLE);
		COLORS.add(ChatFormatting.DARK_PURPLE);
		COLORS.add(ChatFormatting.BLUE);
		COLORS.add(ChatFormatting.DARK_BLUE);
		COLORS.add(ChatFormatting.DARK_AQUA);
		COLORS.add(ChatFormatting.AQUA);
		COLORS.add(ChatFormatting.GREEN);
		COLORS.add(ChatFormatting.DARK_GREEN);
		COLORS.add(ChatFormatting.BLACK);
		COLORS.add(ChatFormatting.DARK_GRAY);
		COLORS.add(ChatFormatting.GRAY);
		COLORS.add(ChatFormatting.WHITE);
	}
	
	private GuiTextFieldTooltip textField;
	private final MutableTextComponent string;
	private final ILogicColorMenu logic;
	private final String translationKey;
	
	public MenuColorField(int x, int y, String translationKey, MutableTextComponent string)
	{
		this(x, y, translationKey, string, new ILogicColorMenu(){});
	}
	
	public MenuColorField(int x, int y, String translationKey, MutableTextComponent string, ILogicColorMenu logic)
	{
		super(x, y);
		this.translationKey = translationKey;
		this.string = string;
		this.logic = logic;
	}
	
	@Override
	public void initGui(Container container)
	{
		this.textField = new GuiTextFieldTooltip(this.x + 118, this.y, 114, 20, new TranslatableComponent(this.translationKey));
		this.textField.setFilter(this.logic::validate);
		this.textField.setFormatter(this.string::formatter);
		this.textField.setValue(this.string.getContents());
		this.textField.setResponder(text ->
		{
			this.string.setText(text);
		});
	}
	
	@Override
	public void initButtons(Container container)
	{
		if(this.logic.doDrawTextField())
		{
			container.add(this.textField);
		}
		
		if(this.logic.doDrawButtons())
		{
			container.add(new GuiButtonList<ChatFormatting>(this.x + 118, this.y + 24, COLORS, 114, 20, container, new ILogicMapped<ChatFormatting>()
			{
				@Override
				public MutableComponent translate(ChatFormatting item)
				{
					return new TranslatableComponent("gui.worldhandler.color").withStyle(item).append(": ").append(new TranslatableComponent("gui.worldhandler.color." + item.getName()));
				}
				
				@Override
				public MutableComponent toTooltip(ChatFormatting item)
				{
					return null;
				}
				
				@Override
				public MutableComponent formatTooltip(ChatFormatting item, int index, int max)
				{
					return null;
				}
				
				@Override
				public void onClick(ChatFormatting item)
				{
					MenuColorField.this.string.withStyle(item);
				}
				
				@Override
				public String getId()
				{
					return MenuColorField.this.logic.getId();
				}
			}));
			
			container.add(new GuiButtonBase(this.x + 118, this.y + 48, 20, 20, new TextComponent("I").setStyle(Style.EMPTY.withItalic(this.string.getStyle().isItalic())), () ->
			{
				this.string.setStyle(this.string.getStyle().withItalic(!this.string.getStyle().isItalic()));
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 - 1, this.y + 48, 20, 20, new TextComponent("B").setStyle(Style.EMPTY.withBold(this.string.getStyle().isBold())), () ->
			{
				this.string.setStyle(this.string.getStyle().withBold(!this.string.getStyle().isBold()));
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, new TextComponent("U").setStyle(Style.EMPTY.setUnderlined(this.string.getStyle().isUnderlined())), () ->
			{
				this.string.setStyle(this.string.getStyle().setUnderlined(!this.string.getStyle().isUnderlined()));
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, new TextComponent("S").setStyle(Style.EMPTY.setStrikethrough(this.string.getStyle().isStrikethrough())), () ->
			{
				this.string.setStyle(this.string.getStyle().setStrikethrough(!this.string.getStyle().isStrikethrough()));
				container.init();
			}));
			container.add(new GuiButtonBase(this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, new TextComponent("O").setStyle(Style.EMPTY.setObfuscated(this.string.getStyle().isObfuscated())), () ->
			{
				this.string.setStyle(this.string.getStyle().setObfuscated(!this.string.getStyle().isObfuscated()));
				container.init();
			}));
		}
	}
	
	@Override
	public void tick()
	{
		if(this.logic.doDrawTextField())
		{
			this.textField.tick();
		}
	}
	
	@Override
	public void draw(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		if(this.logic.doDrawTextField())
		{
			this.textField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
}
