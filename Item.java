
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private int weight;
    
    public Item(String desc)
    {
        description = desc;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public int setWeight(int x)
    {
        weight = x;
        return weight;
    }
    }
