# reagent-table [![GitHub Actions status |pink-gorilla/reagent-table](https://github.com/pink-gorilla/reagent-table/workflows/CI/badge.svg)](https://github.com/pink-gorilla/reagent-table/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/reagent-table.svg)](https://clojars.org/org.pinkgorilla/reagent-table)


- reagent table vizualizes big tables in reagent.
- visualizations can be either in table or chart form.
- data can be loaded 
  - either inline (as clojure datastructure) or
  - as a techml dataset (which gets loaded via transit-json encoding)
- under the hood it uses
  - ag-grid
  - highcharts.js
- it also implements its own 
  - table renderer and
  - canvas chart renderer.
  

# demo

```
cd demo
clj -X:npm-install
clj -X:demo
```

Open Browser at port 8080.