package uk.co.larby.neural;


/**
 * A container class for static implementations of activation function logic.  Activation functions are returned as 
 * anonymous instances of IActivationFunction interfaces to provide function pointer like operation
 * @author larbyp
 */
public class ActivationFunctions {
	
	/**
	 * A static factory providing activation function logic wrapped up in an IActivationFunction interface
	 * @param af Activation Function Enum
	 * @return An anonymously typed instance of an IActivationFunction interface encapsulating the logic for the requested activation function
	 */
	public static IActivationFunction get(ActivationFunction af)
	{
		switch (af)
		{
		case ThresholdLessThanZero:
			return getThresholdLessThanZero();
		case ThresholdLessThanEqualToZero:
			return getThresholdLessThanEqualToZero();
		case ThresholdGreaterThanZero:
			return getThresholdGreaterThanZero();
		case ThresholdGreaterThanEqualToZero:
			return getThresholdGreaterThanEqualToZero();
		case Sigmoid:
			return getSigmoid();
		default:
			break;
		}
		
		//If the requested activation function is not configured in the switch structure then throw an exception
		throw new RuntimeException(String.format("IActivateFunction.get is not configured for activation function {0}", af.name()));
	}
	
	//Activation function logic for T(<0,1,0)
	private static IActivationFunction getThresholdLessThanZero()
	{
		return new IActivationFunction(){
			public double run(Unit u)
			{
				if (u.Net() < 0) return 1;
				return 0;
			}
		};
	}
	
	//Activation function logic for T(<=0,1,0)
	private static IActivationFunction getThresholdLessThanEqualToZero()
	{
		return new IActivationFunction(){
			public double run(Unit u)
			{
				if (u.Net() <= 0) return 1;
				return 0;
			}
		};
	}
	
	//Activation function logic for T(>0,1,0)
	private static IActivationFunction getThresholdGreaterThanZero()
	{
		return new IActivationFunction(){
			public double run(Unit u)
			{
				if (u.Net() > 0) return 1;
				return 0;
			}
		};
	}
	
	//Activation function logic for T(<=0,1,0)
	private static IActivationFunction getThresholdGreaterThanEqualToZero()
	{
		return new IActivationFunction(){
			public double run(Unit u)
			{
				if (u.Net() >= 0) return 1;
				return 0;
			}
		};
	}
	
	//Activation function logic for sigmoid(net)
	private static IActivationFunction getSigmoid()
	{
		return new IActivationFunction(){
			public double run(Unit u)
			{
				return 1/(1 + java.lang.Math.exp(-u.Net()));
			}
		};
	}
}
