package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.ListObservableValue;

public interface IListChange<T>
{
    ListObservableValue<T> observable();

    T oldValue();

    T newValue();

    int changeIndex();

    boolean wasAddition();

    boolean wasRemoval();

    boolean wasReplace();
}
