# Webscraping_Presentation
Scrapes the same website in Python and in Java for the same data, implemented in different ways using different libraries

# Python
Python uses the Scrapy library, which does all the heavy lifting for me
Execute the file via a cmd by going into the webscraping directory and writing (in the cmd): scrapy crawl pokedex -O pokedex.csv
The additional parameter -O pokedex.csv writes the results in a csv file called pokedex (if the file already exists it is overwritten)

# Java
Java uses HtmlUnit, where I have to do much more myself and in general is more complicated because of the missing inferred types in Java
Execute the file by running Main.java in the src folder
