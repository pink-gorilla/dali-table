(ns demo.helper.homemade)

(defn goto-person [col-info row]
  [:a {:href (str "/person/id/" (:id row))}
   [:span "goto-person"]])

(defn format-hash [v]
  (str "#" v))

(defn attrs-red [v] {:class "bg-red-300"})

(defn format-boolean [b]
  (let [s (case b
            true "✓" ; "true"
            false "✕" ; "false"
            "")]
    s))