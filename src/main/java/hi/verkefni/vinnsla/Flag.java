/******************************************************************************
 *  Höfundur: Anton Benediktsson
 *  Netfang : anb59@hi.is
 *  Lýsing  : Klasi sem heldur utan um upplýsingar um fána, þar á meðal landakóða, 
 *            nafn lands, slóð á mynd af fána og heimsálfu.
 *****************************************************************************/

package hi.verkefni.vinnsla;

public class Flag {
    private String countryCode;
    private String countryName;
    private String imageUrl;
    private String continent;
    
    public Flag(String countryCode, String countryName, String continent) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.imageUrl = "/flags/" + countryCode + ".png";
        this.continent = continent;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public String getContinent() {
        return continent;
    }
}