package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.property.MapProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class MapValueChangeRecorder<K, V> implements MapValueChangeListener<K, V>, Recorder
{
    private int count;

    private final List<K> keys      = new ArrayList<>();
    private final List<V> oldValues = new ArrayList<>();
    private final List<V> newValues = new ArrayList<>();

    public MapValueChangeRecorder()
    {
        
    }

    @SafeVarargs
    public MapValueChangeRecorder(MapProperty<K, V>... mapProperties)
    {
        for (MapProperty<K, V> mapProperty : mapProperties)
            mapProperty.addListener(this);
    }

    @Override
    public int getCount()
    {
        return count;
    }

    public List<K> getKeys()
    {
        return keys;
    }

    public List<V> getOldValues()
    {
        return oldValues;
    }

    public List<V> getNewValues()
    {
        return newValues;
    }

    @Override
    public void reset()
    {
        count = 0;
        keys.clear();
        oldValues.clear();
        newValues.clear();
    }

    @Override
    public void valueChanged(ObservableValue<?> observable, K key, V oldValue, V newValue)
    {
        count++;
        keys.add(key);
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
