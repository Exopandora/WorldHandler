package exopandora.worldhandler.gui.container;

import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.element.Element;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IContainer
{
	<T extends Widget> T add(T button);
	
	void initButtons();
	void add(Element element);
	
	String getPlayer();
	
	Content getContent();
}
