(ns advent-of-code-2018.day-02
  (:require [advent-of-code-2018.core :as core]))

;You stop falling through time, catch your breath, and check the screen on the device. "Destination reached. Current Year: 1518. Current Location: North Pole Utility Closet 83N10." You made it! Now, to find those anomalies.
;
;Outside the utility closet, you hear footsteps and a voice. "...I'm not sure either. But now that so many people have chimneys, maybe he could sneak in that way?" Another voice responds, "Actually, we've been working on a new kind of suit that would let him fit through tight spaces like that. But, I heard that a few days ago, they lost the prototype fabric, the design plans, everything! Nobody on the team can even seem to remember important details of the project!"
;
;"Wouldn't they have had enough fabric to fill several boxes in the warehouse? They'd be stored together, so the box IDs should be similar. Too bad it would take forever to search the warehouse for two similar box IDs..." They walk too far away to hear any more.
;
;Late at night, you sneak to the warehouse - who knows what kinds of paradoxes you could cause if you were discovered - and use your fancy wrist device to quickly scan every box and produce a list of the likely candidates (your puzzle input).
;
;To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID containing exactly two of any letter and then separately counting those with exactly three of any letter. You can multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.
;
;For example, if you see the following box IDs:
;
;abcdef contains no letters that appear exactly two or three times.
;bababc contains two a and three b, so it counts for both.
;abbcde contains two b, but no letter appears exactly three times.
;abcccd contains three c, but no letter appears exactly two times.
;aabcdd contains two a and two d, but it only counts once.
;abcdee contains two e.
;ababab contains three a and three b, but it only counts once.
;Of these box IDs, four of them contain a letter which appears exactly twice, and three of them contain a letter which appears exactly three times. Multiplying these together produces a checksum of 4 * 3 = 12.
;
;What is the checksum for your list of box IDs?

