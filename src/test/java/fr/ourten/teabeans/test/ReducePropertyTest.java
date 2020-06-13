package fr.ourten.teabeans.test;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ReduceProperty;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReducePropertyTest
{
    @Test
    void computeValue_givenSimpleMultiplication_thenShouldReduce()
    {
        BaseProperty<Integer> two = new BaseProperty<>(2, "two");
        BaseProperty<Integer> four = new BaseProperty<>(4, "four");

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
        BaseProperty<Integer> two = new BaseProperty<>(2, "two");
        BaseProperty<Integer> four = new BaseProperty<>(4, "four");

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")), two, four);

        assertThat(concatenated.getValue()).isEqualTo("2,4");

        four.setValue(8);

        assertThat(concatenated.isValid()).isFalse();
        assertThat(concatenated.getValue()).isEqualTo("2,8");
    }

    @Test
    void bind_givenInvalidObservable_thenShouldThrow()
    {
        BaseProperty<Integer> two = new BaseProperty<>(2, "two");
        Observable observable = new TestObservable();

        ReduceProperty<Integer, String> concatenated = ReduceProperty.reduce(values -> values.map(String::valueOf).collect(joining(",")));
        assertThatThrownBy(() -> concatenated.bind(two, observable)).isInstanceOf(RuntimeException.class).hasMessageContaining("ObservableValue");
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
        public void mute()
        {

        }

        @Override
        public void unmute()
        {

        }

        @Override
        public void muteWhile(Runnable runnable)
        {

        }

        @Override
        public boolean isMuted()
        {
            return false;
        }
    }
}
