/**
 * 
 */
package uk.co.larby.neural;

import java.util.ArrayList;
import java.util.List;

/**
 * @author larbyp
 * A network encapsulates collections of units and static value inputs, and builds connection between them to
 * allow calculations to be performed on the network.
 */
public class Network {
	
	private List<IUnit> _units; //A collection of computing units
	private List<IValue> _staticValues; //A collection of static values
	
	public Network()
	{
		_units = new ArrayList<IUnit>();
		_staticValues  = new ArrayList<IValue>();
	}
	
	/**
	 * Accessor for static values
	 * @param index
	 * @return
	 */
	public IValue getStaticInput(int index)
	{
		return _staticValues.get(index);
	}

	/**
	 * Accessor for a network unit
	 * @param index
	 * @return
	 */
	public IUnit getUnit(int index)
	{
		return _units.get(index);
	}
	
	/**
	 * Clears all the inputs associated with the specified unit, and returns the weights that were associated with them
	 * @param unitIndex The index of the unit to have its inputs cleared
	 * @return An array of the weights associated with the unit, in order of input index
	 */
	public double[] ClearUnitInputs(int unitIndex)
	{
		//Get the inputs from the unit
		List<Input> unitInputs = _units.get(unitIndex).getInputs();
		double[] ret = new double[unitInputs.size()];
		for (int i = 0; i < unitInputs.size(); i++)
		{
			ret[i] = unitInputs.get(i).getWeight();
		}
		
		_units.get(unitIndex).setInputs(new ArrayList<Input>());
		
		return ret;
	}
	
	/**
	 * Assigns a static value to a new input on the computing unit 
	 * with the specified index and assigns it the specified weight
	 * @param unitIndex The index of the target unit in the collection
	 * @param valueIndex The index of the static value in the collection
	 */
	public void setStaticInput(int unitIndex, int valueIndex, double weight)
	{
		_units.get(unitIndex).addInput(new Input(_staticValues.get(valueIndex), weight));
	}
	
	/**
	 * Assigns the supplied static value to a new input on the computing unit with the 
	 * specified index and assigns it a weight of 0
	 * @param unitIndex The index of the target unit in the collection
	 * @param value The value object to be assigned
	 */
	public void setStaticInput(int unitIndex, IValue value)
	{
		//add the IValue object to the static values collection
		_staticValues.add(value);
		
		//get the index of the object just added
		int i = _staticValues.indexOf(value);
		
		//Assign the value to the unit
		setStaticInput(unitIndex, i, 0d);
	}
	
	/**
	 * Assigns the supplied static value to a new input on the computing unit with the 
	 * specified index and assigns it the specified weight
	 * @param unitIndex The index of the target unit in the collection
	 * @param value The value object to be assigned
	 * @param weight The weight to be assigned
	 */
	public void setStaticInput(int unitIndex, IValue value, double weight)
	{
		//add the IValue object to the static values collection
		_staticValues.add(value);
		
		//get the index of the object just added
		int i = _staticValues.indexOf(value);
		
		//Assign the value to the unit
		setStaticInput(unitIndex, i, weight);
	}
	
	public void setStaticInput(int unitIndex, IValue value, double weight, int index)
	{
		//add the IValue object to the static values collection
		addStaticInput(index, value);
		
		//get the index of the object just added
		int i = _staticValues.indexOf(value);
		
		//Assign the value to the unit
		setStaticInput(unitIndex, i, weight);
	}
	
	/**
	 * Adds a unit to the units collection and returns the index
	 * @param unit The unit to be added
	 * @return The index of the unit in the collection
	 */
	public int addUnit(IUnit unit)
	{
		_units.add(unit);
		return _units.indexOf(unit);
	}
	
	/**
	 * Adds an item to the collection of static inputs
	 * @param input The static value to be added
	 * @return The index of the added item
	 */
	public int addStaticInput(IValue input)
	{
		_staticValues.add(input);
		return _staticValues.indexOf(input);
	}
	
	/**
	 * Adds an item to the collection of static inputs at the specified index location. Replaces any static input
	 * that already exists at the specified index location
	 * @param input The static value to be added
	 * @param index The index of the item in the static values collection
	 * @return The index of the added item
	 */
	public int addStaticInput(int index, IValue input)
	{	
		//ToDO: Add error handling for IndexOutOfBoundsExceptions
		_staticValues.remove(index);
		_staticValues.add(index, input);
		return _staticValues.indexOf(input);
	}
	
	/**
	 * Connects the output of one unit to the input of another applying a weight of 0
	 * @param OutputUnitIndex
	 * @param InputUnitIndex
	 */
	public void connect (final int OutputUnitIndex, int InputUnitIndex)
	{
		connect(OutputUnitIndex, OutputUnitIndex, 0);
	}
	
	/**
	 * Connects the output of one unit to the input of another applying the specified weight
	 * @param OutputUnitIndex
	 * @param InputUnitIndex
	 * @param weight
	 */
	public void connect (final int OutputUnitIndex, int InputUnitIndex, double weight)
	{
		//get a reference the the output.  Hopefully using an anonymous inner class 
		//will allow a reference to be maintained to the output and I won't fall 
		//foul of java's tendency to pass by value as I want to maintain a 'live' 
		//connection between the units... Wish me luck!
		IValue output = new IValue()
		{ 
			public double getOutput() 
			{
				return _units.get(OutputUnitIndex).getOutput();
			}; 
		};  
		
		//Add the output to the input of the target unit
		_units.get(InputUnitIndex).addInput(new Input(output, weight));
	}
}
