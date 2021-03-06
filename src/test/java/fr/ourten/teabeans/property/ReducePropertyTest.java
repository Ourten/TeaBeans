package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReducePropertyTest
{
    @Test
    void computeValue_givenSimpleMultiplication_thenShouldReduce()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        ReduceProperty<Integer, Integer> multiplied = ReduceProperty.reduce((first, second) -> first * second);
        multiplied.bind(two, four);

        assertThat(multiplied.getValue()).isEqualTo(8);

        four.setValue(8);

        assertThat(multiplied.isValid()).isFalse();
        assertThat(multiplied.getValue()).isEqualTo(16);
    }

    @Test
    void computeValue_givenSimpleJoin_thenShouldReduce()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);

        assertThat(concatenated.getValue()).isEqualTo("2,4");

        four.setValue(8);

        assertThat(concatenated.isValid()).isFalse();
        assertThat(concatenated.getValue()).isEqualTo("2,8");
    }

    @Test
    void bind_givenInvalidObservable_thenShouldThrow()
    {
        Property<Integer> two = new Property<>(2);
        Observable observable = new TestObservable();

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")));
        assertThatThrownBy(() -> concatenated.bind(two, observable)).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("ObservableValue");
    }

    @Test
    void setValue_givenManualSet_thenShouldStopBinding()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);

        assertThat(concatenated.getValue()).isEqualTo("2,4");

        concatenated.setValue("ABC");
        assertThat(concatenated.getValue()).isEqualTo("ABC");

        four.setValue(8);
        assertThat(concatenated.getValue()).isEqualTo("ABC");
    }

    @Test
    void setValue_givenPropertyBind_thenShouldStopBinding()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        Property<String> result = new Property<>("ABC");

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);

        assertThat(concatenated.getValue()).isEqualTo("2,4");

        concatenated.bindProperty(result);
        assertThat(concatenated.getValue()).isEqualTo(result.getValue());

        four.setValue(8);
        assertThat(concatenated.getValue()).isEqualTo(result.getValue());
    }

    @Test
    void setValue_givenPropertyBind_thenShouldPropagateChange()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        Property<String> result = new Property<>("ABC");

        AtomicInteger invalidated = new AtomicInteger();
        AtomicInteger changed = new AtomicInteger();

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);
        concatenated.addListener(obs -> invalidated.getAndIncrement());

        assertThat(concatenated.getValue()).isEqualTo("2,4");

        concatenated.addChangeListener((obs, oldValue, newValue) ->
        {
            changed.getAndIncrement();
            assertThat(oldValue).isEqualTo("2,4");
            assertThat(newValue).isEqualTo(result.getValue());
        });

        concatenated.bindProperty(result);

        assertThat(invalidated.get()).isEqualTo(1);
        assertThat(changed.get()).isEqualTo(1);
    }

    @Test
    void actAsBinding_givenPropertyBehaviorThenBinding_thenShouldRestoreBindingDependencies()
    {
        Property<Integer> two = new Property<>(2);
        Property<Integer> four = new Property<>(4);

        Property<String> result = new Property<>("ABC");

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);
        concatenated.bindProperty(result);

        assertThat(concatenated.getValue()).isEqualTo(result.getValue());

        concatenated.actAsBinding();

        assertThat(concatenated.getValue()).isEqualTo("2,4");
        four.setValue(8);
        assertThat(concatenated.getValue()).isEqualTo("2,8");
    }

    private static class TestObservable implements Observable
    {
        @Override
        public void addListener(ValueInvalidationListener listener)
        {

        }

        @Override
        public void removeListener(ValueInvalidationListener listener)
        {

        }

        @Override
        public void addChangeListener(ValueInvalidationListener listener)
        {
            
        }

        @Override
        public void removeChangeListener(ValueInvalidationListener listener)
        {

        }

        @Override
        public void mute()
        {

        }

        @Override
        public void unmute()
        {

        }

        @Override
        public boolean isMuted()
        {
            return false;
        }
    }
}
