package uk.co.larby.app.Neural;

import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.*;

import uk.co.larby.helpers.*;
import uk.co.larby.neural.*;

public class Coursework {

	DataSet<Double> ds; //Reference to a dataset with  attributes of double type
	
	/*
	 * ---START SECTION 1---
	 * Members that are only applicable to the perceptron learning algorithm
	 */
	
	//Target class for identification.  This is only used in the perceptron learning algorithm and 
	//should be refactored to be encapsulated within this process
	String targetClass; 
	
	//The maximum running time for the perceptron learning algorithm.  This is also only used
	//for the perceptron learning algorithm so should be refactored to a more appropriate scope
	//than a class level instance variable
	int runForSeconds; 
	
	//A collection of units used for the perceptron learning algorithm.  This should be refactored to use a Network object, 
	//but remains in this format at the moment as it was created before the network class
	ArrayList<IUnit> units; 
	
	//For testing we need to know which classes are to be positively categorised (above the line) 
	//and which are negatively categorised (below the line).  We will use a hash table to associate
	//a boolean with each class to indicate its desired end position
	Hashtable<String, Boolean> classDivider;
	
	//Retrieve a unit by index. A wrapper for the ArrayList get function, but with a neater syntax 
	//This can be refactored into the network object
	private IUnit unit(int index)
	{
		//Throw an exception if requested index is out of range
		if (index > units.size() - 1 || index < 0)
			throw new RuntimeException("Attempting to retreive a unit with index outside of the collection bounds.");
		
		return units.get(index);
	}

	/**
	 * Populate the classDivider collection with information on which classes should be 
	 * positively or negatively classified to give a correct classification result
	 * @param Positive A collection of the class numbers that should be positively classified
	 * @param Negative A collection of the class numbers that should be negatively classified
	 */
	private void ConfigureClassifierDivision(List<Integer> Positive, List<Integer> Negative)
	{
		//Build a collection of all positive and negative values to ensure all are correctly classified
		List<Integer> combined = new ArrayList<Integer>();
		combined.addAll(Positive);
		combined.addAll(Negative);
		
		//Classify all members as elements of the positive (above the line) or negative (below the line)
		//sets.  Boolean false indicates negative and True indicates positive
		for(Integer i : combined)
		{
			classDivider.put(ds.classifiers().get(i), Positive.contains(i));
		}
	}	

	/**
	 * Inner class to capture the values associated with the most successful weight configuration
	 * @author larbyp
	 *
	 */
	private class pocket
	{
		private int correct;
		private int incorrect;
		private List<Input> inputs;
		
		/**
		 * Default constructor sets default instance variable values
		 */
		public pocket()
		{
			correct = 0;
			incorrect = 0;
			inputs = new ArrayList<Input>();
		}
		
		/**
		 * Remember the specified inputs
		 * @param a collection of inputs to remember
		 */
		public void setInputs (List<Input> Inputs)
		{
			//Clear any persistent data from previous runs
			inputs.clear();
			
			//Copy value and weight from current input to new objects . New objects are 
			//constructed as we don't want to continue to reference input objects that 
			//may change
			for (final Input i : Inputs)
			{
				inputs.add
				(
					new Input(
						new IValue() { public double getOutput() { return i.getValue(); } },
						i.getWeight())
				);
			}
		}
	}
	
	
	/*
	 * ---END SECTION 1---
	 * Members that are only applicable to the perceptron learning algorithm
	 */
	
	/**
	 * default constructor loads the iris dataset and initialises class scope objects
	 */
	public Coursework()
	{
		//first we must import the data set from the iris.data file
		ds = Helpers.LoadIrisData();
		
		//Set up for perceptron learning - this should be refactored to have a more appropriate scope
		classDivider =  new Hashtable<String, Boolean>();
		runForSeconds = 3; //Maximum number of seconds to run the perceptron learning algorithm before forcing a stop
		units = new ArrayList<IUnit>(); //Initialise the collection of units for the perceptron learning process
	}
	
	public void TwoA()
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Neural Networks (311) Coursework 1 - Part 2A");
		System.out.println("Submission from Phil Larby ");
		System.out.println("-------------------------------------------");
		System.out.println("Using a single perceptron to identify class 1 from attribute 1");
		System.out.println("-------------------------------------------");
		System.out.println("Press <enter> to continue");
		
		in.nextLine();
		
		//We are trying to classify the first class, which has index 0
		int TargetClassIndex = 0;
		
		//We are using the first set of attributes, which have index 0
		List<Integer> AttributeIndices = Arrays.asList(0);
		
		//Run the classification
		classify(TargetClassIndex, AttributeIndices); 
		
		System.out.println("-------------------------------------------");
		System.out.println("Done. Press <enter> to complete");
		
