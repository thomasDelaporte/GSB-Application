package fr.delaporte.laboratoireGSB.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.data.LaboratoireDatabase;
import fr.delaporte.laboratoireGSB.data.converters.RoleConverter;
import fr.delaporte.laboratoireGSB.helper.Encryption;
import fr.delaporte.laboratoireGSB.helper.enums.Role;

@Table(database= LaboratoireDatabase.class, name="personnels")
public class Personnel extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement= true)
    @Column
    int idPersonnel;

    @Column
    String name;

    @Column
    String surname;

    @Unique
    @Column
    String email;

    @ColumnIgnore
    @Column
    String password;

    @Column(typeConverter= RoleConverter.class)
    Role role;

    public Personnel() {}

    public Personnel(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = Encryption.encryptSHA512(password);
        this.role = Role.VISITEURMEDICAL;
    }

    protected Personnel(Parcel in) {
        idPersonnel = in.readInt();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        password = in.readString();
        role = (Role) in.readValue(Role.class.getClassLoader());
    }

    public static final Parcelable.Creator<Personnel> CREATOR = new Parcelable.Creator<Personnel>() {

        @Override
        public Personnel createFromParcel(Parcel in) {
            return new Personnel(in);
        }

        @Override
        public Personnel[] newArray(int size) {
            return new Personnel[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return this.role;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = Encryption.encryptSHA512(password);
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname + " (" + email + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPersonnel);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeValue(role);
    }


}
