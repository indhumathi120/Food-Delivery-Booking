import java.util.*;

public class Main{
    static int order=1,index=0;
    static String[][] data = new String[1001][9];
    public static int traverse_min(int[] de)
    { 
        int m=de[0];
        for(int i=0;i<de.length;i++)
        {
            if(de[i]<m)
            {
                return i;
            }
        }
        return 0;
    }
    public static int[] getorder(int pointer)
    {  
         int p=0;
         int[] orders = new int[pointer];
         while(p<pointer)
         {
             //System.out.println(data[p][0].charAt(2));
            orders[((data[p][0].charAt(2))-'0')-1]++;
            p++;
         }
         return orders;

    }
    public static void store_data(int executive, int time, char Restaurant, char Destination,int pointer,int[] de)
    {
        
        data[pointer][0]="DE"+(executive+1);  // executive
        data[pointer][1]=Restaurant+"";
        data[pointer][2]=Destination+"";
        data[pointer][3]= null; // order
        String t="";
        String d_t="";
        int d=(time+15)+30;
        if((time+15)/60>=0 && (time+15)/60<=9)
            t+="0"+(time+15)/60;
        else
            t+=time/60;
        t+=":";
        if((time+15)%60>=0 && (time+15)%60<=9)
            t+="0"+(time+15)%60;
        else
            t+=(time+15)%60;
        if(d/60>=00 && d/60<=9)
            d_t+="0"+d/60;
        else
            d_t+=d/60;
        d_t+=':';
        if(d%60>=0 && d%60<=9)
            d_t+="0"+d%60;
        else
            d_t+=d%60;
        data[pointer][4]=t; 
        data[pointer][5]=d_t;
        
        data[pointer][6]=de[(traverse_min(de)-1==-1?traverse_min(de):traverse_min(de)-1)]+"";  // according to order
        data[pointer][7]=time+"";
        
        
    }
    public static void display_bill(int pointer,int[] orders)
    {
        int p=0;
        System.out.println("\n\nDelivery History");
        System.out.println("Trip        Executive        Restaurant     Destination    Orders    PickupTime        DeliveryTime        DeliveryCharge");
        
        while(p<pointer){
       
        System.out.print(order+"           ");
        order++;
        
        for(int i=0;i<7;i++)
        {
            if(i==3){
                System.out.print(orders[index++]+"        ");
                continue;
            }
            System.out.print(data[p][i]+"               ");
        }
        
        System.out.println();
        p++;
    }
    }
    
    static int book_id=0;
    public static void handlebooking(int[] de)
    {
        System.out.println("\nBooking ID: "+book_id);
        book_id++;
        System.out.println("Available Executives: ");
        System.out.println("Executives           Delivery Charge Earned");
        for(int i=0;i<de.length;i++)
        {

            System.out.println("DE"+(i+1)+"                      "+de[i]);
        }
        //System.out.println("Allotted executive : DE"+(executive+1));
    }
    public static int ordertrip(int[] de,int time,char Restaurant,char Destination,int pointer)
    {
        if(pointer==0 || traverse_min(de)==0){
            de[0]+=50;
            return 0;
        }
        int i; //
        if(data[pointer-1][1].equals(Restaurant+"") && data[pointer-1][2].equals(Destination+"") && time-Integer.parseInt(data[pointer-1][7])<=15)
        {
            i=traverse_min(de)-1;
            de[i%de.length]+=5;
        }
        else
        {
            i=traverse_min(de);
            de[i%de.length]+=50;
               
        }
        return i;
        
    }
    public static void Total_Earned(int pointer,int[] orders)
    {
            System.out.println("\nTotal Earned");
            System.out.println("Executive        Allowance       Deliver Charges        Total");
            int p=0;
            
            while(p<pointer)
            {
                int var=Integer.parseInt(data[p][6]);
                if(orders[p]>=1){
                System.out.println(data[p][0]+"               10                "+var+"                 "+(var+10));
                }
                else{
                    var+=10;
                    System.out.println(data[p][0]+"               10                "+var+"                 "+(var+10));
                    
                }
                p++;
            }
    }
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of Delivery_executive:");
        int De_count = sc.nextInt();

        // Number of DE
        int[] Delivery_exeutive = new int[De_count];
        char Restaurant,Destination;
        int count=0;
        

        
        while(true)
        {
            //Customer ID
            System.out.print("Customer ID:");
            int customer_id=sc.nextInt();
            sc.nextLine();

            // Restaurant
            System.out.print("Restaurant:");
            Restaurant = sc.next().charAt(0);
            if(!(Restaurant>=65 && Restaurant<=69))
            {
                System.out.println("Restaurant not found!");
                System.out.println("Please enter valid Restaurant ['A','B','C','D','E']");
                continue;
            }
            // Destination
            System.out.print("Destination:");
            Destination = sc.next().charAt(0);
            if(!(Destination>=65 && Destination<=69))
            {
                System.out.println("Cannot deliver to this Location"); 
                System.out.println("Please enter valid Destination ['A','B','C','D','E']");
                continue;
            }
            //Time
            System.out.print("Time:");
            String time = sc.next();
            String[] t = time.split("\\.");
            int hour = Integer.parseInt(t[0]);
            int min = Integer.parseInt(t[1]);
            int total_minutes=hour*60+min;
            sc.next();
            order=1;
           
            
            // method operation
            // int index=count-1;
            //int executive = traverse_min(Delivery_exeutive);
            handlebooking(Delivery_exeutive);
            int executive=ordertrip(Delivery_exeutive, total_minutes, Restaurant, Destination, count);
            System.out.println("Alloted Executive: DE"+(executive+1));
            store_data(executive, total_minutes, Restaurant, Destination, count, Delivery_exeutive);
            count++;
            System.out.print("==============================================");
            // TERMINATE STATMENT
            System.out.print("\nTerminate Order? Y/N:");
            char terminate = sc.next().charAt(0);
            if(terminate=='Y' || terminate=='y')
            {
                break;
            }
           
            
        }
        int[] p = getorder(count);
        display_bill(count,p);
        Total_Earned(count,p);

        sc.close();
    
    }
}