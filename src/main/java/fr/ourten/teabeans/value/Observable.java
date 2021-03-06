package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ValueInvalidationListener;

public interface Observable
{
    void addListener(ValueInvalidationListener listener);

    void removeListener(ValueInvalidationListener listener);

    void addChangeListener(ValueInvalidationListener listener);

    void removeChangeListener(ValueInvalidationListener listener);

    void mute();

    void unmute();

    default void muteWhile(Runnable runnable)
    {
        mute();
        runnable.run();
        unmute();
    }

    boolean isMuted();
}