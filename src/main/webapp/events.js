
// When page is fully loaded we call the getTable() function
document.addEventListener("DOMContentLoaded", () => {

	getTable();
});


// This function extracts all the houses
function getTable() {
	// Create an XhrGet variable to handle GET requests
	var xhrGet = new XMLHttpRequest();

	xhrGet.onreadystatechange = function() {

		if (xhrGet.readyState == 4) {

			// We parse the response into objects and one at a time it invokes the addHouse() function
			JSON.parse(xhrGet.responseText).forEach(element => {
				addHouse(element);
			});
		}
		// this function also sets all the houses id's to hidden so they will not be accessed
		document.getElementById("housesTable").getElementsByTagName("tr")[0].getElementsByTagName("th")[0].setAttribute("class", "hidden");
		document.getElementById("mainTable").innerHTML = "Here are all the houses:";
	}
	xhrGet.open("GET", "firstServlet");
	xhrGet.send();
}



// The addHouse() function recieves a house and adds(using DOM manipulation) it to the allHousesBody 
// which is the table body for the main table
function addHouse(house) {

	// Create a row 
	var row = document.createElement("TR");
	// Create cells with all the house attributes
	var id = document.createElement("TD");
	var streetAddress = document.createElement("TD");
	var city = document.createElement("TD");
	var state = document.createElement("TD");
	var country = document.createElement("TD");
	var postalCode = document.createElement("TD");
	var sqft = document.createElement("TD");
	var estPrice = document.createElement("TD");
	var numberBedRooms = document.createElement("TD");
	var numberBathRooms = document.createElement("TD");
	var forSale = document.createElement("TD");
	var typeOfProperty = document.createElement("TD");

	// For each cell made assign them the value from the house passed to the function
	id.innerText = house.houseid;
	streetAddress.innerText = house.streetAddress;
	city.innerText = house.city;
	state.innerText = house.state;
	country.innerText = house.country;
	postalCode.innerText = house.postalCode;
	sqft.innerText = house.sqft;
	estPrice.innerText = house.estPrice;
	numberBedRooms.innerText = house.numberBedRooms;
	numberBathRooms.innerText = house.numberBathRooms;
	typeOfProperty.innerText = house.typeOfProperty;

	// If the house is for sale we would like to display "Yes/No" rather than "true/false" or "1/0"
	if (house.forSale == false) {
		forSale.innerText = "No";
		forSale.value = "No";
	} else {
		forSale.innerText = "Yes";
		forSale.value = "Yes";
	};

	// Assign to the row we created all the cells with the houses attribute
	row.appendChild(id);
	row.appendChild(streetAddress);
	row.appendChild(city);
	row.appendChild(state);
	row.appendChild(country);
	row.appendChild(postalCode);
	row.appendChild(sqft);
	row.appendChild(estPrice);
	row.appendChild(numberBedRooms);
	row.appendChild(numberBathRooms);
	row.appendChild(forSale);
	row.appendChild(typeOfProperty);

	// We give the row a class "clickable" and a function to be invoked when the row is clicked
	row.class = "clickable";
	row.onclick = function() { sendDataToOption(row) }

	// Set the first cell(houseID) to be hidden from the user
	row.getElementsByTagName("td")[0].setAttribute("class", "hidden");

	// Append the row tp the table body
	document.getElementById("allHousesBody").appendChild(row);
}



// Declare a variable that will be used from this point on in the script
var houseFromForm;
var houseDetail;
var tempRow;


