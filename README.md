### String processor App ###

Directions
Write a simple application that defines and runs 2 requests simultaneously, each request is defined below:

Every10thCharacterRequest:

Grab https://www.compass.com/about/ content from the web

Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen 

WordCounterRequest:

Grab https://www.compass.com/about/ content from the web

Split the text into words using whitespace characters (i.e. space, tab, line break, etc.), count the occurrence of every unique word (case insensitive) and display the count for each word on the screen 


Consider the content plain-text, regardless of what is returned by the response. Treat anything separated

by whitespace characters as a single word. Example:

"<p> Compass Hello World </p>" should produce +1 for each of these: "<p>", "Compass",

"Hello", "World", and "</p>".


The application should:

1. Show a single Button to run the two requests simultaneously

2. Show the results  single TextView of each request above as soon as the processing of the corresponding request finishes, displayed in views representing lists

3. Data should be cached and made available offline after the first fetch 

4. The code should be unit tested
https://github.com/SantiagoFlynnUTN/TestApp/assets/26491668/f660fe49-57a0-4641-ad29-2880589bc25a

Tech used:
- MultiModule Clean Arch
- MVI
- Flows
- Hilt
- Retrofit
- Room
- Compose
- Coroutines
- Unit Testing
- Material 3

Architecture:
![image](https://github.com/SantiagoFlynnUTN/TestApp/assets/26491668/dc62d21e-3d8f-4f38-a62e-91441d3d445f)

![image](https://github.com/SantiagoFlynnUTN/TestApp/assets/26491668/a6cfc317-40e5-4cf2-ba0c-41b8a1959ebe)


