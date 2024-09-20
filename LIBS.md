
good comparison:
https://jsgrids.statico.io/


# fast-grid
https://github.com/gabrielpetersson/fast-grid/
https://github.com/gabrielpetersson/fast-grid/blob/master/example/src/App.tsx
- reuses dom div nodes depending on how scrolling goes.
- worker thread for filtering and sorting
- CAN NOT STYLE INDIVIDUAL CELLS.

# ag-grid
257kb gzmin
https://www.ag-grid.com/

# Tanstack Table
headless ui.
seems that it is used by ag-grid.


# glide data grid
https://grid.glideapps.com/
https://www.npmjs.com/package/@glideapps/glide-data-grid
https://bundlephobia.com/package/@glideapps/glide-data-grid@6.0.3
63kb gzmin
- canvas based
- cannot import because of dynamic import that clojure-compiler does not support.
