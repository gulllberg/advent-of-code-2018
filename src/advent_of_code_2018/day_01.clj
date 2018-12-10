(ns advent-of-code-2018.day-01)

;After feeling like you've been falling for a few minutes, you look at the device's tiny screen. "Error: Device must be calibrated before first use. Frequency drift detected. Cannot maintain destination lock." Below the message, the device shows a sequence of changes in frequency (your puzzle input). A value like +6 means the current frequency increases by 6; a value like -3 means the current frequency decreases by 3.
;
;For example, if the device displays frequency changes of +1, -2, +3, +1, then starting from a frequency of zero, the following changes would occur:
;
;Current frequency  0, change of +1; resulting frequency  1.
;Current frequency  1, change of -2; resulting frequency -1.
;Current frequency -1, change of +3; resulting frequency  2.
;Current frequency  2, change of +1; resulting frequency  3.
;In this example, the resulting frequency is 3.
;
;Here are other example situations:
;
;+1, +1, +1 results in  3
;+1, +1, -2 results in  0
;-1, -2, -3 results in -6
;Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have been applied?

(def input "+7\n-5\n-9\n-1\n-3\n+7\n-8\n-17\n-4\n-18\n-15\n+18\n-11\n-3\n-15\n+6\n+17\n-3\n+19\n+6\n+4\n-15\n+18\n-14\n+8\n-11\n-7\n-15\n+19\n-1\n+10\n+18\n-1\n+5\n-12\n+11\n-5\n+17\n+9\n-12\n+7\n+19\n-9\n+17\n+12\n-15\n-12\n+11\n+14\n-16\n-13\n+7\n+5\n-6\n+20\n-1\n+8\n-13\n-6\n-9\n+4\n+23\n-5\n-5\n-19\n+20\n-6\n+21\n-4\n+12\n+10\n-1\n+16\n+15\n-12\n-17\n-3\n+9\n-19\n-3\n+7\n-5\n-7\n-8\n+2\n+7\n+16\n-2\n+13\n+13\n+20\n-15\n-10\n-5\n-1\n+7\n-15\n+18\n+3\n-1\n+16\n-13\n-1\n-10\n+15\n-17\n-18\n-11\n+1\n+2\n+7\n+12\n-14\n+22\n+14\n+5\n-13\n-16\n+25\n+20\n+2\n+10\n+16\n-19\n-1\n-7\n+3\n+3\n+10\n+4\n+16\n+12\n+8\n+7\n-12\n-1\n-7\n+19\n-4\n+11\n+18\n-15\n+12\n+11\n-10\n-7\n+12\n-16\n+2\n-5\n-5\n+14\n+9\n+14\n+19\n+11\n+6\n+13\n+14\n+14\n-6\n-9\n-9\n-11\n-5\n-5\n+15\n-7\n+1\n-17\n+7\n-15\n-7\n+12\n-6\n-13\n-19\n+10\n+5\n-1\n+6\n+20\n-12\n-7\n+18\n-17\n-15\n-11\n+16\n+12\n+13\n-21\n+9\n+9\n+11\n-2\n-13\n-1\n-6\n+4\n-16\n+2\n-5\n+23\n-13\n-12\n-19\n+2\n+2\n-12\n-6\n+1\n-21\n-16\n-18\n+5\n+18\n+4\n-11\n+17\n+3\n+6\n-1\n-4\n-18\n+2\n-4\n-3\n-15\n-6\n-1\n-13\n+10\n+15\n+6\n+20\n+18\n-9\n+11\n-3\n-18\n-16\n-1\n+20\n-7\n+19\n-24\n+20\n+12\n+22\n-15\n+24\n-21\n+3\n+13\n-15\n+23\n+37\n+9\n+18\n+3\n-11\n+20\n+4\n+15\n+14\n-5\n+7\n-6\n+9\n-2\n-9\n+12\n+11\n-18\n+8\n-15\n+16\n-7\n+11\n+11\n+1\n-8\n+6\n-8\n+18\n-21\n+9\n-7\n+12\n+4\n+13\n+15\n+5\n-10\n-4\n+10\n-3\n+24\n-14\n+10\n+7\n-11\n-20\n+19\n+6\n+7\n+12\n+1\n+1\n-7\n+1\n+20\n+25\n-14\n-20\n-15\n-19\n-23\n-14\n+9\n-30\n-13\n-13\n-20\n+11\n+15\n-3\n-9\n+13\n-12\n-10\n-9\n+2\n+19\n-8\n+3\n-6\n+18\n-5\n+15\n-16\n-10\n-15\n-6\n-5\n-10\n+18\n-16\n+25\n-15\n-18\n+12\n+19\n-12\n+4\n+6\n+9\n-27\n-60\n-48\n-4\n+2\n-17\n-12\n+11\n-18\n-9\n+5\n-6\n-28\n+3\n-13\n+35\n+5\n+25\n-22\n-35\n-15\n-27\n-10\n-9\n-64\n-9\n+10\n-3\n-25\n+9\n-14\n-83\n+11\n+61\n-8\n-8\n+12\n+50\n-20\n-165\n+7\n-65\n-3\n-5\n+281\n+56\n+121\n-55464\n+8\n-9\n-15\n-3\n-16\n+10\n-11\n+9\n+5\n+4\n+7\n-20\n-15\n-1\n-10\n-11\n+12\n-11\n+16\n+21\n-3\n+4\n+1\n-9\n-2\n-6\n-7\n+9\n-11\n-1\n-4\n+15\n-4\n-13\n-1\n-1\n+10\n-17\n-15\n-17\n+11\n+9\n+3\n-9\n+13\n+2\n-3\n-17\n+19\n-16\n-4\n+8\n-3\n+5\n-12\n+16\n+8\n-15\n-19\n-8\n-3\n-13\n-5\n-16\n+3\n+2\n+10\n-5\n+2\n+14\n+10\n+8\n-11\n+2\n-6\n+9\n+8\n+7\n+6\n-3\n-6\n-11\n+12\n-23\n-11\n+13\n+22\n+18\n+21\n+18\n+19\n+6\n-3\n+5\n+17\n+7\n-4\n+13\n-5\n+13\n+18\n+10\n-7\n+12\n+11\n-10\n-7\n+14\n+6\n-16\n+20\n+16\n-1\n+6\n-14\n+15\n-2\n-25\n+13\n+39\n-11\n-9\n+34\n-60\n-14\n-22\n-27\n-12\n-10\n+3\n+20\n+3\n+8\n+7\n-10\n-14\n+12\n+9\n-6\n+8\n-19\n-10\n-16\n-14\n+16\n-4\n-20\n+10\n-17\n-9\n-15\n+16\n+11\n-20\n-11\n-14\n-28\n-16\n-2\n+21\n-18\n+17\n+5\n-6\n-18\n-15\n-14\n+15\n+11\n-16\n-5\n+19\n-12\n-15\n-1\n+11\n-16\n+11\n+15\n-19\n+3\n-9\n-2\n-9\n+1\n-5\n+7\n+11\n+24\n+14\n+4\n+17\n-46\n-5\n-17\n-8\n-10\n-12\n-5\n+10\n+3\n+9\n+14\n+7\n-6\n-16\n+17\n-18\n+14\n-10\n+18\n-17\n-11\n-17\n-12\n-15\n-2\n-4\n-9\n-13\n+10\n+4\n-3\n-9\n-5\n+7\n+11\n-17\n-21\n+9\n+17\n+17\n+13\n+12\n-3\n-3\n+18\n-5\n+12\n+10\n+3\n+10\n+24\n+21\n+13\n+22\n+19\n+72\n+7\n+11\n-15\n-5\n+6\n+83\n+8\n-153\n+24\n+8\n+39\n+43\n-265\n-17\n+18\n-4\n-12\n+7\n-4\n-2\n+18\n+12\n+11\n-7\n-9\n-4\n+10\n+15\n+25\n-20\n-16\n-22\n-21\n-11\n+2\n+13\n+20\n+10\n+12\n-6\n-23\n+3\n-17\n-7\n+15\n-19\n-10\n-8\n-2\n-8\n-2\n-6\n+5\n+7\n-5\n-3\n-3\n-15\n+4\n+5\n+15\n-12\n-16\n-13\n+15\n-4\n-9\n+12\n-8\n+2\n+19\n-4\n+1\n-23\n-9\n-10\n-2\n+5\n-10\n+2\n-3\n-10\n-15\n+19\n-7\n-9\n-17\n+10\n-18\n+16\n-14\n+7\n-10\n+12\n+3\n-7\n+12\n+21\n-5\n-11\n-7\n+3\n+11\n-16\n+15\n+14\n+17\n+8\n-2\n-1\n+19\n+10\n+11\n+22\n-1\n+18\n+12\n-19\n-5\n+17\n-4\n-4\n+19\n-46\n-22\n-11\n+19\n-12\n-19\n-11\n-17\n-15\n-42\n-11\n-4\n+20\n+6\n-38\n+8\n-58\n+5\n-11\n-60\n-7\n-61\n-5\n+36\n-82\n-3\n-35\n-11\n+33\n-51\n-17\n+9\n-277\n+251\n-55001\n+3\n-11\n+1\n+13\n+3\n-15\n+1\n+2\n+7\n-16\n+13\n+9\n+18\n-1\n-10\n+5\n+17\n-9\n+2\n+10\n+18\n+19\n+3\n-9\n-18\n-4\n+6\n-13\n+6\n+19\n+15\n+14\n-13\n+17\n+14\n-3\n-7\n-16\n-9\n+16\n-5\n-12\n+19\n+16\n-15\n+12\n-5\n+7\n-20\n-6\n-1\n+6\n-7\n+17\n+22\n+7\n-13\n+8\n+16\n+14\n-18\n+12\n-4\n-1\n+10\n-15\n+14\n+11\n+9\n-14\n-3\n+10\n-8\n-8\n-16\n+18\n-4\n+17\n-3\n-22\n-18\n-20\n+19\n-6\n+14\n-1\n+4\n+15\n-4\n+24\n-31\n-10\n+7\n-24\n-1\n+8\n+4\n-21\n+8\n-4\n-16\n+2\n+4\n-18\n+6\n-5\n-19\n-6\n-5\n-13\n+17\n+15\n-5\n+4\n+2\n-19\n+12\n-16\n-8\n-11\n+3\n+14\n-5\n+8\n+17\n-5\n-11\n+23\n+2\n-18\n-30\n-11\n+5\n+15\n-14\n-17\n+13\n+19\n+2\n-15\n-7\n-18\n-14\n-3\n-2\n-6\n-12\n+1\n+20\n+5\n+2\n-3\n-20\n-3\n+9\n-15\n-4\n-6\n+2\n+3\n+19\n+17\n-3\n-18\n-17\n-20\n+14\n+2\n-18\n+13\n+13\n-21\n-13\n-4\n-18\n+11\n+1\n+3\n+10\n-15\n-4\n-7\n-2\n-16\n-11\n-10\n-5\n+6\n+1\n-12\n-8\n-5\n-10\n+1\n+5\n+15\n-14\n-5\n-3\n-14\n+19\n+111653")

