package fr.ourten.teabeans.value.list;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Implementation of ListProperty using CopyOnWriteArrayList
 * 
 * @author Phenix246
 */
public class CopyOnWriteArrayListProperty<T> extends ListProperty<T>
{

    public CopyOnWriteArrayListProperty(final List<T> value, final String name)
    {
        super(value != null ? Lists.newCopyOnWriteArrayList(value) : Lists.newCopyOnWriteArrayList(), name);
    }

    public CopyOnWriteArrayListProperty(final List<T> value)
    {
        this(value, "");
    }

}
