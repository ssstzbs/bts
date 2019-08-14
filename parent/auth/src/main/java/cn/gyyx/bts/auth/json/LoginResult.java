package cn.gyyx.bts.auth.json;

public class LoginResult {
	private String code;
	private String Id;
    private int gold;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Override
	public String toString() {
		return "LoginResult [code=" + code + ", Id=" + Id + ", gold=" + gold + "]";
	}

}
