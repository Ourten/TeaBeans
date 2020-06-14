package fr.ourten.teabeans.test;

import fr.ourten.teabeans.property.Property;
import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.WritableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 12 oct. 2016
 */
public class ObservableValueTest
{
    private int count;

    @BeforeEach
    public void setup()
    {
        count = 0;
    }

    @Test
    public void testPresentCheck()
    {
        ObservableValue<Integer> obs = new Property<>(2);

        assertThat(obs.isPresent()).isTrue();

        ((WritableValue<Integer>) obs).setValue(null);

        assertThat(obs.isPresent()).isFalse();

        obs.ifPresent(i -> count++);

        assertThat(count).isEqualTo(0);

        ((WritableValue<Integer>) obs).setValue(5);

        obs.ifPresent(i -> count++);

        assertThat(count).isEqualTo(1);
    }
}