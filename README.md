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
          
