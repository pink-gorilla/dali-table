(ns demo.helper.menu
  (:require
   [ui.site.template]
   [ui.site.layout]))

(defn demo-header []
  [ui.site.template/header-menu
   {:brand "demo"
    :brand-link "/"
    :items [{:text "complex" :dispatch [:bidi/goto 'demo.page.complex/page-complex-menu :query-params {}]}
            {:text "simple" :dispatch [:bidi/goto 'demo.page.simple/page-simple-menu :query-params {}]}
            {:text "simple2" :dispatch [:bidi/goto 'demo.page.simple2/page-simple2-menu :query-params {}]}
            {:text "simple2-no-menu" :dispatch [:bidi/goto 'demo.page.simple2/page-simple2 :query-params {}]}
            {:text "static" :dispatch [:bidi/goto 'demo.page.static/page-static-menu :query-params {}]}
            {:text "static-full" :dispatch [:bidi/goto 'demo.page.static/page-static-full-menu :query-params {}]}
            {:text  "home-made" :dispatch [:bidi/goto 'demo.page.homemade/page-table-menu :query-params {}]}
            {:text  "tml" :dispatch [:bidi/goto 'demo.page.tml/page :query-params {}]}
            ]}])

(defn wrap-menu [page]
  (fn [route]
    [ui.site.layout/header-main  ; .w-screen.h-screen
     [demo-header]
     [page route]]))
