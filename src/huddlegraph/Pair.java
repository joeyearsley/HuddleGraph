/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huddlegraph;

/**
 * A custom object used to store a vertex and the distance to this vertex.
 * To be used in conjunction with a list and hash map to make a adjacency list.
 * @author joe yearsley
 */
class Pair<Character, Integer> {

    //first element
    private Character first;
    //second element
    private Integer second;

    /**
     * To allow outputting of the pair.
     * @return String representation of the pair
     */
    public String toString() {
        return first.toString() + second.toString();
    }

    /**
     * Simple object to store distance and vertex together.
     * @param first first value
     * @param second second value
     */
    public Pair(Character first, Integer second) {
        this.first = first;
        this.second = second;
    }

    /**
     * 
     * @return the first element in the pair
     */
    public Character getFirst() {
        return first;
    }

    /**
     * 
     * @param first to set the first element to.
     */
    public void setFirst(Character first) {
        this.first = first;
    }

    /**
     * 
     * @return The second element.
     */
    public Integer getSecond() {
        return second;
    }

    /**
     * 
     * @param second The second element to set.
     */
    public void setSecond(Integer second) {
        this.second = second;
    }
}
