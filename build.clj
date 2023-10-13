(ns build
  (:require
   [babashka.fs :as fs]
   [clojure.tools.build.api :as b]
   [deps-deploy.deps-deploy :as dd]))

(def lib 'org.pinkgorilla/reagent-table)
(def version (format "0.0.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn- spit-version []
  (spit (doto (fs/file "target/classes/META-INF/pink-gorilla/reagent-table/meta.edn")
            (-> fs/parent fs/create-dirs)) {:module-name "reagent-table"
                                            :version version}))

(def pom-template
  [[:licenses
    [:license
     [:name "Eclipse Public License"]
     [:url "https://www.eclipse.org/legal/epl-v10.html"]]]])


(defn jar [_]
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :pom-data pom-template
                :src-dirs ["src"]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (spit-version)
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))


(defn deploy "Deploy the JAR to Clojars." []
  (println "Deploying to Clojars.")
  (dd/deploy {:installer :remote
              ;:sign-releases? true
              ;:pom-file (b/pom-path (select-keys opts [:lib :class-dir]))
              :artifact (b/resolve-path jar-file)}))

