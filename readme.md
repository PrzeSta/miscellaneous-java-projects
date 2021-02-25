# Miscellaneous java projects
---
This repository contains four projects that I have made for course of Java programming during my studies at University of Wrocław. They are small window applications,  and each took me one week to develop (at most) and is centred around one major topic provided by the lecturer (in form of a task with strict guidelines, which sometimes resulted in overcomplicated interfaces or solutions). There were more tasks, but decided that only this four are worthy of publishing as the most complex and, in my opinion, interesting. 

## Technology
---
Each project is written in Java, three of them use *Java AWT* and/or *Java Swing* while the fourth one (SemiCalculator) utilises *JavaFX* exclusively. 

## Content
---
+ **Maze** - simple game in which player moves as a small ***SPIDER*** in a labyrinth, trying to reach top left corner of the board from randomly chosen place in labyrinth. I used *Prim's Algorithm* to generate the maze and to be honest: this project was definitely most satisfying of them all and I "wasted" the first coding session on reading about types of maze generation in Computer Science. Controls: arrows for movement, R key for restart. 
+ **Peg Soltaire** - implementaion of [Peg solitaire](https://en.wikipedia.org/wiki/Peg_solitaire) with multiple ways to move pegs (by clicking on peg and empty field where player wants them to be moved, by right clicking on the peg and choosing destination from drop-down menu and by special option on the nav-bar) and ability to change colours of all pegs, selected pegs and board with *JColorChooser*. Includes both version of the game - English (32 pegs) and European (36 pegs). 
+ **Calendar** - implementation of Gregorian calendar (with "missing days" in October 1582 AD) and two types of view (tabs): yearly view (standard grid) and monthly view (three vertical lists - current month in the middle, following month on the right and preceding month on the left). Nav-bar on the bottom may look a little cumbersome thanks to the task guidelines which required all of those buttons, spinner and scrollbar to be into it. 
+ **SemiCalculator** - *JavaFX* (with *FXML*) implementation of integer-only big number calculator (using *BigInteger* class) with several computing operations (e.g. add, subtract, factorial, power, binomial coefficient etc.) Calculations can be carried out in binary, decimal and hexagonal systems and transition between them can be achieved at any point of operation. Per task guidelines application has been modelled as finite automaton.
