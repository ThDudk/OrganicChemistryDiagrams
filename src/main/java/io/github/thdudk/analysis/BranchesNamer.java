package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BranchesNamer {
    public static String getName(Map<Graph<ComponentIdPair>, List<Integer>> branches) {
        if(branches.isEmpty()) return "";

        val positions = new HashMap<String, List<Integer>>();

        // get the name and positions of all branches
        for(val entry : branches.entrySet()) {
            String name = BranchNamer.getName(entry.getKey());

            positions.putIfAbsent(name, new ArrayList<>());
            positions.get(name).addAll(entry.getValue());
        }

        // alphabetically sort the names
        List<String> names = new ArrayList<>(positions.keySet());
        names.sort(String::compareTo);

        // put it all together
        StringBuilder branchNamePortion = new StringBuilder();

        for(String name : names) {
            for(Integer pos : positions.get(name))
                branchNamePortion.append(pos).append(",");

            // remove the last ", "
            branchNamePortion.deleteCharAt(branchNamePortion.length() - 1);

            branchNamePortion.append("-");
            branchNamePortion.append(Prefixes.componentCount.get(positions.get(name).size())).append(name);
            branchNamePortion.append("-");
        }
        branchNamePortion.deleteCharAt(branchNamePortion.length() - 1);

        return branchNamePortion.toString();
    }
}
