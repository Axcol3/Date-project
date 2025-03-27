import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

class Date implements Comparable<Date>{
    
    
    int day, month, year;
    boolean leapYear = false;
    List<Integer> month30 = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
    List<Integer> month31 = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
    
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public boolean isValidDate(int day, int month, int year){
        if(year % 400 == 0 || year % 100 != 0 && year % 4 == 0){
                    leapYear = true;
        }
        if(month >= 1 && month <= 12 && day >= 1 && day <= 31 ){
            if(month30.contains(month) && day <= 30){
                return true;
            }else if(month31.contains(month)){
                return true;
            }else{
                if(year % 400 == 0 && day <= 29 || year % 100 != 0 && year % 4 == 0 && day <= 29){
                   // leapYear = true;
                     return true;  
                }else if(day <= 28){
                    return true;
                }  
            }
        }
        System.out.println("Date is not valid!");
        return false;
    }
    
    public void updateDate(int d, int m, int y){
        if(isValidDate(d, m, y)){
           this.day = d;
           this.month = m;
           this.year = y;
       }else Error();
    }
    
    public String getDayOfWeek(){
            String res = "No valid data";
            if(isValidDate(day, month, year)){
                LocalDate date = LocalDate.of(year, month, day);
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                res = String.valueOf(dayOfWeek);
            }
        return res;
    }
    
    public long calculateDifference(Date otherDate){
        long diffD = 0;
        if(isValidDate(day, month, year) || isValidDate(otherDate.day, otherDate.month, otherDate.year)){
            long diffY = Math.abs(otherDate.year - year);
            if(otherDate.year > year && otherDate.month < month || otherDate.year < year && otherDate.month > month ){
                diffY--;
            }
            long countLY = diffY/4 - diffY/100 + diffY/400;
            long minMonth = Math.min(otherDate.month, month);
            long maxMonth = Math.max(otherDate.month, month);
            
            if(leapYear || otherDate.leapYear){
                diffD += (leapYear ? 1 : 0) + (otherDate.leapYear ? 1 : 0);
            }
        
            if(otherDate.year > year) diffD += (otherDate.day - day);
            else diffD +=  (day - otherDate.day);
            if(otherDate.year > year && otherDate.month < month || otherDate.year < year && otherDate.month > month ){
                long temp = minMonth;
                minMonth = maxMonth;
                maxMonth = temp;
            }
            for(long i = minMonth; i != maxMonth  ; i++){
                if(month31.contains((int)i)){
                    diffD+=31;
                }else if(month30.contains((int)i)){
                    diffD+=30;
                }
                if(i >= 12) i = 0;
              
            }
        
            diffD += countLY + diffY * 365;
        }else{
           Error();
           return 0;
        } 
        return diffD;
        
    }
    
    public void printDate(){
        String[] whichMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        System.out.println(whichMonth[month-1] + " " + day +", " + year);
    }
    
    public int compareTo(Date other) {
       if(isValidDate(day, month, year) || isValidDate(other.day, other.month, other.year)){
            if (this.year != other.year) return Integer.compare(this.year, other.year);
            if (this.month != other.month) return Integer.compare(this.month, other.month);
       }else{
           Error();
           return 0;
        } 
        return Integer.compare(this.day, other.day);
    }
    
    public String toString() {
        return String.format("%02d.%02d.%d", day, month, year);
    }
    
    public void Error(){
        System.out.println("Date is not valid!");
    }

    
}
public class Main
{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String date = input.nextLine().trim();
		long countDot = date.chars().filter(ch -> ch == '.').count();
		if (countDot == 2){
		    int d = Integer.parseInt(date.substring( 0, date.indexOf(".") ));
            int m = Integer.parseInt(date.substring( date.indexOf(".")+1, date.lastIndexOf(".") ));
            int y = Integer.parseInt(date.substring(date.lastIndexOf(".")+1));
            Date date0 = new Date();
            Date date1 = new Date();
            List<Date> dates = new ArrayList<>();
            dates.add(new Date(15, 6, 2022));
            dates.add(new Date(12, 12, 2000));
            dates.add(new Date(1, 1, 2024));
            dates.add(new Date(8, 5, 1995));
            date1.isValidDate(01,01,1900);
            date0.updateDate(d, m, y);
            date1.updateDate(01,01,1900);
            System.out.println(date0.isValidDate(d, m, y));
            System.out.println(date0.getDayOfWeek());
            System.out.println(date0.calculateDifference(date1));
            date0.printDate();
            Collections.sort(dates);
            for (Date dat : dates) {
                System.out.println(dat);
            }
            
		}
		
	}
} 