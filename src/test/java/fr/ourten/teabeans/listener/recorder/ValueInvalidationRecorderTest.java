package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueInvalidationRecorderTest
{
    @Test
    void invalidated_givenActualChanges_thenShouldTriggerInvalidation()
    {
        Property<Integer> property = new Property<>(0);

        ValueInvalidationRecorder recorder = new ValueInvalidationRecorder(property);

        property.setValue(5);

        assertThat(recorder.getCount()).isEqualTo(1);
    }

    @Test
    void invalidated_givenNoChanges_thenShouldStillTriggerInvalidation()
    {
        Property<Integer> property = new Property<>(0);

        ValueInvalidationRecorder recorder = new ValueInvalidationRecorder(property);

        property.setValue(property.getValue());

        assertThat(recorder.getCount()).isEqualTo(1);
    }
}
