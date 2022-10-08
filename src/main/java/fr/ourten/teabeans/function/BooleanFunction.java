package fr.ourten.teabeans.function;

@FunctionalInterface
public interface BooleanFunction<R>
{
    R apply(boolean value);
}