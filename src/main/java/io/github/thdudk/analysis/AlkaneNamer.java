package io.github.thdudk.analysis;

import io.github.thdudk.CSAcademyConvertor;
import io.github.thdudk.Prefixes;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.val;

import javax.swing.text.html.Option;
import java.util.*;

@ToString
public class AlkaneNamer implements RootNamer {
    private final List<ComponentIdPair> rootPath;
    private final Map<Graph<ComponentIdPair>, List<Integer>> branches;

    public AlkaneNamer(Graph<ComponentIdPair> molecule) {
        // iterate through all root path candidates and choose the first valid one
        for(List<ComponentIdPair> path : getRootPathCandidates(molecule)) {
            val pathBranches = getBranches(molecule, path);

            if(producesInvalidBranches(molecule, path)) continue; // ignore this root path if there are invalid branches

            rootPath = path;
            branches = pathBranches;
            return;
        }

        // if the program has reached this point, it failed to find a valid root path
        throw new RuntimeException("Failed to locate valid root path");
    }
    protected static List<List<ComponentIdPair>> getRootPathCandidates(Graph<ComponentIdPair> molecule) {
        val carbonMolecule = molecule.filter(a -> a.getComponent().equals(AtomicComponents.CARBON));

        List<List<ComponentIdPair>> candidates = new ArrayList<>();

        // find all spanning paths with the max length
        Set<ComponentIdPair> edgeNodes = carbonMolecule.filter(n -> carbonMolecule.getOutDegree(n) <= 1).getNodes(); // degree <= 1 to support methane

        for(ComponentIdPair node : edgeNodes) {
            // goes through all possible paths and only keep those with the longest length
            for(List<ComponentIdPair> path : carbonMolecule.allFullyExtendedPathsFrom(node)) {
                // current path is equally long to the longest found
                if(candidates.isEmpty() || candidates.getFirst().size() == path.size())
                    candidates.add(path);

                // current path is longer than the longest found
                if(candidates.getFirst().size() < path.size()) {
                    candidates.clear();
                    candidates.add(path);
                }
            }
        }

        return candidates;
    }

    protected static boolean producesInvalidBranches(Graph<ComponentIdPair> molecule, List<ComponentIdPair> rootPath) {
        for(Graph<ComponentIdPair> branch : splitBranches(molecule, rootPath))
            for(ComponentIdPair component : branch.getNodes())
                // A branch is invalid when it splits into more branches
                if(molecule.getOutDegree(component) >= 3)
                    return true;

        return false; // all branches are valid
    }
    protected static Map<Graph<ComponentIdPair>, List<Integer>> getBranches(Graph<ComponentIdPair> molecule, List<ComponentIdPair> rootPath) {
        Map<Graph<ComponentIdPair>, List<Integer>> branches = new HashMap<>();

        // identify the positions of all branches
        for(Graph<ComponentIdPair> branch : splitBranches(molecule, rootPath)) {
            branches.putIfAbsent(branch, new ArrayList<>());

            int posIdx = -1;

            // iterate through all root path nodes until you find one connected to the branch
            for(ComponentIdPair element : branch.getNodes())
                for(int i = 0; i < rootPath.size(); i++)
                    if(molecule.getOutNeighbours(rootPath.get(i)).contains(element))
                        posIdx = i;

            // add the branch position to the map
            branches.get(branch).add(posIdx + 1);
        }

        return branches;
    }

    protected static Set<Graph<ComponentIdPair>> splitBranches(Graph<ComponentIdPair> molecule, List<ComponentIdPair> rootPath) {
        return molecule.filter(a -> !rootPath.contains(a)).getAllDisconnectedGraphs();
    }

    public String getName() {
        return BranchesNamer.getName(branches) // branch names
            + Prefixes.carbonLength.get(rootPath.size()) + "ane"; // root name
    }
}