// Assign to the variable formTemp to check that the form is fully rendered and not null
var formTemp = document.getElementById("house");
if (formTemp) {
	// Add an event Listener for the 'submit' event
	formTemp.addEventListener('submit', function(event) {

		event.preventDefault();

		// retrieve all the properties of the house that the user wants to add to the DB and put them in variables
		var streetAddress = document.getElementById("address").value;
		var city = document.getElementById("city").value;
		var state = document.getElementById("state").value;
		var country = document.getElementById("country").value;
		var postalCode = document.getElementById("postal").value;
		var sqft = document.getElementById("sqft").value;
		var estPrice = document.getElementById("price").value;
		var numberBedRooms = document.getElementById("numberBedRooms").value;
		var numberBathRooms = document.getElementById("numberBathRooms").value;
		// If the forSale checkbox is checked than we assign a 'Yes' else we assign 'No'
		if (document.getElementById("forsale").checked) {
			var forSale = "Yes";
		} else {
			var forSale = "No";
		}
		var typeOfProperty = document.getElementById("typeOfProperty").value;

		// We create a house using all of the properties from the form
		houseFromForm =
		{
			streetAddress: streetAddress,
			city: city,
			state: state,
			country: country,
			postalCode: postalCode,
			sqft: sqft,
			estPrice: estPrice,
			numberBedRooms: numberBedRooms,
			numberBathRooms: numberBathRooms,
			forSale: (forSale == "Yes") ? true : false,
			typeOfProperty: typeOfProperty
		}
		// Debug print // console.log(houseFromForm);

		// Create an XhrPost variable to handle POST requests
		var xhrPost = new XMLHttpRequest();

		xhrPost.onreadystatechange = function() {

			if (xhrPost.readyState === 4) {
				// If we weren't able to save the house(doesn't matter why) we alert the user and do not add the house to the table
				if (xhrPost.responseText == 'null') {
					document.getElementById('house').reset();
					alert("The house you submited already exists, try updating it instead");
				} else {
					// Debug print // console.log(JSON.parse(xhrPost.responseText));
					// If we were able to save the house then we parse it into a object and add it to the table for the user
					var newHouse = JSON.parse(xhrPost.responseText);
					addHouse(newHouse);
					document.getElementById('house').reset();
				}
			}
		}


		xhrPost.open('POST', 'firstServlet');


		xhrPost.send(JSON.stringify(houseFromForm));
	});
}





// This function send the row we clicked on in the main table to the House Details form
function sendDataToOption(row) {
	// Asign the values of the clicked row to the forms elements
	document.getElementById("IdOption").value = row.getElementsByTagName("td")[0].innerText;
	document.getElementById("addressOption").value = row.getElementsByTagName("td")[1].innerText;
	document.getElementById("cityOption").value = row.getElementsByTagName("td")[2].innerText;
	document.getElementById("stateOption").value = row.getElementsByTagName("td")[3].innerText;
	document.getElementById("countryOption").value = row.getElementsByTagName("td")[4].innerText;
	document.getElementById("postalOption").value = row.getElementsByTagName("td")[5].innerText;
	document.getElementById("sqftOption").value = row.getElementsByTagName("td")[6].innerText;
	document.getElementById("priceOption").value = row.getElementsByTagName("td")[7].innerText;
	document.getElementById("numberBedRoomsOption").value = row.getElementsByTagName("td")[8].innerText;
	document.getElementById("numberBathRoomsOption").value = row.getElementsByTagName("td")[9].innerText;
	var forSale = row.getElementsByTagName("td")[10].innerText;
	if (forSale == "Yes") {
		document.getElementById("forSaleOption").checked = true;
		document.getElementById("forSaleOption").value = "Yes";
	} else {
		document.getElementById("forSaleOption").checked = false;
		document.getElementById("forSaleOption").value = "No";
	}
	document.getElementById("typeOfPropertyOption").value = row.getElementsByTagName("td")[11].innerText;

	// Save the clicked row in a global variable declared before
	tempRow = row;
	// Assign all the clicked houses porperties to the global houseDetail object 
	houseDetail =
	{
		houseid: document.getElementById("IdOption").value,
		streetAddress: document.getElementById("addressOption").value,
		city: document.getElementById("cityOption").value,
		state: document.getElementById("stateOption").value,
		country: document.getElementById("countryOption").value,
		postalCode: document.getElementById("postalOption").value,
		sqft: document.getElementById("sqftOption").value,
		estPrice: document.getElementById("priceOption").value,
		numberBedRooms: document.getElementById("numberBedRoomsOption").value,
		numberBathRooms: document.getElementById("numberBathRoomsOption").value,
		forSale: (document.getElementById("forSaleOption").value == "Yes") ? true : false,
		typeOfProperty: document.getElementById("typeOfPropertyOption").value,
	}
}

