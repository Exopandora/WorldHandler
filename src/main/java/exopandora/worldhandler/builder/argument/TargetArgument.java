package exopandora.worldhandler.builder.argument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.argument.tag.ITagProvider;
import exopandora.worldhandler.util.Util;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.resources.ResourceLocation;

public class TargetArgument implements IArgument
{
	@Nullable
	private String target; // player name or entity UUID
	@Nullable
	private String selectorType;
	private NegatableCriterion<String> name = new NegatableCriterion<String>();
	private MinMaxBounds.Doubles distance = MinMaxBounds.Doubles.ANY;
	@Nullable
	private Double x;
	@Nullable
	private Double y;
	@Nullable
	private Double z;
	@Nullable
	private Double deltaX;
	@Nullable
	private Double deltaY;
	@Nullable
	private Double deltaZ;
	private WrappedMinMaxBounds rotX = WrappedMinMaxBounds.ANY;
	private WrappedMinMaxBounds rotY = WrappedMinMaxBounds.ANY;
	@Nullable
	private Double level;
	@Nullable
	private Integer limit;
	private NegatableCriterion<TagArgument> nbt = new NegatableCriterion<TagArgument>();
	private NegatableCriterion<ResourceLocation> type = new NegatableCriterion<ResourceLocation>();
	private Map<ResourceLocation, Boolean> advancements;
	private List<NegatableCriterion<ResourceLocation>> predicates;
	private NegatableCriterion<String> team = new NegatableCriterion<String>();
	private Map<String, MinMaxBounds.Ints> scores;
	@Nullable
	private NegatableCriterion<Gamemode> gamemode = new NegatableCriterion<Gamemode>();
	@Nullable
	private Sort sort;
	
	protected TargetArgument()
	{
		super();
	}
	
	public void setTarget(@Nullable String target)
	{
		this.target = target;
	}
	
	public void setSelectorType(@Nullable String type)
	{
		this.selectorType = type;
	}
	
	public void setName(@Nullable String name)
	{
		this.name.setCriterion(name);
	}
	
	public void setNameNegated(boolean negated)
	{
		this.name.setNegated(negated);
	}
	
	public void setName(@Nullable String name, boolean negated)
	{
		this.setName(name);
		this.setNameNegated(negated);
	}
	
	public void setDistance(@Nullable Double distance)
	{
		this.distance = MinMaxBounds.Doubles.exactly(distance);
	}
	
	public void setDistance(@Nullable Double min, @Nullable Double max)
	{
		this.distance = MinMaxBounds.Doubles.between(min, max);
	}
	
	public void setDistanceMin(@Nullable Double min)
	{
		this.distance = this.distance.max()
			.map(max -> MinMaxBounds.Doubles.between(min, max))
			.orElseGet(() -> MinMaxBounds.Doubles.exactly(min));
	}
	
	public void setDistanceMax(@Nullable Double max)
	{
		this.distance = this.distance.min()
			.map(min -> MinMaxBounds.Doubles.between(min, max))
			.orElseGet(() -> MinMaxBounds.Doubles.exactly(max));
	}
	
	public void setX(@Nullable Double x)
	{
		this.x = x;
	}
	
	public void setY(@Nullable Double y)
	{
		this.y = y;
	}
	
	public void setZ(@Nullable Double z)
	{
		this.z = z;
	}
	
	public void setDeltaX(@Nullable Double deltaX)
	{
		this.deltaX = deltaX;
	}
	
	public void setDeltaY(@Nullable Double deltaY)
	{
		this.deltaY = deltaY;
	}
	
	public void setDeltaZ(@Nullable Double deltaZ)
	{
		this.deltaZ = deltaZ;
	}
	
	public void setRotationX(@Nullable Float rotX)
	{
		this.rotX = WrappedMinMaxBounds.exactly(rotX);
	}
	
	public void setRotationX(@Nullable Float min, @Nullable Float max)
	{
		this.rotX = WrappedMinMaxBounds.between(min, max);
	}
	
	public void setRotationXMin(@Nullable Float min)
	{
		this.rotX = WrappedMinMaxBounds.between(min, this.rotX.max());
	}
	
	public void setRotationXMax(@Nullable Float max)
	{
		this.rotX = WrappedMinMaxBounds.between(this.rotX.min(), max);
	}
	
