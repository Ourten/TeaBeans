package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.property.SetProperty;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class SetValueChangeRecorderTest
{
    @Test
    void valueChanged_givenAdd_thenShouldTriggerAndRecord()
    {
        SetProperty<Integer> property = new SetProperty<>(singleton(0));

        SetValueChangeRecorder<Integer> recorder = new SetValueChangeRecorder<>(property);

        property.add(5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsNull();
        assertThat(recorder.getNewValues()).containsExactly(5);
    }

    @Test
    void valueChanged_givenRemove_thenShouldTriggerAndRecord()
    {
        SetProperty<Integer> property = new SetProperty<>(singleton(0));

        SetValueChangeRecorder<Integer> recorder = new SetValueChangeRecorder<>(property);

        property.remove(0);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsNull();
    }

    @Test
    void valueChanged_givenReplace_thenShouldTriggerAndRecord()
    {
        SetProperty<Integer> property = new SetProperty<>(new HashSet<>(asList(0, 1, 2, 3, 4)));

        SetValueChangeRecorder<Integer> recorder = new SetValueChangeRecorder<>(property);

        property.replace(3, 5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(3);
        assertThat(recorder.getNewValues()).containsExactly(5);
    }
}
