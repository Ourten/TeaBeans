package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueListenerTest
{
    private boolean               used;
    private BaseProperty<Integer> property;

    @Before
    public void setup()
    {
        this.property = new BaseProperty<>(5, "testIntegerProperty");
        this.used = false;
    }

    @After
    public void after()
    {
        this.property.setValue(6);
        assertThat(this.used).isTrue();
    }

    @Test
    public void testInvalidationListener()
    {
        this.property.addListener(observable -> ValueListenerTest.this.used = true);
    }

    @Test
    public void testChangeListener()
    {
        this.property.addListener((observable, oldValue, newValue) ->
        {
            ValueListenerTest.this.used = true;
            assertThat(oldValue).isEqualTo(5);
            assertThat(newValue).isEqualTo(6);
        });
    }
}