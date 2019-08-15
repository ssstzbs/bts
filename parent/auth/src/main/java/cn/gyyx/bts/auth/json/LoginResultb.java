package cn.gyyx.bts.auth.json;

public class LoginResultb {
	private String codeb;
	private String Idb;
    private int goldb;
    
    private int silver;
    private int level;
	
	public String getCode() {
		return codeb;
	}

	public void setCode(String code) {
		this.codeb = code;
	}

	public String getId() {
		return Idb;
	}

	public void setId(String id) {
		Idb = id;
	}

	public int getGold() {
		return goldb;
	}

	public void setGold(int gold) {
		this.goldb = gold;
	}

	public int getSilver() {
		return silver;
	}

	public void setSilver(int silver) {
		this.silver = silver;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "LoginResultb [codeb=" + codeb + ", Idb=" + Idb + ", goldb=" + goldb + ", silver=" + silver + ", level="
				+ level + "]";
	}

}
