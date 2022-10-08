package fr.ourten.teabeans.listener;


import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class WeakObservableListener implements ValueInvalidationListener
{
    private final WeakReference<ObservableValue<?>> ownerRef;

    public WeakObservableListener(ObservableValue<?> owner)
    {
        Objects.requireNonNull(owner);

        ownerRef = new WeakReference<>(owner);
    }

    @Override
    public void invalidated(Observable observable)
    {
        ObservableValue<?> owner = ownerRef.get();
        if (owner == null)
        {
            observable.removeListener(this);
            return;
        }

        owner.invalidate();
    }
}
