# json-validation
How to run/use this application:
- navigate to the folder where the .jar file is stored (the file is called jsonvalidator-0.0.1-SNAPSHOT.jar), using your CLI
- execute the following command: java -jar jsonvalidator-0.0.1-SNAPSHOT.jar. After this, the server should start and you should see a message saying it is listening for connections on port 8080

  As a human user:
- using Postman or an equivalent, create a new POST request for this URL: http://localhost:8080/validate?schemaName={insert schema name of your choice}
- select "form-data" as the type of the request body
- add the following parts to the request body:
      - Key: jsonObject; type: Text; value: the JSON object of your choice, given as a character string
      - Key: schemaFile; type: File; value: the schema file you choose to upload
- hit "Send"

  As a client java application: since the API uses MultipartFile objects, in order to be able to use it, you need to convert your File object to a MultipartFile implementation, and then perform the HTTP POST request using the MultipartFile obtained and a JSON string as the request parts.

  You can use the following example for testing the application:
  JSON object:
{  
  "name": "Emma Watson",  
  "artist": "Paul Walker",  
  "description": null,  
  "tags": ["oil", "famous"]  
}  

JSON schema: 
{
	"$schema": "https://json-schema.org/draft/2019-09/schema#",
	"$id+": "http://my-paintings-api.com/schemas/painting-schema.json",
	"type": "object",
	"title": "Painting",
	"description": "Painting information",
	"additionalProperties": true,
	"required": ["name", "artist", "description", "tags"],
	"properties": {
		"name": {
			"type": "string",
			"description": "Painting name"
		},
		"artist": {
			"type": "string",
			"maxLength": 50,
			"description": "Name of the artist"
		},
		"description": {
			"type": ["string", "null"],
			"description": "Painting description"
		},
		"tags": {
			"type": "array",
			"items": { "$ref": "#/$defs/tag" }
		}
	},
	"$defs": {
		"tag": {
			"type": "string",
			"enum": ["oil", "watercolor", "digital", "famous"]
		}
	}
}
