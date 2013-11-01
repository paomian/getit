(ns getit.template
  (:use [hiccup core page]
        [hiccup.form])
  (:require [noir.session              :as session]))

(defn html-temp [code]
  (let [user (session/get :uname)]
    (html5
     [:head
      [:title "Hello World"]
      (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap.min.css")
      (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap-responsive.min.css")
      ]
     [:body
      [:div.navbar.navbar-inverse 
       [:div.navbar-inner 
        [:div.container 
         [:a.btn.btn-navbar {:data-toggle "collapse" :data-target ".nav-collapse"}
          (for [x (range 4)] 
            [:span.icon-bar])]
         [:a.brand {:href "/"} "Getit"]
         [:ul.nav 
          [:li [:a {:href "/"} "主页"]]
          [:li [:a {:href "http://sdutlinux.org/"} "技术支持"]]
          [:li [:a {:href "http://gotit.asia/"} "got it"]]
          ]
         [:form.navbar-search.pull-left [:input.search-query.span2 {:type "text" :placeholder "Search"}]]
         [:div.nav-collapse.collapse
          [:div.btn-group.pull-right
           [:button.btn.dropdown-toggle.btn-primary {:data-toggle "dropdown" :href "#"} (if user (str user) "用户")
            [:span.caret]]
           (if user 
             [:ul.dropdown-menu
              [:li [:a {:href "/logout"} "注销"]]]
             [:ul.dropdown-menu
              [:li [:a {:href "/login"} "登陆"]]])
           ]]]]]
      code
      (include-js "http://libs.baidu.com/jquery/2.0.2/jquery.min.js")
      (include-js "http://libs.baidu.com/bootstrap/2.3.2/js/bootstrap.min.js")
      ])))
(defmacro template [page-name [& args] & code]
  `(defn ~page-name [~@args]
     (html-temp (do ~@code))))
#_(defmacro def-page [page-name [& args] & code]
    `(defn ~page-name [~@args]
            (html-doc (do ~@code))))
