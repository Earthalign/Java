package ksiegarnia;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;



class Okno extends JFrame {
                                                               

   
    // Komunikacja z BAZĄ DANYCH
    private String jdbcUrl = "jdbc:mysql://localhost:3306/ksiegarnia", jdbcUser = "root", jdbcPass = "";  //Łączenie się z bazą danych *głównie w xampie*
    // Komunikaty z applikacji
    private JTextField komunikat = new JTextField();  
    
    
    // Panel ogólny
    private JTabbedPane tp = new JTabbedPane();
    private JPanel p_kli = new JPanel(); // Clients
    private JPanel p_ksi = new JPanel(); // Books
    private JPanel p_zam = new JPanel(); // Orders
    
    // Panel klienta
    
    private JTextField pole_pesel = new JTextField(); //odczytywanie danych z jednej linii 
    private JTextField pole_im = new JTextField();
    private JTextField pole_naz = new JTextField();
    private JTextField pole_ur = new JTextField();
    private JTextField pole_mail = new JTextField();
    private JTextField pole_adr = new JTextField();
    private JTextField pole_tel = new JTextField();
    private JButton przyc_zapisz_kli = new JButton("zapisz");   //przyciski
    private JButton przyc_usun_kli = new JButton("usuń");
    private DefaultListModel<String> lmodel_kli = new DefaultListModel<>(); //Przygotowana klasa, która ułatwia tworzenie dynamicznych list
    private JList<String> l_kli = new JList<>(lmodel_kli);
    private JScrollPane sp_kli = new JScrollPane(l_kli);  //Panel przewijany - JScrollPane. Służy do przedstawiania komponentów, których rozmiar jest większy niż widoczny w panelu obszar. Do przewijania widoku komponentu przeznaczone są suwaki.

    // Panel książęk
    
    private JTextField pole_isbn = new JTextField();
    private JTextField pole_autor = new JTextField();
    private JTextField pole_tytul = new JTextField();
    private String[] typy = {
        "WYBIERZ",  "kawa", "alkohole", "młodzieżowa", "dziecięca", "reportaż", "podręcznik", 
        "horror", "biografia", "fantastyka", "podroze", "sensacja", "gastronomia",
        "obyczajowa", "poradnik", "kryminal", 
        "historyczna", "thriller", "romans", "popularnonaukowa","wesoła twórczość", "inne"
      };
    private JComboBox pole_typ = new JComboBox(typy); //zaznaczenie danego pola
    private JTextField pole_wyd = new JTextField();
    private JTextField pole_rok = new JTextField();
    private JTextField pole_cena = new JTextField();
    private JButton przyc_zapisz_ksi = new JButton("zapisz");
    private JButton przyc_usun_ksi = new JButton("usuń");
    private JButton przyc_zmien_cene = new JButton("zmień cenę");
    private DefaultListModel<String> lmodel_ksi = new DefaultListModel<>();
    private JList<String> l_ksi = new JList<>(lmodel_ksi); //używany jest specjalny obiekt, określający zasady wykreślania tych komórek.
    private JScrollPane sp_ksi = new JScrollPane(l_ksi);
   
    
    
