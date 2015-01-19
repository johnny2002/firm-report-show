package com.ibm.gbsc.reportadapter.cognos;

import org.springframework.beans.factory.annotation.Value;

import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.CAM;
import com.cognos.developer.schemas.bibus._3.FormFieldVar;
import com.cognos.developer.schemas.bibus._3.FormatEnum;
import com.cognos.developer.schemas.bibus._3.HdrSession;

public abstract class BaseBean {

	private String cognosApp;

	private String password;

	private String userName;

	private String namespace;

	public BaseBean() {
	}

	public BiBusHeader getBIBusHeader() {
		BiBusHeader bibus = new BiBusHeader();
		bibus.setCAM(new CAM());
		bibus.getCAM().setAction("logonAs");
		bibus.setHdrSession(new HdrSession());

		FormFieldVar[] ffs = new FormFieldVar[3];

		ffs[0] = new FormFieldVar();
		ffs[0].setName("CAMUsername");
		ffs[0].setValue(this.userName);
		ffs[0].setFormat(FormatEnum.not_encrypted);

		ffs[1] = new FormFieldVar();
		ffs[1].setName("CAMPassword");
		ffs[1].setValue(this.password);
		ffs[1].setFormat(FormatEnum.not_encrypted);

		ffs[2] = new FormFieldVar();
		ffs[2].setName("CAMNamespace");
		ffs[2].setValue(this.namespace);
		ffs[2].setFormat(FormatEnum.not_encrypted);

		bibus.getHdrSession().setFormFieldVars(ffs);
		return bibus;
	}

	protected String getCognosAppUrl() {
		return cognosApp + "servlet/dispatch";
	}

	/**
	 * @return the cognosApp
	 */
	public String getCognosApp() {
		return cognosApp;
	}

	/**
	 * @param cognosApp
	 *            the cognosApp to set
	 */
	@Value("${cognosServiceRoot}")
	public void setCognosApp(String cognosApp) {
		this.cognosApp = cognosApp;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	@Value("${cognosPassword}")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	@Value("${cognosUserName}")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	@Value("${cognosNamespace}")
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
