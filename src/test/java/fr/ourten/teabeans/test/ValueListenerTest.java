package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueListenerTest
{
    private boolean           used;
    private Property<Integer> property;

    @BeforeEach
    public void setup()
    {
        property = new Property<>(5);
        used = false;
    }

    @AfterEach
    public void after()
    {
        property.setValue(6);
        assertThat(used).isTrue();
    }

    @Test
    public void testInvalidationListener()
    {
        property.addListener(observable -> used = true);
    }

    @Test
    public void testChangeListener()
    {
        property.addListener((observable, oldValue, newValue) ->
        {
            used = true;
            assertThat(oldValue).isEqualTo(5);
            assertThat(newValue).isEqualTo(6);
        });
    }
}