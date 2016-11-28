package fr.ourten.teabeans.value;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Implementation of ListProperty using ArrayList
 * 
 * @author Phenix246
 */
public class ArrayListProperty<T> extends ListProperty<T>
{

    public ArrayListProperty(final List<T> value, final String name)
    {
        super(value != null ? Lists.newArrayList(value) : Lists.newArrayList(), name);
    }

    public ArrayListProperty(final List<T> value)
    {
        this(value, "");
    }

}
