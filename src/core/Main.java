package core;

import utils.Statistics;

public class Main {
	
	public static void main(String[] args) {
		CrivoAlgorithm crivo = new CrivoAlgorithm();
		
		final int executionLaps = 10;
		final int inputLimit = 4;
		int[] maxValues = new int[] { 200000, 500000, 1000000, 2000000 };
		int[] threadsAmount = new int[] { 1, 4, 8, 12 }; //For a CPU with 24 threads and 12 core
		
		//Storage results into arrays
		double[] executionTimes = new double[executionLaps];
		double sequentialTime = 0;
		
		//Main execution loop for performance analysis
		for(int value = 0; value < inputLimit; value++) {
			for(int thread = 0; thread < inputLimit; thread++) {
				System.out.println("============ Setup (" + maxValues[value] + "/" + threadsAmount[thread] + "t) ============");
				for(int lap = 0; lap < executionLaps; lap++) {
					var executionTime = crivo.start(maxValues[value], threadsAmount[thread]);
					
					System.out.println("------------ Execution " + (lap + 1) + " ------------");
					System.out.print("Finish with time: ");
					System.out.printf("%.10f\n", convertNanoToSeconds(executionTime));
					System.out.println();
					
					executionTimes[lap] = convertNanoToSeconds(executionTime);
				}
				
				var mean = Statistics.getMean(executionTimes);
				var standardDeviation = Statistics.getStandardDeviation(executionTimes);
				
				System.out.println("-=-=-=-=-=- Data " + (value + 1) + " -=-=-=-=-=-");
				
				if(threadsAmount[thread] == 1) {
					sequentialTime = mean;
				}
				else {
					var speedUp = getSpeedup(sequentialTime, mean);
					System.out.printf("Speedup: %.8f\n", speedUp);
					System.out.printf("Efficiency: %.2f\n", getEfficiency(speedUp, threadsAmount[thread]));
					System.out.printf("Karp Flatt Metric: %.8f\n", getKarpFlattMetric(speedUp, threadsAmount[thread]));
				}
				
				System.out.print("Mean: ");
				System.out.printf("%.8f\n", mean);
				
				System.out.print("Standard Deviation: ");
				System.out.printf("%.8f\n", standardDeviation);
				System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			}
		}
	}
	
	public static double convertNanoToSeconds(long nanoSeconds) {
		return (double)nanoSeconds / 1000000000;
	}
	
	public static double getSpeedup(double sequentialTime, double parallelTime) {
		return sequentialTime / parallelTime;
	}
	
	public static double getEfficiency(double speedUp, int processingElements) {
		return speedUp / processingElements;
	}
	
	public static double getKarpFlattMetric(double speedUp, int processingElements) {
		return ((1 / speedUp) - (1 / processingElements)) / (1 - (1 / processingElements));
	}

}
