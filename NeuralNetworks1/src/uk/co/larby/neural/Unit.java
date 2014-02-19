package uk.co.larby.neural;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import uk.co.larby.neural.Perceptron.TestResult;


/**A unit represents an instance of a computing unit*/
public class Unit implements IUnit {
	
	//instance variable declarations
	private ActivationFunction _activationFunction;
	private List<Input> _inputs;
	private double _learningRate;
	private int _epoch;
	
	/**Accessor for Activation Function*/
	@Override
	public ActivationFunction getActivationFunction()
	{
		return _activationFunction;
	}
	
	@Override
	public TestResult Test(DataSet<?> ds, Hashtable<String, Boolean> Classifier, List<Integer> Attributes)
	{
		throw new RuntimeException("Not yet implemented");
	}
	
	/**Mutator for Activation Function
	 * @param AF The type of activation function the unit implements*/
	@Override
	public void setActivationFunction(ActivationFunction Af)
	{
		_activationFunction = Af;
	}
	
	/**Accessor for Activation Function*/
	public List<Input> getInputs()
	{
		return _inputs;
	}
	/**Mutator for Activation Function.
	 * @param a new collection of inputs to replace the current collection*/
	@Override
	public void setInputs(List<Input> Inputs)
	{
		_inputs = Inputs;
	}
	 
	@Override
	public Input getInputAt(int index)
	{
		return _inputs.get(index);
	}
	
	/**Adds a new input to the inputs collection */
	@Override
	public void addInput(Input input)
	{
		_inputs.add(input);
	}
	
	@Override
	public double getLearningRate()
	{
		return _learningRate;
	}
	
	@Override
	public int getEpoch()
	{
		return _epoch;
	}
	
	/**
	 * Default Constructor initialises object to default values
	 */
	public Unit()
	{
		setDefaults();
	}
	
	/**Constructor specifying an activation function
	 * @param af The activation function to be applied to this unit
	 * */
	public Unit(ActivationFunction af)
	{
		this();
		_activationFunction = af;
	}
	
	/**Sets up the unit into a default state */
	private void setDefaults()
	{
		_inputs = new ArrayList<Input>();
		_activationFunction = null;
		_learningRate = 0;
		_epoch = 0;
	}
	
	/**Calculates the net value of the inputs to the current unit */
	@Override
	public double Net()
	{
		double d = 0;
		
		//Sum weights * input values
		for (Input i: getInputs())
		{
			try {
				d += i.getValue() * i.getWeight();
			} catch (Exception e) {
				System.out.println(String.format("Exception {0} thrown while calculating NET value", e.getMessage()));
				e.printStackTrace();
			}
		}
		
		return d;
	}
	
	/**Calculates a Y coordinate for the unit given an X coordinate */
	@Override
	public double getY(double x)
	{
		//ToDo: Implement Logic
		return 0;
	}
	
	/**Calculates the output of the unit based in the current set of inputs */
	@Override
	public double getOutput()
	{
		IActivationFunction func = ActivationFunctions.get(_activationFunction);
		return func.run(this);
	}
	
	/**
	 * Generic method providing the unit output as a specified data type.  
	 * At present this only supports boolean and double return types as these are useful 
	 * for this course work, but can be extended to support other return types as needed
	 * @param c class type defining the return type of the output
	 * @return the unit output in the specified format
	 */
	public <t> t getOutput(Class<t> c)
	{
		double output = getOutput();
	
		switch(c.getCanonicalName())
		{
		case "java.lang.Boolean":
			if (output == 1d || output == 0d)
				return c.cast(output == 1d);
			else
				throw new RuntimeException("Output cannot be converted to a boolean type as it does not equal 1 or 0.  Output value: %s");
		case "java.lang.Double":
			return c.cast(output);
		default:
			throw new RuntimeException(String.format("No logic is defined for casting an output to type %s", c.getCanonicalName()));
		}	
	}
	
	//ToDO: Assumes input numbers correspond to data set member element index - refactor
	@Override
	public void add(DataSetMember<Double> dsm, List<Integer> Attributes)
	{	
		try
		{
			getInputAt(0).setWeight(getInputAt(0).getWeight() +  getInputAt(0).getValue());
					
			for (int i = 1; i < getInputs().size(); i++)
			{
				getInputAt(i).setWeight(getInputAt(i).getWeight() + dsm.getValueAt(Attributes.get(i-1)));
			}			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	//ToDO: Assumes input numbers correspond to data set member element index - refactor
	@Override
	public void subtract(DataSetMember<Double> dsm, List<Integer> Attributes)
	{	
		try
		{
			//Bias will subtract the static value from the input
			getInputAt(0).setWeight(getInputAt(0).getWeight() -  getInputAt(0).getValue());
	
			//For each input, subtract the data set member value from the input 
			for (int i = 1; i < getInputs().size(); i++)
			{
				getInputAt(i).setWeight(getInputAt(i).getWeight() -  dsm.getValueAt(Attributes.get(i-1)));
			}	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
