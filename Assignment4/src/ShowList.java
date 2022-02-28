import java.util.ArrayList;
/**
 * ShowList class contains inner class ShowNode and attributes size and head to point
 * to the first node in this list object. In addition, contains methods to manipulate with linkedList of Show nodes.
 */
public class ShowList {

    /**
     * ShowNode class to create objects of TVShow type and  a pointer to a next ShowNode object.
     */
    private class ShowNode{

        //Instance variables initialization
        private TVShow tvShow;
        private ShowNode next;

        //Default constructor
        public ShowNode()
        {
            this.tvShow=null;
            this.next=null;
        }

        //Parameterized constructor
        public ShowNode(TVShow tvShow, ShowNode next)
        {
            this.tvShow = tvShow;
            this.next = next;
        }

        //Copy constructor
        public ShowNode(ShowNode anotherShoNode)
        {
            this.tvShow=anotherShoNode.getTvShow();
            this.next=anotherShoNode.getNext();
        }

        /**
         * Method to get a TVShow object.
         * @return reference to TVShow object.
         */
        public TVShow getTvShow()
        {
            return tvShow;
        }

        /**
         * Method to get next node reference.
         * @return reference to next node.
         */
        public ShowNode getNext()
        {
            return next;
        }

        /**
         * Method to set next node reference.
         * @param link receives next node reference.
         */
        public void setLink(ShowNode link)
        {
            this.next = link;
        }

        /**
         * Vethod to clone a node.
         * @return deep copy of copied node
         */
        public ShowNode clone()
        {
            return new ShowNode(this.getTvShow(), this.getNext());
        }
    }

    //ShowList attributes initialization
    private ShowNode head;
    private int size;

    //Default constructor
    public ShowList()
    {
        this.head=null;
    }

    //Copy constructor
    public ShowList(ShowList anotherShowList)
    {
        this.head= anotherShowList.getHead();
        this.size= anotherShowList.getSize();
    }

    //Setters and getters
    public ShowNode getHead()
    {
        return head;
    }

    //Setters and getters
    public void setHead(ShowNode head)
    {
        this.head = head;
    }

    //Setters and getters
    public int getSize()
    {
        int count=0;
        ShowNode position = head;
        while (position!=null)
        {
            count++;
            position=position.next;
        }
        return count;
    }

    /**
     * Method to proceed with user watching and wishlist shows. Searches passing as arguments IDs in linked list, compares
     * their start and time and output whether the user is able to watch them or not.
     * @param watchingArrayList Receives an arraylist of watching show IDs.
     * @param wishingArrayList Receives an arraylist of wishlist show IDs.
     */
    public void canWatch(ArrayList watchingArrayList, ArrayList wishingArrayList )
    {
        //temporary watching and wishing nodes creation
        ShowNode wishingTempNode;
        ShowNode watchingTempNode;

        //variable to control the flow
        boolean overLapped;

        //loop to go through the user wishlist
        for (int i=0;i<wishingArrayList.size();i++)
        {
            overLapped=false;
            wishingTempNode = find((String) wishingArrayList.get(i));

            //special case(ID does not exist)
            if (wishingTempNode==null)
            {
                System.out.println("ID " + wishingArrayList.get(i) +" does not exist in the list");
            }
            else
            {
                //loop to go through the user watching list
                for (int j=0;j< watchingArrayList.size();j++)
                {
                    watchingTempNode = find((String) watchingArrayList.get(j));

                    if (!(wishingTempNode.getTvShow().isOnSameTime(watchingTempNode.getTvShow())))
                    {
                        continue;
                    }
                    else
                    {
                        System.out.println("User can not watch "+wishingTempNode.getTvShow().getID()
                                +" as he/she is not finished with a show he/she is watching ");
                        overLapped=true;
                    }
                }
                if (!overLapped)
                    System.out.println("User can watch "+wishingTempNode.getTvShow().getID()
                            +" as he/she is not watching anything during this time ");
            }
        }
    }

    /**
     * Method to add a node at the head of the list.
     * @param tvShow Receives a variable of TVShow type.
     */
    public void addToStart(TVShow tvShow)
    {
        this.head=new ShowNode(tvShow,head);
    }

    /**
     * If the index is not valid (a valid index must have a value between 0 and size1),  terminates the program.
     * If the index is valid, creates a node with the passed TVShow object and inserts this node at the given index.
     * @param tvShow an object from TVShow class.
     * @param index an integer representing an index.
     */
    public void insertAtIndex(TVShow tvShow, int index)
    {
        try
        {
            if (head!=null)
            {
                ShowNode newNode = new ShowNode(tvShow,head);
                ShowNode previous = head;
                int counter=1;
                while (counter<index-1)
                {
                    previous=previous.next;
                    counter++;
                }
                ShowNode current = previous.next;
                newNode.next=current;
                previous.next=newNode;
            }
        }
        catch (Exception e)
        {
            System.out.println("Impossible to add at this index. Index out of limits. The program has been terminated.");
            System.exit(0);
        }
    }

