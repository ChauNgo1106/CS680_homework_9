package edu.umb.cs680.hw09.fat;

import java.time.LocalDateTime;

import java.util.LinkedList;

import edu.umb.cs680.hw09.fs.FSElement;
import edu.umb.cs680.hw09.apfs.ApfsLink;

public class FatDirectory extends FatFSElement {
	private LinkedList<FatFSElement> children = new LinkedList<>();

	public FatDirectory(FatDirectory parent, String name, int size, LocalDateTime creationTime) {
		super(parent, name, size, creationTime); // called FSElement constructor ==> grandparent
	}

	// this is directory class
	@Override
	public boolean isDirectory() {
		return true;
	}

	public LinkedList<FatFSElement> getChildren() {
		return this.children;
	}

	public void appendChild(FatFSElement child) {
		children.add(child);
		child.setParent(this);
	}

	public int getSize() {
		return this.size;
	}

	public int countChildren() {
		return this.children.size();
	}

	public LinkedList<FatFSElement> getFiles() {
		LinkedList<FatFSElement> files = new LinkedList<>();
		for (FatFSElement child : children) {
			if (!child.isDirectory()) {
				files.add(child);
			}
		}
		return files;
	}

	public int getTotalSize() {
		int total = 0;
		for (FatFSElement child : children) {
			// if this is not a Directory ==> must check whether it is Link or File
			if (!child.isDirectory()) {
				// actual File size.
				total += child.getSize();

			} else if (child.isDirectory()) {
				FatDirectory convert = (FatDirectory) child;
				total += convert.getTotalSize();
			}
		}
		return total;
	}

	public LinkedList<FatDirectory> getSubDirectories() {
		LinkedList<FatDirectory> sub = new LinkedList<>();
		for (FatFSElement child : children) {
			if (child.isDirectory()) {
				sub.add((FatDirectory) child);
			}
		}
		return sub;
	}

	public void printChildName(LinkedList<FatFSElement> children) {
		for (FatFSElement child : children) {
			System.out.println(child.getName());
		}
	}
}
