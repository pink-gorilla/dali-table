(ns demo.helper.menu
  (:require
   [ui.site.template]
   [ui.site.layout]))

(defn demo-header []
  [ui.site.template/header-menu
   {:brand "demo"
    :brand-link "/"
    :items [; reagent-table
            {:text "rt-simple" :dispatch [:bidi/goto 'demo.page.reagent-table-simple/page-simple-menu :query-params {}]}
            {:text "rt-simple2" :dispatch [:bidi/goto 'demo.page.reagent-table-simple2/page-simple2-menu :query-params {}]}
            {:text "rt-simple2-no-menu" :dispatch [:bidi/goto 'demo.page.reagent-table-simple2/page-simple2 :query-params {}]}
            {:text "rt-complex" :dispatch [:bidi/goto 'demo.page.reagent-table-complex/page-complex-menu :query-params {}]}

            ; html-table
            {:text "html-simple" :dispatch [:bidi/goto 'demo.page.htmltable-static/page-static-menu :query-params {}]}
            {:text "html-full" :dispatch [:bidi/goto 'demo.page.htmltable-static/page-static-full-menu :query-params {}]}
            ; rtable
            {:text  "rtable" :dispatch [:bidi/goto 'demo.page.homemade/page-table-menu :query-params {}]}
            ; tml
            {:text  "tml-txt" :dispatch [:bidi/goto 'demo.page.tml/page :query-params {}]}
            {:text  "tml-rtable" :dispatch [:bidi/goto 'demo.page.tml-rtable/page :query-params {}]}
            ; aggrid
            {:text  "aggrid" :dispatch [:bidi/goto 'demo.page.aggrid/page :query-params {}]}
            {:text  "aggrid-tml" :dispatch [:bidi/goto 'demo.page.aggrid-tml/page :query-params {}]}

            ; highcharts
            {:text  "highchart" :dispatch [:bidi/goto 'demo.page.highcharts/highchart-page :query-params {}]}
            {:text  "highchart0full" :dispatch [:bidi/goto 'demo.page.highcharts/highchart-full-page :query-params {}]}]}])

(defn wrap-menu [page]
  (fn [route]
    [ui.site.layout/header-main  ; .w-screen.h-screen
     [demo-header]
     [page route]]))
