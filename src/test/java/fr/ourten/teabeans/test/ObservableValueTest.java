package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.WritableValue;

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

        Assert.assertTrue("should be true", obs.isPresent());

        ((WritableValue<Integer>) obs).setValue(null);

        Assert.assertFalse("should be false", obs.isPresent());

        obs.ifPresent(i -> this.count++);

        Assert.assertEquals("should be equals", 0, this.count);

        ((WritableValue<Integer>) obs).setValue(5);

        obs.ifPresent(i -> this.count++);

        Assert.assertEquals("should be equals", 1, this.count);
    }
}