(def input "bazvmqthjtrnlosiecxyghkwud\npazvmqbijirzlosiecxyghkwud\npazvtqbmjtrnlosiecxyghkwzd\npazvmqbfjtrjlosnlcxyghkwud\npazvkqbfjtrtlosiecjyghkwud\npaztmqbfjtrnbosiecxyglkwud\npazvmqbfjtunlosievxmghkwud\npazvmqbfjtmngosiecyyghkwud\njazvmqbfjtrnlosiecxygikpud\npazvqqbfctrnlosimcxyghkwud\npazvmqbfjtrnwogiecxyihkwud\npazvmqbfjtrqlojiecxeghkwud\npayvmqbfjtrzlosiecxyghkwuk\npkzvmqnfjtrnlosieciyghkwud\npazvmqqfjtrnldsiecxyghkwui\npazvmqbfttrqlosiecxywhkwud\ngazvmybfjthnlosiecxyghkwud\npazvmqbfjtrnlasiecxygptwud\npktvmqbfjtrnwosiecxyghkwud\npazvmqwfjtrnlosiecxgghkkud\npazvmzkbjtrnlosiecxyghkwud\npazvmqbfjtrnloslecxyghuwui\npezvmqbfjtrnlesieyxyghkwud\ncazvmqbfjrrnlosiecxyghkmud\npazvmqrfjjrnlosiecxyghkwnd\npazvmqbgjtrnlosiecxyphtwud\npazvmqbvmtrnlosiecxyghkpud\npazdmqbfjtrnlosiecxyuhkpud\npazvmqbflnrnloshecxyghkwud\npazvvqbfjprilosiecxyghkwud\npazvwqbfjtrllosiecxyghknud\npazvmqbfjtrnloniecxdghkaud\npazvmqbfjtrnlvsuecxynhkwud\nptzvmqwfjtrnlosieccyghkwud\npkzvmqbjjtrnlosiecryghkwud\npazvmqqfjtrexosiecxyghkwud\npazgmqbfjtrneoyiecxyghkwud\npaznmqbfjtrnlosiecxydhkwzd\npazvmqbfjtrnaosiwcxsghkwud\npazomqbfjxrnlosiewxyghkwud\npazsmqbfjprnlosiecxrghkwud\npazvmqbfqtrnoosiecxygmkwud\naazvmqbfjtrnlosiacxyghjwud\npazviqbfjtrnlobiecxygrkwud\nqazwmqbfjhrnlosiecxyghkwud\npazvmqbfftrnlosiqcxygfkwud\npatvmqbfjtonlosircxyghkwud\npazvmqbfjtrnlomaecxyghkpud\npaztmqbfjtrulossecxyghkwud\npazvmqbijtrnlobiecxyghkwkd\npazvsqbfjtrnlospecxyghkqud\npbzmmqbfjtrnlosiecxyghkwhd\npezvkqbfjtenlosiecxyghkwud\nrazvmqbfjkrnlosiecxeghkwud\npazcmqbfjtrnloriecxyghkgud\npazvmqbfftfnlosiecvyghkwud\npazvmqpujtrnlosiepxyghkwud\npatvgqbfjtrnloslecxyghkwud\npazvmqbfltrnlosibcxyghswud\npazvmebfjtrnlosaecxyehkwud\npazdmqbejtrnlosiecxyghrwud\npazvmcbfntrplosiecxyghkwud\npszvmqbfjtrnlosivcfyghkwud\npuzvmqbfjtrnloeiecxyxhkwud\npazvmqbfjtrivooiecxyghkwud\npazvyqbfjtrngosiwcxyghkwud\npauvmqbfjtrnlosimexyghkwud\npazvmqbfjtrnwoshecxeghkwud\ndazvmqbfjtrnloshecxygxkwud\npazvmqbfjtrtdosiecxyghvwud\npazxmqbfjtrnlosieceyghjwud\npazvmqbfjtrnlosihexjghkwud\npazvmqbfjsrnlosiecxughiwud\nphzvcqbfjtrqlosiecxyghkwud\npazvmibfjtrnlosjecxxghkwud\npazvmqbfjtrbeosiecxlghkwud\npazvmqyfjttolosiecxyghkwud\nfawvmqbfjtrnlosiecxyghkwhd\npazvmqbfjprnxosiecxyghkbud\nmacvmqbfjtrnlosiesxyghkwud\npazsmqbfjtrflouiecxyghkwud\npacvmqbfjtrnltsiecxyghcwud\npazvmqbfjtymlosiecxygykwud\npazvmqbfjtrclosiecxygukwmd\npazvmqbfjtrnlobiecxphhkwud\nmazvmqbhitrnlosiecxyghkwud\npazvmqdtjtrnlrsiecxyghkwud\npazvmqbfjgrnllsieczyghkwud\npazvmqbfjtrilosiecxxgikwud\npazvmqbjjtrnlosreceyghkwud\npaxvmmbfjtrilosiecxyghkwud\npazqmwbfjtrnlowiecxyghkwud\npazvmqbfjfrnqosiecxyghkwui\npazvmqbfjtrrgosiecxyghswud\npazvmqnfjtrnlosiecsyghkwmd\npaiemqbmjtrnlosiecxyghkwud\npazvmqbfdtqnlosiecxyjhkwud\npazvmxbfjthndosiecxyghkwud\npqzvmqbfjtrnlosiecxbghkzud\npagrmqbfjtrnlosiecxygskwud\npazamqtfjtrnsosiecxyghkwud\npazvmqbfjtrnldshecxyzhkwud\npazvmnbfjtrllosieclyghkwud\nsnzvmqbfjnrnlosiecxyghkwud\npazvsqbfjdrnlosiecxyghswud\npazvmqnfjfrnlosiecsyghkwud\npazvmqbfjtrnlosiecxjghowum\npazvmqbfjtjnlosieczygfkwud\npazvmqbsjtrnloziecxyghkeud\npazvxqbgjtrnlooiecxyghkwud\npazvmqbfjtrnlooiecxmyhkwud\npazvmqbyftrnlosgecxyghkwud\npazvmqbfjtrnlosiwcxyqhksud\npazvmqkyjtrnlokiecxyghkwud\npazfmqbfjtrnlosijcxyohkwud\npazvmqbfjtrnlociecxygikcud\nfazvmqbfjtrnlosiecxyjhkuud\npazvmqbojtknlohiecxyghkwud\npazvmqbfjtgnlosbecxyghkwux\npazvmqbfjtrnlocieckoghkwud\npazvdqbfjtrlltsiecxyghkwud\npazvmqbfjtsnlfsiecxyglkwud\nprzvpqbfjtrnyosiecxyghkwud\npazvmbrfjtrnlosiecxmghkwud\ndazvmqbfttrnlostecxyghkwud\npazvmqbfttdnlosiecxygwkwud\npazvmqbvitrnlosieexyghkwud\npazvmqbfjhrnlosjecxyvhkwud\npazvmqbfstrnlosiecxyggkwpd\nbazvmqbfjtrnlmsiecxyohkwud\npatmmqbfjtrnlosizcxyghkwud\npazvmqbfwtrglosieqxyghkwud\npazvmqbfjtrnlosiecxdhhkwmd\npazvmqbfjdrnlosnexxyghkwud\noazrrqbfjtrnlosiecxyghkwud\npazvmqbfjcrnlosiecxygakwjd\npazvmqbfjtrnlosifcxfghkwyd\npazvmnbfjtrnlosiecxyahzwud\npazvmqbfgtrnlojiecxyghkgud\npazvmqbfjtrnlaliecxyghkwuy\npazvmqbfjtrnlfsiecrtghkwud\npazvmqbkjtrnloswecxdghkwud\npazvtqbfjtdnlosiecxyghkwuu\npozvmqbfrtrnlosiesxyghkwud\npayvmqbfjornlossecxyghkwud\npazvuqbfjtrnlosiscxyghkpud\npgzcmqbfjtrnlotiecxyghkwud\npazvvqbfjtrnlobieyxyghkwud\npazycqbfjtrnlosiecxyzhkwud\npizvdqbfjtrnlosiecxbghkwud\npazvmqbfjtrnloqiecxmgtkwud\ngazvmqbfjtrnlusiecxpghkwud\npazvmqdfjtralosiecxyghkwmd\npazvmqbfjtmnlosiecxywhawud\npazvlqbfjtrnlosqecxyghqwud\npazvmqbfjtrnlhsneixyghkwud\nkazvmqbfjtrqlosimcxyghkwud\npazvmwbfjtrclosiecxyghkuud\npazvmqjfjtrnlosieckyghpwud\npezvmqbgjtrnloseecxyghkwud\npazvqqbfjtfnlosvecxyghkwud\noazvmqbfjtunlosiecxyghkwad\npazvmqbfjtrncoswecxyghfwud\npazvyqbfjtrnlosqecxygtkwud\npazvmqbfjtrvlzsiecxygwkwud\npazvmqbfjjrnlosiekxylhkwud\nmadvmqbfjtrnlosircxyghkwud\npazvmybfjtrnlisiecxyghkwbd\npazvmqbjjixnlosiecxyghkwud\npazvmqefjtrnloqiecxyghhwud\npazveqbfjtrnlosiecgygzkwud\npazvmqbfjtrxlosiecxmgwkwud\nuazvmqufjtrnlosiecxyghkwuo\npasymqbfjtrnlosiecxyghowud\npazvmqbfjtlnlpsiecxyghswud\npnzvmqbfjprnloszecxyghkwud\npafjmqcfjtrnlosiecxyghkwud\npazvmqxfbtrnloqiecxyghkwud\npazvmzbfjtrnposiccxyghkwud\npazvmqbfjotulosiecxyghkwud\npazvmqbfotrnlosgecxykhkwud\nprzvmqbfjtrnlosiecxyqhkwcd\npazvmqbfjtsnlogiecxyyhkwud\npazvmqbfrtrnlzsiecxyghkwug\npazvmqbfjtrnlosiecxzgukwuo\npqzvmqbqjtrnlosdecxyghkwud\npazvmqbfjtqqlosiecxughkwud\npazvmqbfjtrnlosiedhyphkwud\npazsmqbcutrnlosiecxyghkwud\npazvmqbgrtrnlosiecxyghpwud\npazemqbfjtznlosiecxyghkvud\npazvkqbfjtrilosiecxyghkwod\npfzvmqbfjtrnlopiecxygjkwud\npazvmqvfjtreloniecxyghkwud\npazvmqbfjernljsiecxgghkwud\npazvmqikjtrnlosiecxyghqwud\npazvmqbfjtrnpesoecxyghkwud\nfazvmqbfjtrnlosihchyghkwud\npazvmqbfjtgnloanecxyghkwud\npazvmqsfjqrnlosiecxychkwud\nparvmqbfjtrnlosiecxygfuwud\nprzvmqbfjtrhlosihcxyghkwud\npazvmqbcjtrnlosimcxgghkwud\npazvmqbfjtrnlosceciyjhkwud\npazvkqbfjtrylosivcxyghkwud\npazvmqbfjtrnlgsieoxyghdwud\npazvmqnfstrnlowiecxyghkwud\npazvmqbfdtrnlosieumyghkwud\npazvmqbfjtrnlosyecxfghkwul\npazvmqbfjtrclosivcxyghkcud\npazjmqbfjtrnlosiecxygokwkd\nhazvmqbfjtrflosiecxzghkwud\nwazvmqbfjtrnlomiecxyphkwud\nyazvmqbfjirnkosiecxyghkwud\npczvmqbfjtrnlohiecxyghkwpd\npazvmqbfotrbeosiecxlghkwud\npazvmqbfjtrplosiecxynhzwud\npaxvbqbwjtrnlosiecxyghkwud\npazvmqvfjtrnlosiecbyghqwud\npazjmqbfjtrnlosiecxoghkwed\npazvmqbfjtreljsitcxyghkwud\nmazamqbfjtrnlosiecxoghkwud\npazvmqbfjjrnposiscxyghkwud\npbrvmqbfjtrnloliecxyghkwud\npazvmqbfjtrnlosiecxgghkyyd\npmzvmqbfntrnlosiecxyghkwuw\npazvzqbfjtrnlosienxyghzwud\npazvmqifjtvnlosrecxyghkwud\ntazvmqbhjtjnlosiecxyghkwud\npazvmqbfjtlnxosiecxyghkwuo\npazvmqbfjennlosiecxyghkwxd\npahvmqbfjhrnlosiecxythkwud\npazvmlkfjtrnlxsiecxyghkwud\npfzvmqbojtrnlosieciyghkwud\npazvbqbfjtrollsiecxyghkwud\neazvmqbfjtrnlosiecayghkoud\npazvmqbfjtjnlvsiecxyghkwsd\npazvoqbojtrnlosiecfyghkwud\npazvmqbfjtuslosiecxyghksud\npazvmqbfjnrnlosiedxyghkwup\npazvmqbjjtrnlosieaxyghdwud\npazccqbfjtrhlosiecxyghkwud\npbzvmqkfjtrnlosievxyghkwud\npazvmqrljtrnlosiscxyghkwud\npazvmqbfjfoqlosiecxyghkwud\npazcmqbfjtrnlosiecxyihkwuf\npszvmqbfjtrnnosiacxyghkwud\naazvmqbfjtrnlosieyxyghkwld\npazvrqbfntrnlosiycxyghkwud\npkzvoqbfjtrnlosiecxyghxwud")

