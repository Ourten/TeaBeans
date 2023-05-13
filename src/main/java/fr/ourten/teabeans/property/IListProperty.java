package fr.ourten.teabeans.property;

import fr.ourten.teabeans.value.ListObservableValue;

import java.util.List;

public interface IListProperty<T> extends ListObservableValue<T>, IProperty<List<T>>, List<T>
{
}