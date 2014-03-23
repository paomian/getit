(ns getit.search
  (:use [monger.operators]
        [hiccup.form]
        [hiccup.element]
        [monger.collection       :only [find-one-as-map find-maps update]]
        [getit.template          :only [template]])
  (:require 
    [noir.session                :as session]
    [ring.util.response          :as response]
    [clojure.data.json           :as json]
    [clj-http.client             :as client]))
(declare result)
(declare api-date)
(defn parse-int [str] (try (java.lang.Long/valueOf str) (catch NumberFormatException _ 0)))
(template search-page []
          [:div.container
           [:div.row
            [:div.span6
             [:form.form-signin {:method "GET" :action "/id"}
              [:table
               [:tr
                [:td (label :id "学号")]
                [:td (text-field  :id)]]
               [:tr
                [:td]
                [:td [:button.btn.btn-primary {:type "submit"} "Search"]]]]]]
            [:div.span6
             [:form.form-signin {:method "GET" :action "/name"}
              [:table
               [:tr
                [:td (label :rlname "姓名")]
                [:td (text-field  :rlname)]]
               [:tr
                [:td]
                [:td [:button.btn.btn-primary {:type "submit"} "Search"]]]]]]]])
(defn search []
  (let [admin (session/get :uname)]
    (if admin
      (search-page)
      (response/redirect "/"))))
(template info [result]
          ;;(for [res result]
          [:div.container
           [:table.table
            ;;(for [[name label-name]
            ;;  [["姓名 :" :realname]
            ;;  ["学号 :" :username]
            ;;  ["密码 :" :password]
            ;;["邮箱 :" :email]]]
            [:tr
             (for [x ["姓名"  "学号"  "密码"  "email"]]
               [:th (str x)])]
            (for [res result]
              [:tr
               [:td
                [:a {:href (str "/search/" (:username res))} (str (:realname res))]]
               [:td
                #_[:a {:href (str "/test/" (:username res))} (str (:username res))]
                (:username res)
                ]
               (for [[label-name]
                     [
                      [:password]
                      [:email]]]
                 [:td (str (label-name res))])])]])
(defn show-image
  [result]
  (let [id (str (:username result))
        admin (session/get :uname)]
      (cond
        (= \1 (get id 0)) (cond
                            (= \0 (get id 1)) "/photo/2010/"
                            (= \1 (get id 1)) "/photo/2011/"
                            (= \2 (get id 1)) "/photo/2012/")
        (= \9 (get id 0)) "/photo/2009/0")))
(template show-id [result]
          [:div.container
           [:div.row
            [:div.span6
             [:table.table
              (for [[name label-name]
                    [
                     ["编号 :" :id]
                     ["姓名 :" :realname]
                     ["学号 :" :username]
                     ["密码 :" :password]
                     ["邮箱 :" :email]
                     ["注册 :" :registe_date]
                     ["reg_comm :" :reg_comm]
                     ["lastaction :" :lastaction]
                     ["login_time :" :login_time]
                     ["login_host :" :login_host]
                     ["login_application :" :login_application]]]
                [:tr
                 [:td (str name)]
                 [:td (str (label-name result))]])]]
            [:div.span6
             [:img {:src (str (show-image result) (:username result) ".jpg")}]]]])
(template show-api-id [id]
          [:div.container
           [:table.table
            (for [[name label-name]
                  (api-date id)]
              [:tr
               [:td (str name)]
               [:td (str label-name)]])]])
(defn search-api
  [id]
  (let [admin (session/get :uname)]
    (if admin
      (do
        (show-api-id id))
      (response/redirect "/"))))
(defn search-id 
  [id]
  (println (type id))
  (let [result (find-maps "user" {:username (parse-int id)})
        admin (session/get :uname)]
    (if admin
      (do
        #_(for [res result]
          (future (def api-result (api-date (:username res)))))
        (info result))
      (response/redirect "/"))))
(defn search-nm 
  [rlname]
  (println rlname)
  (let [result (find-maps "user" {:realname rlname})
        admin (session/get :uname)]
    (if admin
      (do
        #_(for [res result]
          (future (def api-result (api-date (:username res)))))
        (info result))
      (response/redirect "/"))))
(defn show 
  [id]
  (let [result (find-one-as-map "user" {:username (parse-int id)})
        admin (session/get :uname)]
    (if admin
      (do
        (println result)
        (show-id result))
      (response/redirect "/"))))
(defn api-date 
  [id]
  (json/read-str
    (:body
      (client/post "http://gotit.asia/api/gpa" {:form-params {:xh id}})) :key-fn keyword))
