package cn.gyyx.bts.auth.json;

public class LoginResultc {
	private String codec;
	private String Idc;
    private int goldc;
	
	public String getCode() {
		return codec;
	}

	public void setCode(String code) {
		this.codec = code;
	}

	public String getId() {
		return Idc;
	}

	public void setId(String id) {
		Idc = id;
	}

	public int getGold() {
		return goldc;
	}

	public void setGold(int gold) {
		this.goldc = gold;
	}

	@Override
	public String toString() {
		return "LoginResult [code=" + codec + ", Id=" + Idc + ", gold=" + goldc + "]";
	}

}
