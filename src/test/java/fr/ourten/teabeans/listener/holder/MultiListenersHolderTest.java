package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiListenersHolderTest
{
    @Test
    void removeChangeListener_givenThreeListeners_shouldRemoveMiddle()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueChangeListener<String> firstListener = (observable, oldValue, newValue) -> firstCounter.incrementAndGet();
        ValueChangeListener<String> secondListener = (observable, oldValue, newValue) -> secondCounter.incrementAndGet();
        ValueChangeListener<String> thirdListener = (observable, oldValue, newValue) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(firstListener, null, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(secondListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeChangeListener_givenThreeListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueChangeListener<String> firstListener = (observable, oldValue, newValue) -> firstCounter.incrementAndGet();
        ValueChangeListener<String> secondListener = (observable, oldValue, newValue) -> secondCounter.incrementAndGet();
        ValueChangeListener<String> thirdListener = (observable, oldValue, newValue) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(firstListener, null, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(firstListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeChangeListener_givenThreeListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueChangeListener<String> firstListener = (observable, oldValue, newValue) -> firstCounter.incrementAndGet();
        ValueChangeListener<String> secondListener = (observable, oldValue, newValue) -> secondCounter.incrementAndGet();
        ValueChangeListener<String> thirdListener = (observable, oldValue, newValue) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(firstListener, null, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(thirdListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(1);
    }

    @Test
    void removeChangeListener_givenTwoListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueChangeListener<String> firstListener = (observable, oldValue, newValue) -> firstCounter.incrementAndGet();
        ValueChangeListener<String> secondListener = (observable, oldValue, newValue) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(firstListener, null, null);
        holder.addChangeListener(secondListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeChangeListener(firstListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
    }

    @Test
    void removeChangeListener_givenTwoListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueChangeListener<String> firstListener = (observable, oldValue, newValue) -> firstCounter.incrementAndGet();
        ValueChangeListener<String> secondListener = (observable, oldValue, newValue) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(firstListener, null, null);
        holder.addChangeListener(secondListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeChangeListener(secondListener);

        holder.fireChangeListeners(null, null, null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
    }

    ////////////////////////
    // ARGLESS LISTENERS  //
    ////////////////////////

    @Test
    void removeArglessChangeListener_givenThreeListeners_shouldRemoveMiddle()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, firstListener, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(secondListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeArglessChangeListener_givenThreeListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, firstListener, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(firstListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeArglessChangeListener_givenThreeListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, firstListener, null);
        holder.addChangeListener(secondListener);
        holder.addChangeListener(thirdListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeChangeListener(thirdListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(1);
    }

    @Test
    void removeArglessChangeListener_givenTwoListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(null, firstListener, null);
        holder.addChangeListener(secondListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeChangeListener(firstListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
    }

    @Test
    void removeArglessChangeListener_givenTwoListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(null, firstListener, null);
        holder.addChangeListener(secondListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeChangeListener(secondListener);

        holder.fireChangeArglessListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
    }

    ////////////////////////////
    // INVALIDATION LISTENERS //
    ////////////////////////////

    @Test
    void removeInvalidationListener_givenThreeListeners_shouldRemoveMiddle()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, null, firstListener);
        holder.addListener(secondListener);
        holder.addListener(thirdListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeListener(secondListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeInvalidationListener_givenThreeListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, null, firstListener);
        holder.addListener(secondListener);
        holder.addListener(thirdListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeListener(firstListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(2);
    }

    @Test
    void removeInvalidationListener_givenThreeListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);
        var thirdCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();
        ValueInvalidationListener thirdListener = (observable) -> thirdCounter.incrementAndGet();

        var holder = new MultiListenersHolder<>(null, null, firstListener);
        holder.addListener(secondListener);
        holder.addListener(thirdListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);
        assertThat(thirdCounter.get()).isEqualTo(1);

        holder.removeListener(thirdListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(2);
        assertThat(thirdCounter.get()).isEqualTo(1);
    }

    @Test
    void removeInvalidationListener_givenTwoListeners_shouldRemoveFirst()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(null, null, firstListener);
        holder.addListener(secondListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeListener(firstListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(2);
    }

    @Test
    void removeInvalidationListener_givenTwoListeners_shouldRemoveLast()
    {
        var firstCounter = new AtomicInteger(0);
        var secondCounter = new AtomicInteger(0);

        ValueInvalidationListener firstListener = (observable) -> firstCounter.incrementAndGet();
        ValueInvalidationListener secondListener = (observable) -> secondCounter.incrementAndGet();

        ListenersHolder<String> holder = new MultiListenersHolder<>(null, null, firstListener);
        holder.addListener(secondListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(1);
        assertThat(secondCounter.get()).isEqualTo(1);

        holder = holder.removeListener(secondListener);

        holder.fireInvalidationListeners(null);

        assertThat(firstCounter.get()).isEqualTo(2);
        assertThat(secondCounter.get()).isEqualTo(1);
    }
}
