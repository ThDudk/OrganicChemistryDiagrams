package io.github.thdudk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;

import java.io.IOException;
import java.util.UUID;

/// Added bc jackson is the worst
public class ComponentIdPairKeyDeserializer extends KeyDeserializer
    {
        @Override
        public ComponentIdPair deserializeKey(final String key, final DeserializationContext ctxt ) throws IOException, JsonProcessingException
        {
            String componentStart = key.substring(key.indexOf("component=") + "component=".length());
            String componentStr = componentStart.substring(0, componentStart.indexOf(","));
            AtomicComponents component = AtomicComponents.valueOf(componentStr);

            String uuidStart = key.substring(key.indexOf("uuid=") + "uuid=".length());
            String uuidStr = uuidStart.substring(0, uuidStart.indexOf(")"));
            UUID uuid = UUID.fromString(uuidStr);

            return new ComponentIdPair(component, uuid);
        }
    }