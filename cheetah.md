# cheetah grid

- https://future-architect.github.io/cheetah-grid/documents/api/js/grid_data/#using-array-object
- renders table as canvas
- a lot faster than ag-grid and our table.
- supports 
  - get data via function call (so not one huge block)
  - custom cell style function



## frozen col count

ListGrid({
  parentElement: document.querySelector(".sample1"),
  header: [
    { field: "personid", caption: "ID", width: 100 },
    { field: "fname", caption: "First Name", width: 200 },
    { field: "lname", caption: "Last Name", width: 200 },
    { field: "email", caption: "Email", width: 250 },
  ],
  frozenColCount: 1,