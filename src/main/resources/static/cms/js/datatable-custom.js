var dataTables = {};
var lastFilteredColumns = {};
function loadTableAjaxGET(url,table,columns,initComplete,drawCallback,rowsGroup) {
	
	if(!url || url == '') {
		console.log('/cms/js/datatable-custom.js - loadTableAjaxGET Error : URL must be exists data !');
		return;
	}
	if(!table || !table.is( "table" )) {
		console.log('/cms/js/datatable-custom.js - loadTableAjaxGET : '+table+' incorrect!');
		return;
	}
	
	$("#overlay").show();
	$.ajax({
		type : "GET",
		url : url,
		dataType : "json",
		success : function(data) {
			$("#overlay").hide();
			if (data.status == 200) {
				var list = data.data;
				$.when(loadTable(table,columns,list,initComplete,drawCallback,rowsGroup))
				.then(function() {
					dataTables[table.selector] = table.DataTable().rows().data();
					filterDataByLastFilteredCoulmns(table);
				});
			}
		},
		error : function(xhr) {
			$("#overlay").hide();
			if(xhr.responseJSON.message && xhr.responseJSON.message!='') {
	        	Swal.fire({
					position: 'center',
		            title: xhr.responseJSON.message,
		            type: 'warning',
		            showConfirmButton: false,
		            timer:2000,
		            width:550
		        })
	        }
		}
	});
}

function loadTable(table,columns,data,initComplete,drawCallback,rowsGroup) {
	
	if(table.prop("tagName") != 'TABLE') {
		alert('initTableDefault: Error: Table '+table.prop("tagName")+' is not table');
		console.log('/cms/js/datatable-custom.js - loadTable Error: Table '+table.prop("tagName")+' is not table');
		return;
	}
	
	if(!Array.isArray(columns)) {
		alert('initTableDefault: Error: Columns '+columns+' is not array');
		console.log('/cms/js/datatable-custom.js - loadTable Error: Columns '+columns+' is not array');
		return;
	}
	
	if($.fn.dataTable.isDataTable(table)) {
		table.DataTable().clear().draw();
		table.DataTable().rows.add(data); // Add new data
		table.DataTable().columns.adjust().draw();
		return;
	}
	
	table.DataTable({
		"order": [],
		"columnDefs": [
           	{
           	 "orderable": false,
        	  className: 'text-center',
        	  targets: 0,
        	  searchable: false,
        	  "width": "5%"
        	}
         ],
        "scrollY": "54vh",
 	    "scrollCollapse": true,
 	    "paging": true,
 	    "bLengthChange": false,
 	    "searching": false,
 	    "select": true,
 	    "responsive": true,
 	    "width": "100%",
 	    "bJQueryUI": true,
 	    "sDom": 'lfrtip',
 	    "language": {
 	    	"emptyTable": "No data was retrieved.",
 	    	"paginate": {
 	          "previous": "Prev",
 	          "next": "Next"
 	        },
 	        "info": "_PAGE_ ~ _PAGES_ /ALL _TOTAL_ ",
 	        "infoEmpty": "0 ~ 0 /all 0"
 	    },
	    "initComplete": function(settings){
	    	handleCheckRow(table);
 	    	settings.aoHeader[0].forEach(function(e,i) {
 	    	    if($(e.cell).hasClass('no-sorting')) {
 	    	    	settings.aoColumns[i].bSortable = false;
 	    	    }
 	    	});
 	    	lastFilteredColumns[table.selector] = {};
	    	initComplete&&initComplete(settings)
	    },
	    "drawCallback": function(settings){
			$(".action-menu").off();
			$(".action-menu ul").hide();
			$(".action-menu")&&$(".action-menu").on('click', function(e) {
				var crrEl = $(this).find('ul')[0];
				var isHidden = $(crrEl).is(':hidden');
				$(".action-menu ul").hide();
				if(isHidden) {
					$(crrEl).show();
				} else {
					$(crrEl).hide();
				}
				return false;
			});
	    	drawCallback&&drawCallback(settings);
	    },
	    "pageLength": 5,
	    columns: columns,
	    data:data,
	    rowsGroup: rowsGroup?rowsGroup:[]
	 });
}

function filterDataByColumns(columns, list) {
	var columnsData = columns.map(function(e,index) {
	    return e.data
	});
	return list.map(function(e,index) {
	    var obj = {}
	    for (var [key, value] of Object.entries(e)) {
			if( columnsData.includes(key))
	             obj[key] = value?value:''
		}
	    return obj;
	});
} 

function handleCheckRow(table) {
	
	if(!$.fn.dataTable.isDataTable(table)) {
		console.log('/cms/js/datatable-custom.js - handleCheckRow Error: Table '+table+' is not datatable');
		return;
	}
	
	$(table.selector+'_wrapper').on('change',  'thead th:first input:first', function () {
		var isCheckAll = this.checked;
		table.DataTable().rows().settings()[0].aoData.map(function(e) {
			if(!$(e.anCells[0].children[0]).is("[disabled]")) {
				e.anCells[0].children[0].checked = isCheckAll;
			}
    	})
	});
	
	$(table.selector+'_wrapper').on('change', 'tbody td[tabindex="0"] input', function () {
		var checkedRows = table.DataTable().rows().settings()[0].aoData.filter(function(e) {
			return e.anCells[0].children[0].checked == true;
	    }).length;
	    var allRows = table.DataTable().rows().data().length;
		var disabledRows = table.DataTable().rows().settings()[0].aoData.filter(function(e) {
			return $(e.anCells[0].children[0]).is("[disabled]");
	    }).length;
		allRows = allRows - disabledRows;
	    table.DataTable().rows().settings()[0].aoHeader[0][0].cell.children[0].checked = (checkedRows == allRows)
	});
	
	$(table.selector+'_wrapper input[type="checkbox"]').each(function(e) {
		$(this).css('cursor', 'pointer');
	});
}

