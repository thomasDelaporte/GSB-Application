package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;

@Table(database= LaboratoireDatabase.class, name="activities")
public class ComplementaryActivity extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    int idAC;

    @ForeignKey
    @Column
    Personnel personnel;

    @Column
    String salle;

    @Column
    String compteRendu;

    @Column(name="topic")
    String theme;

    @Column
    int budget;

    public ComplementaryActivity() {}

    public ComplementaryActivity(String salle, int budget, String theme, Personnel personnel) {
        this.salle = salle;
        this.budget = budget;
        this.theme = theme;
        this.personnel = personnel;
    }

    protected ComplementaryActivity(Parcel in) {
        idAC = in.readInt();
        personnel = in.readParcelable(Personnel.class.getClassLoader());
        salle = in.readString();
        compteRendu = in.readString();
        theme = in.readString();
        budget = in.readInt();
    }

    public String getSalle() {
        return salle;
    }

    @Override
    public String toString() {
        return this.salle + " - " + this.theme;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAC);
        dest.writeParcelable(personnel, flags);
        dest.writeString(salle);
        dest.writeString(compteRendu);
        dest.writeInt(budget);
    }

    public static final Creator<ComplementaryActivity> CREATOR = new Creator<ComplementaryActivity>() {

        @Override
        public ComplementaryActivity createFromParcel(Parcel in) {
            return new ComplementaryActivity(in);
        }

        @Override
        public ComplementaryActivity[] newArray(int size) {
            return new ComplementaryActivity[size];
        }
    };

    public int getBudget() {
        return budget;
    }

    public String getTheme() {
        return theme;
    }
}
