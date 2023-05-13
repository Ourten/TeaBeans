package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.property.ListProperty;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ListValueChangeRecorderTest
{
    @Test
    void valueChanged_givenAdd_thenShouldTriggerAndRecord()
    {
        ListProperty<Integer> property = new ListProperty<>(singletonList(0));

        ListValueChangeRecorder<Integer> recorder = new ListValueChangeRecorder<>(property);

        property.add(5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsNull();
        assertThat(recorder.getNewValues()).containsExactly(5);
    }

    @Test
    void valueChanged_givenRemove_thenShouldTriggerAndRecord()
    {
        ListProperty<Integer> property = new ListProperty<>(singletonList(0));

        ListValueChangeRecorder<Integer> recorder = new ListValueChangeRecorder<>(property);

        property.remove(0);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsNull();
    }

    @Test
    void valueChanged_givenSet_thenShouldTriggerAndRecord()
    {
        ListProperty<Integer> property = new ListProperty<>(singletonList(0));

        ListValueChangeRecorder<Integer> recorder = new ListValueChangeRecorder<>(property);

        property.set(0, 5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsExactly(5);
    }
}
