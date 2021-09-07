import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;


public class Register implements Serializable {

    String last_modification;

    private ArrayList<Dog> NationalDogRegister;
    private ArrayList<Dog> LostDogRegister;
    private ArrayList<Dog> StrayDogRegister;

    public Register(){
        NationalDogRegister = new ArrayList<>();
        StrayDogRegister = new ArrayList<>();
        LostDogRegister = new ArrayList<>();
    }


    public synchronized void addNewDog (Dog d){
        last_modification = new Date().toString();
        NationalDogRegister.add(d);
    }

    public synchronized void addLostDog(Dog d){
        last_modification = new Date().toString();
        LostDogRegister.add(d);
    }

    public synchronized void addStrayDog(Dog d){
        last_modification = new Date().toString();
        StrayDogRegister.add(d);
    }


    public synchronized String foundMicrochip(String mic){
        String s;
        for (Dog d: NationalDogRegister){
            if(d.getMicrochip().equals(mic)){
                s = "FOUND_MICROCHIP_OK";
                return s;
            }
        }
        s = "FOUND_MICROCHIP_ERROR";
        return s;
    }


    public synchronized  String remove(String micro, int i) {
        String canc = null;
        if (i == 1) {
            last_modification = new Date().toString();
            for (Dog d : NationalDogRegister) {

                if ((d.getMicrochip().equals(micro))) {
                    NationalDogRegister.remove(d);
                     canc = "REMOVE_N_OK";
                    return canc;
                }
            }
        }

        if (i == 2) {
            last_modification = new Date().toString();
            for (Dog d : LostDogRegister) {
                if ((d.getMicrochip().equals(micro))) {
                    LostDogRegister.remove(d);
                     canc = "REMOVE_L_OK";
                    return canc;
                }
            }
        }

        if (i == 3) {
            last_modification = new Date().toString();
            for (Dog d : StrayDogRegister) {
                if ((d.getMicrochip().equals(micro))) {
                    StrayDogRegister.remove(d);
                   canc = "REMOVE_S_OK";
                    return canc;
                }
            }
        }
        canc = "REMOVE_ERROR";
        return canc;
    }



    public ArrayList<Dog> getNatCopy(){
        ArrayList<Dog> s_reg = new ArrayList<>();
        s_reg.addAll(NationalDogRegister);
        return s_reg;
    }

    public ArrayList<Dog> getLostCopy(){
        ArrayList<Dog> s_reg = new ArrayList<>();
        s_reg.addAll(LostDogRegister);
        return s_reg;
    }

    public ArrayList<Dog> getStrayCopy(){
        ArrayList<Dog> s_reg = new ArrayList<>();
        s_reg.addAll(StrayDogRegister);
        return s_reg;
    }



    @Override
    public String toString(){
        String s;
        s = "BEGIN_LIST";
        s = s + "MOD_DATE: " +last_modification;
        for (Dog d: NationalDogRegister){
            s = s + "NAME OWNER " +d.getNameOwner();
            s = s + "SURNNAME OWNER " +d.getSurnameOwner();
            s = s + "PHONE " +d.getPhone();
            s = s + "NAME DOG " +d.getNameDog();
            s = s + "RACE " +d.getRace();
            s = s + "MICROCHIP " +d.getMicrochip();
            s = s + "WEIGHT " +d.getWeight();
        }
        s = s + "END_LIST";
        return s;
    }
}/*Register*/
