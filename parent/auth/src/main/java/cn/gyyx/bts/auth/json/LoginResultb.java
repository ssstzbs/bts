package cn.gyyx.bts.auth.json;

public class LoginResultb {
	private String code;
	private String Id;
    private int gold;
    
    private int silver;
    private int level;

    private int exp;
    
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return "LoginResultb [codeb=" + code + ", Idb=" + Id + ", goldb=" + gold + ", silver=" + silver + ", level="
				+ level + ", exp=" + exp + "]";
	}

}
