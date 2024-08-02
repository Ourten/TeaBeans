package fr.ourten.teabeans.function;

@FunctionalInterface
public interface ToBooleanBiFunction<T, U>
{
    boolean applyAsBoolean(T firstValue, U secondValue);
}
