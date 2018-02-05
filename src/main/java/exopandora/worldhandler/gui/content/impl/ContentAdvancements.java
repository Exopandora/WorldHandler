package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderAdvancement;
import exopandora.worldhandler.builder.impl.BuilderAdvancement.EnumActionType;
import exopandora.worldhandler.builder.impl.BuilderAdvancement.EnumMode;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.storage.ButtonStorage;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import exopandora.worldhandler.helper.AdvancementHelper;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentAdvancements extends Content
{
	private final AdvancementHelper helper = new AdvancementHelper();
	private final BuilderAdvancement builderAdvancement = new BuilderAdvancement(EnumMode.values()[0]);
	
	private GuiButtonList modeButton;
	
	private final List<Advancement> advancements = StreamSupport.stream(new AdvancementManager(null).getAdvancements().spliterator(), true).filter(advancement -> advancement.getDisplay() != null).collect(Collectors.toList());
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderAdvancement;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		ElementPageList<Advancement, String> list = new ElementPageList<Advancement, String>(x, y, this.advancements, null, 114, 20, 3, this, new int[] {6, 7, 8}, new ILogicPageList<Advancement, String>()
		{
			@Override
			public String translate(Advancement key)
			{
				return I18n.format(key.getDisplay().getTitle().getUnformattedText());
			}
			
			@Override
			public void onClick(Advancement clicked)
			{
				builderAdvancement.setAdvancement(clicked.getId());
			}
			
			@Override
			public String getRegistryName(Advancement key)
			{
				return key.getId().toString();
			}
			
			@Override
			public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, Advancement value, Container container)
			{
				GuiButtonWorldHandler button;
				container.add(button = new GuiButtonWorldHandler(id, x, y, width, height, display, value.getId().toString(), EnumTooltip.TOP_RIGHT));
				button.enabled = enabled;
			}
			
			@Override
			public Advancement convert(String object)
			{
				return helper.ADVANCEMENT_MANAGER.getAdvancement(Type.parseResourceLocation(object));
			}
			
			@Override
			public String getId()
			{
				return "advancements";
			}
		});
		
		container.add(list);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(this.modeButton = new GuiButtonList(2, x + 118, y, 114, 20, EnumTooltip.TOP_RIGHT, this, new IListButtonLogic<EnumMode>()
		{
			private final EnumMode[] values = Arrays.stream(EnumMode.values()).filter(mode -> !mode.equals(EnumMode.EVERYTHING)).toArray(EnumMode[]::new);
			
			@Override
			public void actionPerformed(Container container, GuiButton button, ButtonStorage<EnumMode> storage)
			{
				builderAdvancement.setMode(storage.getObject());
			}
			
			@Override
			public int getMax()
			{
				return this.values.length;
			}
			
			@Override
			public EnumMode getObject(int index)
			{
				return this.values[index];
			}
			
			@Override
			public String getDisplayString(ButtonStorage<EnumMode> storage)
			{
				return I18n.format("gui.worldhandler.advancements." + storage.getObject().toString());
			}
			
			@Override
			public String getId()
			{
				return "mode";
			}
		}));
		
		container.add(new GuiButtonWorldHandler(3, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.advancements.grant")));
		container.add(new GuiButtonWorldHandler(4, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.advancements.revoke")));
		container.add(new GuiButtonWorldHandler(5, x + 118, y + 72, 114, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.actions.reset")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				this.modeButton.actionPerformed(container, button);
				container.initGui();
				break;
			case 3:
				WorldHandler.sendCommand(this.builderAdvancement.getBuilderForAction(EnumActionType.GRANT));
				break;
			case 4:
				WorldHandler.sendCommand(this.builderAdvancement.getBuilderForAction(EnumActionType.REVOKE));
				break;
			case 5:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderAdvancement.getBuilder(EnumActionType.REVOKE, EnumMode.EVERYTHING)).withParent(Contents.ADVANCEMENTS)));
				break;
			default:
				break;
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.player.advancements");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.player.advancements");
	}
	
	@Override
	public String[] getHeadline()
	{
		return new String[] {null, I18n.format("gui.worldhandler.generic.options")};
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.ADVANCEMENTS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderAdvancement.setPlayer(username);
	}
}
