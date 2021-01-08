
package database.model;
public class produkt {
    public static void add(produkt produkt) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    	private int id;
	private String nazwa;
	private int cena;
	private String opis;
	private int ilosc;
	public produkt(int id, String nazwa, int cena, String opis, int ilosc) {
		this.id = id;
		this.nazwa = nazwa;
		this.cena = cena;
		this.opis = opis;
		this.ilosc = ilosc; 
	}
	
	@Override
	public String toString() {
		return "produkt [id=" + id + ", nazwa=" + nazwa + ", cena=" + cena + ", opis=" + opis
				+ ", ilosc=" + ilosc + "]";
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public int getIlosc() {
		return ilosc;
	}
	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}

}
