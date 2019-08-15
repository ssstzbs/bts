package cn.gyyx.bts.auth.json;

public class LoginResultb {
	private String codeb;
	private String Idb;
    private int goldb;
	
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

	@Override
	public String toString() {
		return "LoginResult [code=" + codeb + ", Id=" + Idb + ", gold=" + goldb + "]";
	}

}
