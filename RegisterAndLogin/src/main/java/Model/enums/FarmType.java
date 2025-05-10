package Model.enums;

public enum FarmType {
    TYPE_1(30 , 20 , 1 , 1 , 10 , 7 , 15 , 10 ),
    TYPE_2(30 , 20 , 1 , 1 , 15 , 10 , 12 , 8 ),
    TYPE_3(30 , 20 , 1 , 1 , 8 , 6 , 20 , 15 ),
    ;

    public final int lakeX , lakeY , quarryX , quarryY ,
            lakeWidth , lakeHeight , quarryWidth , quarryHeight ;
    FarmType(int lakeX , int lakeY , int quarryX , int quarryY
    , int lakeWidth , int lakeHeight , int quarryWidth , int quarryHeight ) {
        this.lakeX = lakeX;
        this.lakeY = lakeY;
        this.quarryX = quarryX;
        this.quarryY = quarryY;
        this.lakeWidth = lakeWidth;
        this.lakeHeight = lakeHeight;
        this.quarryWidth = quarryWidth;
        this.quarryHeight = quarryHeight;
    }
}
