package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;

@Table(database= LaboratoireDatabase.class, name="drugsleft")
public class DrugLeft extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    private int idDrugLeft;

    @ForeignKey
    @Column
    private Drug drug;

    @ForeignKey
    @Column
    private Visit visit;

    @Column
    private int quantity = 1;

    private int maxQuantity = 0;

    public DrugLeft(){}

    protected DrugLeft(Parcel in) {
        idDrugLeft = in.readInt();
        drug = in.readParcelable(Drug.class.getClassLoader());
        quantity = in.readInt();
    }

    public DrugLeft(Drug drug, Visit visit) {
        this.setDrug(drug);
        this.visit = visit;
    }

    public int getIdDrugLeft() {
        return idDrugLeft;
    }

    public void setIdDrugLeft(int idDrugLeft) {
        this.idDrugLeft = idDrugLeft;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
        this.maxQuantity = drug.getQuantityAvailable();
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.maxQuantity = drug.getQuantityAvailable() + quantity;
    }

    public void addQuantity() {
        quantity++;
    }

    public void subQuantity(){
        quantity--;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idDrugLeft);
        dest.writeParcelable(drug, flags);
        dest.writeInt(quantity);
    }

    public static final Creator<DrugLeft> CREATOR = new Creator<DrugLeft>() {
        @Override
        public DrugLeft createFromParcel(Parcel in) {
            return new DrugLeft(in);
        }

        @Override
        public DrugLeft[] newArray(int size) {
            return new DrugLeft[size];
        }
    };

    public int getMaxQuantity() {
        return this.maxQuantity;
    }
}
