(ns getit.db
  (:use 
    [monger.core           :only [connect-via-uri! set-db! get-db]]
    [getit.dbconn          :only [dburi]]))
(defn prepare-mongo []
  (do
    #_(connect!)
    (connect-via-uri! dburi)
    #_(set-db! (get-db "getit"))))
