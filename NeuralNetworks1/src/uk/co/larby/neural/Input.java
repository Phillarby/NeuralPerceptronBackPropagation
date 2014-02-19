package uk.co.larby.neural;

/**
 * An input represents an input into a unit, and encapsulated a single value and a weight.
 * @author Phil Larby
 */
public class Input {
	
	private IValue _valueObject;
	private double _weight;
	
	//Accessors and mutators	
	public void setValueObject(IValue Value) {_valueObject = Value;}
	public double getWeight(){ return _weight; }	
	public void setWeight(double Weight){_weight = Weight;}
	public double getValue() {return _valueObject.getOutput(); }
	
	/**
	 * Default constructor sets default values
	 */
	public Input()
	{
		setDefaults();
	}
	
	/**
	 * Constructor specifying value but leaving default weight of 0
	 * @param Value
	 */
	public Input(IValue Value)
	{
		this();
		_valueObject = Value;
	}
	
	/**
	 * Constructor specifying weight and value
	 * @param Value
	 * @param Weight
	 */
	public Input(IValue Value, double Weight)
	{
		this(Value);
		_weight = Weight;
	}
	
	//Configure the object to a default state with 0 value and 0 weight
	private void setDefaults()
	{
		//Create a new anonymously typed IValue object with zero value
		_valueObject = new IValue(){
			public double getOutput()
			{
				return 0;
			}
		};
		
		//Set weight to zero
		_weight = 0;
	}
}
