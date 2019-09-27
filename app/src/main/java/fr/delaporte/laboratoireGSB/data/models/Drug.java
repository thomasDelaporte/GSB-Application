package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;

@Table(name="drugs", database= LaboratoireDatabase.class)
public class Drug extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    private int idDrug;

    @Column
    String name;

    @Column
    int quantityAvailable = 1;

    public Drug(){}

    public Drug(String name){
        this.name = name;
    }

    protected Drug(Parcel in) {
        idDrug = in.readInt();
        name = in.readString();
    }

    public Drug(String name, Integer quantity) {
        this(name);
        this.quantityAvailable = quantity;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setQuantityAvailable(int quantityAvailable){
        this.quantityAvailable = quantityAvailable;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.quantityAvailable + " en stock)";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idDrug);
        dest.writeString(name);
    }

    public static final Creator<Drug> CREATOR = new Creator<Drug>() {
        @Override
        public Drug createFromParcel(Parcel in) {
            return new Drug(in);
        }

        @Override
        public Drug[] newArray(int size) {
            return new Drug[size];
        }
    };

    public void addQuantityAvaiable(int quantity) {
        this.quantityAvailable += quantity;
    }
}
