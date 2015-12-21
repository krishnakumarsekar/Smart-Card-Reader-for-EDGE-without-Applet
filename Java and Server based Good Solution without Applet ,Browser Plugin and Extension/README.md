# Smart-Card-Reader-for-EDGE-without-Applet

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
          
