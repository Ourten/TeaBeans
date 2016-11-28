package fr.ourten.teabeans.value.map;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Implementation of MapProperty using HashMap
 * 
 * @author Phenix246
 */
public class HashMapProperty<K, T> extends MapProperty<K, T>
{

    public HashMapProperty(final Map<K, T> value, final String name)
    {
        super(value != null ? Maps.newHashMap(value) : Maps.newHashMap(), name);
    }

    public HashMapProperty(final Map<K, T> value)
    {
        this(value, "");
    }

}
