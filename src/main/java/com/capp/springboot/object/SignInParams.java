/*
*
*-------Modification History-------
* Sr No         Date             Change No       Author             Description
*------         ----             ---------       ------             ------------
* 01            01 Oct 2017          CH01         Gireesh M T         Initial Version
* 
*
*
*@Author Gireesh M T*
*/
package com.capp.springboot.object;

public class SignInParams {
    private String UseNameFromDb;
    private String PassWordFromDb;
    private String TableNameFromDb;
    private String NameFromDb;
    private String ViewFromDb;
    private String errMsg;
    private boolean isShwRed=false;
    private String gender;
    private String email;
    private String phone;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUseNameFromDb(String UseNameFromDb) {
        this.UseNameFromDb = UseNameFromDb;
    }

    public String getUseNameFromDb() {
        return UseNameFromDb;
    }

    public void setPassWordFromDb(String PassWordFromDb) {
        this.PassWordFromDb = PassWordFromDb;
    }

    public String getPassWordFromDb() {
        return PassWordFromDb;
    }

    public void setTableNameFromDb(String TableNameFromDb) {
        this.TableNameFromDb = TableNameFromDb;
    }

    public String getTableNameFromDb() {
        return TableNameFromDb;
    }

    public void setNameFromDb(String NameFromDb) {
        this.NameFromDb = NameFromDb;
    }

    public String getNameFromDb() {
        return NameFromDb;
    }

    public void setViewFromDb(String ViewFromDb) {
        this.ViewFromDb = ViewFromDb;
    }

    public String getViewFromDb() {
        return ViewFromDb;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setIsShwRed(boolean isShwRed) {
        this.isShwRed = isShwRed;
    }

    public boolean isIsShwRed() {
        return isShwRed;
    }
}
