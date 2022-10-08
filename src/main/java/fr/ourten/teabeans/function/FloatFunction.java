package fr.ourten.teabeans.function;

@FunctionalInterface
public interface FloatFunction<R>
{
    R apply(float value);
}