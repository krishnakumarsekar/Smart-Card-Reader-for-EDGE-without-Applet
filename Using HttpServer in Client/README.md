# Smart-Card-Reader-for-EDGE-without-Applet

#### A simple Http Server in the client implementation is represented here to communicate a http enterprise domain with the smart card reader
#### Less Secure Enterprise communication support for all browsers

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
          - No need to adapt anything
          
        4. Conclusion
          - This project designed for localhost ,Tested in windows 10 but suitable for an enterprise,Using this project is at own risk
        
        5. Advantages and Disadvantages
          - No support for Https Domain(It's not possible)
          - Support for all browsers
          - Several Antiviruse problem may come
          - Anyone can give a response without card reader and card by simply hosting a server in port 8001
          - All datas are directly communicated in the client machine ,Client or end user can alter the data if its needs in server without any change
          - CSRF issues Supported and gives secure csrf communication
        6. Testing
          - Run http://localhost:8002/test  
          
 
