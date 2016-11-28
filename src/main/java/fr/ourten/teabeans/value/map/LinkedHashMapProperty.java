package fr.ourten.teabeans.value.map;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Implementation of MapProperty using LinkedHashMap
 * 
 * @author Phenix246
 */
public class LinkedHashMapProperty<K, T> extends MapProperty<K, T>
{

    public LinkedHashMapProperty(final Map<K, T> value, final String name)
    {
        super(value != null ? Maps.newLinkedHashMap(value) : Maps.newLinkedHashMap(), name);
    }

    public LinkedHashMapProperty(final Map<K, T> value)
    {
        this(value, "");
    }

}
