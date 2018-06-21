package exopandora.worldhandler.gui.content.element.impl;

import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.persistence.ButtonValues;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.logic.ILogicClickList;
import exopandora.worldhandler.helper.Node;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementClickList extends Element
{
	private final int buttonId1;
	private final int buttonId2;
	private final List<Node> list;
	private final ILogicClickList logic;
	private GuiButtonList button1;
	private GuiButtonList button2;
	private final Content content;
	
	public ElementClickList(int x, int y, List<Node> list, int buttonId1, int buttonId2, Content content, ILogicClickList logic)
	{
		super(x, y);
		this.list = list;
		this.buttonId1 = buttonId1;
		this.buttonId2 = buttonId2;
		this.logic = logic;
		this.content = content;
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(this.button1 = new GuiButtonList(this.buttonId1, this.x, this.y, 114, 20, EnumTooltip.TOP_RIGHT, this.content, new IListButtonLogic<Node>()
		{
			@Override
			public void actionPerformed(Container container, GuiButton button, ButtonValues<Node> values)
			{
				content.getPersistence(listButtonLogic2.getId()).setIndex(0);
				container.initButtons();
			}
			
			@Override
			public int getMax()
			{
				return list.size();
			}
			
			@Override
			public Node getObject(int index)
			{
				return list.get(index);
			}

			@Override
			public String getDisplayString(ButtonValues<Node> values)
			{
				return logic.translate1(values.getObject().getKey());
			}
			
			@Override
			public String getTooltipString(ButtonValues<Node> values)
			{
				if(values != null && values.getObject() != null)
				{
					return values.getObject().getKey() + " (" + (values.getIndex() + 1) + "/" + this.getMax() + ")";
				}
				
				return null;
			}
			
			@Override
			public String getId()
			{
				return logic.getId() + 1;
			}
		}));
		
		final Node node = this.getValues1().getObject();
		this.logic.consumeKey1(node.getKey());
		
		if(node.getEntries() != null)
		{
			container.add(this.button2 = new GuiButtonList(this.buttonId2, this.x, this.y + 24, 114, 20, EnumTooltip.TOP_RIGHT, this.content, this.listButtonLogic2));
			this.logic.consumeKey2(node.getKey(), node.getEntries().get(this.getValues2().getIndex()).getKey());
		}
		else
		{
			container.add(this.button2 = new GuiButtonList(this.buttonId2, this.x, this.y + 24, 114, 20, EnumTooltip.TOP_RIGHT, this.content, this.listButtonLogic2));
			this.button2.enabled = false;
		}
	}
	
	private final IListButtonLogic<Node> listButtonLogic2 = new IListButtonLogic<Node>()
	{
		@Override
		public void actionPerformed(Container container, GuiButton button, ButtonValues<Node> values)
		{
			container.initButtons();
		}
		
		@Override
		public int getMax()
		{
			if(getValues1().getObject() != null)
			{
				return getValues1().getObject().getEntries().size();
			}
			
			return 0;
		}
		
		@Override
		public Node getObject(int index)
		{
			if(getValues1().getObject().getEntries() != null)
			{
				return getValues1().getObject().getEntries().get(index);
			}
			
			return null;
		}
		
		@Override
		public String getDisplayString(ButtonValues<Node> values)
		{
			if(values.getObject() != null)
			{
				return logic.translate2(getValues1().getObject().getKey(), values.getObject().getKey());
			}
			
			return null;
		}
		
		@Override
		public String getTooltipString(ButtonValues<Node> values)
		{
			if(getValues1().getObject().getEntries() != null)
			{
				return values.getObject().getKey() + " (" + (values.getIndex() + 1) + "/" + getValues1().getObject().getEntries().size() + ")";
			}
			
			return null;
		}
		
		@Override
		public String getId()
		{
			return logic.getId() + 2;
		}
	};
	
	@Nullable
	private ButtonValues<Node> getValues1()
	{
		if(this.button1 != null)
		{
			return this.content.<Node>getPersistence(this.button1.getLogic().getId());
		}
		
		return null;
	}
	
	@Nullable
	private ButtonValues<Node> getValues2()
	{
		if(this.button2 != null)
		{
			return this.content.<Node>getPersistence(this.button2.getLogic().getId());
		}
		
		return null;
	}
	
	@Override
	public boolean actionPerformed(Container container, GuiButton button)
	{
		if(button.id == this.buttonId1)
		{
			this.button1.actionPerformed(container, button);
			return true;
		}
		else if(button.id == this.buttonId2)
		{
			this.button2.actionPerformed(container, button);
			return true;
		}
		
		return false;
	}
}
