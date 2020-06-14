package fr.ourten.teabeans.test;

import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueListenerTest
{
    @Test
    void invalidate_givenSimpleChange_thenShouldTriggerInvalidation()
    {
        Property<Integer> property = new Property<>(5);

        AtomicInteger counter = new AtomicInteger(0);

        property.addListener(obs -> counter.getAndIncrement());

        property.setValue(property.getValue());
        assertThat(counter.get()).isEqualTo(1);

        property.setValue(6);
        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void invalidate_givenSimpleChange_thenShouldTriggerChanges()
    {
        Property<Integer> property = new Property<>(5);

        AtomicInteger counter = new AtomicInteger(0);

        property.addListener((observable, oldValue, newValue) ->
        {
            counter.getAndIncrement();
            assertThat(oldValue).isEqualTo(5);
            assertThat(newValue).isEqualTo(6);
        });

        property.setValue(property.getValue());
        assertThat(counter.get()).isEqualTo(0);

        property.setValue(6);
        assertThat(counter.get()).isEqualTo(1);
    }
}