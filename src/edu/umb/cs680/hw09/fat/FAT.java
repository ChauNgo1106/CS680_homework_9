package edu.umb.cs680.hw09.fat;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import edu.umb.cs680.hw09.apfs.ApfsDirectory;
import edu.umb.cs680.hw09.fs.FSElement;
import edu.umb.cs680.hw09.fs.FileSystem;

/*
 FAT32
– Name: up to 11 characters (8+3 format), case insensitive, 
– Multiple tree structures (drives)
– No links allowed
 */
public class FAT extends FileSystem {

	private static FAT instance = null;

	// default constructor
	private FAT() {
	}

	// Singleton
	public static FileSystem getFileSystem() {
		try {
			return Objects.requireNonNull(instance);
		} catch (NullPointerException ex) {
			instance = new FAT();
			return instance;
		}
	}

	protected FSElement createDefaultRoot() {
		return new FatDirectory(null, "root", 0,
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)));
	}

	public ArrayList<FSElement> getRootDirs() {
		return this.rootDirs;
	}
	
	public void addRoot(FatDirectory root) {
		this.rootDirs.add(root);
	}

	public String getName() {
		return this.name;
	}
	public int getCapacity() {
		return this.capacity;
	}
	public int getId() {
		return this.id;
	}
	
}
