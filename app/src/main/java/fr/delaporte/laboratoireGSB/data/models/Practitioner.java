package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;

@Table(database= LaboratoireDatabase.class, name="practitioners")
public class Practitioner extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    private int idPractitioner;

    @Column
    String name;

    @Column
    String surname;

    @Column
    String adress;

    @Column
    String city;

    @Column
    int cityCP;

    public Practitioner(){}

    public Practitioner(Parcel in) {
        idPractitioner = in.readInt();
        name = in.readString();
        surname = in.readString();
        adress = in.readString();
        city = in.readString();
        cityCP = in.readInt();
    }

    public Practitioner(String name, String surname, String adress, int cityCP, String city) {
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.cityCP = cityCP;
        this.city = city.toUpperCase();
    }

    public int getIdPractitioner() {
        return idPractitioner;
    }

    public void setIdPractitioner(int idPractitioner) {
        this.idPractitioner = idPractitioner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullname(){
        return this.name + " " + this.surname;
    }

    public String getAdress(){
        return this.adress;
    }

    public void setAdress(String adress){
        this.adress = adress;
    }

    public String getCity(){ return this.city; }

    public void setCity(String city){
        this.city = city;
    }

    public int getCityCP(){ return this.cityCP; }

    public void setCityCP(int cityCP){
        this.cityCP = cityCP;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPractitioner);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(adress);
        dest.writeString(city);
        dest.writeInt(cityCP);
    }

    public static final Creator<Practitioner> CREATOR = new Creator<Practitioner>() {

        @Override
        public Practitioner createFromParcel(Parcel in) {
            return new Practitioner(in);
        }

        @Override
        public Practitioner[] newArray(int size) {
            return new Practitioner[size];
        }
    };

}
