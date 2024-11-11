{:npm-deps
 {; shadow cljs creates package.json 
  ; based on this dependencies 

  ; grids
  "ag-grid-community" "^32.3.2" ; "^25.2.0"
  "ag-grid-react" "^32.3.2" ; "^25.2.0"
  "cheetah-grid" "^1.15.0"
  ; charts
  "highcharts" "^11.4.8"
  "pixi.js" "^8.4.1"
  "@pixi/ui" "^2.1.5"

   ;vega
  "vega" "^5.20.2"
  "vega-lite" "^4.17.0"
  "vega-tooltip" "^0.25.1"
  "vega-embed" "6.18.2" ; needs to be 6.18, as 6.19 breaks build: https://github.com/vega/vega-embed/issues/780
  "react-vega" "^7.4.3" ; react-vega brings embed
;  "vega-loader-arrow" "0.0.10"

  "prop-types" "^15.5.7" ; to compile vega in ci

  ; shadow cljs version needs to match the one in webly/deps.edn
  "shadow-cljs" "2.28.15"  ; buffer polyfill fix

;
  }}
