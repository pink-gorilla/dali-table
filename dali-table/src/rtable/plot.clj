(ns rtable.plot
  (:require
   [potemkin :refer [import-vars]]
   [rtable.plot.aggrid]
   [rtable.plot.cheetah]
   [rtable.plot.highcharts]
   [rtable.plot.pixi]
   [rtable.plot.rtable]
   [rtable.plot.vega]
   [rtable.plot.echarts]))

(import-vars
 ; no ds - table 
 rtable.plot.rtable/rtable
 ; ds-table
 rtable.plot.aggrid/aggrid-ds
 rtable.plot.cheetah/cheetah-ds
 rtable.plot.rtable/rtable-ds
 ; no ds - chart
 rtable.plot.highcharts/highchart
 rtable.plot.vega/vegalite
 rtable.plot.vega/vega
 rtable.plot.echarts/echarts
 ; ds-chart
 rtable.plot.highcharts/highstock-ds
 rtable.plot.vega/vegalite-ds
 rtable.plot.pixi/pixi-ds
 )

