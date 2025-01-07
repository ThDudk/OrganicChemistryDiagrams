package io.github.thdudk.analysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.thdudk.ComponentIdPairKeyDeserializer;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonTest {
    @Test
    void myTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(ComponentIdPair.class, new ComponentIdPairKeyDeserializer());
        mapper.registerModule(simpleModule);

        val molecule = TestMolecules.alkaneOf(2);
        String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(molecule);

        val deserialized = mapper.readValue(serialized, new TypeReference<AdjacencyListWeightedGraphImpl<ComponentIdPair, Bonds>>(){});

        assertEquals(molecule, deserialized);
    }
}
