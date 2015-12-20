# Smart-Card-Reader-for-EDGE-without-Applet

###### As Chrome and Firefox going to stop the NPAPI Plugin Support and EDGE Having no plugin support, A unique solution with user friendly support is required to access the smart card for Secure Enterprise application
 
###### This project is having list of Solution and its implementation for secure application.
###### As its Not Matured , Its Not Suitable for Production Environment

#### 1. Project Residing Under Java and Server based Good Solution without Applet ,Browser Plugin and Extension

###### Unique Secure Solution to support all browsers without applet ,Extension or Plugin for Angular,Backbone JS Based Web Application

###### This project is not matured not tested properly, used several code snippets from forums ,Not suitable for production, Idea Might be useful 

###### Project Usage

        1. How to use
            - Deploy the SCARDREADER.EAR in a secure server (will be converted to JAR in future version)
            - Put the SCARDREADER.db sqlite file under D directory ,Will Convert to deploy inside the server in future release 
            - Load index-SCARDREADER.html dynamically in the area of the page you want using angular or Backbone JS
              or
            - Add the content in index-SCARDREADER.html in your web page
            - Launch the web page in EDGE
            - Enjoy :)
        
        2. Requirements
          - Currently support only for windows 7 to 10 (Mac support will be release soon)
          - Support Latest Chrome,EDGE,IE > 10,Firefox,Opera 
          - Java 1.6 >
        
        3. Need to adapt
          - For using outside localhost ,proper ssl handling mechanism have to add in the main java file under SCARDREADER
          
        4. Conclusion
          - This project designed for localhost ,Tested in windows 10 but suitable for an enterprise,Using this project is at own risk

        5. Advantages and Disadvantages
          - Support for all browsers
          - No installation problem
          - No dependency with any browser
          - All datas are secure and there is no client interaction in the data from the card
          - No one can able to send data without card and card reader(Because the communication is from client app to server),More                Security can be provided by using card certificate
          - NO CSRF or Any cookies issue
          - Support in httpOnly cookie
          - The one disadvantage is design is complex
          
#### 2. Project using Https Server in client side using Java web server

###### A simple Https Server in the client implementation is represented here to communicate a https enterprise domain with the smart card reader
###### Secure Enterprise communication support for all browsers

###### Unique Secure Solution to support all browsers without applet ,Extension or Plugin for Angular,Backbone JS Based Web Application

###### This project is not matured not tested properly, used several code snippets from forums ,Please clean it up for production or Idea Might be useful 

###### Project Usage

        1. How to use
            - Install the SCARDREADER-installer.exe or put the resource folder under your server
            - Load index-SCARDREADER.html dynamically in the area of the page you want using angular or Backbone JS
              or
            - Add the content in index-SCARDREADER.html in your web page
            - Launch the web page in EDGE or any browser
            - Enjoy :)
        
        2. Requirements
          - Currently support only for windows 7 to 10 (Mac support will be release soon)
          - Support Latest Chrome,EDGE,IE > 10,Firefox,Opera 
          - Java 1.6 >
        
        3. Need to adapt
          - For using outside localhost ,proper ssl handling mechanism have to add in the main java file with proper key
          
        4. Conclusion
          - This project designed for localhost ,Tested in windows 10 but suitable for an enterprise,Using this project is at own risk
        
        5. Advantages and Disadvantages
          - Support for all browsers
          - Several Antiviruse problem may come
          - Anyone can give a response without card reader and card by simply hosting a server in port 8001(No Solution available so              far)
          - All datas are directly communicated in the client machine ,Client or end user can alter the data if its needs in server               without any change
          - CSRF issues Supported and gives secure csrf communication
          - A separate secure cookies and headers can be pass from main domain to the client server for more security
          - Dont support httpOnly cookie
          
