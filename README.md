# json_nullify
Returning blank json object with only keys and no values in the same structure.
json_nullify will set the defaul values to key based on its data type.
eg) Say i have following json and i will like to nullify it

{
	"id": "201212088",
	"first-name": "Dipen",
	"last-name": "Rangwani",
	"age": 25,
	"score": 4.5,
	"single": true,
	"contact-no": [
		"0123456789",
		"0123456789"
	],
	"address": {
		"street": "street name",
		"landmark": "landmark name",
		"city": "city name"
	}
}

Response:

{
	"id": "",
	"first-name": "",
	"last-name": "",
	"age": 0,
	"score": 0.0,
	"single": false,
	"contact-no": [],
	"address": {
		"street": "",
		"landmark": "",
		"city": ""
	}
}
