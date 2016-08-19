package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.List;

public interface Binding<T> extends ObservableValue<T>
{
    void bind(Observable... observables);

    void unbind(Observable... observables);

    T computeValue();

    List<Observable> getDependencies();
}