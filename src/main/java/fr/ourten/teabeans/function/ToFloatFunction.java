package fr.ourten.teabeans.function;

@FunctionalInterface
public interface ToFloatFunction<T>
{
    float applyAsFloat(T value);
}
