(ns advent-of-code-2018.day-06
  (:require [clojure.test]))

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

(defn input->coordinates
  {:test (fn []
           (clojure.test/is (= (input->coordinates "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9")
                               [[1 1] [1 6] [8 3] [3 4] [5 5] [8 9]])))}
  [input]
  (as-> input $
        (clojure.string/split-lines $)
        (map (fn [coordinate-string]
               (->> (clojure.string/split coordinate-string #", ")
                    (map read-string)))
             $)))

(defn find-corners
  {:test (fn []
           (clojure.test/is (= (find-corners (input->coordinates "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9"))
                               [[1 1] [8 9]])))}
  [coordinates]
  (let [x-coordinates (map first coordinates)
        y-coordinates (map second coordinates)]
    [[(apply min x-coordinates) (apply min y-coordinates)] [(apply max x-coordinates) (apply max y-coordinates)]]))

(defn get-manhattan-distance
  {:test (fn []
           (clojure.test/is (= (get-manhattan-distance [1 2] [3 4]) 4))
           (clojure.test/is (= (get-manhattan-distance [3 4] [1 2]) 4)))}
  [c1 c2]
  (+ (Math/abs (- (first c1) (first c2))) (Math/abs (- (second c1) (second c2)))))

(defn edgy?
  {:test (fn []
           (clojure.test/is (edgy? (find-corners (input->coordinates "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9"))
                                   [1 4]))
           (clojure.test/is (not (edgy? (find-corners (input->coordinates "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9"))
                                        [2 4]))))}
  [corners coordinate]
  (or (= (first coordinate) (get-in corners [0 0]))
      (= (first coordinate) (get-in corners [1 0]))
      (= (second coordinate) (get-in corners [1 0]))
      (= (second coordinate) (get-in corners [1 1]))))

(defn solve-a
  []
  (let [coordinates (input->coordinates input)
        corners (find-corners coordinates)]
    (apply max (vals (reduce (fn [result x-coordinate]
                               (reduce (fn [result y-coordinate]
                                         (let [[_ shortest-coordinate] (reduce (fn [[shortest-distance shortest-coordinate] coordinate]
                                                                                 (let [distance (get-manhattan-distance [x-coordinate y-coordinate] coordinate)]
                                                                                   (cond (= distance shortest-distance)
                                                                                         [shortest-distance nil]
                                                                                         (> shortest-distance distance)
                                                                                         [distance coordinate]
                                                                                         :else
                                                                                         [shortest-distance shortest-coordinate])))
                                                                               [1000000000 nil]
                                                                               coordinates)]
                                           (cond (nil? shortest-coordinate)
                                                 result
                                                 (edgy? corners [x-coordinate y-coordinate])
                                                 (assoc result shortest-coordinate -1000000000)
                                                 :else
                                                 (update result shortest-coordinate inc))))
                                       result
                                       (range (get-in corners [0 1]) (inc (get-in corners [1 1])))))
                             (reduce (fn [a v] (assoc a v 0)) {} coordinates)
                             (range (get-in corners [0 0]) (inc (get-in corners [1 0]))))))))

(comment
  (solve-a)
  ;; 4829
  )

;On the other hand, if the coordinates are safe, maybe the best you can do is try to find a region near as many coordinates as possible.
;
;For example, suppose you want the sum of the Manhattan distance to all of the coordinates to be less than 32. For each location, add up the distances to all of the given coordinates; if the total of those distances is less than 32, that location is within the desired region. Using the same coordinates as above, the resulting region looks like this:
;
;..........
;.A........
;..........
;...###..C.
;..#D###...
;..###E#...
;.B.###....
;..........
;..........
;........F.
;In particular, consider the highlighted location 4,3 located at the top middle of the region. Its calculation is as follows, where abs() is the absolute value function:
;
;Distance to coordinate A: abs(4-1) + abs(3-1) =  5
;Distance to coordinate B: abs(4-1) + abs(3-6) =  6
;Distance to coordinate C: abs(4-8) + abs(3-3) =  4
;Distance to coordinate D: abs(4-3) + abs(3-4) =  2
;Distance to coordinate E: abs(4-5) + abs(3-5) =  3
;Distance to coordinate F: abs(4-8) + abs(3-9) = 10
;Total distance: 5 + 6 + 4 + 2 + 3 + 10 = 30
;Because the total distance to all coordinates (30) is less than 32, the location is within the region.
;
;This region, which also includes coordinates D and E, has a total size of 16.
;
;Your actual region will need to be much larger than this example, though, instead including all locations with a total distance of less than 10000.
;
;What is the size of the region containing all locations which have a total distance to all given coordinates of less than 10000?

(defn solve-b
  []
  (let [coordinates (input->coordinates input)
        corners (find-corners coordinates)]
    (reduce (fn [size x-coordinate]
              (reduce (fn [size y-coordinate]
                        (let [total-distance (reduce (fn [total-distance coordinate]
                                                       (+ total-distance (get-manhattan-distance [x-coordinate y-coordinate] coordinate)))
                                                     0
                                                     coordinates)]
                          (if (< total-distance 10000)
                            (inc size)
                            size)))
                      size
                      (range (get-in corners [0 1]) (inc (get-in corners [1 1])))))
            0
            (range (get-in corners [0 0]) (inc (get-in corners [1 0]))))))

(comment
  (solve-b)
  ;; 46966
  )
