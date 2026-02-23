
# Basis Path Testing & Cyclomatic Complexity Practice

## Overview

**Basis Path Testing** is a white-box testing technique that derives a set of test cases 
based on the control flow graph (CFG) of a program. It ensures every independent path 
through the code is executed at least once.

**Cyclomatic Complexity (CC)** is a quantitative measure of the number of linearly 
independent paths through a program's source code. It determines:
- How many test cases are needed (minimum)
- How many basis paths exist
- Overall code complexity

## Formula: Cyclomatic Complexity

### Method 1: Control Flow Graph (CFG)
```
CC = E - N + 2P
```
Where:
- E = number of edges in CFG
- N = number of nodes in CFG
- P = number of connected components (usually 1)

### Method 2: Decision Points
```
CC = 1 + number of decision points (if, for, while, switch, catch, etc.)
```

### Method 3: Region Counting
```
CC = number of regions in CFG
```

## Steps for Basis Path Testing

1. **Draw the Control Flow Graph (CFG)**
   - Nodes represent statements/blocks
   - Edges represent control flow
   - Decision nodes (if, while) branch to multiple paths

2. **Calculate Cyclomatic Complexity**
   - Count decision points OR use formula above
   - CC = minimum number of test cases needed

3. **Identify Independent Paths**
   - Each path exercises a different combination of decisions
   - Linear combination of basis paths covers all code

4. **Create Test Cases**
   - One test case per independent path
   - Cover all branches and decisions

5. **Execute & Verify**
   - Ensure 100% statement coverage
   - Ensure 100% branch coverage

## Benefits

✓ Ensures comprehensive code coverage
✓ Identifies complex/risky code
✓ Prevents untested paths
✓ Improves software reliability
✓ White-box testing (code visibility)

## Limitations

✗ Doesn't test data dependencies (black-box missing)
✗ Complex for large functions
✗ Doesn't guarantee functional correctness
✗ Loop iterations not fully explored

------------------------------------------------------------------


## Analysis: dfsColor() Function - Solution One (DFS)


#	Decision Point	Line	Type
1	if (neighbor < 0 || neighbor >= graph.length)	54	Conditional
2	if (neighbor == node)	                        59	Conditional
3	if (color[neighbor] == 0)	                    65	Conditional
4	if (!dfsColor(...))	                            67	Conditional
5	else if (color[neighbor] == assignedColor)	    70	Conditional

CC = 1 + number of decision points
CC = 1 + 5
CC = 6



## Analysis: bfsColor() Function - Solution Two (BFS)

#	Decision Point	Type
1	while (!queue.isEmpty())	Loop
2	if (neighbor < 0 || neighbor >= graph.length)	Conditional
3	if (neighbor == node)	Conditional
4	if (color[neighbor] == 0)	Conditional
5	else if (color[neighbor] == nodeColor)	Conditional

CC = 1 + 5 = 6



## Independent Paths for dfsColor() 

### Path 1: Exception Case - Invalid Neighbor Index
Path: Decision 1 = True (neighbor out of bounds)

    Condition: neighbor < 0 || neighbor >= graph.length
    Action: Throw IndexOutOfBoundsException
    Expected: Exception thrown

### Path 2: Self-Loop Detection
Path: Decision 1 = False, Decision 2 = True (neighbor == node)

    Condition: neighbor == node (same as current node)
    Action: Return false
    Expected: Non-bipartite



### Path 3: Uncolored Neighbor - Recursive Success
Path: Decision 1 = False, Decision 2 = False, Decision 3 = True, Decision 4 = False (recursion succeeds)

    Condition: neighbor not validated, color[neighbor] == 0, recursive call returns true
    Action: Continue to next neighbor
    Expected: Continue checking other neighbors


### Path 4: Uncolored Neighbor - Recursive Failure
Path: Decision 1 = False, Decision 2 = False, Decision 3 = True, Decision 4 = True (recursion fails)

    Condition: neighbor not validated, color[neighbor] == 0, recursive call returns false
    Action: Return false immediately
    Expected: Non-bipartite

### Path 5: Already Colored - Conflict
Path: Decision 1 = False, Decision 2 = False, Decision 3 = False, Decision 5 = True (conflict)

    Condition: neighbor already colored with same color as current node
    Action: Return false
    Expected: Non-bipartite


### Path 6: Already Colored - No Conflict
Path: Decision 1 = False, Decision 2 = False, Decision 3 = False, Decision 5 = False (already colored correctly)

    Condition: neighbor already colored with opposite color
    Action: Continue to next neighbor
    Expected: Continue checking



## Run Basis Path Tests


    mvn clean test -Dtest=BasisPathTestingTest


## Check JaCoCo Report