
package acme.helpers;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.validation.constraints.NotNull;

public class Statistic<E, T> {

	@NotNull
	protected T			minimum;

	@NotNull
	protected T			maximum;

	protected double	average;

	protected double	deviation;


	public Statistic(final Collection<E> sample, final Function<E, T> getProperty) throws NullPointerException, InvalidParameterException {
		if (sample == null)
			throw new NullPointerException("\'Sample\' is not defined");

		final Map<T, Integer> sampleStruct = new HashMap<T, Integer>();
		for (final E model : sample) {

			if (model == null)
				throw new NullPointerException("\'Sample\' contains undefined values");

			final T valueSample = getProperty.apply(model);
			if (!(valueSample instanceof Integer) || !(valueSample instanceof Double))
				throw new InvalidParameterException("\'Sample\' is not full of Numerical Values");

			if (sampleStruct.containsKey(valueSample)) {
				sampleStruct.replace(valueSample, sampleStruct.get(valueSample) + 1);
				continue;
			}
			sampleStruct.put(valueSample, 1);
		}
		final T max = this.maximum(sampleStruct);
		final T min = this.minimum(sampleStruct);

		this.maximum = max;
		this.minimum = min;
		this.average = this.average(sampleStruct);
		this.deviation = this.deviation(sampleStruct, this.average);

	}

	private T maximum(final Map<T, Integer> sample) {
		T max = null;
		Integer numOfElements = 0;
		for (final Entry<T, Integer> sampleElement : sample.entrySet()) {
			if (max != null && sampleElement.getValue() <= numOfElements)
				continue;
			max = sampleElement.getKey();
			numOfElements = sampleElement.getValue();
		}
		return max == null ? (T) numOfElements : max;
	}

	private T minimum(final Map<T, Integer> sample) {
		T min = null;
		Integer numOfElements = 0;
		for (final Entry<T, Integer> sampleElement : sample.entrySet()) {
			if (min != null && sampleElement.getValue() >= numOfElements)
				continue;
			min = sampleElement.getKey();
			numOfElements = sampleElement.getValue();
		}
		return min == null ? (T) numOfElements : min;
	}

	private double deviation(final Map<T, Integer> sample, final double average) {
		int totalElements = 1;
		double totalWeight = 0;

		for (final Entry<T, Integer> sampleElement : sample.entrySet()) {
			totalElements += sampleElement.getValue();
			totalWeight += Math.pow((Double) sampleElement.getKey() - average, 2);
		}
		return totalElements > 1 ? Math.sqrt(totalWeight / (totalElements - 1)) : 0;

	}

	private double average(final Map<T, Integer> sample) {
		int totalElements = 1;
		double totalWeight = 0;

		for (final Entry<T, Integer> sampleElement : sample.entrySet()) {
			totalElements += sampleElement.getValue();
			totalWeight += (Double) sampleElement.getKey() * sampleElement.getValue();
		}

		return totalWeight / totalElements;

	}
}
