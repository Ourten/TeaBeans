package fr.ourten.teabeans.test;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.value.BaseListProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseListPropertyTest
{
    List<Integer>             list;
    BaseListProperty<Integer> property;
    int                       count;

    @BeforeEach
    public void setup()
    {
        this.list = Arrays.asList(0, 2, 3, 5);
        this.property = new BaseListProperty<>(this.list, "testIntegerListProperty");
        this.count = 0;
    }

    @Test
    public void testConstructorListOnly()
    {
        final BaseListProperty<Integer> property = new BaseListProperty<>(this.list);

        final String actual = property.getName();

        assertThat(actual).isEmpty();
    }

    @Test
    public void testConstructorListNull()
    {
        final BaseListProperty<Integer> property = new BaseListProperty<>(null);

        final int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        final BaseListProperty<Integer> property = new BaseListProperty<>(LinkedList::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedList.class);
    }

    @Test
    public void testListPropertySize()
    {
        final int actual = this.property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testListPropertyValue()
    {
        assertThat(this.property.getValue().toArray()).containsExactly(this.list.toArray());

        assertThat(this.property.get(1)).isEqualTo(2);

        assertThat(this.property.contains(5)).isTrue();
        assertThat(this.property.contains(7)).isFalse();

        assertThat(this.property.indexOf(2)).isEqualTo(1);

        assertThat(this.property.isEmpty()).isFalse();

        this.property.clear();

        assertThat(this.property.isEmpty()).isTrue();
    }

    @Test
    public void testListRemoval()
    {
        final List<String> stringList = Arrays.asList("test1", "test2", "test3");
        final BaseListProperty<String> stringProperty = new BaseListProperty<>(stringList, "testStringListProperty");

        assertThat(stringProperty.remove("something")).isFalse();
        assertThat(stringProperty.remove("test2")).isTrue();
        assertThat(stringProperty.contains("test2")).isFalse();
    }

    @Test
    public void testListAdd()
    {
        this.property.add(7);
        assertThat(this.property.get(4)).isEqualTo(7);
    }

    @Test
    public void testListPropertyInvalidationListener()
    {
        this.property.addListener(observable -> BaseListPropertyTest.this.count++);

        this.property.add(6);
        assertThat(this.count).isEqualTo(1);

        this.property.remove(2);
        assertThat(this.count).isEqualTo(2);

        this.property.set(0, 4);
        assertThat(this.count).isEqualTo(3);

        this.property.sort();
        assertThat(this.count).isEqualTo(4);
    }

    @Test
    public void testListPropertyListChangeListener()
    {

        final ListValueChangeListener<Integer> removeListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(0);
        };

        this.property.addListener(removeListener);
        this.property.remove(0);
        this.property.removeListener(removeListener);

        final ListValueChangeListener<Integer> changeListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNotNull();
            assertThat(oldValue).isNotEqualTo(newValue);
            assertThat(oldValue).isEqualTo(2);
            assertThat(newValue).isEqualTo(5);
        };
        this.property.addListener(changeListener);
        this.property.set(0, 5);
        this.property.removeListener(changeListener);

        final ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isEqualTo(18);
            this.count++;
        };
        this.property.addListener(addListener);
        this.property.add(18);
        this.property.add(2, 18);
        this.property.addAll(Arrays.asList(18, 18, 18));
        assertThat(this.count).isEqualTo(5);
        this.property.removeListener(addListener);

        this.count = 0;
        this.property.clear();
        this.property.addAll(Arrays.asList(18, 18, 18, 18, 18));
        final ListValueChangeListener<Integer> clearListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(18);
            this.count++;
        };
        this.property.addListener(clearListener);

        this.property.clear();
        assertThat(this.count).isEqualTo(5);
    }

    @Test
    public void testListPropertySort()
    {
        final List<Integer> unsorted = Arrays.asList(1, 5, 6, 4, 10);
        final BaseListProperty<Integer> property = new BaseListProperty<>(unsorted, "testIntegerListProperty");

        property.sort();
        assertThat(property.getValue().toArray()).containsExactly(new Integer[] { 1, 4, 5, 6, 10 });

        property.sort(Collections.reverseOrder());
        assertThat(property.getValue().toArray()).containsExactly(new Integer[] { 10, 6, 5, 4, 1 });
    }

    @Test
    public void testListPropertyReplace()
    {
        this.property.replace(3, 9);

        assertThat(this.property.contains(9)).isTrue();
        assertThat(this.property.contains(3)).isFalse();
    }
}