	public void setRotationY(@Nullable Float rotY)
	{
		this.rotY = WrappedMinMaxBounds.exactly(rotY);
	}
	
	public void setRotationY(@Nullable Float min, @Nullable Float max)
	{
		this.rotY = WrappedMinMaxBounds.between(min, max);
	}
	
	public void setRotationYMin(@Nullable Float min)
	{
		this.rotY = WrappedMinMaxBounds.between(min, this.rotY.max());
	}
	
	public void setRotationYMax(@Nullable Float max)
	{
		this.rotY = WrappedMinMaxBounds.between(this.rotY.min(), max);
	}
	
	public void setLevel(@Nullable Double level)
	{
		this.level = level;
	}
	
	public void setLimit(@Nullable Integer limit)
	{
		this.limit = limit;
	}
	
	public TargetArgument addTag(ITagProvider provider)
	{
		if(this.nbt.getCriterion() == null)
		{
			this.nbt.setCriterion(new TagArgument());
		}
		
		this.nbt.getCriterion().addTagProvider(provider);
		return this;
	}
	
	public void setType(@Nullable ResourceLocation type)
	{
		this.type.setCriterion(type);
	}
	
	public void setTypeNegated(boolean negated)
	{
		this.type.setNegated(negated);
	}
	
	public void setType(@Nullable ResourceLocation type, boolean negated)
	{
		this.setType(type);
		this.setTypeNegated(negated);
	}
	
	public void setAdvancement(ResourceLocation advancement, boolean unlocked)
	{
		if(this.advancements == null)
		{
			this.advancements = new HashMap<ResourceLocation, Boolean>();
		}
		
		this.advancements.put(advancement, unlocked);
	}
	
	public void setPredicate(ResourceLocation predicate)
	{
		this.setPredicate(predicate, false);
	}
	
	public void setPredicate(ResourceLocation predicate, boolean negated)
	{
		if(this.predicates == null)
		{
			this.predicates = new ArrayList<NegatableCriterion<ResourceLocation>>();
		}
		
		NegatableCriterion<ResourceLocation> entry = this.findPredicate(predicate);
		
		if(entry == null)
		{
			this.predicates.add(new NegatableCriterion<ResourceLocation>(predicate, negated));
		}
		else if(entry.isNegated() != negated)
		{
			entry.setNegated(negated);
		}
	}
	
	private NegatableCriterion<ResourceLocation> findPredicate(ResourceLocation predicate)
	{
		for(NegatableCriterion<ResourceLocation> entry : this.predicates)
		{
			if(predicate.equals(entry.getCriterion()))
			{
				return entry;
			}
		}
		
		return null;
	}
	
	public void setTeam(@Nullable String team)
	{
		this.team.setCriterion(team);
	}
	
	public void setTeamNegated(boolean negated)
	{
		this.team.setNegated(negated);
	}
	
	public void setTeam(@Nullable String team, boolean negated)
	{
		this.setTeam(team);
		this.setTeamNegated(negated);
	}
	
	public void setScore(String score, @Nullable Integer value)
	{
		if(this.scores == null)
		{
			this.scores = new HashMap<String, MinMaxBounds.Ints>();
		}
		
		this.scores.put(score, MinMaxBounds.Ints.exactly(value));
	}
	
	public void setScore(String score, @Nullable Integer min, @Nullable Integer max)
	{
		if(this.scores == null)
		{
			this.scores = new HashMap<String, MinMaxBounds.Ints>();
		}
		
		this.scores.put(score, MinMaxBounds.Ints.between(min, max));
	}
	
	public void setScoreMin(String score, @Nullable Integer min)
	{
		if(this.scores == null)
		{
			this.scores = new HashMap<String, MinMaxBounds.Ints>();
		}
		
		MinMaxBounds.Ints bounds = this.scores.getOrDefault(score, MinMaxBounds.Ints.ANY).max()
			.map(max -> MinMaxBounds.Ints.between(min, max))
			.orElseGet(() -> MinMaxBounds.Ints.exactly(min));
		
		this.scores.put(score, bounds);
	}
	
