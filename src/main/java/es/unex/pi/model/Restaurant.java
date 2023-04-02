package es.unex.pi.model;

public class Restaurant {

	private long id;
	private String name;
	private String address;
	private String telephone;
	private String city;
	private int Idu;
	private int minPrice;
	private int maxPrice;
	private String contactEmail;
	private int gradesAverage;
	private int bikeFriendly;
	private int available;
	private String img;
	private String subtitulo;
	
	
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getGradesAverage() {
		return gradesAverage;
	}
	public void setGradesAverage(int gradesAverage) {
		this.gradesAverage = gradesAverage;
	}
	public int getBikeFriendly() {
		return bikeFriendly;
	}
	public void setBikeFriendly(int bikeFriendly) {
		this.bikeFriendly = bikeFriendly;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public int getIdu() {
		return Idu;
	}
	public void setIdu(int idu) {
		Idu = idu;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}



	
	
}
