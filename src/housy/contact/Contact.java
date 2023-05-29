package housy.contact;

import java.io.Serializable;
import java.util.*;

public class Contact implements Serializable {
    
    private String name = " ";
    private String phone = " ";
    private String email = " ";
    private String notes = " ";
    
    public Contact(String name, String phone, String email) {
        setName(name);
        setPhone(phone);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name == null ? " " : name;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone == null ? " " : phone;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email == null ? " " : email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.phone);
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.notes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.name, other.name))
            return false;
        if (!Objects.equals(this.phone, other.phone))
            return false;
        if (!Objects.equals(this.email, other.email))
            return false;
        return Objects.equals(this.notes, other.notes);
    }
}
