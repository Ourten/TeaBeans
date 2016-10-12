package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Test;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.WritableValue;

/**
 * @author Ourten 12 oct. 2016
 */
public class ObservableValueTest
{
    @Test
    public void testPresentCheck()
    {
        final ObservableValue<Integer> obs = new BaseProperty<>(2, "testObservableValue");

        Assert.assertTrue("should be true", obs.isPresent());

        ((WritableValue<Integer>) obs).setValue(null);

        Assert.assertFalse("should be false", obs.isPresent());
    }
}