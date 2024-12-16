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
Chemical names, as far as this program is concerned, is structured as follows (BNF format):

```
<LengthPrefix> ::= "meth" | "eth" | "prop" | ... | "dec"
<LocationNumPrefix> ::= "mono" | "di" | ... | "deca"
<AliphaticTypeSuffix> ::= "ane" | "ene" | "yne"
<cyclo> ::= "cyclo" | ""
<Location> ::= <number>
<LocationSet> ::= <Location> | <LocationSet>"," <Location>

<HalogenBranch> ::= "chloro" | "floro" | "bromo" | "iodo"
<BranchName> ::= <cyclo><LengthPrefix>"yl" | <HalogenBranch>
<RootName> ::= <cyclo><LengthPrefix><AliphaticTypeSuffix>

<Branch> ::= <LocationSet> "-" <BranchName>
<Root> ::= <RootName>
<Branches> ::= <Branch> | <Branch> "-" <Branches>
<Molecule> ::= <Root> | <Branches><Root>
``` 