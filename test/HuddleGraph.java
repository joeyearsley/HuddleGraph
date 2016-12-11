/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;



    /**
     * A graph implementation as a adjacency list with helper classes
     * to find shortest path {@link #dijkstraDistance(java.lang.Character, java.lang.Character)}
     * , number of routes with certain amount of junctions {@link #MaxNoR(java.lang.Character, java.lang.Character, int, char)},
     * number of routes under a certain distance {@link #NoSR(java.lang.Character, java.lang.Character, int)}
     * and distance of a set route {@link #distance(java.lang.String)}.
     * @author josephyearsley
     */
    public class HuddleGraph {

        //running total used later, global as used in recursion.
        public int total = 0;
        //iteration for use in recursion later on.
        public int iteration;

        /**
         * String representation of graph.
         *
         */
        public String toString() {
            StringBuffer s = new StringBuffer();
            for (Character v : neighbourhood.keySet()) {
                s.append("\n    " + v + " -> " + neighbourhood.get(v).toString());
            }
            return s.toString();
        }
        /**
         * The implementation here is basically an adjacency list, but instead
         * of an array of lists, a Map is used to map each vertex to its list of
         * adjacent vertices and weighting of that edge. Making use of a custom
         * Pair object to store them.
         */
        private Map<Character, List<Pair<Character, Integer>>> neighbourhood = new HashMap<Character, List<Pair<Character, Integer>>>();

        /**
         * Add a vertex to the graph. Nothing happens if vertex is already in
         * graph.
         *
         * @param vertex a character to put into the hash map.
         */
        public void add(Character vertex) {
            if (contains(vertex)) {
                return;
            }
            neighbourhood.put(vertex, new ArrayList<Pair<Character, Integer>>());
        }

        /**
         * True iff graph contains vertex.
         *
         * @param vertex a character representing a vertex
         * @return true or false depending upon if its in the graph or not.
         */
        public boolean contains(Character vertex) {
            return neighbourhood.containsKey(vertex);
        }

        /**
         * Add an edge to the graph; if either vertex does not exist, it's
         * added. This implementation allows multiple edges and a self-edge. It
         * also takes the weight to put into the Pair object.
         *
         * @param from Vertex which the edge starts
         * @param to Vertex which the edge finishes
         * @param weight Integer for the weight of the edge.
         */
        public void add(Character from, Character to, Integer weight) {
            this.add(from);
            this.add(to);
            Pair<Character,Integer> pair = new Pair<>(to, weight);
            neighbourhood.get(from).add(pair);
        }

        /**
         * Works out distance of route by going through the list and seeing if
         * the second vertex is in the start vertexes list, if it's not then
         * return No Such Route, otherwise do it again for the next vertex in
         * the string and keep count of the distance.
         *
         * @param s String representation of route
         * @return either an integer or a No Such Route
         */
        public String distance(String s) {
            int total = 0;
            char x = s.charAt(0);
            //Node not in graph
            if(!contains(x)){return "NO SUCH ROUTE";}
            for (int i = 0; i < s.length() - 1; i++) {
                char y = s.charAt(i + 1);
                //Node not in graph
                if(!contains(y)){return "NO SUCH ROUTE";}
                List<Pair<Character, Integer>> neighbors = neighbourhood.get(x);
                //otherwise run through the graph
                for (Pair<Character, Integer> neighbor : neighbors){
                    int len = neighbors.size();
                    int temp = 0;
                    //equal to next vertex needed in route, add distance to running total
                    if (neighbor.getFirst().equals(y)) {
                        x = y;
                        total += neighbor.getSecond();
                    } else {
                        temp++;
                    }
                    //All vertexes available to initial vertex have been checked, no route.
                    if (temp == len) {
                        return "NO SUCH ROUTE";
                    }
                }
            }
            return Integer.toString(total);
        }
        
        /**
         * Works out the max number of routes given a start node and end node,
         * and number of junctions as well as type of check. So if type is 'e'
         * then has to have equal junctions, otherwise if 'm' then can be any
         * route upto iterx junctions. A custom limited depth first search,
         * returns how many times the end vertex appears for 'm', or only
         * returns number of end vertex at depth set in 'e'.
         *
         * @param s start vertex
         * @param e end vertex
         * @param iterx current iteration, equal to junctions
         * @param type equal or max junctions
         * @return Number of routes
         */
        public int MaxNoR(Character s, Character e, int iterx, char type) {
            //Alreay 'opened' up inital starting Node
            iterx--;
            /*
                Base case, nodes aren't in graph, or number of junctions are neg.
                Allow 0 junctions as can have loop.
            */
            if(!contains(s) || !contains(e) || iterx < 0){return 0;}
            //In graph find number of routes
            List<Pair<Character, Integer>> neighbors = neighbourhood.get(s);
            for (Pair<Character, Integer> neighbor : neighbors) {
                if (type == 'm') {
                    //While larger than 0
                    if (iterx > iteration) {
                        //if one of the end points we are looking for
                        if (neighbor.getFirst().equals(e)) {
                            total++;
                        }
                        MaxNoR(neighbor.getFirst(), e, iterx, type);
                        //Put the iteration back up or the recursion won't find all
                        iterx++;
                    }
                } else if (type == 'e') {
                    if (iterx > iteration) {
                        //Recursively find end points on the layer specified
                        MaxNoR(neighbor.getFirst(), e, iterx, type);
                    } else if (iterx == iteration && neighbor.getFirst().equals(e)) {
                        total++;
                    }
                }
            }
            return total;
        }

        /**
         * Implementation of Dijkstra's algorithm, which returns the shortest
         * distance to a vertex, where the vertex is a character. Stores all
         * distances, then returns only the distance of the vertex requested.
         * Could have made it to return the entire distance map, which then
         * could be stored in a variable for later calls, hence saving on
         * re-computation.
         *
         * @param start The start vertex
         * @param end The end vertex
         * @return The shortest distance to the end vertex
         *
         */
        public int dijkstraDistance(Character s, Character e) {
            //Base case, nodes aren't in graph, return large number for error
            if(!contains(s) || !contains(e)){return Integer.MAX_VALUE;}
            Map<Character, Integer> distance = new HashMap<>();
            // Initially, all distance are infinity, except start node
            for (Character v : neighbourhood.keySet()) {
                distance.put(v, Integer.MAX_VALUE);
            }
            distance.put(s, 0);
            // Process nodes in queue order
            Queue<Character> queue = new LinkedList<>();
            queue.offer(s);  // Place start node in queue
            while (!queue.isEmpty()) {
                Character v = queue.remove();
                int vDist = distance.get(v);
                // Update neighbourhood
                for (Pair<Character, Integer> neighbor : neighbourhood.get(v)) {
                    //check to see if new distance is smaller than old distance for vertex
                    if (vDist + neighbor.getSecond() < distance.get(neighbor.getFirst())) {
                        //put the new smallest distance to vertex into map
                        distance.put(neighbor.getFirst(), vDist + neighbor.getSecond());
                        //put the neighbor into the queue
                        queue.offer(neighbor.getFirst());
                    }
                }
            }
            /*
             *   Go through all vertexs now Dijkstra has finished.
             *   If that vertex has an edge to the end vertex, then add it if the 
             *   distance is 0, otherwise compare with the current shortest route to
             *   decide wether to add it or not.
             */
            if (s.equals(e)) {
                //make an iterator for the hashmap
                Iterator<Map.Entry<Character,Integer>> it = distance.entrySet().iterator();
                while (it.hasNext()) {
                    //store the next entry
                    Map.Entry<Character,Integer> m = it.next();
                    /* for all neighbors in neighbourhood, see if equal to end,
                     if so then check distance to see if its the lowest.
                     */
                    for (Pair<Character, Integer> neighbor : neighbourhood.get(m.getKey())) {
                        int newVal = Integer.parseInt(m.getValue().toString());
                        //No value yet so put first value in
                        if (distance.get(e) == 0) {
                            distance.put(e, neighbor.getSecond());
                            //Now has a value so check that the potential new one is lower
                        } else if (neighbor.getFirst().equals(e)
                                && (newVal < distance.get(e))) {
                            distance.put(e, newVal + neighbor.getSecond());
                        }
                    }
                }
            }
            //return the distance it takes to get to the end vertex
            return Integer.parseInt(distance.get(e).toString());
        }

        /**
         * Similar to MaxNoR, but changed for distance instead of depth.
         *
         * @param s start vertex
         * @param e end vertex
         * @param dist max distance
         * @return number of shortest routes
         * @see #MaxNoR(java.lang.Object, java.lang.Object, int, char)
         */
        public int NoSR(Character s, Character e, int dist) {
            /*
                Base case, nodes aren't in graph. Allow zero distance for loop to self
            */
            if(!contains(s) || !contains(e) || dist < 0){return 0;}
            //Alreay 'opened' up inital starting Node
            List<Pair<Character, Integer>> neighbors = neighbourhood.get(s);
            /*
             A take on Limited depth first search where I count all occurences of 
             the end node which have been visited under the maximum amount of 
             times.
             */
            for (Pair<Character, Integer> neighbor : neighbors) {
                //While larger than 0
                if (dist < iteration) {
                    //Add distance to get to this node.
                    dist += neighbor.getSecond();
                    //recursively pass on distance and find routes below.
                    NoSR(neighbor.getFirst(), e, dist);
                    //increase total as one route found.
                    if (neighbor.getFirst().equals(e) && iteration > dist && dist > 0) {
                        total++;
                    }
                    /* 
                     going back up to another parent recursion,
                     so reduce distance back to normal
                     */
                    dist -= neighbor.getSecond();
                }
            }
            return total;
        }
    }


