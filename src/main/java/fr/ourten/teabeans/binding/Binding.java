package fr.ourten.teabeans.binding;

import java.util.List;

import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

public interface Binding<T> extends ObservableValue<T>
{
    void bind(Observable... observables);

    void unbind(Observable... observables);

    T computeValue();

    List<Observable> getDependencies();

    boolean isValid();

    void invalidate();
}