;; TODO: sub not necessary
(defn solve-a []
  (reduce (fn [result instruction]
            (let [operator (resolve (read-string (subs instruction 0 1)))
                  number (read-string (subs instruction 1))]
              (operator result number)))
          0
          (clojure.string/split-lines input)))

(comment
  (solve-a)
  ;; 406
  )

;You notice that the device repeats the same frequency change list over and over. To calibrate the device, you need to find the first frequency it reaches twice.
;
;For example, using the same list of changes above, the device would loop as follows:
;
;Current frequency  0, change of +1; resulting frequency  1.
;Current frequency  1, change of -2; resulting frequency -1.
;Current frequency -1, change of +3; resulting frequency  2.
;Current frequency  2, change of +1; resulting frequency  3.
;(At this point, the device continues from the start of the list.)
;Current frequency  3, change of +1; resulting frequency  4.
;Current frequency  4, change of -2; resulting frequency  2, which has already been seen.
;In this example, the first frequency reached twice is 2. Note that your device might need to repeat its list of frequency changes many times before a duplicate frequency is found, and that duplicates might be found while in the middle of processing the list.
;
;Here are other examples:
;
;+1, -1 first reaches 0 twice.
;+3, +3, +4, -2, -4 first reaches 10 twice.
;-6, +3, +8, +5, -6 first reaches 5 twice.
;+7, +7, -2, -7, -4 first reaches 14 twice.
;What is the first frequency your device reaches twice?

(defn solve-b []
  (reduce (fn [[last-frequency previous-frequencies] instruction]
            (let [next-frequency (+ last-frequency (read-string instruction))]
              (if (contains? previous-frequencies next-frequency)
                (reduced next-frequency)
                [next-frequency (conj previous-frequencies next-frequency)])))
          [0 #{}]
          (cycle (clojure.string/split-lines input))))

(comment
  (solve-b)
  ;; 312
  )
