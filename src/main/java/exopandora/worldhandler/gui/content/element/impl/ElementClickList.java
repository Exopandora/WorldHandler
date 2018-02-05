package exopandora.worldhandler.gui.content.element.impl;

import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.storage.ButtonStorage;
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
	private final Content master;
	
	public ElementClickList(int x, int y, List<Node> list, int buttonId1, int buttonId2, Content container, ILogicClickList logic)
	{
		super(x, y);
		this.list = list;
		this.buttonId1 = buttonId1;
		this.buttonId2 = buttonId2;
		this.logic = logic;
		this.master = container;
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(this.button1 = new GuiButtonList(this.buttonId1, this.x, this.y, 114, 20, EnumTooltip.TOP_RIGHT, this.master, new IListButtonLogic<Node>()
		{
			@Override
			public void actionPerformed(Container container, GuiButton button, ButtonStorage<Node> storage)
			{
				master.getStorage(listButtonLogic2.getId()).setIndex(0);
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
			public String getDisplayString(ButtonStorage<Node> storage)
			{
				return logic.translate1(storage.getObject().getKey());
			}
			
			@Override
			public String getTooltipString(ButtonStorage<Node> storage)
			{
				if(storage != null && storage.getObject() != null)
				{
					return storage.getObject().getKey() + " (" + (storage.getIndex() + 1) + "/" + this.getMax() + ")";
				}
				
				return null;
			}
			
			@Override
			public String getId()
			{
				return logic.getId() + 1;
			}
		}));
		
		final Node node = this.getStorage1().getObject();
		this.logic.consumeKey1(node.getKey());
		
		if(node.getEntries() != null)
		{
			container.add(this.button2 = new GuiButtonList(this.buttonId2, this.x, this.y + 24, 114, 20, EnumTooltip.TOP_RIGHT, this.master, this.listButtonLogic2));
			this.logic.consumeKey2(node.getKey(), node.getEntries().get(this.getStorage2().getIndex()).getKey());
		}
		else
		{
			container.add(this.button2 = new GuiButtonList(this.buttonId2, this.x, this.y + 24, 114, 20, EnumTooltip.TOP_RIGHT, this.master, this.listButtonLogic2));
			this.button2.enabled = false;
		}
	}
	
	private final IListButtonLogic<Node> listButtonLogic2 = new IListButtonLogic<Node>()
	{
		@Override
		public void actionPerformed(Container container, GuiButton button, ButtonStorage<Node> storage)
		{
			container.initButtons();
		}
		
		@Override
		public int getMax()
		{
			if(getStorage1().getObject() != null)
			{
				return getStorage1().getObject().getEntries().size();
			}
			
			return 0;
		}
		
		@Override
		public Node getObject(int index)
		{
			if(getStorage1().getObject().getEntries() != null)
			{
				return getStorage1().getObject().getEntries().get(index);
			}
			
			return null;
		}
		
		@Override
		public String getDisplayString(ButtonStorage<Node> storage)
		{
			if(storage.getObject() != null)
			{
				return logic.translate2(getStorage1().getObject().getKey(), storage.getObject().getKey());
			}
			
			return null;
		}
		
		@Override
		public String getTooltipString(ButtonStorage<Node> storage)
		{
			if(getStorage1().getObject().getEntries() != null)
			{
				return storage.getObject().getKey() + " (" + (storage.getIndex() + 1) + "/" + getStorage1().getObject().getEntries().size() + ")";
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
	private ButtonStorage<Node> getStorage1()
	{
		if(this.button1 != null)
		{
			return this.master.<Node>getStorage(this.button1.getLogic().getId());
		}
		
		return null;
	}
	
	@Nullable
	private ButtonStorage<Node> getStorage2()
	{
		if(this.button2 != null)
		{
			return this.master.<Node>getStorage(this.button2.getLogic().getId());
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
