package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;

public interface RootNamer {
    String getBranchNamePortion();
    String getRootNamePortion();
    String getName();
}
