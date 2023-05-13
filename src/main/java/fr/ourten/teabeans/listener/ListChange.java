package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.ListObservableValue;

public class ListChange<T> implements IListChange<T>
{
    private final ListObservableValue<T> observable;

    private T   oldValue;
    private T   newValue;
    private int changeIndex;

    private ListChangeType changeType;

    public ListChange(ListObservableValue<T> observable)
    {
        this.observable = observable;
    }

    public ListObservableValue<T> observable()
    {
        return observable;
    }

    public T oldValue()
    {
        return oldValue;
    }

    public T newValue()
    {
        return newValue;
    }

    public int changeIndex()
    {
        return changeIndex;
    }

    @Override
    public boolean wasAddition()
    {
        return changeType == ListChangeType.ADDITION;
    }

    @Override
    public boolean wasRemoval()
    {
        return changeType == ListChangeType.REMOVAL;
    }

    @Override
    public boolean wasReplace()
    {
        return changeType == ListChangeType.REPLACE;
    }

    public void forAddition(int index, T newValue)
    {
        this.oldValue = null;
        this.newValue = newValue;
        changeIndex = index;
    }

    public void forRemoval(int index, T oldValue)
    {
        this.oldValue = oldValue;
        this.newValue = null;
        changeIndex = index;
    }

    public void forReplace(int index, T oldValue, T newValue)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeIndex = index;
    }

    private enum ListChangeType
    {
        ADDITION,
        REMOVAL,
        REPLACE
    }
}
