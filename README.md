# SOFTENG306Project1 - Team 6
Using AI and parallel processing power to solve difficult scheduling problems
  
| Github username | University user name |
|---|---|
| daltonlim | Dalton Lim (dlim779) | 
| magnusrand | Magnus Rand (cran263) |
| raghavgarg32 | Raghav Garg (rgar969) |
| sabatr | Brian Nguyen (bugn877) |
| saddboys | Chuyang Chen (cche381) |


## Running the code
To run the application, type the following command:

NOTE 1: The .jar file has to be in the same repository as your current file path!
NOTE 2: The .dot file has to be a valid DAG graph.
```
java -jar scheduler.jar <INPUT.dot> <NUMBER OF PROCESSORS> <OPTIONAL ARGS>
```

An example:
```
java -jar scheduler.jar Nodes_10_Random.dot 2 -v
```

The optional arguments are as follows:


|  Argument | Description  |
|---|---|
|  `-v` | Visualises the output  |
|  `-p` &lt;number of cores> |  Determines the number of cores used for execution |
|  `-o` &lt;output file> |  Name the output file |
  
 ### CLI UI
 ![CLI](output.png)
 
 ### Visualisation
 ![Visualisation](visualisation.png)
  
## Build  
This project should be built with Gradle.  
Build this project as a runnable jar, include all dependencies within the jar.  

Additional information can be found in our wiki.
