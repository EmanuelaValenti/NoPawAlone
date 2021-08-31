import java.io.Serializable;

public class Dog implements Serializable{

    private String NameOwner;
    private String SurnameOwner;
    private String Phone;
    private String NameDog;
    private String Race;
    private String Microchip;
    private int Weight;

    public Dog(String nameOwner, String surnameOwner, String phone, String nameDog, String race, String microchip, int weight){
        NameOwner = nameOwner;
        SurnameOwner = surnameOwner;
        Phone = phone;
        NameDog = nameDog;
        Race = race;
        Microchip = microchip;
        this.Weight = weight;
    }


    public String getNameOwner() {return NameOwner; }
    public void setNameOwner(String nameOwner) {this.NameOwner = nameOwner; }

    public String getSurnameOwner() {return SurnameOwner; }
    public void setSurnameOwner(String surnameOwner) {this.SurnameOwner = surnameOwner; }

    public String getPhone() { return Phone;    }
    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getNameDog() {return NameDog; }
    public void setNameDog(String namedog) {this.NameDog = namedog; }

    public String getRace() {return Race; }
    public void setRace(String race) {this.Race = race; }

    public String getMicrochip() { return Microchip;    }
    public void setMicrochip(String microchip) {
        this.Microchip = microchip;
    }

    public int getWeight() {return Weight; }
    public void setWeight(int weight) {this.Weight = weight; }

    @Override
    public String toString(){
        return "Dog {"+
                " Name Owner=" + NameOwner +
                ", Surname Owner=" + SurnameOwner +
                ", Phone=" + Phone +
                ", Name Dog=" + NameDog +
                ", Race=" + Race +
                ", Microchip=" + Microchip +
                ", Weight=" + Weight + "Kg " +
                "}";
    }
}/*NewDog*/

