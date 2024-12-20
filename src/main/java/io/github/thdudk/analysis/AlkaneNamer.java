package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;

@RequiredArgsConstructor
public class AlkaneNamer implements RootNamer {
    private final List<ComponentIdPair> rootPath;

    private int getCarbonChainLength() {
        return rootPath.size();
    }

    public String getName() {
        return Prefixes.carbonLength.get(getCarbonChainLength()) + "ane";
    }
}
