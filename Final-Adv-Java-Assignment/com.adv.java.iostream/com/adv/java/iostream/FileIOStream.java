package com.adv.java.iostream;

import java.io.FileInputStream;
import java.io.IOException;
import com.adv.java.iostream.internal.FileIOStreamImpl;

public class FileIOStream {
	//call method FileInput of class FileIOStreamImpl
	public FileInputStream input (String filename) throws IOException {
		FileInputStream in=new FileIOStreamImpl().FileInput(filename);
		return in;
	}
	//call method FileReadIn of class FileIOStreamImpl
	public String[] inputContent(String filename) throws IOException {
		String[] results=new FileIOStreamImpl().FileReadIn(filename);
		return results;
	}
}
