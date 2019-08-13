package cn.gyyx.bts.auth.json;

public class LoginResult {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "LoginResult [code=" + code + "]";
	}
		
}
