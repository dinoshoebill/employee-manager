package employeemanager.models;

import employeemanager.utils.Contact;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;


@Entity
@Table(name = "contacts")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contactId;

    @NotBlank
    private Contact type;

    @NotBlank
    private String contact;

    public ContactInfo() { }

    public ContactInfo(Contact type, String contact) {
        this.type = type;
        this.contact = contact;
    }

    public UUID getContactId() {
        return contactId;
    }

    public Contact getType() {
        return type;
    }

    public void setType(Contact type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
