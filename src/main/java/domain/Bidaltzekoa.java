package domain;

public class Bidaltzekoa {

	String izenburua;
	String testua;
	
	public Bidaltzekoa(String izenburua, String testua) {
		super();
		this.izenburua = izenburua;
		this.testua = testua;
	}
	
	public String getIzenburua() {
		return izenburua;
	}
	public void setIzenburua(String izenburua) {
		this.izenburua = izenburua;
	}
	public String getTestua() {
		return testua;
	}
	public void setTestua(String testua) {
		this.testua = testua;
	}
	
}