function getCheckedKeys(table) {
	if(!$.fn.dataTable.isDataTable(table))
		return [];
	
	return table.DataTable().rows().settings()[0].aoData.filter(function(e) {
		return e.anCells[0].children[0].checked == true;
    }).map(function(e) {
    	return e.anCells[0].children[0].value
    });
}
	
function getAllKeys(table) {
	if(!$.fn.dataTable.isDataTable(table))
		return [];
	return table.DataTable().rows().settings()[0].aoData.map((e) => {return e.anCells[0].children[0].value });
}

function getColumnRows(table, column) {
	if(!$.fn.dataTable.isDataTable(table))
		return [];
	
	var columns = table.DataTable().rows().data().filter(function(e) {
		 for (var [key, value] of Object.entries(e)) {
				return key == column;
		}
	});
	return columns.lenght<1?[]:columns.map(function(e) {
	    var obj = {};
	    for (var [key, value] of Object.entries(e)) {
	         if(key == column) {
			    if(isHTML(value)) {
			       value = getValueOfHtmlStr(value);
			    }
	            obj[key] = value;
	            return obj;
	         }
		}
	})
}

function getDataRows(table) {
	if(!$.fn.dataTable.isDataTable(table))
		return [];
	return table.DataTable().rows().data().map(function(e) {
	    for (var [key, value] of Object.entries(e)) {
	        if(isHTML(value)) {
	            value = getValueOfHtmlStr(value);
	            e[key] = value;
	        }
	    }
	    return e;
	});
}

function getCheckedRows(table,columnKey) {
	if(!$.fn.dataTable.isDataTable(table))
		return [];
	var checkedKeys = getCheckedKeys(table);
	return getDataRows(table).filter(function(e) {
		for (var [key, value] of Object.entries(e)) {
	         return key == columnKey && checkedKeys.includes(value);
		}
	})
}

function loadRowsData(table,rows) {
	var datatable = table.DataTable();
	datatable.clear();
	datatable.rows.add(rows).draw();
}

function filterDataByEvent(element, event, table, columnsData) {
	element&&element.on(event, function(e) {
		var textFilter = this.value;
		textFilter = textFilter.toString().trim();
		
		var columnArr = [];
		columnsData.forEach(t=> {
			var column = t.toString();
			var columnObj = {};
			columnObj[column] = textFilter;
			columnArr.push(columnObj);
		});
		lastFilteredColumns[table.selector][element.selector] = columnArr;
		filterByColumns(table);
	});
}

function filterData(element, table, columnsData) {
	element&&element.on("change keyup copy paste cut", function(e) {
		var textFilter = this.value;
		textFilter = textFilter.toString().trim();
		
		var columnArr = [];
		columnsData.forEach(t=> {
			var column = t.toString();
			var columnObj = {};
			columnObj[column] = textFilter;
			columnArr.push(columnObj);
		});
		lastFilteredColumns[table.selector][element.selector] = columnArr;
		filterByColumns(table);
	});
}

function filterByColumns(table) {
	if(!table || !table.is( "table" )) {
		console.log('/cms/js/datatable-custom.js - filterData : '+table+' incorrect!');
	}
	filterDataByLastFilteredCoulmns(table);
}

function filterDataByLastFilteredCoulmns(table) {
	var allDataRows = dataTables[table.selector];
	var dataFilter = allDataRows?allDataRows.filter(function(e) {
		var elements = lastFilteredColumns[table.selector];
		if(jQuery.isEmptyObject(elements))
			return true;
		
		var isExistsRow = false;
		for (var element in elements) {
		  
		  if (elements.hasOwnProperty(element)) {
			  	
			  	// handle search OR by element
			    var isEleExistsRow = false;
			  	var columnArr = elements[element];
			  	for(var i=0;i<columnArr.length;i++) {
			  		
			  		var column = Object.keys(columnArr[i])[0];
					var text = Object.values(columnArr[i])[0];
					if(text.toString().trim() == '') {
						isEleExistsRow = true;
						break;
					}
					text = text.toString().toLowerCase();
					var cellValue = e[column]?e[column].toString().toLowerCase():'';
					if(cellValue.includes(text)) {
						isEleExistsRow = true;
					}
			  	}
			  	
			  	// handle search AND to another element
			  	if(!isEleExistsRow)
			  		return false;
			  	isExistsRow = true; 
		   }
		}

		return isExistsRow;
	}):[];
	loadRowsData(table,dataFilter);
	return dataFilter;
}

// sortFlag = 1 > newest, sortFlag = 0 > latest  
function sortSingleDate(table,dateColumn, sortFlag) {
	
	var allRows = filterDataByLastFilteredCoulmns(table);
	if(sortFlag === "") {
		loadRowsData(table,allRows);
		return;
	}
	if(sortFlag != 1 && sortFlag != 0) {
		console.log('/cms/js/datatable-custom.js - sortSingleDate : sortFlag incorrect!');
		return;
	}
	
	var sortRows = allRows.sort(function(a, b) {
	  if(a[dateColumn] && b[dateColumn]) {
		 var keyA = new Date(a[dateColumn]);
		 var keyB = new Date(b[dateColumn]);
		 // Compare the 2 rows
		 if(sortFlag === 0) {
			if (keyA < keyB) return -1;
		 	if (keyA > keyB) return 1;
		 } else if(sortFlag === 1) {
			if (keyA < keyB) return 1;
		 	if (keyA > keyB) return -1;
		}
	  }
	  return 0;
	});
	
	loadRowsData(table,sortRows);
}
