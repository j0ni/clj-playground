(ns clj-playground.core
  (:gen-class)
  (:require (clj-time
             [core :as time]
             [format :as format]
             [coerce :as coerce])
            (monger
             [core :as mg]
             [collection :as mc]
             [query :as mq])
            (clj-http
             [client :as http])
            (clojure
             [xml :as xml])
            (compojure
             [core :as compojure]
             [route :as route])
            (clojure.data
             [json :as json]))
  (:import org.bson.types.ObjectId))

(def wunderground-key "192b1cab630c7d45")
(def current-conditions-uri
  "http://api.wunderground.com/api/%s/conditions/q/%s,%s.xml")

(def db-name "lifelogger")

(defn -main
  "I boot the world!"
  [& args]
  (println "One day, I will run a bunch of examples!"))

(mg/connect!)
(mg/set-db! (mg/get-db db-name))

;; 4 days outlook hardcoded FIXME
(defn format-query [lat lng]
  (format current-conditions-uri wunderground-key lat lng))

(defn get-point-forecast [lat lng]
  (xml/parse (format-query lat lng)))

(defn save-note [note lat lng]
  (mc/insert "notes"
             {:this-moment (coerce/to-date (time/now))
              :content note
              :weather (get-point-forecast lat lng)}))

(defn get-notes []
  (json/json-str
   (mc/find-maps "notes")))

(defn get-note [id]
  (json/json-str
   (mc/find-one-as-map "notes" {:_id (ObjectId. id)})))

(compojure/defroutes notes-routes
  (compojure/GET "/" [] ( get-notes))
  (compojure/GET "/:id" [id] get-note))

(compojure/defroutes ring-handler
  (compojure/GET "/" [] "<h3>hello world!</h3>")
  (compojure/context "/notes" [] notes-routes))

