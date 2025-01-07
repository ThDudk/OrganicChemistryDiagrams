package io.github.thdudk;

import java.util.Map;

import static java.util.Map.entry;

/// collection of prefixes for certain chemical things. I may replace this with a file later on if I feel like it. (probably not)
public abstract class Prefixes{
    public final static Map<Integer, String> carbonLength = Map.ofEntries(
        entry(1, "meth"), entry(2, "eth"), entry(3, "prop"),
        entry(4, "but"), entry(5, "pent"), entry(6, "hex"),
        entry(7, "hept"), entry(8, "oct"), entry(9, "non"),
        entry(10, "dec"), entry(11, "undec"), entry(12, "dodec"),
        entry(13, "tridec"), entry(14, "tetradec"), entry(15, "pentadec"),
        entry(16, "hexadec"), entry(17, "heptadec"), entry(18, "octadec"),
        entry(19, "nonadec"), entry(20, "icos"), entry(21, "henicos"),
        entry(22, "docos"), entry(23, "tricos"), entry(24, "tetracos"),
        entry(25, "pentacos"), entry(26, "hexacos"), entry(27, "heptacos"),
        entry(28, "octacos"), entry(29, "nonacos"), entry(30, "triacont"),
        entry(31, "hentriacont"), entry(32, "dotriacont"), entry(33, "tritriacont"),
        entry(34, "tetratriacont"), entry(35, "pentatriacont"), entry(36, "hexatriacont"),
        entry(37, "heptatriacont"), entry(38, "octatriacont"), entry(39, "nonatriacont"),
        entry(40, "quadragont"), entry(41, "unquadragont"), entry(42, "duoquadragont"),
        entry(43, "trequadragont"), entry(44, "tetraquadragont"), entry(45, "pentquadragont"),
        entry(46, "hexquadragont"), entry(47, "heptquadragont"), entry(48, "octquadragont"),
        entry(49, "nonquadragont"), entry(50, "quinquagont"));
    public final static Map<Integer, String> componentCount = Map.ofEntries(
        entry(1, ""), entry(2, "di"), entry(3, "tri"),
        entry(4, "tetra"), entry(5, "hepta"), entry(6, "hexa"),
        entry(7, "hepta"), entry(8, "octa"), entry(9, "nona"),
        entry(10, "deca"), entry(11, "undeca"), entry(12, "dodeca"),
        entry(13, "trideca"), entry(14, "tetradeca"), entry(15, "pentadeca"),
        entry(16, "hexadeca"), entry(17, "heptadeca"), entry(18, "octadeca"),
        entry(19, "nonadeca"), entry(20, "icosa"), entry(21, "henicosa"),
        entry(22, "docosa"), entry(23, "tricosa"), entry(24, "tetracosa"),
        entry(25, "pentacosa"), entry(26, "hexacosa"), entry(27, "heptacosa"),
        entry(28, "octacosa"), entry(29, "nonacosa"), entry(30, "triacontaka"),
        entry(31, "hentriaconta"), entry(32, "dotriaconta"), entry(33, "tritriaconta"),
        entry(34, "tetra-triaconta"), entry(35, "pentatriaconta"), entry(36, "hexatriaconta"),
        entry(37, "heptatriaconta"), entry(38, "octatriaconta"), entry(39, "nonatriaconta"),
        entry(40, "quadraginta"), entry(41, "unquadraginta"), entry(42, "duoquadraginta"),
        entry(43, "trequadraginta"), entry(44, "tetrarquadraginta"), entry(45, "pentquadraginta"),
        entry(46, "hexquadraginta"), entry(47, "heptquadraginta"), entry(48, "octquadraginta"),
        entry(49, "nonquadraginta"), entry(50, "quinquaginta"));
    public final static Map<Integer, String> BondComponentCount = Map.ofEntries(
        entry(1, ""), entry(2, "di"), entry(3, "tri"),
        entry(4, "tetr"), entry(5, "hept"), entry(6, "hex"),
        entry(7, "hept"), entry(8, "oct"), entry(9, "non"),
        entry(10, "dec"), entry(11, "undec"), entry(12, "dodec"),
        entry(13, "tridec"), entry(14, "tetradec"), entry(15, "pentadec"),
        entry(16, "hexadec"), entry(17, "heptadec"), entry(18, "octadec"),
        entry(19, "nonadec"), entry(20, "icos"), entry(21, "henicos"),
        entry(22, "docos"), entry(23, "tricos"), entry(24, "tetracos"),
        entry(25, "pentacos"), entry(26, "hexacos"), entry(27, "heptacos"),
        entry(28, "octacos"), entry(29, "nonacos"), entry(30, "triacontak"),
        entry(31, "hentriacont"), entry(32, "dotriacont"), entry(33, "tritriacont"),
        entry(34, "tetra-triacont"), entry(35, "pentatriacont"), entry(36, "hexatriacont"),
        entry(37, "heptatriacont"), entry(38, "octatriacont"), entry(39, "nonatriacont"),
        entry(40, "quadragint"), entry(41, "unquadragint"), entry(42, "duoquadragint"),
        entry(43, "trequadragint"), entry(44, "tetrarquadragint"), entry(45, "pentquadragint"),
        entry(46, "hexquadragint"), entry(47, "heptquadragint"), entry(48, "octquadragint"),
        entry(49, "nonquadragint"), entry(50, "quinquagint"));
}