package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.List;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;

@Table(database= LaboratoireDatabase.class, name="visits")
public class Visit extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    private int idVisite;

    @ForeignKey
    @Column
    Personnel visiteurMedical;

    @ForeignKey
    @Column
    private Practitioner practitioner;

    @Column
    private Calendar date;

    @Column
    private String report;

    @Column
    private Calendar dateDone;

    List<DrugLeft> drugsLeft;

    public Visit(){}

    public Visit(Practitioner practitioner, Calendar date){
        this.practitioner = practitioner;
        this.date = date;
    }

    protected Visit(Parcel in) {
        idVisite = in.readInt();
        visiteurMedical = in.readParcelable(Personnel.class.getClassLoader());
        practitioner = in.readParcelable(Practitioner.class.getClassLoader());
        report = in.readString();
        date = (Calendar) in.readSerializable();
        dateDone = (Calendar) in.readSerializable();
    }

    public Visit(Practitioner practitioner, Personnel visiteurMedical, Calendar visitDate) {
        this.practitioner = practitioner;
        this.visiteurMedical = visiteurMedical;
        this.date = visitDate;
    }

    public Visit(Personnel visiteurMedical, Practitioner practitioner, Calendar date) {
        this.visiteurMedical = visiteurMedical;
        this.practitioner = practitioner;
        this.date = date;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public void setIdVisite(int idVisite){
        this.idVisite = idVisite;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setDrugs(List<DrugLeft> drugs) {
        this.drugsLeft = drugs;
    }

    public String getReport(){
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getIdVisite(){
        return this.idVisite;
    }

    public Calendar getDate(){
        return this.date;
    }

    public Calendar getDateDone() {
        return dateDone;
    }

    public boolean isDone(){
        return this.dateDone != null;
    }

    public void setDateDone(Calendar dateDone) {
        this.dateDone = dateDone;
    }

    public List<DrugLeft> updateDrugsLeft(){

        if(drugsLeft == null || drugsLeft.isEmpty()){
            this.drugsLeft = SQLite.select()
                    .from(DrugLeft.class)
                    .where(DrugLeft_Table.visit_idVisite.eq(idVisite))
                    .queryList();
        }

        return drugsLeft;
    }

    public Personnel getVisiteurMedical() {
        return visiteurMedical;
    }

    public void setVisiteurMedical(Personnel visiteurMedical) {
        this.visiteurMedical = visiteurMedical;
    }

    public List<DrugLeft> getDrugsLeft(){
        return this.drugsLeft;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idVisite);
        dest.writeParcelable(visiteurMedical, flags);
        dest.writeParcelable(practitioner, flags);
        dest.writeString(report);
        dest.writeSerializable(date);
        dest.writeSerializable(dateDone);
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public void setDrugsLeft(List<DrugLeft> drugsLeft) {
        this.drugsLeft = drugsLeft;
    }
}
