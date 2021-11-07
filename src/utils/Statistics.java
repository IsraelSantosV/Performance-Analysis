package utils;

public class Statistics {
	
	public static double getMean(double[] values) {
		double sum = 0;
		
		for(int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		
		return sum / values.length;
	}
	
	public static double getStandardDeviation(double[] values) {
		double sd = 0;
		var mean = getMean(values);
		
		for(int i = 0; i < values.length; i++) {
			sd += Math.pow((values[i] - mean), 2);
		}
		
		return Math.sqrt((sd / values.length));
	}

}
