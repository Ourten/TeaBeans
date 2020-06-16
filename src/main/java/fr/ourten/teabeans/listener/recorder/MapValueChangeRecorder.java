package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.property.MapProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class MapValueChangeRecorder<K, V> extends ObservableValueRecorder<V> implements MapValueChangeListener<K, V>
{
    private final List<K> keys = new ArrayList<>();

    public MapValueChangeRecorder()
    {

    }

    @SafeVarargs
    public MapValueChangeRecorder(MapProperty<K, V>... mapProperties)
    {
        for (MapProperty<K, V> mapProperty : mapProperties)
            mapProperty.addListener(this);
    }

    public List<K> getKeys()
    {
        return keys;
    }

    @Override
    public void reset()
    {
        super.reset();
        keys.clear();
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
