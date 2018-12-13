(ns advent-of-code-2018.day-07
  (:require [clojure.test]
            [clojure.set]))

(def input "Step S must be finished before step G can begin.\nStep E must be finished before step T can begin.\nStep G must be finished before step A can begin.\nStep P must be finished before step Z can begin.\nStep L must be finished before step Z can begin.\nStep F must be finished before step H can begin.\nStep D must be finished before step Y can begin.\nStep J must be finished before step Y can begin.\nStep N must be finished before step O can begin.\nStep R must be finished before step Y can begin.\nStep Y must be finished before step W can begin.\nStep U must be finished before step T can begin.\nStep H must be finished before step W can begin.\nStep T must be finished before step Z can begin.\nStep Q must be finished before step B can begin.\nStep O must be finished before step Z can begin.\nStep K must be finished before step W can begin.\nStep M must be finished before step C can begin.\nStep A must be finished before step Z can begin.\nStep C must be finished before step X can begin.\nStep I must be finished before step V can begin.\nStep V must be finished before step W can begin.\nStep W must be finished before step X can begin.\nStep Z must be finished before step B can begin.\nStep X must be finished before step B can begin.\nStep D must be finished before step M can begin.\nStep S must be finished before step Z can begin.\nStep A must be finished before step B can begin.\nStep V must be finished before step Z can begin.\nStep Q must be finished before step Z can begin.\nStep O must be finished before step W can begin.\nStep S must be finished before step E can begin.\nStep L must be finished before step B can begin.\nStep P must be finished before step Y can begin.\nStep K must be finished before step M can begin.\nStep W must be finished before step Z can begin.\nStep Y must be finished before step Q can begin.\nStep J must be finished before step M can begin.\nStep U must be finished before step H can begin.\nStep Y must be finished before step U can begin.\nStep D must be finished before step A can begin.\nStep C must be finished before step V can begin.\nStep G must be finished before step J can begin.\nStep O must be finished before step C can begin.\nStep P must be finished before step H can begin.\nStep M must be finished before step B can begin.\nStep T must be finished before step C can begin.\nStep A must be finished before step W can begin.\nStep C must be finished before step B can begin.\nStep Q must be finished before step I can begin.\nStep O must be finished before step A can begin.\nStep N must be finished before step H can begin.\nStep Q must be finished before step C can begin.\nStep G must be finished before step W can begin.\nStep V must be finished before step X can begin.\nStep A must be finished before step V can begin.\nStep S must be finished before step C can begin.\nStep O must be finished before step M can begin.\nStep E must be finished before step L can begin.\nStep D must be finished before step V can begin.\nStep P must be finished before step N can begin.\nStep O must be finished before step I can begin.\nStep P must be finished before step K can begin.\nStep N must be finished before step A can begin.\nStep A must be finished before step X can begin.\nStep L must be finished before step A can begin.\nStep L must be finished before step T can begin.\nStep I must be finished before step X can begin.\nStep N must be finished before step C can begin.\nStep N must be finished before step W can begin.\nStep Y must be finished before step M can begin.\nStep R must be finished before step A can begin.\nStep O must be finished before step X can begin.\nStep G must be finished before step T can begin.\nStep S must be finished before step P can begin.\nStep E must be finished before step M can begin.\nStep E must be finished before step A can begin.\nStep E must be finished before step W can begin.\nStep F must be finished before step D can begin.\nStep U must be finished before step C can begin.\nStep R must be finished before step Z can begin.\nStep A must be finished before step C can begin.\nStep F must be finished before step K can begin.\nStep L must be finished before step V can begin.\nStep F must be finished before step T can begin.\nStep W must be finished before step B can begin.\nStep Y must be finished before step A can begin.\nStep D must be finished before step T can begin.\nStep S must be finished before step V can begin.\nStep Y must be finished before step O can begin.\nStep K must be finished before step B can begin.\nStep N must be finished before step V can begin.\nStep Y must be finished before step I can begin.\nStep Z must be finished before step X can begin.\nStep E must be finished before step B can begin.\nStep P must be finished before step O can begin.\nStep D must be finished before step R can begin.\nStep Q must be finished before step X can begin.\nStep E must be finished before step K can begin.\nStep J must be finished before step R can begin.\nStep L must be finished before step N can begin.")

(def test-input "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin.")

