package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Map;

public interface MapListenersHolder<K, V> extends ListenersHolder<Map<K, V>>
{
    MapListenersHolder<K, V> addMapChangeListener(MapValueChangeListener<? super K, ? super V> listener);

    MapListenersHolder<K, V> removeMapChangeListener(MapValueChangeListener<? super K, ? super V> listener);

    MapListenersHolder<K, V> addChangeListener(ValueChangeListener<? super Map<K, V>> listener);

    MapListenersHolder<K, V> removeChangeListener(ValueChangeListener<? super Map<K, V>> listener);


    MapListenersHolder<K, V> addChangeListener(ValueInvalidationListener listener);

    MapListenersHolder<K, V> removeChangeListener(ValueInvalidationListener listener);


    MapListenersHolder<K, V> addListener(ValueInvalidationListener listener);

    MapListenersHolder<K, V> removeListener(ValueInvalidationListener listener);

    void fireMapChangeListeners(ObservableValue<? extends Map<K, V>> observable, K key, V oldValue, V newValue);

    boolean hasMapChangeListeners();

    static <K, V> MapListenersHolder<K, V> addMapChangeListener(MapListenersHolder<K, V> holder, MapValueChangeListener<? super K, ? super V> listener)
    {
        if (holder == null)
            return new MapMonoListenerHolder<>(listener, null, null, null);
        return holder.addMapChangeListener(listener);
    }

    static <K, V> MapListenersHolder<K, V> removeMapChangeListener(MapListenersHolder<K, V> holder, MapValueChangeListener<? super K, ? super V> listener)
    {
        if (holder == null)
            return null;
        return holder.removeMapChangeListener(listener);
    }


    static <K, V> MapListenersHolder<K, V> addChangeListener(MapListenersHolder<K, V> holder, ValueChangeListener<? super Map<K, V>> listener)
    {
        if (holder == null)
            return new MapMonoListenerHolder<>(null, listener, null, null);
        return holder.addChangeListener(listener);
    }

    static <K, V> MapListenersHolder<K, V> removeChangeListener(MapListenersHolder<K, V> holder, ValueChangeListener<? super Map<K, V>> listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <K, V> MapListenersHolder<K, V> addChangeListener(MapListenersHolder<K, V> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new MapMonoListenerHolder<>(null, null, listener, null);
        return holder.addChangeListener(listener);
    }

    static <K, V> MapListenersHolder<K, V> removeChangeListener(MapListenersHolder<K, V> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <K, V> MapListenersHolder<K, V> addListener(MapListenersHolder<K, V> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new MapMonoListenerHolder<>(null, null, null, listener);
        return holder.addListener(listener);
    }

    static <K, V> MapListenersHolder<K, V> removeListener(MapListenersHolder<K, V> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeListener(listener);
    }


    static <K, V> void fireChangeListeners(MapListenersHolder<K, V> holder, ObservableValue<? extends Map<K, V>> observable, Map<K, V> oldValue, Map<K, V> newValue)
    {
        if (holder != null)
            holder.fireChangeListeners(observable, oldValue, newValue);
    }

    static <K, V> void fireMapChangeListeners(MapListenersHolder<K, V> holder, ObservableValue<? extends Map<K, V>> observable, K key, V oldValue, V newValue)
    {
        if (holder != null)
            holder.fireMapChangeListeners(observable, key, oldValue, newValue);
    }

    static <K, V> void fireInvalidationListeners(MapListenersHolder<K, V> holder, Observable observable)
    {
        if (holder != null)
            holder.fireInvalidationListeners(observable);
    }

    static <K, V> void fireChangeArglessListeners(MapListenersHolder<K, V> holder, Observable observable)
    {
        if (holder != null)
            holder.fireChangeArglessListeners(observable);
    }

    static <K, V> boolean hasChangeListeners(MapListenersHolder<K, V> holder)
    {
        if (holder != null)
            return holder.hasChangeListeners();
        return false;
    }

    static <K, V> boolean hasMapChangeListeners(MapListenersHolder<K, V> holder)
    {
        if (holder != null)
            return holder.hasMapChangeListeners();
        return false;
    }

    static <K, V> boolean hasInvalidationListeners(MapListenersHolder<K, V> holder)
    {
        if (holder != null)
            return holder.hasInvalidationListeners();
        return false;
    }

    static <K, V> boolean hasChangeArglessListeners(MapListenersHolder<K, V> holder)
    {
        if (holder != null)
            return holder.hasChangeArglessListeners();
        return false;
    }
}
