(ns rtable.plot
  (:require
   [potemkin :refer [import-vars]]
   [rtable.plot.aggrid]
   [rtable.plot.cheetah]
   [rtable.plot.highcharts]
   [rtable.plot.pixi]
   [rtable.plot.rtable]
   [rtable.plot.vega]))

(import-vars
 rtable.plot.aggrid/aggrid-ds
 rtable.plot.cheetah/cheetah-ds
 rtable.plot.highcharts/highstock-ds
 rtable.plot.pixi/pixi-ds
 rtable.plot.rtable/rtable-ds
 rtable.plot.rtable/rtable
 rtable.plot.vega/vegalite
 rtable.plot.vega/vegalite-ds
 rtable.plot.vega/vega)