	public void setScoreMax(String score, @Nullable Integer max)
	{
		if(this.scores == null)
		{
			this.scores = new HashMap<String, MinMaxBounds.Ints>();
		}
		
		MinMaxBounds.Ints bounds = this.scores.getOrDefault(score, MinMaxBounds.Ints.ANY).min()
			.map(min -> MinMaxBounds.Ints.between(min, max))
			.orElseGet(() -> MinMaxBounds.Ints.exactly(max));
		
		this.scores.put(score, bounds);
	}
	
	public void setGamemode(@Nullable Gamemode gamemode)
	{
		this.gamemode.setCriterion(gamemode);
	}
	
	public void setGamemodeNegated(boolean negated)
	{
		this.gamemode.setNegated(negated);
	}
	
	public void setGamemode(@Nullable Gamemode gamemode, boolean negated)
	{
		this.setGamemode(gamemode);
		this.setGamemodeNegated(negated);
	}
	
	public void setSort(@Nullable Sort sort)
	{
		this.sort = sort;
	}
	
	@Nullable
	public String getTarget()
	{
		return this.target;
	}
	
	@Nullable
	public String getSelectorType()
	{
		return this.selectorType;
	}
	
	public NegatableCriterion<String> getName()
	{
		return this.name;
	}
	
	public MinMaxBounds.Doubles getDistance()
	{
		return this.distance;
	}
	
	@Nullable
	public Double getX()
	{
		return this.x;
	}
	
	@Nullable
	public Double getY()
	{
		return this.y;
	}
	
	@Nullable
	public Double getZ()
	{
		return this.z;
	}
	
	@Nullable
	public Double getDeltaX()
	{
		return this.deltaX;
	}
	
	@Nullable
	public Double getDeltaY()
	{
		return this.deltaY;
	}
	
	@Nullable
	public Double getDeltaZ()
	{
		return this.deltaZ;
	}
	
	public WrappedMinMaxBounds getRotX()
	{
		return this.rotX;
	}
	
	public WrappedMinMaxBounds getRotY()
	{
		return this.rotY;
	}
	
	@Nullable
	public Double getLevel()
	{
		return this.level;
	}
	
	@Nullable
	public Integer getLimit()
	{
		return this.limit;
	}
	
	public NegatableCriterion<TagArgument> getNbt()
	{
		return this.nbt;
	}
	
	public NegatableCriterion<ResourceLocation> getType()
	{
		return this.type;
	}
	
	@Nullable
	public Map<ResourceLocation, Boolean> getAdvancements()
	{
		return this.advancements;
	}
	
	@Nullable
	public List<NegatableCriterion<ResourceLocation>> getPredicates()
	{
		return this.predicates;
	}
	
	public NegatableCriterion<String> getTeam()
	{
		return this.team;
	}
	
	@Nullable
	public Map<String, MinMaxBounds.Ints> getScores()
	{
		return this.scores;
	}
	
	public NegatableCriterion<Gamemode> getGamemode()
	{
		return this.gamemode;
	}
	
	@Nullable
	public Sort getSort()
	{
		return this.sort;
	}
	
	@Nullable
	@Override
	public String serialize()
	{
		if(this.selectorType == null)
		{
			if(this.target != null && !this.target.isBlank())
			{
				return this.target;
			}
			
			return null;
		}
		
		List<String> criteria = new ArrayList<String>();
		
		this.append("name", this.name, criteria, TargetArgument::serializeNegatableCriterion);
		this.append("distance", this.distance, criteria, TargetArgument::serializeMinMaxBounds);
		this.append("x", this.x, criteria);
		this.append("y", this.y, criteria);
		this.append("z", this.z, criteria);
		this.append("dx", this.deltaX, criteria);
		this.append("dy", this.deltaY, criteria);
		this.append("dz", this.deltaZ, criteria);
		this.append("x_rotation", this.rotX, criteria, TargetArgument::serializeWrappedMinMaxBounds);
		this.append("y_rotation", this.rotY, criteria, TargetArgument::serializeWrappedMinMaxBounds);
		this.append("level", this.level, criteria);
		this.append("limit", this.limit, criteria);
		this.append("nbt", this.nbt, criteria, nbt -> TargetArgument.serializeNegatableCriterion(nbt, TagArgument::serialize));
		this.append("type", this.type, criteria, TargetArgument::serializeNegatableCriterion);
		this.appendMap("advancements", this.advancements, criteria, ResourceLocation::toString, b -> b.toString());
		this.appendList("predicate", this.predicates, criteria, TargetArgument::serializeNegatableCriterion);
		this.append("team", this.team, criteria, TargetArgument::serializeNegatableCriterion);
		this.appendMap("scores", this.scores, criteria, String::toString, TargetArgument::serializeMinMaxBounds);
		this.append("gamemode", this.gamemode, criteria, TargetArgument::serializeNegatableCriterion);
		this.append("sort", this.sort, criteria);
		
		if(criteria.isEmpty())
		{
			return "@" + this.selectorType;
		}
		
		return criteria.stream().collect(Collectors.joining(",", "@" + this.selectorType + "[", "]"));
	}
	
