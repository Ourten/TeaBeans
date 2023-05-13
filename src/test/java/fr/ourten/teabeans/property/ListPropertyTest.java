package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.recorder.ListValueChangeRecorder;
import fr.ourten.teabeans.listener.recorder.ValueInvalidationRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ListPropertyTest
{
    List<Integer>         list;
    ListProperty<Integer> property;
    int                   count;

    @BeforeEach
    public void setup()
    {
        list = Arrays.asList(0, 2, 3, 5);
        property = new ListProperty<>(list);
        count = 0;
    }

    @Test
    public void testConstructorListNull()
    {
        ListProperty<Integer> property = new ListProperty<>(null);

        int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        ListProperty<Integer> property = new ListProperty<>(LinkedList::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedList.class);
    }

    @Test
    public void testListPropertySize()
    {
        int actual = property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testListPropertyValue()
    {
        assertThat(property.getValue().toArray()).containsExactly(list.toArray());

        assertThat(property.get(1)).isEqualTo(2);

        assertThat(property.contains(5)).isTrue();
        assertThat(property.contains(7)).isFalse();

        assertThat(property.indexOf(2)).isEqualTo(1);

        assertThat(property.isEmpty()).isFalse();

        property.clear();

        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testListRemoval()
    {
        List<String> stringList = Arrays.asList("test1", "test2", "test3");
        ListProperty<String> stringProperty = new ListProperty<>(stringList);

        assertThat(stringProperty.remove("something")).isFalse();
        assertThat(stringProperty.remove("test2")).isTrue();
        assertThat(stringProperty.contains("test2")).isFalse();
    }

    @Test
    public void testListAdd()
    {
        property.add(7);
        assertThat(property.get(4)).isEqualTo(7);
    }

    @Test
    public void testListPropertyInvalidationListener()
    {
        property.addListener(observable -> count++);

        property.add(6);
        assertThat(count).isEqualTo(1);

        property.remove(2);
        assertThat(count).isEqualTo(2);

        property.set(0, 4);
        assertThat(count).isEqualTo(3);

        property.sort();
        assertThat(count).isEqualTo(4);
    }

    @Test
    public void testListPropertyListChangeListener()
    {

        ListValueChangeListener<Integer> removeListener = (change) ->
        {
            assertThat(change.oldValue()).isNotNull();
            assertThat(change.newValue()).isNull();
            assertThat(change.oldValue()).isEqualTo(0);
        };

        property.addListChangeListener(removeListener);
        property.remove(0);
        property.removeListChangeListener(removeListener);

        ListValueChangeListener<Integer> changeListener = (change) ->
        {
            assertThat(change.oldValue()).isNotNull();
            assertThat(change.newValue()).isNotNull();
            assertThat(change.oldValue()).isNotEqualTo(change.newValue());
            assertThat(change.oldValue()).isEqualTo(2);
            assertThat(change.newValue()).isEqualTo(5);
        };
        property.addListChangeListener(changeListener);
        property.set(0, 5);
        property.removeListChangeListener(changeListener);

        ListValueChangeListener<Integer> addListener = (change) ->
        {
            assertThat(change.oldValue()).isNull();
            assertThat(change.newValue()).isNotNull();
            assertThat(change.newValue()).isEqualTo(18);
            count++;
        };
        property.addListChangeListener(addListener);
        property.add(18);
        property.add(2, 18);
        property.addAll(Arrays.asList(18, 18, 18));
        assertThat(count).isEqualTo(5);
        property.removeListChangeListener(addListener);

        count = 0;
        property.clear();
        property.addAll(Arrays.asList(18, 18, 18, 18, 18));
        ListValueChangeListener<Integer> clearListener = (change) ->
        {
            assertThat(change.oldValue()).isNotNull();
            assertThat(change.newValue()).isNull();
            assertThat(change.oldValue()).isEqualTo(18);
            count++;
        };
        property.addListChangeListener(clearListener);

        property.clear();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void testListPropertySort()
    {
        List<Integer> unsorted = Arrays.asList(1, 5, 6, 4, 10);
        ListProperty<Integer> property = new ListProperty<>(unsorted);

        property.sort();
        assertThat(property.getValue().toArray()).containsExactly(new Integer[]{1, 4, 5, 6, 10});

        property.sort(Collections.reverseOrder());
        assertThat(property.getValue().toArray()).containsExactly(new Integer[]{10, 6, 5, 4, 1});
    }

    @Test
    void getValue_givenSimpleList_thenShouldCreateImmutableView()
    {
        ListProperty<Integer> property = new ListProperty<>(IntStream.range(0, 10).boxed().collect(toList()));

        List<Integer> immutableValues = property.getValue();

        assertThat(immutableValues).containsExactly(IntStream.range(0, 10).boxed().toArray(Integer[]::new));

        assertThatThrownBy(() -> immutableValues.add(0)).isInstanceOf(UnsupportedOperationException.class);

        property.add(10);

        assertThat(immutableValues).containsExactly(IntStream.range(0, 11).boxed().toArray(Integer[]::new));
    }

    @Test
    void getValue_givenSimpleListThenReplaced_thenShouldReCreateImmutableView()
    {
        ListProperty<Integer> property = new ListProperty<>(IntStream.range(0, 10).boxed().collect(toList()));

        List<Integer> immutableValues = property.getValue();
        assertThat(immutableValues).isSameAs(property.getValue());

        property.setValue(IntStream.range(10, 20).boxed().collect(toList()));
        assertThat(immutableValues).containsExactly(IntStream.range(0, 10).boxed().toArray(Integer[]::new));

        assertThat(immutableValues).isNotSameAs(property.getValue());
        assertThat(property.getValue()).containsExactly(IntStream.range(10, 20).boxed().toArray(Integer[]::new));
    }

    @Test
    void addListener_givenBoundPropertyThenAddingListChangeListener_thenShouldStartObserving()
    {
        ListProperty<Integer> property = new ListProperty<>(IntStream.range(0, 10).boxed().collect(toList()));
        ListProperty<Integer> secondProperty = spy(new ListProperty<>(IntStream.range(10, 20).boxed().collect(toList())));
        property.bindProperty(secondProperty);

        verify(secondProperty, never()).addListener(any(ValueInvalidationListener.class));

        new ListValueChangeRecorder<>(property);

        verify(secondProperty, atMostOnce()).addListener(any(ValueInvalidationListener.class));
    }

    @Test
    void invalidate_givenRemovalOfListenerDuringPropagation_thenShouldNotThrow()
    {
        var property = new ListProperty<String>();
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
        var property = new ListProperty<String>();
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