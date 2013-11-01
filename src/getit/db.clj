(ns getit.db
  (:use [monger.core           :only [connect-via-uri! set-db! get-db]])
  (:require [getit.dbconn]))
(defn prepare-mongo []
  (do
    #_(connect!)
    (connect-via-uri! dburi)
    #_(set-db! (get-db "getit"))))
