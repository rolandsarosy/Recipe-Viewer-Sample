Simple Recipe Viewer 
===================================

This app display a list of recipes from a specified endpoint.  
By clicking on any of the recipes, the user is brought to a detailed screen.  
The user also has the option to upload their own recipe by filling out a form.  
The POST and GET request endpoints are both proved by @FamilyFinances.  
Design guidelines and requirements were provided by them as well.  
The network calls are made with Retrofit 2 and the JSON parsing is done with GSON.  
Image loading is done with Glide for better perfomance and scaling options.  

Pre-requisites and build information
-------------------------------------

- Android SDK v27
- Android Build Tools v27
- Target SDK version: 27
- Min SDK version: 19 (4.4 KitKat)

Libraries
---------------
The following libraries are used in this project:

- Support V7 27.1.1.
- RecyclerView V7 27.1.1
- Support Design 27.1.1
- Constraintlayout 1.1.2
- Retrofit 2
- GSON
- Glide 4
- Cloudinary SDK

Feature requirements
---------------
- [X] Design and implement a list completed by the api endpoint above, each item should present the recipe’s name and image.
- [X] By clicking one of the receipts the application should present it’s details too (ingredients, steps).
- [X] Create a form where the application user can upload a new recipe. The user should be able to set the recipe’s name, ingredients, steps and image as well.  

*[Currently being implemented]* In the imgUrl field please upload an image chosen by the user from photo library or camera!

Notes
--------------
Please check the issue tracker for my planned (but not yet finished) improvements, tech debt, and feature implementation plans.

Getting Started
---------------
This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.
