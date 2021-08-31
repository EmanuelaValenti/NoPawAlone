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
        int i = 1;
        NationalDogRegister.add(d);
        saveRegister(NationalDogRegister, i);
    }

    public synchronized void addLostDog(Dog d){
        int i = 2;
        LostDogRegister.add(d);
        saveRegister(LostDogRegister, i);
    }

    public synchronized void addStrayDog(Dog d){
        int i = 3;
        StrayDogRegister.add(d);
        saveRegister(StrayDogRegister, i);
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
        int tipo = i;
        if (tipo == 1) {
            for (Dog d : NationalDogRegister) {
                if ((d.getMicrochip().equals(micro))) {

                    NationalDogRegister.remove(d);
                    saveRegister(NationalDogRegister, tipo);
                    String canc = "REMOVE_N_OK";
                    return canc;
                }
            }
        }

        if (tipo == 2) {
            for (Dog d : LostDogRegister) {
                if ((d.getMicrochip().equals(micro))) {
                    LostDogRegister.remove(d);
                    saveRegister(LostDogRegister, tipo);
                    String canc = "REMOVE_L_OK";
                    return canc;
                }
            }
        }

        if (tipo == 3) {
            for (Dog d : StrayDogRegister) {
                if ((d.getMicrochip().equals(micro))) {
                    StrayDogRegister.remove(d);
                    saveRegister(StrayDogRegister, tipo);
                    String canc = "REMOVE_S_OK";
                    return canc;
                }
            }
        }
        String canc = "REMOVE_ERROR";
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


    public void saveRegister(ArrayList Register, int i){
        try{
            if(i==1) {
                var oos = new ObjectOutputStream((new FileOutputStream("NationalDogRegister.ser")));
                oos.writeObject(Register);
                oos.close();
            }else if(i==2){
                var oos = new ObjectOutputStream((new FileOutputStream("LostDogRegister.ser")));
                oos.writeObject(Register);
                oos.close();

            }else if(i==3){
                var oos = new ObjectOutputStream((new FileOutputStream("StrayDogRegister.ser")));
                oos.writeObject(Register);
                oos.close();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
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
