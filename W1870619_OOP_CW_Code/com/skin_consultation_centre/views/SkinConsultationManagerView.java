package com.skin_consultation_centre.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.skin_consultation_centre.Consultation;
import com.skin_consultation_centre.Doctor;
import com.skin_consultation_centre.WestminsterSkinConsultationManager;

public class SkinConsultationManagerView {
    JFrame frame;
    JTable tableDoctors;
    JPanel bodyPanel;
    JPanel headerPanel;
    JPanel doctorsSidePanel;
    JPanel doctorsContentWrapper;
    JPanel consultationsSidePanel;
    JPanel consultationsContentWrapper;
    JPanel consultationsListPanel;
    Doctor selectedDoctor;
    JButton btnSort;
    JButton btnAddConsultation;
    JPanel consultationItemPanel;
    JLabel heading1;
    JLabel heading2;
    JLabel heading3;
    JLabel lbl1;
    JLabel lbl2;
    JLabel lbl3;
    JScrollPane scrollPane1;
    JScrollPane scrollPane2;

    // Doctor table headers
    private static final String[] doctorsTableColumns = { "Name", "Surname", "Date of birth", "Mobile number",
            "License no:", "Specialisation" };

    private static WestminsterSkinConsultationManager wscm = WestminsterSkinConsultationManager
            .getManager();

    public JFrame start() {
        // Main frame
        frame = new JFrame("Skin Consultation Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 720);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Panel 1
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 40));
        headerPanel.setBorder(new EmptyBorder(0, 50, 0, 50));

        heading1 = new JLabel("Westminster Skin Consultation Center", JLabel.CENTER);
        heading1.setForeground(Color.WHITE);
        heading1.setFont(new Font("Sans-serif", Font.BOLD, 20));
        headerPanel.setBackground(new Color(89, 6, 150));
        headerPanel.add(heading1);
        frame.getContentPane().add(headerPanel);

        // Body panel
        bodyPanel = new JPanel();
        bodyPanel.setPreferredSize(new Dimension(frame.getWidth(), 860));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
        bodyPanel.setBackground(new Color(239, 225, 250));

        // Doctors side panel
        doctorsSidePanel = new JPanel();
        doctorsSidePanel.setLayout(new BoxLayout(doctorsSidePanel, BoxLayout.Y_AXIS));
        doctorsSidePanel.setBorder(new EmptyBorder(15, 25, 25, 15));
        doctorsSidePanel.setBackground(new Color(238, 238, 238));

        doctorsContentWrapper = new JPanel();
        doctorsContentWrapper.setBorder(new EmptyBorder(20, 0, 30, 0));
        doctorsContentWrapper.setBackground(new Color(238, 238, 238));

        heading2 = new JLabel("Doctors list", JLabel.CENTER);
        heading2.setFont(new Font("Sans-serif", Font.BOLD, 18));
        heading2.setBorder(new EmptyBorder(0, 0, 0, 30));
        heading2.setForeground(Color.BLACK);
        doctorsContentWrapper.add(heading2);