;You find yourself standing on a snow-covered coastline; apparently, you landed a little off course. The region is too hilly to see the North Pole from here, but you do spot some Elves that seem to be trying to unpack something that washed ashore. It's quite cold out, so you decide to risk creating a paradox by asking them for directions.
;
;"Oh, are you the search party?" Somehow, you can understand whatever Elves from the year 1018 speak; you assume it's Ancient Nordic Elvish. Could the device on your wrist also be a translator? "Those clothes don't look very warm; take this." They hand you a heavy coat.
;
;"We do need to find our way back to the North Pole, but we have higher priorities at the moment. You see, believe it or not, this box contains something that will solve all of Santa's transportation problems - at least, that's what it looks like from the pictures in the instructions." It doesn't seem like they can read whatever language it's in, but you can: "Sleigh kit. Some assembly required."
;
;"'Sleigh'? What a wonderful name! You must help us assemble this 'sleigh' at once!" They start excitedly pulling more parts out of the box.
;
;The instructions specify a series of steps and requirements about which steps must be finished before others can begin (your puzzle input). Each step is designated by a single letter. For example, suppose you have the following instructions:
;
;Step C must be finished before step A can begin.
;Step C must be finished before step F can begin.
;Step A must be finished before step B can begin.
;Step A must be finished before step D can begin.
;Step B must be finished before step E can begin.
;Step D must be finished before step E can begin.
;Step F must be finished before step E can begin.
;Visually, these requirements look like this:
;
;
;-->A--->B--
;/    \      \
;C      -->D----->E
;\           /
;---->F-----
;Your first goal is to determine the order in which the steps should be completed. If more than one step is ready, choose the step which is first alphabetically. In this example, the steps would be completed as follows:
;
;Only C is available, and so it is done first.
;Next, both A and F are available. A is first alphabetically, so it is done next.
;Then, even though F was available earlier, steps B and D are now also available, and B is the first alphabetically of the three.
;After that, only D and F are available. E is not available because only some of its prerequisites are complete. Therefore, D is completed next.
;F is the only choice, so it is done next.
;Finally, E is completed.
;So, in this example, the correct order is CABDFE.
;
;In what order should the steps in your instructions be completed?

(defn parse-line
  {:test (fn []
           (clojure.test/is (= (parse-line "Step S must be finished before step G can begin.") ["S" "G"])))}
  [line]
  (let [words (clojure.string/split line #" ")]
    [(second words) (nth words 7)]))

(defn get-dependencies
  {:test (fn []
           (clojure.test/is (= (get-dependencies test-input)
                               {"C" #{}
                                "A" #{"C"}
                                "F" #{"C"}
                                "B" #{"A"}
                                "D" #{"A"}
                                "E" #{"B" "D" "F"}})))}
  [input]
  (reduce (fn [dependencies line]
            (let [[enabler depender] (parse-line line)]
              (-> dependencies
                  (update depender (fn [deps]
                                     (if (nil? deps)
                                       #{enabler}
                                       (conj deps enabler))))
                  (update enabler (fn [deps]
                                    (if (nil? deps)
                                      #{}
                                      deps))))))
          {}
          (clojure.string/split-lines input)))

(defn get-next-job
  {:test (fn []
           (clojure.test/is (= (get-next-job (get-dependencies test-input))
                               "C")))}
  [dependencies]
  (->> dependencies
       (keys)
       (sort)
       (some (fn [job]
               (when (= (get dependencies job) #{})
                 job)))))

(defn update-dependencies
  {:test (fn []
           (clojure.test/is (= (update-dependencies (get-dependencies test-input) "C")
                               {"A" #{}
                                "F" #{}
                                "B" #{"A"}
                                "D" #{"A"}
                                "E" #{"B" "D" "F"}})))}
  [dependencies job]
  (let [dependencies (dissoc dependencies job)]
    (reduce (fn [dependencies key]
              (update dependencies key disj job))
            dependencies
            (keys dependencies))))

(defn solve-a
  {:test (fn []
           (clojure.test/is (= (solve-a test-input)
                               "CABDFE")))}
  [input]
  (let [dependencies (get-dependencies input)]
    (first (reduce (fn [[order dependencies] _]
                     (let [next-job (get-next-job dependencies)]
                       [(str order next-job) (update-dependencies dependencies next-job)]))
                   ["" dependencies]
                   (range (count (keys dependencies)))))))

(comment
  (solve-a input)
  ;; FDSEGJLPKNRYOAMQIUHTCVWZXB
  )

;As you're about to begin construction, four of the Elves offer to help. "The sun will set soon; it'll go faster if we work together." Now, you need to account for multiple people working on steps simultaneously. If multiple steps are available, workers should still begin them in alphabetical order.
;
;Each step takes 60 seconds plus an amount corresponding to its letter: A=1, B=2, C=3, and so on. So, step A takes 60+1=61 seconds, while step Z takes 60+26=86 seconds. No time is required between steps.
;
;To simplify things for the example, however, suppose you only have help from one Elf (a total of two workers) and that each step takes 60 fewer seconds (so that step A takes 1 second and step Z takes 26 seconds). Then, using the same instructions as above, this is how each second would be spent:
;
;Second   Worker 1   Worker 2   Done
;0        C          .
;1        C          .
;2        C          .
;3        A          F       C
;4        B          F       CA
;5        B          F       CA
;6        D          F       CAB
;7        D          F       CAB
;8        D          F       CAB
;9        D          .       CABF
;10        E          .       CABFD
;11        E          .       CABFD
;12        E          .       CABFD
;13        E          .       CABFD
;14        E          .       CABFD
;15        .          .       CABFDE
;Each row represents one second of time. The Second column identifies how many seconds have passed as of the beginning of that second. Each worker column shows the step that worker is currently doing (or . if they are idle). The Done column shows completed steps.
;
;Note that the order of the steps has changed; this is because steps now take time to finish and multiple workers can begin multiple steps simultaneously.
;
;In this example, it would take 15 seconds for two workers to complete these steps.
;
;With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the steps?


