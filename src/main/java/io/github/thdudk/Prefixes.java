package io.github.thdudk;

import java.util.Map;

import static java.util.Map.entry;

/// collection of prefixes for certain chemical things. I may replace this with a file later on if I feel like it. (probably not)
public abstract class Prefixes{
    public final static Map<Integer, String> carbonLength = Map.ofEntries(
        entry(1, "meth"), entry(2, "eth"), entry(3, "prop"),
        entry(4, "but"), entry(5, "pent"), entry(6, "hex"),
        entry(7, "hept"), entry(8, "oct"), entry(9, "non"),
        entry(10, "dec"), entry(11, "undec"), entry(12, "dodec"));
    public final static Map<Integer, String> componentCount = Map.ofEntries(
        entry(1, "mono"), entry(2, "di"), entry(3, "tri"),
        entry(4, "tetra"), entry(5, "hepta"), entry(6, "hexa"),
        entry(7, "hepta"), entry(8, "octa"), entry(9, "nona"),
        entry(10, "deca"), entry(11, "undeca"), entry(12, "dodeca"));
}