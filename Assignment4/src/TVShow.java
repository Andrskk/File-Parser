import java.util.Scanner;
/**
 * TVShow class contains attributes, setters/getters, toString and equals methods.
 */
public class TVShow implements Watchable {

    //Instance variables initialization
    private String id;
    private String name;
    private double startTime;
    private double endTime;

    //Parameterized constructor
    public TVShow(String ID, String name, double startTime, double endTime)
    {
        this.id = ID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Copy constructor
    public TVShow(TVShow anotherTVShow, String str)
    {
        this.name=anotherTVShow.getName();
        this.startTime=anotherTVShow.getStartTime();
        this.endTime=anotherTVShow.getEndTime();
        this.id=str;
    }

    //setters and getters
    public String getID()
    {
        return id;
    }

    public void setID(String ID)
    {
        this.id = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

    /**
     * Method to display a TVShow object information.
     * @return
     */
    @Override
    public String toString()
    {
        return "TVShow{" + "ID='" + id + '\'' + ", name='" + name + '\'' + ", startTime=" + startTime + ", endTime="
                + endTime + '}';
    }

    /**
     * Method to compare two objects of TVShow type.
     * @param anotherObject Receives a parameter of class Object, then cast it to TVShow type. Compare it with "this"
     * object parameters.
     * @return false, if passing object is null or has a different class type, return true, if all object attributes are
     * equal
     */
    public boolean equals(Object anotherObject)
    {
        if(anotherObject!=null&&getClass()==anotherObject.getClass())
        {
            TVShow anotherTVShow = (TVShow) anotherObject;
            return this.name.equals(anotherTVShow.getName())&&this.startTime==anotherTVShow.getStartTime()
                    &&this.endTime==anotherTVShow.getEndTime();
        }
        else return false;
    }


    /**
     * Method to clone a TVShow object. Prompt user to enter a new showID, then creates and returns a clone
     * of the calling object with the exception of the showID, which is assigned the value entered by the user.
     * @return a clone of the calling object with the exception of the showID
     */
    public TVShow clone()
    {
        System.out.println("Please enter an ID of TV show being cloned");
        Scanner scanner = new Scanner(System.in);
        String userInput=scanner.next();
        TVShow clonedTVShow = new TVShow(userInput,getName(),getStartTime(),getEndTime());
        return clonedTVShow;
    }

    /**
     * Method to compare two TVSHow objects by time. Returns true if the is some time overlap or TVShows have the same time,
     * false otherwise.
     * @param S Variable of TVShow type.
     * @return
     */
    public boolean isOnSameTime(TVShow S)
    {
        if (this.startTime<S.getStartTime())
        {
            if (this.endTime<=S.getStartTime())return false;
            else return true;
        }
        else if (this.startTime==S.getStartTime())
        {
            if (this.endTime==S.getEndTime())return true;
            else return true;
        }
        else if (this.startTime>S.getStartTime())
            {
                if (this.startTime<S.getEndTime())return true;
                else return false;
            }
            else return false;
    }
}
