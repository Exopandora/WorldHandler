package exopandora.worldhandler.builder.component.impl;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentPotionItem extends ComponentPotion
{
	@Override
	public String getTag()
	{
		return "CustomPotionEffects";
	}
}
