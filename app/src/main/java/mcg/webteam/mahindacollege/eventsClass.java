package mcg.webteam.mahindacollege;

/**
 * Created by Chandupa on 19-Jan-18.
 */

public class eventsClass {
    private String date,desc,time,month,venue;

    public eventsClass(){
    }

    public eventsClass(String date, String desc, String time, String month, String venue){
        this.desc = desc;
        this.time=time;
        this.month=month;
        this.date=date;
        this.venue=venue;
    }

   public void setTime(String time){
        this.time=time;
   }
    public void setDate(String date){
        this.date=date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setVenue(String venue){
        this.venue=venue;
    }

    public void setMonth(String month){
        this.month=month;
}
    public String getDate(){
    return date;
}
    public String getTime(){
    return time;
    }

    public String getDesc() {
        return desc;
    }

    public String getMonth(){
        return month;
    }
    public String getVenue(){return venue;}
}
