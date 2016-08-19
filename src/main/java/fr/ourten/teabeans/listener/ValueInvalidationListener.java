package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.Observable;

public interface ValueInvalidationListener
{
    public void invalidated(Observable observable);
}