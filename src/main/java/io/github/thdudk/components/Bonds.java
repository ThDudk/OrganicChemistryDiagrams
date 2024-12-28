package io.github.thdudk.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Bonds {
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3);

    private final int number;
}
