(defproject getit "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [compojure "1.1.5"]
                           [hiccup "1.0.4"]
                           [com.novemberain/monger "1.5.0"]
                           [ring/ring-core "1.2.0"]
                           [org.jasypt/jasypt "1.7"]
                           [http-kit "2.1.13"]
                           [lib-noir "0.7.0"]
                           ;;[org.clojure/data.json "0.2.3"]
                           ;;[com.taoensso/carmine "2.3.1"]
                           ;;[ring-mock "0.1.5"]
                           [clj-http "0.7.7"]
                           [org.clojure/data.json "0.2.3"]
                           [hickory "0.5.1"]]
            :plugins [[lein-ring "0.8.5"]]
            :main getit.handler
            :profiles
            {:dev {:dependencies [[ring-mock "0.1.5"]]}})
