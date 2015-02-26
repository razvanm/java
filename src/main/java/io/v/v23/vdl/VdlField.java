package io.v.v23.vdl;

import java.io.Serializable;

/**
 * VdlField represents a struct or union field in a VDL type.
 */
public final class VdlField implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final VdlType type;

    public VdlField(String name, VdlType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public VdlType getType() {
        return type;
    }
}
