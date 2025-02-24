# dali-table [![GitHub Actions status |pink-gorilla/dali-table](https://github.com/pink-gorilla/dali-table/workflows/CI/badge.svg)](https://github.com/pink-gorilla/dali-table/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/dali-table.svg)](https://clojars.org/org.pinkgorilla/dali-table)


- dali-table vizualizes big tables in reagent.
- visualizations can be either in table or chart form.
- data can be loaded 
  - either inline (as clojure datastructure) or
  - as a techml dataset (which gets loaded via transit-json encoding)
- under the hood it uses
  - ag-grid
  - cheetah grid
  - highcharts.js
  - vega charts
- it also implements its own 
  - table renderer and
  - pixijs based canvas chart renderer.
  

# demo

```
cd demo
clj -X:npm-install
clj -X:demo
```

Open Browser at port 8080.