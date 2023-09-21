import java.util.HashMap;
import java.util.Set;
import java.util.Map;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;
    private String imageName;
    private String audioName;
    //8.8
    private HashMap<String, Room> exits;
    private Map<String, Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();        //8.8
        items = new HashMap<>();
    }
    //8.29
     public void addItem(String key, Item it)
    {
        items.put(key, it);
    }

    public Item getItem(String description)
    {
        return items.get(description);
    }

    public void removeItem(String description)
    {
        items.remove(description);
    }
    
    public String getItemString()
    {
        String message = "";
        for(Item it : items.values()){
            message += "\nYou see " + it.getDescription();
        }
        return message;
    }

    public Room getRoom(Room room)
    {
        return room;
    }
    //8.8
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(Room north, Room east, Room south, Room west) 
    {
        if(north != null) {
            exits.put("north", north);
        }
        if(east != null) {
            exits.put("east", east);
        }
        if(south != null) {
            exits.put("south", south);
        }
        if(west != null) {
            exits.put("west", west);
        }
    }
    //8.8
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction
     */
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }
    //8.6
    /**
     * Return the exits the user can take
     * @return the exits the user can use.
     */
    public Room getExit(String direction){
        return exits.get(direction);
    }
    //8.7
    /**
     * Return a description of the room's exits
     * for example, "Exits: nort west".
     * @return A description of available exits.
     */
    public String getExitString(){
        //8.11
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;
    }
    //8.11
    /**
     * Return a long description of this room, of the form:
     *      You are in the kicthen.
     *      Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        return "You are " + description
        + ".\n " + getExitString()
        + getItemString();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /*************************************************************
     * added by William H. Hooper, 2006-11-28
     *************************************************************/
    /**
     * @return a String, which hopefully contains the file name
     * of an Image file.
     */
    public String getImage()
    {
        return imageName;
    }

    /**
     * associate an image with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format viewable in the Java AWT.
     * Readable formats include: 
     * PNG, JPG (RGB color scheme only), GIF
     */
    public void setImage(String filename)
    {
        imageName = "media/" + filename;
    }

    /**
     * @return a String, which hopefully contains the file name
     * of an audio file.
     */
    public String getAudio()
    {
        return audioName;
    }

    /**
     * associate an audio clip with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format palyable in the Java AWT.
     * Readable formats include: 
     * WAV, AU.
     */
    public void setAudio(String filename)
    {
        audioName = "media/" + filename;
    }
}