// Take the saved row that was clicked and give it the updated values from the house object that was passed to this function
// Essentially this will be the last step of our update process(assuming it was successful)
function updateRow(house) {
	tempRow.getElementsByTagName("td")[0].innerText = house.houseid;
	tempRow.getElementsByTagName("td")[1].innerText = house.streetAddress;
	tempRow.getElementsByTagName("td")[2].innerText = house.city;
	tempRow.getElementsByTagName("td")[3].innerText = house.state;
	tempRow.getElementsByTagName("td")[4].innerText = house.country;
	tempRow.getElementsByTagName("td")[5].innerText = house.postalCode;
	tempRow.getElementsByTagName("td")[6].innerText = house.sqft;
	tempRow.getElementsByTagName("td")[7].innerText = house.estPrice;
	tempRow.getElementsByTagName("td")[8].innerText = house.numberBedRooms;
	tempRow.getElementsByTagName("td")[9].innerText = house.numberBathRooms;
	tempRow.getElementsByTagName("td")[11].innerText = house.typeOfProperty;

	if (house.forSale == false) {
		tempRow.getElementsByTagName("td")[10].innerText = "No";
		tempRow.getElementsByTagName("td")[10].value = "No";
	} else {
		tempRow.getElementsByTagName("td")[10].innerTextt = "Yes";
		tempRow.getElementsByTagName("td")[10].value = "Yes";
	};
}

