package server.blocks;

public class Block {
    public Integer x = 0;
    public Integer y = 0;
    public String type = "";
    public Integer id = 0;
    public String spriteName = "";
    public Integer strength = 1000;
    public Integer health = strength;

    public Block(Integer id){
        this.id = id;
    }

}
