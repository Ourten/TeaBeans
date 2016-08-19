package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ValueInvalidationListener;

public interface Observable
{
    void addListener(ValueInvalidationListener listener);

    void removeListener(ValueInvalidationListener listener);
}