package fr.ourten.teabeans.property.named;

import org.junit.jupiter.api.Test;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NamedPropertyTest
{
    @Test
    void getName_givenPropertyName_thenShouldStoreIt()
    {
        NamedProperty<Integer> property = new NamedProperty<>(1, "test");

        assertThat(property.getName()).isEqualTo("test");
    }

    @Test
    void getName_givenListPropertyName_thenShouldStoreIt()
    {
        NamedListProperty<Integer> property = new NamedListProperty<>(singletonList(1), "test");

        assertThat(property.getName()).isEqualTo("test");
    }

    @Test
    void getName_givenSetPropertyName_thenShouldStoreIt()
    {
        NamedSetProperty<Integer> property = new NamedSetProperty<>(singleton(1), "test");

        assertThat(property.getName()).isEqualTo("test");
    }

    @Test
    void getName_givenMapPropertyName_thenShouldStoreIt()
    {
        NamedMapProperty<Integer, Integer> property = new NamedMapProperty<>(singletonMap(1, 2), "test");

        assertThat(property.getName()).isEqualTo("test");
    }

    @Test
    void getName_givenReducePropertyName_thenShouldStoreIt()
    {
        NamedReduceProperty<Integer, Integer> property = NamedReduceProperty.reduce("test", Integer::sum);

        assertThat(property.getName()).isEqualTo("test");
    }
}