(defn box-id->count-map
  [box-id]
  (reduce (fn [count-map character]
            (update count-map character (fn [occurrences]
                                          (inc (or occurrences 0)))))
          {}
          box-id))

(defn n-occurrences?
  [count-map n]
  (core/seq-contains? (vals count-map) n))

(defn count-n-occurrences
  [box-ids n]
  (as-> box-ids $
        (map box-id->count-map $)
        (filter (fn [count-map]
                  (n-occurrences? count-map n))
                $)
        (count $)))

(defn solve-a
  []
  (let [box-ids (clojure.string/split-lines input)
        number-2-occurrences (count-n-occurrences box-ids 2)
        number-3-occurrences (count-n-occurrences box-ids 3)]
    (* number-2-occurrences number-3-occurrences)))

(comment
  (solve-a)
  ;; 8296
  )

;Confident that your list of box IDs is complete, you're ready to find the boxes full of prototype fabric.
;
;The boxes will have IDs which differ by exactly one character at the same position in both strings. For example, given the following box IDs:
;
;abcde
;fghij
;klmno
;pqrst
;fguij
;axcye
;wvxyz
;The IDs abcde and axcye are close, but they differ by two characters (the second and fourth). However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.
;
;What letters are common between the two correct box IDs? (In the example above, this is found by removing the differing character from either ID, producing fgij.)

(defn get-string-diff
  [s1 s2]
  (reduce (fn [diff index]
            (if (= (nth s1 index) (nth s2 index))
              diff
              (inc diff)))
          0
          (range (count s1))))

(defn get-matching-characters
  [s1 s2]
  (reduce (fn [matching-characters index]
            (if (= (nth s1 index) (nth s2 index))
              (str matching-characters (nth s1 index))
              matching-characters))
          ""
          (range (count s1))))

(defn solve-b
  []
  (let [box-ids (clojure.string/split-lines input)]
    (->> (some (fn [index]
                 (let [box-id-1 (nth box-ids index)]
                   (when-let [box-id-2 (some (fn [box-id-2]
                                               (when (= (get-string-diff box-id-1 box-id-2) 1)
                                                 box-id-2))
                                             (drop (inc index) box-ids))]
                     [box-id-1 box-id-2])))
               (range (count box-ids)))
         (apply get-matching-characters))))

(comment
  (solve-b)
  ;; pazvmqbftrbeosiecxlghkwud
  )
