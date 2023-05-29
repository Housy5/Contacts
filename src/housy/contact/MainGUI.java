package housy.contact;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class MainGUI extends javax.swing.JFrame {

    public static final String DEFAULT_TITLE = "Contacts";

    private final SearchIndex index;
    private ContactSerializer serializer;
    private Contact currentContact;
    private List<Contact> contacts;

    private void initIconImage() {
        try {
            Image img = ImageIO.read(getClass().getResource("/res/contact-book.png"));
            setIconImage(img);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load the image icon!");
        }
    }

    private void loadContacts() {
        try {
            contacts = serializer.loadContacts();
            index.addAll(contacts);
        } catch (IOException | ClassNotFoundException e) {
            contacts = new ArrayList<>();
        }
    }

    private void initContactSerializer() {
        try {
            serializer = new ContactSerializer("contacts.cs");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to initialize the serializer...\nMaybe try running as administrator?");
            System.exit(1);
        }
    }

    public MainGUI() {
        initComponents();
        initIconImage();
        lock();

        contacts = new ArrayList<>();
        initContactSerializer();
        index = new SearchIndex();
        loadContacts();
        updateList();
    }

    private void updateList() {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        List<String> names = contacts.stream().map(Contact::getName).sorted().toList();
        dlm.addAll(names);
        contactList.setModel(dlm);
    }

    private boolean isUsedName(String name) {
        return contacts.stream().map(Contact::getName).filter(x -> x.equalsIgnoreCase(name)).count() > 0;
    }

    private String getDisplayedName() {
        String name = nameField.getText();
        return name == null ? " " : name.trim();
    }

    private String getDisplayedEmail() {
        String email = emailField.getText();
        return email == null ? " " : email.trim();
    }

    private String getDisplayedPhone() {
        String phone = phoneField.getText();
        return phone == null ? " " : phone.trim();
    }

    private String getDisplayedNotes() {
        String notes = noteArea.getText();
        if (notes == null || notes.isBlank())
            return " ";
        return notes;
    }

    public void saveNewContact() {
        String name = getDisplayedName().replaceAll("/", " ");
        String phone = getDisplayedPhone().replaceAll("/", " ");
        String email = getDisplayedEmail().replaceAll("/", " ");
        String notes = getDisplayedNotes();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name first!");
            return;
        }

        if (isUsedName(name)) {
            JOptionPane.showMessageDialog(this, "This name has already been used!\nPlease provide a different name.");
            return;
        }

        Contact c = new Contact(name, phone, email);
        c.setNotes(notes);
        contacts.add(c);
        index.addContact(c);
        updateList();
        clear();
        lock();
        save();
    }

    public void save() {
        try {
            serializer.save(contacts);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save the contacts!");
        }
    }

    public void saveEdit() {
        String name = getDisplayedName();
        String phone = getDisplayedPhone();
        String email = getDisplayedEmail();
        String notes = getDisplayedNotes();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name first!");
            return;
        }

        if (!name.equalsIgnoreCase(currentContact.getName())) {
            if (isUsedName(name)) {
                JOptionPane.showMessageDialog(this, "This name has already been used!\nPlease provide a different name.");
                return;
            }

            currentContact.setName(name);
        }

        currentContact.setPhone(phone);
        currentContact.setEmail(email);
        currentContact.setNotes(notes);
        updateList();
        lock();
        save();
    }

    public void loadContact(Contact contact) {
        if (contact.equals(currentContact))
            return;

        if (contact instanceof EmptyContact)
            return;

        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        noteArea.setText(contact.getNotes());

        setTitleMessage(contact.getName());
        currentContact = contact;
        lock();
    }

    public void clear() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        noteArea.setText("");
        currentContact = null;
    }

    public void unlock() {
        nameField.setEditable(true);
        phoneField.setEditable(true);
        emailField.setEditable(true);
        noteArea.setEditable(true);
    }

    public void lock() {
        nameField.setEditable(false);
        phoneField.setEditable(false);
        emailField.setEditable(false);
        noteArea.setEditable(false);
    }

    public void setTitleMessage(String msg) {
        setTitle(DEFAULT_TITLE + " - " + msg);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        infoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        noteArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(DEFAULT_TITLE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacts"));
        jPanel1.setMinimumSize(new java.awt.Dimension(268, 153));
        jPanel1.setLayout(new java.awt.CardLayout());

        contactList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        contactList.setFixedCellWidth(contactList.getWidth()
        );
        contactList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactListMouseClicked(evt);
            }
        });
        contactList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                contactListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(contactList);

        jPanel1.add(jScrollPane2, "card2");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact info"));

        jLabel1.setText("Name: ");

        jLabel2.setText("Phone:");

        jLabel3.setText("Email");

        jLabel4.setText("Notes: ");

        noteArea.setColumns(20);
        noteArea.setRows(5);
        jScrollPane1.setViewportView(noteArea);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        jButton2.setText("New");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton5.setText("Save");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel3.add(jButton5);

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean isLeft(MouseEvent evt) {
        return SwingUtilities.isLeftMouseButton(evt);
    }

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if (!isLeft(evt))
            return;
        setTitleMessage("New user");
        unlock();
        clear();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        if (!isLeft(evt))
            return;

        if (currentContact == null) {
            saveNewContact();
            return;
        }

        saveEdit();
    }//GEN-LAST:event_jButton5MouseClicked

    private void contactListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_contactListValueChanged
        String selection = contactList.getSelectedValue();
        if (selection == null || selection.isBlank())
            return;
        Contact c = contacts.stream().filter(x -> x.getName().equalsIgnoreCase(selection)).findFirst().get();
        loadContact(c);
    }//GEN-LAST:event_contactListValueChanged

    private void contactListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactListMouseClicked

    }//GEN-LAST:event_contactListMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        unlock();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (currentContact == null)
            return;
        int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + currentContact.getName() + "?");
        if (opt != JOptionPane.YES_OPTION)
            return;
        contacts.remove(currentContact);
        index.remove(currentContact);
        currentContact = null;
        lock();
        clear();
        updateList();
        save();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SearchDialog dialog = new SearchDialog(this, true, index);
        dialog.setVisible(true);
        String selectedName = dialog.getSelection();
        if (selectedName.isBlank())
            return;
        Contact c = contacts.stream()
                .filter(x -> x.getName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(new EmptyContact());
        loadContact(c);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> contactList;
    private javax.swing.JTextField emailField;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextArea noteArea;
    private javax.swing.JTextField phoneField;
    // End of variables declaration//GEN-END:variables
}
