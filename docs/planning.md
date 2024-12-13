# Planning
There are essentially three components to the project.
1. Representing organic molecules as a graph
2. Analyzing and naming/classifying these molecules
3. Drawing

## Representations
This is the first package in the project. Molecules will be represented as a graph of atomic or polyatomic components where all unfilled orbitals will denote an edges leading to a hydrogen.

For alpha -> beta, the only necessary components are:
* Carbon
* Fluorine
* Chlorine
* Bromine
* Iodine
* Hydroxyl

Perhaps this should be an ENUM?

## Analyzing
This is the second package of the project. It works to take apart the molecule and determine what it is. 

There are a few stops the program must make before it can determine what the compound is.
1. What is the type of molecule? 
   * Alcohol, Aliphatic or, if I get to it, an ester or aromatic molecule
2. How do I name it?
   * This involves analyzing the structure and finding key components
   * If I have time, this is also where name simplification will occur

For part 1, I can simply look for a specific structure
* If there is a hydroxyl, it is an alcohol
* else, it's aliphatic
