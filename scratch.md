# Notes:

- ## Solver.java:
### Solve Methods
  1. brute-force with backtracking

    repeat for each free point
        then try for each point all digits
            if digits is unique in both rows and the square
                set the digit and continue with the next point
            else try a different value
        if no digit is valid
                backtrack to the last entered point 
                and try a different value

  2. solve for easy grids

    repeat 9x9 times
        repeat for each point
            if point can be only one value
            (
            can only one value :=
            point is unique in both rows and the square and
            isnt unique on any other free point in both rows and square
            )
                set this value
        if solved
            break
        else
            continue

  3. fast solve for all grids

    while not solved
        check if solved by false continue
        repeat for each point
            if point can be only one value
            (
            can only one value :=
            point is unique in both rows and the square and
            isnt unique on any other free point in both rows and square
            )
                set this value
        while no side effects exists
            side effects dont exists
            repeat for each point with remember possibility
                if on of the partner points is already set
                    set the remembered value
                    side effects exists
        repeat for each row and grid
            repeat for each digit
                count all unique points for the digit
                if count is 2 to 3 and all points are sharing the same x or y coord
                    remember this possibilities

  4. memory based solve 

    merke für jeden punkt alle möglichen (unique) values und dann setze immer da ein, wo nur eine möglich ist
    dann entferne diese eingesetzte value aus current grid und current rows
    und wiederhole

    
            
    
        
