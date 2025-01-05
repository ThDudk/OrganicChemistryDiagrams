package io.github.thdudk.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Bonds {
    SINGLE(1, "Single"),
    DOUBLE(2, "Double"),
    TRIPLE(3, "Triple");

    public final int number;
    public final String displayName;
}
