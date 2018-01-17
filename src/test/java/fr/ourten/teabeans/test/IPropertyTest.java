package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.IProperty;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 11 oct. 2016
 */
public class IPropertyTest
{
    @Test(expected = NoSuchElementException.class)
    public void testThrow()
    {
        final IProperty<String> property = new BaseProperty<>("test", "throwTestProperty");

        assertThat(property.getOrThrow()).isEqualTo("test");

        property.setValue(null);
        property.getOrThrow();
    }

    @Test
    public void testDefault()
    {
        final IProperty<String> property = new BaseProperty<>(null, "defaultTestProperty");

        assertThat(property.getOrDefault("default")).isEqualTo("default");

        property.setValue("test");

        assertThat(property.getOrDefault("default")).isEqualTo("test");
    }
}
