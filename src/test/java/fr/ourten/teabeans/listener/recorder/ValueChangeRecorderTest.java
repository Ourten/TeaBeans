package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueChangeRecorderTest
{
    @Test
    void valueChanged_givenActualChanges_thenShouldTriggerAndRecord()
    {
        Property<Integer> property = new Property<>(0);

        ValueChangeRecorder<Integer> recorder = new ValueChangeRecorder<>(property);

        property.setValue(5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsExactly(5);
    }

    @Test
    void valueChanged_givenNoChanges_thenShouldNotTriggerAndRecord()
    {
        Property<Integer> property = new Property<>(0);

        ValueChangeRecorder<Integer> recorder = new ValueChangeRecorder<>(property);

        property.setValue(property.getValue());

        assertThat(recorder.getCount()).isEqualTo(0);
        assertThat(recorder.getOldValues()).isEmpty();
        assertThat(recorder.getNewValues()).isEmpty();
    }
}
