package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.property.IProperty;
import fr.ourten.teabeans.value.Observable;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class WeakPropertyListener implements ValueInvalidationListener
{
    private final WeakReference<IProperty<?>> ownerRef;

    public WeakPropertyListener(IProperty<?> owner)
    {
        Objects.requireNonNull(owner);

        ownerRef = new WeakReference<>(owner);
    }

    @Override
    public void invalidated(Observable observable)
    {
        IProperty<?> owner = ownerRef.get();
        if (owner == null)
        {
            observable.removeListener(this);
            return;
        }

        owner.refreshFromBound();
        owner.invalidate();
    }
}
