package exopandora.worldhandler.builder.impl.abstr;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EnumAttributes
{
	MAX_HEALTH("generic.maxHealth", EnumOperation.ADDITIVE, 0, 100, 0, Applyable.BOTH),
	FOLLOW_RANGE("generic.followRange", EnumOperation.ADDITIVE, 0, 100, 0, Applyable.MOB),
	KNOCKBACK_RESISTANCE("generic.knockbackResistance", EnumOperation.PERCENTAGE, 0, 100, 0, Applyable.BOTH),
	MOVEMENT_SPEED("generic.movementSpeed", EnumOperation.PERCENTAGE, 0, 100, 0, Applyable.BOTH),
	ATTACK_DAMAGE("generic.attackDamage", EnumOperation.ADDITIVE, 0, 100, 0, Applyable.BOTH),
	ARMOR("generic.armor", EnumOperation.ADDITIVE, 0, 100, 0, Applyable.BOTH),
	ARMOR_TOUGHNESS("generic.armorToughness", EnumOperation.ADDITIVE, 0, 100, 0, Applyable.BOTH),
	ATTACK_SPEED("generic.attackSpeed", EnumOperation.PERCENTAGE, 0, 100, 0, Applyable.BOTH),
	LUCK("generic.luck", EnumOperation.PERCENTAGE, -100, 100, 0, Applyable.PLAYER),
	HORSE_JUMP_STRENGTH("horse.jumpStrength", EnumOperation.PERCENTAGE, 0, 100, 0, Applyable.MOB),
	ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawnReinforcements", EnumOperation.PERCENTAGE, 0, 100, 0, Applyable.MOB);
	
	private String attribute;
	private EnumOperation operation;
	private float min;
	private float max;
	private float start;
	private Applyable applyable;
	
	public enum Applyable
	{
		BOTH,
		PLAYER,
		MOB
	}
	
	private EnumAttributes(String attribute, EnumOperation operation, float min, float max, float start, Applyable applyable)
	{
		this.attribute = attribute;
		this.operation = operation;
		this.min = min;
		this.max = max;
		this.start = start;
		this.applyable = applyable;
	}
	
	public String getAttribute()
	{
		return this.attribute;
	}
	
	public String getTranslationKey()
	{
		return "attribute.name." + this.attribute;
	}
	
	public String getTranslation()
	{
		return I18n.format(this.getTranslationKey(), new Object[0]);
	}
	
	public EnumOperation getOperation()
	{
		return this.operation;
	}
	
	public float getMin()
	{
		return this.min;
	}
	
	public float getMax()
	{
		return this.max;
	}
	
	public float getStart()
	{
		return this.start;
	}
	
	public Applyable getApplyable()
	{
		return this.applyable;
	}
	
	public double calculate(Float value)
	{
		return this.operation.getOperation().apply(value.doubleValue());
	}
	
	public enum EnumOperation
	{
		ADDITIVE(value -> value, "(+)"),
		PERCENTAGE(value -> value / 100, "%");
		
		private final Function<Double, Double> operation;
		private final String declaration;
		
		private EnumOperation(Function<Double, Double> operation, String declaration)
		{
			this.operation = operation;
			this.declaration = declaration;
		}
		
		public Function<Double, Double> getOperation()
		{
			return this.operation;
		}
		
		public String getDeclaration()
		{
			return this.declaration;
		}
	}
	
	public static List<EnumAttributes> getAttributesFor(Applyable applyable)
	{
		return Arrays.stream(EnumAttributes.values()).filter(attribute -> attribute.getApplyable().equals(applyable)).collect(Collectors.toList());
	}
}
