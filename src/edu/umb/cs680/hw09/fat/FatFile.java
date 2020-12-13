package edu.umb.cs680.hw09.fat;

import java.time.LocalDateTime;

import edu.umb.cs680.hw09.apfs.ApfsDirectory;

public class FatFile extends FatFSElement {

	public FatFile(FatDirectory parent, String name, int size, LocalDateTime creationTime) {
		super(parent, name, size, creationTime);
	}

	// this is file not directory
	@Override
	public boolean isDirectory() {
		return false;
	}
}
