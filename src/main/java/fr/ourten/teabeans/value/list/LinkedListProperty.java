package fr.ourten.teabeans.value.list;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Implementation of ListProperty using LinkedList
 * 
 * @author Phenix246
 */
public class LinkedListProperty<T> extends ListProperty<T>
{

    public LinkedListProperty(final List<T> value, final String name)
    {
        super(value != null ? Lists.newLinkedList(value) : Lists.newLinkedList(), name);
    }

    public LinkedListProperty(final List<T> value)
    {
        this(value, "");
    }

}
