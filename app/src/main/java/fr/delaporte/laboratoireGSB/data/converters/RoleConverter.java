package fr.delaporte.laboratoireGSB.data.converters;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import fr.delaporte.laboratoireGSB.helper.enums.Role;

public class RoleConverter extends TypeConverter<Integer, Role> {

    @Override
    public Integer getDBValue(Role model) {
        return model.ordinal();
    }

    @Override
    public Role getModelValue(Integer data) {
        return Role.values()[data];
    }
}
