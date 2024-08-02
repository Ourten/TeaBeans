package fr.ourten.teabeans.function;

@FunctionalInterface
public interface ToFloatBiFunction<T, U>
{
    float applyAsFloat(T firstValue, U secondValue);
}
