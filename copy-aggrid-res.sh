#!/bin/sh

cpr () {
  sourcedir="demo/node_modules/$1"
  source="$sourcedir/$2"
  targetdir="resources/public/$1"
  if [ -d $sourcedir ]; then
     echo "copying $source ==> $targetdir"
     mkdir -p $targetdir
     cp $source $targetdir
  else 
    echo "ERROR: $sourcedir does not exist."
  fi
}

cpr "ag-grid-community/dist/styles" "*.css"

