@Authors
Vinit Patel, Vraj Patel, Vatsal Patel, Usama Tariq

Flightopedia

IMPORTANT NOTE: IF YOU ARE HAVING PROBLEMS RUNNING THE APPLICATION THROUGH ANRDOID STUDIO, WE HAVE PROVIDED AN APK. PLEASE DRAG AND DROP THIS ONTO THE EMULATOR TO HAVE IT RUNNING.

Opening the app for the first time:
- Give permission to the application to access device storage by going to settings->storage->apps->Flightopedia->Permissions->Toggle Storage ON
- Login for admin is username: admin, password: password

(WARNING: All inputs must be in the same format as written in the text files)

Uploading Client and Flight Information:
- The command for uploading client and flight text files: adb push "filename.txt" /storage/self/primary/Download (WARNING: Download without the s)

Using the App for Admin:
- The admin is presented with 4 tabs (clients, settings, flights, edit flights)
- clients tab: clients are displayed in alphabetical order (according to e-mail), click on a client to edit client information, view bookings, and book flights on behalf of that client
- settings tab: upload client and flight text files
- flights tab: Admin can only view direct flights (Direct Flights checkbox), view itineraries (Multiple Flight checkbox), view by decreasing cost, and view by decreasing time. (NOTE: only one tab can be 
selected at a time)
- edit flights tab: Admin can view and edit all flight information, uploaded into the application

Using the App for Client:
- The client is presented with 3 tabs (account, bookings, flights)
- account tab: view and edit Client information
- bookings tab: view itineraries that the client has booked
- flights tab: view and book flights uploaded to the application

Enhancements:
-> Android SQLite:
    - implemented the Android version of SQLite DATABASE (implementation can be found in the database, client and flights folders) to store, manipulate, and read data in an efficient manner

-> Visually Appealing Desgin:
    - Added a layout which has an appealing look and adds a unique identity to the app
    - seamless transition between activites, horizontally or vertically

-> Good UI Design:
    - Aestheically pleasing colour scheme used throughout the app
    - Sliding tabs that allow users to navigate between different screens allowing for a smooth user experience
    - Supports both portrait and landscape screen layouts 
    - Seamless scrolling so nothing is out of context



 

