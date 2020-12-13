package edu.umb.cs680.hw09.fat;

import java.time.LocalDateTime;
import edu.umb.cs680.hw09.apfs.ApfsDirectory;
import edu.umb.cs680.hw09.fs.FSElement;

public abstract class FatFSElement extends FSElement {

	protected FatDirectory parent;

	public FatFSElement(FatDirectory parent, String name, int size,
			LocalDateTime creationTime) {
		
		super(name, size, creationTime);
		if(this.name.length() < 0 || name.length() > 11) {
			throw new IllegalArgumentException("File name is allowed up to 11 characters");
		}
		this.parent = parent;
	}

	public FatDirectory getParent() {
		return this.parent;
	}

	public String getParentToString() {
		return this.parent.getName();
	}

	public void setParent(FatDirectory parent) {
		this.parent = parent;
	}

}
