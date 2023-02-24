Spring Boot Application for Uploading, Viewing, and Deleting Images with Imgur API
This is a sample Spring Boot application that demonstrates how to use the Imgur API to upload, view, and delete images after authorizing the user's username and password.

Getting Started
To get started with this application, you will need an Imgur account and an API key. You can create an account and obtain an API key from the Imgur website.

Once you have your API key, you will need to set it as an environment variable in your local development environment. You can do this by adding the following line to your ~/.bashrc or ~/.zshrc file:

bash
Copy code
export IMGUR_API_KEY=YOUR_API_KEY_HERE
Next, clone this repository to your local machine and navigate to the project directory.

bash
Copy code
git clone https://github.com/your_username/imgur-api-demo.git
cd imgur-api-demo
Running the Application
To run the application, you will need to use a command line interface (CLI) such as Terminal or Command Prompt.

First, you will need to build the application using Maven. From the project directory, run the following command:

bash
Copy code
mvn clean install
This will compile the application and download any required dependencies.

Next, you can run the application using the following command:

bash
Copy code
mvn spring-boot:run
This will start the application on http://localhost:8080.

Using the Application
Authentication
Before you can use the application to upload, view, or delete images, you will need to authenticate using your Imgur account credentials. To do this, you can send a POST request to the /login endpoint with your username and password in the request body as follows:

http
Copy code
POST http://localhost:8080/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
If your credentials are valid, the API will respond with a JSON object containing an access token that you can use to authenticate subsequent requests:

json
Copy code
{
  "access_token": "YOUR_ACCESS_TOKEN_HERE"
}
Make sure to save this access token as you will need it for subsequent requests.

Uploading Images
To upload an image to Imgur, you can send a POST request to the /images endpoint with the image file and access token in the request body as follows:

http
Copy code
POST http://localhost:8080/images
Content-Type: multipart/form-data
Authorization: Bearer YOUR_ACCESS_TOKEN_HERE

--boundary
Content-Disposition: form-data; name="file"; filename="image.jpg"
Content-Type: image/jpeg

[RAW IMAGE DATA HERE]

--boundary--
If the upload is successful, the API will respond with a JSON object containing metadata about the uploaded image, including the image URL, deletion hash, and other information:

json
Copy code
{
  "success": true,
  "status": 200,
  "data": {
    "id": "IMAGE_ID_HERE",
    "title": null,
    "description": null,
    "type": "image/jpeg",
    "link": "https://i.imgur.com/IMAGE_ID_HERE.jpg",
    "deletehash": "DELETE_HASH_HERE",
    "width": 1920,
    "height": 1080
  }
}