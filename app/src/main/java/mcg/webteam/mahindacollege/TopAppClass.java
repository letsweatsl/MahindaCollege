package mcg.webteam.mahindacollege;

/**
 * Created by Chandupa on 19-Jan-18.
 */

public class TopAppClass {
    private String name,desc,Img;

    public TopAppClass(){
    }

    public TopAppClass(String name,String desc, String Img){
        this.desc = desc;
        this.Img=Img;
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return Img;
    }
}
