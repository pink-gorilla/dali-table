(ns demo.helper.menu
  (:require
   [site]
   [layout]))

(defn demo-header []
  [site/header-menu
   {:brand "demo"
    :brand-link "/"
    :items [; workflow
            {:text "complex" :dispatch [:bidi/goto 'demo.page.complex/page-complex-menu :query-params {}]}
            {:text "simple" :dispatch [:bidi/goto 'demo.page.simple/page-simple-menu :query-params {}]}
            {:text "simple2" :dispatch [:bidi/goto 'demo.page.simple2/page-simple2-menu :query-params {}]}]}])

(defn wrap-menu [page]
  (fn [route]
    [layout/header-main  ; .w-screen.h-screen
     [demo-header]
     [page route]]))
