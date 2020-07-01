package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.gui.menu.IMenu;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Container extends Screen implements IContainer
{
	protected final List<IMenu> menus = new ArrayList<IMenu>();
	
	protected Container(ITextComponent title)
	{
		super(title);
	}
	
	@Override
	public void func_231160_c_()
	{
		super.func_231160_c_();
	}
	
	@Override
	public <T extends Widget> T add(T button)
	{
		return super.func_230480_a_(button);
	}
	
	public <T extends TextFieldWidget> T add(T textfield)
	{
		return super.func_230481_d_(textfield);
	}
	
	@Override
	public void add(Menu menu)
	{
		this.menus.add(menu);
	}
}