		in.nextLine();
		in.close();
	}
	
	public void TwoB()
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Neural Networks (311) Coursework 1 - Part 2B");
		System.out.println("Submission from Phil Larby ");
		
		List<Integer> AttributeIndices = Arrays.asList(0);
		
		for (int TargetClassIndex = 1; TargetClassIndex < ds.classifiers().size(); TargetClassIndex++)
		{
			System.out.println("=======================================================================");
			System.out.println(String.format("a single perceptron classifying class %s using attribute 1", TargetClassIndex + 1));
			
			classify(TargetClassIndex, AttributeIndices); 
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Done. Press <enter> to complete");
		
		in.nextLine();
		in.close();
	}
	
	public void TwoC()
	{	
		Scanner in = new Scanner(System.in);
		
		System.out.println("Neural Networks (311) Coursework 1 - Part 2C");
		System.out.println("Submission from Phil Larby ");
		
		for (int TargetClassIndex = 0; TargetClassIndex < ds.classifiers().size(); TargetClassIndex++)
		{
			//We know there are 4 attributes for the iris data set - generalise this in place of hard coding
			for (int AttributeIndex = 0; AttributeIndex < 4; AttributeIndex++)
			{
				System.out.println("=======================================================================");
				System.out.println(String.format("a single perceptron classifying class %s using attribute %s", TargetClassIndex + 1, AttributeIndex +1));
				
				classify(TargetClassIndex, Arrays.asList(AttributeIndex)); 
			}
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Done. Press <enter> to complete");
		
		in.nextLine();
		in.close();
	}

	public void TwoD()
	{
		/*
		 * Testing classification of classes using combinations of all four attributes
		 * Test Cases
		 * 
		 * Classes: 0 (Iris-setosa)
		 *          1 (Iris-versicolor)
		 *          2 (Iris-virginica)
		 * 
		 * Attributes to test:
		 * 1
		 * 1, 2
		 * 1, 2, 3
		 * 1, 2, 3, 4
		 * 1, 3
		 * 1, 3, 4
		 * 1, 4
		 * 2
		 * 2, 3
		 * 2, 3, 4
		 * 2, 4
		 * 3
		 * 3, 4
		 * 4
		 */
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Neural Networks (311) Coursework 1 - Part 2D");
		System.out.println("Submission from Phil Larby ");
		
		//Lets loop through the classes as we want to check for each
		for (int TargetClassIndex = 0; TargetClassIndex < ds.classifiers().size(); TargetClassIndex++)
		{
			classify(TargetClassIndex, Arrays.asList(0));
			classify(TargetClassIndex, Arrays.asList(0, 1));
			classify(TargetClassIndex, Arrays.asList(0, 1, 2));
			classify(TargetClassIndex, Arrays.asList(0, 1, 2, 3));
			classify(TargetClassIndex, Arrays.asList(0, 2));
			classify(TargetClassIndex, Arrays.asList(0, 2, 3));
			classify(TargetClassIndex, Arrays.asList(0, 3));
			classify(TargetClassIndex, Arrays.asList(1));
			classify(TargetClassIndex, Arrays.asList(1, 2));
			classify(TargetClassIndex, Arrays.asList(1, 2, 3));
			classify(TargetClassIndex, Arrays.asList(1, 3));
			classify(TargetClassIndex, Arrays.asList(2));
			classify(TargetClassIndex, Arrays.asList(2, 3));
			classify(TargetClassIndex, Arrays.asList(3));
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Done. Press <enter> to complete");
		
		in.nextLine();
		in.close();
	}
	
	//Use a Backpropagation network and repeat the experiments of a) to d) above.
	public void TwoE()
	{	
		Scanner in = new Scanner(System.in);
		
		System.out.println("Neural Networks (311) Coursework 1 - Part 2E");
		System.out.println("Submission from Phil Larby ");
		System.out.println();
		
		//Define attributes using a comma separated list that gets parsed into an int array
		System.out.print("Specify the input attribute numbers separated by commas if more than one e.g 1,2,3: ");
		String[] at = in.next().split(",");
		int[] Attributes = new int[at.length];
		for (int i = 0; i < at.length; i++)
		{
			Attributes[i] = Integer.parseInt(at[i]) - 1; //Attribute array is zero indexed so subtract one from specified value
		}
		
		//Define attributes using a comma separated list to define classification targets
		System.out.print("Specify the target classification class numbers separated by commas if more than one e.g 1,2,3: ");
		String[] target = in.next().split(",");
		int[] TestForClasses = new int[target.length];
		for (int i = 0; i < target.length; i++)
		{
			TestForClasses[i] = Integer.parseInt(target[i]); 
		}
		
		//Use a network with the following configuration:
		System.out.println("As present only 2 hidden units can be used! I haven't fixed this bug yet... Sorry!");
		int HiddenUnits = 2; //Only configuration supported at present is two hidden units
	
		//Define parameters for the back propagation algorithm
		System.out.print("Specify the epoch error target: ");
		double ErrorTarget = in.nextDouble(); //The target for the aggregated errors in a learning epoch
		
		System.out.print("Specify the maximum allowed training epochs: ");
		int MaxTrainingEpochs = in.nextInt(); //The maximum number of training epochs to run if the error target is not reached
		
		System.out.print("Specify the learning rate: ");
		double LearningRate = in.nextDouble(); //The learning rate (applied equally to all weight updates)
		
		System.out.print("Specify the positive classification threshold: ");
		double ClassificationThreshold = in.nextDouble(); //The maximum divergence from the target output to still qualify as a valid classification
		
		System.out.print("Display epoch error sums during training? (Y/N): ");
		String dispErrorSum = in.next();
		Boolean de = (dispErrorSum.equals("Y") || dispErrorSum.equals("y")) ? true : false;
		
		//Run the algorithm
		runBackProp(Attributes, HiddenUnits, TestForClasses, ErrorTarget, MaxTrainingEpochs, LearningRate, ClassificationThreshold, false, de);
		
		System.out.println("-------------------------------------------");
		System.out.println("Done. Press <enter> to complete");
		
		in.nextLine();
		in.nextLine();
		in.close();
	}

	
	private void runBackProp(
			int[] Attributes, 
			int HiddenUnits,
			int[] TestForClasses, 
			double ErrorTarget, 
			int MaxTrainingEpochs, 
			double LearningRate, 
			double ClassificationThreshold,
			boolean IndividualOutput,
			boolean EpochOutput)
	{
		//Run the back propagation algorithm 
		
		int[] a = new int[Attributes.length];
		for (int at = 0; at < Attributes.length; at++) { a[at] = Attributes[at] + 1;} //Add one to output as attributes are zero indexed
		
		System.out.println(String.format("Attempting to classify class %s using attribute %s...", TestForClasses[0], Arrays.toString(a)));
		System.out.println(String.format("Network Inputs: %s", Attributes.length));
		System.out.println(String.format("Hidden Units: %s", HiddenUnits));
		System.out.println(String.format("Output Units: %s", TestForClasses.length));
		System.out.println(String.format("Total epoch error target: %s", ErrorTarget));
		System.out.println(String.format("Maximum Epochs: %s", MaxTrainingEpochs));
		System.out.println(String.format("Learning Rate: %s", LearningRate));
		System.out.println(String.format("Classification Threshold: %s", ClassificationThreshold));
		System.out.println("Running back propagation learning algorithm.  This may take a few minutes... Please wait...");
		BackPropResults res = BackProp(Attributes, HiddenUnits, TestForClasses, ErrorTarget, MaxTrainingEpochs, LearningRate, ClassificationThreshold, IndividualOutput, EpochOutput);
		System.out.println(String.format("Correctly classified %s of %s members", res.CorrectClassifications, ds.size()));
		System.out.println(res.Message);
	}
	
	int[] _inputIndices;
	int[] _hiddenIndices;
	int[] _outputIndices;
	
	/**
	 * Create a network with the specified number of inputs, a single hidden layer with the specified 
	 * number of units and the specified number of output units.  All inputs are connected to all hidden units
	 * and all hidden units are connected to all outputs
	 * @param inputs
	 * @param hidden
	 * @param outputs
	 * @return
	 */
	private Network CreateNetwork(int Inputs, int Hidden, int Outputs, ActivationFunction Function)
	{
		Network _net = new Network();
		
		//Create collection of units 
		IValue[] inputs = new IValue[Inputs];
		IUnit[] hidden = new IUnit[Hidden];
		IUnit[] outputs = new IUnit[Outputs];
		
		//Instantiate inputs and units
		for (int i = 0; i < Inputs; i++) { inputs[i] = Helpers.getStaticValue(1d); }
		for (int i = 0; i < Hidden; i++) { hidden[i] = new Unit(Function); }
		for (int i = 0; i < Outputs; i++) { outputs[i] = new Unit(Function); }
		
		//Add the units and inputs to the network and get their indices
		_inputIndices = new int[Inputs];
		_hiddenIndices = new int[Hidden];
		_outputIndices = new int[Outputs];
		for (int i = 0; i < Inputs; i++) { _inputIndices[i] = _net.addStaticInput(inputs[i]); }
		for (int i = 0; i < Hidden; i++) { _hiddenIndices[i] = _net.addUnit(hidden[i]); }
		for (int i = 0; i < Outputs; i++) { _outputIndices[i] = _net.addUnit(outputs[i]); }
		
		//Connect input values to hidden units
		for (int i = 0; i < Inputs; i++)
		{
			for (int h = 0; h < Hidden; h++)
			{
				//Connect the input to the hidden unit with a random weight where -1 < w < 1
				_net.setStaticInput(_hiddenIndices[h], _inputIndices[i], 2 * java.lang.Math.random() - 1);
			}
		}
		
		//Connect hidden units to output units
		for (int h = 0; h < Hidden; h++)
		{
			for (int out = 0; out < Outputs; out++)
			{
				//Connect the hidden unit's output to the output unit's input with a random weight where -1 < w < 1
				_net.connect(_hiddenIndices[h], _outputIndices[out], 2 * java.lang.Math.random() - 1);
			}
		}
		
		return _net;
	}
	
	/**
	 * Perform a back propagation training process
	 * @param Attributes The index of the input attributes to be used for training
	 * @param HiddenUnits The number of hidden units to include in the hidden layer
	 * @param TestForClasses The indices of the classes being categorised
	 * @param ErrorTarget The maximum error threshold allowable for a positive identification
	 * @param MaxTrainingEpochs The maximum number of training epochs before the learning process terminates
	 * @param LearningRate The learning rate to be applied
	 */
	public BackPropResults BackProp(
			int[] Attributes, 
			int HiddenUnits, 
			int TestForClasses[], 
			double ErrorTarget, 
			int MaxTrainingEpochs, 
			double LearningRate, 
			double ClassificationThreshold,
			boolean IndividualOutput,
			boolean EpochOutput)
	{
		
		if (IndividualOutput) 
		{
			System.out.println("Running back propogation learning algorithm");
			
			System.out.print("Attempting to identify class(es): ");
			for (int a : TestForClasses)
			{
				System.out.print(String.format(" %s ", ds.classifiers(a - 1)));
			}
			System.out.println();
			
			System.out.print("Using Attribute(s): ");
			for (int a : Attributes)
			{
				System.out.print(String.format(" %s ", a));
			}
			System.out.println();
			
			System.out.println(String.format("Error Threshold for positive classification: %s ", ErrorTarget));
			System.out.println(String.format("Learning Rate: %s ", LearningRate));
			System.out.println(String.format("Maximum number of training epochs: %s ", MaxTrainingEpochs));
		}

		int OutputUnits = TestForClasses.length; //the Number of output units is determined by the expected output pattern
		
		//Create a network with the specified configuration and using sigmoid activation functions
		Network net = CreateNetwork(Attributes.length, HiddenUnits, OutputUnits, ActivationFunction.Sigmoid);
		
		if (IndividualOutput) 
		{
			System.out.println("Created network... ");
			
			System.out.println(String.format("Inputs: [%s]", _inputIndices.length));
			for (int a : _inputIndices)
			{
				System.out.println(String.format("       Value: [%s] ", net.getStaticInput(a).getOutput()));
			}
			
			System.out.println(String.format("Hidden Units: [%s]", _hiddenIndices.length));
			for (int hu = 0; hu < _hiddenIndices.length; hu++)
			{
				for (Input in : net.getUnit(hu).getInputs()) System.out.println(String.format("       Value: [%s] Weight [%s] ", in.getValue(), in.getWeight()));
				System.out.println("       -");
			}
			
			System.out.println(String.format("Outputs Units: [%s]", _outputIndices.length));
			for (int ou = 0; ou < _outputIndices.length; ou++)
			{
				for (Input in : net.getUnit(_outputIndices[ou]).getInputs()) System.out.println(String.format("       Value: [%s] Weight [%s] ", in.getValue(), in.getWeight()));
				System.out.println("       -");
			}
			
			System.out.print("Using Attribute(s): ");
			for (int a : Attributes)
			{
				System.out.print(String.format(" %s ", a));
			}
			System.out.println();
			
			System.out.println(String.format("Error Threshold for positive classification: %s ", ErrorTarget));
			System.out.println(String.format("Learning Rate: %s ", LearningRate));
			System.out.println(String.format("Maximum number of training epochs: %s ", MaxTrainingEpochs));
		}
		
		double errorSum = Double.MAX_VALUE;  //Holder for the aggregated output error during a single training epoch
		double[][] outputWeightDeltas; //Holder for the aggregated weight deltas for the output layer
		double[][] hiddenWeightDeltas; //Holder for the aggregated weight deltas for the hidden layer
		double [][] hiddenWeights = new double[_hiddenIndices.length][_inputIndices.length]; //Holder for hidden layer weights
		
		//Loop training algorithm until the error target is reached or the specified maximum training epochs are reached
		for (int epoch = 0; epoch < MaxTrainingEpochs && errorSum > ErrorTarget; epoch++)
		{
			//Reset local values for this training epoch
			outputWeightDeltas = new double[OutputUnits][HiddenUnits]; //First dimension indicates unit, second dimension holds weights for each input
			hiddenWeightDeltas = new double[HiddenUnits][Attributes.length]; //First dimension indicates unit, second dimension holds weights for each input
			errorSum = 0; //Reset aggregated error sum
			
			//Train network
			for (int i = 0; i < ds.size(); i++)
			{
				//Output to console
				if (IndividualOutput) System.out.println("---------------");
				
				//Select the next data set member
				DataSetMember<Double> dsm = ds.getMemberAt(i);
				
				//Output to console
				if (IndividualOutput) 
				{
					System.out.print(String.format("Selected Item: %s, ", dsm.getCategory()));
					for (int a = 0; a < Attributes.length; a++)
					{
						System.out.print(String.format("Attribute %s value: %s", a, dsm.getValueAt(Attributes[a])));
					}
					System.out.println();
				}
						
				//Calculate the expected output
				double[] ExpectedOutput = new double[TestForClasses.length]; //The number of output units corresponds with the number of classes being classified
				for(int out = 0; out < ExpectedOutput.length; out++)
				{
					//includes subtraction in TestForClasses[out] - 1 as dataset classifier is zero indexed, but classes are specified from 1
					ExpectedOutput[out] = ds.getClassifierIndex(dsm.getCategory()) == TestForClasses[out] - 1 ? 1d : 0d; 
				}
			
				//Output to console
				if (IndividualOutput) 
				{
					System.out.print("Expected Output: [");
					for (int e = 0; e < ExpectedOutput.length; e++)
					{
						System.out.print(String.format(" %s ", ExpectedOutput[e]));
					}
					System.out.println("]");
				}
				
				//Clear down any existing inputs to the network ready for next cycle 
				//remember any cleared input weights for re-application with subsequent input values
				for (int hi = 0; hi <_hiddenIndices.length; hi++)
				{
					hiddenWeights[hi] = net.ClearUnitInputs(hi);
				}
				
				//Output to console
				if (IndividualOutput)
				{
					System.out.println("Confirm any existing inputs have been cleared:");
					for (int h = 0 ; h < HiddenUnits; h++)
					{
						System.out.println(String.format("Hidden unit [%s]: Inputs [%s]", h, net.getUnit(_hiddenIndices[h]).getInputs().size()));
					}
				}
				
				//Update the network's input values
				for (int hi = 0; hi <_hiddenIndices.length; hi++)
				{
					for (int a = 0; a < Attributes.length; a++)
					{
						net.setStaticInput(_hiddenIndices[hi], dsm.getIValueAt(Attributes[a]), hiddenWeights[hi][a], a);
					}
				}
				
				//Output to console
				if (IndividualOutput)
				{
					System.out.println("Confirm new inputs have been added:");
					for (int h = 0 ; h < HiddenUnits; h++)
					{
						System.out.println(String.format("Hidden unit [%s]: Inputs [%s]", h, net.getUnit(_hiddenIndices[h]).getInputs().size()));
						
						for (Input in : net.getUnit(_hiddenIndices[h]).getInputs())
						{
							System.out.println(String.format("                 -> Value [%s] Weight [%s]", in.getValue(), in.getWeight()));
						}
						
						System.out.println(String.format("                 Output [%s]", net.getUnit(_hiddenIndices[h]).getOutput()));
					}
				}
				
				//Output to console
				if (IndividualOutput)
				{
					System.out.println("Confirm new output unit values:");
					for (int o = 0 ; o < OutputUnits; o++)
					{
						System.out.println(String.format("Output unit [%s]: Inputs [%s]", o, net.getUnit(_outputIndices[o]).getInputs().size()));
						
						for (Input in : net.getUnit(_outputIndices[o]).getInputs())
						{
							System.out.println(String.format("                 -> Value [%s] Weight [%s]", in.getValue(), in.getWeight()));
						}
						
						System.out.println(String.format("                 Output [%s]", net.getUnit(_outputIndices[o]).getOutput()));
					}
				}
				
				//get the output pattern
				double[] outputs = new double[OutputUnits];
				for (int out = 0; out < OutputUnits; out++)
				{
					outputs[out] = net.getUnit(_outputIndices[out]).getOutput();
				}
				
				if (IndividualOutput) 
				{
					System.out.print("Network Output Pattern: [");
					for (int o = 0; o < outputs.length; o++) 
					{
						System.out.print(String.format(" %s ", java.lang.Math.abs(ExpectedOutput[0] - outputs[o]) < ErrorTarget ? 1 : 0));
					}
					System.out.println("]");
				}
				
				//Calculate the output errors
				double[] OutputErrors = new double[OutputUnits];
				for (int u = 0 ; u < OutputUnits; u++)
				{
					OutputErrors[u] = outputs[u] * (1 - outputs[u]) * (ExpectedOutput[u] - outputs[u]);
					errorSum += java.lang.Math.abs(OutputErrors[u]); //Add the output error to the total epoch error
				}
				
				//Output to console
				if (IndividualOutput) 
				{
					System.out.print("Output Errors: [ ");
					for (double e : OutputErrors) System.out.print(String.format(" %s ", e));
					System.out.println("]");
				}
				
				//Calculate the weight delta for the output layer
				for (int ou = 0; ou < OutputUnits; ou++)
				{
					for (int hu = 0; hu < HiddenUnits; hu++)
					{
						//Delta is the product of the output error and the value of the input.  This is aggregated over the training epoch
						outputWeightDeltas[ou][hu] += OutputErrors[ou] * net.getUnit(_hiddenIndices[hu]).getOutput();
					}
				}
				
				//Output to console
				if (IndividualOutput) 
				{ 
					//Calculate the weight delta for the output layer
					
					for (int e = 0; e < outputWeightDeltas.length; e++) 
					{
						for (int f = 0; f < outputWeightDeltas[e].length; f++)
						{
							System.out.print(String.format("Output Weight Delta output:%s input:%s: [ ", e, f));
							System.out.print(String.format(" %s ", outputWeightDeltas[e][f]));
							System.out.println("]");
						}
					}
				}
				
				//Calculate the errors for the hidden layer
				double[] hiddenErrors = new double[HiddenUnits];
				for (int hu = 0; hu < HiddenUnits; hu++)
				{
					//Get the weights from each output to the current hidden unit and aggregate
					double errorWeight = 0;
					for (int ou = 0; ou < OutputUnits; ou++)
					{
						errorWeight += net.getUnit(_outputIndices[ou]).getInputAt(hu).getWeight() * OutputErrors[ou];
					}
					
					//Delta is the product of the output error and the value of the input.  This is aggregated over the training epoch
					hiddenErrors[hu] = net.getUnit(_hiddenIndices[hu]).getOutput() * (1 - net.getUnit(_hiddenIndices[hu]).getOutput()) * errorWeight;
				}

				//Output to console for debug
				if (IndividualOutput)	
				{
					System.out.print("Hidden Layer Errors: ");
					for (int he = 0; he < HiddenUnits; he++)
					{
						System.out.print(String.format(" %s ", hiddenErrors[he]));
					}
					System.out.println();
				}
				
				//Calculate new hidden layer weight delta
				for (int hu = 0; hu < HiddenUnits; hu++)
				{
					double dlt[] = new double [Attributes.length];
					
					//Calculate deltas for current hidden layer
					for (int at = 0; at < net.getUnit(_hiddenIndices[hu]).getInputs().size(); at++)
					{
						//get the value from the hidden unit's input and multiply by the units error
						dlt[at] = net.getUnit(_hiddenIndices[hu]).getInputAt(at).getValue() * hiddenErrors[hu];
					}
					
					hiddenWeightDeltas[hu] = dlt;
				}
				
				//Output to console
				if (IndividualOutput) 
				{ 
					//Calculate the weight delta for the output layer
					
					for (int e = 0; e < hiddenWeightDeltas.length; e++) 
					{
						for (int f = 0; f < hiddenWeightDeltas[e].length; f++)
						{
							System.out.print(String.format("Hidden Weight Delta Hidden Unit:%s input:%s: [ ", e, f));
							System.out.print(String.format(" %s ", hiddenWeightDeltas[e][f]));
							System.out.println("]");
						}
					}
				}
			}
			
			if (EpochOutput) System.out.println(String.format("Epoch: %s ErrorSum: %s", epoch, errorSum));
			
			//Update the output layer weights
			for (int out = 0; out < _outputIndices.length; out++)
			{
				for (int h = 0; h < _hiddenIndices.length; h++)
				{
					double w = net.getUnit(_outputIndices[out]).getInputAt(h).getWeight();
					net.getUnit(_outputIndices[out]).getInputAt(h).setWeight(w + outputWeightDeltas[out][h] * LearningRate);
				}
			}
			
			//ToDo: BUG!
			//THERE IS A PROBLEM HERE AS THE HIDDEN WEIGHT DELTAS SEEM TO BE IN REVERSE ORDER TO WHAT I EXPECT
			//NEED TO CLEAR THIS UP!!!  FOR THE TIME BEING I HAVE HARDCODED THIS FOR 2 HIDDEN UNITS, BUT IT WILL FAIL IF
			//ANOTHER NUMBER OF HIDDEN UNITS IF USED
			if (_hiddenIndices.length != 2) throw new RuntimeException("due to a bug in the BackProp method only networks with two hidden units can be used at present");
			
			//Update the hidden layer weights
//			for (int h = 0; h < _hiddenIndices.length; h++)
//			{
//				for (int a = 0; a < net.getUnit(_hiddenIndices[h]).getInputs().size(); a++)
//				{
//					double w = net.getUnit(_hiddenIndices[h]).getInputAt(a).getWeight();
//					net.getUnit(_hiddenIndices[h]).getInputAt(a).setWeight(w + hiddenWeightDeltas[h][a] * LearningRate);
//				}
//			}
			
			//START: Delete once bug is fixed!
			net.getUnit(0).getInputAt(0).setWeight(net.getUnit(0).getInputAt(0).getWeight() + hiddenWeightDeltas[1][0] * LearningRate);
			net.getUnit(1).getInputAt(0).setWeight(net.getUnit(1).getInputAt(0).getWeight() + hiddenWeightDeltas[0][0] * LearningRate);
			//END: Delete once bug is fixed!
		}
		
		//Evaluate network efficiency
		int resultCount = 0;
		for (int i = 0; i < ds.size(); i++)
		{
			//System.out.println("-------------------------------------------");
			
			//Select the next data set member
			DataSetMember<Double> dsm = ds.getMemberAt(i);
			//System.out.println(String.format("Selected Item: %s, Attribute 1 value: %s", dsm.getCategory(), dsm.getValueAt(0)));
			
			//Clear down any existing inputs to the network ready for next cycle 
			//remember any cleared input weights for re-application with subsequent input values
			for (int hi = 0; hi <_hiddenIndices.length; hi++)
			{
				hiddenWeights[hi] = net.ClearUnitInputs(hi);
			}
				
			//Update the network's input values
			for (int hi = 0; hi <_hiddenIndices.length; hi++)
			{
				for (int a = 0; a < Attributes.length; a++)
				{
					net.setStaticInput(_hiddenIndices[hi], dsm.getIValueAt(Attributes[a]), hiddenWeights[hi][a], a);
				}
			}
			
			//ToDo: Only for single output at present - refactor for multiple output units
			if (_outputIndices.length != 1) throw new RuntimeException("Only network with a single output unit are supported at present");
			
			//Calculate the expected output
			double[] ExpectedOutput = new double[TestForClasses.length]; //The number of output units corresponds with the number of classes being classified
			for(int out = 0; out < ExpectedOutput.length; out++)
			{
				//includes subtraction in TestForClasses[out] - 1 as dataset classifier is zero indexed, but classes are specified from 1
				ExpectedOutput[out] = ds.getClassifierIndex(dsm.getCategory()) == TestForClasses[out] - 1 ? 1d : 0d; 
			}
			
			//Check if member was correctly classified within threshold
			resultCount += IsClassified(ExpectedOutput[0], net.getUnit(_outputIndices[0]).getOutput(), ClassificationThreshold);
		}
			
		BackPropResults r = new BackPropResults();
		r.CorrectClassifications = resultCount;
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Network has %s hidden units with %s inputs: \n", _hiddenIndices.length, _inputIndices.length));
		//Iterate through hidden layers getting weights
		for (int h = 0; h < _hiddenIndices.length; h++)
		{
			sb.append(String.format("Hidden Unit %s:\n", h));
			for (int in = 0; in < _inputIndices.length; in++)
			{
				sb.append(String.format("   Input: %s\n", in));
				sb.append(String.format("      Weight: %s\n", net.getUnit(_hiddenIndices[h]).getInputAt(in).getWeight()));
				sb.append(String.format("      Value: %s\n", net.getUnit(_hiddenIndices[h]).getInputAt(in).getValue()));
			}
		}
		
		sb.append(String.format("Network has %s output units with %s inputs: \n", _outputIndices.length, _hiddenIndices.length));
		//Iterate through hidden layers getting weights
		for (int ou = 0; ou < _outputIndices.length; ou++)
		{
			sb.append(String.format("Output Unit %s:\n", ou));
			for (int in = 0; in < _hiddenIndices.length; in++)
			{
				sb.append(String.format("   Input: %s\n", in));
				sb.append(String.format("      Weight: %s\n", net.getUnit(_outputIndices[ou]).getInputAt(in).getWeight()));
				sb.append(String.format("      Value: %s\n", net.getUnit(_outputIndices[ou]).getInputAt(in).getValue()));
			}
		}
		
		r.Message = sb.toString();
		return r;
	}
	
	private class BackPropResults
	{
		private int CorrectClassifications;
		private String Message;
	}
	
	/**
	 * Evaluate if an expected output is a correct classification when applying the classification threshold
	 * @param Expected The expected output
	 * @param Actual The actual output
	 * @param Threshold The allowable error threshold for a correct classification
	 * @return 1 if correctly classified, 0 is incorrectly classified
	 */
	private int IsClassified(double Expected, double Actual, double Threshold)
	{
		if (Actual == Expected) return 1;
		if (Expected > Actual && Actual > Expected - Threshold) return 1;
		if (Expected < Actual && Actual < Expected + Threshold) return 1;
		
		return 0;
	}
	
	//This is hard coded for the iris dataset - maybe it can be generalised
	//ToDo: Check if this needs to take into account the attributes being tested
	private List<Integer> getPositiveClassification(int classIndex)
	{
		switch (classIndex)
		{
		case 0:
			return Arrays.asList(1, 2);
		case 1:
			return Arrays.asList(1);
		case 2:
			return Arrays.asList(0, 1);
		default:
			throw new RuntimeException("Class index is outside of range");
		}
	}
	
	//This is hard coded for the iris dataset - maybe can be generalised
	//ToDo: Check if this needs to take into account the attributes being tested
	private List<Integer> getNegativeClassification(int classIndex)
	{
		switch (classIndex)
		{
		case 0:
			return Arrays.asList(0);
		case 1:
			return Arrays.asList(0, 2);
		case 2:
			return Arrays.asList(2);
		default:
			throw new RuntimeException("Class index is outside of range");
		}
	}
	
	// Try to classify the specified class using the specified attributes
	private boolean classify(int cls, List<Integer> Attributes)
	{
		System.out.println("=======================================================================");
		System.out.println(String.format("Running perceptron learning algorithm on Class %s (%s) using attribute(s) %s", cls + 1, ds.classifiers(cls), StringHelpers.JoinAddOne(Attributes, ",")));
		System.out.println(String.format("Maximum learning time is set to %s seconds... Please wait for completion", runForSeconds));
		
		//run the learning algorithm and retrieve the best results
		pocket p = learn(getPositiveClassification(cls), getNegativeClassification(cls), Attributes);

		System.out.println(String.format("The best weight configuration correctly classified %s members and incorrectly classified %s", p.correct, p.incorrect));
		System.out.println(String.format("The best configuration has: bias weight of: %s ", p.inputs.get(0).getWeight()));
		for (int i = 1; i < p.inputs.size(); i++)
		{
			System.out.println(String.format("The best configuration has: attribtute %s weight: %s ", Attributes.get(i-1) + 1, p.inputs.get(i).getWeight()));
		}
		
		System.out.println();
		
		//If the number of correctly classified members is the 
		return p.correct == ds.size();
	}
	
	/**
	 * Confirms if the data set member has been correctly categorised
	 * @param dsm
	 * @return
	 */
	private boolean validate(DataSetMember dsm)
	{
		return classDivider.get(dsm.getCategory()) == unit(0).getOutput(Boolean.class);
	}
	
	private void UpdateInputs(List<Integer> Attributes, DataSetMember<?> dsm)
	{
		//Update the unit's input values with the specified attributes from the selected data set member
		int inputIndex = 1; //Update from input 1 as input 0 is the bias
		for(int att : Attributes)
		{
			unit(0).getInputAt(inputIndex).setValueObject(dsm.getIValueAt(att));
			if (Settings.DebugLevel > 0) System.out.println(String.format("Updated Attribute index %s Value to %s", inputIndex, unit(0).getInputAt(inputIndex).getValue()));
			inputIndex++;
		}
	}
	
	//Run the perceptron learning algorithm
	private pocket learn(List<Integer> PositiveClasses, List<Integer> NegativeClasses, List<Integer> Attributes)
	{
		if (Settings.DebugLevel > 0) System.out.println("------------------------------------------------------");
		
		//Clear any existing unit configuration before learning
		units.clear();
		
		//Create a unit implementing activation function T(>=0,1,0).
		units.add(new Perceptron(ActivationFunction.ThresholdGreaterThanEqualToZero));
		
		//Get a reference to the unit's input collection
		List<Input> inputs = units.get(0).getInputs();
		
		//Assign an input for the bias with value 1 and weight 0
		inputs.add(Helpers.getStaticInput(1, 0));
		
		//Create an inputs for each of the attribute values using an arbitrary value of 0 and a random weight.  
		//These will be updated with the appropriate attribute values during the learning process.
		for (int i = 0; i < Attributes.size(); i++)
		{
			inputs.add(Helpers.getStaticInput(0, new Random().nextDouble()));
		}
		
		//Configure the classes that should be identified as positive and negative
		ConfigureClassifierDivision(PositiveClasses, NegativeClasses);
		
		//Create a pocket to store the best results
		pocket p = new pocket();
		
		//Remember the start time of the learning cycle, so that we can break out after the 
		//required run time. 
		long startLoopTime = System.currentTimeMillis();
		
		//This loop contains the logic for each iteration of the learning process.  Loop will
		//run until either all data set members are correctly classified, or running time limit
		//is reached
		while (true)
		{		
			//Break out of loop once running time limit has been reached
			if (System.currentTimeMillis() >= startLoopTime + runForSeconds * 1000)
			{
				if (Settings.DebugLevel > 0) System.out.println(String.format("Learning time limit of %s seconds has been reached", runForSeconds));
				break;
			}
				
			//Select a random row from the data set
			DataSetMember<Double> dsm = ds.getRandomMember();	

			//Just printing some debugging info to the console
			if (Settings.DebugLevel > 0) 
			{
				System.out.println(String.format("Random data member selected: Class %s", dsm.getCategory()));
				for(int i=0; i < dsm.ElementCount(); i++)
				{
					System.out.println(String.format("Random data member attribute index %s Value: %s ", i, dsm.getValueAt(i)));
				}
				
				System.out.println(String.format("Current unit Bias Value: %s Weight: %s", unit(0).getInputAt(0).getValue(), unit(0).getInputAt(0).getWeight()));
				for(int i=0; i < Attributes.size(); i++)
				{
					System.out.println(String.format("Current unit Attribute index %s Value: %s Weight: %s",Attributes.get(i) , unit(0).getInputAt(i+1).getValue(), unit(0).getInputAt(i+1).getWeight()));
				}
			}
			
			//Load the attributes from the selected data set member into the unit's inputs
			UpdateInputs(Attributes, dsm);
			
			if (Settings.DebugLevel > 0) System.out.println(String.format("Perceptron Output: %s", unit(0).getOutput(Boolean.class)));
			
			//If correctly classified, the output of the unit should match the value 
			//listed in the class divider hash table for the current class type
			if (validate(dsm)) 
			{
				//The data member was correctly identified
				if (Settings.DebugLevel > 0) System.out.println("Data set member was correctly classified");
					
				//Test the current weight configuration against all members of the 
				//data set to confirm if all members are correctly classified
				
				//Create a new perceptron for testing, else the values will be incorrect for updating
				//ToDo: Move this to the test logic
				IUnit testPerceptron = new Perceptron(ActivationFunction.ThresholdGreaterThanEqualToZero);
				for (Input in : unit(0).getInputs())
				{
					testPerceptron.addInput(Helpers.getStaticInput(in.getValue(), in.getWeight()));
				}
				
				if (Settings.DebugLevel > 0) System.out.println("Testing data set with current weight configuration");
				Perceptron.TestResult TestOutput = testPerceptron.Test(ds, classDivider, Attributes);
				
				//Respond to test results
				if (TestOutput.getResult())
				{
					System.out.println("All data set members were correctly classified with current weight configuration!  Hooray!");
					
					//ToDo: Refactor - same code is duplicated below
					p.correct = TestOutput.getCorrectClassifications();
					p.incorrect = TestOutput.getIncorrectClassifications();
					p.setInputs(unit(0).getInputs());
					
					break;
				}	
				
				if (Settings.DebugLevel > 0) 
				{
					System.out.println(String.format("Correctly classified members: %s", TestOutput.getCorrectClassifications()));
					System.out.println(String.format("Incorrectly classified members: %s", TestOutput.getIncorrectClassifications()));
					for(int i = 0; i < unit(0).getInputs().size(); i++)
					{
						System.out.println(String.format("Attribute index %s value: %s weight: %s ", i, unit(0).getInputAt(i).getValue(), unit(0).getInputAt(i).getWeight()));
					}
				}
				
				//Check the test output to see of the match should be pocketed as the
				//best result so far.
				if (TestOutput.getCorrectClassifications() > p.correct)
				{
					if (Settings.DebugLevel > 0) System.out.println("Current weight configuration has the best results set so far.  Pocketing results:");
					
					p.correct = TestOutput.getCorrectClassifications();
					p.incorrect = TestOutput.getIncorrectClassifications();
					p.setInputs(unit(0).getInputs());
				}
				
				if (Settings.DebugLevel > 0) 
					System.out.println(String.format("%s were correctly classified. %s were incorrectly classified", 
													 TestOutput.getCorrectClassifications(), 
													 TestOutput.getIncorrectClassifications()));
			}
				
			//If the data set member is part of the negative set
			else if (!classDivider.get(dsm.getCategory()))
			{
				if (Settings.DebugLevel > 0) System.out.println("Data set member was incorrectly classified");
				if (Settings.DebugLevel > 0) System.out.println(String.format("Classification type is %s", dsm.getCategory()));
				if (Settings.DebugLevel > 0) System.out.println(String.format("Data set member is an element of the %s set", classDivider.get(dsm.getCategory())?"Positive":"Negative"));
				if (Settings.DebugLevel > 0) System.out.println(String.format("Unit Y at 0 is %s", -unit(0).getInputAt(0).getWeight() / unit(0).getInputAt(1).getWeight()));
				if (Settings.DebugLevel > 0) System.out.println("Subtracting");
				
				unit(0).subtract(dsm, Attributes);
			}
			
			//If the data set member is part of the positive set
			else if (classDivider.get(dsm.getCategory()))
			{
				if (Settings.DebugLevel > 0) System.out.println("Data set member was incorrectly classified");
				if (Settings.DebugLevel > 0) System.out.println(String.format("Classification type is %s", dsm.getCategory()));
				if (Settings.DebugLevel > 0) System.out.println(String.format("Data set member is an element of the %s set", classDivider.get(dsm.getCategory())?"Positive":"Negative"));
				if (Settings.DebugLevel > 0) System.out.println(String.format("Unit Y at 0 is %s", -unit(0).getInputAt(0).getWeight() / unit(0).getInputAt(1).getWeight()));
				if (Settings.DebugLevel > 0) System.out.println("Adding");
				
				unit(0).add(dsm, Attributes);
			}
			
			if (Settings.DebugLevel > 0) System.out.println("------------------------------------------------------");
		}
		
		return p;
	}
}
