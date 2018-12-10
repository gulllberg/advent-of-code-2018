(ns advent-of-code-2018.day-06)

(def input "353, 177\n233, 332\n178, 231\n351, 221\n309, 151\n105, 289\n91, 236\n321, 206\n156, 146\n94, 82\n81, 114\n182, 122\n81, 153\n319, 312\n334, 212\n275, 93\n224, 355\n347, 94\n209, 65\n118, 172\n113, 122\n182, 320\n191, 178\n99, 70\n260, 184\n266, 119\n177, 178\n313, 209\n61, 285\n155, 218\n354, 198\n274, 53\n225, 138\n228, 342\n187, 165\n226, 262\n143, 150\n124, 159\n325, 210\n163, 176\n326, 91\n170, 193\n84, 265\n199, 248\n107, 356\n45, 340\n277, 173\n286, 44\n242, 150\n120, 230")


;The device on your wrist beeps several times, and once again you feel like you're falling.
;
;"Situation critical," the device announces. "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."
;
;The device then produces a list of coordinates (your puzzle input). Are they places it thinks are safe or dangerous? It recommends you check manual page 729. The Elves did not give you a manual.
;
;If they're dangerous, maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.
;
;Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer X,Y locations that are closest to that coordinate (and aren't tied in distance to any other coordinate).
;
;Your goal is to find the size of the largest area that isn't infinite. For example, consider the following list of coordinates:
;
;1, 1
;1, 6
;8, 3
;3, 4
;5, 5
;8, 9
;If we name these coordinates A through F, we can draw them on a grid, putting 0,0 at the top left:
;
;..........
;.A........
;..........
;........C.
;...D......
;.....E....
;.B........
;..........
;..........
;........F.
;This view is partial - the actual grid extends infinitely in all directions. Using the Manhattan distance, each location's closest coordinate can be determined, shown here in lowercase:
;
;aaaaa.cccc
;aAaaa.cccc
;aaaddecccc
;aadddeccCc
;..dDdeeccc
;bb.deEeecc
;bBb.eeee..
;bbb.eeefff
;bbb.eeffff
;bbb.ffffFf
;Locations shown as . are equally far from two or more coordinates, and so they don't count as being closest to any.
;
;In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here, their areas extend forever outside the visible grid. However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17 (both including the coordinate's location itself). Therefore, in this example, the size of the largest area is 17.
;
;What is the size of the largest area that isn't infinite?

