package housy.contact;

import java.util.*;

public final class SearchIndex {

    private final Map<String, List<Contact>> index;

    public SearchIndex() {
        index = new HashMap<>();
    }

    private String getFirstChar(String str) {
        return String.valueOf(str.charAt(0)).toLowerCase();
    }
    
    public void addContact(Contact c) {
        String[] tokens = c.getName().split(" ");

        for (String token : tokens) {
            if (token.isBlank())
                continue;
            String key = getFirstChar(token);
            List<Contact> list = index.getOrDefault(key, new ArrayList<>());
            list.add(c);
            index.put(key, list);
        }
    }

    public void addAll(List<Contact> contacts) {
        for (Contact c : contacts) {
            addContact(c);
        }
    }
    
    public void remove(Contact contact) {
        String[] tokens = contact.getName().split(" ");
        
        for (String token : tokens) {
            String key = getFirstChar(token);
            List<Contact> found = index.getOrDefault(key, new ArrayList<>());
            found.remove(contact);
            index.put(key, found);
        }
    }
    
    public List<Contact> search(String term) {
        String key = getFirstChar(term);
        List<Contact> found = index.getOrDefault(key, new ArrayList<>());
        return found.stream().filter(x -> x.getName().toLowerCase().contains(term.toLowerCase())).toList();
    }
}
