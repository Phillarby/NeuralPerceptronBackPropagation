package uk.co.larby.neural;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_DataSetBuilder {
	
	/**
	 * Test Case: 	Confirm that the constructor of the DataSetBuilder creates a dataset with the 
	 *            	expected number of entries
	 * Details: 	We know that the iris dataset contains 150 entries: 50 for each of the three 
	 * 				type of Iris.
	 */
	@Test
	public void Confirm_the_correct_number_of_records_are_created_from_a_known_training_dataset() {
		//Arrange
		DataSetBuilder<Double> dsb = new DataSetBuilder<Double>("iris.data");
		int expected = 150;
		
		//Act
		int actual = dsb.getDataMembers().size();

		//Assert
		assertEquals(expected, actual);
	}

}
