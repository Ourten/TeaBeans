package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.Observable;

public interface ValueInvalidationListener
{
    void invalidated(Observable observable);
}