        btnSort = new JButton("Sort list");
        btnSort.setFont(new Font("Sans-serif", Font.BOLD, 14));
        btnSort.setPreferredSize(new Dimension(150, 36));
        btnSort.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnSortClick();
            }
        });
        doctorsContentWrapper.add(btnSort);

        doctorsSidePanel.add(doctorsContentWrapper);

        // Scrollepane 1 with doctor table
        tableDoctors = new JTable(new DefaultTableModel(doctorsTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tableDoctors.setShowHorizontalLines(true);
        tableDoctors.setFont(new Font("Sans-serif", Font.PLAIN, 13));
        tableDoctors.getTableHeader().setFont(new Font("Sans-serif", Font.BOLD, 13));
        tableDoctors.getTableHeader().setPreferredSize(new Dimension(400, 28));

        tableDoctors.setRowHeight(28);
        tableDoctors.setDragEnabled(false);
        tableDoctors.setSelectionMode(0);

        tableDoctors.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                setSelectedDoctor(
                        Integer.parseInt(tableDoctors.getValueAt(tableDoctors.getSelectedRow(), 4).toString()));

            }
        });
        generateDoctorTableData(false);
        scrollPane1 = new JScrollPane(tableDoctors);
        scrollPane1.setPreferredSize(new Dimension(550, 400));
        scrollPane1.setBackground(new Color(196, 220, 224));
        doctorsSidePanel.add(scrollPane1);

        bodyPanel.add(doctorsSidePanel);

        // Consultations side panel
        consultationsSidePanel = new JPanel();
        consultationsSidePanel.setLayout(new BoxLayout(consultationsSidePanel, BoxLayout.Y_AXIS));
        consultationsSidePanel.setBorder(new EmptyBorder(15, 15, 25, 25));
        consultationsSidePanel.setBackground(new Color(238, 238, 238));

        consultationsContentWrapper = new JPanel();

        consultationsContentWrapper.setBorder(new EmptyBorder(20, 0, 30, 0));
        consultationsContentWrapper.setBackground(new Color(238, 238, 238));
        heading3 = new JLabel("Consultations list", JLabel.CENTER);
        heading3.setFont(new Font("Sans-serif", Font.BOLD, 18));
        heading3.setBorder(new EmptyBorder(0, 0, 0, 30));
        heading3.setForeground(Color.BLACK);
        consultationsContentWrapper.add(heading3);

        //consultation button
        btnAddConsultation = new JButton("Add consultation");
        btnAddConsultation.setFont(new Font("Sans-serif", Font.BOLD, 14));
        btnAddConsultation.setPreferredSize(new Dimension(180, 36));
        btnAddConsultation.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnAddConsultationClick();

            }
        });
        consultationsContentWrapper.add(btnAddConsultation);

        consultationsSidePanel.add(consultationsContentWrapper);

        // Scrollpane 2 with consultations list
        consultationsListPanel = new JPanel();
        consultationsListPanel.setLayout(new BoxLayout(consultationsListPanel, BoxLayout.Y_AXIS));
        consultationsListPanel.setBackground(new Color(215, 221, 241));
        generateConsultationListData();
        scrollPane2 = new JScrollPane(consultationsListPanel);
        scrollPane2.setPreferredSize(new Dimension(100, 400));
        scrollPane2.setBackground(new Color(215, 221, 241));
        consultationsSidePanel.add(scrollPane2);

        bodyPanel.add(consultationsSidePanel);
        frame.getContentPane().add(bodyPanel);
        frame.setVisible(true);
        return frame;
    }

    // Generate doctor table
    private void generateDoctorTableData(boolean isSorted) {
        ArrayList<Doctor> doctors = wscm.getDoctors();

        // Sort doctors
        if (isSorted) {
            Collections.sort(doctors);
        }

        DefaultTableModel dtm = (DefaultTableModel) tableDoctors.getModel();
        dtm.setRowCount(0);

        // Add doctors to table
        for (Doctor d : doctors) {

            String[] rowData = new String[] { d.getName(), d.getSurname(),
                    d.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    d.getMobileNumber(),
                    String.valueOf(d.getMedicalLicenseNumber()),
                    d.getSpecialisation()
            };

            dtm.addRow(rowData);
        }

        frame.revalidate();
        frame.repaint();
    }

    // Generate consultations list
    public void generateConsultationListData() {
        if (wscm.getConsultations().isEmpty()) {
            consultationsContentWrapper = new JPanel();
            consultationsContentWrapper.setBorder(null);
            consultationsContentWrapper.setBackground(Color.WHITE);
            lbl1 = new JLabel("No consultations added yet", JLabel.CENTER);
            lbl1.setFont(new Font("Sans-serif", Font.PLAIN, 22));
            lbl1.setPreferredSize(new Dimension(600, 100));
            lbl1.setForeground(new Color(231, 76, 60));
            consultationsContentWrapper.add(lbl1);
            consultationsListPanel.add(consultationsContentWrapper);

        } else {
            consultationsListPanel.removeAll();
            for (Consultation c : wscm.getConsultations()) {
                consultationItemPanel = new JPanel();
                consultationItemPanel.setPreferredSize(new Dimension(400, 10));
                consultationItemPanel.setBackground(Color.WHITE);

                lbl2 = new JLabel(c.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm")));
                lbl2.setFont(new Font("Sans-serif", Font.BOLD, 13));
                lbl2.setBorder(new EmptyBorder(0, 0, 0, 20));
                consultationItemPanel.add(lbl2);

                lbl3 = new JLabel(String.format("Patient %d with Dr. %s %s", c.getPatient().getPatientId(),
                        c.getDoctor().getName(), c.getDoctor().getSurname()));
                // lbl3.setPreferredSize(new Dimension(450, 10));
                lbl3.setFont(new Font("Sans-serif", Font.PLAIN, 13));
                lbl3.setBorder(new EmptyBorder(0, 15, 0, 40));
                consultationItemPanel.add(lbl3);

                JButton btn1 = new JButton("View");
                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showConsultationDetails(c);
                    }
                });

                consultationItemPanel.add(btn1);
                consultationsListPanel.add(consultationItemPanel);
            }
        }

        // Update frame
        SwingUtilities.updateComponentTreeUI(frame);
        frame.revalidate();
        frame.repaint();

    }

    private void handleBtnSortClick() {
        generateDoctorTableData(true);
    }

    private void handleBtnAddConsultationClick() {
        if (selectedDoctor != null) {
            openAddConsultationFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a doctor first");
        }
    }

    private void setSelectedDoctor(int medicalLicenseNumber) {
        for (Doctor d : wscm.getDoctors()) {
            if (d.getMedicalLicenseNumber() == medicalLicenseNumber) {
                selectedDoctor = d;
                break;
            }
        }
    }

    private JFrame openAddConsultationFrame() {
        AddConsultationView gui = new AddConsultationView(this, selectedDoctor);
        return gui.start();
    }

    private JFrame showConsultationDetails(Consultation consultation) {
        ViewConsultation gui = new ViewConsultation(consultation);
        return gui.start();
    }
}
