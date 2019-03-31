# webCrawler

Steps to configure the application:
1. Clone the project.
2. Import the project in Eclipse.
3. Make sure that the liberies (2 jars) are included in the project.
4. http://www.jsonschema2pojo.org/ is used to generate the Java Object from the Json.
5. Google GSON parser is used. Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
Fore more info, visit https://sites.google.com/site/gson/gson-user-guide


Steps to run the application:
1. This is simple Java application with console input and console output.
2. Eclipse Console editor can be used. 
3. There are two approaches - (i) Recursion and (ii) Multi Thread.
By default recursion is ON.
4. The boolean variable is isMultiThreaded in WebCrawlerMain.java
5. On run the application.  Enter file names in useCases folder.
Enter input file name with relative path : <internet_1.json>
Note : In multithread, sleep() are used two times. So need to wait the output in hte console.

For any new input file with same json structure, useCases folder can be used.
TBD: For Thread approach, changes required with wait/notify and synchronized to avoid sleep() to make it inter thread communication. 




