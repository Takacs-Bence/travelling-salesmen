# travelling-salesmen
Application to plan your holiday and help prioritise stops in between.

### Project status
First step is to get familiar with the shortest-path algorithm and implement one.

ShortPathApp a simple Java SE console application, that parses the input.txt from the resources directory <br>
and finds the shortest path from the first city to the last.

schema for the input file:

```
/start-cities
4
Seattle
Portland
Vancouver
Spokane

/start-connections
6
Seattle    Portland    173
Seattle    Vancouver   142
Seattle    Spokane     279
Portland   Vancouver   313
Portland   Spokane     509
Vancouver  Spokane     348
```

must contain first the "/start-cities" constant exactly and then the number of cities coming <br>
then similarly the "/start-connections" constant exactly then the number of connections coming. <br>
a connection contains three data parts grouped by one or more white-spaces. these are: 1. city A 2. city B 3. distance <br>

the connections are undirected at this point <br>

the app finds the shortest path (with Dijkstra's algorithm) from starting point to destination and prints it to the console.

TODO: currently only the parsing and building the graph is implemented. not the algo itself.