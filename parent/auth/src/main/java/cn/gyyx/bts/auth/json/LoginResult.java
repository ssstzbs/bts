package cn.gyyx.bts.auth.json;

public class LoginResult {
	private String code;
        private String Id;

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

	@Override
	public String toString() {
		return "LoginResult [code=" + code + "]";
	}
		
}