    // Panel dla zamówień
    private JTextField pole_pesel_zam = new JTextField();
    private JButton przyc_zapisz_zam = new JButton("zamów");
    private JButton przyc_zmien_st = new JButton("zmień status");
    private JSpinner pole_ilosc = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private DefaultListModel<String> lmodel_zam_ksi = new DefaultListModel<>();
    private JList<String> l_zam_ksi = new JList<>(lmodel_zam_ksi);
    private JScrollPane sp_zam_ksi = new JScrollPane(l_zam_ksi);
    private DefaultListModel<String> lmodel_zam = new DefaultListModel<>();
    private JList<String> l_zam = new JList<>(lmodel_zam);
    private JScrollPane sp_zam = new JScrollPane(l_zam);
    
    
    //Aktualizowanie klientów
    private void AktualnaListaKlientów(JList<String> lis) {
        try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT pesel, imie, nazwisko FROM klienci ORDER BY nazwisko, imie";
            ResultSet res = stmt.executeQuery(sql);
            lmodel_kli.clear();
            while(res.next()) {
                String s = res.getString(3) + " " + res.getString(2) + ": " + res.getString(1);
                lmodel_kli.addElement(s);
            }
        }
        catch (SQLException ex) { }
    }
    // funkcja aktualizująca listę książek
    private void AktualnaListaKsiazek(JList<String> lis, DefaultListModel<String> model) {
        try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT tytul, autor, cena, isbn FROM ksiazki ORDER BY autor, tytul";
            ResultSet res = stmt.executeQuery(sql);
            model.clear();
            while(res.next()) {
                String s = res.getString(2) + " - " + res.getString(1) + ", " + res.getString(3) + "zł, isbn: " + res.getString(4);
                model.addElement(s);
            }
        }
        catch (SQLException ex) { } //wychwytywanie błędów
    }
    // funkcja aktualizująca listę zamówień
    private void AktualnaListaZamowien(JList<String> lis, DefaultListModel<String> model) {
        try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT pesel, isbn, ilosc, status, id FROM zamowienia ORDER BY pesel";
            ResultSet res = stmt.executeQuery(sql);
            model.clear();
            while(res.next()) {
                String s = res.getString(1) + " zamówił " + res.getString(2) + " w ilości " + res.getString(3) + ", status: "+res.getString(4) + ", id: "+res.getString(5);
                model.addElement(s);
            }
        }
        catch (SQLException ex) { }
    }
    
    
    /* obsługa akcji  
    
    
    od przycisku 'zapisz klienta'

    */
    
    private ActionListener akc_zap_kl = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String pesel = pole_pesel.getText();
            if (pesel.length() < 3) {
                JOptionPane.showMessageDialog(Okno.this, "błąd w polu z peselem");
                pole_pesel.setText("");
                pole_pesel.requestFocus();
                return;
            }
            String imie = pole_im.getText();
            String nazwisko = pole_naz.getText();
            String ur = pole_ur.getText();
            if (imie.equals("") || nazwisko.equals("") || ur.equals("")) {
                JOptionPane.showMessageDialog(Okno.this, "nie wypełnione pole z imieniem lub nazwiskiem lub datą urodzenia");
                return;
            }
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql1 = "INSERT INTO klienci(pesel, imie, nazwisko, ur) VALUES('" + pole_pesel.getText() + "', '" + pole_im.getText() + "', '" + pole_naz.getText() + "', '" + pole_ur.getText() + "')";
                int res1 = stmt.executeUpdate(sql1);
                String sql2 = "INSERT INTO kontakty(pesel, mail, adres, tel) VALUES('" + pole_pesel.getText() + "', '" + pole_mail.getText() + "', '" + pole_adr.getText() + "', '" + pole_tel.getText() + "')";
                int res2 = stmt.executeUpdate(sql2);
                if (res1 == 1 && res2 == 1) {
                    komunikat.setText("OK - klient dodany do bazy");
                    l_kli.repaint();
                    AktualnaListaKlientów(l_kli);
                }
                else komunikat.setText("Błąd: nie wpisano klienta do bazy");
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }
        }
    };

     /* obsługa akcji  
    
    
   usuwania klienta

    */
    private ActionListener akc_usun_kli = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (l_kli.getSelectionModel().getSelectedItemsCount() == 0) return;
            String p = l_kli.getModel().getElementAt(l_kli.getSelectionModel().getMinSelectionIndex());
            p = p.substring(p.indexOf(':')+2, p.length());
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT COUNT(*) FROM zamowienia WHERE pesel = '" + p + "'";
                ResultSet res = stmt.executeQuery(sql);
                res.next();
                int k = res.getInt(1);
                if (k == 0) {
                    String sql1 = "DELETE FROM klienci WHERE pesel = '" + p + "'";
                    stmt.executeUpdate(sql1);
                    String sql2 = "DELETE FROM kontakty WHERE pesel = '" + p + "'";
                    stmt.executeUpdate(sql2);
                    komunikat.setText("OK - klient usunięty bazy");
                    AktualnaListaKlientów(l_kli);
                }
                else komunikat.setText("nie usunięto klienta, ponieważ składał już zamówienia");
            }
            catch (SQLException ex) {
                komunikat.setText("błąd SQL - nie usunięto klienta");
            }
        }
    };

        /* obsługa akcji  
    
    
    książek

    */
    private ActionListener akc_zap_ks = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String typ = (String)pole_typ.getSelectedItem();
            if (typ == "WYBIERZ") {
                JOptionPane.showMessageDialog(Okno.this, "błąd w polu typ");
                pole_typ.requestFocus();
                return;
            }
            String isbn = pole_isbn.getText();
            String autor = pole_autor.getText();
            String tytul = pole_tytul.getText();
            String wyd = pole_wyd.getText();
            String rok = pole_rok.getText();
            String cena = pole_cena.getText();
            if (autor.equals("") || tytul.equals("") || typ.equals("") || cena.equals("")) {
                JOptionPane.showMessageDialog(Okno.this, "nie wypełnione pole z autorem lub tytulem lub typem lub cena");
                return;
            }
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO ksiazki(isbn, autor, tytul, typ, wydawnictwo, rok, cena) VALUES('" + pole_isbn.getText() + "', '" + pole_autor.getText() + "', '" + pole_tytul.getText() + "', '" + pole_typ.getSelectedItem() + "', '" + pole_wyd.getText() + "', '" + pole_rok.getText() + "', '" + pole_cena.getText() + "')";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK - ksiazka dodana do bazy");
                    l_ksi.repaint();
                    AktualnaListaKsiazek(l_ksi, lmodel_ksi);
                    AktualnaListaKsiazek(l_zam_ksi, lmodel_zam_ksi);
                }
                else komunikat.setText("Błąd: nie wpisano ksiazki do bazy");
            }
            catch(SQLException ex) {
                komunikat.setText("Błąd SQL, spróbuj podać inne dane");
            }
        }
    };

        /* obsługa akcji  
    
    
    Zmiany cennika

    */
    private ActionListener akc_zmien_cene = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (l_ksi.getSelectionModel().getSelectedItemsCount() == 0){
                JOptionPane.showMessageDialog(Okno.this, "nie została wybrana żadna książka");
                return;
            }
            JFrame fr = new JFrame();
            Object result = JOptionPane.showInputDialog(fr, "Zmień cenę:");
            double cena = Double.parseDouble((String)result);
            String p = l_ksi.getModel().getElementAt(l_ksi.getSelectionModel().getMinSelectionIndex());
            p = p.substring(p.indexOf("isbn:")+6, p.length());
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE ksiazki SET cena = "+cena+" WHERE isbn="+p+";";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK - cena zmieniona");
                    l_ksi.repaint();
                    AktualnaListaKsiazek(l_ksi, lmodel_ksi);
                }
                else komunikat.setText("Błąd: nie zmieniono statusu");
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }
        }
    };

        /* obsługa akcji  
    
    
    Usuwania książek

    */
    private ActionListener akc_usun_ksi = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (l_ksi.getSelectionModel().getSelectedItemsCount() == 0){
                JOptionPane.showMessageDialog(Okno.this, "nie została wybrana żadna książka");
                return;
            }
            String p = l_ksi.getModel().getElementAt(l_ksi.getSelectionModel().getMinSelectionIndex());
            p = p.substring(p.indexOf("isbn:")+6, p.length());
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT COUNT(*) FROM zamowienia WHERE isbn = '" + p + "'";
                ResultSet res = stmt.executeQuery(sql);
                res.next();
                int k = res.getInt(1);
                if (k == 0) {
                    String sql1 = "DELETE FROM ksiazki WHERE isbn = '" + p + "'";
                    stmt.executeUpdate(sql1);
                    komunikat.setText("OK - książka usunięta z bazy");
                    AktualnaListaKsiazek(l_ksi, lmodel_ksi);
                    AktualnaListaKsiazek(l_zam_ksi, lmodel_zam_ksi);
                }
                else komunikat.setText("nie usunięto książki, ponieważ jest ona zamówiona");
            }
            catch (SQLException ex) {
                komunikat.setText("błąd SQL - nie ununięto książki");
            }
        }
    };

       /* obsługa akcji  
    
    
    Dodawanie zamówień

    */
    private ActionListener akc_zamow = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (l_zam_ksi.getSelectionModel().getSelectedItemsCount() == 0){
                JOptionPane.showMessageDialog(Okno.this, "nie została wybrana żadna książka");
                return;
            }
            String p = l_zam_ksi.getModel().getElementAt(l_zam_ksi.getSelectionModel().getMinSelectionIndex());
            p = p.substring(p.indexOf("isbn:")+6, p.length());
            String zamawiajacy = pole_pesel_zam.getText();
            int ilosc = (int)pole_ilosc.getValue();
            if (zamawiajacy.equals("")) {
                JOptionPane.showMessageDialog(Okno.this, "nie wypełnione pole z peselem klienta");
                return;
            }
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT COUNT(*) FROM klienci WHERE pesel = '" + pole_pesel_zam.getText() + "'";
                ResultSet reslt = stmt.executeQuery(sql);
                reslt.next();
                int k = reslt.getInt(1);
                if (k==0) {
                    JOptionPane.showMessageDialog(Okno.this, "nie istnieje klient z takim peselem");
                    return;
                }
                stmt = conn.createStatement();
                sql = "INSERT INTO zamowienia(pesel, isbn, ilosc, kiedy, status) VALUES('"+pole_pesel_zam.getText()+"', '"+p+"', "+pole_ilosc.getValue()+", '" + java.time.LocalDate.now() + "', 'oczekuje')";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK - zamówienie dodane do bazy");
                    l_zam.repaint();
                    AktualnaListaZamowien(l_zam, lmodel_zam);
                }
                else komunikat.setText("Błąd: nie wpisano zamówienia do bazy");
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }
        }
    };
        /* obsługa akcji  
    
    
    Zmiany statusu

    */
    private final ActionListener akc_zmien_st = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (l_zam.getSelectionModel().getSelectedItemsCount() == 0){
                JOptionPane.showMessageDialog(Okno.this, "nie zostało wybrane żadne zamówienie");
                return;
            }
            JDialog.setDefaultLookAndFeelDecorated(true);
            Object[] selectionValues = { "oczekuje", "wysłane", "zapłacone" };
            String initialSelection = "oczekuje";
            Object selection = JOptionPane.showInputDialog(null, "Jaki status chcesz nadać?",
                    "Zmiana statusu", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            String id = l_zam.getModel().getElementAt(l_zam.getSelectionModel().getMinSelectionIndex());
            id = id.substring(id.indexOf("id:")+4, id.length());
            try (Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE zamowienia SET status = '"+selection+"' WHERE id="+id+";";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK - status zmieniony");
                    l_zam.repaint();
                    AktualnaListaZamowien(l_zam, lmodel_zam);
                }
                else komunikat.setText("Błąd: nie zmieniono statusu");
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }
        }
    };

    public Okno() throws SQLException {
        super("Księgarnia wysyłkowa");
        setSize(720, 460);
        setLocation(100, 100);
        setResizable(false);


        // panel do zarządzania klientami
        p_kli.setLayout(null);
        // pole z peselem
        JLabel lab1 = new JLabel("pesel:");
        p_kli.add(lab1);
        lab1.setSize(100, 20);
        lab1.setLocation(40, 40);
        lab1.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_pesel);
        pole_pesel.setSize(200, 20);
        pole_pesel.setLocation(160, 40);
        // pole z imieniem
        JLabel lab2 = new JLabel("imię:");
        p_kli.add(lab2);
        lab2.setSize(100, 20);
        lab2.setLocation(40, 80);
        lab2.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_im);
        pole_im.setSize(200, 20);
        pole_im.setLocation(160, 80);
        // pole z nazwiskiem
        JLabel lab3 = new JLabel("nazwisko:");
        p_kli.add(lab3);
        lab3.setSize(100, 20);
        lab3.setLocation(40, 120);
        lab3.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_naz);
        pole_naz.setSize(200, 20);
        pole_naz.setLocation(160, 120);
        
        // pole z datą urodzenia
        JLabel lab4 = new JLabel("data ur (r-m-d):");
        p_kli.add(lab4);
        lab4.setSize(100, 20);
        lab4.setLocation(40, 160);
        lab4.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_ur);
        pole_ur.setSize(200, 20);
        pole_ur.setLocation(160, 160);
        
        // pole z mailem
        JLabel lab5 = new JLabel("mail:");
        p_kli.add(lab5);
        lab5.setSize(100, 20);
        lab5.setLocation(40, 200);
        lab5.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_mail);
        pole_mail.setSize(200, 20);
        pole_mail.setLocation(160, 200);
        
        // pole z adresem
        JLabel lab6 = new JLabel("adres:");
        p_kli.add(lab6);
        lab6.setSize(100, 20);
        lab6.setLocation(40, 240);
        lab6.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_adr);
        pole_adr.setSize(200, 20);
        pole_adr.setLocation(160, 240);
        
        // pole z nr telefonu
        JLabel lab7 = new JLabel("telefon:");
        p_kli.add(lab7);
        lab7.setSize(100, 20);
        lab7.setLocation(40, 280);
        lab7.setHorizontalTextPosition(JLabel.RIGHT);
        p_kli.add(pole_tel);
        pole_tel.setSize(200, 20);
        pole_tel.setLocation(160, 280);
        
        // zapis klienta
        p_kli.add(przyc_zapisz_kli);
        przyc_zapisz_kli.setSize(200, 20);
        przyc_zapisz_kli.setLocation(160, 320);
        przyc_zapisz_kli.addActionListener(akc_zap_kl);
        
        // usunięcie klienta
        p_kli.add(przyc_usun_kli);
        przyc_usun_kli.setSize(200, 20);
        przyc_usun_kli.setLocation(400, 320);
        przyc_usun_kli.addActionListener(akc_usun_kli);
        
        
        // lista z klientami
        p_kli.add(sp_kli);
        sp_kli.setSize(240, 260);
        sp_kli.setLocation(400, 40);
        AktualnaListaKlientów(l_kli);


        // panel do zarządzania ksiazkami
        p_ksi.setLayout(null);
        // pole z isbn
        JLabel ksilab1 = new JLabel("isbn:");
        p_ksi.add(ksilab1);
        ksilab1.setSize(100, 20);
        ksilab1.setLocation(40, 40);
        ksilab1.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_isbn);
        pole_isbn.setSize(200, 20);
        pole_isbn.setLocation(160, 40);
        
        // pole z autorem
        JLabel ksilab2 = new JLabel("autor:");
        p_ksi.add(ksilab2);
        ksilab2.setSize(100, 20);
        ksilab2.setLocation(40, 80);
        ksilab2.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_autor);
        pole_autor.setSize(200, 20);
        pole_autor.setLocation(160, 80);
        
        // pole z tytułem
        JLabel ksilab3 = new JLabel("tytuł:");
        p_ksi.add(ksilab3);
        ksilab3.setSize(100, 20);
        ksilab3.setLocation(40, 120);
        ksilab3.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_tytul);
        pole_tytul.setSize(200, 20);
        pole_tytul.setLocation(160, 120);
        
        // pole z typem
        JLabel ksilab4 = new JLabel("typ:");
        p_ksi.add(ksilab4);
        ksilab4.setSize(100, 20);
        ksilab4.setLocation(40, 160);
        ksilab4.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_typ);
        pole_typ.setSize(200, 20);
        pole_typ.setLocation(160, 160);
        
        // pole z wydawnictwo
        JLabel ksilab5 = new JLabel("wydawnictwo:");
        p_ksi.add(ksilab5);
        ksilab5.setSize(100, 20);
        ksilab5.setLocation(40, 200);
        ksilab5.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_wyd);
        pole_wyd.setSize(200, 20);
        pole_wyd.setLocation(160, 200);
        
        // pole z rokiem
        JLabel ksilab6 = new JLabel("rok:");
        p_ksi.add(ksilab6);
        ksilab6.setSize(100, 20);
        ksilab6.setLocation(40, 240);
        ksilab6.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_rok);
        pole_rok.setSize(200, 20);
        pole_rok.setLocation(160, 240);
        
        // pole z ceną
        JLabel ksilab7 = new JLabel("cena:");
        p_ksi.add(ksilab7);
        ksilab7.setSize(100, 20);
        ksilab7.setLocation(40, 280);
        ksilab7.setHorizontalTextPosition(JLabel.RIGHT);
        p_ksi.add(pole_cena);
        pole_cena.setSize(200, 20);
        pole_cena.setLocation(160, 280);
        
        // przycisk do zapisu książki
        p_ksi.add(przyc_zapisz_ksi);
        przyc_zapisz_ksi.setSize(200, 20);
        przyc_zapisz_ksi.setLocation(160, 320);
        przyc_zapisz_ksi.addActionListener(akc_zap_ks);
        
        
        // przycisk do zmiany ceny
        p_ksi.add(przyc_zmien_cene);
        przyc_zmien_cene.setSize(200, 20);
        przyc_zmien_cene.setLocation(400, 310);
        przyc_zmien_cene.addActionListener(akc_zmien_cene);
        
        // przycisk do usunięcia książki
        p_ksi.add(przyc_usun_ksi);
        przyc_usun_ksi.setSize(200, 20);
        przyc_usun_ksi.setLocation(400, 340);
        przyc_usun_ksi.addActionListener(akc_usun_ksi);
        
        // lista z książkami
        p_ksi.add(sp_ksi);
        sp_ksi.setSize(240, 260);
        sp_ksi.setLocation(400, 40);
        AktualnaListaKsiazek(l_ksi, lmodel_ksi);


        // panel do zarządzania zamówieniami
        p_zam.setLayout(null);
        
        // pole z peselem
        JLabel zamlab1 = new JLabel("pesel klienta:");
        p_zam.add(zamlab1);
        zamlab1.setSize(100, 20);
        zamlab1.setLocation(40, 20);
        zamlab1.setHorizontalTextPosition(JLabel.RIGHT);
        p_zam.add(pole_pesel_zam);
        pole_pesel_zam.setSize(150, 20);
        pole_pesel_zam.setLocation(160, 20);
        
        // lista z książkami
        JLabel zamlab2 = new JLabel("wybór książki:");
        p_zam.add(zamlab2);
        zamlab2.setSize(100, 20);
        zamlab2.setLocation(40, 60);
        zamlab2.setHorizontalTextPosition(JLabel.RIGHT);
        p_zam.add(sp_zam_ksi);
        sp_zam_ksi.setSize(240, 200);
        sp_zam_ksi.setLocation(40, 80);
        AktualnaListaKsiazek(l_zam_ksi, lmodel_zam_ksi);
        
        // pole z ilością
        JLabel zamlab3 = new JLabel("ilość:");
        p_zam.add(zamlab3);
        zamlab3.setSize(100, 20);
        zamlab3.setLocation(40, 300);
        zamlab3.setHorizontalTextPosition(JLabel.RIGHT);
        p_zam.add(pole_ilosc);
        pole_ilosc.setSize(60, 20);
        pole_ilosc.setLocation(100, 300);
        
        // przycisk do zamawiania
        p_zam.add(przyc_zapisz_zam);
        przyc_zapisz_zam.setSize(200, 20);
        przyc_zapisz_zam.setLocation(80, 340);
        przyc_zapisz_zam.addActionListener(akc_zamow);
        
        // lista z zamówieniami
        JLabel zamlab4 = new JLabel("zamówienia:");
        p_zam.add(zamlab4);
        zamlab4.setSize(100, 20);
        zamlab4.setLocation(400, 20);
        zamlab4.setHorizontalTextPosition(JLabel.RIGHT);
        p_zam.add(sp_zam);
        sp_zam.setSize(240, 260);
        sp_zam.setLocation(400, 40);
        AktualnaListaZamowien(l_zam, lmodel_zam);
        
        // przycisk do zmiany statusu
        p_zam.add(przyc_zmien_st);
        przyc_zmien_st.setSize(200, 20);
        przyc_zmien_st.setLocation(400, 340);
        przyc_zmien_st.addActionListener(akc_zmien_st);

        // panel z zakładkami
        tp.addTab("klienci", p_kli);
        tp.addTab("książki", p_ksi);
        tp.addTab("zamówienia", p_zam);
        getContentPane().add(tp, BorderLayout.CENTER);
        
        // pole na komentarze
        komunikat.setEditable(false);
        getContentPane().add(komunikat, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}


public class Ksiegarnia {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        new Okno();
    }
}
