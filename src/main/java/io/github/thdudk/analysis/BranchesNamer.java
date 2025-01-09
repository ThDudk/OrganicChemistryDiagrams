package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BranchesNamer {
    public static String getName(Map<WeightedGraph<ComponentIdPair, Bonds>, List<Integer>> branches) {
        val positions = new HashMap<String, List<Integer>>();

        // get the name and positions of all branches
        for(val entry : branches.entrySet()) {
            String name = BranchNamer.getName(entry.getKey());

            // ignore hydroxyls
            if(entry.getKey().getNodes().stream().anyMatch(a -> a.getComponent().equals(AtomicComponents.HYDROXYL)))
                continue;

            positions.putIfAbsent(name, new ArrayList<>());
            positions.get(name).addAll(entry.getValue());
        }

        if(positions.isEmpty()) return "";

        // alphabetically sort the names
        List<String> names = new ArrayList<>(positions.keySet());
        names.sort(String::compareTo);

        // numerically sort the positions
        for(List<Integer> posArr : positions.values())
            posArr.sort(Integer::compareTo);

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
