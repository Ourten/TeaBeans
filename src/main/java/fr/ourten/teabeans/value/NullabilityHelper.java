package fr.ourten.teabeans.value;

import java.util.Objects;

class NullabilityHelper
{
    static void requireNonNull(String message, Object... objects)
    {
        for (Object object : objects)
        {
            Objects.requireNonNull(object, message);
        }
    }
}
