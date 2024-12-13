import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AtomicComponent {
    Carbon(4, "N/A"),
    Fluorine(1, "floro"),
    Chlorine(1, "chloro"),
    Bromine(1, "bromo"),
    Iodine(1, "iodo"),
    Hydroxyl(1, "N/A");

    public int bondingCapacity;
    public String name;
}
