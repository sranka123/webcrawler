1. This project crawls any web URL specified and find all links used on that website.

2. you can run junit  test program  SunilWebCrawlerTest.java in test folder. 
It uses this URL private static String URL = "https://wiprodigital.com";
it will print all details log while iteration and later it will print consolidated data detail as below.
------------------------------------- 
	Final Consolidated Data is as below:
	
 ------------------------------------- 

Total Wipro Digital links count: 106

All Wipro Digital links: [https://wiprodigital.com/tag/bots/,......]

Total Non-Wipro Digital links count:  226
All Non-Wipro Digital links: [http://twitter.com/share?text=Wipro.........]

Total Wipro Digital image links count: 87
All Wipro Digital image links: [https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo.png,....]


3. we have kept maxAllowedDepth = 3 for iterating pages upto three level. we have to break the execution at certain depth else program
 will run for long time and might go  crazy.
 
4.  we added below  check in code , that  will take care to not get into cyclic references , e.g. Page A has links for B , and B has for C, 
  and C has for D and then D has links for A.. then is is circular reference.
           if ((!linkSet.contains(URL) 
           
5. As of now it is ignoring external sites other than wiprodigital.com, if external sites are also required to be navigated 
  then we should create FixedThreadPool using Executors and for each external link assign it to a worker Thread for parallel processing.
 
