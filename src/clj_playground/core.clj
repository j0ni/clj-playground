(ns clj-playground.core
  (:gen-class)
  (:require (clj-time
             [core :as time]
             [format :as format])
            (monger
             [core :as mg]
             [collection :as mc]
             [query :as mq])
            (clj-http
             [client :as http])))

(defn -main
  "I boot the world!"
  [& args]
  (println "One day, I will run a bunch of examples!"))

(def query-url
  "This is the format string for pulling the NOAA query"
  "http://www.weather.gov/forecasts/xml/sample_products/browser_interface/ndfdBrowserClientByDay.php?lat=%d&lon=%d&format=24+hourly&numDays=%d&startDate=%s")

;; 4 days outlook hardcoded FIXME
(defn format-query [lat lng start]
  (format query-url lat lng 4 start))

(defn now-str []
  (format/unparse (format/formatters :date-time) (time/now)))

(defn get-point-forecast [lat lng]
  (http/get (format-query lat lng (now-str))))