	private <T> void append(String name, T criterion, List<String> criteria)
	{
		this.append(name, criterion, criteria, null);
	}
	
	private <T> void append(String name, T criterion, List<String> criteria, Function<T, String> serializer)
	{
		if(criterion != null)
		{
			String serialized = serializer == null ? criterion.toString() : serializer.apply(criterion);
			
			if(serialized != null)
			{
				criteria.add(name + "=" + serialized);
			}
		}
	}
	
	private <T> void appendList(String name, List<T> criterion, List<String> criteria, Function<T, String> serializer)
	{
		if(criterion != null)
		{
			for(T entry : criterion)
			{
				criteria.add(name + "=" + serializer.apply(entry));
			}
		}
	}
	
	private <T, K> void appendMap(String name, Map<T, K> criterion, List<String> criteria, Function<T, String> keySerializer, Function<K, String> valueSerializer)
	{
		if(criterion != null && !criterion.isEmpty())
		{
			List<String> entries = new ArrayList<String>();
			
			for(Entry<T, K> entry : criterion.entrySet())
			{
				entries.add(keySerializer.apply(entry.getKey()) + "=" + valueSerializer.apply(entry.getValue()));
			}
			
			criteria.add(name + "=" + entries.stream().collect(Collectors.joining(",", "{", "}")));
		}
	}
	
	@Nullable
	private static String serializeMinMaxBounds(MinMaxBounds<?> bounds)
	{
		return Util.serializeBounds(bounds.min(), bounds.max());
	}
	
	@Nullable
	private static String serializeWrappedMinMaxBounds(WrappedMinMaxBounds bounds)
	{
		return Util.serializeBounds(bounds.min(), bounds.max());
	}
	
	@Nullable
	private static <T> String serializeNegatableCriterion(NegatableCriterion<T> criterion)
	{
		return serializeNegatableCriterion(criterion, null);
	}
	
	@Nullable
	private static <T> String serializeNegatableCriterion(NegatableCriterion<T> criterion, Function<T, String> serializer)
	{
		if(criterion.getCriterion() != null)
		{
			String serialized = serializer == null ? criterion.getCriterion().toString() : serializer.apply(criterion.getCriterion());
			
			if(criterion.isNegated())
			{
				serialized = '!' + serialized;
			}
			
			return serialized;
		}
		
		return null;
	}
	
	public static class NegatableCriterion<T>
	{
		private T criterion;
		private boolean negated;
		
		public NegatableCriterion()
		{
			super();
		}
		
		public NegatableCriterion(T criterion, boolean negated)
		{
			this.criterion = criterion;
			this.negated = negated;
		}
		
		public T getCriterion()
		{
			return this.criterion;
		}
		
		public void setCriterion(T criterion)
		{
			this.criterion = criterion;
		}
		
		public boolean isNegated()
		{
			return this.negated;
		}
		
		public void setNegated(boolean negated)
		{
			this.negated = negated;
		}
	}
	
	public static class SelectorTypes
	{
		public static final String NEAREST_PLAYER = "p";
		public static final String RANDOM_PLAYER = "r";
		public static final String ALL_PLAYERS = "a";
		public static final String ALL_ENTITIES = "e";
		public static final String SENDER = "s";
	}
	
	public static enum Sort
	{
		NEAREST,
		FURTHEST,
		RANDOM,
		ARBITRARY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	@Override
	public boolean isDefault()
	{
		return false;
	}
}
