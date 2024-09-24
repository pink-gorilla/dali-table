#!/bin/sh

cpr () {
  sourcedir="node_modules/$1"
  source="$sourcedir/$2"
  targetdir="target/node_modules/public/$1" 
  if [ -d $sourcedir ]; then
     echo "copying $source ==> $targetdir"
     mkdir -p $targetdir
     cp $source $targetdir
  else 
    echo "ERROR: $sourcedir does not exist."
  fi
}

cpr "highcharts/gfx/stock-icons" "*.svg"
cpr "highcharts/css" "*.css"
cpr "highcharts/css/themes" "*.css"
cpr "highcharts/css/stocktools" "*.css"
cpr "highcharts/css/annotations" "*.css"
