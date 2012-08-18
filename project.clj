(defproject clj-playground "0.1.0-SNAPSHOT"
  :description "A place for fiddling"
  :url "http://j0ni.ca"
  :license {:name "GPL v3"
            :url "http://opensource.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.novemberain/monger "1.1.2"]
                 [clj-time "0.4.3"]
                 [compojure "1.1.1"]
                 [clj-http "0.5.3"]]
  :profiles {:dev {:plugins [[lein-ring "0.7.3"]]}}
  :main clj-playground.core
  :ring {:handler clj-playground.core/ring-handler})
