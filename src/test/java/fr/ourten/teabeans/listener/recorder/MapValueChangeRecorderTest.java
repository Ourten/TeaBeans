package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.property.MapProperty;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class MapValueChangeRecorderTest
{
    @Test
    void valueChanged_givenPutNonExisting_thenShouldTriggerAndRecord()
    {
        MapProperty<String, Integer> property = new MapProperty<>(singletonMap("TEST", 0));

        MapValueChangeRecorder<String, Integer> recorder = new MapValueChangeRecorder<>(property);

        property.put("AGAIN", 5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsNull();
        assertThat(recorder.getNewValues()).containsExactly(5);
        assertThat(recorder.getKeys()).containsExactly("AGAIN");
    }

    @Test
    void valueChanged_givenPutExisting_thenShouldTriggerAndRecord()
    {
        MapProperty<String, Integer> property = new MapProperty<>(singletonMap("TEST", 0));

        MapValueChangeRecorder<String, Integer> recorder = new MapValueChangeRecorder<>(property);

        property.put("TEST", 5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsExactly(5);
        assertThat(recorder.getKeys()).containsExactly("TEST");
    }

    @Test
    void valueChanged_givenReplaceExisting_thenShouldTriggerAndRecord()
    {
        MapProperty<String, Integer> property = new MapProperty<>(singletonMap("TEST", 0));

        MapValueChangeRecorder<String, Integer> recorder = new MapValueChangeRecorder<>(property);

        property.replace("TEST", 5);

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsExactly(5);
        assertThat(recorder.getKeys()).containsExactly("TEST");
    }

    @Test
    void valueChanged_givenReplaceNonExisting_thenShouldNotTriggerAndRecord()
    {
        MapProperty<String, Integer> property = new MapProperty<>(singletonMap("TEST", 0));

        MapValueChangeRecorder<String, Integer> recorder = new MapValueChangeRecorder<>(property);

        property.replace("AGAIN", 5);

        assertThat(recorder.getCount()).isEqualTo(0);
        assertThat(recorder.getOldValues()).isEmpty();
        assertThat(recorder.getNewValues()).isEmpty();
        assertThat(recorder.getKeys()).isEmpty();
    }

    @Test
    void valueChanged_givenRemove_thenShouldTriggerAndRecord()
    {
        MapProperty<String, Integer> property = new MapProperty<>(singletonMap("TEST", 0));

        MapValueChangeRecorder<String, Integer> recorder = new MapValueChangeRecorder<>(property);

        property.remove("TEST");

        assertThat(recorder.getCount()).isEqualTo(1);
        assertThat(recorder.getOldValues()).containsExactly(0);
        assertThat(recorder.getNewValues()).containsNull();
        assertThat(recorder.getKeys()).containsExactly("TEST");
    }
}
