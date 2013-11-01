(ns getit.handler
  (:use compojure.core
        [getit.login                 :only [login check-login logout]]
        [getit.db                    :only [prepare-mongo]]
        [getit.search]
        [org.httpkit.server          :only [run-server]])
  (:require [compojure.handler       :as handler]
            [compojure.route         :as route]
            [ring.middleware.reload  :as reload]
            [noir.session            :as session]))

(defroutes getit-routes
           (GET "/" [] (login))
           (GET "/logout" [] (logout))
           (POST "/admin" [uname upwd] (check-login uname upwd))
           (GET "/search" [] (search))
           (GET "/search/:id" [id] (show id))
           (GET "/test/:id" [id] (show-api-id id))
           (GET "/id" [id] (search-id id))
           (GET "/name" [rlname] (search-nm rlname))
           (route/resources "/")
           (route/not-found "Not Found"))

(def getit
  (->
    (handler/site getit-routes)
    (session/wrap-noir-session)))
(defn -main [& args]
  (do
    (prepare-mongo)
    (run-server getit {:port 8080})
    (println "server is start at prot 8080!")))
