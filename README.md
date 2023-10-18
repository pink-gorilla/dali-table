# reagent-table [![GitHub Actions status |pink-gorilla/reagent-table](https://github.com/pink-gorilla/reagent-table/workflows/CI/badge.svg)](https://github.com/pink-gorilla/reagent-table/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/reagent-table.svg)](https://clojars.org/org.pinkgorilla/reagent-table)


## another table library ?
- generates "real" html table (table tbody tr td th)
- 100% clojurescript (+ html/css)
- table and cell styling can be easily customized in clojurescript
- cell values can be easily formatted (or completely customized)
- designed to display lots of rows and lots of columns
- a lot of the code is based on: [reagent-table](https://github.com/Frozenlock/reagent-table)

# column properties
- a column is a map
- :path get (keyword/string/etc) or get-in (vector with path)
- :header the column header (gets calculated from :path if not set)
- :format format-function `(fn [col-data] "returns html data, can be just a format fn")`
- :attrs  `(fn [col-data] {:style {:display "none"}} :class "col-hidden")`
- :el-type the element type that gets generated, by default :span
- :max-width maximum width of the column, example: "140px"
- :render-cell by default this uses the table render-cell fn. If passed, format/attrs/el-type will not work.

# table options (a map) 
- :class (a string of class names separated by space)
  - *table-head-fixed* will make the first row sticky (header stays always on top when scrolling)
  - *table-auto* do not fit to container if rows are small.
  - *padding-sm* adds a small padding to rows and columns
  - *table-blue table-red* sets the background
  - *table-striped* adds a striping effect to the background
  - *table-hover* highlights hovering row 
- :style (a map with style options)
- :render-cell (fn [col cell]) that produces hiccup. by default cell-fn (which uses fromat/attrs/el-type)

# example

```
 [rtable {:class "table-head-fixed padding-sm table-red table-striped table-hover"
          :style {:width "50vw"
                  :height "40vh"
                  :border "3px solid green"}}
    [{:path :id}
     {:path :name :max-width "60px"}
     {:path :quote}
     {:path :quote2}]
    data]
```

# demo

The demo uses the extension manager from goldly to add reagent-table to goldly.

```
cd demo
clj -X:demo:npm-install
clj -X:demo:compile
clj -X:demo
```

Open Browser at port 8080.