package io.github.thdudk.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.thdudk.ComponentIdPairKeyDeserializer;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.val;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

// RUN THE PROGRAM FROM THIS CLASS
// I'm not sure why that's needed but the program refuses to run otherwise
// ref: https://www.reddit.com/r/JavaFX/comments/k7aa9q/comment/gexontj/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button
public class Core {
    public static void main(String[] args) {
        try(val reader = new BufferedReader(new InputStreamReader(System.in))) {
            while(true) {
                System.out.println("Actions: ");
                System.out.println("0: Quit");
                System.out.println("1: New Diagram");
                System.out.println("2. Open Example Diagram");
                System.out.print("\nPick an action: ");
                switch(reader.readLine()) {
                    case "0" -> {}
                    case "1" -> App.main(args);
                    case "2" -> loadMolecule(reader);
                    default -> {
                        System.out.println("Invalid input, please try again.");
                        continue;
                    }
                }
                break;
            }
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private static void loadMolecule(BufferedReader reader) throws IOException {
        System.out.println("Molecules:");
        // read all file paths into memory
        List<String> files;

        try (Stream<Path> stream = Files.list(Paths.get("./src/main/resources/molecules"))) {
            files = stream
                .filter(file -> !Files.isDirectory(file))
                .map(Path::getFileName)
                .map(Path::toString)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // print out all available files
        for(int i = 0; i < files.size(); i++)
            System.out.println(i + ") " + files.get(i));

        int chosen;
        // get valid user input
        while(true) {
            System.out.print("File number: ");
            try {
                chosen = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("Invalid input. Please try again");
                continue;
            }
            if(chosen >= 0 && chosen < files.size()) break;
        }

        App.launchGraph(readGraph(new File("./src/main/resources/molecules/" + files.get(chosen))));
    }
    private static WeightedGraph<ComponentIdPair, Bonds> readGraph(File file) throws IOException {
        // create object mapper
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(ComponentIdPair.class, new ComponentIdPairKeyDeserializer());
        mapper.registerModule(simpleModule);

        // read as a graph
        return mapper.readValue(file, new TypeReference<AdjacencyListWeightedGraphImpl<ComponentIdPair, Bonds>>(){});
    }
}
