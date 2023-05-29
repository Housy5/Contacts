package housy.contact;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactSerializer {
    
    private File file;
    private String userhome;
    
    private String findUserHome() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            return System.getenv("USERPROFILE");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return System.getProperty("user.home"); 
        }
        
        throw new UnsupportedOperationException("Unsupported operating system: " + os + ".");
    }
    
    public ContactSerializer(String fileName) throws IOException {
        userhome = findUserHome();
        Path homePath = Path.of(userhome, "ContactData");
        if (!Files.exists(homePath)) {
            Files.createDirectories(homePath);
        }
        file = Path.of(homePath.toString(), "contacts.cs").toFile();
        
    }
    
    public void save(List<Contact> contacts) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(contacts);
        }
    }

    public List<Contact> loadContacts() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (ArrayList<Contact>) in.readObject();
        }
    }
}
