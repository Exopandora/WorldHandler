package exopandora.worldhandler.builder.component.impl;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentPotionMob extends ComponentPotion
{
	@Override
	public String getTag()
	{
		return "ActiveEffects";
	}
}
