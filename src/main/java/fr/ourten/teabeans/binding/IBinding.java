package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.List;

public interface IBinding<T> extends ObservableValue<T>
{
    void bind(Observable... observables);

    void unbind(Observable... observables);

    void unbindAll();

    T computeValue();

    List<Observable> getDependencies();

    boolean isValid();

    void invalidate();
}