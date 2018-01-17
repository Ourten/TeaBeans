package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.WritableValue;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 12 oct. 2016
 */
public class ObservableValueTest
{
    private int count;

    @Before
    public void setup()
    {
        this.count = 0;
    }

    @Test
    public void testPresentCheck()
    {
        final ObservableValue<Integer> obs = new BaseProperty<>(2, "testObservableValue");

        assertThat(obs.isPresent()).isTrue();

        ((WritableValue<Integer>) obs).setValue(null);

        assertThat(obs.isPresent()).isFalse();

        obs.ifPresent(i -> this.count++);

        assertThat(this.count).isEqualTo(0);

        ((WritableValue<Integer>) obs).setValue(5);

        obs.ifPresent(i -> this.count++);

        assertThat(this.count).isEqualTo(1);
    }
}