package fr.ourten.teabeans.function;

@FunctionalInterface
public interface ToBooleanFunction<T>
{
    boolean applyAsBoolean(T value);
}
