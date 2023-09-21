import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private String name;
    private ArrayList<Item> items;
    public static final int maxInv = 5; 
    private int invWeight;
    private List<String> msg;
    /**
     * Constructor for objects of class Player
     */
    public Player(String n)
    {
        name = n;
        invWeight = 0;
        items = new ArrayList<>();
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room cR)
    {
        cR = currentRoom;
    }

    public String getName()
    {
        return name;
    }

    public int getInventoryWeight()
    {
        return invWeight;
    }

    public void takeItem(Item it)
    {
        items.add(it);
        invWeight += it.getWeight();
    }

    public void removeItem(Item it)
    {
        items.remove(it);
        invWeight -= it.getWeight();
    }
    //8.32
    public String playerInv()
    {
        String str = new String();
        String str1 = new String();
        String msg = new String();
        str1 = ("Inventory: ");
        for (Item i : items){
            msg = (i.getDescription());
        }
        str = "\nCurrent weight: " + invWeight + "lbs";
        return str1 += msg += str;
    }
}

