package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.recorder.SetValueChangeRecorder;
import fr.ourten.teabeans.listener.recorder.ValueInvalidationRecorder;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SetPropertyTest
{
    Set<Integer>         set;
    SetProperty<Integer> property;
    int                  count;

    @BeforeEach
    public void setup()
    {
        set = Sets.newHashSet(Arrays.asList(0, 2, 3, 5));
        property = new SetProperty<>(set);
        count = 0;
    }

    @Test
    public void testConstructorSetNull()
    {
        SetProperty<Integer> property = new SetProperty<>(null);

        int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        SetProperty<Integer> property = new SetProperty<>(LinkedHashSet::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedHashSet.class);
    }

    @Test
    public void testSetPropertySize()
    {
        int actual = property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testSetPropertyValue()
    {
        assertThat(property.getValue().toArray()).containsExactly(set.toArray());

        assertThat(property.contains(5)).isTrue();
        assertThat(property.contains(7)).isFalse();

        assertThat(property.isEmpty()).isFalse();

        property.clear();

        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testSetRemoval()
    {
        Set<String> stringSet = new HashSet<>(Arrays.asList("test1", "test2", "test3"));
        SetProperty<String> stringProperty = new SetProperty<>(stringSet);

        assertThat(stringProperty.remove("something")).isFalse();
        assertThat(stringProperty.remove("test2")).isTrue();
        assertThat(stringProperty.contains("test2")).isFalse();
    }

    @Test
    public void testSetAdd()
    {
        property.add(7);
        assertThat(property.getValue()).contains(7);
    }

    @Test
    public void testSetPropertyInvalidationListener()
    {
        property.addListener(observable -> count++);

        property.add(6);
        assertThat(count).isEqualTo(1);

        property.remove(2);
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void testSetPropertyListChangeListener()
    {
        ListValueChangeListener<Integer> removeListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(0);
        };

        property.addChangeListener(removeListener);
        property.remove(0);
        property.removeChangeListener(removeListener);

        ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isIn(18, 20, 22, 24);
            count++;
        };

        property.addChangeListener(addListener);
        property.add(18);
        property.addAll(Arrays.asList(20, 22, 24));
        assertThat(count).isEqualTo(4);
        property.removeChangeListener(addListener);

        count = 0;
        property.clear();
        property.addAll(Arrays.asList(18, 20, 22, 24, 26));
        ListValueChangeListener<Integer> clearListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isIn(18, 20, 22, 24, 26);
            count++;
        };
        property.addChangeListener(clearListener);

        property.clear();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void testListPropertyReplace()
    {
        property.replace(3, 9);

        assertThat(property.contains(9)).isTrue();
        assertThat(property.contains(3)).isFalse();
    }

    @Test
    void getValue_givenSimpleSet_thenShouldCreateImmutableView()
    {
        SetProperty<Integer> property = new SetProperty<>(IntStream.range(0, 10).boxed().collect(toSet()));

        Set<Integer> immutableValues = property.getValue();

        assertThat(immutableValues).containsExactlyInAnyOrder(IntStream.range(0, 10).boxed().toArray(Integer[]::new));

        assertThatThrownBy(() -> immutableValues.add(0)).isInstanceOf(UnsupportedOperationException.class);

        property.add(10);

        assertThat(immutableValues).containsExactlyInAnyOrder(IntStream.range(0, 11).boxed().toArray(Integer[]::new));
    }

    @Test
    void getValue_givenSimpleSetThenReplaced_thenShouldReCreateImmutableView()
    {
        SetProperty<Integer> property = new SetProperty<>(IntStream.range(0, 10).boxed().collect(toSet()));

        Set<Integer> immutableValues = property.getValue();
        assertThat(immutableValues).isSameAs(property.getValue());

        property.setValue(IntStream.range(10, 20).boxed().collect(toSet()));
        assertThat(immutableValues).containsExactlyInAnyOrder(IntStream.range(0, 10).boxed().toArray(Integer[]::new));

        assertThat(immutableValues).isNotSameAs(property.getValue());
        assertThat(property.getValue()).containsExactlyInAnyOrder(IntStream.range(10, 20).boxed().toArray(Integer[]::new));
    }

    @Test
    void addListener_givenBoundPropertyThenAddingListChangeListener_thenShouldStartObserving()
    {
        SetProperty<Integer> property = new SetProperty<>(IntStream.range(0, 10).boxed().collect(toSet()));
        SetProperty<Integer> secondProperty = spy(new SetProperty<>(IntStream.range(10, 20).boxed().collect(toSet())));
        property.bindProperty(secondProperty);

        verify(secondProperty, never()).addListener(any(ValueInvalidationListener.class));

        new SetValueChangeRecorder<>(property);

        verify(secondProperty, atMostOnce()).addListener(any(ValueInvalidationListener.class));
    }

    @Test
    void invalidate_givenRemovalOfListenerDuringPropagation_thenShouldNotThrow()
    {
        var property = new SetProperty<String>();
        property.add("lala");

        var listenerHit = new AtomicBoolean(false);

        var listener = new ValueInvalidationListener[1];
        listener[0] = obs ->
        {
            listenerHit.set(true);
            property.removeListener(listener[0]);
        };
        property.addListener(listener[0]);
        ValueInvalidationRecorder recorder = new ValueInvalidationRecorder(property);

        property.invalidate();

        assertThat(listenerHit.get()).isTrue();
        assertThat(recorder.getCount()).isEqualTo(1);
    }

    @Test
    void invalidate_givenAdditionOfListenerDuringPropagation_thenShouldNotThrow()
    {
        var property = new SetProperty<String>();
        property.add("lala");

        var listenerHit = new AtomicBoolean(false);

        var listener = new ValueInvalidationListener[2];
        listener[0] = obs ->
        {
            listenerHit.set(true);
            property.addListener(listener[1]);
        };
        listener[1] = obs -> {};

        property.addListener(listener[0]);
        ValueInvalidationRecorder recorder = new ValueInvalidationRecorder(property);

        property.invalidate();

        assertThat(listenerHit.get()).isTrue();
        assertThat(recorder.getCount()).isEqualTo(1);
    }
}