// This function takes all values of a specific column and sorts them
function sortTable(n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById("housesTable");
	switching = true;

	dir = "asc";

	while (switching) {

		switching = false;
		rows = table.rows;

		for (i = 1; i < (rows.length - 1); i++) {

			shouldSwitch = false;

			x = rows[i].getElementsByTagName("TD")[n];
			y = rows[i + 1].getElementsByTagName("TD")[n];
			var cmpX = isNaN(parseInt(x.innerHTML)) ? x.innerHTML.toLowerCase() : parseInt(x.innerHTML);
			var cmpY = isNaN(parseInt(y.innerHTML)) ? y.innerHTML.toLowerCase() : parseInt(y.innerHTML);
			cmpX = (cmpX == '-') ? 0 : cmpX;
			cmpY = (cmpY == '-') ? 0 : cmpY;
			if (dir == "asc") {
				if (cmpX > cmpY) {

					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (cmpX < cmpY) {

					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {

			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;

			switchcount++;
		} else {

			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			}
		}
	}
}

// This update are global house variable that is equal to the house in the house Detail form
function updateHouseObj() {

	houseDetail =
	{
		houseid: document.getElementById("IdOption").value,
		streetAddress: document.getElementById("addressOption").value,
		city: document.getElementById("cityOption").value,
		state: document.getElementById("stateOption").value,
		country: document.getElementById("countryOption").value,
		postalCode: document.getElementById("postalOption").value,
		sqft: document.getElementById("sqftOption").value,
		estPrice: document.getElementById("priceOption").value,
		numberBedRooms: document.getElementById("numberBedRoomsOption").value,
		numberBathRooms: document.getElementById("numberBathRoomsOption").value,
		forSale: (document.getElementById("forSaleOption").value == "Yes") ? true : false,
		typeOfProperty: document.getElementById("typeOfPropertyOption").value,
	}
}

// This function is invoked when we click on the update button in the house detail form
function updateHouse() {
	// Call the function to update the house values
	updateHouseObj();

	// Create XhrUpdate to handle all of our PUT requests
	var xhrUpdate = new XMLHttpRequest();
	xhrUpdate.onreadystatechange = function() {

		if (xhrUpdate.readyState === 4) {
			// If we were unable to update the house alert the user
			if (xhrUpdate.responseText == 'null') {
				document.getElementById('houseOptions').reset();
				alert("The house you are trying to update does not exist please try again");
			} else {
				// Debug print // console.log(JSON.parse(xhrUpdate.responseText));
				// If we were able to update the house in the DB then we already 
				// have the house object that we passed to the servlet so we use it to update the table
				/* 
				Debug print
				var json = JSON.stringify(houseDetail);
				console.log(json);
				*/
				// Invoke this function to update the table row with the old house details
				updateRow(houseDetail);
				document.getElementById("houseOptions").reset();
			}
		}
	}

	xhrUpdate.open("PUT", "firstServlet");
	xhrUpdate.send(JSON.stringify(houseDetail));


}


// This function is onvoked when we click on the delete buttonin the house detail form
function deleteHouse() {
	// First we want the user to confirm that he will try to delte a house from the DB
	if (confirm('Are you sure you want to delete this house from the database?')) {

		var xhrDelete = new XMLHttpRequest();
		xhrDelete.onreadystatechange = function() {

			// Create XhrDelete to handle all of our DELETE requests
			if (xhrDelete.readyState === 4) {
				// If the request was fulfilled(it always is because it deletes a house that is recieved from our table) then log that the house was deleted
				console.log('House was deleted from the database.');
				/* 
				Debug print 
				var json = JSON.stringify(houseDetail);
				console.log(json);
				*/
				// Clear the form and delete the row that was clicked
				document.getElementById("houseOptions").reset();
				tempRow.remove();
			}
		}
		xhrDelete.open("DELETE", "firstServlet");
		xhrDelete.send(JSON.stringify(houseDetail));
	} else {
		console.log('Nothing was deleted');
	}
}

// Creat eventListener for our serchHouse element whenever a user clicks on the search submit "button"
document.getElementById("searchHouse").addEventListener('submit', function(event) {

	event.preventDefault();

	var attr = document.getElementById("attr").value;
	var operator = document.getElementById("operator").value;
	var searchValue = document.getElementById("searchValue").value;
	var flag = document.getElementById("flag").value;
	var params = 'attr=' + attr + '&operator=' + operator + '&searchValue=' + searchValue + '&flag=' + flag;

	var url = "searchServlet?" + params;


	var xhrSearch = new XMLHttpRequest();


	xhrSearch.onreadystatechange = function() {

		if (xhrSearch.readyState === 4) {
			$("#allHousesBody tr").remove();
			document.getElementById("mainTable").innerHTML = "These are the housees we found:";
			JSON.parse(xhrSearch.responseText).forEach(element => {
				addHouse(element);
			});
		}
	}


	xhrSearch.open('GET', url);

	xhrSearch.send();
});
// Variable used to let us know on the first click of our select the onchange event 
// (which invokes the checkAttr() function) not to remove anything 
var counter = 0;

// This function checks the value that was chosen in the attribute select element
// If the value is anyone of the attributes that can except string values then we 
// put only 2 operators in the next select dropdown becuse we can only compare if
// strings are equal or not equal.
// If the value is anyone of the attributes that can except only number values then 
// put 4 operators for comparing number values.
// If it is the forSale attribute then we only can display house that are or aren't for sale

function checkAttr() {
	// Retrieve the value of the attribute
	var attr = document.getElementById("attr").value;
	// Debug print // console.log(attr);
	// Check the value with a switch statement
	switch (attr) {

		// If the user chose the 'empty' option then reset the search form and the table back to the original
		case 'empty':
			for (let i = 0; i < counter; i++) {
				document.getElementById("searchValue").remove();
				document.getElementById("submitSearch").remove();
				document.getElementById("flag").remove();
				counter = 0;
			}
			$("#operator option").remove();
			$("#allHousesBody tr").remove();
			getTable();
			break;

		case 'streetAddress':
		case 'city':
		case 'state':
		case 'country':
		case 'postalCode':
		case 'typeOfProperty':
			$("#operator option").remove();

			for (let i = 0; i < counter; i++) {
				document.getElementById("searchValue").remove();
				document.getElementById("submitSearch").remove();
				document.getElementById("flag").remove();
				counter = 0;
			}

			var option1 = document.createElement("option");
			option1.setAttribute("id", "attr");
			option1.setAttribute("value", "equals");
			option1.innerHTML = '=';

			var option2 = document.createElement("option");
			option2.setAttribute("id", "operator");
			option2.setAttribute("value", "notEqual");
			option2.innerHTML = '!=';

			var input = document.createElement("input");
			input.setAttribute("class", "form-control");
			input.setAttribute("id", "searchValue");
			input.setAttribute("type", "text");
			input.setAttribute("value", "");

			var submitSearch = document.createElement("input");
			submitSearch.setAttribute("class", "btn btn-primary submit-button");
			submitSearch.setAttribute("id", "submitSearch");
			submitSearch.setAttribute("type", "submit");
			submitSearch.setAttribute("value", "search");

			var flag = document.createElement("input");
			flag.setAttribute("id", "flag");
			flag.setAttribute("value", "string");
			flag.setAttribute("style", "display: none;");

			document.getElementById("operator").appendChild(option1);
			document.getElementById("operator").appendChild(option2);
			document.getElementById("searchHouse").appendChild(input);
			document.getElementById("searchHouse").appendChild(submitSearch);
			document.getElementById("searchHouse").appendChild(flag);
			counter++;
			break;


		case 'sqft':
		case 'estPrice':
		case 'numberBedRooms':
		case 'numberBathRooms':

			$("#operator option").remove();

			for (let i = 0; i < counter; i++) {
				document.getElementById("searchValue").remove();
				document.getElementById("submitSearch").remove();
				document.getElementById("flag").remove();
				counter = 0;
			}

			var option1 = document.createElement("option");
			option1.setAttribute("value", "equals");
			option1.innerHTML = '=';

			var option2 = document.createElement("option");
			option2.setAttribute("value", "notEqual");
			option2.innerHTML = '!=';

			var option3 = document.createElement("option");
			option3.setAttribute("value", "lessThan");
			option3.innerHTML = '&lt';

			var option4 = document.createElement("option");
			option4.setAttribute("value", "greaterThan");
			option4.innerHTML = '&gt';

			var input = document.createElement("input");
			input.setAttribute("class", "form-control");
			input.setAttribute("id", "searchValue");
			input.setAttribute("type", "number");
			input.setAttribute("value", "");

			var submitSearch = document.createElement("input");
			submitSearch.setAttribute("class", "btn btn-primary submit-button");
			submitSearch.setAttribute("id", "submitSearch");
			submitSearch.setAttribute("type", "submit");
			submitSearch.setAttribute("value", "search");

			var flag = document.createElement("input");
			flag.setAttribute("id", "flag");
			flag.setAttribute("value", "number");
			flag.setAttribute("style", "display: none;");

			document.getElementById("operator").appendChild(option1);
			document.getElementById("operator").appendChild(option2);
			document.getElementById("operator").appendChild(option3);
			document.getElementById("operator").appendChild(option4);
			document.getElementById("searchHouse").appendChild(input);
			document.getElementById("searchHouse").appendChild(submitSearch);
			document.getElementById("searchHouse").appendChild(flag);
			counter++;
			break;


		case 'forSale':

			$("#operator option").remove();

			for (let i = 0; i < counter; i++) {
				document.getElementById("searchValue").remove();
				document.getElementById("submitSearch").remove();
				document.getElementById("flag").remove();
				counter = 0;
			}

			var forSaleSelect = document.createElement("select");
			forSaleSelect.setAttribute("id", "searchValue");

			var option1 = document.createElement("option");
			option1.setAttribute("value", "y");
			option1.innerHTML = "All houses for sale";

			var option2 = document.createElement("option");
			option2.setAttribute("value", "n");
			option2.innerHTML = 'All houses not for sale';

			var submitSearch = document.createElement("input");
			submitSearch.setAttribute("class", "btn btn-primary submit-button");
			submitSearch.setAttribute("id", "submitSearch");
			submitSearch.setAttribute("type", "submit");
			submitSearch.setAttribute("value", "search");

			var flag = document.createElement("input");
			flag.setAttribute("id", "flag");
			flag.setAttribute("value", "boolean");
			flag.setAttribute("style", "display: none;");


			document.getElementById("searchHouse").appendChild(forSaleSelect);
			document.getElementById("searchValue").appendChild(option1);
			document.getElementById("searchValue").appendChild(option2);

			document.getElementById("searchHouse").appendChild(submitSearch);
			document.getElementById("searchHouse").appendChild(flag);
			counter++;

			break;

	}
}


// Invoked when the refresh house table is clicked, this function restores the table to show all the current houses
function refreshHouse() {

	$("#allHousesBody tr").remove();
	document.getElementById("mainTable").innerHTML = "Here are all the houses:";
	getTable();
}

// Invoked when the user clicks the clear button on either form, this clears all data in the form
function clearForm(formID) {
	formID.reset();
}
//////////////////////////////// RAFI HIZGIAEV ///////////////////////////////