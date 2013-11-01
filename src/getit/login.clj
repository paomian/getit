(ns getit.login
  (:use 
    [hiccup.core]
    [hiccup.page]
    [hiccup.form]
    [monger.operators]
    [getit.template             :only [template]]
    [monger.collection          :only [find-one-as-map update]])
  (:import 
    [org.jasypt.util.password StrongPasswordEncryptor])
  (:require
    [ring.util.response          :as response]
    [noir.session                :as session]))
#_(defn login []
    (html
      [:html {:lang "en"} 
       [:head {} [:meta {:http-equiv "Content-Type", :content "text/html; charset=UTF-8"}] "\n\t\t" [:meta {:charset "utf-8"}] "\n\t\t" [:title {} "Getit login"] "\n\t\t" 
        (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap.min.css")
        (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap-responsive.min.css")
        [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
        [:meta {:name "description", :content ""}]
        [:meta {:name "author", :content ""}]
        [:style {:type "text/css"} "\n\t\t\tbody {\n\t\t\t\tpadding-top: 40px;\n\t\t\t\tpadding-bottom: 40px;\n\t\t\t\tbackground-color: #f5f5f5;\n\t\t\t}\n\n\t\t\t.form-signin {\n\t\t\t\tmax-width: 300px;\n\t\t\t\tpadding: 19px 29px 29px;\n\t\t\t\tmargin: 0 auto 20px;\n\t\t\t\tbackground-color: #fff;\n\t\t\t\tborder: 1px solid #e5e5e5;\n\t\t\t\t-webkit-border-radius: 5px;\n\t\t\t\t-moz-border-radius: 5px;\n\t\t\t\tborder-radius: 5px;\n\t\t\t\t-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);\n\t\t\t\t-moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);\n\t\t\t\tbox-shadow: 0 1px 2px rgba(0,0,0,.05);\n\t\t\t}\n\t\t\t.form-signin .form-signin-heading,\n\t\t\t.form-signin .checkbox {\n\t\t\t\tmargin-bottom: 10px;\n\t\t\t}\n\t\t\t.form-signin input[type=\"text\"],\n\t\t\t.form-signin input[type=\"password\"] {\n\t\t\t\tfont-size: 16px;\n\t\t\t\theight: auto;\n\t\t\t\tmargin-bottom: 15px;\n\t\t\t\tpadding: 7px 9px;\n\t\t\t}\n\n\t\t"]
        [:link {:href "http://v2.bootcss.com/assets/css/bootstrap-responsive.css", :rel "stylesheet"}]
        [:style {} "[touch-action=\"none\"]{ -ms-touch-action: none; touch-action: none; }[touch-action=\"pan-x\"]{ -ms-touch-action: pan-x; touch-action: pan-x; }[touch-action=\"pan-y\"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action=\"scroll\"],[touch-action=\"pan-x pan-y\"],[touch-action=\"pan-y pan-x\"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }\n\t\t"]
        ]
       [:body {:data-ember-extension "1"} 
        [:div {:class "container"} 
         [:form {:class "form-signin"} 
          [:h2 {:class "form-signin-heading"} "Please sign in"] 
          [:input {:type "text", :class "input-block-level", :placeholder "Email address"}] 
          [:input {:type "password", :class "input-block-level", :placeholder "Password"}] 
          [:button {:class "btn btn-large btn-primary", :type "submit"} "Sign in"]]]
        (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap.min.css")
        (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap-responsive.min.css")
        ]]))
(template login-page []
          [:div.container
           [:form.form-signin {:method "POST" :action "/admin"}
            [:table
             [:tr
              [:td (label :uname "Username")]
              [:td (text-field :uname)]]
             [:tr
              [:td (label :upwd "Password")]
              [:td (password-field  :upwd)]]
             [:tr
              [:td]
              [:td [:button.btn.btn-primary {:type "submit"} "Log In"]]]]]])
(defn login []
  (let [admin (session/get :uname)]
    (if admin
      (response/redirect "/search")
      (login-page))))

(defn check-login [uname upwd]
  (let [result (find-one-as-map "admin" {:uname uname})]
    (if result
      (if (.checkPassword (StrongPasswordEncryptor.) upwd (result :upwd))
        (do 
          (update "admin" {:uname uname} {$set {:last-login (java.util.Date.)}})
          (session/put! :uname uname)
          (response/redirect "/search"))
        (response/redirect "/"))
      (response/redirect "/"))))
(defn logout []
  (do
    (session/clear!)
    (response/redirect "/")))
(defn encryptor [pwd]
  (.encryptPassword (StrongPasswordEncryptor.) pwd))
;;随机数
(defn random-filename [l] (loop [i l,r ""] (if (zero? i) r (recur (dec i) (str r (rand-nth "abcdefghijklmnopqrstuvwxyz0123456789"))))))
