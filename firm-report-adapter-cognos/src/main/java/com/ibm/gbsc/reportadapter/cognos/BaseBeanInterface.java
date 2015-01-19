package com.ibm.gbsc.reportadapter.cognos;

import java.io.OutputStream;

public abstract interface BaseBeanInterface {
	public abstract void createService();

	public abstract OutputStream getImageStream();
}