    /**
     * Method to delete the node pointed by the index from the list.
     * @param index integer parameter representing an index.
     */
    public void deleteFromIndex(int index)
    {
        try
        {
            if (head!=null)
            {
                ShowNode previous = head;

                int counter=1;
                while (counter<index-1)
                {
                    previous=previous.next;
                    counter++;
                }
                ShowNode tempNode = previous.next;
                previous.next=tempNode.next;
                tempNode.next=null;
            }

        }
        catch (Exception e)
        {
            System.out.println("Impossible to delete this index. Index out of limits. The program has been terminated.");
            System.exit(0);
        }
    }

    /**
     * Method to delete the first node in the list.
     */
    public void deleteFromStart()
    {
        if (head!=null)
        {
            ShowNode position=getHead();
            head=head.next;
            position.next=null;
        }
        else System.out.println("The list is empty");
    }

    /**
     * Method to replace the object in the node at the passed index by the passed object.
     * @param tvShow an object from TVShow class.
     * @param index an integer representing an index.
     */
    public void replaceAtIndex(TVShow tvShow,int index)
    {
        try
        {
            if (head!=null)
            {
                ShowNode newNode = new ShowNode(tvShow,head);
                ShowNode previous = head;
                int counter=1;
                while (counter<index-1)
                {
                    previous=previous.next;
                    counter++;
                }
                ShowNode current = previous.next;
                newNode.next=current.next;
                previous.next=newNode;
            }
        }
        catch (Exception e)
        {
            System.out.println("Impossible to replace this index. Index out of limits.");
        }
    }

    /**
     * Method to find a passing as an argument ID in TVShow list. Also counts and outputs iterations were made before finding.
     * @param target ID as a String
     * @return The node of target ID, null otherwise.
     */
    //Privacy leaking is not taken place, because the node class is inner class.
    public ShowNode findID(String target)
    {
        ShowNode tempNode = head;
        int counter=0;
        while (tempNode!=null)
        {
            counter++;
            if (tempNode.getTvShow().getID().equals(target))
            {
                System.out.println("ID \""+target+"\" is found, "+counter+" iterations were made.");
                return tempNode;
            }
            tempNode=tempNode.next;
        }
        System.out.println("ID \""+target+"\" is not in the list.");
        return null;
    }

    /**
     * Method to find a passing as an argument ID in TVShow list. Simply returns an object of node of target ID.
     * @param target ID as a String.
     * @return The node of target ID, null otherwise.
     */
    //Privacy leaking is not taken place, because the node class is inner class.
    public ShowNode find(String target)
    {
        ShowNode tempNode = head;
        while (tempNode!=null)
        {
            if (tempNode.getTvShow().getID().equals(target))
            {
                return tempNode;
            }
            tempNode=tempNode.next;
        }
        return null;
    }

    /**
     * Method to find a ShowNode by name.
     * @param target Name as a String.
     * @return True, if node with passing name is found, false otherwise.
     */
    public boolean find(TVShow target)
    {
        ShowNode tempNode = head;
        while (tempNode!=null)
        {
            if (tempNode.getTvShow().getName().equals(target.getName())
            && tempNode.getTvShow().getStartTime()==target.getStartTime()&&tempNode.getTvShow().getEndTime()==target.getEndTime())
            {
                return true;
            }
            tempNode=tempNode.next;
        }
        return false;
    }

    /**
     * Method to find a passing as an argument ID in TVShow list.
     * @param target ID as a String.
     * @return True, if passing ID in list, false otherwise.
     */
    public boolean contains(String target)
    {
        ShowNode tempNode = head;
        while (tempNode!=null)
        {

            if (tempNode.getTvShow().getID().equals(target))
            {
                System.out.println("ID is in the list.");
                return true;
            }
            tempNode=tempNode.next;
        }
        System.out.println("ID is not in the list");
        return false;
    }

    /**
     * Method to compare two objects of ShowList type.
     * @param otherObject Receives a parameter of class Object, then cast it to ShowList type. Compare it with "this"
     * object parameters.
     * @return false, if passing object is null or has a different class type, return true, if all object attributes are
     * equal
     */
    public boolean equals(Object otherObject)
    {
        if (otherObject==null)return false;
        else if (getClass()!=otherObject.getClass())return false;
        else
        {
            ShowList otherList = (ShowList) otherObject;

            if (getSize()!=otherList.getSize())return false;
            ShowNode position=head;
            ShowNode anotherPosition= otherList.head;

            while (position!=null)
            {
                if (!(position.tvShow.equals(anotherPosition.tvShow)))
                    return false;
                position=position.next;
                anotherPosition=anotherPosition.next;
            }
            return true;
        }
    }

    /**
     * Method to output ShowList objects.
     */
    public void outputList( )
    {
        ShowNode position = head;
        while (position != null)
        {
            System.out.println(position.getTvShow());
            position = position.next;
        }
    }
}
