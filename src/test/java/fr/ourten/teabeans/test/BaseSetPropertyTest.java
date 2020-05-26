package fr.ourten.teabeans.test;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.value.BaseSetProperty;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseSetPropertyTest
{
    Set<Integer>             set;
    BaseSetProperty<Integer> property;
    int                      count;

    @BeforeEach
    public void setup()
    {
        this.set = Sets.newHashSet(Arrays.asList(0, 2, 3, 5));
        this.property = new BaseSetProperty<>(this.set, "testIntegerSetProperty");
        this.count = 0;
    }

    @Test
    public void testConstructorSetOnly()
    {
        final BaseSetProperty<Integer> property = new BaseSetProperty<>(this.set);

        final String actual = property.getName();

        assertThat(actual).isEmpty();
    }

    @Test
    public void testConstructorSetNull()
    {
        final BaseSetProperty<Integer> property = new BaseSetProperty<>(null);

        final int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        final BaseSetProperty<Integer> property = new BaseSetProperty<>(LinkedHashSet::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedHashSet.class);
    }

    @Test
    public void testSetPropertySize()
    {
        final int actual = this.property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testSetPropertyValue()
    {
        assertThat(this.property.getValue().toArray()).containsExactly(this.set.toArray());

        assertThat(this.property.contains(5)).isTrue();
        assertThat(this.property.contains(7)).isFalse();

        assertThat(this.property.isEmpty()).isFalse();

        this.property.clear();

        assertThat(this.property.isEmpty()).isTrue();
    }

    @Test
    public void testSetRemoval()
    {
        final Set<String> stringSet = new HashSet<>(Arrays.asList("test1", "test2", "test3"));
        final BaseSetProperty<String> stringProperty = new BaseSetProperty<>(stringSet, "testStringSetProperty");

        assertThat(stringProperty.remove("something")).isFalse();
        assertThat(stringProperty.remove("test2")).isTrue();
        assertThat(stringProperty.contains("test2")).isFalse();
    }

    @Test
    public void testSetAdd()
    {
        this.property.add(7);
        assertThat(this.property.getValue()).contains(7);
    }

    @Test
    public void testSetPropertyInvalidationListener()
    {
        this.property.addListener(observable -> BaseSetPropertyTest.this.count++);

        this.property.add(6);
        assertThat(this.count).isEqualTo(1);

        this.property.remove(2);
        assertThat(this.count).isEqualTo(2);
    }

    @Test
    public void testSetPropertyListChangeListener()
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

        final ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isIn(18, 20, 22,24);
            this.count++;
        };

        this.property.addListener(addListener);
        this.property.add(18);
        this.property.addAll(Arrays.asList(20, 22,24));
        assertThat(this.count).isEqualTo(4);
        this.property.removeListener(addListener);

        this.count = 0;
        this.property.clear();
        this.property.addAll(Arrays.asList(18, 20, 22, 24, 26));
        final ListValueChangeListener<Integer> clearListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isIn(18, 20, 22, 24, 26);
            this.count++;
        };
        this.property.addListener(clearListener);

        this.property.clear();
        assertThat(this.count).isEqualTo(5);
    }

    @Test
    public void testListPropertyReplace()
    {
        this.property.replace(3, 9);

        assertThat(this.property.contains(9)).isTrue();
        assertThat(this.property.contains(3)).isFalse();
    }
}
